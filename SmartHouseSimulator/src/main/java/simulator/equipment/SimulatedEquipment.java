package simulator.equipment;

import protocol.response.Response;

public interface SimulatedEquipment {
	public String getId();

	public Response request(String action, Object data);
}
