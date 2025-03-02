package capsolver;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class CaptchaSolver {

    private static final String CAPSOLVER_API_KEY = "CAP-27E8B5E060F8F816650C38036DBC050F016035F8427462577BB4E133D240E0A3";
    private static final String CAPSOLVER_URL = "https://api.capsolver.com/createTask";

    public static void main(String[] args) throws Exception {
        // Setup WebDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get("https://www.capy.me/products/puzzle_captcha/");
            
            // 1. Get captcha image
            WebElement imageElement = driver.findElement(By.cssSelector(".captcha-image"));
            String imageBase64 = getBase64Image(imageElement);
            
            // 2. Solve using Capsolver
            int requiredAngle = solveRotationCaptcha(imageBase64);
            
            // 3. Calculate clicks needed (assuming 15 degrees per click)
            int degreesPerClick = 15;
            int clicks = Math.round(requiredAngle / degreesPerClick);
            boolean rotateRight = requiredAngle > 0;
            
            // 4. Perform rotations
            WebElement arrow = rotateRight ? 
                driver.findElement(By.cssSelector(".right-arrow")) : 
                driver.findElement(By.cssSelector(".left-arrow"));
            
            for (int i = 0; i < Math.abs(clicks); i++) {
                arrow.click();
                Thread.sleep(500); // Add delay between clicks
            }
            
            // 5. Submit solution
            driver.findElement(By.cssSelector("#submit-button")).click();
            
        } finally {
            driver.quit();
        }
    }

    private static String getBase64Image(WebElement imageElement) {
        String imageSrc = imageElement.getAttribute("src");
        if (imageSrc.startsWith("data:image")) {
            return imageSrc.split(",")[1];
        }
        // Handle other image sources if needed
        throw new RuntimeException("Unsupported image source format");
    }

    private static int solveRotationCaptcha(String base64Image) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(CAPSOLVER_URL);
            
            JSONObject task = new JSONObject();
            task.put("type", "RotateCaptchaTask");
            task.put("image", base64Image);
            task.put("angle", 0); // Initial angle
            
            JSONObject request = new JSONObject();
            request.put("clientKey", CAPSOLVER_API_KEY);
            request.put("task", task);
            
            post.setEntity(new StringEntity(request.toString()));
            post.setHeader("Content-Type", "application/json");
            
            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject result = new JSONObject(responseBody);
                
                if (result.getInt("errorId") != 0) {
                    throw new RuntimeException("Capsolver error: " + result.getString("errorDescription"));
                }
                
                return result.getJSONObject("solution").getInt("rotationAngle");
            }
        }
    }
}