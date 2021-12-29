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

import entities.Placement;
import repository.PlacementDao;


@WebServlet("/placement")
public class PlacementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PlacementDao placementDao;
	
	public void init() {
		placementDao = new PlacementDao();
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
			case "placementnew":
				showNewForm(request, response);
				break;
			case "placementinsert":
				insertPlacement(request, response);
				break;
			case "placementdelete":
				deletePlacement(request, response);
				break;
			case "placementedit":
				showEditForm(request, response);
				break;
			case "placementupdate":
				updatePlacement(request, response);
				break;
			default:
				listPlacement(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listPlacement(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Placement> listPlacement = placementDao.getAllPlacement();
		request.setAttribute("listPlacement", listPlacement);
		RequestDispatcher dispatcher = request.getRequestDispatcher("placementDisplay.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("placementIndex.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Placement existingPlacement = placementDao.getPlacement(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("placementIndex.jsp");
		request.setAttribute("placement", existingPlacement);
		dispatcher.forward(request, response);

	}

	private void insertPlacement(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String college = request.getParameter("college");
		String date = request.getParameter("date");
		String qualification = request.getParameter("qualification");
		String year = request.getParameter("year");
		Placement newPlacement = new Placement( name, college, date, qualification,year);
		placementDao.savePlacement(newPlacement);
		response.sendRedirect("placement?action=placementlist");
	}

	private void updatePlacement(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String college = request.getParameter("college");
		String date = request.getParameter("date");
		String qualification = request.getParameter("qualification");
		String year = request.getParameter("year");
		

		Placement placement = new Placement(id, name, college, date, qualification,year);
		placementDao.updatePlacement(placement);
		response.sendRedirect("placement?action=placementlist");
	}

	private void deletePlacement(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		placementDao.deletePlacement(id);
		response.sendRedirect("placement?action=placementlist");
	}
}
