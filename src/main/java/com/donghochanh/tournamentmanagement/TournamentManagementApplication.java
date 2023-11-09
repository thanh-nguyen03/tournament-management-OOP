package com.donghochanh.tournamentmanagement;

import com.donghochanh.tournamentmanagement.swing.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class TournamentManagementApplication extends JFrame {
	private final Main main;

	public TournamentManagementApplication(Main main) {
		this.main = main;
		initUI();
	}

	public static void main(String[] args) {
		var ctx = SpringApplication.run(TournamentManagementApplication.class, args);

		EventQueue.invokeLater(() -> {
			var ex = ctx.getBean(TournamentManagementApplication.class);
			ex.setVisible(true);
		});
	}

	private void initUI() {
		setTitle("Tournament Management");
		setSize(1500, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(this.main);
	}

}
