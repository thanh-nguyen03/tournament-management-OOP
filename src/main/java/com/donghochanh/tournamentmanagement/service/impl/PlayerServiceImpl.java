package com.donghochanh.tournamentmanagement.service.impl;

import com.donghochanh.tournamentmanagement.dto.PlayerDto;
import com.donghochanh.tournamentmanagement.entity.Player;
import com.donghochanh.tournamentmanagement.repository.PlayerRepository;
import com.donghochanh.tournamentmanagement.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
	private final PlayerRepository playerRepository;

	public PlayerServiceImpl(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public List<Player> findAllPlayers() {
		return this.playerRepository.findAll();
	}

	@Override
	public Player findPlayerById(Long id) {
		return this.playerRepository.findById(id).orElse(null);
	}

	@Override
	public Player createPlayer(PlayerDto playerDto) {
		Player player = new Player();
		player.setName(playerDto.name());
		player.setAge(playerDto.age());
		player.setNationality(playerDto.nationality());

		return this.playerRepository.save(player);
	}

	@Override
	public Player updatePlayer(Long id, PlayerDto playerDto) {
		Player player = this.playerRepository.findById(id).orElse(null);

		if (player == null) {
			return null;
		}

		player.setName(playerDto.name());
		player.setAge(playerDto.age());
		player.setNationality(playerDto.nationality());

		return this.playerRepository.save(player);
	}

	@Override
	public void deletePlayer(Long id) {
		this.playerRepository.deleteById(id);
	}
}
