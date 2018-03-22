package view;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import domain.Agent;
import domain.UserInfo;
import domain.UserInfoAdapter;
import domain.UserLoginData;
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

	public ResponseEntity<UserInfo> execute(UserLoginData info) {
		Agent user = part.getAgent(info.getLogin(), info.getPassword(),
				info.getKind());
		UserInfoAdapter data = new UserInfoAdapter(user);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(data.userToInfo(), HttpStatus.OK);
	}
}
