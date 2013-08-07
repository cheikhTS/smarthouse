package main;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import pojo.domain.Area;
import pojo.domain.DriverIdentifier;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Action;
import simulator.SimulatorManager;
import simulator.equipment.SimulatedLightLapeyre;
import simulator.equipment.SimulatedOvenBosch;
import simulator.equipment.SimulatedRadiator;
import driver.DriverFactory;
import driver.DriverManager;

public class Main {
	public static void main(String[] args) {
		DOMConfigurator.configure(LogExample.class.getResource("/log4j.xml"));
		SimulatorManager simulator = SimulatorManager.getInstance();

		// equipements simulés
		SimulatedLightLapeyre lampe1 = new SimulatedLightLapeyre("LAMPE1");
		SimulatedLightLapeyre lampe2 = new SimulatedLightLapeyre("LAMPE2");
		SimulatedOvenBosch four1 = new SimulatedOvenBosch("FOUR");
		SimulatedRadiator radiateur1 = new SimulatedRadiator("RADIA");

		// enregistrement des équipements simulés
		simulator.registerEquipement(lampe1);
		simulator.registerEquipement(lampe2);
		simulator.registerEquipement(four1);
		simulator.registerEquipement(radiateur1);

		DriverFactory.getInstance();
		try {
			Home home = new Home("Maison principale");
			Area area = new Area("Rez-de-chaussée", home);
			Room room = new Room("Cuisine", area);

			Properties prop = new Properties();
			prop.setProperty("id", "LAMPE1");
			DriverIdentifier drvID = new DriverIdentifier("uc.equipment.SimulatedLightLapeyreDriver", "LightDriver.jar");
			Equipment light1 = new Equipment(room, "Lampe 1", drvID, prop);

			System.out.println("----------------------------------------------");
			System.out.println("Simulation d'action sur les équipements");
			System.out.println("----------------------------------------------");

			// light1.allumer();
			Action startLight1 = light1.makeAction("allumer");
			System.out.println("UC -- startLight1.run()");
			startLight1.run();

			System.out.println("----------------------------------------------");
			System.out.println("Auto-détection des méthodes d'action du driver par annotation");
			System.out.println("----------------------------------------------");
			List<Method> listActions = DriverManager.getActionsAvailable(light1.getDriver());
			for ( Method m : listActions ) {
				System.out.println("Action disponible : " + m);
			}

			System.out.println("----------------------------------------------");
			System.out.println("Auto-description du properties d'instanciation du driver par annotation");
			System.out.println("----------------------------------------------");
			List<String> listPropertiesRequired = DriverManager.getPropertiesRequired(Class.forName("uc.equipment.SimulatedOvenBoschDriver"));
			for ( String m : listPropertiesRequired ) {
				System.out.println("Property obligatoire : " + m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
