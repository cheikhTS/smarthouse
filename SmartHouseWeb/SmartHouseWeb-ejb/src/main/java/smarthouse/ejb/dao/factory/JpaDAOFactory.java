package smarthouse.ejb.dao.factory;

import smarthouse.ejb.dao.DAO;
import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.RoomDAO;
import smarthouse.ejb.dao.UserDAO;
import smarthouse.ejb.dao.impl.jpa.EquipmentJpaDAO;
import smarthouse.ejb.dao.impl.jpa.GenericJpaDAO;
import smarthouse.ejb.dao.impl.jpa.RoomJpaDAO;
import smarthouse.ejb.dao.impl.jpa.UserJpaDAO;
import smarthouse.ejb.dao.source.JpaSource;
import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;
import smarthouse.fmk.Config;

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
	public DAO<Task> getTaskDAO() {
		return new GenericJpaDAO<Task>(db, Task.class);
	}

	@Override
	public DAO<Scenario> getScenarioDAO() {
		return new GenericJpaDAO<Scenario>(db, Scenario.class);
	}
	
	@Override
	public UserDAO getUserDAO() {
		return new UserJpaDAO(db);
	}

	@Override
	public EquipmentDAO getEquipmentDAO() {
		return new EquipmentJpaDAO(db, Equipment.class);
	}
	
	@Override
	public RoomDAO getRoomDAO() {
		return new RoomJpaDAO(db, Room.class);
	}

}
