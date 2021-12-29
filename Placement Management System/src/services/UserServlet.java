package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import repository.UserDao;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	
	public void init() {
		userDao = new UserDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {
			case "unew":
				showNewForm(request, response);
				break;
			case "uinsert":
				insertUser(request, response);
				break;
			case "udelete":
				deleteUser(request, response);
				break;
			case "uedit":
				showEditForm(request, response);
				break;
			case "uupdate":
				updateUser(request, response);
				break;
			case "ulist":
				listUser(request, response);
				break;
			case "uAuthenticate":
				try {
					userAuthenticate(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.getAllUser();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("userDisplay.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("userIndex.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.getUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("userIndex.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		User newUser = new User(name, password, type);
		userDao.saveUser(newUser);
		response.sendRedirect("user?action=ulist");
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		
		

		User user = new User(id, name, password, type);
		userDao.updateUser(user);
		response.sendRedirect("user?action=ulist");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDao.deleteUser(id);
		response.sendRedirect("user?action=ulist");
	}
	private void userAuthenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		if (userDao.userValidate(name, password)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("UserDashboard.html");
			dispatcher.forward(request, response);
		} else {
			System.out.println("Login not successful..");
		}
	}
}
