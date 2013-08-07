package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pojo.domain.Area;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;
import dao.factory.DAOFactory;

public class ModDomainGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019753793102844053L;
	private final JLabel lbl_newDoamin = new JLabel("");
	private final JLabel lblNomDeLa = new JLabel("");
	private final JTextField tf_DomainName = new JTextField();
	private final JButton bt_modDomain = new JButton("");
	private Home home;
	private Area area;
	private Room room;
	private Scenario scenario;
	private Task task;
	
	private int cas = 0;
	/**
	 * Create the panel.
	 */
	public ModDomainGUI (Home _home) {
		home = _home;
		cas = 1;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Modifier la maison");
		lblNomDeLa.setText("Nom de la maison");
		bt_modDomain.setText("Valider");
		tf_DomainName.setText(home.getName());
		initComponents();
	}
	
	/**
	 * Create the panel.
	 */
	public ModDomainGUI (Area _area) {
		area = _area;
		cas = 2;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Modifier la zone");
		lblNomDeLa.setText("Nom de la zone");
		bt_modDomain.setText("Valider");
		tf_DomainName.setText(area.getName());
		initComponents();
	}
	
	/**
	 * Create the panel.
	 */
	public ModDomainGUI (Room _room) {
		room = _room;
		cas = 3;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Modifier la pièce");
		lblNomDeLa.setText("Nom de la pièce");
		bt_modDomain.setText("Valider");
		tf_DomainName.setText(room.getName());
		initComponents();
	}
	
	/**
	 * Create the panel.
	 */
	public ModDomainGUI (Scenario _scenario) {
		scenario = _scenario;
		cas = 4;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Modifier le scénario");
		lblNomDeLa.setText("Nom du scénario");
		bt_modDomain.setText("Valider");
		tf_DomainName.setText(scenario.getName());
		initComponents();
	}
	
	/**
	 * Create the panel.
	 */
	public ModDomainGUI (Task _task) {
		task = _task;
		cas = 5;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Modifier la tache");
		lblNomDeLa.setText("Nom de la tache");
		bt_modDomain.setText("Valider");
		tf_DomainName.setText(task.getName());
		initComponents();
	}
	
	private void initComponents() {
		setLayout(null);
		lbl_newDoamin.setBounds(10, 11, 117, 14);
		
		add(lbl_newDoamin);
		lblNomDeLa.setBounds(10, 33, 135, 14);
		
		add(lblNomDeLa);
		
		add(tf_DomainName);
		bt_modDomain.addActionListener(this);
		bt_modDomain.setBounds(10, 80, 135, 23);
		
		add(bt_modDomain);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == bt_modDomain){
			String name = tf_DomainName.getText();
			DAO<Home> daohome = DAOFactory.getFactory().getHomeDAO();
			DAO<Scenario> daoScenario = DAOFactory.getFactory().getScenarioDAO();
			switch(cas){
				case 1:
					home.setName(name);
					daohome.update(home);
					break;
				case 2:
					area.setName(name);
					daohome.update(area.getHome());
					break;
				case 3:
					room.setName(name);
					daohome.update(room.getArea().getHome());
					break;
				case 4:
					scenario.setName(name);
					daoScenario.update(scenario);
					break;
				case 5:
					task.setName(name);
					daoScenario.update(task.getScenario());
					break;
			}
			MainGUI.getInstance().removeRightcomponent();
		}
	}
}
