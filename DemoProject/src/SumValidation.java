import org.testng.Assert;
import org.testng.annotations.Test;

import file.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void SumOfCourses() {
		
		int sum=0;
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count =js.getInt("courses.size()");
		for(int i=0;i<count;i++) {
			int price =js.get("courses["+i+"].price");
			int copies=js.get("courses["+i+"].copies");
			
			int amount = price*copies;
			System.out.println(amount);
			
			sum= sum+ amount;
		}
		System.out.println(sum);
		int PurchaseAmount= js.getInt("dashboard.purchaseAmount");
		System.out.println(PurchaseAmount);
		Assert.assertEquals(sum, PurchaseAmount);
	}
}
