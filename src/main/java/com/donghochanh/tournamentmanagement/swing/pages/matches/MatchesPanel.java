package com.donghochanh.tournamentmanagement.swing.pages.matches;

import com.donghochanh.tournamentmanagement.constants.TournamentStatus;
import com.donghochanh.tournamentmanagement.entity.Match;
import com.donghochanh.tournamentmanagement.entity.Tournament;
import com.donghochanh.tournamentmanagement.exceptions.NotFoundException;
import com.donghochanh.tournamentmanagement.mapper.TableMapping;
import com.donghochanh.tournamentmanagement.service.MatchService;
import com.donghochanh.tournamentmanagement.service.TournamentService;
import com.donghochanh.tournamentmanagement.service.TournamentTeamResultService;
import com.donghochanh.tournamentmanagement.swing.components.Button;
import com.donghochanh.tournamentmanagement.swing.components.Label;
import com.donghochanh.tournamentmanagement.swing.components.Table;
import com.donghochanh.tournamentmanagement.swing.constants.ColorVariant;
import com.donghochanh.tournamentmanagement.swing.constants.TableColumnDefs;
import com.donghochanh.tournamentmanagement.swing.constants.TableConstant;
import com.donghochanh.tournamentmanagement.swing.pages.matches.components.MatchBox;
import com.donghochanh.tournamentmanagement.swing.pages.matches.components.TournamentSelectionDialog;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatchesPanel extends JPanel implements ActionListener {
	// Services
	private final MatchService matchesService;
	private final TournamentTeamResultService tournamentTeamResultService;
	private final TournamentService tournamentService;

	// Injected components
	private final TournamentSelectionDialog tournamentSelectionDialog;

	// Components
	private Label tournamentNameLabel;
	private Table teamRankingTable;
	private JScrollPane teamRankingTableView;
	private Button selectTournamentButton;
	private Button updateRankingButton;
	private JPanel matchListPanel;
	private JScrollPane matchListScrollView;

	public MatchesPanel(MatchService matchesService, TournamentTeamResultService tournamentTeamResultService, TournamentService tournamentService, TournamentSelectionDialog tournamentSelectionDialog) {
		this.matchesService = matchesService;
		this.tournamentTeamResultService = tournamentTeamResultService;
		this.tournamentService = tournamentService;
		this.tournamentSelectionDialog = tournamentSelectionDialog;

		initUI();
	}

	private void initUI() {
		JPanel tablePanel = new JPanel();

		this.tournamentNameLabel = new Label("No tournament selected", 20);

		this.teamRankingTable = new Table(
			TableMapping.tournamentTeamResultToTable(new ArrayList<>()),
			TableColumnDefs.TEAM_RANKING_TABLE_COLUMN_DEFS,
			TableConstant.RAKING_TABLE_COLUMN_WIDTHS
		);
		this.teamRankingTableView = new JScrollPane(this.teamRankingTable);
		this.teamRankingTableView.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH * 2 / 3, TableConstant.TABLE_HEIGHT + 95));
		this.teamRankingTableView.setBorder(BorderFactory.createTitledBorder("Team Ranking"));

		this.selectTournamentButton = new Button("Select tournament", ColorVariant.PRIMARY, TableConstant.TABLE_WIDTH * 2 / 6 - 10, 50, 20);
		this.updateRankingButton = new Button("Update ranking", ColorVariant.SECONDARY, TableConstant.TABLE_WIDTH * 2 / 6 - 10, 50, 20);

		this.selectTournamentButton.addActionListener(this);
		this.updateRankingButton.addActionListener(this);
		this.tournamentSelectionDialog.getSelectButton().addActionListener(this);

		this.matchListPanel = new JPanel();
		this.matchListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		this.matchListPanel.setBorder(BorderFactory.createTitledBorder("Match List"));
		this.matchListPanel.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH / 3 - 20, Integer.MAX_VALUE));

		this.matchListScrollView = new JScrollPane(this.matchListPanel);
		this.matchListScrollView.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH / 3, TableConstant.TABLE_HEIGHT + 200));

		tablePanel.add(this.tournamentNameLabel);
		tablePanel.add(this.teamRankingTableView);
		tablePanel.add(this.selectTournamentButton);
		tablePanel.add(this.updateRankingButton);
		tablePanel.setPreferredSize(new Dimension(TableConstant.TABLE_WIDTH * 2 / 3, TableConstant.TABLE_HEIGHT + 200));
		tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		add(tablePanel);
		add(this.matchListScrollView);
	}

	private void updateRanking(Long tournamentId) {
		Tournament tournament = this.tournamentService.getTournamentById(tournamentId);

		if (tournament == null) {
			return;
		}

		this.tournamentNameLabel.setText(tournament.getName());

		if (tournament.getStatus().equals(TournamentStatus.NOT_STARTED)) {
			this.tournamentNameLabel.setText(tournament.getName() + ": Tournament not started");
		}

		this.teamRankingTable.updateTableData(
			TableMapping.tournamentTeamResultToTable(this.tournamentTeamResultService.getTournamentRanking(tournamentId)),
			TableColumnDefs.TEAM_RANKING_TABLE_COLUMN_DEFS,
			TableConstant.RAKING_TABLE_COLUMN_WIDTHS
		);
		this.matchListPanel.removeAll();
		List<Match> matches = this.matchesService.getMatchesByTournamentId(tournamentId);
		for (Match match : matches) {
			this.matchListPanel.add(new MatchBox(match, this.matchesService));
		}
		this.matchListPanel.revalidate();
		this.matchListPanel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.selectTournamentButton) {
			this.tournamentSelectionDialog.updateTable();
			this.tournamentSelectionDialog.getTournamentTable().clearSelection();
			this.tournamentSelectionDialog.setVisible(true);
		} else if (e.getSource() == this.tournamentSelectionDialog.getSelectButton()) {
			Long selectedTournamentId = this.tournamentSelectionDialog.getSelectedTournamentId();
			updateRanking(selectedTournamentId);
			this.tournamentSelectionDialog.getTournamentTable().clearSelection();
			this.tournamentSelectionDialog.dispose();
		} else if (e.getSource() == this.updateRankingButton) {
			if (this.tournamentSelectionDialog.getSelectedTournamentId() <= 0) {
				JOptionPane.showMessageDialog(
					this,
					"Please select a tournament",
					"Update ranking",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}

			try {
				updateRanking(this.tournamentSelectionDialog.getSelectedTournamentId());
				JOptionPane.showMessageDialog(
					this,
					"Update ranking successfully",
					"Update ranking",
					JOptionPane.INFORMATION_MESSAGE
				);
			} catch (NotFoundException exception) {
				JOptionPane.showMessageDialog(
					this,
					exception.getMessage(),
					"Update ranking",
					JOptionPane.ERROR_MESSAGE
				);
			} catch (Exception exception) {
				exception.printStackTrace();
				JOptionPane.showMessageDialog(
					this,
					"Update ranking failed",
					"Update ranking",
					JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
}
