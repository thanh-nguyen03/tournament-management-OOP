package com.donghochanh.tournamentmanagement.swing.pages.tournaments;

import com.donghochanh.tournamentmanagement.dto.TournamentDto;
import com.donghochanh.tournamentmanagement.exceptions.TeamAlreadyAddedException;
import com.donghochanh.tournamentmanagement.mapper.TableMapping;
import com.donghochanh.tournamentmanagement.service.TeamService;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import com.donghochanh.tournamentmanagement.swing.components.Table;
import com.donghochanh.tournamentmanagement.swing.constants.TableColumnDefs;
import com.donghochanh.tournamentmanagement.swing.constants.TableConstant;
import com.donghochanh.tournamentmanagement.swing.pages.tournaments.components.TournamentForm;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@Component
public class TournamentPanel extends JPanel implements ActionListener, ListSelectionListener {
	private final TournamentForm tournamentForm;
	private final TournamentService tournamentService;
	private final TeamService teamService;

	private Table tournamentTable;
	private JScrollPane tournamentTableView;
	private Table teamTable;
	private JScrollPane teamTableView;
	private Table allTeamTable;
	private JScrollPane allTeamTableView;

	private Boolean currentEditState = false;

	public TournamentPanel(TournamentForm tournamentForm, TournamentService tournamentService, TeamService teamService) {
		this.tournamentForm = tournamentForm;
		this.tournamentService = tournamentService;
		this.teamService = teamService;
		initUI();
	}

	private void initUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.tournamentTable = new Table(
			TableMapping.tournamentToTable(tournamentService.getAllTournaments()),
			TableColumnDefs.TOURNAMENT_TABLE_COLUMN_DEFS
		);
		this.allTeamTable = new Table(
			TableMapping.teamToTable(teamService.getAllTeams()),
			TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
		);
		this.teamTable = new Table(
			TableMapping.teamToTable(new ArrayList<>()),
			TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
		);
		this.tournamentTableView = new JScrollPane(tournamentTable);
		this.teamTableView = new JScrollPane(teamTable);
		this.allTeamTableView = new JScrollPane(allTeamTable);

		tournamentTableView.setBorder(BorderFactory.createTitledBorder("Tournament List"));
		teamTableView.setBorder(BorderFactory.createTitledBorder("Tournament Team List"));
		allTeamTableView.setBorder(BorderFactory.createTitledBorder("All Team List"));
		tournamentTableView.setPreferredSize(new Dimension((TableConstant.TABLE_WIDTH - 10) / 2, TableConstant.TABLE_HEIGHT - 250));
		teamTableView.setPreferredSize(new Dimension((TableConstant.TABLE_WIDTH - 10) / 2, TableConstant.TABLE_HEIGHT - 250));
		allTeamTableView.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH, TableConstant.TABLE_HEIGHT / 2));

		tournamentTable.getSelectionModel().addListSelectionListener(this);
		allTeamTable.getSelectionModel().addListSelectionListener(this);
		teamTable.getSelectionModel().addListSelectionListener(this);
		tournamentForm.getCreateButton().addActionListener(this);
		tournamentForm.getEditButton().addActionListener(this);
		tournamentForm.getCancelButton().addActionListener(this);
		tournamentForm.getDeleteButton().addActionListener(this);
		tournamentForm.getAddTeamButton().addActionListener(this);
		tournamentForm.getRemoveTeamButton().addActionListener(this);

		add(tournamentTableView);
		add(teamTableView);
		add(allTeamTableView);
		add(tournamentForm);
	}

	private void updateTournamentTable() {
		tournamentTable.updateTableData(
			TableMapping.tournamentToTable(tournamentService.getAllTournaments()),
			TableColumnDefs.TOURNAMENT_TABLE_COLUMN_DEFS
		);
		tournamentTableView.setViewportView(tournamentTable);
	}

	private void updateTeamTable(Long id) {
		if (id >= 0) {
			teamTable.updateTableData(
				TableMapping.teamToTable(teamService.getTeamsByTournamentId(id)),
				TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
			);
		} else {
			teamTable.updateTableData(
				TableMapping.teamToTable(new ArrayList<>()),
				TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
			);
		}
		teamTableView.setViewportView(teamTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tournamentForm.getCreateButton()) {
			handleCreateTournament();
		} else if (e.getSource() == tournamentForm.getEditButton() && currentEditState) {
			handleUpdateTournament();
		} else if (e.getSource() == tournamentForm.getCancelButton()) {
			tournamentForm.resetInput();
			tournamentForm.setEditState(false, false);
			currentEditState = false;
			tournamentTable.clearSelection();
			allTeamTable.clearSelection();
			teamTable.updateTableData(
				TableMapping.teamToTable(new ArrayList<>()),
				TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
			);
		} else if (e.getSource() == tournamentForm.getDeleteButton()) {
			handleDeleteTournament();
		} else if (e.getSource() == tournamentForm.getAddTeamButton()) {
			handleAddTeamToTournament();
		} else if (e.getSource() == tournamentForm.getRemoveTeamButton()) {
			handleRemoveTeamFromTournament();
		} else {
			JOptionPane.showMessageDialog(
				null,
				"Please select a team to edit",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}

		// Reset after action
		allTeamTable.clearSelection();
		teamTable.clearSelection();
		tournamentTable.clearSelection();
		tournamentForm.setEditState(false, false);
		currentEditState = false;
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == tournamentTable.getSelectionModel() && !e.getValueIsAdjusting()) {
			int row = tournamentTable.getSelectedRow();
			if (row >= 0) {
				tournamentForm.setForm(
					tournamentTable.getValueAt(row, 2).toString(),
					tournamentTable.getValueAt(row, 3).toString(),
					tournamentTable.getValueAt(row, 4).toString(),
					tournamentTable.getValueAt(row, 5).toString()
				);
				this.teamTable.updateTableData(
					TableMapping.teamToTable(teamService.getTeamsByTournamentId(
						Long.parseLong(tournamentTable.getValueAt(row, 1).toString())
					)),
					TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
				);
				this.teamTableView.setViewportView(teamTable);
				tournamentForm.setEditState(true);
				currentEditState = true;
			}
		}

		if (!tournamentTable.getSelectionModel().isSelectionEmpty() && !allTeamTable.getSelectionModel().isSelectionEmpty()) {
			// Both tables are selected.
			tournamentForm.setEditState(true, true);
			tournamentForm.getRemoveTeamButton().setEnabled(false);
			currentEditState = true;
			return;
		}

		if (!tournamentTable.getSelectionModel().isSelectionEmpty() && !teamTable.getSelectionModel().isSelectionEmpty()) {
			// Both tables are selected.
			tournamentForm.setEditState(true, true);
			tournamentForm.getAddTeamButton().setEnabled(false);
			currentEditState = true;
		}
	}

	public void rerenderPanel() {
		updateTournamentTable();
		allTeamTable.updateTableData(
			TableMapping.teamToTable(teamService.getAllTeams()),
			TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
		);
		allTeamTableView.setViewportView(allTeamTable);
		tournamentForm.resetInput();
		tournamentTable.clearSelection();
		tournamentForm.setEditState(false, false);
		currentEditState = false;
		allTeamTable.clearSelection();
	}

	private void handleCreateTournament() {
		try {
			if (!tournamentForm.validateInput()) {
				throw new Exception("Invalid input data");
			}
			tournamentService.createTournament(
				new TournamentDto(
					tournamentForm.getInputName(),
					tournamentForm.getInputPrize(),
					tournamentForm.getInputStartDate(),
					tournamentForm.getInputEndDate()
				));
			updateTournamentTable();
			tournamentForm.resetInput();
			JOptionPane.showMessageDialog(
				null,
				"Team created successfully",
				"Success",
				JOptionPane.INFORMATION_MESSAGE
			);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
				null,
				"Invalid input data. Please try again",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void handleUpdateTournament() {
		try {
			int row = tournamentTable.getSelectedRow();
			if (row < 0) {
				return;
			}
			if (!tournamentForm.validateInput()) {
				throw new Exception("Invalid input data");
			}
			tournamentService.updateTournament(
				Long.parseLong(tournamentTable.getValueAt(row, 1).toString()),
				new TournamentDto(
					tournamentForm.getInputName(),
					tournamentForm.getInputPrize(),
					tournamentForm.getInputStartDate(),
					tournamentForm.getInputEndDate()
				)
			);
			updateTournamentTable();
			tournamentForm.setEditState(false);
			currentEditState = false;
			tournamentForm.resetInput();
			JOptionPane.showMessageDialog(
				null,
				"Team updated successfully",
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
	}

	private void handleDeleteTournament() {
		int row = tournamentTable.getSelectedRow();
		if (row < 0) {
			return;
		}
		int dialogResult = JOptionPane.showConfirmDialog(
			null,
			"Are you sure you want to delete this team?",
			"Warning",
			JOptionPane.YES_NO_OPTION
		);
		if (dialogResult == JOptionPane.YES_OPTION) {
			try {
				tournamentService.deleteTournament(
					Long.parseLong(tournamentTable.getValueAt(row, 1).toString())
				);
				updateTournamentTable();
				tournamentForm.resetInput();
				tournamentTable.clearSelection();
				tournamentForm.setEditState(false);
				currentEditState = false;
				JOptionPane.showMessageDialog(
					null,
					"Team deleted successfully",
					"Success",
					JOptionPane.INFORMATION_MESSAGE
				);
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(
					null,
					"Error deleting team",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}

	private void handleAddTeamToTournament() {
		int tournament = tournamentTable.getSelectedRow();
		if (tournament < 0) {
			return;
		}

		int team = allTeamTable.getSelectedRow();
		if (team < 0) {
			return;
		}

		try {
			tournamentService.addTeam(
				Long.parseLong(tournamentTable.getValueAt(tournament, 1).toString()),
				Long.parseLong(allTeamTable.getValueAt(team, 1).toString())
			);
			updateTournamentTable();
			updateTeamTable(
				Long.parseLong(tournamentTable.getValueAt(tournament, 1).toString())
			);
			tournamentForm.resetInput();
			allTeamTable.clearSelection();
			JOptionPane.showMessageDialog(
				null,
				"Team added successfully",
				"Success",
				JOptionPane.INFORMATION_MESSAGE
			);
		} catch (TeamAlreadyAddedException exception) {
			JOptionPane.showMessageDialog(
				null,
				exception.getMessage(),
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
				null,
				"Error adding team",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private void handleRemoveTeamFromTournament() {
		int tournament = tournamentTable.getSelectedRow();
		if (tournament < 0) {
			return;
		}

		int team = teamTable.getSelectedRow();
		if (team < 0) {
			return;
		}

		try {
			tournamentService.removeTeam(
				Long.parseLong(tournamentTable.getValueAt(tournament, 1).toString()),
				Long.parseLong(teamTable.getValueAt(team, 1).toString())
			);
			updateTournamentTable();
			updateTeamTable(
				Long.parseLong(tournamentTable.getValueAt(tournament, 1).toString())
			);
			tournamentForm.resetInput();
			allTeamTable.clearSelection();
			JOptionPane.showMessageDialog(
				null,
				"Team removed successfully",
				"Success",
				JOptionPane.INFORMATION_MESSAGE
			);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
				null,
				"Error removing team",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}
}
