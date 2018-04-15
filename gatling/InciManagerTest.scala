
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class InciManagerTest extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8070")
		.inferHtmlResources()
		.acceptHeader("text/css,*/*;q=0.1")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Accept" -> "*/*")



	val scn = scenario("InciManagerTest")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(404)),
            http("request_2")
			.get("/signin.css"),
            http("request_3")
			.get("/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(404))))
		.pause(18)
		.exec(http("request_4")
			.post("/userForm")
			.headers(headers_0)
			.formParam("login", "Prueba01")
			.formParam("password", "4[[j[frVCUMJ>hU")
			.formParam("kind", "PERSON")
			.formParam("Enter", "enter")
			.resources(http("request_5")
			.get("/signin.css")))
		.pause(6)
		.exec(http("request_6")
			.get("/incident/add")
			.headers(headers_0)
			.resources(http("request_7")
			.get("/incident/signin.css")
			.check(status.is(404)),
            http("request_8")
			.get("/incident/signin.css")
			.check(status.is(404))))
		.pause(84)
		.exec(http("request_9")
			.post("/send")
			.headers(headers_0)
			.formParam("name", "Incident")
			.formParam("description", "I'm testing Gatling performance")
			.formParam("tags", "gatling,testing,asw")
			.formParam("lat", "77")
			.formParam("lon", "77")
			.formParam("multimedia", "test.jpeg")
			.formParam("type", "RESCUE")
			.formParam("properties", "song:thejoker,artist:stevemillerband")
			.formParam("Enter", "enter")
			.resources(http("request_10")
			.get("/signin.css")))
		.pause(2)
		.exec(http("request_11")
			.get("/incident/list")
			.headers(headers_0)
			.resources(http("request_12")
			.get("/incident/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(404)),
            http("request_13")
			.get("/incident/signin.css")
			.check(status.is(404)),
            http("request_14")
			.get("/incident/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(404)),
            http("request_15")
			.get("/incident/signin.css")
			.check(status.is(404))))
		.pause(1)
		.exec(http("request_16")
			.get("/incident/details/5ad318a409108f1da0161632")
			.headers(headers_0)
			.resources(http("request_17")
			.get("/incident/details/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(400)),
            http("request_18")
			.get("/incident/details/signin.css")
			.check(status.is(400)),
            http("request_19")
			.get("/incident/details/bootstrap.min.js")
			.headers(headers_1)
			.check(status.is(400)),
            http("request_20")
			.get("/incident/details/signin.css")
			.check(status.is(400))))

	setUp(scn.inject(rampUsers(1000) over(60 seconds))).protocols(httpProtocol)
}