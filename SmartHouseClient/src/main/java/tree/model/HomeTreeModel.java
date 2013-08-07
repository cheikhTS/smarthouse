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

public class HomeTreeModel implements TreeModel {

	private String root = "Système SmartHouse";
	private List<Home> list = null;

	public HomeTreeModel ( List<Home> list_homes ) {
		this.list = list_homes;
	}

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		if ( getRoot().equals(parent) ) {
			return list.get(index);
		} else if ( parent instanceof Home ) {
			return ((Home) parent).getAreas().get(index);
		} else if ( parent instanceof Area ) {
			return ((Area) parent).getRooms().get(index);
		} else if ( parent instanceof Room ) { return ((Room) parent).getEquipments().get(index); }

		return null;
	}

	public int getChildCount(Object parent) {
		if ( getRoot().equals(parent) ) {
			return list.size();
		} else if ( parent instanceof Home ) {
			return ((Home) parent).getAreas().size();
		} else if ( parent instanceof Area ) {
			return ((Area) parent).getRooms().size();
		} else if ( parent instanceof Room ) { return ((Room) parent).getEquipments().size(); }

		return 0;
	}

	public boolean isLeaf(Object node) {
		if ( getRoot().equals(node) ) {
			return list.isEmpty();
		} else if ( node instanceof Home ) {
			return ((Home) node).getAreas().isEmpty();
		} else if ( node instanceof Area ) {
			return ((Area) node).getRooms().isEmpty();
		} else if ( node instanceof Room ) {
			return ((Room) node).getEquipments().isEmpty();
		} else if ( node instanceof Equipment ) { return true; }

		return true;
	}

	public int getIndexOfChild(Object parent, Object child) {
		if ( getRoot().equals(parent) ) {
			return list.indexOf(child);
		} else if ( parent instanceof Home ) {
			return ((Home) parent).getAreas().indexOf(child);
		} else if ( parent instanceof Area ) {
			return ((Area) parent).getRooms().indexOf(child);
		} else if ( parent instanceof Room ) { return ((Room) parent).getEquipments().indexOf(child); }

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
