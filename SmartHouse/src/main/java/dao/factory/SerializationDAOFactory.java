package dao.factory;

import main.Config;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;
import dao.impl.serialization.EquipmentSerializationDAO;
import dao.impl.serialization.HomeSerializationDAO;
import dao.impl.serialization.ScenarioSerializationDAO;
import dao.impl.serialization.TaskSerializationDAO;
import dao.source.SerializationSource;

/**
 * Application de l'AbstractFactory pour les DAO spécialisés dans la
 * serialisation.
 */
public class SerializationDAOFactory extends DAOFactory {

	private SerializationSource src = new SerializationSource(Config.getProps());

	@Override
	public DAO<Home> getHomeDAO() {
		return new HomeSerializationDAO(src);
	}

	@Override
	public DAO<Equipment> getEquipmentDAO() {
		return new EquipmentSerializationDAO(src);
	}

	@Override
	public DAO<Task> getTaskDAO() {
		return new TaskSerializationDAO(src);
	}

	@Override
	public DAO<Scenario> getScenarioDAO() {
		return new ScenarioSerializationDAO(src);
	}

}
