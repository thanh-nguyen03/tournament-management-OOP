package com.donghochanh.tournamentmanagement.swing.pages.team.components;

import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.TextField;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class TeamForm extends JPanel {
	private final TextField name;
	private final TextField stadium;
	private final TextField country;

	private final Button createButton;
	private final Button editButton;
	private final Button deleteButton;
	private final Button cancelButton;

	public TeamForm() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Player Form"));
		setPreferredSize(new Dimension(1200, 80));
		this.name = new TextField("Name");
		this.stadium = new TextField("Stadium");
		this.country = new TextField("Country");
		this.createButton = new Button("Create Team", ColorVariant.PRIMARY);
		this.editButton = new Button("Save", ColorVariant.PRIMARY);
		this.deleteButton = new Button("Delete Team", ColorVariant.ERROR);
		this.cancelButton = new Button("Cancel", ColorVariant.WARNING);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		cancelButton.setVisible(false);
		add(name);
		add(stadium);
		add(country);
		add(createButton);
		add(editButton);
		add(deleteButton);
		add(cancelButton);
	}

	public String getInputName() {
		return name.getText();
	}

	public String getInputStadium() {
		return stadium.getText();
	}

	public String getInputCountry() {
		return country.getText();
	}

	public JButton getCreateButton() {
		return createButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void resetInput() {
		name.setText("");
		stadium.setText("");
		country.setText("");
	}

	public void setForm(String name, String stadium, String country) {
		this.name.setText(name);
		this.stadium.setText(stadium);
		this.country.setText(country);
	}

	public void setState(boolean state) {
		createButton.setEnabled(state);
		editButton.setEnabled(!state);
		deleteButton.setEnabled(!state);
		cancelButton.setVisible(!state);
	}

	public Boolean validateInput() {
		return !name.getText().isEmpty() && !stadium.getText().isEmpty() && !country.getText().isEmpty();
	}
}
