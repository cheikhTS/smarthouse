package smarthouse.ejb.entity.pattern.trigger;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import smarthouse.ejb.entity.pattern.Action;
import smarthouse.ejb.entity.pattern.Task;

/**
 * The persistent class for the trigger database table.
 * 
 */
@Entity
@DiscriminatorColumn(name = "TYPE_ENTITE")
@DiscriminatorValue("ATRIGGER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ATrigger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected int id;

	protected String name;

	protected long startTime;

	@Temporal(TemporalType.DATE)
	protected Date dateExpiration;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date dateStart;

	// bi-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name = "idTask")
	protected Task task;

	@Transient
	protected transient Timer timer;

	public abstract Timer runTimer(List<Action> actions);

	public ATrigger() {
		super();
	}

	public ATrigger(Date dateExpiration, Date dateStart, String name,
			long startTime, Task task) {
		super();
		this.dateExpiration = dateExpiration;
		this.dateStart = dateStart;
		this.name = name;
		this.startTime = startTime;
		this.task = task;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
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

	public long getStartTime() {
		return this.startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Date getDateExpiration() {
		return this.dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}