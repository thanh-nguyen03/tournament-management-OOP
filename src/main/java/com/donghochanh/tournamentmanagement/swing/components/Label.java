package com.donghochanh.tournamentmanagement.swing.components;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
	public Label(String text) {
		super(text);
	}

	public Label(String text, int fontSize) {
		super(text);
		setFont(new Font("Arial", Font.PLAIN, fontSize));
	}
}
