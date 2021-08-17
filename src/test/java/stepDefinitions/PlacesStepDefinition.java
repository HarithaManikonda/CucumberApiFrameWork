package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import pojo.AddPlace;	

public class PlacesStepDefinition extends Utils {
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String place_Id;
	@Given("^Add Place Payload with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void add_Place_Payload_with(String name,String language,String address) throws Throwable
	{


		res=given().spec(requestSpecification())
				.body(data.addPlacePayLoad(name,language,address));

	}

	@When("^User calls \"([^\"]*)\" with \"([^\"]*)\"  http request$")
	public void user_calls_with_http_request(String resource, String method) throws Throwable
	{
		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resspec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();	

		if(method.equalsIgnoreCase("POST"))
			response=res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().get(resourceAPI.getResource());

	}


	@Then("^the API call is success with status code (\\d+)$")
	public void the_API_call_is_success_with_status_code(int arg1) throws Throwable
	{
		assertEquals(response.getStatusCode(),200);
	}


	@Then("^\"([^\"]*)\" in response body is  \"([^\"]*)\"$")
	public void in_response_body_is(String keyValue, String ExpectedValue) throws Throwable 
	{
		assertEquals(getJsonPath(response, keyValue), ExpectedValue);
	}


	@Then("^verify place_Id created maps to \"([^\"]*)\" using \"([^\"]*)\"$")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws Throwable
	{
		
		place_Id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",place_Id);
		user_calls_with_http_request(resource, "GET");
		String actualName=getJsonPath(response, "name");
		assertEquals(actualName,expectedName);	
	}
	
	@Given("^DeletePlace Payload$")
	public void deleteplace_Payload() throws Throwable
	{
		 res=given().spec(requestSpecification())
			   .body(data.deletePlacePayload(place_Id));
	}
}
