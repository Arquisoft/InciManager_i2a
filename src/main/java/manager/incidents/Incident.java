package manager.incidents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import domain.Agent;
import util.IncidentToJson;

@Document(collection = "incidents")
@JsonSerialize(using = IncidentToJson.class)
public class Incident {
	
//	@Autowired
//	private AgentsService agentServ;

	@Id
	private ObjectId id;

	private String name;
	private String description;

	private String agentId;
	private List<String> tags;
	private LatLng location;
	private InciState state;
	private Map<String, Object> properties;
	private List<String> multimedia;
	private int kindCode;
	
	private boolean emergency=false;
	
	
	public Incident(){}
	
	
	
	public Incident(String name, String description, String agentId, List<String> tags, LatLng location,
			InciState state, List<String> multimedia , int kindcode) {
		super();
		this.name = name;
		this.description = description;
		this.agentId=agentId;
		this.tags = tags;
		this.location = location;
		this.state = state;
		this.multimedia = multimedia;
		this.kindCode=kindcode;
	}
	
	public Incident(String name, String description, String agentId, List<String> tags, LatLng location,
			InciState state, HashMap<String,Object> properties,List<String> multimedia, int kindcode) {
		this.name = name;
		this.description = description;
		this.agentId=agentId;
		this.tags = tags;
		this.location = location;
		this.state = state;
		this.properties=properties;
		this.multimedia=multimedia;
		this.kindCode=kindcode;
	}
	
	public Incident(String name, String description, Agent agentId, List<String> tags, LatLng location,
			InciState state, List<String> multimedia, int kindcode) {
		super();
		this.name = name;
		this.description = description;
		this.agentId=agentId.getUserId();
		this.tags = tags;
		this.location = location;
		this.state = state;
		this.multimedia=multimedia;
		this.kindCode=kindcode;
	}
	
	public Incident(String name, String description, Agent agentId, List<String> tags, LatLng location,
			InciState state, HashMap<String,Object> properties,List<String> multimedia, int kindcode) {
		this.name = name;
		this.description = description;
		this.agentId=agentId.getUserId();
		this.tags = tags;
		this.location = location;
		this.state = state;
		this.properties = properties;
		this.multimedia = multimedia;
		this.kindCode=kindcode;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (properties != null) {
			sb.append(",properties='");
			Iterator<Entry<String, Object>> it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				sb.append(pair.getKey() + ":" + pair.getValue() + " ");

			}
			sb.append("'");
		}
		return "{" + "name='" + name + "'" + ",description='" + description + "'" + ",agent='" + agentId + "'"
				+ ",kindCode="+kindCode+",tags='" + tags + "'" + ",location='" + location.toString() + "'" + ",state='" + state + "'"
				+ ",multimedia='" + multimedia + "'"+sb.toString()
				+ ",emergency="+emergency+"}";
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

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

//	public Agent getAgent() {
//		return agentServ.getAgentById(agentId);
//	}

	public void setAgent(Agent agent) {
		this.agentId = agent.getUserId();
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		this.tags.add(tag);
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

	public List<String> getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(List<String> multimedia) {
		this.multimedia = multimedia;
	}

	public void addFile(String file) {
		this.multimedia.add(file);
	}

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}
	
	public int getKindCode() {
		return kindCode;
	}

	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}
	
}
