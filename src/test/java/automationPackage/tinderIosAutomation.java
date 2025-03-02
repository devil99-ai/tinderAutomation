package automationPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import appsPackage.settingsIos;
import daisysms.smsAutomation;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.Location;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import net.sourceforge.tess4j.TesseractException;
import swipePackage.swipeAction;

public class tinderIosAutomation {
	  public static Properties prop = new Properties();
	    public static DesiredCapabilities dcap;
	    public static IOSDriver driver;
	    public static URL url;
	    WebDriverWait wait;
	    smsAutomation sms;
	    swipeAction swipe;
	    settingsIos settings = new settingsIos();
	    private static final String EXCEL_PATH="./TinderAutomation/src/test/java/excelUtils/excelUtils.xlsx";

	    // CAPTCHA Container
	    By captchaContainer = By.xpath("//XCUIElementTypeImage[@name='Match this angle']");
	    // Rotation Controls
	    By clockwiseBtn = By.xpath("//XCUIElementTypeButton[@name='Navigate to previous image']");
	    By counterClockwiseBtn = By.xpath("//XCUIElementTypeButton[@name='Navigate to next image']");
	    // Target Indicator
	    By targetHand = By.xpath("//*[contains(@name, 'target_indicator')]");

	    @BeforeMethod
	    public void setUp() throws IOException, InterruptedException, TesseractException {
	        settings.setup();
	        settings.settings();
	        FileInputStream file = new FileInputStream(new File("C:\\path\\to\\your\\excelUtils.xlsx"));
	        Workbook workbook = WorkbookFactory.create(file);
	        Sheet sheet = workbook.getSheetAt(0);  // Assuming first sheet

	        // Loop through each row (starting from row 1, skipping headers)
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            String platformVersion = row.getCell(2).getStringCellValue();
	            String platformName = row.getCell(0).getStringCellValue();
	            String deviceName = row.getCell(1).getStringCellValue();
	            String udid = row.getCell(3).getStringCellValue();
	        dcap = new DesiredCapabilities();
	        dcap.setCapability("platformName", platformName);
	        dcap.setCapability("platformVersion", platformVersion);
	        dcap.setCapability("deviceName", deviceName);
	        dcap.setCapability("udid", udid);
	        dcap.setCapability("automationName", "XCUITest");
	        dcap.setCapability("app", "com.tinder");
	        dcap.setCapability("newCommandTimeout", 300);
	        dcap.setCapability("connectHardwareKeyboard", true);
	     // Convert String values from Excel to double
	        double latitude = row.getCell(3).getNumericCellValue();
            double longitude = row.getCell(4).getNumericCellValue();
            double altitude = row.getCell(5).getNumericCellValue();
	        Location location = new Location(latitude,longitude ,altitude ); // Latitude, Longitude, Altitude
	        url = new URL("http://127.0.0.1:4723/wd/hub");
	        driver = new IOSDriver(url, dcap);
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        driver.setLocation(location);
	        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	        sms = new smsAutomation();
	        swipe = new swipeAction();
	        tinderAutomationSetup();
	        }
	        workbook.close();
	        file.close();
	    }
	    public void tinderAutomationSetup() throws InterruptedException, TesseractException, IOException {
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Tinder SMS Auth Option Login Button']")).click();
	        WebElement closepopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeImage[@name='Cancel']")));
	        closepopUp.click();

	        String[] num = sms.getNumber();
	        WebElement number = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeTextField[@name='Phone Number Input View']")));
	        number.sendKeys(num[1]);

	        driver.findElement(By.xpath("//XCUIElementTypeButton")).click();
	        Thread.sleep(5000);

	        // OTP enter
	        String otp = sms.getOTP(num[0]);
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='OTP Input View']")).sendKeys(otp);

	        driver.findElement(By.xpath("//XCUIElementTypeButton")).click(); // After entering OTP, clicking on next
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='Email Input']")).sendKeys("johnshow@gmail.com");
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='I Agree']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='Onboarding Name']")).sendKeys("john");
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Let’s Go']")).click();

	        // Filling birth date
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Month'])[1]")).sendKeys("0");
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Month'])[2]")).sendKeys("1");
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Day'])[1]")).sendKeys("1");
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='Day']")).sendKeys("0");
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Year'])[1]")).sendKeys("2");
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Year'])[2]")).sendKeys("0");
	        driver.findElement(By.xpath("(//XCUIElementTypeTextField[@name='Year'])[3]")).sendKeys("0");
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='Year']")).sendKeys("0");
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Gender selection
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Female']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Orientation
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Straight']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Preferences
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Male']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Long-term partner']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Slide distance bar
	        driver.findElement(By.xpath("//XCUIElementTypeSlider[@name='Distance']")).sendKeys("33");
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // School
	        driver.findElement(By.xpath("//XCUIElementTypeTextField[@name='School']")).sendKeys("New York");
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Preferences (alcohol, smoking)
	        driver.findElement(By.xpath("//XCUIElementTypeCheckBox[@name='Drink on special occasion']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeCheckBox[@name='Social smoker']")).click();

	        // Swipe up
	        swipeUpAndClick("Everyday", driver);
	        swipeUpAndClick("Dog", driver);

	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Additional preferences
	        driver.findElement(By.xpath("//XCUIElementTypeCheckBox[@name='Better in person']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeCheckBox[@name='Compliments']")).click();

	        swipeUpAndClick("Capricorn", driver);

	        driver.findElement(By.xpath("//XCUIElementTypeCheckBox[@name='College']")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Hobbies section
	        swipeUpAndClick("Sports", driver);
	        swipeUpAndClick("Tennis", driver);
	        swipeUpAndClick("Film", driver);
	        swipeUpAndClick("Art", driver);

	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        // Image selection
	        driver.findElement(By.xpath("(//XCUIElementTypeImage[@name='Image'])[1]")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        driver.findElement(By.xpath("(//XCUIElementTypeImage[@name='Image'])[2]")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        driver.findElement(By.xpath("(//XCUIElementTypeImage[@name='Image'])[3]")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        driver.findElement(By.xpath("(//XCUIElementTypeImage[@name='Image'])[4]")).click();
	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Next']")).click();

	        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Continue']")).click();
	    }

	    public void swipeUpAndClick(String text, AppiumDriver driver2) {
	        int maxSwipes = 5; // Limit swipes to avoid infinite loop
	        int swipes = 0;

	        while (swipes < maxSwipes) {
	            try {
	                driver2.findElement(AppiumBy.iOSNsPredicateString("new UIAElement().name('" + text + "')")).click();
	                System.out.println("Clicked on: " + text);
	                return; // Exit after clicking
	            } catch (Exception e) {
	                swipeUp(driver); // If element not found, swipe up
	                swipes++;
	            }
	        }
	        System.out.println("Element with text '" + text + "' not found after " + maxSwipes + " swipes.");
	    }

	    // Helper function for swiping up
	    public void swipeUp(IOSDriver driver) {
	        int screenHeight = driver.manage().window().getSize().getHeight();
	        int screenWidth = driver.manage().window().getSize().getWidth();
	        int startX = screenWidth / 2;
	        int startY = screenHeight / 2; // Start near bottom
	        int endY = startY - (int) (screenHeight * 0.25);   // Move upwards

	        new TouchAction (driver)
	                .press(PointOption.point(startX, startY))
	                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
	                .moveTo(PointOption.point(startX, endY))
	                .release()
	                .perform();
	    }
	    public static String[] readExcelData(int rowIndex) throws IOException {
	    	
	    	 FileInputStream file = new FileInputStream(new File(EXCEL_PATH));
	    	    Workbook workbook = new XSSFWorkbook(file);
	    	    Sheet sheet = workbook.getSheetAt(0);
	    	    Row row = sheet.getRow(rowIndex);

	    	    int colCount = row.getLastCellNum(); // Get total columns
	    	    String[] data = new String[colCount];

	    	    for (int i = 0; i < colCount; i++) {
	    	        Cell cell = row.getCell(i);
	    	        if (cell != null) {
	    	            switch (cell.getCellType()) {
	    	                case STRING:
	    	                    data[i] = cell.getStringCellValue();
	    	                    break;
	    	                case NUMERIC:
	    	                    if (DateUtil.isCellDateFormatted(cell)) {
	    	                        // Handle Date values if needed
	    	                        data[i] = cell.getDateCellValue().toString();
	    	                    } else {
	    	                        data[i] = String.valueOf(cell.getNumericCellValue());
	    	                    }
	    	                    break;
	    	                case BOOLEAN:
	    	                    data[i] = String.valueOf(cell.getBooleanCellValue());
	    	                    break;
	    	                case FORMULA:
	    	                    data[i] = cell.getCellFormula();
	    	                    break;
	    	                case BLANK:
	    	                    data[i] = "";
	    	                    break;
	    	                default:
	    	                    data[i] = "";
	    	            }
	    	        } else {
	    	            data[i] = ""; // Handle null cells
	    	        }
	    	    }

	    	    workbook.close();
	    	    return data;
	    	}

	}