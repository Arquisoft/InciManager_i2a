package manager.incidents;

import org.springframework.stereotype.Component;

@Component
public class InciValidator {

	public boolean isEmergency(Incident incident) {
		
		if(incident.getAgent().getKind().equals("Sensor")) {
			return incident.isEmergency();
		}
		return false;
	}
}
