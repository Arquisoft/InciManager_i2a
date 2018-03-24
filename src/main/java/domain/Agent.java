package domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import util.JasyptEncryptor;

/**
 * Created by Damian on 06/02/2017.
 * Modified by Javier on 15/02/2018 to match the new requirements.
 */

@Document(collection = "users")
public class Agent {

	@Id
	private ObjectId id;

	private String name;
	private String location;
	private String email;
	private String password;
	private String userId;
	private String kind;
	private int kindCode;
	

	public Agent() {

	}

	
	public Agent(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = encryptPass(password);
	}

	public Agent(String name, String location, String email, String password, String userId, String kind,
			int kindCode) {
		this(name, email, password);
		this.location = location;
		this.kind = kind;
		this.kindCode = kindCode;
		this.userId = userId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("name='").append(name).append('\'');
		sb.append(",location='").append(location).append('\'');
		sb.append(",email='").append(email).append('\'');
		sb.append(",id='").append(userId).append('\'');
		sb.append(",kind='").append(getKind().toString()).append('\'');
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

		Agent user = (Agent) o;

		return userId.equals(user.userId);

	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUserId() {
		return userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = encryptPass(password);
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String encryptPass(String password) {
		JasyptEncryptor encryptor = new JasyptEncryptor();
		return encryptor.encryptPassword(password);
	}
}
