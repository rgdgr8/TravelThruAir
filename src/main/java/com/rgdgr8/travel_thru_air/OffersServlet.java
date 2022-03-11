package com.rgdgr8.travel_thru_air;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/")
public class OffersServlet extends HttpServlet {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void init() throws ServletException {
		super.init();

		scheduler.scheduleAtFixedRate(() -> FlightsDAO.offset++, 0, 20, TimeUnit.SECONDS);// time interval for testing
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		FlightsDAO dao = null;
		String driver = getServletContext().getInitParameter(FlightsDAO.DB_DRIVER);
		String url = getServletContext().getInitParameter(FlightsDAO.DB_URL);
		String user = getServletContext().getInitParameter(FlightsDAO.DB_USER);
		String pass = getServletContext().getInitParameter(FlightsDAO.DB_PASS);
		
		try {
			dao = FlightsDAO.newInstance(driver, url, user, pass);
			req.setAttribute("offers", dao.getOffers());
			req.setAttribute("head", "<h1>Limited time offers (30% off)</h1>");
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

	@Override
	public void destroy() {
		super.destroy();
		
		scheduler.shutdown();
	}
}
