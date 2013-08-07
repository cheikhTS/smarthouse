/**
 * 
 */
package dao.impl;

import junit.framework.Assert;
import main.Config;

import org.junit.Before;
import org.junit.Test;

import pojo.domain.Area;
import pojo.domain.DriverIdentifier;
import pojo.domain.Equipment;
import pojo.domain.Home;
import pojo.domain.Room;
import dao.DAO;
import dao.factory.DAOFactory;
import dao.impl.serialization.EquipmentSerializationDAO;
import dao.impl.serialization.HomeSerializationDAO;
import dao.source.SerializationSource;

/**
 * Réalisée le 29 sept. 2012 Test unitaire de la DAO pour l'objet Home
 * 
 * @author Florent
 */
public class EquipmentSerializationDaoTest {

	private DAO<Equipment> daoEquipment = null;
	private Home home = null;

	@Before
	public void initConfigForTest() {
		System.out.println(" ----- Config initialization for next test -----");
		Config.getProps().setProperty("source.serialization.file", "dbTest.serial");
		daoEquipment = DAOFactory.getFactory().getEquipmentDAO();
		((SerializationSource) daoEquipment.getDaoSrc()).initializeSource();

		Home obj = new Home(1, "Maison principale");
		Area area_niv0 = new Area("Niv0", obj);
		new Room("LacuisinecestSmith", area_niv0);
		home = obj;
		DAO<Home> daoHome = DAOFactory.getFactory().getHomeDAO();
		((HomeSerializationDAO) daoHome).getDaoSrc().initializeSource();
		daoHome.create(home);
	}

	@Test
	public void testDaoInit() {
		Assert.assertTrue(daoEquipment.getDaoSrc().isInitialized());

		EquipmentSerializationDAO daoCast = (EquipmentSerializationDAO) daoEquipment;
		Assert.assertTrue(daoCast.getDaoSrc().getData() != null);
		Assert.assertTrue(daoCast.findAll().size() == 0);
	}

	@Test
	public void testCreateEquipment() {
		String propert = "id=LAMPE1";
		Equipment light1 = null;
		try {
			DriverIdentifier drivId = new DriverIdentifier("uc.equipment.SimulatedLightDriver", "LightDriver.jar");
			light1 = new Equipment(home.getAreas().get(0).getRooms().get(0), "lampe1", drivId, propert);
			Assert.assertTrue(daoEquipment.create(light1));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		EquipmentSerializationDAO daoCast = (EquipmentSerializationDAO) daoEquipment;
		Assert.assertTrue(daoCast.findAll().size() == 1);
		Assert.assertTrue(daoCast.findAll().contains(light1));
	}
}
