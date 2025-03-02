package appsPackage;

import org.json.JSONObject;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class getNumber {
	 public static String getPhoneNumber() {
	        try {
	            String url = "https://daisysms.com/api/get_number?api_key=YOUR_DAISY_SMS_API_KEY&service=tinder";
	            String response = HttpClient.get(url);
	            JSONObject json = new JSONObject(response);
	            return json.getString("number");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "0000000000"; // Fallback dummy number
	        }
	    }

}
