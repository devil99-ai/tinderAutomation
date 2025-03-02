package appsPackage;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePackage.baseClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class signUpTinder extends baseClass{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	@FindBy(xpath = "//android.widget.Button[@resource-id=\"com.tinder:id/tinder_sms_auth_option_login_button\"]") WebElement signUpWithNumber;
	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.tinder:id/phoneNumberInputView\"]") WebElement number;
	@FindBy(xpath = "//android.widget.EditText[@resource-id=\"com.tinder:id/countryCodeInputView\"]") WebElement countryCode;
	@FindBy(xpath = "//android.widget.Button") WebElement next;
	
	public signUpTinder() {
		PageFactory.initElements(driver, this);
	}
	public void openTinder() {
//		dcap.setCapability("appium:appPackage", "com.tinder");
//		dcap.setCapability("appium:appActivity", "com.tinder.feature.auth.internal.activity.AuthStartActivity");
		//driver= new AppiumDriver(url,dcap);
	}
	public void signup() {
		signUpWithNumber.click();
		wait.until(ExpectedConditions.visibilityOf(number));
		countryCode.sendKeys("91");
		number.sendKeys("9876543210");
		next.click();
	}
	

}
