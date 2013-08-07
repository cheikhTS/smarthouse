package smarthouse.fmk.runtime.pilot;

import java.io.Serializable;
import java.util.List;

public class ActionRequest implements Serializable {

	private static final long serialVersionUID = -8846774975408116228L;

	/**
	 * Identifiant de l'équipement concerné
	 */
	private int idEquipment;
	
	/**
	 * Nom de l'action
	 */
	private String action;
	
	/**
	 * Paramètres d'appel
	 */
	private List<String> params;
	
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder("Equipement="+idEquipment+", action="+action+", params=[");
		if(params != null) {
			for(String p : params) {
				build.append(p+", ");
			}
		}
		build.append("]");
		
		return build.toString();
	}

	public int getIdEquipment() {
		return idEquipment;
	}

	public void setIdEquipment(int idEquipment) {
		this.idEquipment = idEquipment;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}
	
}
