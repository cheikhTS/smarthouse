package smarthouse.ejb.dao.impl.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import smarthouse.ejb.dao.EquipmentDAO;
import smarthouse.ejb.dao.source.JpaSource;
import smarthouse.ejb.entity.domain.Equipment;

public class EquipmentJpaDAO extends GenericJpaDAO<Equipment> implements EquipmentDAO {

	public EquipmentJpaDAO(JpaSource _daoSrc, Class<Equipment> _type) {
		super(_daoSrc, _type);
	}

	@Override
	public List<Equipment> findByRoom(int idRoom) {
		TypedQuery<Equipment> q = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e WHERE e.room.id = :idRoom", type);
		q.setParameter("idRoom", idRoom);
		return q.getResultList();
	}
	
}
