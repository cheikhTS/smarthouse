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
public class SimulatedRadiator implements SimulatedEquipment {

	private String id;
	private boolean on = false;
	private float power = 0.0f;

	public SimulatedRadiator ( String  id ) {
		this.id = id;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
		System.out.println("SIMULATOR -- SimulatedRadiator " + id + " : setOn(" + on + ")");
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
		System.out.println("SIMULATOR -- SimulatedRadiator " + id + " : setPower(" + power + ")");
	}

	public Response request(String action, Object data) {
		if ( action.equals("ETAT") ) {
			return new StringResponse("Etat : " + on + " Puissance : " + ((int)power*100) + "%");
		} else if ( action.equals("ALLUMER") ) {
			setOn(true);
		} else if ( action.equals("ETEINDRE") ) {
			setOn(false);
		} else if ( action.equals("VARIER") ) {
			setPower(((Float) data).floatValue());
		}

		return new NoResponse();
	}

	public String getId() {
		return id;
	}

}
