package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.dto.TeamDto;
import com.donghochanh.tournamentmanagement.entity.Team;

import java.util.List;

public interface TeamService {
	List<Team> getAllTeams();

	Team getTeamById(Long id);

	void createTeam(TeamDto teamDto);

	void updateTeam(Long id, TeamDto teamDto);

	void deleteTeam(Long id);

	List<Team> getTeamsByTournamentId(Long id);
}
