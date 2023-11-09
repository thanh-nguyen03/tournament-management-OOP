package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.constants.MatchResult;
import com.donghochanh.tournamentmanagement.constants.MatchStatus;
import com.donghochanh.tournamentmanagement.constants.TournamentStatus;
import com.donghochanh.tournamentmanagement.dto.MatchResultDto;
import com.donghochanh.tournamentmanagement.entity.Match;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.repository.MatchRepository;
import com.donghochanh.tournamentmanagement.repository.TeamRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentRepository;
import com.donghochanh.tournamentmanagement.repository.TournamentTeamResultRepository;
import com.donghochanh.tournamentmanagement.service.MatchService;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
	private final TournamentService tournamentService;
	private final TeamRepository teamRepository;
	private final MatchRepository matchRepository;
	private final TournamentTeamResultRepository tournamentTeamResultRepository;
	private final TournamentRepository tournamentRepository;

	@Override
	public Match updateMatchResult(MatchResultDto matchResultDto, Long matchId, Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);
		if (tournament == null) {
			throw new NotFoundException("Tournament not found");
		}

		if (tournament.getStatus().equals(TournamentStatus.FINISHED)) {
			throw new NotFoundException("Tournament is finished");
		}

		Match match = this.matchRepository.findById(matchId).orElse(null);
		if (match == null) {
			throw new NotFoundException("Match not found");
		}

		if (match.getMatchStatus().equals(MatchStatus.FINISHED)) {
			throw new RuntimeException("Match is finished");
		}

		match.setTeam1Score(matchResultDto.team1Score());
		match.setTeam2Score(matchResultDto.team2Score());
		match.setMatchStatus(matchResultDto.status());
		if (matchResultDto.team1Score() > matchResultDto.team2Score()) {
			match.setMatchResult(MatchResult.TEAM1_WIN);
		} else if (matchResultDto.team1Score() < matchResultDto.team2Score()) {
			match.setMatchResult(MatchResult.TEAM2_WIN);
		} else {
			match.setMatchResult(MatchResult.DRAW);
		}

		// Only update ranking if match is finished
		if (!matchResultDto.status().equals(MatchStatus.FINISHED)) {
			return this.matchRepository.save(match);
		}

		this.updateTeamResult(
			tournamentId,
			match.getTeam1().getId(),
			matchResultDto.team1Score(),
			matchResultDto.team2Score(),
			match.getMatchResult().equals(MatchResult.TEAM1_WIN) ? 3 : match.getMatchResult().equals(MatchResult.DRAW) ? 1 : 0
		);
		this.updateTeamResult(
			tournamentId,
			match.getTeam2().getId(),
			matchResultDto.team2Score(),
			matchResultDto.team1Score(),
			match.getMatchResult().equals(MatchResult.TEAM2_WIN) ? 3 : match.getMatchResult().equals(MatchResult.DRAW) ? 1 : 0
		);

		return this.matchRepository.save(match);
	}

	@Override
	public List<Match> getMatchesByTournamentId(Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);
		if (tournament == null) {
			return null;
		}

		return tournament.getMatches();
	}

	private void updateTeamResult(Long tournamentId, Long teamId, Integer goalsFor, Integer goalsAgainst, Integer points) {
		TournamentTeamResult tournamentTeamResult = this.tournamentTeamResultRepository.findByTeamIdAndTournamentId(teamId, tournamentId).orElse(null);

		if (tournamentTeamResult == null) {
			return;
		}

		tournamentTeamResult.setMatchesPlayed(tournamentTeamResult.getMatchesPlayed() + 1);
		tournamentTeamResult.setWins(tournamentTeamResult.getWins() + (points == 3 ? 1 : 0));
		tournamentTeamResult.setDraws(tournamentTeamResult.getDraws() + (points == 1 ? 1 : 0));
		tournamentTeamResult.setLosses(tournamentTeamResult.getLosses() + (points == 0 ? 1 : 0));
		tournamentTeamResult.setGoalsFor(tournamentTeamResult.getGoalsFor() + goalsFor);
		tournamentTeamResult.setGoalsAgainst(tournamentTeamResult.getGoalsAgainst() + goalsAgainst);
		tournamentTeamResult.setPoints(tournamentTeamResult.getPoints() + points);
		this.tournamentTeamResultRepository.save(tournamentTeamResult);
	}
}
