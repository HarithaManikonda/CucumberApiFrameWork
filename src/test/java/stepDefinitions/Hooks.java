package stepDefinitions;

import java.lang.annotation.Annotation;

import io.cucumber.java.Before;


public class Hooks 
{

	@Before("@DeletePlace")
	public void beforeScenario() throws Throwable
	{
		//Executes thsi code only when placeID is null
		//write a code that will give you the place id
		PlacesStepDefinition obj=new PlacesStepDefinition();
		if(PlacesStepDefinition.place_Id==null)
		{
			obj.add_Place_Payload_with("Arun","Telugu","India");
			obj.user_calls_with_http_request("AddPlaceAPI", "POST");
			obj.verify_place_Id_created_maps_to_using("Arun", "getPlaceAPI");
		}
	}
}
