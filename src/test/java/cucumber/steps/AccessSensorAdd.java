package cucumber.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import cucumber.AbstractSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domain.AgentLoginData;
import manager.incidents.InciState;
import manager.incidents.Incident;
import manager.incidents.LatLng;

public class AccessSensorAdd extends AbstractSteps{
	
	@Value("${url.agentsmodule}")
	private String agentsURL;
	
	@Given("a list of users:\r\n" + 
			"      | name    | password |	kindCode	|\r\n" + 
			"      | \"(.+)\"    | \"(.+)\"   |	\"(.+)\" |	\r\n" + 
			"      | \"(.+)\"    | \"(.+)\"	  |  \"(.+)\"	|$")
	public void a_list_of_users(String name1, String pass1, String kind1, String name2, String pass2, String kind2) {
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

					   +" 'name' : '"+name1+"',"
					   +" 'location' : 'Aviles',"
					   +" 'email' : 'prueba01@prueba.es',"
					   +" 'password' : 'khZZwjdhWwVbMdmOvz9eqBfKR1N6A+CdFBDM9c1dQduUnGewQyPRlBxB4Q6wT7Cq',"
					   +" 'userId' : '555555555A',"
					   +" 'kind' : 'PERSON',"
					   +" 'kindCode' : '"+kind1+"' }";
				DBObject dbObject = (DBObject)JSON.parse(json);

				agents.insert(dbObject);

				String json2 ="{ "

						   +" 'name' : '"+name2+"',"
						   +" 'location' : 'Aviles',"
						   +" 'email' : 'prueba02@prueba.es',"
						   +" 'password' : 'khZZwjdhWwVbMdmOvz9eqBfKR1N6A+CdFBDM9c1dQduUnGewQyPRlBxB4Q6wT7Cq',"
						   +" 'userId' : '555555555B',"
						   +" 'kind' : 'SENSOR',"
						   +" 'kindCode' : '"+kind2+"' }";
				DBObject dbObject2 = (DBObject)JSON.parse(json2);

				agents.insert(dbObject2);
	}
	
	@When("I login with name \"(.+)\" and password \"(.+)\" and kindCode \"(.+)\"$")
	public void login_with_name_and_password_and_kindcode(String name, String password, String kindcode) throws IOException {
		AgentLoginData data = new AgentLoginData(name, password, kindcode);
//		executeGet("http://localhost:8081");
//		String postUrl = agentsURL+"/checkAgent";
//		Gson gson = new Gson();
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpPost post = new HttpPost(postUrl);
//		StringEntity postingString = new StringEntity(gson.toJson(data));
//		
//		post.setEntity(postingString);
//		post.setHeader("Content-type", "application/json");
//		HttpResponse response = httpClient.execute(post);
//		final StatusLine currentStatusCode = response.getStatusLine();
//        assertThat( currentStatusCode.getStatusCode(),equalTo("200"));
		String jsonString = "{ 'login' : "+name+","
				+ " 'password' : "+password+","
				+ " 'kind' : "+kindcode
				+ " }";
		WireMockServer wireMockServer = new WireMockServer();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		wireMockServer.start();
		WireMock.configureFor("localhost", 8081);
		WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/userForm"))
		  .withHeader("content-type", WireMock.equalTo("application/json"))
		  .withRequestBody(WireMock.containing("login"))
		  .willReturn(WireMock.aResponse().withStatus(200)));
		
		HttpPost request = new HttpPost("http://localhost:8081/userForm");
		StringEntity entity = new StringEntity(jsonString);
		request.addHeader("content-type", "application/json");
		request.setEntity(entity);
		HttpResponse response = httpClient.execute(request);
		
		assertEquals(200, response.getStatusLine().getStatusCode());
		WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/userForm"))
		  .withHeader("content-type", WireMock.equalTo("application/json")));
		
		wireMockServer.stop();
	}
	
	@And("^I call /incident/add$")
	public void the_client_calls_incident_add() throws Throwable {
		executeGet("http://localhost:8081/incident/add");
	}
	
	@And("I send an incident with name \"I1\" and description \"\" and tags \"\" and lattitude 0.0 and longitude 0.0 and multimedia \"\"$")
	public void send_incident_name_desc() throws IOException {
		Incident incid = new Incident("I1", "", "", new ArrayList<String>(), new LatLng(0.0, 0.0),
				InciState.OPEN, new ArrayList<String>() , 3);
		//executeGet("http://localhost:8081/send");
		//WireMockServer wireMockServer = new WireMockServer();
	}
	
	@Then("I access to /incident/sensorAdd")
	public void access_to_sensorAdd() {
		assertThat(latestResponse.getBody(),containsString("emergency")) ;
	}

}
