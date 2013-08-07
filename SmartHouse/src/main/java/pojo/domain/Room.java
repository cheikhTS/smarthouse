package pojo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.CascadeType.ALL;

/**
 * The persistent class for the room database table.
 * 
 */
@Entity
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String name;

	// bi-directional many-to-one association to Equipment
	@OneToMany(mappedBy = "room", cascade = ALL)
	private List<Equipment> equipments = new ArrayList<Equipment>();

	// bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name = "idArea", nullable=false)
	private Area area;

	public Room () {
	}

	public Room ( String _name, Area _area ) {
		name = _name;
		area = _area;
		area.getRooms().add(this);
		// TODO Auto-generated constructor stub
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

	public List<Equipment> getEquipments() {
		return this.equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}