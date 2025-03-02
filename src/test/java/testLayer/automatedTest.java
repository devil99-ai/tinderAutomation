package testLayer;

import java.net.MalformedURLException;

import org.testng.annotations.Test;

import appsPackage.settingsConfigure;
import appsPackage.signUpTinder;
import basePackage.baseClass;

public class automatedTest extends baseClass{
	signUpTinder tinder;
	settingsConfigure settings;
	
	public automatedTest() {
		super();
	}
	@Test
	public void test() throws MalformedURLException, InterruptedException {
		appiumInitiation();
		tinder = new signUpTinder();
		settings = new settingsConfigure();
	//	tinder.openTinder();
		Thread.sleep(5000);
	//	settings.settings();
		tinder.signup();
	}
	
	

}
