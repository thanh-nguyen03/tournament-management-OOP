package com.donghochanh.tournamentmanagement.dto;

public record TournamentTeamResultDto(
	Long teamId,
	Integer goalsFor,
	Integer goalsAgainst,
	Integer points
) {
}
