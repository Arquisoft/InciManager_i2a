package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dbmanagement.IncidentRepository;
import domain.Agent;
import manager.incidents.Incident;

@Service
public class IncidentServiceImpl implements IncidentService{

	@Autowired
	private IncidentRepository incidRep;
	
	@Override
	public void saveIncident(Incident incident) {
		incidRep.save(incident);
	}

	@Override
	public List<Incident> getIncidentsByAgentUsername(String agent) {
		List<Incident> incidents = new ArrayList<Incident>();
		incidents = incidRep.findAllByAgentId(agent);
		return incidents;
	}

}
