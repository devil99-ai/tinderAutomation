package swipePackage;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.android.AndroidDriver;

public class swipeAction {
	public swipeAction() {
		
	}
	private static void swipeUp(int numberOfTimes, AndroidDriver driver1) {
		Dimension dimension = driver1.manage().window().getSize();
		System.out.printf("output of Dimention: ", dimension);
		Point start  = new Point((int)(dimension.width*0.5), (int)(dimension.height*0.9));
		Point end  =  new Point((int)(dimension.width*0.5), (int)(dimension.height*0.1));
		System.out.printf("start output: ",start,"\n End Output: ",end);
		for(int i =0; i<numberOfTimes; i++) {
			doSwipe(driver1, start, end,1000);
			
		}
	}
		private static void swipeDown(int numberOfTimes, AndroidDriver driver1) {
			Dimension dimension = driver1.manage().window().getSize();
			Point start =  new Point((int)(dimension.width*0.5),(int)(dimension.height*0.9));
			Point end = new Point((int)(dimension.width*0.5),(int)(dimension.height*0.1));
			for (int i =0;i<numberOfTimes;i++) {
				doSwipe(driver1, start, end, 1000);
			}
			
		}
		
		
	private final static  PointerInput FINGER = new PointerInput(TOUCH, "finger");
	private static void doSwipe(AndroidDriver driver1, Point start, Point end, int Duration) {
		Sequence swipe = new Sequence(FINGER, 1).addAction(FINGER.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY()))
				.addAction(FINGER.createPointerDown(LEFT.asArg()))
				.addAction(FINGER.createPointerMove(ofMillis(0),viewport(), end.getX(), end.getY()))
				.addAction(FINGER.createPointerUp(LEFT.asArg()));
		driver1.perform(Arrays.asList(swipe));
				
	}

}
