package basePackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class baseClass {
	
	public static Properties prop = new Properties();
	public static DesiredCapabilities dcap;
	public static AppiumDriver driver;
	public static URL url;
	
	public baseClass() {
		try {
			FileInputStream file = new FileInputStream("C:\\Users\\svksh\\eclipse-workspace\\TinderAutomation\\src/test\\java\\enviromentVariables\\config.properties");
			prop.load(file);
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void appiumInitiation() throws MalformedURLException {
		dcap = new DesiredCapabilities();
		dcap.setCapability("appium:deviceName", prop.getProperty("deviceName"));
		dcap.setCapability("appium:udid", prop.getProperty("udid"));
		dcap.setCapability("appium:platformName", prop.getProperty("platformName"));
		dcap.setCapability("appium:platformVersion", prop.getProperty("platformVersion"));
		dcap.setCapability("appium:automationName", "UiAutomator2");
		dcap.setCapability("appium:ensureWebviewsHavePages", true);
		dcap.setCapability("appium:nativeWebScreenshot", true);
		dcap.setCapability("appium:newCommandTimeout", 300);
		dcap.setCapability("appium:connectHardwareKeyboard", true);
		dcap.setCapability("appium:appPackage", "com.tinder");
		dcap.setCapability("appium:appActivity", "com.tinder.feature.auth.internal.activity.AuthStartActivity");

		url = new URL("http://127.0.0.1:4723");
		driver= new AndroidDriver(url,dcap);
		 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}

}
