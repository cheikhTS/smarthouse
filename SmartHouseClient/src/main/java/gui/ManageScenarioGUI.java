package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pojo.pattern.Scenario;
import dao.DAO;
import dao.factory.DAOFactory;

public class ManageScenarioGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 204969439302744003L;
	private final JLabel lbl_newDoamin = new JLabel("Gestion scénario");
	private final JLabel lblNomDeLa = new JLabel("Scénario");
	private Scenario scenario;
	private final JCheckBox cb_enabledScenario = new JCheckBox("Activer le scénario");

	
	/**
	 * Create the panel.
	 */
	public ManageScenarioGUI ( Scenario _scenario ) {
		scenario = _scenario;
		lblNomDeLa.setText("Scénario - " +  scenario.getName());
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		lbl_newDoamin.setBounds(10, 11, 117, 14);

		add(lbl_newDoamin);
		lblNomDeLa.setBounds(10, 33, 135, 14);

		add(lblNomDeLa);
		cb_enabledScenario.setSelected(scenario.isEnabled());
		cb_enabledScenario.addActionListener(this);
		cb_enabledScenario.setBounds(10, 54, 173, 23);
		
		add(cb_enabledScenario);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cb_enabledScenario){
			DAO<Scenario> scenarioDAO = DAOFactory.getFactory().getScenarioDAO();
			scenario.setEnabled(cb_enabledScenario.isSelected());
			scenarioDAO.update(scenario);
			MainGUI.getInstance().refreshScenario();
		}
	}
}
