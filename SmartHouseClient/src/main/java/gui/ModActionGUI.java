package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import pojo.pattern.Scenario;
import tools.TableMethod;
import dao.DAO;
import dao.factory.DAOFactory;
import driver.DriverException;
import driver.DriverManager;
import driver.EquipmentAction;

public class ModActionGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1387324763112014044L;
	protected JLabel lblAjouterUneAction = new JLabel("Modifier action");
	protected JComboBox cb_selectEquipment = new JComboBox();
	protected JLabel lblSelectionnerUnquipment = new JLabel("Selectionner un équipment :");
	protected JComboBox cb_selectMethod = new JComboBox();
	protected JLabel lbl_selectMethod = new JLabel("Séléctionner une méthode :");
	protected TableMethod tbl_method = new TableMethod();
	protected JTable table_args;
	protected JButton bt_modAction = new JButton("Valider");
	protected Action action = null;

	/**
	 * Create the panel.
	 */
	public ModActionGUI (Action _action) {
		setPreferredSize(new Dimension(520, 320));
		action = _action;
		initComponents();
		loadEquipments();
		setVisible(true);
	}

	private void initComponents() {
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
		springLayout.putConstraint(SpringLayout.SOUTH, bt_modAction, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, cb_selectMethod, 0, SpringLayout.EAST, cb_selectEquipment);
		springLayout.putConstraint(SpringLayout.NORTH, sp_args, 10, SpringLayout.SOUTH, cb_selectMethod);
		springLayout.putConstraint(SpringLayout.SOUTH, sp_args, -10, SpringLayout.NORTH, bt_modAction);
		springLayout.putConstraint(SpringLayout.EAST, bt_modAction, 0, SpringLayout.EAST, sp_args);
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
		cb_selectEquipment.addActionListener(this);

		add(cb_selectEquipment);

		add(lblSelectionnerUnquipment);
		cb_selectMethod.addActionListener(this);

		add(cb_selectMethod);

		add(lbl_selectMethod);

		add(sp_args);
		bt_modAction.addActionListener(this);

		add(bt_modAction);
	}

	public void loadEquipments() {
		cb_selectEquipment.removeAllItems();
		List<Equipment> equipments = new ArrayList<Equipment>();
		Home home = action.getTask().getScenario().getHome();
		for ( Area area : home.getAreas() ) {
			for ( Room room : area.getRooms() ) {
				equipments.addAll(room.getEquipments());
			}
		}
		int count = 0;
		int selectedId = 0;
		for ( Equipment equipment : equipments ) {
			if (equipment.equals(action.getEquipment())) {
				selectedId = count;
			}
			cb_selectEquipment.addItem(equipment);
			count++;
		}
		cb_selectEquipment.setSelectedIndex(selectedId);
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
		System.out.println("load " + equipment);
		cb_selectMethod.removeAllItems();
		try {
			equipment.loadDriver();
		} catch (DriverException e) {
			e.printStackTrace();
		}
		List<Method> methods = DriverManager.getActionsAvailable(equipment.getDriver());
		int count = 0;
		int selectedId = 0;
		for ( Method meth : methods ) {
			if (meth.equals(action.getMethod())) {
				selectedId = count;
			}
			cb_selectMethod.addItem(meth.getAnnotation(EquipmentAction.class).name());
			count++;
		}
		cb_selectMethod.setSelectedIndex(selectedId);
	}

	public void loadArgs(Method meth) {
		tbl_method.refresh(meth);
		if(meth.equals(action.getMethod())){
			//tbl_method.setArgs(new ArrayList<Object>(Arrays.asList(action.getArgs())));
		}
	}

	public void updateAction() {
		StringBuilder str = new StringBuilder();
		for ( int i = 0; i < getSelectMethod().getParameterTypes().length; i++ ) {
			str.append(tbl_method.getArgs().get(i).toString()+";");
		}
		action.setEquipment(getSelectEquipment());
		action.setMethod(getSelectMethod().getName());
		action.setArgs(str.toString());
	}

	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == cb_selectEquipment ) {
			if ( cb_selectEquipment.getSelectedIndex() != -1 ) {
				loadMethods(getSelectEquipment());
			}
		} else if ( e.getSource() == cb_selectMethod ) {
			if ( cb_selectMethod.getSelectedIndex() != -1 ) {
				loadArgs(getSelectMethod());
			}
		} else if ( e.getSource() == bt_modAction ) {
			updateAction();
			DAO<Scenario> scenarioDAO = DAOFactory.getFactory().getScenarioDAO();
			scenarioDAO.update(action.getTask().getScenario());
			MainGUI.getInstance().removeRightcomponent();
		}
	}
}
