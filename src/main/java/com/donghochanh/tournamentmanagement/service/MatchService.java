package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.dto.MatchResultDto;
import com.donghochanh.tournamentmanagement.entity.Match;

import java.util.List;

public interface MatchService {
	Match updateMatchResult(MatchResultDto matchResultDto, Long matchId, Long tournamentId);

	List<Match> getMatchesByTournamentId(Long id);
}
