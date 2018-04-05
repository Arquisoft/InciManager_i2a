package domain;

import java.io.Serializable;

/**
 * Created by Nicolas on 15/02/2017. Class that serves as a response for the
 * service, providing a subset of the User class' fields
 * Modified by Javier on 13/02/2018 to match the new requirements.
 * 
 */
public class AgentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String location;
	private String email;
	private String id;
	private String kind;
	private int kindCode;

	public AgentInfo() {

	}

	public AgentInfo(String name, String location, String email, String id, String kind, int kindCode) {
		this.name = name;
		this.location = location;
		this.email = email;
		this.id = id;
		this.kind = kind;
		this.kindCode = kindCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getKindCode() {
		return kindCode;
	}

	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("name='").append(name).append('\'');
		sb.append(",location='").append(location).append('\'');
		sb.append(",email='").append(email).append('\'');
		sb.append(",id='").append(id).append('\'');
		sb.append(",kind='").append(kind).append('\'');
		sb.append(",kindCode='").append(kindCode).append("'");
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AgentInfo userInfo = (AgentInfo) o;
		return id.equals(userInfo.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
