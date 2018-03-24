
package manager.incidents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import domain.Agent;

public class IncidentDTO {

	private String name;
	private String description;
	private String tags;
	private double lat;
	private double lon;
	private String multimedia;

	public IncidentDTO() {
	}

	public IncidentDTO(String name, String description, String tags, double lat, double lon, String multimedia) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.lat = lat;
		this.lon = lon;
		this.multimedia = multimedia;
	}
	
	public Incident getIncident() {
		List<String> tagList = Arrays.asList(tags.split(","));
		List<String> multiList = Arrays.asList(multimedia.split(","));
		return new Incident(name, description, "", tagList, new LatLng(lat,lon), InciState.OPEN, null, multiList);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(String multimedia) {
		this.multimedia = multimedia;
	}

	@Override
	public String toString() {
		return "IncidentDTO [name=" + name + ", description=" + description + ", tags=" + tags + ", lat=" + lat
				+ ", lon=" + lon + ", multimedia=" + multimedia + "]";
	}
	
	

}
