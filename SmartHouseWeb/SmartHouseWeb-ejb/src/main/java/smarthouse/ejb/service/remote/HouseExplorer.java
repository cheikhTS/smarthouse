package smarthouse.ejb.service.remote;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

import smarthouse.ejb.entity.domain.Area;
import smarthouse.ejb.entity.domain.Equipment;
import smarthouse.ejb.entity.domain.Home;
import smarthouse.ejb.entity.domain.Room;
import smarthouse.fmk.runtime.definition.RuntimeEquipmentInfos;

@Remote
public interface HouseExplorer {
	/**
	 * Renvoi l'instance de la maison gérée par ce serveur
	 * @return Entity Home
	 * @throws Exception
	 */
	public Home getHome() throws Exception;
	
	/**
	 * Renvoi les informations détaillées d'un équipement.
	 * @param idEquipment Identifiant de l'équipement
	 * @return POJO RuntimeEquipmentInfos
	 * @throws Exception
	 */
	public RuntimeEquipmentInfos getEquipmentInfos(int idEquipment) throws Exception;
	
	/**
	 * Renvoi les différentes zones de la maison
	 * @return
	 * @throws Exception
	 */
	public List<Area> getAreas() throws Exception;
	
	/**
	 * Renvoi les pièces appartenant à une zone
	 * @param idArea
	 * @return
	 * @throws Exception
	 */
	public List<Room> getRooms(int idArea) throws Exception;
	
	/**
	 * Renvoi les équipements appartenant à une pièce
	 * @param idRoom
	 * @return
	 * @throws Exception
	 */
	public List<Equipment> getEquipments(int idRoom) throws Exception;
}
