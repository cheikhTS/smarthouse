package smarthouse.ejb.dao;

import java.util.List;

public interface DAO<T> {
	/**
	 * Méthode de création
	 * 
	 * @param obj
	 * @return
	 */
	public boolean create(T obj);

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return
	 */
	public boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(T obj);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param id
	 * @return
	 */
	public T find(int id);

	/**
	 * Méthode de recherche totale
	 * 
	 * @param id
	 * @return
	 */
	public List<T> findAll();
}
