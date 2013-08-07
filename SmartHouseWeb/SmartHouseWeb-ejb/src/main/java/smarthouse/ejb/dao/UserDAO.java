package smarthouse.ejb.dao;

import javax.persistence.PersistenceException;

import smarthouse.ejb.entity.third.User;

public interface UserDAO extends DAO<User> {
	public User findByUsername(String username) throws PersistenceException;
}
