package cucumber;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import main.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@SpringBootTest
public class AbstractSteps {
	protected static ResponseResults latestResponse = null;

	public RestTemplate restTemplate = null;
	protected MockHttpServletResponse result;

	protected void setResultMock(MockHttpServletResponse result) {
		this.result=result;
	}
	protected void executeGet(String url) throws IOException {
		final Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "application/json");
		final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
		final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}
		restTemplate.setErrorHandler(errorHandler);
		latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback,
				new ResponseExtractor<ResponseResults>() {
					@Override
					public ResponseResults extractData(ClientHttpResponse response) throws IOException {
						if (errorHandler.hadError) {
							return (errorHandler.getResults());
						} else {
							return (new ResponseResults(response));
						}
					}
				});

	}

	private class ResponseResultErrorHandler implements ResponseErrorHandler {
		private ResponseResults results = null;
		private Boolean hadError = false;

		private ResponseResults getResults() {
			return results;
		}

		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			hadError = response.getRawStatusCode() >= 400;
			return hadError;
		}

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			results = new ResponseResults(response);
		}
	}
}
