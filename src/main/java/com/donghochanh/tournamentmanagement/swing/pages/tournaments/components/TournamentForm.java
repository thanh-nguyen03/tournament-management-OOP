package com.donghochanh.tournamentmanagement.swing.pages.tournaments.components;

import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.DatePicker;
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
	private DatePicker startDate;
	private DatePicker endDate;

	@Getter
	private Button createButton;
	@Getter
	private Button editButton;
	@Getter
	private Button deleteButton;
	@Getter
	private Button startButton;
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
		this.name = new TextField("Name");
		this.prize = new TextField("Prize");
		this.startDate = new DatePicker("Start Date (dd/MM/yyyy)", 333, 30, 14);
		this.endDate = new DatePicker("End Date (dd/MM/yyyy)", 333, 30, 14);
		this.createButton = new Button("Create Tournament", ColorVariant.PRIMARY);
		this.editButton = new Button("Save", ColorVariant.PRIMARY);
		this.deleteButton = new Button("Delete Tournament", ColorVariant.ERROR);
		this.startButton = new Button("Start Tournament", ColorVariant.SECONDARY);
		this.cancelButton = new Button("Cancel", ColorVariant.WARNING);
		this.addTeamButton = new Button("Add Team", ColorVariant.PRIMARY);
		this.removeTeamButton = new Button("Remove Team", ColorVariant.ERROR);
		setPreferredSize(new Dimension(1150, 120));
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		startButton.setEnabled(false);
		cancelButton.setVisible(false);
		addTeamButton.setVisible(false);
		removeTeamButton.setVisible(false);
		add(name);
		add(prize);
		add(startDate);
		add(endDate);
		add(createButton);
		add(editButton);
		add(deleteButton);
		add(startButton);
		add(addTeamButton);
		add(removeTeamButton);
		add(cancelButton);
	}

	public String getInputName() {
		return name.getText();
	}

	public String getInputPrize() {
		return prize.getText();
	}

	public String getInputStartDate() {
		return startDate.getText();
	}

	public String getInputEndDate() {
		return endDate.getText();
	}

	public void setForm(String name, String prize, String startDate, String endDate) {
		this.name.setText(name);
		this.prize.setText(prize);
		this.startDate.setText(startDate);
		this.endDate.setText(endDate);
	}

	public void resetInput() {
		name.setText("");
		prize.setText("");
		startDate.setText("");
		endDate.setText("");
	}


	public void setEditState(boolean editState) {
		createButton.setEnabled(!editState);
		editButton.setEnabled(editState);
		deleteButton.setEnabled(editState);
		startButton.setEnabled(editState);
		cancelButton.setVisible(editState);
	}

	public void setEditState(boolean editState, boolean modifyTeamState) {
		createButton.setEnabled(!editState);
		editButton.setEnabled(editState && !modifyTeamState);
		deleteButton.setEnabled(editState && !modifyTeamState);
		startButton.setEnabled(editState && !modifyTeamState);
		cancelButton.setVisible(editState);
		addTeamButton.setVisible(editState && modifyTeamState);
		removeTeamButton.setVisible(editState && modifyTeamState);
	}

	public Boolean validateInput() {
		return !name.getText().isEmpty() && !prize.getText().isEmpty() && !startDate.getText().isEmpty() && !endDate.getText().isEmpty();
	}
}
