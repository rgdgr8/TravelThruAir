package com.rgdgr8.travel_thru_air;

import java.io.IOException;
import java.sql.Connection;

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
		Connection con = (Connection) getServletContext().getAttribute("con");
		
		try {
			dao = FlightsDAO.newInstance(con);
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
