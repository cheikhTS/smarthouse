package dao.factory;

import main.Config;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;
import dao.impl.jpa.GenericJpaDAO;
import dao.impl.sqlite.EquipmentSqliteDAO;
import dao.impl.sqlite.HomeSqliteDAO;
import dao.impl.sqlite.ScenarioSqliteDAO;
import dao.impl.sqlite.TaskSqliteDAO;
import dao.source.JpaSource;
import dao.source.SqliteSource;

/**
 * Application de l'AbstractFactory pour les DAO spécialisés SQLite.
 */
public class JpaDAOFactory extends DAOFactory {

	protected static JpaSource db = new JpaSource(Config.getProps());

	@Override
	public DAO<Home> getHomeDAO() {
		return new GenericJpaDAO<Home>(db, Home.class);
	}

	@Override
	public DAO<Equipment> getEquipmentDAO() {
		return new GenericJpaDAO<Equipment>(db, Equipment.class);
	}

	@Override
	public DAO<Task> getTaskDAO() {
		return new GenericJpaDAO<Task>(db, Task.class);
	}

	@Override
	public DAO<Scenario> getScenarioDAO() {
		return new GenericJpaDAO<Scenario>(db, Scenario.class);
	}

}
