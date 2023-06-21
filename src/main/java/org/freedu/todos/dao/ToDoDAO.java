package org.freedu.todos.dao;

import java.sql.SQLException;
import java.util.List;

import org.freedu.todos.model.ToDo;

public interface ToDoDAO {
	 void insertTodo(ToDo todo) throws SQLException;

	 ToDo selectTodo(long todoId);

	 List<ToDo> selectAllTodos();

	 boolean deleteTodo(int id) throws SQLException;

	 boolean updateTodo(ToDo todo) throws SQLException;
}
