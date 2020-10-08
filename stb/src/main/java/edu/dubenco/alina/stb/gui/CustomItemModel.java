package edu.dubenco.alina.stb.gui;

import javax.swing.table.AbstractTableModel;

import edu.dubenco.alina.stb.CustomItem;
import edu.dubenco.alina.stb.Policy;

public class CustomItemModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private Policy policy;
	
	private String[] columns = new String[] {"selected", "reference", "type"};
	
	public CustomItemModel(Policy policy) {
		this.policy = policy;
	}

	@Override
	public int getRowCount() {
		return policy != null ? policy.getItems().size() : 0;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(policy == null) {
			return null;
		}
		
		CustomItem item = policy.getItems().get(rowIndex);
		if(columnIndex == 0) {
			return item.isSelected();
		} else {
			String column = columns[columnIndex];
			if(item.getJson().has(column)){
				return item.getJson().get(column);
			} else {
				return null;
			}
		}
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
		fireTableDataChanged();
	}

}
