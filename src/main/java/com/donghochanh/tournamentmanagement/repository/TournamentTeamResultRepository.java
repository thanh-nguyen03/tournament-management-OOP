package com.donghochanh.tournamentmanagement.repository;

import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentTeamResultRepository extends JpaRepository<TournamentTeamResult, Long> {
	List<TournamentTeamResult> findAllByTournamentIdOrderByPointsDescGoalsForDescGoalsAgainstDesc(Long tournamentId);

	Optional<TournamentTeamResult> findByTeamId(Long teamId);
}
