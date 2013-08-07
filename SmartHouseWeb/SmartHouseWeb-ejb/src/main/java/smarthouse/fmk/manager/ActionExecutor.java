package smarthouse.fmk.manager;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.entity.pattern.Action;

/**
 * Centralisation des actions a executer
 * 
 * @author Julien
 * 
 */
public class ActionExecutor extends Thread {
	private LinkedBlockingQueue<Action> actionsQueue = new LinkedBlockingQueue<Action>();
	private boolean enabled = true;
	private static Object lock = new Object();
	private static ActionExecutor instance = null;
	private static Logger logger = LoggerFactory
			.getLogger(ActionExecutor.class);

	public static ActionExecutor getInstance() {
		synchronized (lock) {
			if (instance == null) {
				instance = new ActionExecutor();
			}
			return instance;
		}
	}

	private ActionExecutor() {
		start();
	}

	@Override
	public void interrupt() {
		super.interrupt();
		this.enabled = false;
	}

	@Override
	public void run() {
		while (this.enabled) {
			try {
				Action action = this.actionsQueue.take();
				if (action != null) {
					action.run();
				}
			} catch (Exception e) {
				if (e instanceof InterruptedException) {

				} else {
					e.printStackTrace();
					logger.debug("impossible de lancer l'action : "
							+ e.getMessage());
				}
			}
		}
	}

	public void addAction(Action action) {
		this.actionsQueue.add(action);
	}

	public LinkedBlockingQueue<Action> getActionsQueue() {
		return this.actionsQueue;
	}

	public void setActionsQueue(LinkedBlockingQueue<Action> actionsQueue) {
		this.actionsQueue = actionsQueue;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
