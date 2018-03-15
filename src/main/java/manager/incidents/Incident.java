package manager.incidents;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "incidents")
public class Incident {
	
	@Id
	private ObjectId id;

	private String name;
	private String description;
	private String username;
	private String password;
	private String tags;
	private String location;
	private InciState state;
	
	
	Incident(){}
	
	
	public Incident(String name, String description, String username, String password, String tags, String location,
			InciState state) {
		super();
		this.name = name;
		this.description = description;
		this.username = username;
		this.password = password;
		this.tags = tags;
		this.location = location;
		this.state = state;
	}
	
	

	@Override
	public String toString() {
		return "{"
				+ "name='" + name+"'" 
				+ ",description='" + description+"'"  
				+ ",username='" + username +"'" 
				+ ",password='"+ password+"'"  
				+ ",tags='" + tags +"'" 
				+ ",location='" + location +"'" 
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public InciState getState() {
		return state;
	}
	public void setState(InciState state) {
		this.state = state;
	}
	
	

}
