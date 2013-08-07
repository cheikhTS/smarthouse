package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

import manager.ActionExecutor;
import pojo.domain.Equipment;
import pojo.pattern.Action;
import tools.TableMethod;
import driver.DriverException;
import driver.DriverManager;
import driver.EquipmentAction;

public class StartActionGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8684748257604651115L;
	protected JLabel lblAjouterUneAction = new JLabel("Lancer une action");
	protected JComboBox cb_selectEquipment = new JComboBox();
	protected JLabel lblSelectionnerUnquipment = new JLabel("Equipement selectionné :");
	protected JComboBox cb_selectMethod = new JComboBox();
	protected JLabel lbl_selectMethod = new JLabel("Séléctionner une méthode :");
	protected TableMethod tbl_method = new TableMethod();
	protected JTable table_args;
	protected JButton bt_addAction = new JButton("Lancer l'action");
	protected Equipment equipment = null;

	/**
	 * Create the panel.
	 */
	public StartActionGUI ( Equipment _equipment ) {
		equipment = _equipment;
		initComponents();
		loadEquipments();
		loadMethods(equipment);
		setVisible(true);
	}

	protected void initComponents() {
		table_args = new JTable(tbl_method);
		table_args.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_args.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_args.setFillsViewportHeight(true);
		JScrollPane sp_args = new JScrollPane(table_args);
		lbl_selectMethod.setLabelFor(cb_selectMethod);
		lblSelectionnerUnquipment.setLabelFor(cb_selectEquipment);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.EAST, cb_selectEquipment, 241, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, sp_args, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, bt_addAction, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, cb_selectMethod, 0, SpringLayout.EAST, cb_selectEquipment);
		springLayout.putConstraint(SpringLayout.NORTH, sp_args, 10, SpringLayout.SOUTH, cb_selectMethod);
		springLayout.putConstraint(SpringLayout.SOUTH, sp_args, -10, SpringLayout.NORTH, bt_addAction);
		springLayout.putConstraint(SpringLayout.EAST, bt_addAction, 0, SpringLayout.EAST, sp_args);
		springLayout.putConstraint(SpringLayout.NORTH, cb_selectMethod, 6, SpringLayout.SOUTH, lbl_selectMethod);
		springLayout.putConstraint(SpringLayout.WEST, cb_selectMethod, 0, SpringLayout.WEST, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.NORTH, lbl_selectMethod, 6, SpringLayout.SOUTH, cb_selectEquipment);
		springLayout.putConstraint(SpringLayout.WEST, lbl_selectMethod, 0, SpringLayout.WEST, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.WEST, sp_args, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, cb_selectEquipment, 26, SpringLayout.SOUTH, lblSelectionnerUnquipment);
		springLayout.putConstraint(SpringLayout.NORTH, cb_selectEquipment, 6, SpringLayout.SOUTH, lblSelectionnerUnquipment);
		springLayout.putConstraint(SpringLayout.WEST, cb_selectEquipment, 0, SpringLayout.WEST, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.NORTH, lblSelectionnerUnquipment, 6, SpringLayout.SOUTH, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.WEST, lblSelectionnerUnquipment, 0, SpringLayout.WEST, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.NORTH, lblAjouterUneAction, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblAjouterUneAction, 10, SpringLayout.WEST, this);
		setLayout(springLayout);

		add(lblAjouterUneAction);
		cb_selectEquipment.setEnabled(false);
		cb_selectEquipment.addActionListener(this);

		add(cb_selectEquipment);

		add(lblSelectionnerUnquipment);
		cb_selectMethod.addActionListener(this);

		add(cb_selectMethod);

		add(lbl_selectMethod);

		add(sp_args);
		bt_addAction.addActionListener(this);

		add(bt_addAction);
	}

	private void loadEquipments() {
		cb_selectEquipment.addItem(equipment);
	}

	public Equipment getSelectEquipment() {
		return (Equipment) cb_selectEquipment.getSelectedItem();
	}

	public Method getSelectMethod() {
		int index = cb_selectMethod.getSelectedIndex();
		List<Method> methods = DriverManager.getActionsAvailable(((Equipment) cb_selectEquipment.getSelectedItem()).getDriver());
		return methods.get(index);
	}

	public void loadMethods(Equipment equipment) {
		cb_selectMethod.removeAllItems();
		try {
			equipment.loadDriver();
		} catch (DriverException e) {
			e.printStackTrace();
		}

		List<Method> methods = DriverManager.getActionsAvailable(equipment.getDriver());
		for ( Method meth : methods ) {
			cb_selectMethod.addItem(meth.getAnnotation(EquipmentAction.class).name());
		}
	}

	public void loadArgs(Method meth) {
		tbl_method.refresh(meth);
	}

	public Action createAction() {
		Object[] args = new Object[getSelectMethod().getParameterTypes().length];
		for ( int i = 0; i < args.length; i++ ) {
			args[i] = tbl_method.getArgs().get(i);
		}
		Action ret = new Action(getSelectEquipment(), getSelectMethod(), args);
		return ret;
	}

	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == cb_selectMethod ) {
			if ( cb_selectMethod.getSelectedIndex() != -1 ) {
				loadArgs(getSelectMethod());
			}
		} else if ( e.getSource() == bt_addAction ) {
			Action ac = createAction();
			ActionExecutor.getInstance().addAction(ac);
			MainGUI.getInstance().removeRightcomponent();
		}
	}
}
