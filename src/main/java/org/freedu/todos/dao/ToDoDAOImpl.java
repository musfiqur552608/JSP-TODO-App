package org.freedu.todos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.freedu.todos.model.ToDo;
import org.freedu.todos.utils.JDBCUtils;

public class ToDoDAOImpl implements ToDoDAO{

	private static final String INSERT_TODOS_SQL = "INSERT INTO todos (title, username, description, target_date,  is_done) VALUES (?, ?, ?, ?, ?);";

	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?";
	private static final String SELECT_ALL_TODOS = "select * from todos";
	private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
	private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";
	
	@Override
	public void insertTodo(ToDo todo) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TODOS_SQL)) {
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getUsername());
            preparedStatement.setString(3, todo.getDescription());
            preparedStatement.setDate(4, JDBCUtils.getSQLDate(todo.getTargetDate()));
            preparedStatement.setBoolean(5, todo.isStatus());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            JDBCUtils.printSQLException(exception);
        }
		
	}

	@Override
	public ToDo selectTodo(long todoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ToDo> selectAllTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTodo(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTodo(ToDo todo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
