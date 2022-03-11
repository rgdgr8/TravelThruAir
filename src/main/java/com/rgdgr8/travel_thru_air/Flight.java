package com.rgdgr8.travel_thru_air;

import java.sql.Date;

public class Flight {
	private int id;
	private String deptCity;
	private String arrCity;
	private float cost;
	private Date deptDay;
	private int deptHour;
	private int deptMinute;

	public Flight(int id, String deptCity, String arrCity, float cost, Date deptDay, int deptHour, int deptMinute) {
		this.id = id;
		this.deptCity = deptCity;
		this.arrCity = arrCity;
		this.cost = cost;
		this.deptDay = deptDay;
		this.deptHour = deptHour;
		this.deptMinute = deptMinute;
	}

	public int getId() {
		return id;
	}

	public String getDeptCity() {
		return deptCity;
	}

	public String getArrCity() {
		return arrCity;
	}

	public float getCost() {
		return cost;
	}

	public Date getDeptDay() {
		return deptDay;
	}

	public int getDeptHour() {
		return deptHour;
	}

	public int getDeptMinute() {
		return deptMinute;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "[FlightID: " + id + "] (Time: " + deptDay + " " + deptHour + ":" + deptMinute + ") {Departure: " + deptCity
				+ ", Arrival: " + arrCity + "} Cost: " + cost;
	}
}
