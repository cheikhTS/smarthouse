package smarthouse.ejb.dao;

import java.util.List;

import smarthouse.ejb.entity.domain.Room;

public interface RoomDAO extends DAO<Room> {
	public List<Room> findByArea(int idArea);
}
