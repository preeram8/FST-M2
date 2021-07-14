package activities;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class Activity1 {
	
	final String ROOT_URI = "https://petstore.swagger.io/v2/pet";
	
	 @Test (priority=1)
	  public void addnewpet() {
		  
		    
		  // Write the request body
		  String reqBody = "{\"id\": 77232, \"name\": \"Rosy\", \"status\": \"alive\"}";

		  Response response = 
		      given().contentType(ContentType.JSON) // Set headers
		      .body(reqBody).when().post(ROOT_URI); // Send POST request

		  response.then().body("id", equalTo(77232));
		  response.then().body("name", equalTo("Rosy"));
		  response.then().body("status", equalTo("alive"));
		 }
	  
  @Test (priority=2)
  public void getpetinfo() {
	 
	  Response response =
			  given().contentType(ContentType.JSON)
			  .when().pathParam("petId", "77232")
			  .get(ROOT_URI + "/{petId}");
	  
	  response.then().body("id", equalTo(77232));
	  response.then().body("name", equalTo("Rosy"));
	  response.then().body("status", equalTo("alive"));
	  
  }
  
  @Test (priority=3)
  public void deletepet() {
	  
	  Response response =
			  given().contentType(ContentType.JSON)
			  .when().pathParam("petId", "77232").delete(ROOT_URI + "/{petId}");
	  
	  response.then().body("code", equalTo(200));
	  response.then().body("message", equalTo("77232"));
  }
}
