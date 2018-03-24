package manager.incidents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.Agent;
import domain.AgentKind;
import services.AgentsService;

@Component
public class InciValidator {

	@Autowired
	private AgentsService agentsServ;
	
	public boolean isEmergency(Incident incident) {
		Agent agent = agentsServ.getAgentById(incident.getAgentId());
		if(agent.getKind().equals(AgentKind.SENSOR)) {
			return incident.isEmergency();
		}
		return false;
	}
}
