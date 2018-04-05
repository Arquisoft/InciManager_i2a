package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import domain.Agent;
import domain.AgentInfo;
import domain.AgentInfoAdapter;
import domain.AgentLoginData;
import services.AgentsService;

/**
 * Created by Nicolás on 17/02/2017. Class that handles the login data response.
 * Access the service layer and recovers the user Modified by Marcos on
 * 17/02/2018
 */
public class UserResponseAction {
	private final AgentsService part;

	UserResponseAction(AgentsService part) {
		this.part = part;
	}

	public ResponseEntity<AgentInfo> execute(AgentLoginData info) {
		Agent user = part.getAgent(info.getLogin(), info.getPassword(),
				info.getKind());
		AgentInfoAdapter data = new AgentInfoAdapter(user);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(data.userToInfo(), HttpStatus.OK);
	}
}
