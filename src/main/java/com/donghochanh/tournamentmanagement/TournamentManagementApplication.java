package com.donghochanh.tournamentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TournamentManagementApplication extends JFrame {

	public TournamentManagementApplication() {
		initUI();
	}

	private void initUI() {
		setTitle("Tournament Management");
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		var ctx = SpringApplication.run(TournamentManagementApplication.class, args);

		EventQueue.invokeLater(() -> {
			var ex = ctx.getBean(TournamentManagementApplication.class);
			ex.setVisible(true);
		});
	}

}
