package pojo.pattern.trigger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import manager.ActionExecutor;

import pojo.pattern.Action;
import pojo.pattern.Task;

/**
 * Entity implementation class for Entity: OnceTrigger
 * 
 */
@Entity
@DiscriminatorValue("ONCETRIGGER")
public class OnceTrigger extends ATrigger implements Serializable {

	private static final long serialVersionUID = 1L;

	public OnceTrigger () {
		super();
	}

	public OnceTrigger (Date dateExpiration, Date dateStart, String name, long startTime, Task _task ) {
		super();
		this.dateExpiration = dateExpiration;
		this.dateStart = dateStart;
		this.name = name;
		this.startTime = startTime;
		this.task = _task;
		task.getTriggers().add(this);
	}
	
	public OnceTrigger (Task _task, Date dateStart, long _time) {
		this(null, dateStart, "", _time, _task);
	}

	@Override
	public Timer runTimer(final List<Action> actions) {
		if ( timer != null ) {
			timer.purge();
			timer.cancel();
		}
		timer = new Timer();
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dateStart);
		calendar.set(Calendar.HOUR_OF_DAY, (int) startTime / 3600);
		calendar.set(Calendar.MINUTE, (int) (startTime % 3600) / 60);
		calendar.set(Calendar.SECOND, (int) (startTime % 3600) % 60);
		TimerTask ttask = new TimerTask() {
			@Override
			public void run() {
				if (calendar.before(Calendar.getInstance().getTime()) ) {
					timer.purge();
					timer.cancel();
					return;
				}
				for ( Action action : actions ) {
					ActionExecutor.getInstance().addAction(action);
				}
				timer.purge();
				timer.cancel();
			}
		};
		timer.schedule(ttask, calendar.getTime());
		return timer;
	}

}
