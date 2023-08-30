package com.donghochanh.tournamentmanagement.swing.components;

import javax.swing.*;
import java.awt.*;

public class TextField extends JPanel {
	private int width = 200;
	private int height = 30;
	private int fontSize = 14;

	JTextField textField = new JTextField();
	JLabel label = new JLabel();

	public TextField(String text) {
		setLayout(new BorderLayout());
		label.setText(text + ": ");
		add(label, BorderLayout.WEST);
		add(textField, BorderLayout.CENTER);
		setPreferredSize(new Dimension(width, height));
		textField.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
	}

	public TextField(String text, int width, int height, int fontSize) {
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
