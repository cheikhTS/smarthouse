package smarthouse.ejb.dao.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqliteSource extends DaoSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455111358921790333L;

	/**
	 * Url de configuration de la connexion
	 */
	private String url = null;

	/**
	 * Connexion vers la source
	 */
	private Connection conn = null;

	public SqliteSource(Properties props) {
		super(props);
	}

	@Override
	public void initialize(Properties prop) {
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			this.url = "jdbc:sqlite:" + prop.getProperty("source.sqlite.file");
			connect();
		} catch (final ClassNotFoundException e) {
			System.err.println("JDBC :: Librairie absente");
			System.exit(-1);
		} catch (final Exception e) {
			System.err.println("JDBC :: Erreur de connexion JDBC : "
					+ e.getMessage().split("\n")[0]);
			System.exit(-1);
		}
	}

	private void connect() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}

			this.conn = DriverManager.getConnection(this.url);
			Statement requeteBDD = this.conn.createStatement();
			requeteBDD.execute("PRAGMA foreign_keys=ON;");
			this.conn.setAutoCommit(false);

		} catch (final Exception e) {
			System.out.println("JDBC :: Erreur de connexion JDBC : "
					+ e.getMessage().split("\n")[0]);
			System.exit(-1);
		}
	}

	private void controlConn() {
		try {
			if (!this.conn.isValid(1)) {
				connect();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de recuperer un compte par son nom utilisateur
	 * 
	 * @param nom
	 *            Nom du compte
	 * @return
	 */
	public ResultSet getUser(String name) {
		String sql = "SELECT * FROM user WHERE LOWER(name)='"
				+ name.toLowerCase() + "'";

		PreparedStatement req;
		ResultSet res;
		try {
			controlConn();
			req = this.conn.prepareStatement(sql);

			res = req.executeQuery();

			return res.next() ? res : null;
		} catch (final SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Permet de vérifier l'authentification d'un utilisateur
	 * 
	 * @param nom
	 *            Nom du compte
	 * @return
	 */
	public boolean checkUser(String name, String password) {
		String sql = "SELECT * FROM user WHERE LOWER(name)='"
				+ name.toLowerCase() + "' AND LOWER(password)='"
				+ password.toLowerCase() + "'";

		PreparedStatement req;
		ResultSet res;
		try {
			controlConn();
			req = this.conn.prepareStatement(sql);

			res = req.executeQuery();

			return res.next();
		} catch (final SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Connection getConn() {
		return this.conn;
	}

	public String getUrl() {
		return this.url;
	}
}
