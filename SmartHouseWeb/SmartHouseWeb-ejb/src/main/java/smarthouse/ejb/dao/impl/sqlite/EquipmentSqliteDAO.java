package smarthouse.ejb.dao.impl.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.SqliteSource;
import smarthouse.ejb.entity.domain.Equipment;

public class EquipmentSqliteDAO extends AbstractDAO<Equipment> {

	public EquipmentSqliteDAO(SqliteSource _db) {
		super(_db);
	}

	@Override
	public boolean create(Equipment newEquipment) {
		boolean retourRequete = true;
		// Ajouts directs dans la table Equipment
		try {
			Statement requeteBDD = getDaoSrc().getConn().createStatement();
			/*
			 * if ( requeteBDD.executeUpdate(
			 * "INSERT INTO Equipment (idRoom, nameEquipment, driverPath, driverName, properties) "
			 * + "VALUES(" + newEquipment.getRoom().getId() + ",'" +
			 * newEquipment .getName() + "','" + newEquipment.getDriverPath() +
			 * "','" + newEquipment.getDriverName() + "','" +
			 * newEquipment.getPropertiesSerialized() + "');") != 1 ) {
			 * retourRequete = false; } else { // MAJ de l'id de l'équipement
			 * ResultSet myResultSet =
			 * requeteBDD.executeQuery("SELECT last_insert_rowid()");
			 * newEquipment.setId(myResultSet.getInt("last_insert_rowid()")); }
			 */
			requeteBDD.close();
		} catch (SQLException e) {
			System.err.println("Erreur lors de la creation de l'équipement. "
					+ e.getMessage());
			retourRequete = false;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			retourRequete = false;
		}

		return retourRequete;
	}

	@Override
	public boolean delete(Equipment equipment) {
		boolean retourRequete = true;

		try {
			Statement requeteBDD = getDaoSrc().getConn().createStatement();

			// Suppression directe dans les tables Equipment
			if (requeteBDD.executeUpdate("DELETE FROM Equipment "
					+ "WHERE idEquipment=" + equipment.getId() + ";") != 1) {
				retourRequete = false;
			}
			requeteBDD.close();

			// MAJ de la base
			if (retourRequete == true) {
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
	public boolean update(Equipment equip) {
		boolean retourRequete = true;
		PreparedStatement requeteBDD;
		try {
			requeteBDD = getDaoSrc().getConn().prepareStatement(
					"SELECT * FROM Equipment WHERE idEquipment= ?");
			requeteBDD.setInt(1, equip.getId());

			// Verification de l'existence de la zone
			if (!requeteBDD.executeQuery().next()) {
				throw new SQLException("Equipment inexistante :"
						+ equip.getId());
			}
			requeteBDD = getDaoSrc()
					.getConn()
					.prepareStatement(
							"UPDATE Equipment set nameEquipment = ?, idRoom = ?, driverPath = ?, driverName = ?, properties = ?  where idEquipment = ?");
			requeteBDD.setString(1, equip.getName());
			requeteBDD.setInt(2, equip.getRoom().getId());
			/*
			 * requeteBDD.setString(3, equip.getDriverPath());
			 * requeteBDD.setString(4, equip.getDriverName());
			 */
			// requeteBDD.setString(5, equip.getPropertiesSerialized());
			requeteBDD.setInt(6, equip.getId());

			// Update directs dans la table equipment
			if (requeteBDD.executeUpdate() != 1) {
				retourRequete = false;
			}
			requeteBDD.close();

			// MAJ de la base
			if (retourRequete == true) {
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
	public Equipment find(int id) {
		Equipment equipment = null;
		PreparedStatement requeteBDD;
		try {
			requeteBDD = getDaoSrc().getConn().prepareStatement(
					"SELECT * FROM Equipment WHERE idEquipment= ?");
			requeteBDD.setInt(1, id);
			ResultSet result = requeteBDD.executeQuery();

			if (!result.next()) {
				throw new SQLException("Equipment inexistante :" + id);
			} else {
				result.getInt("idEquipment");
				result.getString("nameEquipment");
				result.getString("driverPath");
				result.getString("driverName");
				result.getString("properties");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return equipment;
	}

	@Override
	public SqliteSource getDaoSrc() {
		return (SqliteSource) super.getDaoSrc();
	}

	@Override
	public List<Equipment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
