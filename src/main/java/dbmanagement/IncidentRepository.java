package dbmanagement;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Agent;
import manager.incidents.Incident;

@Repository
public interface IncidentRepository extends MongoRepository<Incident, ObjectId>{

	

	@Query("{ 'agentid' : ?0 }")
	List<Incident> findAllByAgentId(String agentid);
	
}
