package services;

import java.util.List;

import manager.incidents.Incident;

public interface IncidentService {
	
	public void saveIncident(Incident incident);

	public List<Incident> getIncidentsByAgentId(String agent);
}
