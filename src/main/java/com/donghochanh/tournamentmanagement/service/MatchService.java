package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.dto.MatchResultDto;

public interface MatchService {
	void generateMatches(Long tournamentId);

	void updateMatchResult(MatchResultDto matchResultDto, Long matchId, Long tournamentId);
}
