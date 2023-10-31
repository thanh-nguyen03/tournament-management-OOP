package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.constants.TournamentStatus;
import com.donghochanh.tournamentmanagement.dto.TournamentDto;
import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.exceptions.TeamAlreadyAddedException;
import com.donghochanh.tournamentmanagement.repository.TeamRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentTeamResultRepository;
import com.donghochanh.tournamentmanagement.service.TeamService;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
	private final TournamentRepository tournamentRepository;
	private final TeamRepository teamRepository;
	private final TournamentTeamResultRepository tournamentTeamResultRepository;
	private final TeamService teamService;

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Tournament tournament = new Tournament();
		tournament.setName(tournamentDto.name());
		tournament.setPrize(Integer.valueOf(tournamentDto.prize().trim()));
		tournament.setStartDate(formatter.parse(tournamentDto.startDate(), LocalDate::from));
		tournament.setEndDate(formatter.parse(tournamentDto.endDate(), LocalDate::from));
		tournament.setStatus(TournamentStatus.NOT_STARTED);


		this.tournamentRepository.save(tournament);
	}

	@Override
	public void updateTournament(Long id, TournamentDto tournamentDto) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		tournament.setName(tournamentDto.name());
		tournament.setPrize(Integer.valueOf(tournamentDto.prize().trim()));
		tournament.setStartDate(formatter.parse(tournamentDto.startDate(), LocalDate::from));
		tournament.setEndDate(formatter.parse(tournamentDto.endDate(), LocalDate::from));

		this.tournamentRepository.save(tournament);
	}

	@Override
	public void updateTournamentStatus(Long id, String status) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		tournament.setStatus(TournamentStatus.valueOf(status));
	}

	@Override
	public void deleteTournament(Long id) {
		this.tournamentRepository.deleteById(id);
	}

	@Override
	public void addTeam(Long id, Long teamId) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
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
	public void removeTeam(Long id, Long playerId) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			return;
		}

		Team team = this.teamService.getTeamById(playerId);

		if (team == null) {
			return;
		}

		tournament.getTeams().remove(team);
		this.tournamentRepository.save(tournament);
	}

	@Override
	public List<TournamentTeamResult> getTournamentRanking(Long id) {
		Tournament tournament = this.tournamentRepository.findById(id).orElse(null);

		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		return this.tournamentTeamResultRepository.findAllByTournamentIdOrderByPointsDescGoalsForDescGoalsAgainstDesc(id);
	}
}
