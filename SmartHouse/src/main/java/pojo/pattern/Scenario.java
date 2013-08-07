package pojo.pattern;

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

import pojo.domain.Home;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;

/**
 * The persistent class for the scenario database table.
 * 
 */
@Entity
public class Scenario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private boolean enabled;

	private String name;

	// bi-directional many-to-one association to Home
	@ManyToOne
	@JoinColumn(name = "idHome", nullable=false)
	private Home home;

	// bi-directional many-to-one association to Task
	@OneToMany(mappedBy = "scenario", cascade = ALL)
	private List<Task> tasks = new ArrayList<Task>();

	public Scenario () {
	}

	public Scenario ( String _name, Home _home ) {
		name = _name;
		home = _home;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Home getHome() {
		return this.home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}