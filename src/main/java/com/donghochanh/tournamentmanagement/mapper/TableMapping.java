package com.donghochanh.tournamentmanagement.mapper;

import com.donghochanh.tournamentmanagement.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TableMapping {
	public List<List<Object>> playerToTable(List<Player> players) {
		List<List<Object>> table = new ArrayList<>();
		for (Player player : players) {
			table.add(new ArrayList<>(List.of(player.getId(), player.getName(), player.getAge(), player.getNationality())));
		}
		return table;
	}
}
