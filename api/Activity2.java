package activities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
 
import org.testng.annotations.Test;
 
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
 
public class Activity2 {
    // Set base URL
    final  String ROOT_URI = "https://petstore.swagger.io/v2/user";
 
    @Test (priority=1)
    public void addNewUserFromFile() throws IOException {
    	FileInputStream inputJSON = new FileInputStream("src/activities/userinfo.json");
    	String reqBody = new String(inputJSON.readAllBytes());
    	
    	Response response = given().contentType(ContentType.JSON)
    			.body(reqBody).when().post(ROOT_URI);
    	inputJSON.close();
    	
    	response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9902"));
    }   
    
    @Test (priority=2)
    public void getUserInfo() {
    	
    	File outputJSON = new File("src/activities/userGETResponse.json");
    	Response response = 
                given().contentType(ContentType.JSON)
                		.pathParam("username", "Avengers")
                		.when().get(ROOT_URI + "/{username}");
    	
    	String resBody = response.getBody().asPrettyString();
    	
    	try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
    	
    	 response.then().body("id", equalTo(9902));
         response.then().body("username", equalTo("Avengers"));
         response.then().body("firstName", equalTo("Iron"));
         response.then().body("lastName", equalTo("Man"));
         response.then().body("email", equalTo("ironman@mail.com"));
         response.then().body("password", equalTo("password123"));
         response.then().body("phone", equalTo("9812763450"));
                
                
    }
    
    @Test(priority=3)
    public void deleteUser() throws IOException {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .pathParam("username", "Avengers") // Add path parameter
            .when().delete(ROOT_URI + "/{username}"); // Send POST request
 
        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("Avengers"));
    }
    
}