package validators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import manager.incidents.IncidentDTO;

@Component
public class AddInciValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return IncidentDTO.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		IncidentDTO inci = (IncidentDTO) arg0;
		if(inci.getTags().startsWith(",")||inci.getTags().endsWith(",")) {
			arg1.rejectValue("tags", "Incorrect tag format");
		}
		if(inci.getMultimedia().startsWith(",")||inci.getMultimedia().endsWith(",")) {
			arg1.rejectValue("multimedia", "Incorrect multimedia format");
		}
		if(inci.getProperties().startsWith(",")||inci.getProperties().endsWith(",")
				||inci.getProperties().startsWith(":")||inci.getProperties().endsWith(":")) {
			arg1.rejectValue("properties", "Incorrect properties format");
		}
		HashMap<String, Object> properties = new HashMap<String, Object>();
		try {
			if (inci.getProperties() != null && !inci.getProperties().isEmpty()) {
				List<String> propList = Arrays.asList(inci.getProperties().split(","));
				for (String p : propList) {
					List<String> ar = Arrays.asList(p.split(":"));
					properties.put(ar.get(0), ar.get(1));
				}
			}
		}catch(Exception e) {
			arg1.rejectValue("properties", "Incorrect properties format");
		}
	}

}
