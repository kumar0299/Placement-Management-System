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

import entities.College;
import repository.CollegeDao;


@WebServlet("/college")
public class CollegeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CollegeDao collegeDao;
	
	public void init() {
		collegeDao = new CollegeDao();
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
			case "new":
				showNewForm(request, response);
				break;
			case "insert":
				insertCollege(request, response);
				break;
			case "delete":
				deleteCollege(request, response);
				break;
			case "edit":
				showEditForm(request, response);
				break;
			case "update":
				updateCollege(request, response);
				break;
			case "list":
				listCollege(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listCollege(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<College> listCollege = collegeDao.getAllCollege();
		request.setAttribute("listCollege", listCollege);
		RequestDispatcher dispatcher = request.getRequestDispatcher("collegeDisplay.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("collegeIndex.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		College existingCollege = collegeDao.getCollege(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("collegeIndex.jsp");
		request.setAttribute("college", existingCollege);
		dispatcher.forward(request, response);

	}

	private void insertCollege(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String collegedmin = request.getParameter("collegedmin");
		String collegename = request.getParameter("collegename");
		String location = request.getParameter("location");
		College newCollege = new College(collegedmin, collegename, location);
		collegeDao.saveCollege(newCollege);
		response.sendRedirect("college?action=list");
	}

	private void updateCollege(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String collegedmin = request.getParameter("collegedmin");
		String collegename = request.getParameter("collegename");
		String location = request.getParameter("location");
		
		

		College college = new College(id, collegedmin, collegename, location);
		collegeDao.updateCollege(college);
		response.sendRedirect("college?action=list");
	}

	private void deleteCollege(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		collegeDao.deleteCollege(id);
		response.sendRedirect("college?action=list");
	}
}
