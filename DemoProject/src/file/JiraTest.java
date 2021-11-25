package file;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session = new SessionFilter();
		
		//Login
		String response =given().relaxedHTTPSValidation().header("Content-Type","application/json")
		.body("{ \"username\": \"rafiulantar\", \"password\": \"01731776465\" }").
		filter(session).when().post("/rest/auth/1/session").
		then().extract().response().asString();
		
		//add comment
		String expectedMessage="String";
		String addCommnetResponse =given().pathParam("id","10100").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \" "+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment")
		.then().statusCode(201).extract().response().asString().toString();
		
		JsonPath js = new JsonPath(addCommnetResponse);
		String commentID=js.getString("id");
		System.out.println(commentID);
		
		//add attachment
		
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("id","10100")
		.header("multipart","multiPart/form-data")
		.multiPart("file", new File("jira.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().assertThat().statusCode(200);
		
		//Get issue
		
		String issueDetails=given().filter(session).pathParam("id","10100").queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{id}")
		.then().extract().response().asString().toString();
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount=js1.getInt("fields.comment.comments.size()");
		for(int i=0; i<commentsCount;i++) {
			String commnetIssueID=js1.get("fields.comment.comments["+i+"].id").toString();
			System.out.println(commnetIssueID);
			if(commnetIssueID.equalsIgnoreCase(commentID)) {
				
				String message=js1.get("fields.comment.comments["+i+"].body").toString();
				//System.out.println(message);
				//Assert.assertEquals(expectedMessage,message);
				//System.out.println(expectedMessage);
				Assert.assertEquals(message, expectedMessage);
			}
		}
		
	}

}
