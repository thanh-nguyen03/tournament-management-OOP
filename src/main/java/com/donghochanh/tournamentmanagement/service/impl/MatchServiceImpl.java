package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.constants.MatchResult;
import com.donghochanh.tournamentmanagement.dto.MatchResultDto;
import com.donghochanh.tournamentmanagement.entity.Match;
import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.repository.MatchRepository;
import com.donghochanh.tournamentmanagement.repository.TeamRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentTeamResultRepository;
import com.donghochanh.tournamentmanagement.service.MatchService;
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
public class MatchServiceImpl implements MatchService {
	private final TournamentService tournamentService;
	private final TeamRepository teamRepository;
	private final MatchRepository matchRepository;
	private final TournamentTeamResultRepository tournamentTeamResultRepository;

	@Override
	public void generateMatches(Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);


		if (tournament == null) {
			return;
		}

		List<Team> teams = this.teamRepository.findAllByTournamentsId(tournamentId);
		List<Match> matches = this.generateRobinRoundMatches(teams, tournament);
		this.matchRepository.saveAll(matches);
		tournament.setMatches(matches);
	}

	@Override
	public void updateMatchResult(MatchResultDto matchResultDto, Long matchId, Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);
		if (tournament == null) {
			return;
		}

		Match match = tournament.getMatches().stream().filter(m -> m.getId().equals(matchId)).findFirst().orElse(null);
		if (match == null) {
			return;
		}

		match.setTeam1Score(matchResultDto.team1Score());
		match.setTeam2Score(matchResultDto.team2Score());
		if (matchResultDto.team1Score() > matchResultDto.team2Score()) {
			match.setMatchResult(MatchResult.TEAM1_WIN);
		} else if (matchResultDto.team1Score() < matchResultDto.team2Score()) {
			match.setMatchResult(MatchResult.TEAM2_WIN);
		} else {
			match.setMatchResult(MatchResult.DRAW);
		}


		this.matchRepository.save(match);
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

		for (int i = 0; i < teams.size() - 1; i++) {
			for (int j = 0; j < firstGroup.size(); j++) {
				Match match = new Match();
				match.setNumber(i + 1);
				match.setTeam1(firstGroup.get(j));
				match.setTeam2(secondGroup.get(j));
				match.setTournament(tournament);
				match.setStadium(firstGroup.get(j).getStadium());
				match.setMatchResult(MatchResult.DRAW);
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

	private void updateTeamResult(Long matchId, Long teamId, Integer goalsFor, Integer goalsAgainst, Integer points) {
		Match match = this.matchRepository.findById(matchId).orElse(null);

		if (match == null) {
			throw new NotFoundException("Match not found");
		}

		TournamentTeamResult tournamentTeamResult = this.tournamentTeamResultRepository.findByTeamId(teamId).orElse(null);

		if (tournamentTeamResult == null) {
			TournamentTeamResult tournamentTeamResult1 = new TournamentTeamResult();
			Team team = this.teamRepository.findById(teamId).orElse(null);

			if (team == null) {
				throw new NotFoundException("Team not found");
			}

			tournamentTeamResult1.setTeam(team);
			tournamentTeamResult1.setTournament(match.getTournament());
			tournamentTeamResult1.setGoalsFor(goalsFor);
			tournamentTeamResult1.setGoalsAgainst(goalsAgainst);
			tournamentTeamResult1.setPoints(points);
			
			this.tournamentTeamResultRepository.save(tournamentTeamResult1);
			return;
		}

		tournamentTeamResult.setGoalsFor(tournamentTeamResult.getGoalsFor() + goalsFor);
		tournamentTeamResult.setGoalsAgainst(tournamentTeamResult.getGoalsAgainst() + goalsAgainst);
		tournamentTeamResult.setPoints(tournamentTeamResult.getPoints() + points);
		this.tournamentTeamResultRepository.save(tournamentTeamResult);
	}
}
