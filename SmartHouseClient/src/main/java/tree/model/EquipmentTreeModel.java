package tree.model;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;

public class EquipmentTreeModel implements TreeModel {

	private String root = "Système SmartHouse";
	private List<Home> list = null;

	public EquipmentTreeModel ( List<Home> list_homes ) {
		this.list = list_homes;
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		if ( getRoot().equals(parent) ) {
			return list.get(index);
		} else if ( parent instanceof Home ) {
			int currentIndex = index;

			for ( Area a : ((Home) parent).getAreas() ) {
				for ( Room r : a.getRooms() ) {
					if ( currentIndex >= r.getEquipments().size() ) {
						currentIndex = currentIndex - r.getEquipments().size();
					} else {
						return r.getEquipments().get(currentIndex);
					}
				}
			}
		}

		return null;
	}

	public int getChildCount(Object parent) {
		if ( getRoot().equals(parent) ) {
			return list.size();
		} else if ( parent instanceof Home ) {
			int indexGlobal = 0;

			for ( Area a : ((Home) parent).getAreas() ) {
				for ( Room r : a.getRooms() ) {
					indexGlobal += r.getEquipments().size();
				}
			}
			return indexGlobal;
		}

		return 0;
	}

	public boolean isLeaf(Object node) {
		if ( getRoot().equals(node) ) {
			return list.isEmpty();
		} else if ( node instanceof Home ) {
			for ( Area a : ((Home) node).getAreas() ) {
				for ( Room r : a.getRooms() ) {
					if ( r.getEquipments().size() > 0 ) return false;
				}
			}
			return true;
		} else if ( node instanceof Equipment ) { return true; }

		return true;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if ( getRoot().equals(parent) ) {
			return list.indexOf(child);
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
