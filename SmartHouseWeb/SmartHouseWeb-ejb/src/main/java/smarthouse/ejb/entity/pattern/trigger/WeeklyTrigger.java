package smarthouse.ejb.entity.pattern.trigger;

import java.util.ArrayList;
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
import smarthouse.ejb.util.ConvertTools;
import smarthouse.fmk.manager.ActionExecutor;

/**
 * The persistent class for the dailytrigger database table.
 * 
 */
@Entity
@DiscriminatorValue("WEEKLYTRIGGER")
public class WeeklyTrigger extends ATrigger {
	private static final long serialVersionUID = 1L;

	private int numberOfRepeat;
	private String validDays;

	public WeeklyTrigger(Date dateExpiration, Date dateStart, String name,
			long startTime, Task task, int _numberOfRepeat, String _validDays) {
		super(dateExpiration, dateStart, name, startTime, task);
		this.numberOfRepeat = _numberOfRepeat;
	}

	public WeeklyTrigger() {
	}

	public WeeklyTrigger(Task task, Date date, int startTime,
			ArrayList<Integer> selectValidDay, int nbofRepeat) {
		this(null, date, "", startTime, task, nbofRepeat, ConvertTools
				.intArray2str(selectValidDay));
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
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
				if (WeeklyTrigger.this.dateExpiration != null
						&& WeeklyTrigger.this.dateExpiration.before(Calendar
								.getInstance().getTime())) {
					WeeklyTrigger.this.timer.purge();
					WeeklyTrigger.this.timer.cancel();
					System.out.println("Fin du timer");
					return;
					// Fin du timer
				}
				// On compare les dates pour savoir s'il doit sactiver ou non
				int authorized = (calendar.get(Calendar.WEEK_OF_MONTH) - Calendar
						.getInstance().get(Calendar.WEEK_OF_MONTH))
						% WeeklyTrigger.this.numberOfRepeat;
				if (authorized == 0) {
					int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
					ArrayList<Integer> validDaysInt = ConvertTools
							.str2IntArray(WeeklyTrigger.this.validDays);
					if (validDaysInt.contains(day)) {
						for (Action action : actions) {
							ActionExecutor.getInstance().addAction(action);
						}
					}
				}
			}
		};
		this.timer.scheduleAtFixedRate(ttask, calendar.getTime(),
				(long) 24 * 60 * 60 * 1000);
		return this.timer;
	}

}