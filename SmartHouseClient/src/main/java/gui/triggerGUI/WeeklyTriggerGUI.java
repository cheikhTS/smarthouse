package gui.triggerGUI;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SpringLayout;

public class WeeklyTriggerGUI extends DailyTriggerGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2638243611629491036L;
	private JList jl_selectValidDay;
	private JLabel lblJourDeLancement;

	public WeeklyTriggerGUI () {
		super();
		initComponents();
	}

	private void initComponents() {
		final SpringLayout springLayout = (SpringLayout) getLayout();
		jl_selectValidDay = new JList();
		springLayout.putConstraint(SpringLayout.NORTH, jl_selectValidDay, 62, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, jl_selectValidDay, 10, SpringLayout.WEST, this);
		jl_selectValidDay.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 9004126016325629166L;
			String[] values = new String[] { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		add(jl_selectValidDay);

		lblJourDeLancement = new JLabel("Jour(s) de lancement :");
		springLayout.putConstraint(SpringLayout.WEST, lblJourDeLancement, 0, SpringLayout.WEST, jl_selectValidDay);
		springLayout.putConstraint(SpringLayout.SOUTH, lblJourDeLancement, -6, SpringLayout.NORTH, jl_selectValidDay);
		add(lblJourDeLancement);
	}

	public ArrayList<Integer> getSelectValidDay() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for ( int index : jl_selectValidDay.getSelectedIndices() ) {
			ret.add(index + 2 % 7);
		}
		return ret;
	}
}
