package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;

import manager.SchedulerManager;
import pojo.domain.Home;
import pojo.pattern.Scenario;
import tree.SmartHouseTreeController;
import tree.SmartHouseTreeView;
import tree.model.EquipmentTreeModel;
import tree.model.HomeTreeModel;
import tree.model.ScenarioTreeModel;
import dao.DAO;
import dao.factory.DAOFactory;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static MainGUI instance = null;
	private JSplitPane splitPane = new JSplitPane();
	private SmartHouseTreeController treeControler;
	private JPopupMenu jpm_contextMenu;
	private JMenuItem mui_add;
	private JMenuItem mui_modify;
	private JMenuItem mui_delete;
	private SmartHouseTreeView homesTree;
	private SmartHouseTreeView equipmentsTree;
	private SmartHouseTreeView scenariosTree;
	private JTabbedPane tabbedPane;

	private MainGUI () {
		super();
		initComponents();
		refreshScenario();
	}

	public static MainGUI getInstance() {
		if ( instance == null ) {
			instance = new MainGUI();
		}
		return instance;
	}

	public void removeRightcomponent() {
		homesTree.getModel().valueForPathChanged(null, null);
		equipmentsTree.getModel().valueForPathChanged(null, null);
		scenariosTree.getModel().valueForPathChanged(null, null);
		splitPane.setRightComponent(new JPanel());
		revalidate();
		validate();
		repaint();
	}

	private void initComponents() {
		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);

		splitPane.setRightComponent(new JPanel());
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(275);

		/* home tree start */
		DAO<Home> homeDao = DAOFactory.getFactory().getHomeDAO();
		List<Home> list_homes = homeDao.findAll();
		HomeTreeModel homeModel = new HomeTreeModel(list_homes);
		/* home tree end */

		// onglet 1
		homesTree = new SmartHouseTreeView(homeModel);
		ToolTipManager.sharedInstance().registerComponent(homesTree);
		JScrollPane scrollHomesTree = new JScrollPane(homesTree);
		tabbedPane.addTab("Vue globale", null, scrollHomesTree, null);

		/* equipment tree start */
		EquipmentTreeModel equipmentModel = new EquipmentTreeModel(list_homes);
		/* equipment tree end */

		// onglet 2
		equipmentsTree = new SmartHouseTreeView(equipmentModel);
		ToolTipManager.sharedInstance().registerComponent(homesTree);
		JScrollPane scrollEquipmentsTree = new JScrollPane(equipmentsTree);
		tabbedPane.addTab("Equipements", null, scrollEquipmentsTree, null);

		/* scenario tree start */
		DAO<Scenario> scenarioDao = DAOFactory.getFactory().getScenarioDAO();
		List<Scenario> list_scenarios = scenarioDao.findAll();
		ScenarioTreeModel scenarioModel = new ScenarioTreeModel(list_homes, list_scenarios);
		/* scenario tree end */

		// onglet 3
		scenariosTree = new SmartHouseTreeView(scenarioModel);
		JScrollPane scrollScenariosTree = new JScrollPane(scenariosTree);
		tabbedPane.addTab("Scénarios", null, scrollScenariosTree, null);

		jpm_contextMenu = new JPopupMenu();

		treeControler = new SmartHouseTreeController();

		mui_add = new JMenuItem("Ajouter");
		jpm_contextMenu.add(mui_add);
		mui_add.addActionListener(treeControler);

		mui_modify = new JMenuItem("Modifier");
		jpm_contextMenu.add(mui_modify);
		mui_modify.addActionListener(treeControler);

		mui_delete = new JMenuItem("Supprimer");
		jpm_contextMenu.add(mui_delete);
		mui_delete.addActionListener(treeControler);
		jpm_contextMenu.addMouseListener(treeControler);
		homesTree.addMouseListener(treeControler);
		equipmentsTree.addMouseListener(treeControler);
		scenariosTree.addMouseListener(treeControler);
	}

	public void createRightComponent(Component component) {
		splitPane.setRightComponent(component);
		revalidate();
		validate();
		repaint();
	}
	
	public void refreshScenario(){
		//Chargement des scenarios actives
		DAO<Scenario> scenariodao = DAOFactory.getFactory().getScenarioDAO();
		for (Scenario scenar : scenariodao.findAll()){
			if (scenar.isEnabled()){
				SchedulerManager.getInstance().startScenario(scenar);
			}else{
				SchedulerManager.getInstance().stopScenario(scenar);
			}
				
		}
		
	}

	public JPopupMenu getJpm_contextMenu() {
		return jpm_contextMenu;
	}

	public void setJpm_contextMenu(JPopupMenu jpm_contextMenu) {
		this.jpm_contextMenu = jpm_contextMenu;
	}

	public JMenuItem getMui_add() {
		return mui_add;
	}

	public void setMui_add(JMenuItem mui_add) {
		this.mui_add = mui_add;
	}

	public JMenuItem getMui_modify() {
		return mui_modify;
	}

	public void setMui_modify(JMenuItem mui_modify) {
		this.mui_modify = mui_modify;
	}

	public JMenuItem getMui_delete() {
		return mui_delete;
	}

	public void setMui_delete(JMenuItem mui_delete) {
		this.mui_delete = mui_delete;
	}

	public SmartHouseTreeView getHomesTree() {
		return homesTree;
	}

	public void setHomesTree(SmartHouseTreeView homesTree) {
		this.homesTree = homesTree;
	}

	public SmartHouseTreeView getEquipmentsTree() {
		return equipmentsTree;
	}

	public void setEquipmentsTree(SmartHouseTreeView equipmentsTree) {
		this.equipmentsTree = equipmentsTree;
	}

	public SmartHouseTreeView getScenariosTree() {
		return scenariosTree;
	}

	public void setScenariosTree(SmartHouseTreeView scenariosTree) {
		this.scenariosTree = scenariosTree;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
}
