package smarthouse.ejb.dao.impl.sqlite;

import java.util.List;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.SqliteSource;
import smarthouse.ejb.entity.pattern.Scenario;

public class ScenarioSqliteDAO extends AbstractDAO<Scenario> {

	public ScenarioSqliteDAO(SqliteSource _db) {
		super(_db);
	}

	@Override
	public boolean create(Scenario newScenario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Scenario obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Scenario obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Scenario find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqliteSource getDaoSrc() {
		return (SqliteSource) super.getDaoSrc();
	}

	@Override
	public List<Scenario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
