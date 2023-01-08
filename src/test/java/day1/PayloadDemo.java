package day1;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class PayloadDemo {

	@Test(priority = 1,enabled = false)
	public void HashMapPayload() {
		
		RestAssured.baseURI = "http://localhost:3000";
		
		HashMap data = new HashMap();
		data.put("email", "testotwo@gmail.com");
		data.put("first_name", "Taksh");
		data.put("last_name", "Nipte");
		data.put("age", 2);
		data.put("address", "Thane");
		
		String courses [] = {"JAVA","Python"};
		data.put("courses",courses);
		
		
		 given()
			.contentType("application/json")
				.body(data)
				
		.when()
			.post("/students")
			
		.then()
			.statusCode(201)
				.body("first_name",equalTo("Taksh"))
					.body("courses[0]",equalTo("JAVA"))
						.log().all();	
		
	}
	
	// PAss data using org.json library
	@Test(priority = 2,enabled = false)
	public void JSONPayload() {
		
		RestAssured.baseURI = "http://localhost:3000";
		
		
		JSONObject data = new JSONObject();
		data.put("email", "testotwo@gmail.com");
		data.put("first_name", "Mahesh");
		data.put("last_name", "Bhojne");
		data.put("age", 2);
		data.put("address", "Parel");
		
		String courses [] = {"BCOM","MCOM"};
		data.put("courses",courses);
		
		
		 given()
			.contentType("application/json")
				.body(data.toString())
					
				
		.when()
			.post("/students")
			
		.then()
			.statusCode(201)
				.body("first_name",equalTo("Mahesh"))
					.body("courses[0]",equalTo("BCOM"))
							.log().all();
					
		
		
	}
	
	// Passing Payload using POJO
	@Test(priority = 3, enabled = false)
	public void POJOPayload() {
		
		RestAssured.baseURI = "http://localhost:3000";
		
		
		POJO_PostRequest data = new POJO_PostRequest();
		
		data.setEmail("testoPOJO@gmail.com");
		data.setFirst_name("Rupesh");
		data.setLast_name("Gawde");
		data.setAge("33");
		data.setAddress("Parel");
		
		String courses [] = {"CA"};
		data.setCourses(courses);
		
		
		 given()
			.contentType("application/json")
				.body(data)
					
				
		.when()
			.post("/students")
			
		.then()
			.statusCode(201)
				.body("first_name",equalTo("Rupesh"))
					.body("courses[0]",equalTo("CA"))
							.log().all();
					
	}
	
	@Test(enabled = true)
	public void ExternalJSONFile() throws FileNotFoundException {
		
		RestAssured.baseURI = "http://localhost:3000";
		
		File file = new File(".\\src\\test\\resources\\InputJSON.json");
		FileReader fr = new FileReader(file);
		JSONTokener jsontoken = new JSONTokener(fr);
		JSONObject data = new JSONObject(jsontoken);
		
		given()
		.contentType("application/json")
			.body(data.toString())
				
			
		.when()
			.post("/students")
			
		.then()
			.statusCode(201)
				.body("first_name",equalTo("Will"))
					.body("courses[0]",equalTo("Acting"))
							.log().all();
	}
}
