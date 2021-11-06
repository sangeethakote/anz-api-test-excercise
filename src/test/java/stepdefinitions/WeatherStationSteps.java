package stepdefinitions;

import org.json.JSONObject;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherStationSteps {

	String register_url = "http://api.openweathermap.org/data/3.0/stations";
	RequestSpecification request = RestAssured.given();
	String appid = "75c6128d133113b7e849fd7688e11c9a";
	String stationid;
	Response response;

	@When("I try to register weather station without APIKey in request")
	public void I_try_to_register_weather_station_without_APIKey_in_request() {
		response = request.post(register_url);
	}

	@Then("I expect the response with status code {int} and {string}")
	public void i_expect_the_response_with_status_code_and(int code, String message) {

		Assert.assertEquals(code, response.getStatusCode());
		Assert.assertEquals(code, response.getBody().jsonPath().getInt("cod"));
		Assert.assertEquals(message, response.getBody().jsonPath().getString("message"));
	}

	@When("I try to register weather station with valid APIKey and values {string},{string},{double},{double},{int}")
	public void i_try_to_register_weather_station_with_valid_api_key_and_values(String external_id, String name,
			Double latitude, Double longitude, int altitude) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("external_id", external_id);
		requestParams.put("name", "name");
		requestParams.put("latitude", latitude);
		requestParams.put("longitude", longitude);
		requestParams.put("altitude", altitude);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		response = request.post(register_url + "?appid=" + appid);

	}

	@Then("I expect the response with status code {int}")
	public void i_expect_the_response_with_status_code(int statuscode) {
		Assert.assertEquals(statuscode, response.getStatusCode());
	}

	@Given("I register a weather staion with valid APIKey and values {string},{string},{double},{double},{int}")
	public void i_register_a_weather_staion_with_valid_api_key_and_values(String external_id, String name,
			Double latitude, Double longitude, int altitude) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("external_id", external_id);
		requestParams.put("name", name);
		requestParams.put("latitude", latitude);
		requestParams.put("longitude", longitude);
		requestParams.put("altitude", altitude);

		request.header("Content-Type", "application/json");
		request.body(requestParams.toString());
		response = request.post(register_url + "?appid=" + appid);

		stationid = response.getBody().jsonPath().getString("ID");
	}

	@When("I try to get registered weather station")
	public void i_try_to_get_registered_weather_station() {

		response = request.get(register_url + "/" + stationid + "?appid=" + appid);
	}

	@Then("I expect the response with status code {int} and values {string},{string},{double},{double},{int}")
	public void i_expect_the_response_with_status_code_and_values(int statusCode, String external_id, String name,
			Double latitude, Double longitude, int altitude) {
		Assert.assertEquals(statusCode, response.getStatusCode());
		Assert.assertEquals(external_id, response.getBody().jsonPath().getString("external_id"));
		Assert.assertEquals(name, response.getBody().jsonPath().getString("name"));
		Assert.assertEquals(latitude, response.getBody().jsonPath().getDouble("latitude"), 0.0);
		Assert.assertEquals(longitude, response.getBody().jsonPath().getDouble("longitude"), 0.0);
		Assert.assertEquals(altitude, response.getBody().jsonPath().getInt("altitude"));

	}

}
