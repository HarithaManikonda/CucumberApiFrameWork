package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {
	public AddPlace addPlacePayLoad(String name,String language,String address)
	{
		AddPlace place=new AddPlace();
		place.setAccuracy(50);
		place.setName(name);
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress(address);
		place.setWebsite("http://google.com");
		place.setLanguage(language);
		List<String> types=new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		place.setTypes(types);
		Location location=new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		place.setLocation(location);
		return place;
	}
	public String deletePlacePayload(String place_id)
	{
		return "{\n"
				+ "\n"
				+ "    \"place_id\":\""+place_id+"\"\n"
				+ "}";
	}
	

}
