package smarthouse.ejb.service;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.generator.ExampleDataGenerator;
import smarthouse.ejb.service.remote.Development;
import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.runtime.DriverFactory;
import smarthouse.fmk.runtime.DriverFactoryException;
import smarthouse.fmk.runtime.RuntimeDriverUtil;
import smarthouse.fmk.runtime.definition.RuntimeEquipmentInfos;

@Stateless
@WebService(name = "Development")
public class DevelopmentBean implements Development {

	private Logger logger = LoggerFactory.getLogger(DevelopmentBean.class);
	
	@PersistenceContext
	private EntityManager em;

	@WebMethod
	public void generateExampleData() {
		ExampleDataGenerator.createRandomData();
	}
	
	@WebMethod
	public void driverFactoryLoading() {
		try {
			DriverFactory.getInstance().loadDrivers();
		} catch (DriverFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public RuntimeEquipmentInfos testMakeEquipmentInfos(int idEqp) throws Exception {
		Equipment eqp = DAOFactory.getFactory().getEquipmentDAO().find(idEqp);
		return RuntimeDriverUtil.makeRuntimeEquipmentInfos(eqp);
	}
	
	@WebMethod
	public void toggleLight() throws Exception {
		Equipment eqp = DAOFactory.getFactory().getEquipmentDAO().find(1);
		
		logger.info("State light : "+eqp.getDriver().getState());
		if("KO".equals(eqp.getDriver().getState())) {
			eqp.getDriver().getClass().getMethod("allumer").invoke(eqp.getDriver());
		}
		else {
			eqp.getDriver().getClass().getMethod("eteindre").invoke(eqp.getDriver());
		}
	}
	
	@WebMethod
	public void testLight() throws Exception {
		Equipment eqp = DAOFactory.getFactory().getEquipmentDAO().find(1);
		eqp.getDriver().testEquipment();
	}
	
	/*@WebMethod
	public void testPi4j() throws Exception {
		// Controller
		GpioController gpio = GpioFactory.getInstance();
		
		// GPIO Output mode and off
		GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "TEST", PinState.LOW);
		
		pin.toggle();
		
		Thread.sleep(500);
		
		pin.toggle();
		
		gpio.shutdown();
	}*/

	@WebMethod
	public void loadJar() {
		File jarsDir = new File("C:\\SmartHouse\\");
		
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(".jar");
			}
		};
		
		// récupération des jars à traiter
		File[] jars = jarsDir.listFiles(filter);
		
		// initialisation du tableau d'extension de notre classloader
		URL[] referencedJars = new URL[jars.length];
		for(int i = 0; i < jars.length; i++) {
			try {
				// conversion un par un en URL.
				referencedJars[i] = jars[i].toURI().toURL();
			} 
			catch (MalformedURLException e) {
				referencedJars[i] = null;
			}
		}
		
		// création d'un classloader étendu
		URLClassLoader loader = new URLClassLoader(referencedJars, getClass().getClassLoader());
		
		try {
			Class<?> driverClass = Class.forName("smarthouse.fmk.driver.impl.SimulatedLightLapeyreDriver", true, loader);
			System.out.println("Driver loaded.");
			
			Constructor<?> driverConstructor = driverClass.getConstructor(Properties.class);
			Driver driver = (Driver) driverConstructor.newInstance(new Properties());
			System.out.println("Driver test : "+driver.getHardwareName());
			
			driverClass = Class.forName("smarthouse.fmk.driver.impl.SimulatedOvenBoschDriver", true, loader);
			System.out.println("Driver loaded.");
			
			driverConstructor = driverClass.getConstructor(Properties.class);
			driver = (Driver) driverConstructor.newInstance(new Properties());
			System.out.println("Driver test : "+driver.getHardwareName());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
		/* variables definition */
		/*final URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		final Class<?> sysClass = URLClassLoader.class;
		final Class<?>[] PARAMS = new Class<?>[] { URL.class };

		try {
			final Method myMethod = sysClass.getDeclaredMethod("addURL", PARAMS);
			myMethod.setAccessible(true);
			myMethod.invoke(sysLoader, new Object[] { driversDirectory.toURI().toURL() });
		} catch (final Exception exc) {
			exc.printStackTrace();
		}*/
		

	}
}
