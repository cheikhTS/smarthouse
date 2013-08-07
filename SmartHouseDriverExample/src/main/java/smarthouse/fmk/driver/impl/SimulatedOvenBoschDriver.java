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
import smarthouse.fmk.driver.definition.types.DefTypeLimitedInteger;
import smarthouse.fmk.driver.definition.types.DefTypeLimitedString;
import smarthouse.fmk.driver.definition.types.DefTypeMultiValues;
import smarthouse.fmk.driver.definition.types.ParamType;

/**
 * @author Florent
 * 
 */
@DefPropertiesRequired({ "addr_ip" })
public class SimulatedOvenBoschDriver implements Driver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5588439557808955739L;
	private String ip;

	public SimulatedOvenBoschDriver ( Properties params ) {
		this.ip = params.getProperty("addr_ip");
	}

	@Override
	public Properties getParams() {
		Properties prop = new Properties();
		prop.put("addr_ip", ip);
		return prop;
	}

	@Override
	public String getHardwareIdentifier() throws DriverException {
		return null;
	}

	@Override
	public String getHardwareName() {
		return "O451";
	}

	@Override
	public String getHardwareDescription() {
		return "Four à chaleur tournante allemand";
	}

	@Override
	public String getHardwareManufacturer() {
		return "Bosch";
	}

	@Override
	public String getDriverVersion() {
		return "1.0";
	}

	@DefAction(description = "Permet d'allumer le four", name = "Allumer")
	public void allumer() {
		System.out.println("allumer()");
	}

	@DefAction(description = "Permet d'éteindre le four", name = "Eteindre")
	public void eteindre() {
		System.out.println("eteindre()");
	}

	@Override
	public String getState() throws DriverException {
		System.out.println("getState()");
		return "Impossible de récupérer l'état";
	}

	@DefAction(description = "Paramétrer la température du four", name = "Variation")
	public void temperature(
		@DefParam(name = "Température", description = "Température en degrée celcius", type = ParamType.LIMITED_INTEGER) 
		@DefTypeLimitedInteger(min = 50, max = 300)
		Integer tmp
	) throws DriverException 
	{
		System.out.println("temperature("+tmp+")");
		if ( tmp < 50 || tmp > 300 ) { throw new DriverException("Température entre 50°c et 300°c requise."); }
	}
	
	@DefAction(description = "Changez votre mode de cuisson", name="Changement de mode")
	public void switchMode(
		@DefParam(name = "Mode", description="Mode de cuisson", type=ParamType.MULTI_VALUES)
		@DefTypeMultiValues(
			labels = {"Chaleur tournante", "Cuisson de merde", "Nettoyage pyrolyse rapide", "Nettoyage pyrolyse lent"},
			values = {"CT", "CM", "NPR", "NPL"})
		String mode
	) throws DriverException 
	{
		System.out.println("switchMode("+mode+")");
	}
	
	@DefAction(description = "Diffuse un message en défilement", name="Afficher un message")
	public void afficherMessage(
		@DefParam(name = "Message", description="Message diffusé en défilement", type=ParamType.LIMITED_STRING)
		@DefTypeLimitedString(minSize=1, maxSize=150)
		String msg,
		@DefParam(name = "Vitesse défilement", description="Vitesse de défilement du message diffusé", type=ParamType.LIMITED_INTEGER)
		@DefTypeLimitedInteger(min=1, max=5)
		Integer speed
	) throws DriverException 
	{
		System.out.println("afficherMessage("+msg+", "+speed+")");
	}
	
	@Override
	@DefAction(name = "Test de l'équipement", description = "Permet de contrôler la configuration d'un équipement.")
	public boolean testEquipment()throws DriverException {
		System.out.println("testEquipment()");
		return true;
	}

}
