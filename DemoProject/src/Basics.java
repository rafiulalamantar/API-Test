import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import file.payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// validate addPlace API is working as expected
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response =given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
			.body(new String(Files.readAllBytes(Paths.get("G:\\Software SQA\\API Test\\DemoProject\\payload.json"))))
		.when().post("maps/api/place/add/json")	
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		    .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//System.out.println(response);
		JsonPath js = new JsonPath(response); //For Parsing JSON
		String placeID =js.get("place_id");
		System.out.println("The Place ID : "+placeID);
		
		//PUT API FOR UPDATE ADDRESS
		
		String newAddress = "70, side layout, Dhaka 10";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
			.body("{\r\n"
					+ "    \"place_id\":\""+placeID+"\",\r\n"
					+ "    \"address\": \""+newAddress+"\",\r\n"
					+ "    \"key\":\"qaclick123\"\r\n"
					+ "}").
		when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
			
		//GET API AUTOMATION TO SEE WHETHER THE UPDATE THE API
		String getPlaceResponse =
		given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String currentAddress=js1.get("address");
		System.out.println(currentAddress);
		
		Assert.assertEquals(newAddress,currentAddress);
		
		//Next Day We Start new Section updated for this
	}  

}
