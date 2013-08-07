package smarthouse.ejb.entity.pattern;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.fmk.driver.Driver;
import smarthouse.fmk.driver.DriverException;
import smarthouse.fmk.driver.definition.DefAction;

/**
 * The persistent class for the action database table.
 * 
 */
@Entity
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Action.class);

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String method;

	private String args;

	// bi-directional many-to-one association to Equipment
	@ManyToOne
	@JoinColumn(name = "idEquipment")
	private Equipment equipment;

	// bi-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name = "idTask")
	private Task task;

	public Action() {
	}

	public Action(Task _task, Equipment equipment, String _name, Method method,
			Object... _args) {
		this.equipment = equipment;
		this.name = _name;
		this.setMethod(method.getName());
		for (Object o : _args) {
			setArgs(getArgs() + (o.toString() + ";"));
		}
		this.task = _task;
		this.task.getActions().add(this);
	}

	public Action(Task _task, Equipment equipment, Method method,
			Object... _args) {
		this(_task, equipment, method.getName(), method, _args);
	}

	public Action(Equipment equipment, Method method, Object... args) {
		this(null, equipment, method, args);
	}

	public Action(Equipment equipment, Method method) {
		this(null, equipment, method);
	}

	public Object run() throws Exception {
		if (!this.equipment.isDriverLoaded()) {
			try {
				this.equipment.loadDriver();
			} catch (DriverException e) {
				logger.debug("Action Impossible de charger le driver de l'equipement : "
						+ this);
			}
		}

		logger.debug("Action Execute : " + this);
		Method m = getRealMethod();
		if (m != null) {
			return m.invoke(this.equipment.getDriver(), this.args);
		}
		throw new Exception("Impossible de trouver la methode !!");
	}

	private Method getRealMethod() {
		Driver d = this.equipment.getDriver();
		for (Method m : d.getClass().getMethods()) {
			if (m.getName().equalsIgnoreCase(this.method)) {
				return m;
			}
		}
		return null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Equipment getEquipment() {
		return this.equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getArgs() {
		return this.args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getName() {
		return getRealMethod().getAnnotation(DefAction.class).name();
	}

	public String getDescription() {
		return getRealMethod().getAnnotation(DefAction.class)
				.description();
	}

	public void setName(String name) {
		this.name = name;
	}


}