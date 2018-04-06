package dbmanagement;

import manager.incidents.Incident;

/**
 * Created by Nicolás on 14/02/2017.
 * Interface service for the database. Current implementation uses Spring Boot Data MongoDB API
 * <a href="https://spring.io/guides/gs/accessing-data-mongodb/">link here</a>
 */
public interface Database {

	void saveIncident(Incident incident);

}
