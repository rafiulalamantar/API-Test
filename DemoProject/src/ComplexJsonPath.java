import file.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//1. Print No of courses returned by API
		
		int count =js.getInt("courses.size()");
		System.out.println(count);
		
		//Print purchase amount
		
		int totalAmount =js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//print title 1st course
		
		String firstCourse = js.get("courses[0].title");
		System.out.println(firstCourse);
		
		//Print All course titles and their respective Prices
		
		for(int i=0;i<count;i++) {
			String printTitle=js.get("courses["+i+"].title");
			 
			System.out.println(printTitle);
			System.out.println(js.getInt("courses["+i+"].price"));
		}
		
		System.out.println("Print Number of Copiees Sell by RPA");
		
			for(int i=0;i<count;i++) {
				String CourseTitle=js.get("courses["+i+"].title");
				 if(CourseTitle.equalsIgnoreCase("RPA")) {
					//return sold course
					 int SoldCopies =js.get("courses["+i+"].copies");
					 System.out.println(SoldCopies);
					 break;
				 }
			}
			System.out.println("Verify Sum of all Courses prices");
			

	}

}
