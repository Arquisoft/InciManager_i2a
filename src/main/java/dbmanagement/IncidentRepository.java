package dbmanagement;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import manager.incidents.Incident;

@Repository
public interface IncidentRepository extends MongoRepository<Incident, ObjectId>{

	
	
}
