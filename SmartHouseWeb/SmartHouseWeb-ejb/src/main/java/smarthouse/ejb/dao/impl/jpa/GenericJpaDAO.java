/**
 * 
 */
package smarthouse.ejb.dao.impl.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.JpaSource;


/**
 * Réalisée le 29 sept. 2012
 * 
 * 
 * @author Julien
 * @param <T>
 */
public class GenericJpaDAO<T> extends AbstractDAO<T> {

	private static Logger logger = LoggerFactory.getLogger(GenericJpaDAO.class);

	/**
	 * Gestionnaire des entités avec JPA
	 */
	protected EntityManager em;

	/**
	 * Objet géré par la DAO
	 */
	protected final Class<T> type;

	public GenericJpaDAO(JpaSource _daoSrc, Class<T> _type) {
		super(_daoSrc);
		this.em = _daoSrc.getEm();
		this.type = _type;
	}

	@Override
	public boolean create(T obj) {
		this.em.joinTransaction();
		this.em.merge(obj);
		logger.debug(obj.getClass().getSimpleName() + " creation.");
		return true;
	}

	@Override
	public boolean delete(T obj) {
		this.em.joinTransaction();
		this.em.remove(obj);
		logger.debug(obj.getClass().getSimpleName() + "  delete.");
		return false;
	}

	@Override
	public boolean update(T obj) {
		this.em.joinTransaction();
		this.em.merge(obj);
		logger.debug(obj.getClass().getSimpleName() + " updating");
		return false;
	}

	@Override
	public T find(int id) {
		logger.debug(this.type.getSimpleName() + " -  search [id=" + id + "]");
		T obj = (T) this.em.find(this.type, id);
		if (obj != null) {
			return obj;
		}
		logger.debug(this.type.getSimpleName() + " - search [id=" + id
				+ "] failed");
		return null;
	}

	@Override
	public List<T> findAll() {
		logger.debug(this.type.getSimpleName() + " - search all");
		Query query = this.em.createQuery("SELECT e FROM "
				+ this.type.getSimpleName() + " e");
		return query.getResultList();
	}

	@Override
	public JpaSource getDaoSrc() {
		return (JpaSource) super.getDaoSrc();
	}

}
