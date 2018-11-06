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

public class TC_005_GiveupClickMulti {
	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	WebDriver driver;
	Connection conn;
	RethinkDB r;
	String node_tsk_id = null;
	String savednode_tsk_id = null;
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
	String noderes1 = null;
	String tsklist1 = null;
	String tsklist2 = null;
	String task_code_givenup = null;
	String task_code_new = null;
	int giveup = 0;
	int uniquegiveups = 0;

	//Multiple giveup clicks
	
	@BeforeClass
	public void setup() {

		// Initializing Page Objects
		nodeId = "c6"; // Change this to do OP or QC
		// nooftasks = 1; // no of task an user will do
		giveup = 2;// no of giveup clicks on same data
		uniquegiveups = 2;// giving up unique tasks

		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent("TC_005_GiveupMultiClick" + nodeId);
		test = reports.createTest("Click GiveUp Multiple Times " + nodeId);

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
		RestAssured.baseURI =Utilities.PathUtility.RequestAPIUrl;

		JSONObject jsonreq = new JSONObject();

		jsonreq.put("jobCode", "JC-Byg_ur4AG");
		jsonreq.put("nodeId", nodeId);
		jsonreq.put("userCode", userCode);
		jsonreq.put("port", port);

		// API Execution...

		Response res = null;
		try {
			res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then().contentType(ContentType.JSON)
					.extract().response();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			test.fatal("API Connection problem: " + e1);
		}

		// test.info(jsonreq.toJSONString());
		test.info("**********Task_request API Request ********FOR " + nodeId);
		System.out.println("**********Task_request API Request ********FOR " + nodeId);

		test.info("JSON Request " + jsonreq);

		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		test.info("Task_request API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		reques_id = res.body().path("request_id").toString();
		user_code = res.body().path("user_code").toString();
		status = res.body().path("status").toString();

		test.pass("JSON Response " + res.asString() + " Status code " + res.statusLine());

		// ReThink Extracting node_task_id from task_request
		test.info("------Table Details after Task Request-------");
		LocalDateTime now = LocalDateTime.now();

		Map map;
		try {
			Object ob = rethink.task_request_node_task_id_extract(reques_id);
			map = (Map) ob;
			test.info("Task Request Table=> request_id:" + map.get("request_id").toString() + " |status: "
					+ map.get("status").toString());

			// Map map = (Map) ob;
			node_tsk_id = map.get("node_task_id").toString();
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Task Request Reql found no data");
			test.fail("Task Request Reql found no data");
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

		RestAssured.baseURI =  Utilities.PathUtility.APIurl+"/imerit/platform/task/send/to/operator/0";
		// driver.get("https://www.google.com/");
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
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

		test.info("***********task_send_to_operator API Request******** ");
		System.out.println("***********task_send_to_operator API Request******** ");

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
		tsklist2 = null;
		tsklist2 = rethink.main_task_list().toString();
		test.info("Table main_task_list data => C6 distribute_count: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
				+ JsonPath.read(tsklist2, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
				+ JsonPath.read(tsklist2, "$.task_status"));

		// user_task table
		Object ob45 = rethink.user_task();
		Map map45 = (Map) ob45;
		test.info("Table user_task data=> node_task_id: " + map.get("node_task_id").toString() + " |status: "
				+ map.get("status").toString() + " |port: " + map.get("port").toString());

	}

	// Multiple click of GiveUp on same task
	@SuppressWarnings("unchecked")
	@Test(priority = 3, dependsOnMethods = "task_request")
	public void task_multi_giveup_click() throws InterruptedException {

		RestAssured.baseURI =  Utilities.PathUtility.APIurl+"/imerit/platform/operator/task/submit/0";
		int count = 1;
		nooftasks = giveup;
		while (count <= nooftasks) {

			test.info("*******************MULTIPLE GIVEUP CLICKS ON SAME TASK************************   Task#" + count
					+ " node_tsk_id: " + node_tsk_id);
			System.out.println("*********MULTIPLE GIVEUP CLICKS ON SAME TASK*********   Task#" + count
					+ " node_tsk_id: " + node_tsk_id);

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

			if (count == 2) {

				Object ob = rethink.task_request(next_reques_id);

				Map map = (Map) ob;
				savednode_tsk_id = map.get("node_task_id").toString();
				System.out.println("Next node_task_id to be passed: "+savednode_tsk_id);
				test.warning(savednode_tsk_id);

			}

			jsonreq.put("userCode", user_code);
			jsonreq.put("node_task_id", node_tsk_id);
			jsonreq.put("port", port);
			jsonreq.put("status", "give_up");
			// If last task dont request next task
			if (count >= nooftasks) {
				jsonreq.put("next", new Boolean(false));
			} else {
				jsonreq.put("next", new Boolean(true));
			}
			//jsonreq.put("submit_data", ob3);

			// API Execution...

			Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					.contentType(ContentType.JSON).extract().response();

			/*
			 * System.out.println("Sleeping for 1 minute..."); Thread.sleep(60000);
			 */

			int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
			String message = res.body().path("message").toString();
			// String result=res.body().path("result").toString();

			test.info("**********Multi giveup click Request************ for node_task_id: " + node_tsk_id
					+ " |userCode: " + user_code + " |port:" + port);
			test.info("MultiGiveUp Clicks  API Request " + jsonreq);
			System.out.println("MultiGiveUp Clicks  API Request " + jsonreq);
			test.info("Status code: " + res.statusLine() + " JSON Response: " + res.asString()
					+ "MultiGiveUp Clicks API Response time : " + Integer.toString(time) + " ms");
			System.out.println(" MultiGiveUp CLICK API Response: " + res.asString());

			// Extracting response from JSON API response

			try {
				next_reques_id = res.body().path("result.request_id").toString();
				test.info("next _request id " + next_reques_id);
			} catch (Exception e) {
				test.info("request_id not returned by API");
			}

			// user_code = res.body().path("result.user_code").toString();
			if (message.contains("Success") & count == 1) {
				test.pass("MultiGiveup clicks API Reponse " + message);
			} else if (message.contains("Invalid task request") & count > 1) {
				test.pass("MultiGiveup clicks API Reponse " + message);
			} else
				test.fail("MultiGiveup clicks API Reponse " + message);

			LocalDateTime now = LocalDateTime.now();

			test.info("--------Table Details After Task Submit--------");
			// task response
			
			/*System.out.println("Sleeping for 1 minute");
			Thread.sleep(60000);*/
			try {
				if (count == 1) {
					test.info
					(
							"Table task_response data " + rethink.task_response_with_status_change_processed_GiveUp().toString());
				} else {
					test.info("Table task_response data " + rethink.task_response().toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("TASK rESPONSE TABLE ISSUE: "+e);
				test.warning("TASK rESPONSE TABLE ISSUE: "+e);
			}
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
			noderes1 = null;
			noderes1 = rethink.node_wise_task_data(reques_id).toString();
			test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(noderes1, "$.node_wise_status")
					+ " |node_task_id" + JsonPath.read(noderes1, "$.node_task_id") + " |task_code"
					+ JsonPath.read(noderes1, "$.task_code"));

			// Main task list
			// test.info("Table main_task_list " + rethink.main_task_list().toString());
			tsklist1 = null;
			tsklist1 = rethink.main_task_list().toString();
			test.info("Table main_task_list data => C6 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
					+ JsonPath.read(tsklist1, "$.task_status") + " |Task_code: "
					+ JsonPath.read(tsklist1, "$.task_code"));
			test.info("Main Task List final Task data" + JsonPath.read(tsklist1, "$.task_data"));

			/*
			 * if(count==1) { task_code_givenup=JsonPath.read(tsklist1, "$.task_status"); }
			 * else { task_code_new=JsonPath.read(tsklist1, "$.task_status"); }
			 * 
			 * //If same given up task code is given to same user then fail tc
			 * if(task_code_givenup.equalsIgnoreCase(task_code_new)) { test.
			 * fail("Same task was assigned to same user even after giving up the task .TASK_CODE: "
			 * +task_code_givenup); } else {
			 * test.pass("After request same task was not assigned"); }
			 */
			// test.info("jssss "+JsonPath.read(dep_table, "$.[0].c6"));

			// user_task table
			Object ob46 = rethink.user_task();
			Map map46 = (Map) ob46;
			test.info("Table user_task data=> node_task_id: " + map46.get("node_task_id").toString() + " |status: "
					+ map46.get("status").toString() + " |port: " + map46.get("port").toString());
			count++;// Increase the task count after submit
		}

	}

	// gIVE uP One task after another

	/*@SuppressWarnings("unchecked")
	@Test(priority = 4, dependsOnMethods = "task_multi_giveup_click")
	public void task_giveup() throws InterruptedException {

		RestAssured.baseURI = "http://35.231.97.185:4000/impp/imerit/platform/operator/task/submit/0";
		int count = 1;
		nooftasks = uniquegiveups;
		node_tsk_id=savednode_tsk_id;//Assign nodetask id
		
		while (count <= nooftasks) {

			test.info("*******************TASK GIVEUP************************   Task#" + count + " node_tsk_id: "
					+ node_tsk_id);
			System.out.println("*********Task GIVEUP*********   Task#" + count + " node_tsk_id: " + node_tsk_id);

			JSONObject jsonreq = new JSONObject();
			JSONObject ob1 = new JSONObject();
			JSONObject ob2 = new JSONObject();
			JSONObject ob3 = new JSONObject();

			ob1.put("key1", "val1_val2211111");
			ob2.put("someKey", "some_val");
			ob3.put("data", ob1);
			ob3.put("annotated_data", ob2);

			if (count > 1) {

				Object ob = rethink.task_request(next_reques_id);

				Map map = (Map) ob;
				node_tsk_id = map.get("node_task_id").toString();

				reques_id = next_reques_id;

				test.info("-------Table details after Giving up task-----");
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
			jsonreq.put("status", "give_up");
			// If last task dont request next task
			if (count >= nooftasks) {
				jsonreq.put("next", new Boolean(false));
			} else {
				jsonreq.put("next", new Boolean(true));
			}
			jsonreq.put("submit_data", ob3);

			// API Execution...

			Response res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					.contentType(ContentType.JSON).extract().response();

			
			 * System.out.println("Sleeping for 1 minute..."); Thread.sleep(60000);
			 

			int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
			String message = res.body().path("message").toString();
			// String result=res.body().path("result").toString();

			test.info("**********task_GiveUP API Request************ for node_task_id: " + node_tsk_id + " |userCode: "
					+ user_code + " |port:" + port);
			test.info("Unique GiveUp API Request " + jsonreq);
			System.out.println("Unique GiveUp API Request " + jsonreq);
			test.info("Status code: " + res.statusLine() + " JSON Response: " + res.asString()
					+ "Unique GiveUp API Response time : " + Integer.toString(time) + " ms");

			// Extracting response from JSON API response

			try {
				next_reques_id = res.body().path("result.request_id").toString();
				test.info("next _request id " + next_reques_id);
			} catch (Exception e) {
				test.info("Next task not requested |request_id not returned by API");
			}

			// user_code = res.body().path("result.user_code").toString();
			if (message.contains("Success") & count == 1) {
				test.pass("Unique Giveup API Reponse " + message);
				System.out.println("Unique Giveup API Reponse " + message);
			} else
			{
				test.fail("Unique Giveup API Reponse " + message);
				System.out.println("Unique Giveup API Reponse " + message);
			}
			LocalDateTime now = LocalDateTime.now();

			test.info("--------Table Details After Task GiveUp--------");
			// task response
			System.out.println("Sleeping for 1 minute");
			Thread.sleep(60000);
			test.info("Table task_response data " + rethink.task_response_with_status_change_processed().toString());

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
			noderes1 = null;
			noderes1 = rethink.node_wise_task_data(reques_id).toString();
			test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(noderes1, "$.node_wise_status")
					+ " |node_task_id" + JsonPath.read(noderes1, "$.node_task_id") + " |task_code"
					+ JsonPath.read(noderes1, "$.task_code"));

			// Main task list
			// test.info("Table main_task_list " + rethink.main_task_list().toString());
			tsklist1 = null;
			tsklist1 = rethink.main_task_list().toString();
			test.info("Table main_task_list data => C6 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") + " |C6 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") + " |C10 distribute_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") + " |C10 submit_count: "
					+ JsonPath.read(tsklist1, "$.dependency_table.[1].c10.submit_count") + " |Task Status: "
					+ JsonPath.read(tsklist1, "$.task_status") + " |Task_code: "
					+ JsonPath.read(tsklist1, "$.task_code"));
			test.info("Main Task List final Task data" + JsonPath.read(tsklist1, "$.task_data"));

			if (count == 1) {
				task_code_givenup = JsonPath.read(tsklist1, "$.task_status");
			} else {
				task_code_new = JsonPath.read(tsklist1, "$.task_status");
			}

			// If same given up task code is given to same user then fail tc
			if (task_code_givenup.equalsIgnoreCase(task_code_new)) {
				test.fail("Same task was assigned to same user even after giving up the task .TASK_CODE: "
						+ task_code_givenup);
			} else {
				test.pass("After request same task was not assigned=>New TASK CODE: " + task_code_new);
			}

			// test.info("jssss "+JsonPath.read(dep_table, "$.[0].c6"));

			// user_task table
			Object ob46 = rethink.user_task();
			Map map46 = (Map) ob46;
			test.info("Table user_task data=> node_task_id: " + map46.get("node_task_id").toString() + " |status: "
					+ map46.get("status").toString() + " |port: " + map46.get("port").toString());
			count++;// Increase the task count after submit
		}

	}*/

	@AfterClass
	public void teardown() {
		// reports.removeTest(test);
		reports.flush();

	}

}
