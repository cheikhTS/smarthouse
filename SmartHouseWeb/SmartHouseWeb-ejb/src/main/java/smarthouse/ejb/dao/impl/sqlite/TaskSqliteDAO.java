package smarthouse.ejb.dao.impl.sqlite;

import java.util.List;

import smarthouse.ejb.dao.AbstractDAO;
import smarthouse.ejb.dao.source.SqliteSource;
import smarthouse.ejb.entity.pattern.Task;

public class TaskSqliteDAO extends AbstractDAO<Task> {

	public TaskSqliteDAO(SqliteSource _db) {
		super(_db);
	}

	@Override
	public boolean create(Task obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Task obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Task obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Task find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqliteSource getDaoSrc() {
		return (SqliteSource) super.getDaoSrc();
	}

	@Override
	public List<Task> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
