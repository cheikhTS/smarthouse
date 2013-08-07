package smarthouse.fmk.runtime.definition;

import java.io.Serializable;


public class RuntimeActionDef implements Serializable {
	/**
	 * Nom de l'action
	 * !!! Identifiant !!!
	 */
	private String actionName;
	
	/**
	 * Nom IHM de l'action
	 */
	private String name;
	
	/**
	 * Descriptio IHM de l'action
	 */
	private String description;
	
	/**
	 * Paramètres de l'action
	 */
	private RuntimeParamDef[] params;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RuntimeParamDef[] getParams() {
		return params;
	}

	public void setParams(RuntimeParamDef[] params) {
		this.params = params;
	}
	
	public void setParam (RuntimeParamDef param, int i) {
		params[i] = param;
	}
	
	
}
