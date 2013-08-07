package manager;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.pattern.Action;

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
	private static Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

	public static ActionExecutor getInstance() {
		synchronized (lock) {
			if ( instance == null ) instance = new ActionExecutor();
			return instance;
		}
	}

	private ActionExecutor () {
		start();
	}

	@Override
	public void interrupt() {
		super.interrupt();
		enabled = false;
	}

	public void run() {
		while ( enabled ) {
			try {
				Action action = actionsQueue.take();
				if ( action != null ) action.run();
			} catch (Exception e) {
				if ( e instanceof InterruptedException ) {

				} else {
					e.printStackTrace();
					logger.debug("impossible de lancer l'action : " + e.getMessage());
				}
			}
		}
	}

	public void addAction(Action action) {
		actionsQueue.add(action);
	}

	public LinkedBlockingQueue<Action> getActionsQueue() {
		return actionsQueue;
	}

	public void setActionsQueue(LinkedBlockingQueue<Action> actionsQueue) {
		this.actionsQueue = actionsQueue;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
