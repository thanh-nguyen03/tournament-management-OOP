package com.donghochanh.tournamentmanagement.swing.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table extends JTable {
	public Table(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		TableColumnModel columnModel = getColumnModel();
		TableColumn column = columnModel.getColumn(0);
		column.setMaxWidth(50);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public Table(Object[][] data, Object[] columnNames, int[] columnWidths) {
		super(data, columnNames);
		TableColumnModel columnModel = getColumnModel();
		for (int i = 0; i < columnWidths.length; i++) {
			if (columnWidths[i] == 0) continue;
			TableColumn column = columnModel.getColumn(i);
			column.setMaxWidth(columnWidths[i]);
			column.setMinWidth(columnWidths[i]);
		}
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void updateTableData(Object[][] data, Object[] columnNames) {
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		setModel(model);
	}

	public void updateTableData(Object[][] data, Object[] columnNames, int[] columnWidths) {
		this.updateTableData(data, columnNames);
		TableColumnModel columnModel = getColumnModel();
		for (int i = 0; i < columnWidths.length; i++) {
			if (columnWidths[i] == 0) continue;
			TableColumn column = columnModel.getColumn(i);
			column.setMaxWidth(columnWidths[i]);
			column.setMinWidth(columnWidths[i]);
		}
	}
}