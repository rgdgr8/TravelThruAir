package com.rgdgr8.travel_thru_air;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectingListener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String driver = sce.getServletContext().getInitParameter(FlightsDAO.DB_DRIVER);
		String url = sce.getServletContext().getInitParameter(FlightsDAO.DB_URL);
		String user = sce.getServletContext().getInitParameter(FlightsDAO.DB_USER);
		String pass = sce.getServletContext().getInitParameter(FlightsDAO.DB_PASS);
		
		Connection con;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			sce.getServletContext().setAttribute("con", con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return;
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}
}
