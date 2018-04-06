
package manager.incidents;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IncidentDTO {

	private String name;
	private String description;
	private String tags;
	private double lat;
	private double lon;
	private String multimedia;
	private String properties;
	private String type;

	public IncidentDTO() {
	}

	public IncidentDTO(String name, String description, String tags, double lat, double lon, String multimedia,
			String properties, String type) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.lat = lat;
		this.lon = lon;
		this.multimedia = multimedia;
		this.properties = properties;
		this.type = type;
	}

	public Incident getIncident() {
		List<String> tagList = Arrays.asList(tags.split(","));
		List<String> multiList = Arrays.asList(multimedia.split(","));
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("type", type);
		if (this.properties != null && !this.getProperties().isEmpty()) {
			List<String> propList = Arrays.asList(this.properties.split(","));
			for (String p : propList) {
				List<String> ar = Arrays.asList(p.split(":"));
				properties.put(ar.get(0), ar.get(1));
			}
		}
		return new Incident(name, description, "", tagList, new LatLng(lat, lon), InciState.OPEN, properties,
				multiList,0);
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
				+ ", lon=" + lon + ", type="+type+", multimedia=" + multimedia + ", properties=" + properties + "]";
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
