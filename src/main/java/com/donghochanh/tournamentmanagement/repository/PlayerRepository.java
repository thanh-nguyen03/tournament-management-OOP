package com.donghochanh.tournamentmanagement.repository;

import com.donghochanh.tournamentmanagement.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
