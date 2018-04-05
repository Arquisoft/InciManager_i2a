package services;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import manager.producers.KafkaProducer;

@Service
public class InsertSampleDataService {

	@Autowired
	private KafkaProducer kafkaProducer;

	@PostConstruct
	public void init() throws InterruptedException {
		// Delete all documents in collection users
		MongoClient mongoClient = new MongoClient("localhost", 27017);
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

		// Runs sensor simulation in background
		Runnable runnable = new Runnable() {

			public void run() {
				while (true) {
					// ------- code for task to run
					try {
						sensorSimulator(incidents);
					} catch (InterruptedException e1) {

						e1.printStackTrace();
					}
					// ------- ends here

				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();

	}

	private void sensorSimulator(DBCollection incidents) throws InterruptedException {
		Random rand = new Random();


		while(true)
		{
			int temperature = rand.nextInt(60);
			boolean emergency = false;
			if (temperature > 35) {
				emergency = true;
			}

			String incidentJson = "{ "

					+ " '_class' : 'manager.incidents.Incident'," + " 'name' : 'Temperature',"
					+ " 'description' : 'Hight temperature'," 
					+ " 'agentId' : 'Prueba02'," 
					+ " 'kindCode': '3',"
					+ " 'tags' : ['temperature'],"
					+ " 'location' : {'lat' : 1234.0, 'lng' : 1234.0}," + " 'state' : 'OPEN',"
					+ " 'properties' : {'temperature' : '" + temperature + "'}," + " 'multimedia' : [''],"
					+ " 'emergency' : " + emergency + "}";

			DBObject incidentObj = (DBObject) JSON.parse(incidentJson);

			incidents.insert(incidentObj);
			if (emergency) {
				kafkaProducer.send("incident", incidentJson);}
			Thread.sleep(3000);

	}

	}
}
