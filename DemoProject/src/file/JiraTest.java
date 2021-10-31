package file;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session = new SessionFilter();
		
		String response =given().header("Content-Type","application/json")
		.body("{ \"username\": \"rafiulantar\", \"password\": \"01731776465\" }").
		log().all().filter(session).when().post("/rest/auth/1/session").
		then().log().all().extract().response().asString();
		
		
		given().pathParam("id","10100").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \" This From Eclipse for comment automation\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().statusCode(201);

	}

}
