package automationPackage;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class CapSolverHandler {
    private static final String API_KEY = "your_capsolver_api_key";

    public static String solveCaptcha(String captchaImageBase64) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("clientKey", API_KEY);
            payload.put("task", new JSONObject()
                    .put("type", "ReCaptchaV2TaskProxyless") // Change this if different type
                    .put("websiteURL", "https://tinder.com") // Adjust URL accordingly
                    .put("captchaBase64", captchaImageBase64)
            );

            HttpResponse<JsonNode> response = Unirest.post("https://api.capsolver.com/createTask")
                    .header("Content-Type", "application/json")
                    .body(payload.toString())
                    .asJson();

            JSONObject jsonResponse = response.getBody().getObject();
            return jsonResponse.getString("solution"); // This returns the solved CAPTCHA token

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}