package gui.triggerGUI;

import gui.MainGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import pojo.pattern.Scenario;
import pojo.pattern.Task;
import pojo.pattern.trigger.ATrigger;
import pojo.pattern.trigger.DailyTrigger;
import pojo.pattern.trigger.MonthlyTrigger;
import pojo.pattern.trigger.OnceTrigger;
import pojo.pattern.trigger.WeeklyTrigger;

import com.toedter.calendar.JDateChooser;

import dao.DAO;
import dao.factory.DAOFactory;

public class AddTriggerGUI extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1451595757170853768L;
	protected Task task = null;
	private JLabel lblAjouter;
	private JComboBox cb_selectTrigger;
	private JDateChooser jdc_selectDate;
	private JDateChooser jdc_selectDateExpiration;
	private JFormattedTextField ftf_selectStartTime;
	private SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm:ss");
	private JLabel lblHeureDeDmarrage;
	private JPanel pnl_triggerOption;
	private JButton bt_addTrigger;
	private DailyTriggerGUI dailyTriggerGui;
	private WeeklyTriggerGUI weeklyTriggerGui;
	private MonthlyTriggerGUI monthlyTriggerGui;
	private JCheckBox chckb_useExpirDate;

	/**
	 * Create the panel.
	 */
	public AddTriggerGUI () {
		initComponents();
	}

	/**
	 * Create the panel.
	 */
	public AddTriggerGUI ( Task _task ) {
		task = _task;
		initComponents();
	}

	public int getTimeInSecond() {
		String str = ftf_selectStartTime.getText();
		String[] hours = str.split(":");
		int hour = Integer.parseInt(hours[0]);
		int minut = Integer.parseInt(hours[1]);
		int second = Integer.parseInt(hours[2]);
		return hour * 3600 + minut * 60 + second;
	}

	private void initComponents() {
		setPreferredSize(new Dimension(500, 320));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		lblAjouter = new JLabel("Ajouter un déclencheur");
		add(lblAjouter);

		cb_selectTrigger = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, cb_selectTrigger, 32, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblAjouter, 0, SpringLayout.WEST, cb_selectTrigger);
		springLayout.putConstraint(SpringLayout.SOUTH, lblAjouter, -6, SpringLayout.NORTH, cb_selectTrigger);
		cb_selectTrigger.addActionListener(this);
		cb_selectTrigger.setModel(new DefaultComboBoxModel(new String[] { "Une fois", "Chaque jour", "Chaque semaine", "Chaque mois" }));
		springLayout.putConstraint(SpringLayout.WEST, cb_selectTrigger, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, cb_selectTrigger, 130, SpringLayout.WEST, this);
		add(cb_selectTrigger);

		jdc_selectDate = new JDateChooser(Calendar.getInstance().getTime());
		springLayout.putConstraint(SpringLayout.NORTH, jdc_selectDate, 25, SpringLayout.SOUTH, cb_selectTrigger);
		springLayout.putConstraint(SpringLayout.WEST, jdc_selectDate, 0, SpringLayout.WEST, lblAjouter);
		springLayout.putConstraint(SpringLayout.EAST, jdc_selectDate, 0, SpringLayout.EAST, cb_selectTrigger);
		jdc_selectDate.setBorder(new TitledBorder(null, "Date de d\u00E9marrage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(jdc_selectDate);

		jdc_selectDateExpiration = new JDateChooser(Calendar.getInstance().getTime());
		jdc_selectDateExpiration.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, jdc_selectDateExpiration, 0, SpringLayout.NORTH, jdc_selectDate);
		jdc_selectDateExpiration.setBorder(new TitledBorder(null, "D\u00E9finir une date d'expiration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		springLayout.putConstraint(SpringLayout.WEST, jdc_selectDate, 0, SpringLayout.WEST, lblAjouter);
		add(jdc_selectDateExpiration);

		ftf_selectStartTime = new JFormattedTextField(simpleDate);
		springLayout.putConstraint(SpringLayout.WEST, ftf_selectStartTime, 10, SpringLayout.EAST, jdc_selectDate);
		springLayout.putConstraint(SpringLayout.EAST, ftf_selectStartTime, 80, SpringLayout.EAST, jdc_selectDate);
		ftf_selectStartTime.setText(simpleDate.format(Calendar.getInstance().getTime()));
		add(ftf_selectStartTime);

		lblHeureDeDmarrage = new JLabel("Heure de démarrage");
		springLayout.putConstraint(SpringLayout.WEST, lblHeureDeDmarrage, 10, SpringLayout.EAST, jdc_selectDate);
		springLayout.putConstraint(SpringLayout.EAST, jdc_selectDateExpiration, 200, SpringLayout.EAST, lblHeureDeDmarrage);
		springLayout.putConstraint(SpringLayout.NORTH, ftf_selectStartTime, 6, SpringLayout.SOUTH, lblHeureDeDmarrage);
		springLayout.putConstraint(SpringLayout.WEST, jdc_selectDateExpiration, 26, SpringLayout.EAST, lblHeureDeDmarrage);
		springLayout.putConstraint(SpringLayout.NORTH, lblHeureDeDmarrage, 0, SpringLayout.NORTH, jdc_selectDate);
		add(lblHeureDeDmarrage);

		pnl_triggerOption = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, ftf_selectStartTime, -10, SpringLayout.NORTH, pnl_triggerOption);
		pnl_triggerOption.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		springLayout.putConstraint(SpringLayout.NORTH, pnl_triggerOption, 10, SpringLayout.SOUTH, jdc_selectDate);
		springLayout.putConstraint(SpringLayout.WEST, pnl_triggerOption, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, pnl_triggerOption, -10, SpringLayout.EAST, this);
		add(pnl_triggerOption);

		bt_addTrigger = new JButton("Ajouter le déclencheur");
		bt_addTrigger.addActionListener(this);
		springLayout.putConstraint(SpringLayout.NORTH, bt_addTrigger, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, pnl_triggerOption, -10, SpringLayout.NORTH, bt_addTrigger);
		springLayout.putConstraint(SpringLayout.SOUTH, bt_addTrigger, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, bt_addTrigger, 0, SpringLayout.EAST, pnl_triggerOption);
		pnl_triggerOption.setLayout(new BorderLayout(0, 0));
		add(bt_addTrigger);

		chckb_useExpirDate = new JCheckBox("Indiquer une date d'expiration");
		chckb_useExpirDate.addActionListener(this);
		springLayout.putConstraint(SpringLayout.WEST, chckb_useExpirDate, 0, SpringLayout.WEST, jdc_selectDateExpiration);
		springLayout.putConstraint(SpringLayout.SOUTH, chckb_useExpirDate, -6, SpringLayout.NORTH, jdc_selectDateExpiration);
		add(chckb_useExpirDate);
	}

	public ATrigger createTrigger() {
		ATrigger trig = null;
		switch ( cb_selectTrigger.getSelectedIndex() ) {
			case 0:
				trig = new OnceTrigger(task, jdc_selectDate.getDate(), getTimeInSecond());
			break;
			case 1:
				trig = new DailyTrigger(task, jdc_selectDate.getDate(), getTimeInSecond(), dailyTriggerGui.getNbofRepeat());
			break;
			case 2:
				trig = new WeeklyTrigger(task, jdc_selectDate.getDate(), getTimeInSecond(), weeklyTriggerGui.getSelectValidDay(), weeklyTriggerGui.getNbofRepeat());
			break;
			case 3:
				trig = new MonthlyTrigger(task, jdc_selectDate.getDate(), getTimeInSecond(), monthlyTriggerGui.getSelectValidMonth(), monthlyTriggerGui.getSelectValidNumberDay());
			break;
		}
		if ( chckb_useExpirDate.isSelected() ) {
			trig.setDateExpiration(jdc_selectDateExpiration.getDate());
		}
		return trig;
	}

	public void actionPerformed(ActionEvent e) {
		if ( e.getSource() == cb_selectTrigger ) {
			if ( cb_selectTrigger.getSelectedIndex() != -1 ) {
				switch ( cb_selectTrigger.getSelectedIndex() ) {
					case 0:
						pnl_triggerOption.removeAll();
						pnl_triggerOption.revalidate();
						pnl_triggerOption.validate();
						pnl_triggerOption.repaint();
					break;
					case 1:
						pnl_triggerOption.removeAll();
						dailyTriggerGui = new DailyTriggerGUI();
						pnl_triggerOption.add(dailyTriggerGui);
						pnl_triggerOption.revalidate();
						pnl_triggerOption.validate();
						pnl_triggerOption.repaint();
					break;
					case 2:
						pnl_triggerOption.removeAll();
						weeklyTriggerGui = new WeeklyTriggerGUI();
						pnl_triggerOption.add(weeklyTriggerGui);
						pnl_triggerOption.revalidate();
						pnl_triggerOption.validate();
						pnl_triggerOption.repaint();
					break;
					case 3:
						pnl_triggerOption.removeAll();
						monthlyTriggerGui = new MonthlyTriggerGUI();
						pnl_triggerOption.add(monthlyTriggerGui);
						pnl_triggerOption.revalidate();
						pnl_triggerOption.validate();
						pnl_triggerOption.repaint();
					break;
				}
			}
		} else if ( e.getSource() == chckb_useExpirDate ) {
			jdc_selectDateExpiration.setEnabled(chckb_useExpirDate.isSelected());
		} else if ( e.getSource() == bt_addTrigger ) {
			createTrigger();
			DAO<Scenario> scenarioDAO = DAOFactory.getFactory().getScenarioDAO();
			Scenario scenario = task.getScenario();
			scenarioDAO.create(scenario);
			MainGUI.getInstance().removeRightcomponent();
		}
	}
}
