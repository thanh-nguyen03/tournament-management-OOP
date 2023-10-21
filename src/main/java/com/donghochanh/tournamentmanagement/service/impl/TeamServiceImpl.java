package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.dto.TeamDto;
import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.repository.TeamRepository;
import com.donghochanh.tournamentmanagement.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
	private final TeamRepository teamRepository;

	@Override
	public List<Team> getAllTeams() {
		return this.teamRepository.findAll();
	}

	@Override
	public Team getTeamById(Long id) {
		return this.teamRepository.findById(id).orElse(null);
	}

	@Override
	public void createTeam(TeamDto teamDto) {
		Team team = new Team();
		team.setName(teamDto.name());
		team.setStadium(teamDto.stadium());
		team.setCountry(teamDto.country());

		this.teamRepository.save(team);
	}

	@Override
	public void updateTeam(Long id, TeamDto teamDto) {
		Team team = this.teamRepository.findById(id).orElse(null);

		if (team == null) {
			return;
		}

		team.setName(teamDto.name());
		team.setStadium(teamDto.stadium());
		team.setCountry(teamDto.country());

		this.teamRepository.save(team);
	}

	@Override
	public void deleteTeam(Long id) {
		this.teamRepository.deleteById(id);
	}

	@Override
	public List<Team> getTeamsByTournamentId(Long id) {
		return this.teamRepository.findAllByTournamentsId(id);
	}
}
