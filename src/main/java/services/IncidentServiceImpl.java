package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbmanagement.IncidentRepository;
import manager.incidents.Incident;

@Service
public class IncidentServiceImpl implements IncidentService{

	@Autowired
	IncidentRepository incidRep;
	
	@Override
	public void saveIncident(Incident incident) {
		incidRep.save(incident);
	}

}
