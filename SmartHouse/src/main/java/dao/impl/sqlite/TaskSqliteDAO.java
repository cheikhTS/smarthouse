package dao.impl.sqlite;

import java.util.List;

import pojo.pattern.Task;
import dao.DAO;
import dao.source.SqliteSource;

public class TaskSqliteDAO extends DAO<Task> {

	public TaskSqliteDAO ( SqliteSource _db ) {
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
