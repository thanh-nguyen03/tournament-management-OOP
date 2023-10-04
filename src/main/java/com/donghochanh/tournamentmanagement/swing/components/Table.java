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

	public void updateTableData(Object[][] data, Object[] columnNames) {
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		setModel(model);
	}
}