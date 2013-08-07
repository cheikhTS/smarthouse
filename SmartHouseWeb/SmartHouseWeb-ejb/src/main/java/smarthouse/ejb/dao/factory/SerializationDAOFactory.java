package smarthouse.ejb.dao.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.DAO;
import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.RoomDAO;
import smarthouse.ejb.dao.UserDAO;
import smarthouse.ejb.dao.impl.serialization.EquipmentSerializationDAO;
import smarthouse.ejb.dao.impl.serialization.HomeSerializationDAO;
import smarthouse.ejb.dao.impl.serialization.ScenarioSerializationDAO;
import smarthouse.ejb.dao.impl.serialization.TaskSerializationDAO;
import smarthouse.ejb.dao.source.SerializationSource;
import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.ejb.entity.third.User;
import smarthouse.fmk.Config;

/**
 * Application de l'AbstractFactory pour les DAO spécialisés dans la
 * serialisation.
 */
public class SerializationDAOFactory extends DAOFactory {
	private Logger logger = LoggerFactory.getLogger(SqliteDAOFactory.class);
	
	private SerializationSource src = new SerializationSource(Config.getProps());

	@Override
	public AbstractDAO<Home> getHomeDAO() {
		return new HomeSerializationDAO(this.src);
	}

	@Override
	public EquipmentDAO getEquipmentDAO() {
		logger.error("EquipmentDAO for Serialization not implemented !");
		return null;
	}

	@Override
	public AbstractDAO<Task> getTaskDAO() {
		return new TaskSerializationDAO(this.src);
	}

	@Override
	public AbstractDAO<Scenario> getScenarioDAO() {
		return new ScenarioSerializationDAO(this.src);
	}
	
	@Override
	public UserDAO getUserDAO() {
		logger.error("UserDAO for Serialization not implemented !");
		return null;
	}

	@Override
	public RoomDAO getRoomDAO() {
		logger.error("RoomDAO for Serialization not implemented !");
		return null;
	}

}
