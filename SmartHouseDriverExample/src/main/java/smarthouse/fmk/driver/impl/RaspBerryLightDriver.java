/**
 * 
 */
package smarthouse.fmk.driver.impl;

import java.util.Properties;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.driver.definition.DefAction;

/**
 * @author Flo
 *
 */
public class RaspBerryLightDriver implements Driver {

	private GpioController gpio = null;
	private GpioPinDigitalOutput pin = null;
	
	public RaspBerryLightDriver(Properties props) {
		// Controller
		gpio = GpioFactory.getInstance();
		
		// GPIO Output mode and off
		pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, getHardwareName(), PinState.LOW);
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(gpio != null) {
			pin.unexport();
			gpio.shutdown();
		}
		
		super.finalize();
	}
	
	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getParams()
	 */
	@Override
	public Properties getParams() {
		return new Properties();
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getHardwareIdentifier()
	 */
	@Override
	public String getHardwareIdentifier() throws DriverException {
		return "Not identified";
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getHardwareName()
	 */
	@Override
	public String getHardwareName() {
		return "Eclairage 3V";
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getHardwareDescription()
	 */
	@Override
	public String getHardwareDescription() {
		return "Eclairage simple sur une alimentation continue de 3 volts.";
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getHardwareManufacturer()
	 */
	@Override
	public String getHardwareManufacturer() {
		return "SmartHouse";
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getDriverVersion()
	 */
	@Override
	public String getDriverVersion() {
		return "1.0";
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#getState()
	 */
	@Override
	public String getState() throws DriverException {
		try {
			PinState state = pin.getState();
			return state.isHigh() ? "OK" : "KO";
		}
		catch(Exception e) {
			return "KO";
		}
	}

	/* (non-Javadoc)
	 * @see smarthouse.fmk.driver.Driver#testEquipment()
	 */
	@Override
	@DefAction(name = "Test de l'équipement", description = "Permet de contrôler la configuration d'un équipement.")
	public boolean testEquipment() throws DriverException {
		try {
			pin.blink(400, 2000);
			return true;
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	@DefAction(name = "Allumer", description = "Permet d'allumer l'éclairage.")
	public void allumer() throws DriverException {
		pin.high();
	}
	
	@DefAction(name = "Eteindre", description = "Permet d'arrêter l'éclairage.")
	public void eteindre() throws DriverException {
		pin.low();
	}
	
}
