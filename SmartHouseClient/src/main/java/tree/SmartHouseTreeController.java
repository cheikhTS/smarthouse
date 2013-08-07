/**
 * 
 */
package tree;

import gui.AddActionGUI;
import gui.AddDomainGUI;
import gui.AddEquipmentGUI;
import gui.EquipmentStateConsultGUI;
import gui.MainGUI;
import gui.ManageScenarioGUI;
import gui.ModActionGUI;
import gui.ModDomainGUI;
import gui.ModEquipmentGUI;
import gui.StartActionGUI;
import gui.triggerGUI.AddTriggerGUI;
import gui.triggerGUI.ModTriggerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSplitPane;
import javax.swing.JTree;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import pojo.pattern.trigger.ATrigger;
import dao.factory.DAOFactory;

/**
 * Réalisée le 30 sept. 2012
 * 
 * 
 * @author Florent
 */
public class SmartHouseTreeController extends MouseAdapter implements ActionListener {

	public SmartHouseTreeController () {

	}

	/**
	 * Retourne l'objet sélectionné dans une source JTree
	 * 
	 * @param e
	 * @return
	 */
	public Object getSelectedObject() {
		MainGUI gui = MainGUI.getInstance();
		if ( !gui.getHomesTree().isSelectionEmpty() ) {
			return gui.getHomesTree().getSelectionPath().getLastPathComponent();
		} else if ( !gui.getEquipmentsTree().isSelectionEmpty() ) {
			return gui.getEquipmentsTree().getSelectionPath().getLastPathComponent();
		} else if ( !gui.getScenariosTree().isSelectionEmpty() ) { return gui.getScenariosTree().getSelectionPath().getLastPathComponent(); }
		return null;
	}

	/**
	 * Lorsqu'on veut notifier l'arbre qu'une modif du modèle a été faite.
	 * 
	 * @param tree
	 */
	public void fireTreeModelChanged(JTree tree) {
		tree.getModel().valueForPathChanged(null, null);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if ( e.isPopupTrigger() ) {
			MainGUI.getInstance().getJpm_contextMenu().show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ( e.getClickCount() == 2 ) {
			Object obj = getSelectedObject();			
			// exemple si j'add une home, je notifie la tree pour qu'elle se
			// mette à jour.
			fireTreeModelChanged((JTree) e.getSource());
		}else if (e.getClickCount() == 1){
			Object obj = getSelectedObject();	
			if (obj instanceof Scenario){
				MainGUI.getInstance().createRightComponent(new ManageScenarioGUI((Scenario) obj));
			} else if (obj instanceof Equipment){
				MainGUI.getInstance().createRightComponent(new EquipmentStateConsultGUI((Equipment) obj));
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = null;
		if ( e.getSource() == MainGUI.getInstance().getMui_add() ) {
			obj = getSelectedObject();
			addEvent(obj);
		} else if ( e.getSource() == MainGUI.getInstance().getMui_modify() ) {
			obj = getSelectedObject();
			modEvent(obj);
		} else if ( e.getSource() == MainGUI.getInstance().getMui_delete() ) {
			obj = getSelectedObject();
			delEvent(obj);
		}
	}

	public void delEvent(Object obj) {
		if ( obj != null ) {
			MainGUI.getInstance().removeRightcomponent();
			if ( MainGUI.getInstance().getTabbedPane().getSelectedIndex() == 1 ) { return; }
			if ( obj instanceof Home ) {
				DAOFactory.getFactory().getHomeDAO().delete((Home) obj);
			} else if ( obj instanceof Area ) {
				Home home = ((Area) obj).getHome();
				home.getAreas().remove(obj);
				DAOFactory.getFactory().getHomeDAO().update(home);
			} else if ( obj instanceof Room ) {
				Home home = ((Room) obj).getArea().getHome();
				((Room) obj).getArea().getRooms().remove(obj);
				DAOFactory.getFactory().getHomeDAO().update(home);
			} else if ( obj instanceof Scenario ) {
				DAOFactory.getFactory().getScenarioDAO().delete((Scenario) obj);
			} else if ( obj instanceof Task ) {
				Scenario scenario = ((Task) obj).getScenario();
				scenario.getTasks().remove(obj);
				DAOFactory.getFactory().getScenarioDAO().update(scenario);
			} else if ( obj instanceof Action ) {
				Scenario scenario = ((Action) obj).getTask().getScenario();
				((Action) obj).getTask().getActions().remove(obj);
				DAOFactory.getFactory().getScenarioDAO().update(scenario);
			} else if ( obj instanceof ATrigger ) {
				Scenario scenario = ((ATrigger) obj).getTask().getScenario();
				((ATrigger) obj).getTask().getTriggers().remove(obj);
				DAOFactory.getFactory().getScenarioDAO().update(scenario);
			} else if ( obj instanceof String ) {
				MainGUI.getInstance().createRightComponent(new AddDomainGUI());
			}
			MainGUI.getInstance().removeRightcomponent();
		}
	}

	public void addEvent(Object obj) {
		if ( obj != null ) {
			MainGUI.getInstance().removeRightcomponent();
			switch ( MainGUI.getInstance().getTabbedPane().getSelectedIndex() ) {
				case 0:
					if ( obj instanceof Home ) {
						MainGUI.getInstance().createRightComponent(new AddDomainGUI((Home) obj));
					} else if ( obj instanceof Area ) {
						MainGUI.getInstance().createRightComponent(new AddDomainGUI((Area) obj));
					} else if ( obj instanceof Room ) {
						MainGUI.getInstance().createRightComponent(new AddEquipmentGUI((Room) obj));
					} else if ( obj instanceof String ) {
						MainGUI.getInstance().createRightComponent(new AddDomainGUI());
					} else if ( obj instanceof Equipment ) {
						MainGUI.getInstance().createRightComponent(new StartActionGUI((Equipment) obj));
					}
				break;
				case 1:
					if ( obj instanceof Equipment ) {
						MainGUI.getInstance().createRightComponent(new StartActionGUI((Equipment) obj));
					}
				break;
				case 2:
					if ( obj instanceof Scenario ) {
						MainGUI.getInstance().createRightComponent(new AddDomainGUI((Scenario) obj));
					} else if ( obj instanceof Home ) {
						MainGUI.getInstance().createRightComponent(new AddDomainGUI((Home) obj, true));
					} else if ( obj instanceof Task ) {
						JSplitPane splitpane = new JSplitPane();
						splitpane.setLeftComponent(new AddTriggerGUI((Task) obj));
						splitpane.setRightComponent(new AddActionGUI((Task) obj));
						MainGUI.getInstance().createRightComponent(splitpane);
					}
				break;
			}
		}
	}
	
	public void modEvent(Object obj){
		if (obj != null) {
			MainGUI.getInstance().removeRightcomponent();
			switch (MainGUI.getInstance().getTabbedPane().getSelectedIndex()) {
				case 0 : 
					if (obj instanceof Home) {
						MainGUI.getInstance().createRightComponent(
								new ModDomainGUI((Home) obj));
					} else if (obj instanceof Area) {
						MainGUI.getInstance().createRightComponent(
								new ModDomainGUI((Area) obj));
					} else if (obj instanceof Room) {
						MainGUI.getInstance().createRightComponent(
								new ModDomainGUI((Room) obj));
					} else if (obj instanceof Equipment) {
						MainGUI.getInstance().createRightComponent(
								new ModEquipmentGUI((Equipment) obj));
					}
					break;
				case 1 : 
					if (obj instanceof Equipment){
						MainGUI.getInstance().createRightComponent(
								new ModEquipmentGUI((Equipment) obj));
					}
					break;
				case 2 :
					if (obj instanceof Scenario) {
						MainGUI.getInstance().createRightComponent(
								new ModDomainGUI((Scenario) obj));
					} else if (obj instanceof Task) {
						MainGUI.getInstance().createRightComponent(
								new ModDomainGUI((Task) obj));
					} else if (obj instanceof Action) {
						MainGUI.getInstance().createRightComponent(
								new ModActionGUI((Action) obj));
					} else if (obj instanceof ATrigger) {
						MainGUI.getInstance().createRightComponent(
								new ModTriggerGUI((ATrigger) obj));
					}
					break;
			}
		}
	}
}
