/**
 * 
 */
package smarthouse.ejb.dao.impl.serialization;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.DaoSource;
import smarthouse.ejb.dao.source.SerializationSource;
import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.fmk.Config;

/**
 * Réalisée le 29 sept. 2012
 * 
 * 
 * @author Florent
 */
public class EquipmentSerializationDAO extends AbstractDAO<Equipment> {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	public EquipmentSerializationDAO(DaoSource _daoSrc) {
		super(_daoSrc);
	}

	@Override
	public boolean create(Equipment obj) {
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "]  creation.");
		return update(obj);
	}

	@Override
	public boolean delete(Equipment obj) {
		List<Home> homes = getDaoSrc().getData().getHomes();
		for (Home home : homes) {
			for (Area area : home.getAreas()) {
				for (Room room : area.getRooms()) {
					for (int i = 0; i < room.getEquipments().size(); i++) {
						if (room.getEquipments().get(i).equals(obj)) {
							room.getEquipments().remove(i);
							getDaoSrc().save();
							logger.debug(this.getClass().getName() + " -"
									+ obj.getClass().getName() + "object [id="
									+ obj.getId() + "]  deleted.");
							return true;
						}
					}
				}
			}
		}
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "]  unfound ; deletion skipped.");
		return false;
	}

	@Override
	public boolean update(Equipment obj) {
		logger.debug(this.getClass().getName() + " -"
				+ obj.getClass().getName() + "object [id=" + obj.getId()
				+ "] updating");
		delete(obj);
		boolean result = false;
		List<Home> homes = getDaoSrc().getData().getHomes();
		for (Home home : homes) {
			for (Area area : home.getAreas()) {
				for (Room room : area.getRooms()) {
					if (room.equals(obj.getRoom())) {
						result = room.getEquipments().add(obj);
					}
				}
			}
		}
		getDaoSrc().save();
		return result;
	}

	@Override
	public Equipment find(int id) {
		logger.debug(this.getClass().getName() + " - Eqp search [id=" + id
				+ "]");
		List<Home> homes = getDaoSrc().getData().getHomes();
		for (Home home : homes) {
			for (Area area : home.getAreas()) {
				for (Room room : area.getRooms()) {
					for (int i = 0; i < room.getEquipments().size(); i++) {
						if (room.getEquipments().get(i).getId() == id) {
							logger.debug(this.getClass().getName()
									+ " - Eqp search [id=" + id + "] succeed");
							return room.getEquipments().get(i);
						}
					}
				}
			}
		}
		logger.debug(this.getClass().getName() + " - Eqp search [id=" + id
				+ "] failed");
		return null;
	}

	@Override
	public List<Equipment> findAll() {
		logger.debug(this.getClass().getName() + " - Eqp search all");
		List<Equipment> equipments = new ArrayList<Equipment>();
		List<Home> homes = getDaoSrc().getData().getHomes();
		for (Home home : homes) {
			for (Area area : home.getAreas()) {
				for (Room room : area.getRooms()) {
					equipments.addAll(room.getEquipments());
				}
			}
		}
		return equipments;
	}

	@Override
	public SerializationSource getDaoSrc() {
		return (SerializationSource) super.getDaoSrc();
	}

}
