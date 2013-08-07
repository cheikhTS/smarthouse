/**
 * 
 */
package simulator;

import java.util.HashSet;
import java.util.Set;

import protocol.request.Request;
import protocol.response.Response;
import simulator.equipment.SimulatedEquipment;

/**
 * @author Florent
 * 
 */
public class SimulatorManager {

	/**
	 * Liste des équipements simulés gérés
	 */
	private Set<SimulatedEquipment> listEquipments;

	private static SimulatorManager instance;

	public SimulatorManager ( int nbEquipments ) {
		listEquipments = new HashSet<SimulatedEquipment>(nbEquipments);
		// pool = Executors.newFixedThreadPool(nbEquipments);
	}

	public static SimulatorManager getInstance() {
		if ( instance == null ) {
			// c'est con
			instance = new SimulatorManager(10);
		}

		return instance;
	}

	/**
	 * Enregistrer un équipement dans le gestionnaire
	 * 
	 * @param eqp
	 */
	public void registerEquipement(SimulatedEquipment eqp) {
		listEquipments.add(eqp);

		/*
		 * if(started) { pool.execute(eqp); }
		 */
	}

	public Response requestEquipment(Request req) {
		for ( SimulatedEquipment eqp : listEquipments ) {
			if ( eqp.getId().equals(req.getIdDestination()) ) { return eqp.request(req.getAction(), req.getData()); }
		}

		if ( req.getIdDestination() == null ) {
			System.err.println("Request invalide : destination null");
		} else {
			System.err.println("Equipement " + req.getIdDestination() + " introuvable");
		}

		return null;
	}

	/**
	 * On démarrer les threads de simulation de chaque équipement enregistré
	 */
	/*
	 * public void start() { started = true;
	 * 
	 * for(SimulatedEquipment eqp : listEquipments) { pool.execute(eqp); } }
	 */
}
