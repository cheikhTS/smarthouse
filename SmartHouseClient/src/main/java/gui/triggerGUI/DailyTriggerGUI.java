package gui.triggerGUI;

import java.awt.Dimension;
import java.text.Format;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class DailyTriggerGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4176456650857710942L;
	protected JLabel lblNombre;
	protected JFormattedTextField ftf_nbRepeat;
	protected JLabel lblNewLabel;
	private Format integerFormat = NumberFormat.getIntegerInstance();

	/**
	 * Create the panel.
	 */
	public DailyTriggerGUI () {

		initComponents();
	}

	private void initComponents() {
		setPreferredSize(new Dimension(300, 150));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		lblNombre = new JLabel("Répéter tous les ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNombre, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblNombre, 10, SpringLayout.WEST, this);
		add(lblNombre);

		ftf_nbRepeat = new JFormattedTextField(integerFormat);
		ftf_nbRepeat.setText("1");
		springLayout.putConstraint(SpringLayout.NORTH, ftf_nbRepeat, 4, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, ftf_nbRepeat, 6, SpringLayout.EAST, lblNombre);
		springLayout.putConstraint(SpringLayout.EAST, ftf_nbRepeat, 35, SpringLayout.EAST, lblNombre);
		add(ftf_nbRepeat);
		ftf_nbRepeat.setColumns(10);

		lblNewLabel = new JLabel("jours.");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, lblNombre);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 6, SpringLayout.EAST, ftf_nbRepeat);
		add(lblNewLabel);
	}

	public int getNbofRepeat() {
		return Integer.parseInt(ftf_nbRepeat.getText());
	}

}
