package dbmanagement;

import domain.Agent;
import manager.incidents.Incident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDatabase implements Database{
	
	@Autowired
	private AgentsRepository users;
	@Autowired
	private IncidentRepository incidentRep;

    @Override
	public void updateInfo(Agent user) {
		users.save(user);
	}

	@Override
	public Agent getAgent(String name) {
		return users.findByName(name);
	}
	
	@Override
	public void saveIncident(Incident incident) {
		incidentRep.save(incident);
	}

}
