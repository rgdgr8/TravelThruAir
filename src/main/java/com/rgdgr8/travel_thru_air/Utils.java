package com.rgdgr8.travel_thru_air;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Utils {
	private static HashMap<String, List<Flight>> getAdjacencyList(List<Flight> flights) {
		HashMap<String, List<Flight>> edgeList = new HashMap<String, List<Flight>>();
		for (Flight f : flights) {
			List<Flight> temp = null;
			if (edgeList.containsKey(f.getDeptCity())) {
				temp = edgeList.get(f.getDeptCity());
			} else {
				temp = new ArrayList<Flight>();
				edgeList.put(f.getDeptCity(), temp);
			}
			temp.add(f);
		}

		return edgeList;
	}

	private static void dfs(HashMap<String, List<Flight>> edgeList, String to, String cur, List<Flight> path,
			List<List<Flight>> paths, HashMap<String, Boolean> vis) {

		//System.out.println("dfs cur=" + cur);

		vis.put(cur, true);
		if (!cur.equals(to)) {
			if (edgeList.containsKey(cur)) {
				for (Flight f : edgeList.get(cur)) {
					if (vis.getOrDefault(f.getArrCity(), false) == false) {
						path.add(f);
						dfs(edgeList, to, f.getArrCity(), path, paths, vis);
						path.remove(path.size() - 1);
					}
				}
			}
		} else {
			paths.add(new ArrayList<Flight>(path));
		}
		vis.put(cur, false);
	}

	public static List<List<Flight>> dfs(List<Flight> flights, String from, String to) {
		HashMap<String, List<Flight>> edgeList = getAdjacencyList(flights);

//		for (String k : edgeList.keySet()) {
//			System.out.println(k);
//			for (Flight f : edgeList.get(k)) {
//				System.out.println(f);
//			}
//		}

		List<List<Flight>> paths = new ArrayList<List<Flight>>();
		dfs(edgeList, to, from, new ArrayList<Flight>(), paths, new HashMap<String, Boolean>());
		return paths;
	}
}
