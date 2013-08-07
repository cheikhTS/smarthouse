package smarthouse.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.ejb.service.remote.HouseExplorer;
import smarthouse.fmk.runtime.RuntimeDriverUtil;
import smarthouse.fmk.runtime.definition.RuntimeEquipmentInfos;

@Stateless
@WebService(name="HouseExplorer")
public class HouseExplorerBean implements HouseExplorer {
	
	private Logger logger = LoggerFactory.getLogger(HouseExplorerBean.class);
	private DAOFactory daoFactory = DAOFactory.getFactory();
	
	/**
	 * Fonction d'accès à la maison.
	 * @return
	 * @throws Si aucune maison n'est configuré (= base de données vide)
	 */
	public Home getHome() throws Exception {
		List<Home> homes = daoFactory.getHomeDAO().findAll();
		if(homes != null && homes.size() > 0) {
			if(homes.size() > 1) {
				logger.warn("More than one home are configured in database.");
			}
			
			return homes.get(0);
		}
		else {
			logger.error("No home configured in database.");
			throw new Exception("No home configured in database.");
		}
	}
	
	public RuntimeEquipmentInfos getEquipmentInfos(int idEquipment) throws Exception {
		Equipment eqp = daoFactory.getEquipmentDAO().find(idEquipment);
		return RuntimeDriverUtil.makeRuntimeEquipmentInfos(eqp);
	}
	
	@WebMethod(exclude = true)
	public List<Area> getAreas() throws Exception {
		return getHome().getAreas();
	}
	
	@WebMethod(exclude = true)
	public List<Room> getRooms(int idArea) throws Exception {
		return daoFactory.getRoomDAO().findByArea(idArea);
	}
	
	@WebMethod(exclude = true)
	public List<Equipment> getEquipments(int idRoom) throws Exception {
		return daoFactory.getEquipmentDAO().findByRoom(idRoom);
	}
}
