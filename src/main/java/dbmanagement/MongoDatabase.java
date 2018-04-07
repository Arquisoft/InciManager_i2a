package dbmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import manager.incidents.Incident;

@Service
public class MongoDatabase implements Database{
	
	@Autowired
	private IncidentRepository incidentRep;
	
	@Override
	public void saveIncident(Incident incident) {
		incidentRep.save(incident);
	}

}
