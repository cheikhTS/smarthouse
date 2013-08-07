package dao.impl.serialization;

import java.util.ArrayList;
import java.util.List;

import main.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;
import dao.source.DaoSource;
import dao.source.SerializationSource;

public class TaskSerializationDAO extends DAO<Task> {
	private static Logger logger = LoggerFactory.getLogger(Config.class);

	public TaskSerializationDAO ( DaoSource _daoSrc ) {
		super(_daoSrc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Task obj) {
		logger.debug(this.getClass().getName() + " -" + obj.getClass().getName() + "object [id=" + obj.getId() + "]  creation.");
		return update(obj);
	}

	@Override
	public boolean delete(Task obj) {
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for ( Scenario scenar : scenarios ) {
			for ( int i = 0; i < scenar.getTasks().size(); i++ ) {
				if ( scenar.getTasks().get(i).equals(obj) ) {
					scenar.getTasks().remove(i);
					getDaoSrc().save();
					logger.debug(this.getClass().getName() + " -" + obj.getClass().getName() + "object [id=" + obj.getId() + "]  deleted.");
					return true;
				}
			}
		}
		logger.debug(this.getClass().getName() + " -" + obj.getClass().getName() + "object [id=" + obj.getId() + "]  unfound ; deletion skipped.");
		return false;
	}

	@Override
	public boolean update(Task obj) {
		logger.debug(this.getClass().getName() + " -" + obj.getClass().getName() + "object [id=" + obj.getId() + "] updating");
		delete(obj);
		boolean result = false;
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for ( Scenario scenar : scenarios ) {
			if ( scenar.equals(obj.getScenario()) ) {
				result = scenar.getTasks().add(obj);
				getDaoSrc().save();
				return result;
			}
		}
		return result;
	}

	@Override
	public Task find(int id) {
		logger.debug(this.getClass().getName() + " - Task search [id=" + id + "]");
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for ( Scenario scenar : scenarios ) {
			for ( Task task : scenar.getTasks() ) {
				if ( task.getId() == id ) {
					logger.debug(this.getClass().getName() + " - Task search [id=" + id + "] succeed");
					return task;
				}
			}
		}
		logger.debug(this.getClass().getName() + " - Task search [id=" + id + "] failed");
		return null;
	}

	@Override
	public List<Task> findAll() {
		logger.debug(this.getClass().getName() + " - Task search all");
		List<Task> tasks = new ArrayList<Task>();
		List<Scenario> scenarios = getDaoSrc().getData().getScenarios();
		for ( Scenario scenar : scenarios ) {
			tasks.addAll(scenar.getTasks());
		}
		return tasks;
	}

	@Override
	public SerializationSource getDaoSrc() {
		return (SerializationSource) super.getDaoSrc();
	}
}
