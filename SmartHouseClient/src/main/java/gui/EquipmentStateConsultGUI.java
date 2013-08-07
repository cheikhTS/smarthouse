package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import pojo.domain.Equipment;
import tools.TableMethod;
import driver.DriverException;

public class EquipmentStateConsultGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 4396261639755758999L;
	protected JLabel lblAjouterUneAction = null;
	protected TableMethod tbl_method = new TableMethod();
	protected JButton bt_addAction = new JButton("Refresh");
	protected Equipment equipment = null;
	private final JTextArea textArea = new JTextArea();

	/**
	 * Create the panel.
	 */
	public EquipmentStateConsultGUI ( Equipment _equipment ) {
		equipment = _equipment;
		initComponents();
		loadState();
		setVisible(true);
	}

	protected void initComponents() {
		
		lblAjouterUneAction = new JLabel("Etat de l'équipement " + equipment.getName() + " :");
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 18, SpringLayout.SOUTH, lblAjouterUneAction);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lblAjouterUneAction, 309, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, bt_addAction, 21, SpringLayout.SOUTH, textArea);
		springLayout.putConstraint(SpringLayout.EAST, bt_addAction, 0, SpringLayout.EAST, textArea);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 309, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblAjouterUneAction, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblAjouterUneAction, 10, SpringLayout.WEST, this);
		setLayout(springLayout);

		add(lblAjouterUneAction);
		bt_addAction.addActionListener(this);

		add(bt_addAction);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		add(textArea);
	}

	private void loadState() {
		System.out.println("re2");
		try {
			equipment.loadDriver();
			textArea.setText(equipment.getDriver().getState());
		} catch (DriverException e) {
			e.printStackTrace();
			textArea.append("Erreur lors du chargement du driver.");
		}
		repaint();
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == bt_addAction) {
			loadState();
		}
	}
}
