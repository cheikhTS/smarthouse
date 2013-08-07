/**
 * 
 */
package simulator.equipment;

import protocol.response.NoResponse;
import protocol.response.Response;
import protocol.response.StringResponse;

/**
 * @author Florent
 * 
 */
public class SimulatedOvenBosch implements SimulatedEquipment {

	private String id;
	private boolean on = false;
	private int temperature = 150;

	public SimulatedOvenBosch ( String id ) {
		this.id = id;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
		System.out.println("SIMULATOR -- SimulatedOvenBosch " + id + " : setOn(" + on + ")");
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
		System.out.println("SIMULATOR -- SimulatedOvenBosch " + id + " : setTemperature(" + temperature + ")");
	}

	public Response request(String action, Object data) {
		if ( action.equals("ETAT") ) {
			return new StringResponse("Etat : " + on);
		} else if ( action.equals("ALLUMER") ) {
			setOn(true);
		} else if ( action.equals("ETEINDRE") ) {
			setOn(false);
		} else if ( action.equals("TEMPERATURE") ) {
			setTemperature((Integer) data);
		}

		return new NoResponse();
	}

	public String getId() {
		return id;
	}

}
