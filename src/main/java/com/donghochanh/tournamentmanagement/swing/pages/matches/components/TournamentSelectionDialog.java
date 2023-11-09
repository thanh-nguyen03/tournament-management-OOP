package com.donghochanh.tournamentmanagement.swing.pages.matches.components;

import com.donghochanh.tournamentmanagement.mapper.TableMapping;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.Table;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import com.donghochanh.tournamentmanagement.swing.constants.TableColumnDefs;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class TournamentSelectionDialog extends JDialog implements ActionListener, ListSelectionListener {
	// Components
	@Getter
	private final Button selectButton = new Button("Select", ColorVariant.PRIMARY);
	private final Button cancelButton = new Button("Cancel", ColorVariant.WARNING);
	// Services
	private final TournamentService tournamentService;
	@Getter
	private Table tournamentTable;
	private JScrollPane tournamentTableView;
	@Getter
	@Setter
	private Long selectedTournamentId = 0L;

	public TournamentSelectionDialog(TournamentService tournamentService) {
		this.tournamentService = tournamentService;

		setTitle("Select tournament");
		setSize(600, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel mainPanel = new JPanel();

		this.tournamentTable = new Table(
			TableMapping.tournamentSelectionToTable(this.tournamentService.getAllTournaments()),
			TableColumnDefs.TOURNAMENT_SELECTION_TABLE_COLUMN_DEFS
		);
		this.tournamentTableView = new JScrollPane(this.tournamentTable);
		this.tournamentTableView.setPreferredSize(new Dimension(550, 250));

		this.cancelButton.addActionListener(this);
		this.tournamentTable.getSelectionModel().addListSelectionListener(this);

		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(this.tournamentTableView);
		mainPanel.add(this.selectButton);
		mainPanel.add(this.cancelButton);
		add(mainPanel);
	}

	public void updateTable() {
		this.tournamentTable.updateTableData(
			TableMapping.tournamentSelectionToTable(this.tournamentService.getAllTournaments()),
			TableColumnDefs.TOURNAMENT_SELECTION_TABLE_COLUMN_DEFS
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.cancelButton) {
			this.dispose();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == this.tournamentTable.getSelectionModel()) {
			if (this.tournamentTable.getSelectedRow() == -1) {
				return;
			}

			this.selectedTournamentId = Long.parseLong(this.tournamentTable.getValueAt(this.tournamentTable.getSelectedRow(), 1).toString());
		}
	}
}
