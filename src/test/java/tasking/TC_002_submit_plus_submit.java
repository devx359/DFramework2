package tasking;

import static com.jayway.restassured.RestAssured.given;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
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

import Utilities.ExtentManager;
import lib.Rethink_query;

//Multiple Tasks Single User Serially

public class TC_002_submit_plus_submit {

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
	String next_reques_id = null;
	String status = null;
	// change node value for OP and QC here
	String nodeId = null;
	String userCode = null;
	int port;
	int nooftasks;

	@BeforeClass
	public void setup() {

		// Initializing Page Objects
		nodeId = "c10"; // Change this to do OP or QC
		nooftasks = 3; // no of task an user will do
		
		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent("TC_002_"+nodeId);
		test = reports.createTest("Serial tasks Scenario-For Node: "+nodeId);

		

		if (nodeId.equalsIgnoreCase("c6")) {
			userCode = "debapriyo.halder@imerit.net";
			port = 1000;
		}
		if (nodeId.equalsIgnoreCase("c10")) {
			userCode = "namita.singh@imerit.net";
			port = 10022;
		}

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void task_request() {
		 RestAssured.baseURI=Utilities.PathUtility.RequestAPIUrl;

		JSONObject jsonreq = new JSONObject();

		jsonreq.put("jobCode", "JC-Byg_ur4AG");
		jsonreq.put("nodeId", nodeId);
		jsonreq.put("userCode", userCode);
		jsonreq.put("port", port);

		// API Execution...

		Response res=null;
		try {
			res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					.contentType(ContentType.JSON).extract().response();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			test.fatal("API Connection Issue: "+e1);
		}

		// test.info(jsonreq.toJSONString());
		test.info("**********Task_request API Request ********FOR " + nodeId);
		test.info("JSON Request " + jsonreq);

		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		test.info("Task_request API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		reques_id = res.body().path("request_id").toString();
		user_code = res.body().path("user_code").toString();
		status = res.body().path("status").toString();

		if (status.equalsIgnoreCase("dispatched")) {
			test.pass("JSON Response " + res.asString() + " Status code " + res.statusLine());
		} else {
			test.pass("JSON Response " + res.asString() + " Status code " + res.statusLine());
		}
		// ReThink Extracting node_task_id from task_request
		test.info("------Table Details after Task Request-------");
		LocalDateTime now = LocalDateTime.now();

		try {
			Object ob = rethink.task_request_node_task_id_extract(reques_id);
			Map map = (Map) ob;
			test.info("Task Request Table=> request_id:" + map.get("request_id").toString() + " |status: "
					+ map.get("status").toString());

			// Map map = (Map) ob;
			node_tsk_id = map.get("node_task_id").toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.info("Task_request table data not found");
		}

		LocalDateTime now1 = LocalDateTime.now();
		test.info("Time taken to fetch node_task_id " + Duration.between(now, now1).toMillis() + " milliseconds");

		// Node wise task data details
		String noderes = null;
		noderes = rethink.node_wise_task_data(reques_id).toString();
		test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(noderes, "$.node_wise_status"));

		// main_task_list details
		String tsklist1 = null;
		tsklist1 = rethink.main_task_list().toString();
		test.info("Table main_task_list data => C6 distribute_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
				+ JsonPath.read(tsklist1, "$.task_status"));

		// user_task table
		Object ob45 = rethink.user_task();
		Map map45 = (Map) ob45;
		test.info("Table user_task data=> node_task_id " + map45.get("node_task_id").toString() + " |status "
				+ map45.get("status").toString() + " |port" + map45.get("port").toString());

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2, dependsOnMethods = "task_request")
	public void task_send_to_operator() {

		RestAssured.baseURI = Utilities.PathUtility.APIurl+ "/imerit/platform/task/send/to/operator/0";
		// driver.get("https://www.google.com/");
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("nodeTaskId", node_tsk_id);

		// API Execution...

		Response res=null;
		try {
			res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					// .contentType(ContentType.JSON)
					.extract().response();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test.fatal("API Issue: "+e);
		}

		// Adding Response JSON in Report

		// test.info(jsonreq.toJSONString());
		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		String message = res.body().path("message").toString();
		String result = res.body().path("result").toString();

		test.info("***********task_send_to_operator API Request******** ");
		test.info(" API Request " + jsonreq);
		test.info("Status code " + res.statusLine() + " JSON Response " + res.asString());

		// test.info("API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		if (message.contains("Task distributed")) {
			test.pass("Task send to operator API Reponse " + message);
		} else {
			test.fail("Task send to operator API Reponse " + message);
		}
		test.info("------Table Details after Task Send to Operator-------");
		// Task Request
		Object ob = rethink.task_request(reques_id);
		Map map = (Map) ob;
		test.info("Task Request Table=> request_id:" + map.get("request_id").toString() + " |status: "
				+ map.get("status").toString());

		// Node wise task data
		// test.info("Table Node wise task data " +
		// rethink.node_wise_task_data(reques_id).toString());
		String noderes = null;
		noderes = rethink.node_wise_task_data(reques_id).toString();
		test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(noderes, "$.node_wise_status"));

		// Main task list
		// test.info("Table main_task_list data " +
		// rethink.main_task_list().toString());
		String tsklist1 = null;
		tsklist1 = rethink.main_task_list().toString();
		test.info("Table main_task_list data => C6 distribute_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
				+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
				+ JsonPath.read(tsklist1, "$.task_status"));

		// user_task table
		Object ob45 = rethink.user_task();
		Map map45 = (Map) ob45;
		test.info("Table user_task data=> node_task_id: " + map45.get("node_task_id").toString() + " |status: "
				+ map45.get("status").toString() + " |port: " + map45.get("port").toString());

	}

	@SuppressWarnings("unchecked")
	@Test(priority = 3, dependsOnMethods = "task_request")
	public void task_submit() {

		RestAssured.baseURI =  Utilities.PathUtility.APIurl+"/imerit/platform/operator/task/submit/0";
		int count = 1;

		while (count <= nooftasks) {
			if (count == 1) {
				test.info("*********Task submit*********   Task#" + count + " Request_Id: " + reques_id);
			} else {
				test.info("*********Task submit*********   Task#" + count + " Request_Id: " + next_reques_id);
			}
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
			

			if (count > 1) {
				Object ob = rethink.task_request(next_reques_id);

				Map map = (Map) ob;
				node_tsk_id = map.get("node_task_id").toString();

				reques_id = next_reques_id;

				test.info("-------Table details after New task request-----");
				// Fetch tables
				Object ob11 = rethink.task_request(reques_id);
				Map map1 = (Map) ob11;
				test.info("Task Request Table=> request_id:" + map1.get("request_id").toString() + " |status: "
						+ map1.get("status").toString());
				// Node wise task data details
				String noderes = null;
				noderes = rethink.node_wise_task_data(reques_id).toString();
				test.info("Table Node wise task data => node_wise_status: "
						+ JsonPath.read(noderes, "$.node_wise_status"));

				// main_task_list details
				// test.info("Table main_task_list data " +
				// rethink.main_task_list().toString());
				String tsklist1 = null;
				tsklist1 = rethink.main_task_list().toString();
				test.info("Table main_task_list data => C6 distribute_count: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
						+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
						+ JsonPath.read(tsklist1, "$.task_status"));

				// user_task table
				Object ob45 = rethink.user_task();
				Map map45 = (Map) ob45;
				test.info("Table user_task data=> node_task_id " + map45.get("node_task_id").toString() + " |status "
						+ map45.get("status").toString() + " |port" + map45.get("port").toString());

			}

			jsonreq.put("userCode", user_code);
			jsonreq.put("node_task_id", node_tsk_id);
			jsonreq.put("port", port);
			jsonreq.put("status", "submit");
			// If last task dont request next task
			if (count >= nooftasks) {
				jsonreq.put("next", new Boolean(false));
			} else {
				jsonreq.put("next", new Boolean(true));
			}
			

			// API Execution...

			Response res=null;
			try {
				res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
						.contentType(ContentType.JSON).extract().response();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				test.fatal("API Issue: "+e1);
			}

			count++;// Increase the task count after submit

			int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
			String message = res.body().path("message").toString();
			// String result=res.body().path("result").toString();

			test.info("************task_submit API Request***************** ");
			test.info("API Request " + jsonreq);
			test.info("Status code: " + res.statusLine() + " JSON Response: " + res.asString() + " API Response time : "
					+ Integer.toString(time) + " ms");

			// Extracting response from JSON API response

			try {
				next_reques_id = res.body().path("result.request_id").toString();
				test.info("next _request id " + next_reques_id);
			} catch (Exception e) {
				test.info("request_id not returned by API");
			}

			// user_code = res.body().path("result.user_code").toString();
			if (message.contains("Success")) {
				test.pass("Task Submit API Reponse " + message);
			} else
				test.fail("Task Submit API Reponse " + message);

			LocalDateTime now = LocalDateTime.now();

			test.info("--------Table Details After Task Submit--------");
			// task response
			test.info("Table task_response data " + rethink.task_response_with_status_change_processed("submit").toString());

			LocalDateTime now1 = LocalDateTime.now();
			test.info("Time taken to fetch response from Task Response table " + Duration.between(now, now1).toMillis()
					+ " milliseconds");

			// Task Request
			Object ob22 = rethink.task_request(reques_id);
			Map map22 = (Map) ob22;
			test.info("Task Request Table=> request_id: " + map22.get("request_id").toString() + " |status: "
					+ map22.get("status").toString());

			// Node wise task data
			// test.info("Table Node wise task data " +
			// rethink.node_wise_task_data(reques_id).toString());
			String noderes = null;
			noderes = rethink.node_wise_task_data(reques_id).toString();
			test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(noderes, "$.node_wise_status"));

			// Main task list
			// test.info("Table main_task_list " + rethink.main_task_list().toString());
			String tsklist1 = null;
			tsklist1 = rethink.main_task_list().toString();
			test.info("Table main_task_list data => C6 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
					+ JsonPath.read(tsklist1, "$.task_status"));
			test.info("Main Task List final Task data"+JsonPath.read(tsklist1, "$.task_data"));

			// test.info("jssss "+JsonPath.read(dep_table, "$.[0].c6"));

			// user_task table
			Object ob46 = rethink.user_task();
			Map map46 = (Map) ob46;
			test.info("Table user_task data=> node_task_id: " + map46.get("node_task_id").toString() + " |status: "
					+ map46.get("status").toString() + " |port: " + map46.get("port").toString());
		}

	}

	@AfterClass
	public void teardown() {
		// reports.removeTest(test);
		reports.flush();

	}

}
