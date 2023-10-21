package com.donghochanh.tournamentmanagement.repository;

import com.donghochanh.tournamentmanagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
	List<Team> findAllByTournamentsId(Long id);
	
	Team findByIdAndTournamentsId(Long id, Long tournamentId);
}
