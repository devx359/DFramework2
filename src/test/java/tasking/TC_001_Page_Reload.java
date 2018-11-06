package tasking;

import static com.jayway.restassured.RestAssured.given;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
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
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import Utilities.DriverUtil;
import Utilities.ExtentManager;
import lib.Rethink_query;

//Page Reload scenario

public class TC_001_Page_Reload {

	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	WebDriver driver;
	Connection conn;
	RethinkDB r;
	String node_tsk_id = null;
	String task_code = null;
	String reques_id = null;
	String user_code = null;
	String response_status = "Submit";
	Rethink_query rethink;

	@BeforeClass
	public void setup() {
		
		// Initializing Page Objects

		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
			
		reports = ExtentManagerObj.GetExtent("TC_001");
		test = reports.createTest("Page Reload Scenario");
		
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void task_request() {
		RestAssured.baseURI =  Utilities.PathUtility.RequestAPIUrl;
		
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", "debapriyo.halder@imerit.net");
		jsonreq.put("jobCode", "JC-Byg_ur4AG");
		jsonreq.put("nodeId", "c6");
		jsonreq.put("port", new Integer(1001));

		// API Execution...

		Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
				.contentType(ContentType.JSON).extract().response();

		

		// test.info(jsonreq.toJSONString());
		test.info("*****Task_request API Request******" + jsonreq);
		test.info("Task_request API Request " + jsonreq);
		test.info("JSON Response " + res.asString() + " Status code " + res.statusLine());
		test.info("JSON Response " + res.asString() + " Status code " + res.statusLine());
		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		test.info("Task_request API Response time : " + Integer.toString(time) + " ms");
		test.info("Task_request API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		reques_id = res.body().path("request_id").toString();
		user_code = res.body().path("user_code").toString();

		// ReThink Extracting node_task_id from task_request

		LocalDateTime now = LocalDateTime.now();
		
		Object ob = rethink.task_request_node_task_id_extract(reques_id);
		test.info("Task Request Table " + ob.toString());

		Map map = (Map) ob;
		node_tsk_id = map.get("node_task_id").toString();

		LocalDateTime now1 = LocalDateTime.now();
		test.info("Time taken to fetch response " + Duration.between(now, now1).toMillis() + " milliseconds");

		// Node wise task data details
		test.info("Table Node wise task data " + rethink.node_wise_task_data(reques_id).toString());

		// main_task_list details
		test.info("Table main_task_list data " + rethink.main_task_list().toString());
		// user_task table
		test.info("Table user_task data " + rethink.user_task().toString());

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2, dependsOnMethods = "task_request")
	public void task_send_to_operator() {

		RestAssured.baseURI =  Utilities.PathUtility.APIurl+"/imerit/platform/task/send/to/operator/0";
		// driver.get("https://www.google.com/");
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", "debapriyo.halder@imerit.net");
		jsonreq.put("nodeTaskId", node_tsk_id);

		// API Execution...

		Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
				// .contentType(ContentType.JSON)
				.extract().response();

		// Adding Response JSON in Report

		// test.info(jsonreq.toJSONString());
		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		String message = res.body().path("message").toString();
		String result = res.body().path("result").toString();

		test.info("task_send_to_operator API Request " + jsonreq);
		test.info("Status code " + res.statusLine());
		test.info("JSON Response " + res.asString());
		test.info("API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		test.info("Task send to operator API Reponse " + message);

		// Task Request
		Object ob = rethink.task_request(reques_id);
		test.info("Task Request Table " + ob.toString());

		// Node wise task data
		test.info("Table Node wise task data " + rethink.node_wise_task_data(reques_id).toString());

		// Main task list
		test.info("Table main_task_list data " + rethink.main_task_list().toString());

		// user_task table
		test.info("Table user_task data " + rethink.user_task().toString());

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 3, dependsOnMethods = "task_request")
	public void task_submit() {

		RestAssured.baseURI =  Utilities.PathUtility.APIurl+"/imerit/platform/operator/task/submit/0";
		
		JSONObject jsonreq = new JSONObject();
		JSONObject ob1 = new JSONObject();
		JSONArray arr = new JSONArray();
		JSONObject ob2 = new JSONObject();
		JSONObject ob3 = new JSONObject();	
		
		ob1.put("checked", "true");
		ob1.put("name", "some_val");
		arr.add(ob1);			
		ob2.put("category", arr);
		
		jsonreq.put("submit_data", ob2);

		jsonreq.put("userCode", "debapriyo.halder@imerit.net");
		jsonreq.put("node_task_id", node_tsk_id);
		jsonreq.put("port", new Integer(1001));
		jsonreq.put("status", "submit");
		jsonreq.put("next", new Boolean(true));
		//jsonreq.put("submit_data", ob3);

		// API Execution...

		Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
				 .contentType(ContentType.JSON)
				.extract().response();

		// Adding Response JSON in Report

		// test.info(jsonreq.toJSONString());
		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		String message = res.body().path("message").toString();
		// String result=res.body().path("result").toString();

		test.info("task_submit API Request " + jsonreq);
		test.info("Status code " + res.statusLine());
		test.info("JSON Response " + res.asString());
		test.info("API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		test.info("Task send to operator API Reponse " + message);

		LocalDateTime now = LocalDateTime.now();
		// task response
		System.out
				.println("Table task_response data " + rethink.task_response_with_status_change_processed("submit").toString());

		LocalDateTime now1 = LocalDateTime.now();
		test.info("Time taken to fetch response " + Duration.between(now, now1).toMillis() + " milliseconds");

		// Task Request
		test.info("Task Request Table " + rethink.task_request(reques_id).toString());

		// Node wise task data
		test.info("Table Node wise task data " + rethink.node_wise_task_data(reques_id).toString());

		// Main task list
		//test.info("Table main_task_list data " + rethink.main_task_list().toString());
		Object resH  = rethink.main_task_list();
		Map mapresH = (Map) resH;
		//String dep_table = resH.toString();
		JSONObject trt = new JSONObject(mapresH);
		//JSONArray tr2= new JSONArray();
		String dep_table=trt.get("dependency_table").toString();
		test.info("Table main_task_list dependency table"+dep_table);
		//test.info("jssss "+JsonPath.read(dep_table, "$.[0].c6"));
		

		// user_task table
		test.info("Table user_task data " + rethink.user_task().toString());

	}
	
	@AfterClass
	 public void teardown()
	 {
		 //reports.removeTest(test);
		 reports.flush();

	 }

}
