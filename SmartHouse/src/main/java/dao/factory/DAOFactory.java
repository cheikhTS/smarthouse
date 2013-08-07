package dao.factory;

import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.pattern.Scenario;
import pojo.pattern.Task;
import dao.DAO;

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
	public abstract DAO<Equipment> getEquipmentDAO();

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
