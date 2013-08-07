package dao;

import java.util.List;

import main.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.source.DaoSource;

// http://www.siteduzero.com/tutoriel-3-119237-le-pattern-dao-1-2.html

// http://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/
// => MIEUX
public abstract class DAO<T> {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	private DaoSource daoSrc = null;

	/**
	 * Constructeur
	 */
	public DAO ( DaoSource _daoSrc ) {
		daoSrc = _daoSrc;
		logger.debug(this.getClass().getName() + " instanciated");
	}
	
	/**
	 * Constructeur
	 */
	public DAO () {
		//daoSrc = _daoSrc;
		logger.debug(this.getClass().getName() + " instanciated");
	}

	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @return
	 */
	public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param id
	 * @return
	 */
	public abstract T find(int id);

	/**
	 * Méthode de recherche totale
	 * 
	 * @param id
	 * @return
	 */
	public abstract List<T> findAll();

	/**
	 * Renvoi la source de persistence
	 * 
	 * @return
	 */
	public DaoSource getDaoSrc() {
		return daoSrc;
	}
}