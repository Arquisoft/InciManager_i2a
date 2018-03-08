package domain;

/**
 * Created by Nicolas on 15/02/2017. Class in charge of translating a User
 * object into the response format Note: this class only creates a model class
 * that contains a subset of the fields in the User class
 * Modified by Javier on 13/02/2018 to match the new requirements.
 */
public class UserInfoAdapter {

	private User user;

	public UserInfoAdapter(User user) {
		this.user = user;
	}

	public UserInfo userToInfo() {
		return new UserInfo(user.getName(), user.getLocation(), user.getEmail(), user.getUserId(),
				user.getKind(), user.getKindCode());
	}
}
