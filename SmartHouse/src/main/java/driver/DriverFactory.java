/**
 * 
 */
package driver;

import java.util.Properties;

/**
 * Singleton factory of driver equipment
 * 
 * @author Florent
 * 
 */
public class DriverFactory {

	/**
	 * Instance singleton
	 */
	private static DriverFactory instance;

	private DriverFactory () {
	}

	/**
	 * Singleton access
	 * 
	 * @return
	 */
	public static DriverFactory getInstance() {
		if ( instance == null ) {
			instance = new DriverFactory();
		}

		return instance;
	}

	/**
	 * Renvoi le driver correspondant
	 * 
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public Driver getDriver(String path, Properties prop) throws DriverException {
		Driver instance = null;

		try {
			Class<?> driver = Class.forName(path);
			instance = (Driver) driver.getDeclaredConstructor(Properties.class).newInstance(prop);
		} catch (Exception e) {
			throw new DriverException("Error while instanciating driver " + path + " : " + e.getMessage());
		}

		return instance;
	}
}
