/**
 * 
 */
package uc.equipment;

import java.util.Properties;

import protocol.request.Request;
import protocol.response.BooleanResponse;
import protocol.response.Response;
import protocol.response.StringResponse;
import simulator.SimulatorManager;
import driver.Driver;
import driver.DriverException;
import driver.EquipmentAction;
import driver.EquipmentPropertiesRequired;

/**
 * @author Florent
 * 
 */
@EquipmentPropertiesRequired({ "addr_ip" })
public class SimulatedOvenBoschDriver implements Driver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5588439557808955739L;
	private String ip;

	public SimulatedOvenBoschDriver ( Properties params ) {
		this.ip = params.getProperty("addr_ip");
	}

	public Properties getParams() {
		Properties prop = new Properties();
		prop.put("addr_ip", ip);
		return prop;
	}

	public String getHardwareIdentifier() {
		return null;
	}

	public String getHardwareName() {
		return "O451";
	}

	public String getHardwareDescription() {
		return "Four à chaleur tournante allemand";
	}

	public String getHardwareManufacturer() {
		return "Bosch";
	}

	public String getDriverVersion() {
		return "1.0";
	}

	@EquipmentAction(description = "Permet d'allumer le four", name = "Allumer", paramsName = { "" })
	public void allumer() {
		try {
			Request req = new Request(ip, "ALLUMER", null);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EquipmentAction(description = "Permet d'éteindre le four", name = "Eteindre", paramsName = { "" })
	public void eteindre() {
		try {
			Request req = new Request(ip, "ETEINDRE", null);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getState() {
		try {
			Request req = new Request(ip, "ETAT", null);
			StringResponse response = (StringResponse) SimulatorManager.getInstance().requestEquipment(req);
			return response.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Impossible de récupérer l'état";
	}

	@EquipmentAction(description = "Paramétrer la température du four", name = "Variation", paramsName = { "Température en degrée celcius" })
	public void temperature(Integer tmp) throws DriverException {
		if ( tmp < 50 || tmp > 300 ) { throw new DriverException("Température entre 50°c et 300°c requise."); }

		try {
			Request req = new Request(ip, "TEMPERATURE", tmp);
			SimulatorManager.getInstance().requestEquipment(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EquipmentAction(description = "Action de test d'équipement", name = "Test", paramsName = { "" })
	public boolean testEquipment() {
		try {
			Request req = new Request(ip, "TEST", null);
			Response res = SimulatorManager.getInstance().requestEquipment(req);
			return ((BooleanResponse) res).getResponse();
		} catch (Exception e) {
			return false;
		}
	}

}
