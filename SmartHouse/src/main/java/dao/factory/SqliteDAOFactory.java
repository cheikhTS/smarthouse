package dao.factory;

import main.Config;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;
import dao.impl.sqlite.EquipmentSqliteDAO;
import dao.impl.sqlite.HomeSqliteDAO;
import dao.impl.sqlite.ScenarioSqliteDAO;
import dao.impl.sqlite.TaskSqliteDAO;
import dao.source.SqliteSource;

/**
 * Application de l'AbstractFactory pour les DAO spécialisés SQLite.
 */
public class SqliteDAOFactory extends DAOFactory {

	protected static SqliteSource db = new SqliteSource(Config.getProps());

	@Override
	public DAO<Home> getHomeDAO() {
		return new HomeSqliteDAO(db);
	}

	@Override
	public DAO<Equipment> getEquipmentDAO() {
		return new EquipmentSqliteDAO(db);
	}

	@Override
	public DAO<Task> getTaskDAO() {
		return new TaskSqliteDAO(db);
	}

	@Override
	public DAO<Scenario> getScenarioDAO() {
		return new ScenarioSqliteDAO(db);
	}

}
