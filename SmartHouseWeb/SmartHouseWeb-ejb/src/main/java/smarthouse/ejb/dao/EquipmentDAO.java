package smarthouse.ejb.dao;

import java.util.List;

import smarthouse.ejb.entity.domain.Equipment;

public interface EquipmentDAO extends DAO<Equipment> {
	public List<Equipment> findByRoom(int idRoom);
}
