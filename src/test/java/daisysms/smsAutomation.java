package daisysms;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class smsAutomation {
	public smsAutomation(){}
	//	public static void main(String[] args) {
	private static final String FILE_PATH = "/TinderAutomation/src/test/java/daisysms/sms.xlsx";
			
	public String[] getNumber() {
		 String apiKey = "rspgsuGoOyhZ6GNFGPo04BbArDW1HQ";
	        String service = "oi";
	        String maxPrice = "0.40";
	        String urlString = "https://daisysms.com/stubs/handler_api.php";
	        
	        Response response = RestAssured.given()
	                .queryParam("api_key", apiKey)
	                .queryParam("action", "getNumber")
	                .queryParam("service", service)
	                .queryParam("max_price", maxPrice)
	                .when()
	                .get(urlString);
	        
	        String result = response.getBody().asString();
	        String[] extractedValues = new String[2];
	        extractedValues[0] = result.split(":")[1]; // ID (first number)
	        extractedValues[1] = result.split(":")[2]; // Number (second number)

	        System.out.println("Extracted ID: " + extractedValues[0]);
	        System.out.println("Extracted Number: " + extractedValues[1]);

	        return extractedValues;
	}
	public String getId(String[] extractedValues) {
		  String id = extractedValues[0];
		 System.out.println(id);
		return id;
	}
	public String getOTP(String id) {
		String apiKey = "rspgsuGoOyhZ6GNFGPo04BbArDW1HQ";
		String urlString = "https://daisysms.com/stubs/handler_api.php";
		Response response = RestAssured.given()
				.queryParam("api_key", apiKey)
				.queryParam("action", "getStatus")
				.queryParam("id",id).when().get(urlString);
		String result = response.getBody().asString();
		String otp = result.split(":").length > 1 ? result.split(":")[1].trim() : "";;
		System.out.println(otp);
		return otp;
	}
	  private void saveToExcel(String id, String number) throws IOException {
	        File file = new File(FILE_PATH);
	        Workbook workbook;
	        Sheet sheet;

	        if (file.exists()) {
	            FileInputStream fis = new FileInputStream(file);
	            workbook = WorkbookFactory.create(fis);
	            sheet = workbook.getSheetAt(0);
	            fis.close();
	        } else {
	            workbook = new XSSFWorkbook();
	            sheet = workbook.createSheet("SMS Data");
	            
	            // Create Header Row
	            Row header = sheet.createRow(0);
	            header.createCell(0).setCellValue("ID");
	            header.createCell(1).setCellValue("Number");
	        }

	        // Find the last row and add new data
	        int rowNum = sheet.getLastRowNum() + 1;
	        Row row = sheet.createRow(rowNum);
	        row.createCell(0).setCellValue(id);
	        row.createCell(1).setCellValue(number);

	        // Write back to file
	        FileOutputStream fos = new FileOutputStream(file);
	        workbook.write(fos);
	        fos.close();
	        workbook.close();

	        System.out.println("Data saved to Excel: ID=" + id + ", Number=" + number);
	    }
	}
	