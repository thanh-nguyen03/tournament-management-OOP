package com.donghochanh.tournamentmanagement.swing.components;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DatePicker extends JPanel {
	private int width = 200;
	private int height = 30;
	private int fontSize = 14;
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	JFormattedTextField textField = new JFormattedTextField(dateFormat);
	JLabel label = new JLabel();

	public DatePicker(String text) {
		setLayout(new BorderLayout());
		label.setText(text + ": ");
		add(label, BorderLayout.WEST);
		add(textField, BorderLayout.CENTER);
		setPreferredSize(new Dimension(width, height));
		textField.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
	}

	public DatePicker(String text, int width, int height, int fontSize) {
		this(text);
		this.width = width;
		this.height = height;
		this.fontSize = fontSize;
		setPreferredSize(new Dimension(this.width, this.height));
		textField.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
	}

	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
	}
}
