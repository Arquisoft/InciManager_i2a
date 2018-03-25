package util;

import com.fasterxml.jackson.databind.module.SimpleModule;

import manager.incidents.Incident;

public class SerializerLinker extends SimpleModule{
	
	private static final long serialVersionUID = -4442230568888669762L;

	public SerializerLinker() {
		this.addSerializer(Incident.class, new IncidentToJson());
	}
}
