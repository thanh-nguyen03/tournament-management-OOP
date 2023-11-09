package com.donghochanh.tournamentmanagement.swing.pages.matches.components;

import com.donghochanh.tournamentmanagement.constants.MatchStatus;
import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.TextField;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MatchResultDialog extends JDialog implements ActionListener {
	// Components
	private final JComboBox<String> matchStatusComboBox = new JComboBox<>();
	// Services
	private TextField team1ScoreField;
	private TextField team2ScoreField;
	// Buttons
	@Getter
	private Button updateButton;
	@Getter
	private Button cancelButton;

	public MatchResultDialog() {

		setTitle("Update match result");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initUI();
	}

	private void initUI() {
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		this.team1ScoreField = new TextField("Team 1 score", 300, 30, 16);
		this.team2ScoreField = new TextField("Team 2 score", 300, 30, 16);
		matchStatusComboBox.addItem(String.valueOf(MatchStatus.STARTED));
		matchStatusComboBox.addItem(String.valueOf(MatchStatus.FINISHED));
		this.updateButton = new Button("Update", ColorVariant.PRIMARY);
		this.cancelButton = new Button("Cancel", ColorVariant.WARNING);

		this.cancelButton.addActionListener(this);

		buttonPanel.add(updateButton);
		buttonPanel.add(cancelButton);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonPanel.setPreferredSize(new Dimension(350, 50));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		mainPanel.add(this.team1ScoreField);
		mainPanel.add(this.team2ScoreField);
		mainPanel.add(matchStatusComboBox);
		mainPanel.add(buttonPanel);
		add(mainPanel);
	}

	public Integer getTeam1Score() {
		return Integer.parseInt(this.team1ScoreField.getText());
	}

	public Integer getTeam2Score() {
		return Integer.parseInt(this.team2ScoreField.getText());
	}

	public void setTeam1ScoreField(Integer score) {
		this.team1ScoreField.setText(String.valueOf(score));
	}

	public void setTeam2ScoreField(Integer score) {
		this.team2ScoreField.setText(String.valueOf(score));
	}

	public MatchStatus getMatchStatus() {
		return MatchStatus.valueOf(String.valueOf(matchStatusComboBox.getSelectedItem()));
	}

	public void setMatchStatusComboBox(MatchStatus matchStatus) {
		this.matchStatusComboBox.setSelectedItem(matchStatus);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.cancelButton) {
			// close dialog
			this.dispose();
		}
	}
}
