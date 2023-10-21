package com.donghochanh.tournamentmanagement.mapper;

import com.donghochanh.tournamentmanagement.entity.Player;
import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.entity.Tournament;

import java.time.format.DateTimeFormatter;
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

	public static Object[][] teamToTable(List<Team> teams) {
		Object[][] table = new Object[teams.size()][5];
		for (int i = 0; i < teams.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = teams.get(i).getId();
			table[i][2] = teams.get(i).getName();
			table[i][3] = teams.get(i).getStadium();
			table[i][4] = teams.get(i).getCountry();
		}

		return table;
	}

	public static Object[][] tournamentToTable(List<Tournament> tournaments) {
		Object[][] table = new Object[tournaments.size()][6];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (int i = 0; i < tournaments.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = tournaments.get(i).getId();
			table[i][2] = tournaments.get(i).getName();
			table[i][3] = tournaments.get(i).getPrize();
			table[i][4] = formatter.format(tournaments.get(i).getStartDate());
			table[i][5] = formatter.format(tournaments.get(i).getEndDate());
		}
		return table;
	}
}
