package org.freedu.todos.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.freedu.todos.dao.UserDAO;
import org.freedu.todos.model.User;

@WebServlet("/register")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	UserDAO userDao = new UserDAO();
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("register.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		register(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User employee = new User();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setUsername(username);
        employee.setPassword(password);

        try {
            int result = userDao.registerEmployee(employee);
            if (result == 1 && password.length()>=6) {
                request.setAttribute("NOTIFICATION", "User Registered Successfully!");
            }
            else {
            	request.setAttribute("NOTIFICATION", "User Registered Failed!");
            	request.setAttribute("firstName", firstName);
            	request.setAttribute("lastName", lastName);
            	request.setAttribute("username", username);
            	request.setAttribute("password", password);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

}
