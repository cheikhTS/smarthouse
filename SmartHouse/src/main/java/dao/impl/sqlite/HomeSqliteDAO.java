package dao.impl.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pojo.domain.Area;
import pojo.domain.Home;
import dao.DAO;
import dao.source.SqliteSource;

public class HomeSqliteDAO extends DAO<Home> {

	public HomeSqliteDAO ( SqliteSource _db ) {
		super(_db);
	}

	@Override
	public boolean create(Home newHome) {
		boolean retourRequete = true;

		try {
			Statement requeteBDD = getDaoSrc().getConn().createStatement();

			// Ajouts directs dans la table Home
			if ( requeteBDD.executeUpdate("INSERT INTO Home (nameHome)" + "VALUES ('" + newHome.getName() + "');") != 1 ) {
				retourRequete = false;
			} else {
				// MAJ de l'id de la maison
				ResultSet myResultSet = requeteBDD.executeQuery("SELECT last_insert_rowid()");
				newHome.setId(myResultSet.getInt("last_insert_rowid()"));
			}
			requeteBDD.close();

			// Ajouts indirects dans la table Area
			/*
			 * for ( Area newArea : newHome.getAreas() ) { if (
			 * DAOFactory.getFactory().getAreaDAO().create(newArea) != true ) {
			 * retourRequete = false; } }
			 */

			// MAJ de la base
			if ( retourRequete == true ) {
				getDaoSrc().getConn().commit();
			} else {
				getDaoSrc().getConn().rollback();
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			retourRequete = false;
		}

		return retourRequete;
	}

	@Override
	public boolean delete(Home home) {
		boolean retourRequete = true;

		try {
			Statement requeteBDD = getDaoSrc().getConn().createStatement();

			// Suppression directe dans les tables Home
			if ( requeteBDD.executeUpdate("DELETE FROM Home " + "WHERE idHome=" + home.getId() + ";") != 1 ) {
				retourRequete = false;
			}
			requeteBDD.close();

			// MAJ de la base
			if ( retourRequete == true ) {
				getDaoSrc().getConn().commit();
			} else {
				getDaoSrc().getConn().rollback();
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			retourRequete = false;
		}

		return retourRequete;
	}

	@Override
	public boolean update(Home home) {
		boolean retourRequete = true;

		try {
			Statement requeteBDD = getDaoSrc().getConn().createStatement();

			// Verification de l'existence de la maison
			ResultSet myResultSet = requeteBDD.executeQuery("SELECT * FROM Home WHERE idHome=" + home.getId() + ";");
			if ( myResultSet.next() == false ) { return false; }

			// Update directs dans la table Home
			if ( requeteBDD.executeUpdate("UPDATE Home " + "SET ('" + home.getName() + "') WHERE idHome=" + home.getId() + ";") != 1 ) {
				retourRequete = false;
			}
			requeteBDD.close();

			// Update indirects dans la table Area
			/*
			 * for ( Area area : home.getAreas() ) { if (
			 * DAOFactory.getFactory().getAreaDAO().update(area) != true ) {
			 * retourRequete = false; } }
			 */

			// MAJ de la base
			if ( retourRequete == true ) {
				getDaoSrc().getConn().commit();
			} else {
				getDaoSrc().getConn().rollback();
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			retourRequete = false;
		}

		return retourRequete;
	}

	@Override
	public Home find(int id) {
		Home home = null;
		ArrayList<Area> areas = new ArrayList<Area>();
		PreparedStatement requeteBDD;
		try {
			requeteBDD = getDaoSrc().getConn().prepareStatement("SELECT * FROM Home WHERE idHome= ?");
			requeteBDD.setInt(1, id);
			ResultSet result = requeteBDD.executeQuery();
			// Verification de l'existence de la home
			if ( !result.next() ) {
				throw new SQLException("Home inexistante :" + id);
			} else {
				result.getInt("idHome");
				result.getString("nameHome");
			}

			// Recherche des zones
			requeteBDD = getDaoSrc().getConn().prepareStatement("SELECT idArea FROM Area WHERE idHome= ?");
			requeteBDD.setInt(1, id);
			result = requeteBDD.executeQuery();
			/*
			 * while ( result.next() ) { Area area =
			 * DAOFactory.getFactory().getAreaDAO
			 * ().find(result.getInt("idArea")); area.setHome(home);
			 * areas.add(area); }
			 */
			requeteBDD.close();
			// Ajout des zones a la maison
			home.getAreas().addAll(areas);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return home;
	}

	// Utile non ?
	public ArrayList<Home> findAll() {
		ArrayList<Home> homes = new ArrayList<Home>();
		PreparedStatement requeteBDD;
		try {
			requeteBDD = getDaoSrc().getConn().prepareStatement("SELECT idHome FROM Home");
			ResultSet result = requeteBDD.executeQuery();
			while ( result.next() ) {
				Home home = find(result.getInt("idHome"));
				homes.add(home);
			}
			requeteBDD.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return homes;
	}

	@Override
	public SqliteSource getDaoSrc() {
		return (SqliteSource) super.getDaoSrc();
	}
}
