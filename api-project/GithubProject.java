package Project1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
 
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GithubProject {
		String SSHkey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDPe/pS2F8lbExmT3+U7eOS1xGpHnTH1txEoOXItn3ZWnZUvKJKXbXHCXnB1Ykm9bL3WeVcclT/JSDe7GSwezs7RhPFBVcNH1TVDn2jOzPdQjzFVz7pILG9j20fRkTm1I4D+H7IW25zEwlyDAkZwnPhiQ7JmwZt44aT13SyYFwhcn1TtYgF1Sj6srGD+/jKJHoCSuoAV4olJV6+cb8gM29GWEJFAVblAYW7txm4d8gPsariI/foJyeETHjAEHMFY3fxpUfCSpLAProDJThYlKycwiRg4JKLodBNvBoOfxX6rhauT2pgaP9z3P3vxfpz0IEDgnBWHtyL5S7bULQgxxrX";
		int SSHid;
		
		RequestSpecification requestSpec;
		ResponseSpecification responseSpec;
		String ROOT_URI = "https://api.github.com";
		
		@BeforeClass
		public void setUp() {
			
			requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
					.addHeader("Authorization", "token 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")
					.setBaseUri(ROOT_URI)
					.build();
			
			
		}
		
		@Test (priority=1)
		public void addSSHkey() {
			String reqBody = "{\"title\": \"TestAPIKey\",  \"key\": \"" + SSHkey + "\"}";
			System.out.println("The uri to add ssh is " + ROOT_URI + "/user/keys");
			System.out.println("Request body is " + reqBody);
			Response response = given().spec(requestSpec).body(reqBody)
					 .when().post(ROOT_URI + "/user/keys");
			SSHid= response.then().extract().path("id");
	    	System.out.println("The SSHid that was added is " + SSHid);
			
	        response.then().statusCode(201);
	        
		}
		
				
		 @Test (priority=2)
		    public void getSSHkey() {
		    	
		    	
		    	Response response = 
		                given().spec(requestSpec)
		                		.when().get(ROOT_URI + "/user/keys");
		    	System.out.println(response.getBody().asPrettyString());
		    	
		    	
		    	// response.then().statusCode(200);
		    	
		 }
		 
		 @Test (priority=3)
			public void deleteSSHkey() {
				Response response = given().spec(requestSpec)
						.pathParam("keyId", SSHid)
						.when().delete("/user/keys/{keyId}");
				
				System.out.println(response.getBody().asPrettyString());
				System.out.println(response.getStatusCode());
				
				response.then().statusCode(204);
				
			}
		 
		 
}
