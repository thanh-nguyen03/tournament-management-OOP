package com.donghochanh.tournamentmanagement.repository;

import com.donghochanh.tournamentmanagement.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
	Integer countByTeam1_IdOrTeam2_Id(Long team1Id, Long team2Id);
}
