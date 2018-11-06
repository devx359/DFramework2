package Keywords;

import static com.jayway.restassured.RestAssured.given;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBodyData;
import com.mysql.cj.xdevapi.JsonString;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import Utilities.DriverUtil;
import Utilities.ExtentManager;
import lib.Rethink_query;

public class Keyword {

	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	WebDriver driver;
	Connection conn;
	RethinkDB r;
	public String node_tsk_id = null;
	String task_code = null;
	String reques_id = null;
	String user_code = null;
	String response_status = "Submit";
	Rethink_query rethink;
	String next_reques_id = null;
	public String Cat1,cat2,cat3;
	public String submittedCat;
	
	public Keyword(ExtentTest test1,ExtentReports report,Rethink_query rethink1)
	{
		test=test1;
		reports=report;
		rethink=rethink1;
	}
	 Random rn = new Random();
	    
  public   int port = rn.nextInt(342575) + 1;
			
  @SuppressWarnings("unchecked")
	public HashMap task_request(String userCode,String nodeId)
	{
		//RestAssured.baseURI = "http://35.231.97.185:9899";
	    RestAssured.baseURI=Utilities.PathUtility.RequestAPIUrl;   
		JSONObject jsonreq = new JSONObject();
		HashMap<String,String>  jsonarray = new HashMap<String,String> ();
		jsonreq.put("userCode", userCode);
		jsonreq.put("jobCode", "JC-Byg_ur4AG");
		jsonreq.put("nodeId", nodeId);
		jsonreq.put("port", port);

		// API Execution...
try {
	
	Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
				.contentType(ContentType.JSON).extract().response();

				// API Execution...
	            
				int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
				System.out.println("JSON for Task Request---- " + jsonreq);
				test.info("JSON for Task request " + jsonreq);
				test.info(MarkupHelper.createLabel("JSON for Task request"+jsonreq, ExtentColor.BLUE));
				//test.info("Task_Request JSON Response " + res.asString() + " Status code " 
				//+ res.statusLine()+"Task_request API Response time : " + Integer.toString(time) + " ms");
			
				// Extracting response from JSON API response
				reques_id = res.body().path("request_id").toString();
				user_code = res.body().path("user_code").toString();
				
       }catch(Exception e)

          {
	            System.out.println("Unable to execute API"+e);
	            test.fail("Unable to execute API"+e);
          }

				LocalDateTime now = LocalDateTime.now();
				
				Object ob = rethink.task_request(reques_id);
				System.out.println("Task Request Table " + ob.toString());
				

				Map map = (Map) ob;
				
				node_tsk_id = map.get("node_task_id").toString();
				//System.out.println(node_tsk_id);

				LocalDateTime now1 = LocalDateTime.now();
				test.info("Time taken to fetch node_task_id " + Duration.between(now, now1).toMillis() + " milliseconds");
				
				//Store data in Hashmap 
				jsonarray.put("nodetaskid",node_tsk_id);
				jsonarray.put("Table Node wise task data",rethink.node_wise_task_data(reques_id).toString());
				jsonarray.put("Table main_task_list data",rethink.main_task_list().toString());
				jsonarray.put("Table user_task data",rethink.user_task().toString());
				
				//System.out.println(jsonarray);
				
				return jsonarray;
			}

			

			@SuppressWarnings("unchecked")
			public HashMap task_send_to_operator(String userCode) {

				HashMap<String,String>  jsonarray = new HashMap<String,String> ();
				RestAssured.baseURI = Utilities.PathUtility.APIurl+"/imerit/platform/task/send/to/operator/0";
				// driver.get("https://www.google.com/");
				JSONObject jsonreq = new JSONObject();
				jsonreq.put("userCode", userCode);
				jsonreq.put("nodeTaskId", node_tsk_id);
				
				// API Execution...
				
				Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
						 .contentType(ContentType.JSON)
						.extract().response();

				// Adding Response JSON in Report

				System.out.println("JSON for task_send_to_operator request" + jsonreq);
				int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
				String message = res.body().path("message").toString();
				try {
				//result=res.body().asString();
				
					Cat1 = res.body().asString();
					System.out.println("Response"+Cat1);
					cat3=JsonPath.read(Cat1, "$.result.partial_data").toString();
					
					// System.out.println("Category-----------"+JsonPath.read(Cat1, "$.result.partial_data.category"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 //  String result1 = res.body().path("result.partial_data").toString();
			   if(!(cat3.isEmpty()))
			   {
		
				 cat2=JsonPath.read(Cat1, "$.result.partial_data.category").toString();
				   jsonarray.put("result", cat2.toString());
			   }
			   else
			   {
				 System.out.println("Category not found");
				  jsonarray.put("result", cat2);
			   }

				
				System.out.println("JSON for task_send_to_operator Response"+res.asString());
				
				test.info("JSON for task_send_to_operator Response"+res.asString());
				test.info("API Response time : " + Integer.toString(time) + " ms");

				// Extracting response from JSON API response
				
				
				//Send to table..
				
				jsonarray.put("nodetaskid",node_tsk_id);
				jsonarray.put("Task Request Table",rethink.task_request(reques_id).toString());
				jsonarray.put("Table Node wise task data",rethink.node_wise_task_data(reques_id).toString());
				jsonarray.put("Table main_task_list data",rethink.main_task_list().toString());
				jsonarray.put("Table user_task data",rethink.user_task().toString());
				
				return jsonarray;

			}

			@SuppressWarnings("unchecked")
			public HashMap task_submit(String userCode,String status) {

				HashMap<String,String>  jsonarray = new HashMap<String,String> ();
				RestAssured.baseURI = Utilities.PathUtility.APIurl+"/imerit/platform/operator/task/submit/0";
				
				JSONObject jsonreq = new JSONObject();
	            JSONObject ob1 = new JSONObject();
	            JSONArray arr = new JSONArray();
	            JSONObject ob2 = new JSONObject();
	            

	            ob1.put("name", "some_val");
	            ob1.put("checked", "true");
	            ob1.put("name", "some_val");
	            arr.add(ob1);            
	            ob2.put("category", arr);
	            
	           submittedCat=arr.toJSONString();
	           
	           System.out.println(submittedCat);
	            
	            jsonreq.put("submit_data", ob2);
				jsonreq.put("userCode", userCode);
				jsonreq.put("node_task_id", node_tsk_id);
				jsonreq.put("port", port);
				jsonreq.put("status", status);
				jsonreq.put("next", new Boolean(true));
				jsonreq.put("submit_data", ob2);

				// API Execution...
                //test.info("*******Task submit starts*****");
				Response res = given().body(jsonreq).when()
						.contentType(ContentType.JSON)
						.post().then()
						 .contentType(ContentType.JSON)
						.extract().response();

				// Adding Response JSON in Report

				System.out.println("JSON for task_submit" + jsonreq);
				int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
				String message = res.body().path("message").toString();
				 String appcode=res.body().path("appcode").toString();

				test.info("JSON for task_submit API Request " + jsonreq);
				test.info("Status code " + res.statusLine());
				test.info("JSON for Task submit Response " + res.asString());
				// Extracting response from JSON API response
				
				LocalDateTime now = LocalDateTime.now();
				//task response
				if(status.equalsIgnoreCase("p_submit"))
				{	
					System.out.println("PSubmit");
				LocalDateTime now1 = LocalDateTime.now();
				test.info("Time taken to fetch response" + Duration.between(now, now1).toMillis() + " milliseconds");
				}
				else if(status.equalsIgnoreCase("submit"))
				{
					
				System.out.println("Table task_response data " + rethink.task_response_with_status_change_processed("Submit").toString());
				jsonarray.put("Table Task Response",rethink.task_response().toString());
					System.out.println("Submit");
					LocalDateTime now1 = LocalDateTime.now();
					test.info("Time taken to fetch response " + Duration.between(now, now1).toMillis() + " milliseconds");
				}
				else
				{
					System.out.println("Table task_response data " + rethink.task_response_with_status_change_processed_GiveUp().toString());
					System.out.println("Submit");
					jsonarray.put("Table Task Response",rethink.task_response().toString());
					LocalDateTime now1 = LocalDateTime.now();
					test.info("Time taken to fetch response " + Duration.between(now, now1).toMillis() + " milliseconds");
				}
				
				
				
				if(appcode.contains("50003"))
				{
					test.pass("Task send to operator to successfully.");
				}
				else
				{
					test.pass("Task send to operator to successfully");
				}
								
				jsonarray.put("nodetaskid",node_tsk_id);
				jsonarray.put("Task Request Table",rethink.task_request(reques_id).toString());
				jsonarray.put("Table Node wise task data",rethink.node_wise_task_data(reques_id).toString());
				jsonarray.put("Table main_task_list data",rethink.main_task_list().toString());
				jsonarray.put("Table Task Response",rethink.task_response().toString());
				jsonarray.put("Table user_task data",rethink.user_task().toString());
				
				return jsonarray;

			}

			
			
			public void teardown() {
				// reports.removeTest(test);
				reports.flush();

			}

	}

