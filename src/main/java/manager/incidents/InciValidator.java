package manager.incidents;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.mongodb.util.JSON;

import domain.AgentKind;
import net.minidev.json.JSONObject;

@Component
public class InciValidator {

	public boolean isEmergency(Incident incident) {
		
		if(incident.getAgent().getKind().equals(AgentKind.SENSOR)) {
			return incident.isEmergency();
		}
		return false;
	}
}
