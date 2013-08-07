/**
 * 
 */
package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.factory.DAOFactory;

import pojo.domain.Area;
import pojo.domain.Home;
import pojo.domain.Room;

/**
 * @author Florent
 * 
 */
public class LogExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure(LogExample.class.getResource("/log4j.xml"));
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("SmartHouse");
		//EntityManager em = emf.createEntityManager();

		//EntityTransaction transac = em.getTransaction();
		//transac.begin();

		Home h2 = new Home("Ma maison");
		Area a1 = new Area("Ma zone", h2);
		Room r1 = new Room("Ma pièce", a1);
		DAOFactory.getFactory().getHomeDAO().create(h2);
		
		System.out.println(h2.getName());
		System.out.println(h2.getAreas().size());
		//em.flush();
		
		DAOFactory.getFactory().getHomeDAO().delete(h2);
		
		//transac.commit();
		
		
		
		//em.close();
		//emf.close();

	}

}
