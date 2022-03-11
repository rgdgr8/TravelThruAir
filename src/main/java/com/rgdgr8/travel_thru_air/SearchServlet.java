package com.rgdgr8.travel_thru_air;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String date = req.getParameter("date").strip();
		String hour = req.getParameter("hour").strip();
		String from = req.getParameter("from").strip();
		String to = req.getParameter("to").strip();
		// String min = req.getParameter("min");
		System.out.println(from);
		System.out.println(to);
		System.out.println(date);
		System.out.println(hour/* +":"+min */);

		FlightsDAO dao = null;
		String driver = getServletContext().getInitParameter(FlightsDAO.DB_DRIVER);
		String url = getServletContext().getInitParameter(FlightsDAO.DB_URL);
		String user = getServletContext().getInitParameter(FlightsDAO.DB_USER);
		String pass = getServletContext().getInitParameter(FlightsDAO.DB_PASS);
		
		try {
			dao = FlightsDAO.newInstance(driver, url, user, pass);
			req.setAttribute("search", dao.search(date, hour, from, to));
			if (hour == null || hour.equals(""))
				hour = "all";
			if (date == null || date.equals(""))
				date = "all days";
			req.setAttribute("head",
					"<h2>Search result for " + date + " at " + hour + " hrs from " + from + " to " + to + "</h2>");
			RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
			rd.forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println(e);
		} finally {
			if (dao != null) {
				try {
					dao.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
