package util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import manager.incidents.Incident;

public class IncidentToJson extends JsonSerializer<Incident>{
	
	@Override
	public void serialize(Incident incident, 
			JsonGenerator jsonGenerator, SerializerProvider serProvider) throws IOException {
		jsonGenerator.writeStartObject();
		
		jsonGenerator.writeStringField("name", incident.getName());
		jsonGenerator.writeStringField("description", incident.getDescription());
		
		// incident's agent
		jsonGenerator.writeStringField("agentId", incident.getAgentId());
		jsonGenerator.writeStringField("kindCode", String.valueOf(incident.getKindCode()));
		
		// tags
		jsonGenerator.writeArrayFieldStart("tags");
		for(String tag: incident.getTags()) {
			jsonGenerator.writeString(tag);
		}
		jsonGenerator.writeEndArray();
		
		// location
		jsonGenerator.writeObjectFieldStart("location");
		jsonGenerator.writeStringField("lat", String.valueOf(incident.getLocation().getLat()));
		jsonGenerator.writeStringField("lng", String.valueOf(incident.getLocation().getLng()));
		jsonGenerator.writeEndObject();
		
		// state
		jsonGenerator.writeStringField("state", incident.getState().toString());
		
		//multimedia
		jsonGenerator.writeArrayFieldStart("multimedia");
		for(String multimedia: incident.getMultimedia()) {
			jsonGenerator.writeString(multimedia);
		}
		jsonGenerator.writeEndArray();
		
		//properties
		jsonGenerator.writeObjectFieldStart("properties");
		for (String key : incident.getProperties().keySet()) {
			jsonGenerator.writeObjectField(key, incident.getProperties().get(key));
		}
		jsonGenerator.writeEndObject();
		
		jsonGenerator.writeEndObject();
	}

}
