/**
 * 
 */
package dao.impl;

import java.io.File;

import junit.framework.Assert;
import main.Config;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import pojo.domain.Home;
import dao.DAO;
import dao.factory.DAOFactory;
import dao.impl.serialization.HomeSerializationDAO;

/**
 * Réalisée le 29 sept. 2012 Test unitaire de la DAO pour l'objet Home
 * 
 * @author Florent
 */
public class HomeSerializationDaoTest {
	private final static String testFileName = "dbTest.serial";

	private DAO<Home> daoHome = null;

	@AfterClass
	public static void clean() {
		new File(testFileName).delete();
		new File(testFileName + ".bak").delete();
	}

	@Before
	public void initConfigForTest() {
		System.out.println(" ----- Config initialization for next test -----");
		Config.getProps().setProperty("source.serialization.file", testFileName);
		daoHome = DAOFactory.getFactory().getHomeDAO();
		((HomeSerializationDAO) daoHome).getDaoSrc().initializeSource();
	}

	@Test
	public void testDaoInit() {
		Assert.assertTrue(daoHome.getDaoSrc().isInitialized());

		HomeSerializationDAO daoCast = (HomeSerializationDAO) daoHome;
		Assert.assertTrue(daoCast.getDaoSrc().getData() != null);
		Assert.assertTrue(daoCast.getDaoSrc().getData().getHomes() != null);
		Assert.assertTrue(daoCast.getDaoSrc().getData().getHomes().size() == 0);
	}

	@Test
	public void testCreateHome() {
		Home obj = new Home("Maison principale");
		Assert.assertTrue(daoHome.create(obj));

		HomeSerializationDAO daoCast = (HomeSerializationDAO) daoHome;
		Assert.assertTrue(daoCast.getDaoSrc().getData().getHomes().size() == 1);
		Assert.assertEquals(daoCast.getDaoSrc().getData().getHomes().get(0), obj);
	}

	@Test
	public void testCreateSameHome() {
		Home obj = new Home(1, "Maison principale");
		Home obj2 = new Home(1, "Maison principale");
		Assert.assertTrue(daoHome.create(obj));
		Assert.assertTrue(daoHome.create(obj2));

		HomeSerializationDAO daoCast = (HomeSerializationDAO) daoHome;
		Assert.assertTrue(daoCast.getDaoSrc().getData().getHomes().size() == 1);
	}

	@Test
	public void testUpdateHome() {
		Home obj = new Home(1, "Maison principale");
		Assert.assertTrue(daoHome.create(obj));

		obj.setName("Maison secondaire");
		Assert.assertTrue(daoHome.update(obj));
		Assert.assertEquals(daoHome.find(1), obj);

		Home obj2 = new Home(1, "Maison principale");
		Assert.assertTrue(daoHome.update(obj2));
		Assert.assertEquals(daoHome.find(1), obj2);
	}

	@Test
	public void testFindHome() {
		Home obj1 = new Home(1, "Maison principale");
		Home obj2 = new Home(2, "Maison principale");
		Home obj3 = new Home(3, "Maison principale");
		Assert.assertTrue(daoHome.create(obj1));
		Assert.assertTrue(daoHome.create(obj2));
		Assert.assertTrue(daoHome.create(obj3));

		Home search = daoHome.find(1);
		Assert.assertTrue(search != null);
		Assert.assertEquals(obj1, search);

		Assert.assertTrue(daoHome.findAll() != null);
		Assert.assertTrue(daoHome.findAll().size() == 3);
	}

	@Test
	public void testDeleteHome() {
		Home obj = new Home(1, "Maison principale");
		Assert.assertTrue(daoHome.create(obj));

		Assert.assertTrue(daoHome.delete(obj));
		Assert.assertTrue(daoHome.find(1) == null);
	}

	@Test
	public void testDeleteUnexistedHome() {
		Home obj = new Home(1, "Maison principale");
		Assert.assertTrue(!daoHome.delete(obj));
	}

	@Test
	public void testPersistence() {
		Home obj1 = new Home(1, "Maison principale 1");
		Home obj2 = new Home(2, "Maison principale 2");
		Home obj3 = new Home(3, "Maison principale 3");
		Assert.assertTrue(daoHome.create(obj1));
		Assert.assertTrue(daoHome.create(obj2));
		Assert.assertTrue(daoHome.create(obj3));

		// re-load
		((HomeSerializationDAO) daoHome).getDaoSrc().initialize(Config.getProps());
		Assert.assertTrue(daoHome.find(1) != null);
		Assert.assertTrue(daoHome.find(1).getName().equals("Maison principale 1"));
	}
}
