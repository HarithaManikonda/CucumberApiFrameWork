package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils 
{
	public static RequestSpecification reqspec;
	public RequestSpecification requestSpecification() throws IOException
	{
		if(reqspec==null)
		{
			PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));

			reqspec=new RequestSpecBuilder().setBaseUri(getGloblValue("baseUrl")).addQueryParam("key","qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))	
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
			return reqspec;
		}
		return reqspec;
	}

	public String getGloblValue(String key) throws IOException
	{
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream("/Users/arunakirannulu/eclipse-workspace/CucumberApiFrameWork/src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);


	}
	public String getJsonPath(Response response,String key)
	{
		String resp=response.asString(); 
		JsonPath js=new JsonPath(resp);
		return js.get(key);
	}
	
}
