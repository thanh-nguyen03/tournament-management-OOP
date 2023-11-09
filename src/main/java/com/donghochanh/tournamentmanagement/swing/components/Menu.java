package com.donghochanh.tournamentmanagement.swing.components;

import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class Menu extends JPanel {
	private JButton btnHome;
	private JButton btnTeam;
	private JButton btnTournament;
	private JButton btnTournamentDetail;

	public Menu() {
		setPreferredSize(new Dimension(200, 900));
		setBackground(Color.decode(ColorVariant.WARNING.getColor()));
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		btnHome = new JButton("Home");
		btnTeam = new JButton("Team");
		btnTournament = new JButton("Tournament");
		btnTournamentDetail = new JButton("Tournament Detail");
		btnHome.setPreferredSize(new Dimension(180, 50));
		btnTeam.setPreferredSize(new Dimension(180, 50));
		btnTournament.setPreferredSize(new Dimension(180, 50));
		btnTournamentDetail.setPreferredSize(new Dimension(180, 50));
		add(btnHome);
		add(btnTeam);
		add(btnTournament);
		add(btnTournamentDetail);
	}

	public JButton getBtnHome() {
		return btnHome;
	}

	public JButton getBtnTeam() {
		return btnTeam;
	}

	public JButton getBtnTournament() {
		return btnTournament;
	}

	public JButton getBtnTournamentDetail() {
		return btnTournamentDetail;
	}
}
