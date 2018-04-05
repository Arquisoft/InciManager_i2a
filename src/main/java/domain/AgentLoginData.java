package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class AgentLoginData {

	private String login;
	private String password;
	private String kind;
	private List<String> kindCodes = new ArrayList<String>();

	public AgentLoginData(){
		identifyAgentType();
	}

	public AgentLoginData(String login, String password, String kind) {
		this.login = login;
		this.password = password;
		this.setKind(kind);
		identifyAgentType();
	}

	public String getLogin() {
		return login;
	}

    public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public List<String> getKindCodes() {
		return kindCodes;
	}
	
	/**
	 * This method parses the CSV file in order to make sure that the type of agent
	 * is allowed
	 */
	private void identifyAgentType() {
		try (BufferedReader br = new BufferedReader(new FileReader("agentTypes.csv"))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] agentType = line.split(",");
				String kind = agentType[1];
				kindCodes.add(kind);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
