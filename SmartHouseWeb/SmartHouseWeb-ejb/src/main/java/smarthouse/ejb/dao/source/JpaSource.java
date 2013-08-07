package smarthouse.ejb.dao.source;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaSource extends DaoSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455111358921790333L;

	private EntityManager em;

	public JpaSource(Properties props) {
		super(props);
	}

	@Override
	public void initialize(Properties prop) {
		System.out.println(prop.getProperty("source.jpa.persistenceName"));
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(prop
				.getProperty("source.jpa.persistenceName"));
		System.out.println(emf);
		setEm(emf.createEntityManager());
	}

	public EntityManager getEm() {
		return this.em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
