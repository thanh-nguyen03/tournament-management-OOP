package com.donghochanh.tournamentmanagement.swing.pages.team;

import com.donghochanh.tournamentmanagement.dto.TeamDto;
import com.donghochanh.tournamentmanagement.mapper.TableMapping;
import com.donghochanh.tournamentmanagement.service.TeamService;
import com.donghochanh.tournamentmanagement.swing.components.Table;
import com.donghochanh.tournamentmanagement.swing.constants.TableColumnDefs;
import com.donghochanh.tournamentmanagement.swing.constants.TableConstant;
import com.donghochanh.tournamentmanagement.swing.pages.team.components.TeamForm;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class TeamPanel extends JPanel implements ActionListener, ListSelectionListener {
	private final TeamForm teamForm;
	private final TeamService teamService;
	private Table teamTable;
	private JScrollPane tableView;
	private Boolean currentEditState = false;

	public TeamPanel(TeamForm teamForm, TeamService teamService) {
		this.teamForm = teamForm;
		this.teamService = teamService;
		initUI();
	}

	private void initUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.teamTable = new Table(
			TableMapping.teamToTable(teamService.getAllTeams()),
			TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
		);
		this.tableView = new JScrollPane(teamTable);
		tableView.setBorder(BorderFactory.createTitledBorder("Team List"));
		tableView.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH, TableConstant.TABLE_HEIGHT + 50));
		teamForm.getCreateButton().addActionListener(this);
		teamForm.getEditButton().addActionListener(this);
		teamForm.getCancelButton().addActionListener(this);
		teamForm.getDeleteButton().addActionListener(this);
		teamTable.getSelectionModel().addListSelectionListener(this);
		add(tableView);
		add(teamForm);
	}

	private void updateTable() {
		teamTable.updateTableData(
			TableMapping.teamToTable(teamService.getAllTeams()),
			TableColumnDefs.TEAM_TABLE_COLUMN_DEFS
		);
		tableView.setViewportView(teamTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == teamForm.getCreateButton()) {
			try {
				if (!teamForm.validateInput()) {
					throw new Exception("Invalid input data");
				}
				teamService.createTeam(
					new TeamDto(
						teamForm.getInputName(),
						teamForm.getInputStadium(),
						teamForm.getInputCountry()
					));
				updateTable();
				teamForm.resetInput();
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
		} else if (e.getSource() == teamForm.getEditButton() && currentEditState) {
			try {
				int row = teamTable.getSelectedRow();
				if (row < 0) {
					return;
				}
				if (!teamForm.validateInput()) {
					throw new Exception("Invalid input data");
				}
				teamService.updateTeam(
					Long.parseLong(teamTable.getValueAt(row, 1).toString()),
					new TeamDto(
						teamForm.getInputName(),
						teamForm.getInputStadium(),
						teamForm.getInputCountry()
					)
				);
				updateTable();
				currentEditState = false;
				teamForm.resetInput();
				teamForm.setState(currentEditState);
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
		} else if (e.getSource() == teamForm.getCancelButton()) {
			teamForm.resetInput();
			teamForm.setState(currentEditState);
			teamTable.clearSelection();
			currentEditState = false;
		} else if (e.getSource() == teamForm.getDeleteButton()) {
			int row = teamTable.getSelectedRow();
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
					teamService.deleteTeam(
						Long.parseLong(teamTable.getValueAt(row, 1).toString())
					);
					updateTable();
					teamForm.resetInput();
					teamForm.setState(currentEditState);
					teamTable.clearSelection();
					currentEditState = false;
					JOptionPane.showMessageDialog(
						null,
						"Team deleted successfully",
						"Success",
						JOptionPane.INFORMATION_MESSAGE
					);
				} catch (RuntimeException exception) {
					JOptionPane.showMessageDialog(
						null,
						"Cannot delete team in tournament that is started",
						"Error",
						JOptionPane.ERROR_MESSAGE
					);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(
						null,
						"Error while deleting team",
						"Error",
						JOptionPane.ERROR_MESSAGE
					);
				}
			}
		} else {
			JOptionPane.showMessageDialog(
				null,
				"Please select a team to edit",
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
		int row = teamTable.getSelectedRow();
		if (row < 0) {
			return;
		}
		teamForm.setForm(
			teamTable.getValueAt(row, 2).toString(),
			teamTable.getValueAt(row, 3).toString(),
			teamTable.getValueAt(row, 4).toString()
		);
		teamForm.getCreateButton().setEnabled(false);
		teamForm.getEditButton().setEnabled(true);
		teamForm.getDeleteButton().setEnabled(true);
		teamForm.getCancelButton().setVisible(true);
		currentEditState = true;
	}

	public void rerenderPanel() {
		updateTable();
		teamForm.resetInput();
		teamForm.setState(false);
		teamTable.clearSelection();
		currentEditState = false;
	}
}
