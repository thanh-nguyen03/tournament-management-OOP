package com.donghochanh.tournamentmanagement.swing.pages.matches.components;

import com.donghochanh.tournamentmanagement.constants.MatchStatus;
import com.donghochanh.tournamentmanagement.dto.MatchResultDto;
import com.donghochanh.tournamentmanagement.entity.Match;
import com.donghochanh.tournamentmanagement.service.MatchService;
import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.Label;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatchBox extends JPanel implements ActionListener {
	// Components
	private JLabel team1Label;
	private JLabel team2Label;
	private JLabel scoreLabel;
	private Button updateScoreButton;
	private MatchResultDialog matchResultDialog;

	// Attributes
	private Match match;

	// Services
	private MatchService matchService;

	public MatchBox(Match match, MatchService matchService) {
		this.match = match;
		this.matchService = matchService;

		setBorder(BorderFactory.createTitledBorder("Match " + match.getNumber()));
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		this.team1Label = new Label(match.getTeam1().getName(), 16);
		this.team2Label = new Label(match.getTeam2().getName(), 16);
		this.scoreLabel = new Label(match.getTeam1Score() + " - " + match.getTeam2Score(), 16);
		if (match.getMatchStatus().equals(MatchStatus.FINISHED)) {
			this.updateScoreButton = new Button("Match finished", ColorVariant.WARNING, 130, 26, 14);
		} else {
			this.updateScoreButton = new Button("Update score", ColorVariant.PRIMARY, 130, 26, 14);
		}
		this.matchResultDialog = new MatchResultDialog();

		this.updateScoreButton.addActionListener(this);
		this.matchResultDialog.getUpdateButton().addActionListener(this);

		setPreferredSize(new Dimension(350, 95));
		topPanel.add(this.team1Label);
		topPanel.add(this.scoreLabel);
		topPanel.add(this.team2Label);
		bottomPanel.add(this.updateScoreButton);
		setLayout(new GridLayout(2, 1));
		add(topPanel);
		add(bottomPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.updateScoreButton && !this.match.getMatchStatus().equals(MatchStatus.FINISHED)) {
			this.matchResultDialog.setVisible(true);
			this.matchResultDialog.setTeam1ScoreField(this.match.getTeam1Score());
			this.matchResultDialog.setTeam2ScoreField(this.match.getTeam2Score());
			this.matchResultDialog.setMatchStatusComboBox(this.match.getMatchStatus());
		} else if (e.getSource() == this.matchResultDialog.getUpdateButton()) {
			handleUpdateScore();
		}
	}

	private void handleUpdateScore() {
		try {
			MatchResultDto matchResultDto = new MatchResultDto(
				this.matchResultDialog.getTeam1Score(),
				this.matchResultDialog.getTeam2Score(),
				this.matchResultDialog.getMatchStatus()
			);
			Match updatedMatch = this.matchService.updateMatchResult(
				matchResultDto,
				this.match.getId(),
				this.match.getTournament().getId()
			);

			this.scoreLabel.setText(
				updatedMatch.getTeam1Score() + " - " + updatedMatch.getTeam2Score()
			);

			this.matchResultDialog.setTeam1ScoreField(updatedMatch.getTeam1Score());
			this.matchResultDialog.setTeam2ScoreField(updatedMatch.getTeam2Score());

			if (updatedMatch.getMatchStatus().equals(MatchStatus.FINISHED)) {
				this.updateScoreButton.setEnabled(false);
			}

			this.matchResultDialog.dispose();
			JOptionPane.showMessageDialog(
				null,
				"Match result updated successfully",
				"Success",
				JOptionPane.INFORMATION_MESSAGE
			);
		} catch (RuntimeException exception) {
			this.matchResultDialog.dispose();
			JOptionPane.showMessageDialog(
				null,
				exception.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		} catch (Exception exception) {
			this.matchResultDialog.dispose();
			JOptionPane.showMessageDialog(
				null,
				"Error while updating match result",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
