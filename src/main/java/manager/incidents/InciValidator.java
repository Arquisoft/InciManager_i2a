package manager.incidents;

import org.springframework.stereotype.Component;

import com.mongodb.util.JSON;

import domain.AgentKind;
import net.minidev.json.JSONObject;

@Component
public class InciValidator {

	public boolean isEmergency(JSON incidentJSON) {
		
		incidentJSON.
		for(int i=0; i<dataJsonArray.length; i++) {
		   JSONObject dataObj = dataJsonArray.get(i);
		   String id = dataObj.getString("id");
		   //Similarly you can extract for other fields.
		}
		if(incidentJSON.getAgent().getKind().equals(AgentKind.SENSOR)) {
			return incidentJSON.isEmergency();
		}
		return false;
	}
}
