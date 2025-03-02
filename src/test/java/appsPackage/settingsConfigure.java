package appsPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import basePackage.baseClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class settingsConfigure{
	JavascriptExecutor js;
	public static Properties prop = new Properties();
	public static DesiredCapabilities dcap;
	public static AppiumDriver driver;
	public static URL url;
	@BeforeMethod
	public void setup() throws MalformedURLException {
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
		dcap.setCapability("appium:appPackage", "com.android.settings");
		dcap.setCapability("appium:appActivity", "com.android.settings.Settings");
		url = new URL("http://127.0.0.1:4723/wd/hub");
		driver= new AndroidDriver(url,dcap);
	}
	@Test
	public void settings() throws MalformedURLException {
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Apps\").instance(0))")).click();//click on apps
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"android:id/title\" and @text=\"See all 42 apps\"]")).click();//click on see more apps
		js = (JavascriptExecutor)driver;
		//------------------------------click on tinder--------------------------------------------------------------------------------------------------------------------------------
		while (true) {
		    try {
		        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Tinder\")")).click();
		        break; // Exit the loop if element is found and clicked
		    } catch (Exception e) {
		        // Scroll down if element is not found
		        driver.findElement(AppiumBy.androidUIAutomator(
		            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
		    }
		}//--------------------------------------------------------------click on tinder-----------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//android.widget.ScrollView/android.view.View[6]")).click(); //click on permissions
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Location\").instance(0))")).click();//click on location
		driver.findElement(By.xpath("//android.widget.RadioButton[@resource-id=\"com.android.permissioncontroller:id/allow_foreground_only_radio_button\"]")).click(); //click on allow permission
		driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]")).click(); //back to permission page
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Photos and videos\").instance(0))")).click();//click on photos and videos
		driver.findElement(By.xpath("//android.widget.RadioButton[@resource-id=\"com.android.permissioncontroller:id/allow_radio_button\"]")).click(); //allow always
		
		

	}

}
