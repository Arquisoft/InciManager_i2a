package domain;

/**
 * Created by Nicolas on 15/02/2017. Class in charge of translating a User
 * object into the response format Note: this class only creates a model class
 * that contains a subset of the fields in the User class
 * Modified by Javier on 13/02/2018 to match the new requirements.
 */
public class AgentInfoAdapter {

	private Agent user;

	public AgentInfoAdapter(Agent user) {
		this.user = user;
	}

	public AgentInfo userToInfo() {
		return new AgentInfo(user.getUsername(), user.getLocation(), user.getEmail(), user.getUserId(),
				user.getKind().toString(), user.getKindCode());
	}
}
