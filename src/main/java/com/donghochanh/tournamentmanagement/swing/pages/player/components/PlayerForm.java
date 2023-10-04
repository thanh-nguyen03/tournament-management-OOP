package com.donghochanh.tournamentmanagement.swing.pages.player.components;

import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.TextField;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class PlayerForm extends JPanel {
	private final TextField name;
	private final TextField age;
	private final TextField nationality;
	private final Button createButton;
	private final Button editButton;
	private final Button deleteButton;
	private final Button cancelButton;

	public PlayerForm() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Player Form"));
		setPreferredSize(new Dimension(1150, 80));
		this.name = new TextField("Name");
		this.age = new TextField("Age");
		this.nationality = new TextField("Nationality");
		this.createButton = new Button("Add Player", ColorVariant.PRIMARY);
		this.editButton = new Button("Edit Player", ColorVariant.PRIMARY);
		this.deleteButton = new Button("Delete Player", ColorVariant.ERROR);
		this.cancelButton = new Button("Cancel", ColorVariant.WARNING);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		cancelButton.setVisible(false);
		add(name);
		add(age);
		add(nationality);
		add(createButton);
		add(editButton);
		add(deleteButton);
		add(cancelButton);
	}

	public String getInputName() {
		return name.getText();
	}

	public String getInputAge() {
		return age.getText();
	}

	public String getInputNationality() {
		return nationality.getText();
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

	public void setForm(String name, String age, String nationality) {
		this.name.setText(name);
		this.age.setText(age);
		this.nationality.setText(nationality);
	}

	public void resetInput() {
		name.setText("");
		age.setText("");
		nationality.setText("");
	}
}
