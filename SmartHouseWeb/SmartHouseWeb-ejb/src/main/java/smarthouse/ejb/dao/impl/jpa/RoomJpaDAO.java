package smarthouse.ejb.dao.impl.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import smarthouse.ejb.dao.RoomDAO;
import smarthouse.ejb.dao.source.JpaSource;
import smarthouse.ejb.entity.domain.Room;

public class RoomJpaDAO extends GenericJpaDAO<Room> implements RoomDAO {

	public RoomJpaDAO(JpaSource _daoSrc, Class<Room> _type) {
		super(_daoSrc, _type);
	}

	@Override
	public List<Room> findByArea(int idArea) {
		TypedQuery<Room> q = em.createQuery("SELECT r FROM " + type.getSimpleName() + " r WHERE r.area.id = :idArea", type);
		q.setParameter("idArea", idArea);
		return q.getResultList();
	}
}
