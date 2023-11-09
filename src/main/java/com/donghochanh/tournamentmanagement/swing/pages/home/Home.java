package com.donghochanh.tournamentmanagement.swing.pages.home;

import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class Home extends JPanel {
	private final Button name1 = new Button("Nguyễn Đức Thành", ColorVariant.SUCCESS, 400, 60, 25);
	private final Button name2 = new Button("Đỗ Huy Hoàng", ColorVariant.INFO, 400, 60, 25);
	private final Button name3 = new Button("Đỗ Nam Anh", ColorVariant.SECONDARY, 400, 60, 25);
	private final Button name4 = new Button("Lê Khánh Linh", ColorVariant.WARNING, 400, 60, 25);

	public Home() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
		Button label = new Button("Tournament Management", ColorVariant.ERROR, 800, 150, 60);
		add(label);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1250, 610));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 150));
		panel.setBorder(BorderFactory.createTitledBorder("Nhóm 6"));
		// center names horizontally
		name1.setAlignmentX(CENTER_ALIGNMENT);
		name2.setAlignmentX(CENTER_ALIGNMENT);
		name3.setAlignmentX(CENTER_ALIGNMENT);
		name4.setAlignmentX(CENTER_ALIGNMENT);

		panel.add(name1);
		panel.add(name2);
		panel.add(name3);
		panel.add(name4);
		add(panel);
	}
}
