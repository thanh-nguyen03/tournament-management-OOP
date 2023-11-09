package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.repository.TournamentTeamResultRepository;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import com.donghochanh.tournamentmanagement.service.TournamentTeamResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TournamentTeamResultServiceImpl implements TournamentTeamResultService {
	private final TournamentTeamResultRepository tournamentTeamResultRepository;
	private final TournamentService tournamentService;

	@Override
	public List<TournamentTeamResult> getTournamentRanking(Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);
		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}
		return this.tournamentTeamResultRepository.findAllByTournamentIdOrderByPointsDescGoalsForDescGoalsAgainstDesc(tournamentId);
	}
}
