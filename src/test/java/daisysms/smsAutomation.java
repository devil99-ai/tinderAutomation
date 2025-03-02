package daisysms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class smsAutomation {
	public smsAutomation(){}
	//	public static void main(String[] args) {
			
	public String[] getNumber() {
		 String apiKey = "rspgsuGoOyhZ6GNFGPo04BbArDW1HQ";
	        String service = "oi";
	        String maxPrice = "0.40";
	        String urlString = "https://daisysms.com/stubs/handler_api.php";
	        
	        Response response = RestAssured.given()
	                .queryParam("api_key", apiKey)
	                .queryParam("action", "getNumber")
	                .queryParam("service", service)
	                .queryParam("max_price", maxPrice)
	                .when()
	                .get(urlString);
	        
	        String result = response.getBody().asString();
	        String[] extractedValues = new String[2];
	        extractedValues[0] = result.split(":")[1]; // ID (first number)
	        extractedValues[1] = result.split(":")[2]; // Number (second number)

	        System.out.println("Extracted ID: " + extractedValues[0]);
	        System.out.println("Extracted Number: " + extractedValues[1]);

	        return extractedValues;
	}
	public String getId(String[] extractedValues) {
		  String id = extractedValues[0];
		 System.out.println(id);
		return id;
	}
	public String getOTP(String id) {
		String apiKey = "rspgsuGoOyhZ6GNFGPo04BbArDW1HQ";
		String urlString = "https://daisysms.com/stubs/handler_api.php";
		Response response = RestAssured.given()
				.queryParam("api_key", apiKey)
				.queryParam("action", "getStatus")
				.queryParam("id",id).when().get(urlString);
		String result = response.getBody().asString();
		String otp = result.split(":").length > 1 ? result.split(":")[1].trim() : "";;
		System.out.println(otp);
		return otp;
	}
	}
	//curl "https://daisysms.com/stubs/handler_api.php?api_key=rspgsuGoOyhZ6GNFGPo04BbArDW1HQ&action=getStatus&id=156327021"
