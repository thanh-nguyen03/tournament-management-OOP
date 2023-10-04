package com.donghochanh.tournamentmanagement;

import com.donghochanh.tournamentmanagement.swing.pages.player.PlayerPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TournamentManagementApplication extends JFrame {
	private final PlayerPanel playerPanel;

	public TournamentManagementApplication(PlayerPanel playerPanel) {
		this.playerPanel = playerPanel;
		initUI();
	}

	private void initUI() {
		setTitle("Tournament Management");
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(this.getSize());
		tabbedPane.addTab("Player", this.playerPanel);
		tabbedPane.addTab("Tournament", new JPanel());
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
