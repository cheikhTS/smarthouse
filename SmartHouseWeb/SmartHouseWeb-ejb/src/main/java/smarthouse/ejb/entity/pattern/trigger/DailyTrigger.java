package smarthouse.ejb.entity.pattern.trigger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import smarthouse.ejb.entity.pattern.Action;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.fmk.manager.ActionExecutor;

/**
 * The persistent class for the weeklytrigger database table.
 * 
 */
@Entity
@DiscriminatorValue("DAILYTRIGGER")
public class DailyTrigger extends ATrigger {
	private static final long serialVersionUID = 1L;

	private int numberOfRepeat;

	public DailyTrigger(Date dateExpiration, Date dateStart, String name,
			long startTime, Task task, int _numberOfRepeat) {
		super(dateExpiration, dateStart, name, startTime, task);
		this.numberOfRepeat = _numberOfRepeat;
	}

	public DailyTrigger() {
	}

	public DailyTrigger(Task task, Date date, int timeInSecond, int nbofRepeat) {
		this(null, date, "", timeInSecond, task, nbofRepeat);
	}

	public int getNumberOfRepeat() {
		return this.numberOfRepeat;
	}

	public void setNumberOfRepeat(int numberOfRepeat) {
		this.numberOfRepeat = numberOfRepeat;
	}

	@Override
	public Timer runTimer(final List<Action> actions) {
		if (this.timer != null) {
			this.timer.purge();
			this.timer.cancel();
		}
		this.timer = new Timer();
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.dateStart);
		calendar.set(Calendar.HOUR_OF_DAY, (int) this.startTime / 3600);
		calendar.set(Calendar.MINUTE, (int) (this.startTime % 3600) / 60);
		calendar.set(Calendar.SECOND, (int) (this.startTime % 3600) % 60);
		TimerTask ttask = new TimerTask() {
			@Override
			public void run() {
				if (DailyTrigger.this.dateExpiration != null
						&& DailyTrigger.this.dateExpiration.before(Calendar
								.getInstance().getTime())) {
					DailyTrigger.this.timer.purge();
					DailyTrigger.this.timer.cancel();
					return;
					// Fin du timer
				}

				// On compare les dates pour savoir s'il doit sactiver ou non
				int authorized = (calendar.get(Calendar.DAY_OF_WEEK) - Calendar
						.getInstance().get(Calendar.DAY_OF_WEEK))
						% DailyTrigger.this.numberOfRepeat;
				if (authorized == 0) {
					for (Action action : actions) {
						ActionExecutor.getInstance().addAction(action);
					}
				}
			}
		};
		this.timer.scheduleAtFixedRate(ttask, calendar.getTime(),
				(long) 24 * 60 * 60 * 1000);
		return this.timer;
	}

}