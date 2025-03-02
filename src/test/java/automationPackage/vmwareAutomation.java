package automationPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import appsPackage.settingsIos;
import daisysms.smsAutomation;
import io.appium.java_client.ios.IOSDriver;
import swipePackage.swipeAction;

public class vmwareAutomation {
	 public static Properties prop = new Properties();
	    public static DesiredCapabilities dcap;
	    public static IOSDriver driver;
	    public static URL url;
	    WebDriverWait wait;
	    smsAutomation sms;
	    swipeAction swipe;
	    settingsIos settings = new settingsIos();

	    // CAPTCHA Container
	    By captchaContainer = By.xpath("//XCUIElementTypeImage[@name='Match this angle']");
	    // Rotation Controls
	    By clockwiseBtn = By.xpath("//XCUIElementTypeButton[@name='Navigate to previous image']");
	    By counterClockwiseBtn = By.xpath("//XCUIElementTypeButton[@name='Navigate to next image']");
	    // Target Indicator
	    By targetHand = By.xpath("//*[contains(@name, 'target_indicator')]");

	    @BeforeMethod
	    public void setUp() throws MalformedURLException {
	        settings.setup();
	        settings.settings();
	        try {
	            FileInputStream file = new FileInputStream("C:\\Users\\svksh\\eclipse-workspace\\TinderAutomation\\src\\test\\java\\enviromentVariables\\config.properties");
	            prop.load(file);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        dcap = new DesiredCapabilities();
	        dcap.setCapability("platformName", "iOS");
	        dcap.setCapability("platformVersion", "14.5");
	        dcap.setCapability("deviceName", "myphone");
	        //dcap.setCapability("udid", prop.getProperty("udid"));
	        dcap.setCapability("automationName", "XCUITest");
	        dcap.setCapability("app", "com.tinder");
	        dcap.setCapability("newCommandTimeout", 300);
	        dcap.setCapability("connectHardwareKeyboard", true);

	        url = new URL("http://192.168.1.100:4723/wd/hub");
	        driver = new IOSDriver(url, dcap);
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	        sms = new smsAutomation();
	        swipe = new swipeAction();
	    }
	    @Test
	    public void test() {
	    	
	    }

}
