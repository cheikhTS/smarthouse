package pojo.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.ALL;

/**
 * The persistent class for the driveridentifier database table.
 * 
 */
@Entity
public class DriverIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String driverClass;

	private String driverName;

	private String driverPath;

	// bi-directional many-to-one association to Equipment
	// bi-directional many-to-one association to Room
	@OneToMany(mappedBy = "driverIdentifier", cascade = ALL)
	private List<Equipment> equipments;

	public DriverIdentifier () {
	}

	public DriverIdentifier ( String driverName, String driverPath, Class driverClass) {
		this.driverName = driverName;
		this.driverPath = driverPath;
		this.driverClass = driverClass.getCanonicalName();
	}

	public DriverIdentifier ( String _driverName, String _driverPath ) {
		this(_driverName, _driverPath, Object.class);
	}

	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
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

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	@Override
	public String toString() {
		return driverName.substring(driverName.lastIndexOf(".")+1) + " - " + driverPath;
	}

	

}