package services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import manager.producers.KafkaProducer;

@Service
public class InsertSampleDataService {

	@Autowired
	private KafkaProducer kafkaProducer;

	@PostConstruct
	public void init() throws InterruptedException {
		// Delete all documents in collection users
		MongoClientURI uri = new MongoClientURI("mongodb://admin:aswadmin2018@ds159489.mlab.com:59489/aswdb");
		MongoClient mongoClient = new MongoClient(uri);
		DB db = mongoClient.getDB("aswdb");
		DBCollection agents = db.getCollection("users");
		agents.remove(new BasicDBObject());

		DBCollection incidents = db.getCollection("incidents");
		incidents.remove(new BasicDBObject());

		//Insert agents
		String json ="{ "

			   +" 'name' : 'Prueba01',"
			   +" 'location' : 'Aviles',"
			   +" 'email' : 'prueba01@prueba.es',"
			   +" 'password' : 'khZZwjdhWwVbMdmOvz9eqBfKR1N6A+CdFBDM9c1dQduUnGewQyPRlBxB4Q6wT7Cq',"
			   +" 'userId' : '555555555A',"
			   +" 'kind' : 'PERSON',"
			   +" 'kindCode' : '1' }";
		DBObject dbObject = (DBObject)JSON.parse(json);

		agents.insert(dbObject);

		String json2 ="{ "

				   +" 'name' : 'Prueba02',"
				   +" 'location' : 'Aviles',"
				   +" 'email' : 'prueba02@prueba.es',"
				   +" 'password' : 'khZZwjdhWwVbMdmOvz9eqBfKR1N6A+CdFBDM9c1dQduUnGewQyPRlBxB4Q6wT7Cq',"
				   +" 'userId' : '555555555B',"
				   +" 'kind' : 'SENSOR',"
				   +" 'kindCode' : '3' }";
		DBObject dbObject2 = (DBObject)JSON.parse(json2);

		agents.insert(dbObject2);


	}

}
