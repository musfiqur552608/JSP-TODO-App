package org.freedu.todos.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.freedu.todos.dao.ToDoDAO;
import org.freedu.todos.dao.ToDoDAOImpl;
import org.freedu.todos.model.ToDo;

@WebServlet("/")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ToDoDAO todoDAO = new ToDoDAOImpl();
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertTodo(request, response);
                    break;
                case "/delete":
                    deleteTodo(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTodo(request, response);
                    break;
                case "/list":
                    listTodo(request, response);
                    break;
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void listTodo(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, IOException, ServletException {
			List < ToDo > listTodo = todoDAO.selectAllTodos();
			request.setAttribute("listTodo", listTodo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo-list.jsp");
			dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		      RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
		      dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ToDo existingTodo = todoDAO.selectTodo(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo-form.jsp");
		request.setAttribute("todo", existingTodo);
		dispatcher.forward(request, response);

	}
	
	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String title = request.getParameter("title");
        String username = request.getParameter("username");
        String description = request.getParameter("description");

        /*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"),df);*/

        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
        ToDo newTodo = new ToDo(title, username, description, LocalDate.now(), isDone);
        todoDAO.insertTodo(newTodo);
        response.sendRedirect("list");
    }
	
	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        String title = request.getParameter("title");
        String username = request.getParameter("username");
        String description = request.getParameter("description");
        //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));

        boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
        ToDo updateTodo = new ToDo(id, title, username, description, targetDate, isDone);

        todoDAO.updateTodo(updateTodo);

        response.sendRedirect("list");
    }
	
	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        todoDAO.deleteTodo(id);
        response.sendRedirect("list");
    }

}
