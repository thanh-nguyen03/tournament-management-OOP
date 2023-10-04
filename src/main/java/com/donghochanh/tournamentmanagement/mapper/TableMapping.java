package com.donghochanh.tournamentmanagement.mapper;

import com.donghochanh.tournamentmanagement.entity.Player;

import java.util.List;

public class TableMapping {
	public static Object[][] playerToTable(List<Player> players) {
		Object[][] table = new Object[players.size()][5];
		for (int i = 0; i < players.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = players.get(i).getId();
			table[i][2] = players.get(i).getName();
			table[i][3] = players.get(i).getAge();
			table[i][4] = players.get(i).getNationality();
		}
		return table;
	}
}
