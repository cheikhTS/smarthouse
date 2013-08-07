package smarthouse.ejb.dao;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.source.DaoSource;
import smarthouse.fmk.Config;


// http://www.siteduzero.com/tutoriel-3-119237-le-pattern-dao-1-2.html

// http://cyrille-herby.developpez.com/tutoriels/java/mapper-sa-base-donnees-avec-pattern-dao/
// => MIEUX
public abstract class AbstractDAO<T> implements DAO<T> {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	protected DaoSource daoSrc = null;

	/**
	 * Constructeur
	 */
	public AbstractDAO(DaoSource _daoSrc) {
		this.daoSrc = _daoSrc;
		logger.debug(this.getClass().getName() + " instanciated");
	}

	/**
	 * Constructeur
	 */
	public AbstractDAO() {
		// daoSrc = _daoSrc;
		logger.debug(this.getClass().getName() + " instanciated");
	}

	/**
	 * Renvoi la source de persistence
	 * 
	 * @return
	 */
	public DaoSource getDaoSrc() {
		return this.daoSrc;
	}
}