package file;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import java.io.File;

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
				+ "    \"body\": \" This From Eclipse for add text file\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().statusCode(201);
		
		//add attachment
		
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("id","10100")
		.header("multipart","multiPart/form-data")
		.multiPart("file", new File("jira.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get issue
		
		given().filter(session).pathParam("id","10100").queryParam("fields", "comment")
		.log().all().when().get("/rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		
	}

}
