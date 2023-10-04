package com.donghochanh.tournamentmanagement.swing.pages.player;

import com.donghochanh.tournamentmanagement.dto.PlayerDto;
import com.donghochanh.tournamentmanagement.mapper.TableMapping;
import com.donghochanh.tournamentmanagement.service.PlayerService;
import com.donghochanh.tournamentmanagement.swing.components.Table;
import com.donghochanh.tournamentmanagement.swing.constants.TableColumnDefs;
import com.donghochanh.tournamentmanagement.swing.pages.player.components.PlayerForm;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class PlayerPanel extends JPanel implements ActionListener, ListSelectionListener {
	private final PlayerService playerService;
	private final PlayerForm playerForm;

	private Table playerTable;

	private JScrollPane tableView;

	private Boolean currentEditState = false;

	public PlayerPanel(PlayerService playerService, PlayerForm playerForm) {
		this.playerService = playerService;
		this.playerForm = playerForm;
		initUI();
	}

	private void initUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.playerTable = new Table(
			TableMapping.playerToTable(playerService.findAllPlayers()),
			TableColumnDefs.PLAYER_TABLE_COLUMN_DEFS
		);
		this.tableView = new JScrollPane(playerTable);
		tableView.setBorder(BorderFactory.createTitledBorder("Player List"));
		tableView.setPreferredSize(new Dimension(1150, 500));
		playerForm.getCreateButton().addActionListener(this);
		playerForm.getEditButton().addActionListener(this);
		playerForm.getCancelButton().addActionListener(this);
		playerForm.getDeleteButton().addActionListener(this);
		playerTable.getSelectionModel().addListSelectionListener(this);
		add(tableView);
		add(this.playerForm);
	}

	private void updateTable() {
		playerTable.updateTableData(
			TableMapping.playerToTable(playerService.findAllPlayers()),
			TableColumnDefs.PLAYER_TABLE_COLUMN_DEFS
		);
		tableView.setViewportView(playerTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playerForm.getCreateButton()) {
			try {
				playerService.createPlayer(
					new PlayerDto(
						playerForm.getInputName(),
						Integer.parseInt(playerForm.getInputAge()),
						playerForm.getInputNationality()
					));
				updateTable();
				playerForm.resetInput();
				JOptionPane.showMessageDialog(
					null,
					"Player created successfully",
					"Success",
					JOptionPane.INFORMATION_MESSAGE
				);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(
					null,
					"Incorrect input data. Please try again",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		} else if (e.getSource() == playerForm.getEditButton() && currentEditState) {
			try {
				int row = playerTable.getSelectedRow();
				if (row < 0) {
					return;
				}
				playerService.updatePlayer(
					Long.parseLong(playerTable.getValueAt(row, 1).toString()),
					new PlayerDto(
						playerForm.getInputName(),
						Integer.parseInt(playerForm.getInputAge()),
						playerForm.getInputNationality()
					)
				);
				updateTable();
				playerForm.resetInput();
				playerForm.getCreateButton().setEnabled(true);
				currentEditState = false;
				JOptionPane.showMessageDialog(
					null,
					"Player updated successfully",
					"Success",
					JOptionPane.INFORMATION_MESSAGE
				);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(
					null,
					"Incorrect input data. Please try again",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		} else if (e.getSource() == playerForm.getCancelButton()) {
			playerForm.resetInput();
			playerForm.getCreateButton().setEnabled(true);
			playerForm.getCancelButton().setVisible(false);
			playerTable.clearSelection();
			currentEditState = false;
		} else if (e.getSource() == playerForm.getDeleteButton()) {
			int row = playerTable.getSelectedRow();
			if (row < 0) {
				return;
			}
			int dialogResult = JOptionPane.showConfirmDialog(
				null,
				"Are you sure you want to delete this player?",
				"Warning",
				JOptionPane.YES_NO_OPTION
			);
			if (dialogResult == JOptionPane.YES_OPTION) {
				playerService.deletePlayer(
					Long.parseLong(playerTable.getValueAt(row, 1).toString())
				);
				updateTable();
				playerForm.resetInput();
				playerForm.getCreateButton().setEnabled(true);
				playerForm.getCancelButton().setVisible(false);
				playerTable.clearSelection();
				currentEditState = false;
				JOptionPane.showMessageDialog(
					null,
					"Player deleted successfully",
					"Success",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
		} else {
			JOptionPane.showMessageDialog(
				null,
				"Please select a player to edit",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		int row = playerTable.getSelectedRow();
		if (row < 0) {
			return;
		}
		playerForm.setForm(
			playerTable.getValueAt(row, 2).toString(),
			playerTable.getValueAt(row, 3).toString(),
			playerTable.getValueAt(row, 4).toString()
		);
		playerForm.getCreateButton().setEnabled(false);
		playerForm.getEditButton().setEnabled(true);
		playerForm.getDeleteButton().setEnabled(true);
		playerForm.getCancelButton().setVisible(true);
		currentEditState = true;
	}
}
