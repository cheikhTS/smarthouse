package pojo.pattern.trigger;

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
import util.ConvertTools;

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

	public MonthlyTrigger () {
	}

	public MonthlyTrigger (Date dateExpiration, Date dateStart, String name, long startTime, Task task, String _validMonths, String _validDays ) {
		super(dateExpiration, dateStart, name, startTime, task);
		validMonths = _validMonths;
		validDays = _validDays;
	}

	public MonthlyTrigger ( Task task, Date date, int startTime, ArrayList<Integer> selectValidMonth, ArrayList<Integer> selectValidNumberDay ) {
		this(null, date, "", startTime, task, ConvertTools.intArray2str(selectValidMonth), ConvertTools.intArray2str(selectValidNumberDay));
	}

	public String getValidDays() {
		return validDays;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	public String getValidMonths() {
		return validMonths;
	}

	public void setValidMonths(String validMonths) {
		this.validMonths = validMonths;
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
				if ( dateExpiration != null && dateExpiration.before(Calendar.getInstance().getTime()) ) {
					timer.purge();
					timer.cancel();
					return;
					// Fin du timer
				}
				int month = Calendar.getInstance().get(Calendar.MONTH);
				int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				ArrayList<Integer> validMonthsInt = ConvertTools.str2IntArray(validMonths);
				ArrayList<Integer> validDaysInt = ConvertTools.str2IntArray(validDays);
				if ( validMonthsInt.contains(month) ) {
					if ( validDaysInt.contains(day) ) {
						for ( Action action : actions ) {
							ActionExecutor.getInstance().addAction(action);
						}
					}
				}
			}
		};
		timer.scheduleAtFixedRate(ttask, calendar.getTime(), (long) 24 * 60 * 60 * 1000);
		return timer;
	}

}