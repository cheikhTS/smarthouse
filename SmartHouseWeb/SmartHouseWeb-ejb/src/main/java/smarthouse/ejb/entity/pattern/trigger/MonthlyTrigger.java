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
 * The persistent class for the monthlytrigger database table.
 * 
 */
@Entity
@DiscriminatorValue("MONTHLYTRIGGER")
public class MonthlyTrigger extends ATrigger {
	private static final long serialVersionUID = 1L;

	private String validDays;
	private String validMonths;

	public MonthlyTrigger() {
	}

	public MonthlyTrigger(Date dateExpiration, Date dateStart, String name,
			long startTime, Task task, String _validMonths, String _validDays) {
		super(dateExpiration, dateStart, name, startTime, task);
		this.validMonths = _validMonths;
		this.validDays = _validDays;
	}

	public MonthlyTrigger(Task task, Date date, int startTime,
			ArrayList<Integer> selectValidMonth,
			ArrayList<Integer> selectValidNumberDay) {
		this(null, date, "", startTime, task, ConvertTools
				.intArray2str(selectValidMonth), ConvertTools
				.intArray2str(selectValidNumberDay));
	}

	public String getValidDays() {
		return this.validDays;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	public String getValidMonths() {
		return this.validMonths;
	}

	public void setValidMonths(String validMonths) {
		this.validMonths = validMonths;
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
				if (MonthlyTrigger.this.dateExpiration != null
						&& MonthlyTrigger.this.dateExpiration.before(Calendar
								.getInstance().getTime())) {
					MonthlyTrigger.this.timer.purge();
					MonthlyTrigger.this.timer.cancel();
					return;
					// Fin du timer
				}
				int month = Calendar.getInstance().get(Calendar.MONTH);
				int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				ArrayList<Integer> validMonthsInt = ConvertTools
						.str2IntArray(MonthlyTrigger.this.validMonths);
				ArrayList<Integer> validDaysInt = ConvertTools
						.str2IntArray(MonthlyTrigger.this.validDays);
				if (validMonthsInt.contains(month)) {
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