package day1;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class HttpRequests {

	int id ;
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in/api";
	}

	@Test(priority = 2,dependsOnMethods = {"createUser"})
	public void getUser() {

		given().when().get("/users?page="+id).then().statusCode(200).body("page", equalTo(id)).log().all();

	}

	@Test(priority = 1)
	public void createUser() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "Yogesh");
		data.put("job", "Trainer");

		id = given().contentType("application/json").body(data).when().post("/users").jsonPath().getInt("id");
		
		System.out.println("id is -->>"+id);
				
				
	}
	
	@Test(priority = 3, dependsOnMethods = {"createUser"})
	public void updateUser() {
		
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", "Yogesh");
		data.put("job", "Engineer");

		given().contentType("application/json").body(data).when().post("/users").then().statusCode(201).log().all();
		
		//System.out.println("id is -->>"+id);
	}
	
}
