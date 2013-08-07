package dao.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	public JpaSource ( Properties props ) {
		super(props);
	}

	@Override
	public void initialize(Properties prop) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(prop.getProperty("source.jpa.file"));
		setEm(emf.createEntityManager());
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
