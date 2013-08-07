package smarthouse.ejb.dao.impl.serialization;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.DaoSource;
import smarthouse.ejb.dao.source.SerializationSource;
import smarthouse.ejb.entity.pattern.Action;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.ejb.entity.pattern.trigger.ATrigger;
import smarthouse.fmk.Config;

public class ScenarioSerializationDAO extends AbstractDAO<Scenario> {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	public ScenarioSerializationDAO(DaoSource _daoSrc) {
		super(_daoSrc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Scenario obj) {
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "]  creation.");
		if (obj.getId() == -1) {
			obj.setId(getNewScenarioId());
		}

		return update(obj);
	}

	private int getNewScenarioId() {
		List<Scenario> scenarios = findAll();
		int maxid = 0;
		for (Scenario h : scenarios) {
			if (h.getId() >= maxid) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewTaskId(Scenario scenario) {
		List<Task> tasks = scenario.getTasks();
		int maxid = 0;
		for (Task h : tasks) {
			if (h.getId() >= maxid) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewTriggerId(Task task) {
		List<ATrigger> triggers = task.getTriggers();
		int maxid = 0;
		for (ATrigger h : triggers) {
			if (h.getId() >= maxid) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewActionId(Task task) {
		List<Action> actions = task.getActions();
		int maxid = 0;
		for (Action h : actions) {
			if (h.getId() >= maxid) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	@Override
	public boolean delete(Scenario obj) {
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for (int i = 0; i < scenarios.size(); i++) {
			if (scenarios.get(i).equals(obj)) {
				scenarios.remove(i);
				getDaoSrc().save();
				logger.debug(this.getClass().getName() + " -"
						+ obj.getClass().getName() + "object [id="
						+ obj.getId() + "]  deleted.");
				return true;
			}
		}
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "]  unfound ; deletion skipped.");
		return false;
	}

	@Override
	public boolean update(Scenario obj) {
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "] updating");
		delete(obj);

		for (Task task : obj.getTasks()) {
			if (task.getId() == -1) {
				task.setId(getNewTaskId(obj));
			}
			for (ATrigger trigger : task.getTriggers()) {
				if (trigger.getId() == -1) {
					trigger.setId(getNewTriggerId(task));
				}
			}
			for (Action action : task.getActions()) {
				if (action.getId() == -1) {
					action.setId(getNewActionId(task));
				}
			}
		}

		boolean result = false;
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		result = scenarios.add(obj);
		getDaoSrc().save();
		return result;
	}

	@Override
	public Scenario find(int id) {
		logger.debug(this.getClass().getName() + " - Scenario search [id=" + id
				+ "]");
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for (Scenario scenar : scenarios) {
			if (scenar.getId() == id) {
				logger.debug(this.getClass().getName()
						+ " - Scenario search [id=" + id + "] succeed");
				return scenar;
			}
		}
		logger.debug(this.getClass().getName() + " - Scenario search [id=" + id
				+ "] failed");
		return null;
	}

	@Override
	public List<Scenario> findAll() {
		logger.debug(this.getClass().getName() + " - Scenario search all");
		return getDaoSrc().getData().getScenarios();
	}

	@Override
	public SerializationSource getDaoSrc() {
		return (SerializationSource) super.getDaoSrc();
	}
}
