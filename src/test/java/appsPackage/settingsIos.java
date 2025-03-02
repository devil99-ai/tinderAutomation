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

import com.google.common.collect.ImmutableMap;

import basePackage.baseClass;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
public class settingsIos {
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
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        // Set DesiredCapabilities for iOS
	        dcap = new DesiredCapabilities();
	        dcap.setCapability("platformName", "iOS");
	        dcap.setCapability("platformVersion", prop.getProperty("platformVersion"));
	        dcap.setCapability("deviceName", prop.getProperty("deviceName"));
	        dcap.setCapability("udid", prop.getProperty("udid"));
	        dcap.setCapability("automationName", "XCUITest");
	        dcap.setCapability("newCommandTimeout", 300);
	        dcap.setCapability("app", "/path/to/your/iOS/app.app"); // Update path to your iOS app
	        dcap.setCapability("appPackage", "com.apple.Preferences"); // For iOS settings app
	        dcap.setCapability("appActivity", "Settings");

	        url = new URL("http://127.0.0.1:4723/wd/hub");
	        driver = new IOSDriver(url, dcap);
	    }

	    @Test
	    public void settings() throws MalformedURLException {
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	        // Scroll and click on 'Apps' (adjust the text based on actual element for iOS)
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Apps'`]")).click();

	        // Click on 'See all apps' (adjusted for iOS)
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'See All Apps'`]")).click();

	        js = (JavascriptExecutor) driver;

	        //------------------------------Click on Tinder (adjusted for iOS)-----------------------------------
	        while (true) {
	            try {
	                driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Tinder'`]")).click();
	                break; // Exit the loop if element is found and clicked
	            } catch (Exception e) {
	                // Scroll down if element is not found using executeScript for scrolling
	                ((JavascriptExecutor) driver).executeScript("mobile: scroll", ImmutableMap.of("direction", "down"));
	            }
	        }

	        //------------------------------Navigate to Permissions (iOS)--------------------------------------
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'Permissions'`]")).click();

	        //------------------------------Click on Location Permission--------------------------------------
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Location'`]")).click();
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeRadioButton[`name == 'Allow'`]")).click(); // Allow Location permission

	        // Go back to the previous screen
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`name == 'Back'`]")).click();

	        //------------------------------Click on Photos and Videos Permission--------------------------------
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Photos and Videos'`]")).click();
	        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeRadioButton[`name == 'Allow Always'`]")).click(); // Allow Always
	    }
	}