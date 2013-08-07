package smarthouse.ejb.generator;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Random;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import smarthouse.ejb.dao.DAO;
import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.DriverIdentifier;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.ejb.entity.pattern.Action;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.ejb.entity.pattern.trigger.ATrigger;
import smarthouse.ejb.entity.pattern.trigger.OnceTrigger;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.runtime.DriverFactoryException;
import smarthouse.fmk.runtime.RuntimeDriverUtil;

/**
 * Réalisée le 30 sept. 2012
 * 
 * 
 * @author Florent
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ExampleDataGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createRandomData();
		print();
	}

	public static void print() {
		DAOFactory.getFactory().getHomeDAO();
		DAO<Scenario> scenerioDao = DAOFactory.getFactory().getScenarioDAO();

		for (Scenario sce : scenerioDao.findAll()) {
			System.out.println(sce);
			for (Task tsk : sce.getTasks()) {
				System.out.println(tsk);
				for (Action act : tsk.getActions()) {
					System.out.println(act);
				}
				for (ATrigger trig : tsk.getTriggers()) {
					System.out.println(trig);
				}
			}
		}
	}

	public static void createRandomData() {
		Random r = new Random();
		DAO<Home> homeDao = DAOFactory.getFactory().getHomeDAO();
		
		DriverIdentifier drivId1 = new DriverIdentifier(
			"smarthouse.fmk.driver.impl.SimulatedLightLapeyreDriver",
			"BundleDrivers.jar");
		DriverIdentifier drivId2 = new DriverIdentifier(
			"smarthouse.fmk.driver.impl.SimulatedOvenBoschDriver",
			"BundleDrivers.jar");
		
		// Creation de la maison
		Home home = new Home(1, "Home" + r.nextInt());
		for (int i = 0; i < 3; i++) {
			Area area_niv = new Area("area" + r.nextInt(), home);
			for (int j = 0; j < 3; j++) {
				Room room = new Room("room" + r.nextInt(), area_niv);
				
				int idEquipment;
				try {
					idEquipment = r.nextInt();
					new Equipment(room, "lampe" + idEquipment, drivId1, "id=LAMPE" + idEquipment);
					
					idEquipment = r.nextInt();
					new Equipment(room, "lampe" + idEquipment, drivId1, "id=LAMPE" + idEquipment);
					
					idEquipment = r.nextInt();
					new Equipment(room, "Four" + idEquipment, drivId2, "addr_ip=FOUR" + idEquipment);
				} 
				catch (DriverException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (DriverFactoryException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		homeDao.create(home);
		
		//DAO<Scenario> scenerioDao = DAOFactory.getFactory().getScenarioDAO();
		/*Equipment eq = home.getAreas().get(0).getRooms().get(0).getEquipments().get(0);
		Method m = DriverManager.getActionsAvailable(eq.getDriver()).get(0);
		for (int z = 0; z < 3; z++) {
			// Creation de scenar
			Scenario scenario = new Scenario("Scenar" + r.nextInt(), home);
			for (int y = 0; y < 3; y++) {
				Task task = new Task("Task" + r.nextInt(), scenario);
				new OnceTrigger(task, Calendar.getInstance().getTime(),
						60 * r.nextInt());
				new Action(task, eq, m);
			}
			scenerioDao.create(scenario);
		}*/

	}

}
