package com.donghochanh.tournamentmanagement.mapper;

import com.donghochanh.tournamentmanagement.entity.Team;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.entity.TournamentTeamResult;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableMapping {
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
		Object[][] table = new Object[tournaments.size()][5];
		for (int i = 0; i < tournaments.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = tournaments.get(i).getId();
			table[i][2] = tournaments.get(i).getName();
			table[i][3] = tournaments.get(i).getPrize();
			table[i][4] = String.valueOf(tournaments.get(i).getStatus());
		}
		return table;
	}

	public static Object[][] tournamentToTable(List<Tournament> tournaments, List<Team> champions) {
		Object[][] table = new Object[tournaments.size()][6];
		for (int i = 0; i < tournaments.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = tournaments.get(i).getId();
			table[i][2] = tournaments.get(i).getName();
			table[i][3] = tournaments.get(i).getPrize();
			table[i][4] = String.valueOf(tournaments.get(i).getStatus());
			table[i][5] = champions.get(i) != null ? champions.get(i).getName() : "N/A";
		}
		return table;
	}

	public static Object[][] tournamentSelectionToTable(List<Tournament> tournaments) {
		Object[][] table = new Object[tournaments.size()][3];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for (int i = 0; i < tournaments.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = tournaments.get(i).getId();
			table[i][2] = tournaments.get(i).getName();
		}
		return table;
	}

	public static Object[][] tournamentTeamResultToTable(List<TournamentTeamResult> tournamentTeamResults) {
		Object[][] table = new Object[tournamentTeamResults.size()][10];
		for (int i = 0; i < tournamentTeamResults.size(); i++) {
			table[i][0] = i + 1;
			table[i][1] = tournamentTeamResults.get(i).getTeam().getName();
			table[i][2] = tournamentTeamResults.get(i).getMatchesPlayed();
			table[i][3] = tournamentTeamResults.get(i).getWins();
			table[i][4] = tournamentTeamResults.get(i).getDraws();
			table[i][5] = tournamentTeamResults.get(i).getLosses();
			table[i][6] = tournamentTeamResults.get(i).getGoalsFor();
			table[i][7] = tournamentTeamResults.get(i).getGoalsAgainst();
			table[i][8] = tournamentTeamResults.get(i).getGoalsFor() - tournamentTeamResults.get(i).getGoalsAgainst();
			table[i][9] = tournamentTeamResults.get(i).getPoints();
		}
		return table;
	}
}
