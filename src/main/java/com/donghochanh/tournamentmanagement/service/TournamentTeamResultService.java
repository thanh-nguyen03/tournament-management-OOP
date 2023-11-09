package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;

import java.util.List;

public interface TournamentTeamResultService {
	List<TournamentTeamResult> getTournamentRanking(Long tournamentId);
}
