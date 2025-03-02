package automationPackage;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import appsPackage.settingsConfigure;
import daisysms.smsAutomation;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import swipePackage.swipeAction;

public class tinderAutomation {
	
	public static Properties prop = new Properties();
	public static DesiredCapabilities dcap;
	public static AndroidDriver driver;
	public static URL url;
	WebDriverWait wait;
	smsAutomation sms;
	swipeAction swipe;
	settingsConfigure settings=new settingsConfigure();
	// CAPTCHA Container
	By captchaContainer = By.xpath("//android.widget.Image[@text=\"Match this angle\"]");
	// Rotation Controls
	By clockwiseBtn = By.xpath("//android.widget.Button[@text=\"Navigate to previous image\"]");
	By counterClockwiseBtn = By.xpath("//android.widget.Button[@text=\"Navigate to next image\"]");
	// Target Indicator
	By targetHand = By.xpath("//*[contains(@resource-id, 'target_indicator')]");
	@BeforeMethod
	public void setUp() throws MalformedURLException {
		settings.setup();
		settings.settings();
		try {
			FileInputStream file = new FileInputStream("C:\\Users\\svksh\\eclipse-workspace\\TinderAutomation\\src\\test\\java\\enviromentVariables\\config.properties");
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
		dcap.setCapability("appium:webviewDebug", true);
		dcap.setCapability("appium:appPackage", "com.tinder");
		dcap.setCapability("appium:appActivity", "com.tinder.feature.auth.internal.activity.AuthStartActivity");

		url = new URL("http://127.0.0.1:4723/wd/hub");
		driver= new AndroidDriver(url,dcap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver,Duration.ofSeconds(60));
		sms = new smsAutomation();
		swipe = new swipeAction();
		
		
		
	}
	@Test
	public void tinderAutomationSetup() throws InterruptedException, TesseractException, IOException {
		driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.tinder:id/tinder_sms_auth_option_login_button\"]")).click();
		WebElement closepopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ImageView[@content-desc=\"Cancel\"]")));
		closepopUp.click();
		String[] num = sms.getNumber();
		WebElement number=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.EditText[@resource-id=\"com.tinder:id/phoneNumberInputView\"]")));
		number.sendKeys(num[1]);
		
		driver.findElement(By.xpath("//android.widget.Button")).click();
		Thread.sleep(5000);
		//--------------------------captcha disable---------------------------------------------------------------------------------------------
		//driver.findElement(By.xpath("//android.widget.Button[@text=\"START PUZZLE\"]")).click(); //clicking on solve puzzle
		
		//--------------------------------------------------------------------------------------------------------------------------------------------
		
		//--------------otp enter----------------------------------
		String otp = sms.getOTP(num[0]);
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.tinder:id/otp_input_view_0\"]")).sendKeys(otp);
		
		//----------------------------------------------------------
		driver.findElement(By.xpath("//android.widget.Button")).click(); //after enter otp clicking on next
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.tinder:id/collect_email_input_edit_text\"]")).sendKeys("johnshow@gmail.com");//enter email
		driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.tinder:id/collect_email_continue_button\"]/android.view.View/android.view.View/android.widget.Button")).click();//click on next after entering email
		driver.findElement(By.xpath("//android.widget.Button")).click();//clicking on i agree button
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.tinder:id/onboarding_name_edit_text\"]")).sendKeys("john"); //entering first name
		driver.findElement(By.xpath("//android.widget.Button")).click();//click on next after entering first name
		driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.tinder:id/onboarding_confirm_dialog_primary_button\"]/android.view.View/android.view.View/android.widget.Button")).click();//click on let's go button
		//-------------------filling birth date-----------------------------------
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"M\"])[1]")).sendKeys("0");//month
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"M\"])")).sendKeys("1");//month
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"D\"])[1]")).sendKeys("1");//date
		driver.findElement(By.xpath("//android.widget.EditText[@text=\"D\"]")).sendKeys("0");//date
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"Y\"])[1]")).sendKeys("2");//year
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"Y\"])[1]")).sendKeys("0");//year
		driver.findElement(By.xpath("(//android.widget.EditText[@text=\"Y\"])[1]")).sendKeys("0");//year
		driver.findElement(By.xpath("//android.widget.EditText[@text=\"Y\"]")).sendKeys("0");//year
		driver.findElement(By.xpath("//android.widget.Button")).click();//enter next after filling date of birth
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.tinder:id/gender_female_selection_button\"]")).click();//selecting gender as female
		driver.findElement(By.xpath("//android.widget.Button")).click();//click on next after selecting gender
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.tinder:id/sexual_orientation_item_title\" and @text=\"Straight\"]")).click();//select sexual orientation as straight
		driver.findElement(By.xpath("//android.widget.Button")).click();//then clicking on next
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.tinder:id/men_selection\"]")).click();//clicking on interested on male
		driver.findElement(By.xpath("//android.widget.Button")).click();//after interested selection click on next
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View")).click();//selecting looking for as long term partner
		driver.findElement(By.xpath("//android.widget.Button")).click();//then clicking on next
		//-------------------------------------slide the distance bar ---------------------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.tinder:id/distance_slider\"]/android.view.View/android.widget.Button")).sendKeys("33");//adjusting the distance
		driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id=\"com.tinder:id/continue_text_button\"]/android.view.View/android.view.View/android.widget.Button")).click();//clicking on next
		//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.tinder:id/school_text\"]")).click();//click on enter school name
		driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.tinder:id/school_edit_text\"]")).sendKeys("New York");//enter school name
		driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.tinder:id/school_text_view\" and @text=\"University of New York at Tirana\"]")).click();//select school name
		driver.findElement(By.xpath("//android.widget.Button")).click();//clicck on next
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[4]/android.widget.CheckBox")).click();//selecting drinkng in speciaal occassion
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[7]/android.widget.CheckBox")).click();//selecting soocial smoker
		//-------------------------swipe up--------------------------------------------------------------------------------------------------------------------------------------------
		
		swipeUpAndClick("Everyday", driver);//click on workout everyday
		swipeUpAndClick("Dog", driver);//click on pet as Dog
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Do you have any pets\").instance(0))")).click();//scroll to pet section
	//	driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[6]/android.widget.CheckBox")).click();//selecting workout as daily
		//driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[11]/android.widget.CheckBox")).click();//selecting pet as cat
		driver.findElement(By.xpath("//android.widget.Button")).click();//clicking on next
		//------------------------------------------what else make you make section--------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[7]/android.widget.CheckBox")).click();//clicking on better in person
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[11]/android.widget.CheckBox")).click();//clicking on bcomplements
		swipeUpAndClick("Capricorn", driver);//scroll to below zodica sign
		//driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"What is your zodiac sign?\").instance(0))")).click();//scroll to zodiac section
		driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[7]/android.widget.CheckBox")).click();//clicking on cpollege
		//driver.findElement(By.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View[1]/android.view.View[13]/android.widget.CheckBox")).click();//clicking on zodiac sign capricon
		driver.findElement(By.xpath("//android.widget.Button")).click();//clicking on next
		//----------------------------------------------------------------------what you into section--------------------------------------------------------------------
		swipeUpAndClick("Sports", driver);
		swipeUpAndClick("Tennis", driver);
		swipeUpAndClick("Flim", driver);
		swipeUpAndClick("Art", driver);
		
		
//		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Sports\")")).click();
//		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Tennis\")")).click();
//		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Flim Festival\")")).click();
//		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Art\")")).click();
		
		//-----------------------------------------------------------------------------------------------------------------
		driver.findElement(By.xpath("//android.widget.Button")).click();//clicking on next
		//---------------------------------------------------------------------------------------------------------------------------------------------------------------
		//--------------------------------------------------------selecting images------------------------------------------
		driver.findElement(By.xpath("(//android.widget.ImageView[@resource-id=\"com.tinder:id/piv_image\"])[1]")).click();//selecting for 1st image
		driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.tinder:id/sourceListView\"]/android.view.ViewGroup[2]")).click();//clicking on gallery
		driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Gallery\"])[1]")).click();//click on 1st image
		driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.tinder:id/mediaSelectorMenuActionNext\"]")).click();//click on next
		
		
		driver.findElement(By.xpath("(//android.widget.ImageView[@resource-id=\"com.tinder:id/piv_image\"])[2]")).click();//click on second image slot
		driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.tinder:id/sourceListView\"]/android.view.ViewGroup[2]")).click();//click on gallery
		driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"Gallery\"])[2]")).click();//click on image
		driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.tinder:id/mediaSelectorMenuActionNext\"]")).click();//click on next
		
		
		driver.findElement(By.xpath("(//android.widget.ImageView[@resource-id=\"com.tinder:id/piv_image\"])[3]")).click();//click on 3rd image slot
		driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.tinder:id/sourceListView\"]/android.view.ViewGroup[2]")).click();//click on gallary
		driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\\\"Gallery\\\"])[3]")).click();//click on 3rd image
		driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.tinder:id/mediaSelectorMenuActionNext\"]")).click();//click on next
		
		driver.findElement(By.xpath("(//android.widget.ImageView[@resource-id=\"com.tinder:id/piv_image\"])[4]")).click();//click on 4rd image slot
		driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"com.tinder:id/sourceListView\"]/android.view.ViewGroup[2]")).click();//click on gallary
		driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\\\"Gallery\\\"])[4]")).click();//click on 3rd image
		driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.tinder:id/mediaSelectorMenuActionNext\"]")).click();//click on next
		
		driver.findElement(By.xpath("//android.widget.Button")).click(); //click on next
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		WebElement continueBtn =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button")));
		continueBtn.click();//click on continue
		
		
	}
	public void swipeUpAndClick(String text, AndroidDriver driver) {
	    int maxSwipes = 5; // Limit swipes to avoid infinite loop
	    int swipes = 0;

	    while (swipes < maxSwipes) {
	        try {
	            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")")).click();
	            System.out.println("Clicked on: " + text);
	            return; // Exit after clicking
	        } catch (Exception e) {
	            swipeUp(driver); // If element not found, swipe up
	            swipes++;
	        }
	    }
	    System.out.println("Element with text '" + text + "' not found after " + maxSwipes + " swipes.");
	}

	// 🔹 Helper function for swiping up
	public void swipeUp(AndroidDriver driver) {
	    int screenHeight = driver.manage().window().getSize().getHeight();
	    int screenWidth = driver.manage().window().getSize().getWidth();
	    int startX = screenWidth / 2;
	    int startY = screenHeight /2; // Start near bottom
	    int endY = startY - (int) (screenHeight * 0.25);   // Move upwards

	    new TouchAction<>(driver)
	            .press(PointOption.point(startX, startY))
	            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
	            .moveTo(PointOption.point(startX, endY))
	            .release()
	            .perform();
	}
	
   }
