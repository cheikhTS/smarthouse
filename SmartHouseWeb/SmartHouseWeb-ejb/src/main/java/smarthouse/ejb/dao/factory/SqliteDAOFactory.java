package smarthouse.ejb.dao.factory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.RoomDAO;
import smarthouse.ejb.dao.UserDAO;
import smarthouse.ejb.dao.impl.sqlite.HomeSqliteDAO;
import smarthouse.ejb.dao.impl.sqlite.ScenarioSqliteDAO;
import smarthouse.ejb.dao.impl.sqlite.TaskSqliteDAO;
import smarthouse.ejb.dao.source.SqliteSource;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.fmk.Config;

/**
 * Application de l'AbstractFactory pour les DAO spécialisés SQLite.
 */
public class SqliteDAOFactory extends DAOFactory {

	private Logger logger = LoggerFactory.getLogger(SqliteDAOFactory.class);
	
	protected static SqliteSource db = new SqliteSource(Config.getProps());

	@Override
	public AbstractDAO<Home> getHomeDAO() {
		return new HomeSqliteDAO(db);
	}

	@Override
	public EquipmentDAO getEquipmentDAO() {
		// not implemented
		logger.error("EquipmentDAO for Sqlite not implemented !");
		return null;
	}

	@Override
	public AbstractDAO<Task> getTaskDAO() {
		return new TaskSqliteDAO(db);
	}

	@Override
	public AbstractDAO<Scenario> getScenarioDAO() {
		return new ScenarioSqliteDAO(db);
	}
	
	@Override
	public UserDAO getUserDAO() {
		// not implemented
		logger.error("UserDAO for Sqlite not implemented !");
		return null;
	}

	@Override
	public RoomDAO getRoomDAO() {
		// not implemented
		logger.error("RoomDAO for Sqlite not implemented !");
		return null;
	}

}
