/**
 * 
 */
package example;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.ToolTipManager;

import pojo.domain.Area;
import pojo.domain.DriverIdentifier;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import pojo.pattern.trigger.ATrigger;
import pojo.pattern.trigger.OnceTrigger;
import tree.SmartHouseTreeView;
import tree.model.HomeTreeModel;
import dao.DAO;
import dao.factory.DAOFactory;
import driver.DriverException;
import driver.DriverManager;

/**
 * Réalisée le 30 sept. 2012
 * 
 * 
 * @author Florent
 */
public class HomeTreeViewMainTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4126347162700015306L;

	public HomeTreeViewMainTest () {
		DAO<Home> homeDao = DAOFactory.getFactory().getHomeDAO();
		Collection<Home> list_homes = homeDao.findAll();

		HomeTreeModel model = new HomeTreeModel(new LinkedList(list_homes));
		SmartHouseTreeView view = new SmartHouseTreeView(model);
		ToolTipManager.sharedInstance().registerComponent(view);
		// pour la désactivation
		// ToolTipManager.sharedInstance().unregisterComponent(view);
		getContentPane().add(view);
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createRandomData();
		print();
		 new HomeTreeViewMainTest();
	}

	public static void print() {
		DAOFactory.getFactory().getHomeDAO();
		DAO<Scenario> scenerioDao = DAOFactory.getFactory().getScenarioDAO();

		for ( Scenario sce : scenerioDao.findAll() ) {
			System.out.println(sce);
			for ( Task tsk : sce.getTasks() ) {
				System.out.println(tsk);
				for ( Action act : tsk.getActions() ) {
					System.out.println(act);
				}
				for ( ATrigger trig : tsk.getTriggers() ) {
					System.out.println(trig);
				}
			}
		}
	}

	public static void createRandomData() {
		Random r = new Random();
		DAO<Home> homeDao = DAOFactory.getFactory().getHomeDAO();
		DAO<Scenario> scenerioDao = DAOFactory.getFactory().getScenarioDAO();
		// Creation de la maison
		for ( int x = 0; x < 20; x++ ) {
			Home home = new Home("Home" + r.nextInt());
			for ( int i = 0; i < 3; i++ ) {
				Area area_niv = new Area("area" + r.nextInt(), home);
				for ( int j = 0; j < 3; j++ ) {
					Room room = new Room("room" + r.nextInt(), area_niv);
					for ( int k = 0; k < 4; k++ ) {
						int idlampe = r.nextInt();
						try {
							DriverIdentifier drivId = new DriverIdentifier("uc.equipment.SimulatedLightLapeyreDriver", "LightDriver.jar");
							new Equipment(room, "lampe" + idlampe, drivId, "id=LAMPE" + idlampe);
						} catch (DriverException e) {
							e.printStackTrace();
						}
					}
				}
			}
			homeDao.create(home);
			Equipment eq = home.getAreas().get(0).getRooms().get(0).getEquipments().get(0);
			Method m = DriverManager.getActionsAvailable(eq.getDriver()).get(0);
			for ( int z = 0; z < 3; z++ ) {
				// Creation de scenar
				Scenario scenario = new Scenario("Scenar" + r.nextInt(), home);
				for ( int y = 0; y < 3; y++ ) {
					Task task = new Task("Task" + r.nextInt(), scenario);
					new OnceTrigger(task, Calendar.getInstance().getTime(), 60 * r.nextInt());
					new Action(task, eq, m);
				}
				scenerioDao.create(scenario);
			}

		}
		/*
		 * ArrayList<Home> homes = (homeDao).findAll();
		 * System.out.println("Liste des maisons"); System.out.println(homes);
		 */
	}

}
