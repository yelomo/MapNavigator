package com.tyczj.mapnavigator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Directions {
	
	private ArrayList<Route> routes = new ArrayList<Route>();
	private String directions;
	
	public enum DrivingMode{
		DRIVING,MASS_TRANSIT,BYCICLE,WALKING
	}
	
	public enum Avoid{
		TOLLS,HIGHWAYS,NONE
	}
	
	public Directions(String directions){
		this.directions = directions;
		
		if(directions != null){
			parseDirections();
		}

	}
	
	private void parseDirections(){
		try {
			JSONObject json = new JSONObject(directions);

			
			if(!json.isNull("routes")){
				JSONArray route = json.getJSONArray("routes");
				
				for(int k=0;k<route.length(); k++){
					
					JSONObject obj3 = route.getJSONObject(k);
					routes.add(new Route(obj3));
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Route> getRoutes(){
		return routes;
	}

	public String getDuration(){
		return routes.get(0).getDuration();
	}

	public String getLocalizedDurationDE(){
		String duration = routes.get(0).getDuration();
		//localize the string
		duration = duration.replace("days", "Tage");
		duration = duration.replace("day", "Tag");
		duration = duration.replace("hours", "Stunden");
		duration = duration.replace("hour", "Stunde");
		duration = duration.replace("mins", "Minuten");
		duration = duration.replace("min", "Minute");

		return duration;
	}

	public double getDistance(){
		double total = 0.0;
		for(Route route : routes){
			if(route.getDistance() != null){
				//the string looks like this 2,890.0 km so we have to get the first element before the
				//whitespace and replace the comma to parse a Double
				total = total + Double.parseDouble(route.getDistance().split("\\s+")[0].replace(",",""));
			}
		}
		return total;
	}
}
