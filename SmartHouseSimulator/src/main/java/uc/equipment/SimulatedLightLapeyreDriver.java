/**
 * 
 */
package uc.equipment;

import java.util.Properties;

import protocol.request.Request;
import protocol.response.StringResponse;
import simulator.SimulatorManager;
import driver.Driver;
import driver.EquipmentAction;
import driver.EquipmentPropertiesRequired;

/**
 * @author Florent
 * 
 */
@EquipmentPropertiesRequired({ "id" })
public class SimulatedLightLapeyreDriver implements Driver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400954185580926337L;
	private String id;

	public SimulatedLightLapeyreDriver ( Properties params ) {
		this.id = params.getProperty("id");
	}

	public Properties getParams() {
		Properties prop = new Properties();
		prop.put("id", id);
		return prop;
	}

	public String getHardwareIdentifier() {
		return null;
	}

	public String getHardwareName() {
		return "HL454-MLE";
	}

	public String getHardwareDescription() {
		return "Lampe à variation avec fonction couleur ambiance";
	}

	public String getHardwareManufacturer() {
		return "Lapeyre";
	}

	public String getDriverVersion() {
		return "1.0";
	}

	@EquipmentAction(description = "Permet d'allumer la lampe", name = "Allumer", paramsName = { "" })
	public void allumer() {
		try {
			Request req = new Request(id, "ALLUMER", null);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@EquipmentAction(description = "Permet d'eteindre la lampe", name = "Eteindre", paramsName = { "" })
	public void eteindre() {
		try {
			Request req = new Request(id, "ETEINDRE", null);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@EquipmentAction(description = "Faire varier l'intensite de la lampe", name = "Variation", paramsName = { "Nombre flotant" })
	public void varier(Float coeff) {
		try {
			Request req = new Request(id, "VARIER", coeff);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean testEquipment() {
		// c'est tout droit
		return true;
	}

	public String getState() {
		try {
			Request req = new Request(id, "ETAT", null);
			StringResponse response = (StringResponse) SimulatorManager.getInstance().requestEquipment(req);
			return response.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Impossible de récupérer l'état";
	}

}
