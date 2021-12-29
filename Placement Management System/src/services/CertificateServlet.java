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

import entities.Certificate;
import repository.CertificateDao;


@WebServlet("/certificate")
public class CertificateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CertificateDao certificateDao;
	
	public void init() {
		certificateDao = new CertificateDao();
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
			case "certificatenew":
				showNewForm(request, response);
				break;
			case "certificateinsert":
				insertCertificate(request, response);
				break;
			case "certificatedelete":
				deleteCertificate(request, response);
				break;
			case "certificateedit":
				showEditForm(request, response);
				break;
			case "certificateupdate":
				updateCertificate(request, response);
				break;
			default:
				listCertificate(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listCertificate(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Certificate> listCertificate = certificateDao.getAllCertificate();
		request.setAttribute("listCertificate", listCertificate);
		RequestDispatcher dispatcher = request.getRequestDispatcher("certificateDisplay.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("certificateIndex.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Certificate existingCertificate = certificateDao.getCertificate(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("certificateIndex.jsp");
		request.setAttribute("certificate", existingCertificate);
		dispatcher.forward(request, response);

	}

	private void insertCertificate(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String year = request.getParameter("year");
		String college = request.getParameter("college");
		Certificate newCertificate = new Certificate( year, college);
		certificateDao.saveCertificate(newCertificate);
		response.sendRedirect("certificate?action=certificatelist");
	}

	private void updateCertificate(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String year = request.getParameter("year");
		String college = request.getParameter("college");
		
		

		Certificate certificate = new Certificate(id, year, college);
		certificateDao.updateCertificate(certificate);
		response.sendRedirect("certificate?action=certificatelist");
	}

	private void deleteCertificate(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		certificateDao.deleteCertificate(id);
		response.sendRedirect("certificate?action=certificatelist");
	}
}
