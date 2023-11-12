package com.donghochanh.tournamentmanagement.swing.pages.tournaments.components;

import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.TextField;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class TournamentForm extends JPanel {
	private TextField name;
	private TextField prize;

	@Getter
	private Button createButton;
	@Getter
	private Button editButton;
	@Getter
	private Button deleteButton;
	@Getter
	private Button startButton;
	@Getter
	private Button endButton;
	@Getter
	private Button cancelButton;
	@Getter
	private Button addTeamButton;
	@Getter
	private Button removeTeamButton;

	public TournamentForm() {
		initUI();
	}

	private void initUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Tournament Form"));
		this.name = new TextField("Name", 400, 30, 14);
		this.prize = new TextField("Prize", 200, 30, 14);
		this.createButton = new Button("Create Tournament", ColorVariant.PRIMARY);
		this.editButton = new Button("Save", ColorVariant.PRIMARY);
		this.deleteButton = new Button("Delete Tournament", ColorVariant.ERROR);
		this.startButton = new Button("Start Tournament", ColorVariant.SECONDARY);
		this.endButton = new Button("End Tournament", ColorVariant.SUCCESS);
		this.cancelButton = new Button("Cancel", ColorVariant.WARNING);
		this.addTeamButton = new Button("Add Team", ColorVariant.PRIMARY);
		this.removeTeamButton = new Button("Remove Team", ColorVariant.ERROR);
		setPreferredSize(new Dimension(1150, 140));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.setPreferredSize(new Dimension(1100, 60));

		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		startButton.setEnabled(false);
		endButton.setEnabled(false);
		cancelButton.setVisible(false);
		addTeamButton.setVisible(false);
		removeTeamButton.setVisible(false);
		add(name);
		add(prize);
		buttonPanel.add(createButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(startButton);
		buttonPanel.add(endButton);
		buttonPanel.add(addTeamButton);
		buttonPanel.add(removeTeamButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel);
	}

	public String getInputName() {
		return name.getText();
	}

	public String getInputPrize() {
		return prize.getText();
	}

	public void setForm(String name, String prize) {
		this.name.setText(name);
		this.prize.setText(prize);
	}

	public void resetInput() {
		name.setText("");
		prize.setText("");
	}


	public void setEditState(boolean editState) {
		createButton.setEnabled(!editState);
		editButton.setEnabled(editState);
		deleteButton.setEnabled(editState);
		startButton.setEnabled(editState);
		endButton.setEnabled(editState);
		cancelButton.setVisible(editState);
	}

	public void setEditState(boolean editState, boolean modifyTeamState) {
		createButton.setEnabled(!editState);
		editButton.setEnabled(editState && !modifyTeamState);
		deleteButton.setEnabled(editState && !modifyTeamState);
		startButton.setEnabled(editState && !modifyTeamState);
		endButton.setEnabled(editState && !modifyTeamState);
		cancelButton.setVisible(editState);
		addTeamButton.setVisible(editState && modifyTeamState);
		removeTeamButton.setVisible(editState && modifyTeamState);
	}

	public Boolean validateInput() {
		return !name.getText().isEmpty() && !prize.getText().isEmpty();
	}
}
