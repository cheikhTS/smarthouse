package smarthouse.fmk.runtime.definition;

import java.io.Serializable;


public class RuntimeEquipmentInfos implements Serializable {
	

	/**
	 * Identifiant de l'équipement (dynamic data)
	 */
	private int id;
	
	/**
	 * Nom de l'équipement (dynamic data)
	 */
	private String name;
	
	/**
	 * Configuration de l'équipement (dynamic data)
	 * Properties serialisé de la forme key1=value1;key2=value2;
	 */
	private String config;
	
	/**
	 * Etat de l'équipement (dynamic data)
	 */
	private String state;
	
	/**
	 * Identifiant de l'équipement (dynamic data)
	 */
	private String hardwareIdentifier;
	
	/**
	 * Actions disponibles sur l'équipement (static data)
	 */
	private RuntimeActionDef[] actions;

	/**
	 * Nom de l'équipement correspondant au driver (static data)
	 */
	private String hardwareName;

	/**
	 * Description du driver (static data)
	 */
	private String hardwareDescription;

	/**
	 * Fabricant du matériel (static data)
	 */
	private String hardwareManufacturer;

	/**
	 * Version du driver (static data)
	 */
	private String driverVersion;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getHardwareIdentifier() {
		return hardwareIdentifier;
	}

	public void setHardwareIdentifier(String hardwareIdentifier) {
		this.hardwareIdentifier = hardwareIdentifier;
	}

	public RuntimeActionDef[] getActions() {
		return actions;
	}

	public void setActions(RuntimeActionDef[] actions) {
		this.actions = actions;
	}

	public String getHardwareName() {
		return hardwareName;
	}

	public void setHardwareName(String hardwareName) {
		this.hardwareName = hardwareName;
	}

	public String getHardwareDescription() {
		return hardwareDescription;
	}

	public void setHardwareDescription(String hardwareDescription) {
		this.hardwareDescription = hardwareDescription;
	}

	public String getHardwareManufacturer() {
		return hardwareManufacturer;
	}

	public void setHardwareManufacturer(String hardwareManufacturer) {
		this.hardwareManufacturer = hardwareManufacturer;
	}

	public String getDriverVersion() {
		return driverVersion;
	}

	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
}
