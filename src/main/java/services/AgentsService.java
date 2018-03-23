package services;

import domain.Agent;
import domain.AgentKind;

/**
 * Created by Nicolás on 14/02/2017. Façade for the business layer
 * implementation
 */

public interface AgentsService {

	/**
	 * Given the data of a user, checks if there's such an user, and if the
	 * password matches
	 * 
	 * @param name
	 *            The login name for the user
	 * @param password
	 *            The password given on the credentials
	 * @return Either the proper user, if the user exists and the password
	 *         matches. Null otherwise
	 */
	Agent getAgent(String name, String password, AgentKind kind);

	/**
	 * Updates the password for the given user
	 * 
	 * @param user
	 *            The given user
	 * @param newPassword
	 *            The new password
	 */
	void updateInfo(Agent user, String newPassword);
	
	Agent getAgent(String name, String password);

}
