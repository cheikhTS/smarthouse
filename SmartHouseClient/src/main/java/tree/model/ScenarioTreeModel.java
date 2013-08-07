package tree.model;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import pojo.domain.Area;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import pojo.pattern.trigger.ATrigger;

public class ScenarioTreeModel implements TreeModel {

	private String root = "Système SmartHouse";
	private List<Home> listHomes = null;
	private List<Scenario> listScenarios = null;

	public ScenarioTreeModel ( List<Home> list_homes, List<Scenario> list_scenarios ) {
		this.listHomes = list_homes;
		this.listScenarios = list_scenarios;
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		if ( getRoot().equals(parent) ) {
			return listHomes.get(index);
		} else if ( parent instanceof Home ) {
			int count = 0;

			for ( Scenario s : listScenarios ) {
				if ( s.getHome().equals(parent) ) {
					if ( count == index ) {
						return s;
					} else {
						count++;
					}
				}
			}
		} else if ( parent instanceof Scenario ) {
			return ((Scenario) parent).getTasks().get(index);
		} else if ( parent instanceof Task ) {
			Task task = (Task) parent;
			if ( index < task.getActions().size() ) {
				return task.getActions().get(index);
			} else {
				return task.getTriggers().get(index - task.getActions().size());
			}
		} else if ( parent instanceof Action ) {

		} else if ( parent instanceof ATrigger ) {

		}

		return null;
	}

	public int getChildCount(Object parent) {
		if ( getRoot().equals(parent) ) {
			return listHomes.size();
		} else if ( parent instanceof Home ) {
			int count = 0;

			for ( Scenario s : listScenarios ) {
				if ( s.getHome().equals(parent) ) {
					count++;
				}
			}

			return count;
		} else if ( parent instanceof Scenario ) {
			return ((Scenario) parent).getTasks().size();
		} else if ( parent instanceof Task ) {
			return ((Task) parent).getActions().size() + ((Task) parent).getTriggers().size();
		} else if ( parent instanceof Action ) {

		} else if ( parent instanceof ATrigger ) {

		}

		return 0;
	}

	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if ( getRoot().equals(parent) ) {
			return listHomes.indexOf(child);
		} else if ( parent instanceof Home ) {

			int indexGlobal = 0;
			for ( Area a : ((Home) parent).getAreas() ) {
				for ( Room r : a.getRooms() ) {
					int index = r.getEquipments().indexOf(child);

					if ( index != -1 ) {
						return indexGlobal + index;
					} else {
						indexGlobal += r.getEquipments().size();
					}
				}
			}
		}

		return 0;
	}

	private List<TreeModelListener> listTree = new LinkedList<TreeModelListener>();

	public void valueForPathChanged(TreePath path, Object newValue) {
		TreeModelEvent event = new TreeModelEvent(this, new TreePath(root));
		for ( TreeModelListener listener : listTree ) {
			listener.treeStructureChanged(event);
		}
	}

	public void addTreeModelListener(TreeModelListener l) {
		listTree.add(l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
		listTree.remove(l);
	}

}
