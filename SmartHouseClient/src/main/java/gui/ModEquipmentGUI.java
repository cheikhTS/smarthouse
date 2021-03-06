package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;

import pojo.domain.DriverIdentifier;
import pojo.domain.Equipment;
import pojo.domain.Home;
import tools.TableProperties;
import dao.DAO;
import dao.factory.DAOFactory;
import driver.DriverManager;

public class ModEquipmentGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8027344322168539444L;
	private Equipment equipment;
	private JLabel lblAjouterUnquipement;
	private JLabel lbl_choosePilote;
	private JComboBox<DriverIdentifier> cb_chooseDriver;
	private JTable jtable_properties;
	private TableProperties tbl_properties = new TableProperties();
	private JLabel lblNewLabel;
	private JTextField tf_equipmentName;
	private JButton bt_ModEquipment;
	private JButton bt_addDriver;
	/**
	 * Create the panel.
	 */
	public ModEquipmentGUI (Equipment _equipment) {
		equipment = _equipment;
		initComponents();
		loadDrivers();
		loadProperties(equipment.getDriverIdentifier());
	}
	
	private void initComponents() {
		jtable_properties = new JTable(tbl_properties);
		jtable_properties.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtable_properties.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtable_properties.setFillsViewportHeight(true);
		JScrollPane sp_args = new JScrollPane(jtable_properties);
		
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, sp_args, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, sp_args, -10, SpringLayout.EAST, this);
		setLayout(springLayout);
		
		lblAjouterUnquipement = new JLabel("Modifier un équipement");
		springLayout.putConstraint(SpringLayout.NORTH, lblAjouterUnquipement, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblAjouterUnquipement, 10, SpringLayout.WEST, this);
		add(lblAjouterUnquipement);
		
		lbl_choosePilote = new JLabel("Choisir le pilote :");
		springLayout.putConstraint(SpringLayout.WEST, lbl_choosePilote, 0, SpringLayout.WEST, lblAjouterUnquipement);
		add(lbl_choosePilote);
		
		cb_chooseDriver = new JComboBox<DriverIdentifier>();
		springLayout.putConstraint(SpringLayout.NORTH, sp_args, 10, SpringLayout.SOUTH, cb_chooseDriver);
		springLayout.putConstraint(SpringLayout.EAST, cb_chooseDriver, 0, SpringLayout.EAST, sp_args);
		cb_chooseDriver.addActionListener(this);
		springLayout.putConstraint(SpringLayout.NORTH, cb_chooseDriver, 6, SpringLayout.SOUTH, lbl_choosePilote);
		springLayout.putConstraint(SpringLayout.WEST, cb_chooseDriver, 10, SpringLayout.WEST, this);
		lbl_choosePilote.setLabelFor(cb_chooseDriver);
		add(cb_chooseDriver);
		add(sp_args);
		
		lblNewLabel = new JLabel("Nom de l'équipement :");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, lblAjouterUnquipement);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblAjouterUnquipement);
		add(lblNewLabel);
		
		tf_equipmentName = new JTextField(equipment.getName());
		springLayout.putConstraint(SpringLayout.NORTH, lbl_choosePilote, 6, SpringLayout.SOUTH, tf_equipmentName);
		springLayout.putConstraint(SpringLayout.NORTH, tf_equipmentName, 6, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, tf_equipmentName, 0, SpringLayout.WEST, lblAjouterUnquipement);
		springLayout.putConstraint(SpringLayout.EAST, tf_equipmentName, 0, SpringLayout.EAST, lblAjouterUnquipement);
		add(tf_equipmentName);
		tf_equipmentName.setColumns(10);
		
		bt_ModEquipment = new JButton("Valider");
		bt_ModEquipment.addActionListener(this);
		springLayout.putConstraint(SpringLayout.SOUTH, sp_args, -10, SpringLayout.NORTH, bt_ModEquipment);
		springLayout.putConstraint(SpringLayout.NORTH, bt_ModEquipment, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, bt_ModEquipment, -150, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, bt_ModEquipment, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, bt_ModEquipment, -10, SpringLayout.EAST, this);
		add(bt_ModEquipment);
		
		bt_addDriver = new JButton("Charger un pilote");
		bt_addDriver.addActionListener(this);
		springLayout.putConstraint(SpringLayout.SOUTH, bt_addDriver, 0, SpringLayout.SOUTH, tf_equipmentName);
		springLayout.putConstraint(SpringLayout.EAST, bt_addDriver, 0, SpringLayout.EAST, cb_chooseDriver);
		add(bt_addDriver);
	}
	
	public void loadDrivers(){
		List<DriverIdentifier> drivers = DriverManager.driversIdentifier;
		int count = 0;
		int selectedId = 0;
		for (DriverIdentifier cl : drivers){
			if (cl.equals(equipment.getDriverIdentifier())) {
				selectedId = count;
			}
			cb_chooseDriver.addItem(cl);
			count++;
		}
		cb_chooseDriver.setSelectedIndex(selectedId);
	}
	
	public void loadProperties(DriverIdentifier driver){
		Properties prop = new Properties();
		if (driver.equals(equipment.getDriverIdentifier())) {
			prop = equipment.getRealProperties();
		} else {
			List<String> keys;
			try {
				keys = DriverManager.getPropertiesRequired(Class.forName(driver.getDriverClass()));
				for (String key : keys){
					prop.put(key, "");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		tbl_properties.refresh(prop);
	}
	
	public void updateEquipment(){
		DriverIdentifier drivId = (DriverIdentifier)cb_chooseDriver.getSelectedItem();
		Properties prop = tbl_properties.getProperties();
		String name = tf_equipmentName.getText();
		equipment.setDriverIdentifier(drivId);
		equipment.setProperties(prop);
		equipment.setName(name);

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt_ModEquipment){
			DAO<Home> daohome = DAOFactory.getFactory().getHomeDAO();
			updateEquipment();
			daohome.update(equipment.getRoom().getArea().getHome());
			MainGUI.getInstance().removeRightcomponent();
		}
		else if(e.getSource() == cb_chooseDriver){
			if (cb_chooseDriver.getSelectedIndex() != -1){
				loadProperties((DriverIdentifier)cb_chooseDriver.getSelectedItem());
			}
		}else if(e.getSource() == bt_addDriver){
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            String fileName = file.getAbsolutePath();
	            String extension =  fileName.substring(fileName.lastIndexOf(".")+1);
	            if (extension.equalsIgnoreCase("jar")){
	            	DriverManager.loadDriver(fileName);
	            }else{
	            	System.err.println("Erreur lors du chargement du jar");
	            }
	            loadDrivers();
	        }
		}
	}
	
}
