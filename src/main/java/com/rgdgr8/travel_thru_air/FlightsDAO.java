package com.rgdgr8.travel_thru_air;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FlightsDAO {
	private static final String FLIGHTS_TABLE = "flights";
	private static final String ID = "id";
	private static final String DEPT_CITY = "dept_city";
	private static final String ARR_CITY = "arr_city";
	private static final String COST = "cost";
	private static final String DAY = "dept_day";
	private static final String HOUR = "dept_hour";
	private static final String MINUTE = "dept_minute";
	
	public static final String DB_DRIVER = "DB_DRIVER";
	public static final String DB_URL = "DB_URL";
	public static final String DB_USER = "DB_USER";
	public static final String DB_PASS = "DB_PASS";

	public static int offset = -1;

	private final static List<Flight> offers = new ArrayList<Flight>();

	private static FlightsDAO dao = null;
	private Connection con;
	private Statement stmt;

	public static FlightsDAO newInstance(String driver, String url, String user, String pass) {
		if (dao == null) {
			dao = new FlightsDAO(driver, url, user, pass);
		}
		return dao;
	}

	private FlightsDAO(String driver, String url, String user, String pass) {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			stmt = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Flight> getOffers() throws Exception {
		ResultSet set = stmt.executeQuery("select count(*) from " + FLIGHTS_TABLE);
		while (set.next()) {
			offset %= set.getLong(1);
		}

		offers.clear();
		StringBuilder sb = new StringBuilder("");

		sb.append("select * from ");
//		sb.append(ID);
//		sb.append(",");
//		sb.append(DEPT_CITY);
//		sb.append(",");
//		sb.append(ARR_CITY);
//		sb.append(",");
//		sb.append(COST);
//		sb.append(",");
//		sb.append(DAY);
//		sb.append(",");
//		sb.append(HOUR);
//		sb.append(",");
//		sb.append(MINUTE);
		sb.append(FLIGHTS_TABLE);
//		sb.append(" natural join ");
//		sb.append(OFFERS_TABLE);
		sb.append(" limit 3 offset ");
		sb.append(offset);

		set = stmt.executeQuery(sb.toString());
		while (set.next()) {
			offers.add(new Flight(set.getInt(1), set.getString(2), set.getString(3),
					set.getFloat(4) * 0.7f /* 30% off */, set.getDate(5), set.getInt(6), set.getInt(7)));
		}
		set.close();

		return offers;
	}

	public List<List<Flight>> search(String date, String hour/* , String min */, String from, String to)
			throws Exception {
		List<Flight> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder("");

		sb.append("select * from ");
		sb.append(FLIGHTS_TABLE);
		sb.append(" where ");
		sb.append(DEPT_CITY);
		sb.append("=");
		sb.append("'" + from + "'");
		sb.append(" or ");
		sb.append(ARR_CITY);
		sb.append("=");
		sb.append("'" + to + "'");
		if (date != null && !date.equals("")) {
			sb.append(" and ");
			sb.append(DAY);
			sb.append("=");
			sb.append("'" + date + "'");
		}
		if (hour != null && !hour.equals("")) {
			sb.append(" and ");
			sb.append(HOUR);
			sb.append("=");
			sb.append(hour);
//		sb.append(" and ");
//		sb.append(MINUTE);
//		sb.append("=");
//		sb.append(min);
		}
		System.out.println(sb.toString());

		ResultSet set = stmt.executeQuery(sb.toString());
		while (set.next()) {
			Flight t = new Flight(set.getInt(1), set.getString(2), set.getString(3), set.getFloat(4), set.getDate(5),
					set.getInt(6), set.getInt(7));

			int ind = offers.indexOf(t);
			if (ind!=-1) {
				t = offers.get(ind);
			}
			res.add(t);
		}
		set.close();

		System.out.println(res);

		List<List<Flight>> flights = Utils.dfs(res, from, to);

		PrintStream out = System.out;
		for (int i = 0; i < flights.size(); i++) {
			out.println("Route " + (i + 1));
			for (Flight f : flights.get(i)) {
				out.println(f);
			}
		}

		return flights;
	}

	public void close() throws Exception {
		con.close();
		stmt.close();
		dao = null;
	}
}
