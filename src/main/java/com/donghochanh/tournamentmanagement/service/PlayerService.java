package com.donghochanh.tournamentmanagement.service;

import com.donghochanh.tournamentmanagement.dto.PlayerDto;
import com.donghochanh.tournamentmanagement.entity.Player;

import java.util.List;

public interface PlayerService {
	List<Player> findAllPlayers();

	Player findPlayerById(Long id);

	Player createPlayer(PlayerDto playerDto);

	Player updatePlayer(Long id, PlayerDto playerDto);

	void deletePlayer(Long id);
}
