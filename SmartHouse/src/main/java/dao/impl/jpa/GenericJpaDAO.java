/**
 * 
 */
package dao.impl.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import main.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import dao.DAO;
import dao.source.DaoSource;
import dao.source.JpaSource;
import dao.source.SerializationSource;

/**
 * Réalisée le 29 sept. 2012
 * 
 * 
 * @author Julien
 * @param <T>
 */
public class GenericJpaDAO<T> extends DAO<T> {

	private static Logger logger = LoggerFactory.getLogger(GenericJpaDAO.class);

	private EntityManager em;
	
	private EntityTransaction transac;
	
	private final Class type;
	
	public GenericJpaDAO ( DaoSource _daoSrc, Class _type ) {
		super(_daoSrc);
		em = ((JpaSource) _daoSrc).getEm();
		type = _type;
		transac =  em.getTransaction();
	}

	@Override
	public boolean create(T obj) {
		transac.begin();
		em.persist(obj);
		em.flush();
		transac.commit();
		logger.debug(obj.getClass().getSimpleName() + " creation.");
		return true;
	}

	@Override
	public boolean delete(T obj) {
		transac.begin();
		em.remove(obj);
		em.flush();
		transac.commit();
		logger.debug(obj.getClass().getSimpleName() + "  delete.");
		return false;
	}

	@Override
	public boolean update(T obj) {
		logger.debug(obj.getClass().getSimpleName() + " updating");
		
		return false;
	}

	@Override
	public T find(int id) {
		logger.debug(type.getSimpleName() + " -  search [id=" + id + "]");
		T obj = (T) em.find(type, id);
		if (obj != null){
			return obj;
		}
		logger.debug(type.getSimpleName() + " - search [id=" + id + "] failed");
		return null;
	}

	@Override
	public List<T> findAll() {
		logger.debug(type.getSimpleName() + " - search all");
		Query query = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e");
		return (List<T>)query.getResultList();
	}

	@Override
	public JpaSource getDaoSrc() {
		return (JpaSource) super.getDaoSrc();
	}

}
