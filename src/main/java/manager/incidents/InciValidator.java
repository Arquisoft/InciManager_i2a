package manager.incidents;

import org.springframework.stereotype.Component;

import domain.AgentKind;

@Component
public class InciValidator {

	public boolean isEmergency(Incident incident) {
		
		if(incident.getAgent().getKind().equals(AgentKind.SENSOR)) {
			return incident.isEmergency();
		}
		return false;
	}
}
