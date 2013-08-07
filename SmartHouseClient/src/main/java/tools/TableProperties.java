package tools;

import java.util.Enumeration;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

import main.Main;

import org.apache.log4j.Logger;

public class TableProperties extends AbstractTableModel {

	private static final Logger logger = Logger.getLogger(Main.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties properties;

	public TableProperties () {

	}

	public TableProperties ( Properties _properties ) {
		properties = _properties;
	}

	public void refresh() {
		fireTableDataChanged();
	}

	public void refresh(Properties _properties) {
		properties = _properties;
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int col) {
		switch ( col ) {
			case 0:
				return "Nom";
			case 1:
				return "Valeur";
		}
		return "";
	}

	public int getRowCount() {
		if ( properties == null ) { return 0; }
		return properties.size();
	}

	public Object getValueAt(final int row, final int col) {
		Enumeration<Object> enumerator = properties.keys();
		int i = 0;
		while ( enumerator.hasMoreElements() ) {
			Object key = enumerator.nextElement();
			if ( i == row ) {
				if ( col == 0 ) {
					return key;
				} else {
					return properties.get(key);
				}
			}
			i++;
		}
		return "";
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		if ( col == 1 ) { return true; }
		return false;
	}

	public void removeRow(final int row) {

	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Enumeration<Object> enumerator = properties.keys();
		int i = 0;
		while ( enumerator.hasMoreElements() ) {
			Object key = enumerator.nextElement();
			if ( i == row ) {
				properties.setProperty(key.toString(), value.toString());
			}
			i++;
		}
		fireTableCellUpdated(row, col);
	}

	public int getColumnCount() {
		return 2;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
