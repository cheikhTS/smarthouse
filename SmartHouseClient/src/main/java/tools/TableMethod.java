package tools;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import main.Main;

import org.apache.log4j.Logger;

import driver.EquipmentAction;

public class TableMethod extends AbstractTableModel {

	private static final Logger logger = Logger.getLogger(Main.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Method method = null;
	private ArrayList<Object> args = new ArrayList<Object>();

	public TableMethod () {

	}

	public TableMethod ( Method _method ) {
		method = _method;
	}

	public void refresh() {
		fireTableDataChanged();
	}

	public void refresh(Method _method) {

		method = _method;
		fireTableDataChanged();
		fireTableStructureChanged();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(final int col) {
		if ( method == null ) { return ""; }
		return method.getAnnotation(EquipmentAction.class).paramsName()[col];
	}

	public int getRowCount() {
		if ( method != null && method.getParameterTypes().length > 0 ) { return 1; }
		return 0;
	}

	public Object getValueAt(final int row, final int col) {
		if ( col >= args.size() ) { return ""; }
		return args.get(col);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if ( columnIndex >= args.size() ) { return Object.class; }
		return method.getParameterTypes()[columnIndex];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return true;
	}

	public void removeRow(final int row) {

	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if ( col >= args.size() ) {
			args.add(col, value);
		} else {
			args.set(col, value);
		}
		fireTableCellUpdated(row, col);
	}

	public int getColumnCount() {
		if ( method != null ) { return method.getParameterTypes().length; }
		return 0;
	}

	public ArrayList<Object> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<Object> args) {
		this.args = args;
	}

}
