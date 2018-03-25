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
		jsonGenerator.writeObjectFieldStart("agent");
		jsonGenerator.writeStringField("username", incident.getAgent().getUsername());
		jsonGenerator.writeStringField("password", incident.getAgent().getPassword());
		jsonGenerator.writeStringField("kind", incident.getAgent().getKind().toString());
		jsonGenerator.writeEndObject();
		
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
		jsonGenerator.writeArrayFieldStart("properties");
		for(String property: incident.getProperties().keySet()) {
			jsonGenerator.writeObjectField(property, incident.getProperties().get(property));
		}
		jsonGenerator.writeEndObject();
		
		jsonGenerator.writeEndObject();
	}

}
