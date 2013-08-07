package smarthouse.fmk.manager;

import java.util.ArrayList;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.ejb.entity.pattern.trigger.ATrigger;
import smarthouse.fmk.Config;


public class SchedulerManager {
	// Centralisation de tout les timers lances.
	private ArrayList<Timer> timers = new ArrayList<Timer>();
	private static Object lock = new Object();
	private static SchedulerManager instance = null;
	private static Logger logger = LoggerFactory.getLogger(Config.class);

	public static SchedulerManager getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new SchedulerManager();
			}
			return instance;
		}
	}

	private SchedulerManager() {

	}

	public void startScenario(Scenario scenario) {
		if (scenario.isEnabled()) {
			logger.debug("Lancement du scenario (" + scenario.getName() + ")");
			for (final Task task : scenario.getTasks()) {
				for (ATrigger trigger : task.getTriggers()) {
					this.timers.add(trigger.runTimer(task.getActions()));
				}
			}
		} else {
			logger.debug("Le scenario n'est pas lance car il n'est pas active");
		}
	}

	public void stopScenario(Scenario scenario) {
		if (scenario.isEnabled()) {
			for (final Task task : scenario.getTasks()) {
				for (ATrigger trigger : task.getTriggers()) {
					Timer timer = trigger.getTimer();
					if (timer != null) {
						timer.purge();
						timer.cancel();
					}
				}
			}
		}
	}

	public void closeTimers() {
		for (Timer t : this.timers) {
			t.purge();
			t.cancel();
		}
	}
}
