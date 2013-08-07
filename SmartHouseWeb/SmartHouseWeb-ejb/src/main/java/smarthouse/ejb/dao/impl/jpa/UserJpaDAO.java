package smarthouse.ejb.dao.impl.jpa;

import javax.persistence.TypedQuery;

import smarthouse.ejb.dao.UserDAO;
import smarthouse.ejb.dao.source.JpaSource;
import smarthouse.ejb.entity.third.User;

public class UserJpaDAO extends GenericJpaDAO<User> implements UserDAO {

	public UserJpaDAO(JpaSource _daoSrc) {
		super(_daoSrc, User.class);
	}

	@Override
	public User findByUsername(String username) {
		TypedQuery<User> q = em.createQuery("SELECT u FROM " + type.getSimpleName() + " u WHERE u.username = :username", type);
		q.setParameter("username", username);
		return q.getSingleResult();
	}

}
