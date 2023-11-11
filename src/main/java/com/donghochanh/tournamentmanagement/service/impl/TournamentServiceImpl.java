package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.constants.MatchResult;
import com.donghochanh.tournamentmanagement.constants.MatchStatus;
import com.donghochanh.tournamentmanagement.constants.TournamentStatus;
import com.donghochanh.tournamentmanagement.dto.TournamentDto;
import com.donghochanh.tournamentmanagement.entity.Match;
import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.exceptions.TeamAlreadyAddedException;
import com.donghochanh.tournamentmanagement.repository.MatchRepository;
import com.donghochanh.tournamentmanagement.repository.TeamRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentTeamResultRepository;
import com.donghochanh.tournamentmanagement.service.TeamService;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
	private final TournamentRepository tournamentRepository;
	private final TeamRepository teamRepository;
	private final TournamentTeamResultRepository tournamentTeamResultRepository;
	private final TeamService teamService;
	private final MatchRepository matchRepository;

	@Override
	public List<Tournament> getAllTournaments() {
		return this.tournamentRepository.findAll();
	}

	@Override
	public Tournament getTournamentById(Long id) {
		return this.tournamentRepository.findById(id).orElse(null);
	}

	@Override
	public void createTournament(TournamentDto tournamentDto) {
		Tournament tournament = new Tournament();
		tournament.setName(tournamentDto.name());
		tournament.setPrize(Integer.valueOf(tournamentDto.prize().trim()));
		tournament.setStatus(TournamentStatus.NOT_STARTED);

		this.tournamentRepository.save(tournament);
	}

	@Override
	public void updateTournament(Long id, TournamentDto tournamentDto) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		if (tournament.getStatus().equals(TournamentStatus.STARTED)) {
			throw new RuntimeException("Tournament already started");
		}

		if (tournament.getStatus().equals(TournamentStatus.FINISHED)) {
			throw new RuntimeException("Tournament already finished");
		}

		tournament.setName(tournamentDto.name());
		tournament.setPrize(Integer.valueOf(tournamentDto.prize().trim()));

		this.tournamentRepository.save(tournament);
	}

	@Override
	public void startTournament(Long id) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		if (tournament.getStatus() == TournamentStatus.STARTED) {
			throw new RuntimeException("Tournament already started");
		}

		if (tournament.getStatus() == TournamentStatus.FINISHED) {
			throw new RuntimeException("Tournament already finished");
		}

		List<Team> teams = this.teamRepository.findAllByTournamentsId(id);


		if (teams.size() < 2) {
			throw new RuntimeException("Tournament must have at least 2 teams");
		}

		List<Match> matches = this.generateRobinRoundMatches(teams, tournament);
		tournament.setMatches(matches);
		tournament.setStatus(TournamentStatus.STARTED);

		// Set all team result to 0
		List<TournamentTeamResult> tournamentTeamResults = new ArrayList<>();
		for (Team team : teams) {
			if (team == null) {
				continue;
			}
			TournamentTeamResult tournamentTeamResult = new TournamentTeamResult();
			tournamentTeamResult.setTeam(team);
			tournamentTeamResult.setTournament(tournament);
			tournamentTeamResult.setMatchesPlayed(0);
			tournamentTeamResult.setWins(0);
			tournamentTeamResult.setDraws(0);
			tournamentTeamResult.setLosses(0);
			tournamentTeamResult.setGoalsFor(0);
			tournamentTeamResult.setGoalsAgainst(0);
			tournamentTeamResult.setPoints(0);
			tournamentTeamResults.add(tournamentTeamResult);
		}

		this.matchRepository.saveAll(matches);
		this.tournamentTeamResultRepository.saveAll(tournamentTeamResults);
		this.tournamentRepository.save(tournament);
	}

	@Override
	public void endTournament(Long id) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		if (tournament.getStatus() == TournamentStatus.NOT_STARTED) {
			throw new RuntimeException("Tournament not started");
		}

		if (tournament.getStatus() == TournamentStatus.FINISHED) {
			throw new RuntimeException("Tournament already finished");
		}

		tournament.setStatus(TournamentStatus.FINISHED);
		this.tournamentRepository.save(tournament);
	}

	@Override
	public void deleteTournament(Long id) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		if (!tournament.getStatus().equals(TournamentStatus.NOT_STARTED)) {
			throw new RuntimeException("Tournament already started or finished cannot be deleted");
		}

		this.tournamentRepository.deleteById(id);
	}

	@Override
	public void addTeam(Long id, Long teamId) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		if (tournament.getStatus() == TournamentStatus.STARTED) {
			throw new RuntimeException("Tournament already started");
		}

		if (tournament.getStatus() == TournamentStatus.FINISHED) {
			throw new RuntimeException("Tournament already finished");
		}

		Team existingTeam = this.teamRepository.findByIdAndTournamentsId(teamId, id);

		if (existingTeam != null) {
			throw new TeamAlreadyAddedException("Team already added to tournament");
		}

		Team team = this.teamService.getTeamById(teamId);

		if (team == null) {
			return;
		}

		tournament.getTeams().add(team);
		this.tournamentRepository.save(tournament);
	}

	@Override
	public void addMultipleTeams(Long id, List<Long> teamIds) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		if (tournament.getStatus() == TournamentStatus.STARTED) {
			throw new RuntimeException("Tournament already started");
		}

		if (tournament.getStatus() == TournamentStatus.FINISHED) {
			throw new RuntimeException("Tournament already finished");
		}

		List<Team> teams = this.teamRepository.findAllById(teamIds);

		if (teams == null) {
			return;
		}

		for (Team team : teams) {
			if (team == null) {
				continue;
			}
			Team existingTeam = this.teamRepository.findByIdAndTournamentsId(team.getId(), id);

			if (existingTeam != null) {
				throw new TeamAlreadyAddedException("Team already added to tournament");
			}

			tournament.getTeams().add(team);
		}

		this.tournamentRepository.save(tournament);
	}

	@Override
	public void removeTeam(Long id, Long teamId) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		if (tournament.getStatus() == TournamentStatus.STARTED) {
			throw new RuntimeException("Tournament already started");
		}

		if (tournament.getStatus() == TournamentStatus.FINISHED) {
			throw new RuntimeException("Tournament already finished");
		}

		Team team = this.teamService.getTeamById(teamId);

		if (team == null) {
			return;
		}

		tournament.getTeams().remove(team);
		this.tournamentRepository.save(tournament);
	}

	private List<Match> generateRobinRoundMatches(List<Team> teams, Tournament tournament) {
		List<Match> matches = new ArrayList<>();
		int halfSize = teams.size() / 2;
		if (teams.size() % 2 == 1) {
			halfSize++;
			teams.add(null);
		}
		List<Team> firstGroup = new ArrayList<>(teams.subList(0, halfSize));
		List<Team> secondGroup = new ArrayList<>(teams.subList(halfSize, teams.size()));
		Collections.reverse(secondGroup);

		if (secondGroup.get(0) == null) {
			secondGroup.remove(0);
			secondGroup.add(null);
		}

		int currentNumber = 1;
		for (int i = 0; i < teams.size() - 1; i++) {
			for (int j = 0; j < firstGroup.size(); j++) {
				if (firstGroup.get(j) == null || secondGroup.get(j) == null) {
					continue;
				}
				Match match = new Match();
				match.setNumber(currentNumber++);
				match.setTeam1(firstGroup.get(j));
				match.setTeam2(secondGroup.get(j));
				match.setTournament(tournament);
				match.setStadium(firstGroup.get(j).getStadium());
				match.setMatchResult(MatchResult.DRAW);
				match.setMatchStatus(MatchStatus.NOT_STARTED);
				match.setTeam1Score(0);
				match.setTeam2Score(0);
				matches.add(match);
			}
			Team temp = firstGroup.get(0);
			firstGroup.remove(0);
			secondGroup.add(1, temp);
			temp = secondGroup.get(secondGroup.size() - 1);
			secondGroup.remove(secondGroup.size() - 1);
			firstGroup.add(temp);
		}
		return matches;
	}
}