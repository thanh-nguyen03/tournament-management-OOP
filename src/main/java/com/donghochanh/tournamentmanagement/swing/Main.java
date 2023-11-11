package com.donghochanh.tournamentmanagement.swing;

import com.donghochanh.tournamentmanagement.swing.components.Menu;
import com.donghochanh.tournamentmanagement.swing.pages.home.Home;
import com.donghochanh.tournamentmanagement.swing.pages.matches.MatchesPanel;
import com.donghochanh.tournamentmanagement.swing.pages.team.TeamPanel;
import com.donghochanh.tournamentmanagement.swing.pages.tournaments.TournamentPanel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class Main extends JPanel implements ActionListener {
	private final Menu menu;
	private final JPanel mainPanel;
	private final Home home;
	private final TeamPanel teamPanel;
	private final TournamentPanel tournamentPanel;
	private final MatchesPanel matchesPanel;

	private CardLayout cardLayout;


	public Main(Menu menu, Home home, TeamPanel teamPanel, TournamentPanel tournamentPanel, MatchesPanel matchesPanel) {
		// Inject dependency
		this.menu = menu;
		this.home = home;
		this.teamPanel = teamPanel;
		this.tournamentPanel = tournamentPanel;
		this.matchesPanel = matchesPanel;

		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		add(this.menu);

		cardLayout = new CardLayout();

		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1290, 900));
		mainPanel.setLayout(cardLayout);
		mainPanel.add("Home", this.home);
		mainPanel.add("Teams", this.teamPanel);
		mainPanel.add("Tournaments", this.tournamentPanel);
		mainPanel.add("Matches", this.matchesPanel);
		add(mainPanel);
		cardLayout.show(mainPanel, "Home");

		// Add event listener
		this.menu.getBtnTeam().addActionListener(this);
		this.menu.getBtnTournament().addActionListener(this);
		this.menu.getBtnTournamentDetail().addActionListener(this);
		this.menu.getBtnHome().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menu.getBtnTeam()) {
			cardLayout.show(mainPanel, "Teams");
		} else if (e.getSource() == menu.getBtnTournament()) {
			this.tournamentPanel.rerenderPanel();
			cardLayout.show(mainPanel, "Tournaments");
		} else if (e.getSource() == menu.getBtnTournamentDetail()) {
			cardLayout.show(mainPanel, "Matches");
		} else if (e.getSource() == menu.getBtnHome()) {
			cardLayout.show(mainPanel, "Home");
		}
	}
}
