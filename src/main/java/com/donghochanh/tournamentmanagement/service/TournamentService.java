package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.dto.TournamentDto;
import com.donghochanh.tournamentmanagement.entity.Tournament;

import java.util.List;

public interface TournamentService {
	List<Tournament> getAllTournaments();

	Tournament getTournamentById(Long id);

	void createTournament(TournamentDto tournamentDto);

	void updateTournament(Long id, TournamentDto tournamentDto);

	void deleteTournament(Long id);

	void addTeam(Long id, Long playerId);

	void removeTeam(Long id, Long playerId);
}
