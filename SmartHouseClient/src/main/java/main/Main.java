package main;

import gui.MainGUI;

import javax.swing.UIManager;

import manager.ActionExecutor;
import manager.SchedulerManager;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import simulator.SimulatorManager;
import simulator.equipment.SimulatedLightLapeyre;
import simulator.equipment.SimulatedOvenBosch;

public class Main {
	/**
	 * 
	 */
	private static final String PATH_LOG4J = "/log4j.xml";
	private static final Logger logger = Logger.getLogger(Main.class);
	static {
		try {
			UIManager.setLookAndFeel("net.sf.nimrod.NimRODLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			DOMConfigurator.configure(Main.class.getResource(PATH_LOG4J));
		} catch (Exception e) {
			System.err.println("Fichier des parametres de journalisation introuvable (/log4j.xml)");
		}
		logger.debug("Fichier des parametres de journalisation charge");

		logger.debug("Chargement des equipements simules");
		// Chargement des equipments
		SimulatorManager simulator = SimulatorManager.getInstance();
		SimulatedLightLapeyre lampe1 = new SimulatedLightLapeyre("LAMPE1");
		SimulatedLightLapeyre lampe2 = new SimulatedLightLapeyre("LAMPE2");
		SimulatedOvenBosch four1 = new SimulatedOvenBosch("FOUR1");

		// enregistrement des équipements simulés
		simulator.registerEquipement(lampe1);
		simulator.registerEquipement(lampe2);
		simulator.registerEquipement(four1);
	}

	public static void main(String[] args) {
		// lancement de la fenetre d'identification
		// login = new LoginGUI();
		MainGUI.getInstance();
		
		
		
		
		// hooks de fermeture propre
		Runtime.getRuntime().addShutdownHook(new Thread("ShutdownHook") {
			@Override
			public void run() {
				setName("Shutdown Hook");
				ActionExecutor.getInstance().interrupt();
				SchedulerManager.getInstance().closeTimers();
			}
		});
	}
}
