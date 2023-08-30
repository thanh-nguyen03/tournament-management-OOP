package com.donghochanh.tournamentmanagement.swing.components;

import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
	private ColorVariant colorVariant = ColorVariant.PRIMARY;
	private int fontSize = 14;

	public Button(String text) {
		super(text);
		defaultSettings();
	}

	public Button(String text, ColorVariant colorVariant) {
		super(text);
		this.colorVariant = colorVariant;
		defaultSettings();
	}

	public Button(String text, ColorVariant colorVariant, int fontSize) {
		super(text);
		this.colorVariant = colorVariant;
		this.fontSize = fontSize;
		defaultSettings();
	}

	public Button(String text, ColorVariant colorVariant, int width, int height, int fontSize) {
		this(text, colorVariant, fontSize);
		setPreferredSize(new Dimension(width, height));
	}

	private void defaultSettings() {
		setFont(new Font("Arial", Font.PLAIN, this.fontSize));
		setBackground(Color.decode(this.colorVariant.getColor()));
		putClientProperty("JButton.buttonType", "segmentedRoundRect-only");
		setOpaque(true);
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
