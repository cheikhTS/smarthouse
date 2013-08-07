package dao.impl.serialization;

import java.util.List;

import main.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.domain.Area;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import pojo.pattern.Scenario;
import dao.DAO;
import dao.factory.DAOFactory;
import dao.source.DaoSource;
import dao.source.SerializationSource;

public class HomeSerializationDAO extends DAO<Home> {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	public HomeSerializationDAO ( DaoSource _daoSrc ) {
		super(_daoSrc);
	}

	private int getNewHomeId() {
		List<Home> homes = getDaoSrc().getData().getHomes();
		int maxid = 0;
		for ( Home h : homes ) {
			if ( h.getId() >= maxid ) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewAreaId(Home home) {
		List<Area> areas = home.getAreas();
		int maxid = 0;
		for ( Area h : areas ) {
			if ( h.getId() >= maxid ) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewRoomId(Area area) {
		List<Room> rooms = area.getRooms();
		int maxid = 0;
		for ( Room h : rooms ) {
			if ( h.getId() >= maxid ) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	private int getNewEquipementId(Room room) {
		List<Equipment> equipments = room.getEquipments();
		int maxid = 0;
		for ( Equipment h : equipments ) {
			if ( h.getId() >= maxid ) {
				maxid = h.getId() + 1;
			}
		}
		return maxid;
	}

	@Override
	public boolean create(Home obj) {
		logger.debug("HomeSerializationDAO - Home object [id=" + obj.getId() + "] creation");
		if ( obj.getId() == -1 ) {
			obj.setId(getNewHomeId());
		}
		return update(obj);
	}

	@Override
	public boolean delete(Home obj) {
		List<Home> homes = getDaoSrc().getData().getHomes();

		if ( homes.remove(obj) ) {
			// Suppression indirect des scenarios relies
			DAO<Scenario> scenarioDAO = DAOFactory.getFactory().getScenarioDAO();
			List<Scenario> scenarios = scenarioDAO.findAll();
			for ( int i = 0; i < scenarios.size(); i++ ) {
				if ( scenarios.get(i).getHome().equals(obj) ) {
					scenarioDAO.delete(scenarios.get(i));
				}
			}

			getDaoSrc().save();
			logger.debug("HomeSerializationDAO - Home object [id=" + obj.getId() + "] deleted");
			return true;
		}

		logger.debug("HomeSerializationDAO - Home object [id=" + obj.getId() + "] unfound ; deletion skipped.");
		return false;
	}

	@Override
	public boolean update(Home obj) {
		logger.debug("HomeSerializationDAO - Home object [id=" + obj.getId() + "] updating");
		delete(obj);

		// Renseignement des id indirect
		for ( Area area : obj.getAreas() ) {
			if ( area.getId() == -1 ) {
				area.setId(getNewAreaId(obj));
			}
			for ( Room room : area.getRooms() ) {
				if ( room.getId() == -1 ) {
					room.setId(getNewRoomId(area));
				}
				for ( Equipment eq : room.getEquipments() ) {
					if ( eq.getId() == -1 ) {
						eq.setId(getNewEquipementId(room));
					}
				}
			}
		}

		boolean result = getDaoSrc().getData().getHomes().add(obj);
		getDaoSrc().save();

		return result;
	}

	@Override
	public Home find(int id) {
		logger.debug("HomeSerializationDAO - Home search [id=" + id + "]");
		for ( Home h : getDaoSrc().getData().getHomes() ) {
			if ( h.getId() == id ) {
				logger.debug("HomeSerializationDAO - Home search [id=" + id + "] succeed");
				return h;
			}
		}
		logger.debug("HomeSerializationDAO - Home search [id=" + id + "] failed");
		return null;
	}

	@Override
	public List<Home> findAll() {
		logger.debug("HomeSerializationDAO - Home search all");
		return getDaoSrc().getData().getHomes();
	}

	@Override
	public SerializationSource getDaoSrc() {
		return (SerializationSource) super.getDaoSrc();
	}
}
