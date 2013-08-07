package smarthouse.ejb.entity.domain;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.entity.pattern.Action;
import smarthouse.ejb.util.ConvertTools;
import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.runtime.DriverFactory;
import smarthouse.fmk.runtime.DriverFactoryException;

/**
 * The persistent class for the equipment database table.
 * 
 */
@Entity
public class Equipment implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(Equipment.class);
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private String properties;

	/**
	 * Instance du driver pointant sur l'équipement distant Ce driver est
	 * l'implémentation du protocol de communication entre l'équipement et l'UC
	 */
	private transient Driver driver;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idDriver", nullable = true)
	private DriverIdentifier driverIdentifier;

	// bi-directional many-to-one association to Room
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idRoom", nullable = false)
	private Room room;

	public Equipment() {
	}

	public Equipment(Room _room, String name,
			DriverIdentifier _driverIdentifier, Properties properties)
			throws DriverException, DriverFactoryException {
		this.name = name;
		this.driverIdentifier = _driverIdentifier;
		this.properties = ConvertTools.properties2Str(properties);
		this.room = _room;
		loadDriver();
		this.room.getEquipments().add(this);
	}

	public Equipment(Room _room, String name,
			DriverIdentifier _driverIdentifier, String... _properties)
			throws DriverException, DriverFactoryException {
		this.name = name;
		this.driverIdentifier = _driverIdentifier;
		// conversion
		StringBuilder str = new StringBuilder();
		for (String s : _properties) {
			str.append(s + ";");
		}
		this.properties = str.toString();
		this.room = _room;
		loadDriver();
		this.room.getEquipments().add(this);
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
		for (int i = 0; i < params.length; i++) {
			paramsTypes[i] = params[i].getClass();
		}

		Method m = this.driver.getClass().getMethod(method, paramsTypes);

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
	public Action makeAction(Method m, Object... params)
			throws NoSuchMethodException {
		return new Action(this, m, params);
	}

	/**
	 * Driver chargé ?
	 * @return
	 */
	public boolean isDriverLoaded() {
		return this.driver != null;
	}

	/**
	 * Fonction de chargement du driver de l'équipement
	 * @throws DriverException
	 * @throws DriverFactoryException
	 */
	@PostLoad
	public void loadDriver() throws DriverException, DriverFactoryException {
		logger.info("@PostLoad on equipment "+name);
		this.driver = DriverFactory.getInstance().getDriver(
				this.driverIdentifier.getDriverName(),
				ConvertTools.str2Properties(this.properties));
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
		return ConvertTools.str2Properties(this.properties);
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public void setProperties(Properties properties) {
		this.properties = ConvertTools.properties2Str(properties);
	}

	@XmlTransient
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@XmlTransient
	public Driver getDriver() {
		return this.driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public DriverIdentifier getDriverIdentifier() {
		return this.driverIdentifier;
	}

	public void setDriverIdentifier(DriverIdentifier driverIdentifier) {
		this.driverIdentifier = driverIdentifier;
	}

}