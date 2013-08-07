/**
 * 
 */
package tree;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import pojo.pattern.trigger.ATrigger;

/**
 * Réalisée le 30 sept. 2012
 * 
 * 
 * @author Florent
 */
public class SmartHouseTreeView extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5259170122394151691L;

	public SmartHouseTreeView ( TreeModel model ) {
		super(model);

		setCellRenderer(new HomeTreeCellRenderer());
	}

	/*
	 * @Override public TreeCellRenderer getCellRenderer() { return renderer; }
	 */

	private static ImageIcon loadIcon(String relativeIconPath) {
		URL url = SmartHouseTreeView.class.getResource(relativeIconPath);
		return new ImageIcon(url);
	}

	/**
	 * Personnalisation graphique du JTree
	 * 
	 * @author Florent
	 */
	private class HomeTreeCellRenderer extends DefaultTreeCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8092084882520125715L;
		private ImageIcon iconSmartHome = loadIcon("/smarthouse16.png");
		private ImageIcon iconHome = loadIcon("/icon_home16.png");
		private ImageIcon iconArea = loadIcon("/icon_area16.png");
		private ImageIcon iconRoom = loadIcon("/icon_room16.png");
		private ImageIcon iconEquipment = loadIcon("/icon_equipment16.png");
		private ImageIcon iconScenario = loadIcon("/icon_scenario16.png");
		private ImageIcon iconAction = loadIcon("/icon_action16.png");
		private ImageIcon iconTrigger = loadIcon("/icon_trigger16.png");

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

			if ( value instanceof Home ) {
				Home home = (Home) value;
				setIcon(iconHome);
				setText(home.getName());
				setToolTipText(home.getAreas().size() + " zones");
			} else if ( value instanceof Area ) {
				Area area = (Area) value;
				setIcon(iconArea);
				setText(area.getName());
				setToolTipText(area.getRooms().size() + " pièces");
			} else if ( value instanceof Room ) {
				Room room = (Room) value;
				setIcon(iconRoom);
				setText(room.getName());
				setToolTipText(room.getEquipments().size() + " équipements");
			} else if ( value instanceof Equipment ) {
				Equipment equip = (Equipment) value;
				setIcon(iconEquipment);
				setText(equip.getName());

				if ( equip.getDriver() != null ) {
					setToolTipText("Equipement " + equip.getDriver().getHardwareName() + ", " + equip.getDriver().getHardwareManufacturer() + ", " + equip.getDriver().getHardwareDescription());
				} else {
					setToolTipText("Aucun driver affecté");
				}
			} else if ( value instanceof Scenario ) {
				Scenario scenario = (Scenario) value;
				setIcon(iconScenario);
				setText(scenario.getName());
			} else if ( value instanceof Task ) {
				Task task = (Task) value;
				setIcon(iconScenario);
				setText(task.getName());
			} else if ( value instanceof Action ) {
				Action action = (Action) value;
				setIcon(iconAction);
				setText(action.getName());
				setToolTipText(action.getDescription());
			} else if ( value instanceof ATrigger ) {
				ATrigger trigger = (ATrigger) value;
				setIcon(iconTrigger);
				setText(trigger.getName());
				setToolTipText("Effectif du " + trigger.getDateStart() + " au " + trigger.getDateExpiration());
			} else if ( value instanceof String ) {
				setIcon(iconSmartHome);
				setText((String) value);
			}

			return this;
		}

	}
}
