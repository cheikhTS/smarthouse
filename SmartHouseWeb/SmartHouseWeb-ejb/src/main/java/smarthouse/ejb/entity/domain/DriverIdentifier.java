package smarthouse.ejb.entity.domain;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the driveridentifier database table.
 * 
 */
@Entity
public class DriverIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * Nom du driver
	 */
	private String driverName;

	/**
	 * Bundle contenant le driver
	 */
	private String driverPath;

	// bi-directional many-to-one association to Equipment
	// bi-directional many-to-one association to Room
	@OneToMany(mappedBy = "driverIdentifier", cascade = ALL)
	private List<Equipment> equipments;

	public DriverIdentifier() {
		
	}

	public DriverIdentifier(String driverName, String driverPath) {
		this.driverName = driverName;
		this.driverPath = driverPath;
	}
	
	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverPath() {
		return this.driverPath;
	}

	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}

	@XmlTransient
	public List<Equipment> getEquipments() {
		return this.equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

}