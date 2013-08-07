package gui.triggerGUI;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class MonthlyTriggerGUI extends DailyTriggerGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8074502271606551338L;
	private JList<String> jl_validMonth = new JList<String>();
	private JList<Integer> jl_validDay = new JList<Integer>();
	private JLabel lbl_month = new JLabel("Mois :");;
	private SpringLayout springLayout;
	private JLabel lbl_days = new JLabel("Jours :");

	public MonthlyTriggerGUI () {
		super();
		initComponents();
	}

	private void initComponents() {
		lblNewLabel.setEnabled(false);
		ftf_nbRepeat.setEnabled(false);
		lblNombre.setEnabled(false);
		jl_validMonth.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3992273203095970760L;
			String[] values = new String[] { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		jl_validDay.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6597030743429293151L;
			String[] values = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
					"28", "29", "30", "31" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		JScrollPane sp_day = new JScrollPane(jl_validDay);
		JScrollPane sp_month = new JScrollPane(jl_validMonth);

		lbl_month.setLabelFor(sp_month);

		springLayout = (SpringLayout) getLayout();
		springLayout.putConstraint(SpringLayout.EAST, lbl_days, 50, SpringLayout.EAST, sp_month);
		springLayout.putConstraint(SpringLayout.EAST, lbl_month, 50, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lbl_days, 0, SpringLayout.NORTH, lbl_month);
		springLayout.putConstraint(SpringLayout.WEST, lbl_days, 100, SpringLayout.WEST, lbl_month);
		springLayout.putConstraint(SpringLayout.NORTH, sp_month, 0, SpringLayout.SOUTH, lbl_month);
		springLayout.putConstraint(SpringLayout.WEST, sp_month, 0, SpringLayout.WEST, lbl_month);

		springLayout.putConstraint(SpringLayout.NORTH, lbl_month, 44, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lbl_month, 10, SpringLayout.WEST, this);

		springLayout.putConstraint(SpringLayout.NORTH, jl_validDay, 15, SpringLayout.SOUTH, sp_day);
		springLayout.putConstraint(SpringLayout.NORTH, sp_day, 0, SpringLayout.SOUTH, lbl_days);
		springLayout.putConstraint(SpringLayout.WEST, sp_day, 0, SpringLayout.WEST, lbl_days);
		add(lbl_month);
		add(lbl_days);
		add(sp_month);
		add(sp_day);
	}

	public ArrayList<Integer> getSelectValidNumberDay() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for ( int index : jl_validDay.getSelectedIndices() ) {
			ret.add(index + 1);
		}
		return ret;
	}

	public ArrayList<Integer> getSelectValidMonth() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for ( int index : jl_validMonth.getSelectedIndices() ) {
			ret.add(index);
		}
		return ret;
	}
}
