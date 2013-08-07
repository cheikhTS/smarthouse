package smarthouse.ejb.dao.factory;

import smarthouse.ejb.dao.DAO;
import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.RoomDAO;
import smarthouse.ejb.dao.UserDAO;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.pattern.Scenario;
import smarthouse.ejb.entity.pattern.Task;

public abstract class DAOFactory {
	/**
	 * Article sur l'implémentation du singleton thread-safe par static class
	 * holder
	 * http://embarcaderos.net/2009/06/23/the-singleton-pattern-in-java-multi
	 * -threaded-applications/ => Intéressant à comprendre le problème du
	 * double-checked locking !
	 */
	private static class DAOFactoryHolder {
		public static DAOFactory factory = new JpaDAOFactory();
	}

	/**
	 * Retourne la DAO qui gère les objets Home
	 * 
	 * @return
	 */
	public abstract DAO<Home> getHomeDAO();

	/**
	 * Retourne la DAO qui gère les objets Task
	 * 
	 * @return
	 */
	public abstract DAO<Task> getTaskDAO();

	/**
	 * Retourne la DAO qui gère les objets Scenario
	 * 
	 * @return
	 */
	public abstract DAO<Scenario> getScenarioDAO();

	/**
	 * Retourne la DAO qui gère les objets Equipment
	 * 
	 * @return
	 */
	public abstract EquipmentDAO getEquipmentDAO();
	
	/**
	 * Retourne la DAO qui gère les utilisateurs
	 * > Interface spécifique utilisée.
	 * @return
	 */
	public abstract UserDAO getUserDAO();
	
	public abstract RoomDAO getRoomDAO();
	
	/**
	 * Retourne la factory de DAO => localisation du point de changement actuel
	 * de la factory => FLEXIBILITE
	 * 
	 * @return
	 */
	public static DAOFactory getFactory() {
		return DAOFactoryHolder.factory;
	}

}
