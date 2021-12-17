package OAuth;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class oauth {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver","G:\\Software SQA\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input['type=email']")).sendKeys("rafiulantar@gmail.com");
		driver.findElement(By.cssSelector("input['type=email']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input['type=password']")).sendKeys("test");
		driver.findElement(By.cssSelector("input['type=password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String currentUrl=driver.getCurrentUrl();
		//System.out.println(currentUrl);
		String partialCode=currentUrl.split("code=")[1];
		
		
		
		String accessTokenResponse=given() .queryParams("code","")
				.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type","authorization_code")
		.when()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken=js.getString("access_token");
		
		
		String response=given().queryParam("access_token", accessToken)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);

	}

}
