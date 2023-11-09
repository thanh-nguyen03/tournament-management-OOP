package com.donghochanh.tournamentmanagement.dto;

import com.donghochanh.tournamentmanagement.constants.MatchStatus;

public record MatchResultDto(
	Integer team1Score,
	Integer team2Score,
	MatchStatus status
) {
}
