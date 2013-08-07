package smarthouse.ejb.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import smarthouse.ejb.dao.factory.DAOFactory;
import smarthouse.ejb.util.inject.DAOFactoryInjection;


/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {
	// use @SuppressWarnings to tell IDE to ignore warnings about field not
	// being referenced directly
	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext
	private EntityManager em;
	
	@Produces
	@Named("daoFactory")
	@DAOFactoryInjection
	public DAOFactory produceDAOFactory(InjectionPoint injectionPoint) {
		return DAOFactory.getFactory();
	}
}
