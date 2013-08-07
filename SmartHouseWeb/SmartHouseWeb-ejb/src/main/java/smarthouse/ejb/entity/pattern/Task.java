package smarthouse.ejb.entity.pattern;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import smarthouse.ejb.entity.pattern.trigger.ATrigger;

/**
 * The persistent class for the task database table.
 * 
 */
@Entity
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String name;

	// bi-directional many-to-one association to Action
	@OneToMany(mappedBy = "task")
	private List<Action> actions = new ArrayList<Action>();

	// bi-directional many-to-one association to Scenario
	@ManyToOne
	@JoinColumn(name = "idScenario", nullable = false)
	private Scenario scenario;

	// bi-directional many-to-one association to Trigger
	@OneToMany(mappedBy = "task", cascade = REMOVE)
	private List<ATrigger> aTriggers = new ArrayList<ATrigger>();

	public Task() {
	}

	public Task(String _name, Scenario _scenario) {
		this.name = _name;
		this.scenario = _scenario;
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

	public List<Action> getActions() {
		return this.actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public List<ATrigger> getTriggers() {
		return this.aTriggers;
	}

	public void setTriggers(List<ATrigger> aTriggers) {
		this.aTriggers = aTriggers;
	}

}