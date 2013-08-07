/**
 * 
 */
package smarthouse.fmk.driver.impl;

import java.util.Properties;

import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.driver.definition.DefAction;
import smarthouse.fmk.driver.definition.DefParam;
import smarthouse.fmk.driver.definition.DefPropertiesRequired;
import smarthouse.fmk.driver.definition.types.DefTypeLimitedFloat;
import smarthouse.fmk.driver.definition.types.ParamType;

/**
 * @author Florent
 * 
 */
@DefPropertiesRequired({ "id" })
public class SimulatedLightLapeyreDriver implements Driver {

	private static final long serialVersionUID = -1400954185580926337L;
	private String id;

	public SimulatedLightLapeyreDriver ( Properties params ) {
		this.id = params.getProperty("id");
	}

	@Override
	public Properties getParams() {
		Properties prop = new Properties();
		prop.put("id", id);
		return prop;
	}

	@Override
	public String getHardwareIdentifier() throws DriverException {
		return null;
	}

	@Override
	public String getHardwareName() {
		return "HL454-MLE";
	}

	@Override
	public String getHardwareDescription() {
		return "Lampe à variation avec fonction couleur ambiance";
	}

	@Override
	public String getHardwareManufacturer() {
		return "Lapeyre";
	}

	@Override
	public String getDriverVersion() {
		return "1.0";
	}

	@DefAction(description = "Permet d'allumer la lampe", name = "Allumer")
	public void allumer() {
		System.out.println("allumer()");
	}
	
	@DefAction(description = "Permet d'eteindre la lampe", name = "Eteindre")
	public void eteindre() {
		System.out.println("eteindre()");
	}

	@DefAction(description = "Faire varier l'intensite de la lampe", name = "Variation")
	public void varier(
		@DefParam(name="Intensité", description="Variation de l'intensité", type=ParamType.LIMITED_FLOAT)
		@DefTypeLimitedFloat(min=0, max=100, step=0.5f)
		Float coeff) 
	{
		System.out.println("varier(Float)");
	}

	@Override
	public boolean testEquipment() throws DriverException {
		// c'est tout droit
		return true;
	}

	@Override
	public String getState() throws DriverException {
		System.out.println("getState()");
		return "Impossible de récupérer l'état";
	}

}
