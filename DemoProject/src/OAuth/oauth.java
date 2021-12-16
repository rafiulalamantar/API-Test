package OAuth;

import static io.restassured.RestAssured.given;

public class oauth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String response=given().queryParam("access_token", "")
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);

	}

}
