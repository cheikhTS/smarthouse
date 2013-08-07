package pojo.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import driver.Driver;
import driver.DriverException;
import driver.DriverFactory;
import driver.DriverManager;

import pojo.pattern.Action;
import util.ConvertTools;
import util.ObjectSerialization;
import util.PropertiesSerialization;
import static javax.persistence.CascadeType.PERSIST;

/**
 * The persistent class for the equipment database table.
 * 
 */
@Entity
public class Equipment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String properties;
	
	/**
	 * Instance du driver pointant sur l'équipement distant Ce driver est
	 * l'implémentation du protocol de communication entre l'équipement et l'UC
	 */
	private transient Driver driver;

	@ManyToOne(cascade = PERSIST)
	@JoinColumn(name = "idDriver", nullable=false)
	private DriverIdentifier driverIdentifier;

	// bi-directional many-to-one association to Room
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idRoom", nullable=false)
	private Room room;

	public Equipment () {
	}
	
	public Equipment (Room _room, String name, DriverIdentifier _driverIdentifier, Properties properties ) throws DriverException {
		this.name = name;
		driverIdentifier = _driverIdentifier;
		this.properties = ConvertTools.properties2Str(properties);
		room = _room;
		loadDriver();
		room.getEquipments().add(this);
	}
	
	public Equipment (Room _room, String name, DriverIdentifier _driverIdentifier, String ... _properties ) throws DriverException {
		this.name = name;
		driverIdentifier = _driverIdentifier;
		//conversion
		StringBuilder str = new StringBuilder();
		for (String s : _properties){
			str.append(s+";");
		}
		this.properties = str.toString();
		room = _room;
		loadDriver();
		room.getEquipments().add(this);
	}

	/**
	 * Fabrique d'une action pour l'instance d'équipement lié
	 * 
	 * @param method
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 */
	public Action makeAction(String method, Object... params) throws NoSuchMethodException {
		// construction du tableau des types des paramètres
		Class<?>[] paramsTypes = new Class<?>[params.length];
		for ( int i = 0; i < params.length; i++ ) {
			paramsTypes[i] = params[i].getClass();
		}

		Method m = driver.getClass().getMethod(method, paramsTypes);

		return new Action(this, m, params);
	}
	
	/**
	 * Fabrique d'une action pour l'instance d'équipement lié
	 * 
	 * @param method
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 */
	public Action makeAction(Method m, Object... params) throws NoSuchMethodException {
		return new Action(this, m, params);
	}

	public boolean isDriverLoaded() {
		return driver != null;
	}

	public void loadDriver() throws DriverException {
		DriverManager.loadDriver(driverIdentifier.getDriverPath());
		driver = DriverFactory.getInstance().getDriver(driverIdentifier.getDriverName(), ConvertTools.str2Properties(properties));
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperties() {
		return this.properties;
	}
	
	public Properties getRealProperties() {
		return ConvertTools.str2Properties(properties);
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = ConvertTools.properties2Str(properties);
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public DriverIdentifier getDriverIdentifier() {
		return driverIdentifier;
	}

	public void setDriverIdentifier(DriverIdentifier driverIdentifier) {
		this.driverIdentifier = driverIdentifier;
	}

}