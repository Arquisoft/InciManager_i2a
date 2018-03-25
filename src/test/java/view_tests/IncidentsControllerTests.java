package view_tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import cucumber.AbstractSteps;

public class IncidentsControllerTests extends AbstractSteps {

	@Test
	public void getLoginPage() throws IOException {
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is2xxSuccessful());
	}
	
	@Test
	public void getInvalidPage() throws IOException {
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080/foo");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is4xxClientError());
	}
	
	@Test
	public void getIncidentAdd() throws IOException {
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080/incident/add");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is2xxSuccessful());
	}
	
	@Test
	public void getSensorIncidentAdd() throws IOException{
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080/incident/sensorAdd");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is2xxSuccessful());
	}
	
	@Test
	public void getIncidentList() throws IOException {
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080/incident/list");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is5xxServerError());
	}
	
	@Test
	public void getIncidentDetail() throws IOException {
		boolean isOkay = true;
		try {
			executeGet("http://localhost:8080/incident/details/4");
		} catch (IOException e) {
			isOkay=false;
		}
		assertTrue(isOkay);
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertTrue(currentStatusCode.is5xxServerError());
	}
}
