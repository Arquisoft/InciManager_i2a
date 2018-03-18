package manager.incidents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import domain.User;

@Document(collection = "incidents")
public class Incident {
	
	@Id
	private ObjectId id;

	private String name;
	private String description;
	private User agent;
	private String tags;
	private LatLng location;
	private InciState state;
	private Map<String, Object> properties;
	
	
	Incident(){}
	
	
	public Incident(String name, String description, User agent, String tags, LatLng location,
			InciState state) {
		super();
		this.name = name;
		this.description = description;
		this.agent=agent;
		this.tags = tags;
		this.location = location;
		this.state = state;
	}
	
	public Incident(String name, String description, User agent, String tags, LatLng location,
			InciState state, HashMap<String,Object> properties) {
		this.name = name;
		this.description = description;
		this.agent=agent;
		this.tags = tags;
		this.location = location;
		this.state = state;
		this.properties=properties;
	}
	
	

	@Override
	public String toString() {
		if(agent.getKind().equals("SENSOR")){
			StringBuilder sb = new StringBuilder();
			sb.append(",properties='");
			Iterator it = properties.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        sb.append(pair.getKey()+":"+pair.getValue()+" ");
		    }
		    sb.append("'");
		}
		return "{"
				+ "name='" + name+"'" 
				+ ",description='" + description+"'"  
				+ ",agent='" + agent.getUserId() +"'" 
				+ ",tags='" + tags +"'" 
				+ ",location='" + location.toString() +"'"
				+ ",state='" + state + "'}";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incident other = (Incident) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Map<String, Object> getProperties() {
		return properties;
	}


	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}


	public ObjectId getId() {
		return id;
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

	public User getAgent() {
		return agent;
	}


	public void setAgent(User agent) {
		this.agent = agent;
	}


	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public LatLng getLocation() {
		return location;
	}
	public void setLocation(LatLng location) {
		this.location = location;
	}
	public InciState getState() {
		return state;
	}
	public void setState(InciState state) {
		this.state = state;
	}
	
	

}
