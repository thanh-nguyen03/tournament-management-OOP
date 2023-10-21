package com.donghochanh.tournamentmanagement;

import com.donghochanh.tournamentmanagement.swing.pages.team.TeamPanel;
import com.donghochanh.tournamentmanagement.swing.pages.tournaments.TournamentPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TournamentManagementApplication extends JFrame {
	private final TeamPanel teamPanel;
	private final TournamentPanel tournamentPanel;

	public TournamentManagementApplication(TeamPanel teamPanel, TournamentPanel tournamentPanel) {
		this.teamPanel = teamPanel;
		this.tournamentPanel = tournamentPanel;
		initUI();
	}

	private void initUI() {
		setTitle("Tournament Management");
		setSize(1500, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(this.getSize());
		tabbedPane.addTab("Team", this.teamPanel);
		tabbedPane.addTab("Tournament", this.tournamentPanel);
		tabbedPane.addChangeListener(e -> {
			if (tabbedPane.getSelectedIndex() == 0) {
				teamPanel.rerenderPanel();
			} else if (tabbedPane.getSelectedIndex() == 1) {
				tournamentPanel.rerenderPanel();
			}
		});
		add(tabbedPane);
	}

	public static void main(String[] args) {
		var ctx = SpringApplication.run(TournamentManagementApplication.class, args);

		EventQueue.invokeLater(() -> {
			var ex = ctx.getBean(TournamentManagementApplication.class);
			ex.setVisible(true);
		});
	}

}
