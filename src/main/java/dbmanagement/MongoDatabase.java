package dbmanagement;

import domain.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDatabase implements Database{
	
	@Autowired
	private AgentsRepository users;

    @Override
	public void updateInfo(Agent user) {
		users.save(user);
	}

	@Override
	public Agent getAgent(String name) {
		return users.findByName(name);
	}

}
