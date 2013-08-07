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

public class AddDomainGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 204969439302744003L;
	private final JLabel lbl_newDoamin = new JLabel("Ajouter une maison");
	private final JLabel lblNomDeLa = new JLabel("Nom de la maison :");
	private final JTextField tf_DomainName = new JTextField();
	private final JButton bt_addDomain = new JButton("Ajouter la maison");
	private Home home;
	private Area area;
	private Scenario scenario;

	private int cas = 0;

	/**
	 * Create the panel.
	 */
	public AddDomainGUI () {
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);

		initComponents();
	}

	/**
	 * Create the panel.
	 */
	public AddDomainGUI ( Home _home ) {
		home = _home;
		cas = 1;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Ajouter une zone");
		lblNomDeLa.setText("Nom de la zone");
		bt_addDomain.setText("Ajouter la zone");
		initComponents();
	}

	/**
	 * Create the panel.
	 */
	public AddDomainGUI ( Area _area ) {
		area = _area;
		cas = 2;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Ajouter une pièce");
		lblNomDeLa.setText("Nom de la pièce");
		bt_addDomain.setText("Ajouter la pièce");
		initComponents();
	}

	/**
	 * Create the panel.
	 */
	public AddDomainGUI ( Home _home, boolean scenario ) {
		home = _home;
		cas = 3;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Ajouter un scénario");
		lblNomDeLa.setText("Nom du scénario");
		bt_addDomain.setText("Ajouter le scénario");
		initComponents();
	}

	/**
	 * Create the panel.
	 */
	public AddDomainGUI ( Scenario _scenario ) {
		scenario = _scenario;
		cas = 4;
		tf_DomainName.setBounds(10, 51, 117, 20);
		tf_DomainName.setColumns(10);
		lbl_newDoamin.setText("Ajouter une tâche");
		lblNomDeLa.setText("Nom de la tâche");
		bt_addDomain.setText("Ajouter la tâche");
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		lbl_newDoamin.setBounds(10, 11, 117, 14);

		add(lbl_newDoamin);
		lblNomDeLa.setBounds(10, 33, 135, 14);

		add(lblNomDeLa);

		add(tf_DomainName);
		bt_addDomain.addActionListener(this);
		bt_addDomain.setBounds(10, 80, 135, 23);

		add(bt_addDomain);
	}

	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == bt_addDomain ) {
			String name = tf_DomainName.getText();
			DAO<Home> daohome = DAOFactory.getFactory().getHomeDAO();
			DAO<Scenario> daoScenario = DAOFactory.getFactory().getScenarioDAO();
			switch ( cas ) {
				case 0:
					Home home_new = new Home(name);
					home = home_new;
					daohome.create(home);
				break;
				case 1:
					new Area(name, home);
					daohome.create(home);
				break;
				case 2:
					Room room_new = new Room(name, area);
					home = room_new.getArea().getHome();
					daohome.create(home);
				break;
				case 3:
					Scenario scenario1 = new Scenario(name, home);
					daoScenario.create(scenario1);
				break;
				case 4:
					new Task(name, scenario);
					daoScenario.create(scenario);
				break;
			}
			MainGUI.getInstance().removeRightcomponent();
		}
	}
}
