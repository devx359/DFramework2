package tasking;

import static com.jayway.restassured.RestAssured.given;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.PathUtility;
import Utilities.UnixTimeStampConverter;
import lib.Rethink_query;
import lib.TaskAdd;

public class MainClass {
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
	String type = null;
	String response_status = "Submit";
	String jobCode = null;
	String nodewisestatus = null;
	Rethink_query rethink;
	String next_reques_id = null;
	String status = null;
	// change node value for OP and QC here
	String opNode = null;
	String nodetype = null;
	String userCode = null;
	String password = null;
	int port;
	int nooftasks;
	Boolean taskfound = false;
	int qcPercentage = 0;
	int BatchSize = 0;
	int opTaskSubmitted = 0;
	int qcTaskSubmitted = 0;
	int noOfJobsToBeReady = 0;
	int totalOpReadyTasks = 0;
	int bypassedTasks = 0;
	int qcpercent = 0;
	int nodeNumber = 0;
	int mycount=0;
	String distribute_time = null;
	String ack_time = null;
	String submit_time = null;
	String request_time = null;
	String qcnode = null;
	TaskAdd taskadd;
	String submit_type = "submit";
	List<Map<String, String>> listAll = new ArrayList<Map<String, String>>();
	Map<String, String> mapAll;
	UnixTimeStampConverter unix = new UnixTimeStampConverter();
	int count = 1;
	Dbconnection dbcon;
	String token;
	Boolean DeleteRethink;
	Boolean DeleteMysql;
	Boolean waitForTaskResponseProcess;
	Boolean active;
	String EngagementCode = null;
	int Addtask = 0;
	Boolean DeleteMysqlLog;
	String PrevTestCaseSerial = "empty";
	String CurrentTestCaseSerial = "empty";
	Boolean endreport = false;
	String action;
	Boolean TaskUploaded = false;
	Boolean Assert = true;
	Boolean IsTaskMysqlProccessedState = true;
	int requeueCount = 1;
	int reworkCount = 1;
	int flushCount = 1;
	int Loadcounter = 0;
	ArrayList<String> RequeueNodeList = new ArrayList<String>(25);
	ArrayList<String> ReworkNodeList = new ArrayList<String>(25);
	ArrayList<String> FlushNodeList = new ArrayList<String>(25);
	Boolean dontDoAnyAction = false;
	String job_name;
	int batch_size;
	String sheetname;

	// String sheetName=null;
	TaskAdd tskObj;

	@BeforeTest
	public void initialSetup(ITestContext context) {
		PathUtility path = new PathUtility(context);// Sets up paths according to environment parameter passed in testng
													// xml file
		
		
	}

	@BeforeClass
	public void setup(ITestContext context) {
		sheetname=context.getCurrentXmlTest().getParameter("sheetName");
		System.out.println("----------------------------------------------------------");
		System.out.println("                   "+sheetname+"                          ");
		System.out.println("----------------------------------------------------------");

		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent(this.getClass().getSimpleName().toString());
		dbcon = new Dbconnection();
		taskadd = new TaskAdd();
		tskObj = new TaskAdd();
		// sheetName = context.getCurrentXmlTest().getParameter("Excelsheet");//sets the
		// sheet from where testdata provider will read data
		
		
	}

	public void doWhatAction() throws InterruptedException {
		if (action.equalsIgnoreCase("none")) { // if no action is defined do tasking
			task_request();
			if (taskfound == true) {

				task_send_to_operator();
			} else {
				test.info(MarkupHelper.createLabel("Task_Send_to_operator API Not called since no task was found for:"
						+ userCode + " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
			}
			if (taskfound == true) {
				task_submit();
			} else {
				test.info(MarkupHelper.createLabel("Task_Submit API Not called since no task was found for:" + userCode
						+ " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
			}
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		} else if (action.equalsIgnoreCase("request_only")) { // if only request is required
			task_request();
			listAll.add(mapAll);
			System.out.println("------------------------------------------------------------");

		} else if (action.equalsIgnoreCase("ack")) { // if only ACK task required pass ack in action field in excel
			task_request();
			if (taskfound == true) {

				task_send_to_operator();
				listAll.add(mapAll);
			} else {
				test.info(MarkupHelper.createLabel("Task_Send_to_operator API Not called since no task was found for:"
						+ userCode + " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
			}
			System.out.println("------------------------------------------------------------");

		} else if (action.equalsIgnoreCase("requeue") && (dontDoAnyAction == false)) {
			requeue();
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		} else if (action.equalsIgnoreCase("rework") && (dontDoAnyAction == false)) {
			rework();
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		} 
		else if (action.equalsIgnoreCase("resetJob") && (dontDoAnyAction == false)) {
			resetByJob();
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		}
		else if (action.equalsIgnoreCase("flushByJob") && (dontDoAnyAction == false)) {
			flushByJob();
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		}

		else if (action.equalsIgnoreCase("flushBytask")
				|| action.equalsIgnoreCase("flushBynode") && (dontDoAnyAction == false)) {
			flushBytask(action);
			if (Assert == true) // After
			{
				Assertions(CurrentTestCaseSerial);
			}
			System.out.println("------------------------------------------------------------");
		}
	}

	public void defaultSteps(Hashtable<String, String> tdata) throws ParseException {
		count = 1;

		// Fetch from Excel
		try {

			active = Boolean.parseBoolean(tdata.get("Active").toString());
			action = tdata.get("action");
			userCode = tdata.get("usercode");
			opNode = tdata.get("OpNode");
			
			CurrentTestCaseSerial = tdata.get("TestCaseNo");
			Assert = Boolean.parseBoolean(tdata.get("Assert").toString());
			jobCode = tdata.get("jobcode");
			IsTaskMysqlProccessedState = Boolean.parseBoolean(tdata.get("IsTaskMysqlProccessedState").toString());
			nooftasks = Integer.parseInt(tdata.get("tasks").toString());
			EngagementCode = tdata.get("engagement_code");
			nodetype = tdata.get("nodetype");
			nodeNumber = Integer.parseInt(tdata.get("NodeNumber").toString());
			Addtask = Integer.parseInt(tdata.get("AddTask").toString());
			job_name = tdata.get("job_name");
			DeleteRethink = Boolean.parseBoolean(tdata.get("DeleteRethink").toString());
			DeleteMysql = Boolean.parseBoolean(tdata.get("DeleteMysql"));
			DeleteMysqlLog = Boolean.parseBoolean(tdata.get("DeleteMysqlLog").toString());
			port = Integer.parseInt(tdata.get("port").toString());
			qcnode = tdata.get("qcnode");
			qcpercent = Integer.parseInt(tdata.get("qcpercent").toString());
			submit_type = tdata.get("submit_type");
			password = tdata.get("password");
			waitForTaskResponseProcess = Boolean.parseBoolean(tdata.get("waitForTaskResponseProcess").toString());
			batch_size = Integer.parseInt(tdata.get("batch_size").toString());

			// System.out.println("nodeNumber " + nodeNumber);

		} catch (Exception e2) {
			System.out.println("Excel file read issue: " + e2+" in sheet: "+sheetname+" "+CurrentTestCaseSerial);
			
			//  e2.printStackTrace(); e2.getMessage(); 
			 
		}

		if (active == true) { // If test case is active then execute

			// login();

			if (DeleteRethink == true) {
				System.out.println("Delete Rethink started");
				rethink.tableDelete(jobCode);
			}
			if (DeleteMysql == true) {
				System.out.println("Delete MySql started");
				dbcon.deleteTaskFromMysql(EngagementCode, jobCode, DeleteMysqlLog);
			}
			if (Addtask > 0) {
				tskObj.add(userCode, jobCode, Addtask, token, job_name);
			}

			// Sets Extent test name here
			if (PrevTestCaseSerial.equals("empty")) {
				test = reports.createTest(CurrentTestCaseSerial);
				System.out.println("-------------------------------------------------------");
				System.out.println("           " + CurrentTestCaseSerial+" "+sheetname);
				System.out.println("-------------------------------------------------------");

			} else {
				if (!CurrentTestCaseSerial.equalsIgnoreCase(PrevTestCaseSerial)) {
					// Assertions(PrevTestCaseSerial);
					System.out.println("-------------------------------------------------------");
					System.out.println("           " + CurrentTestCaseSerial+" "+sheetname);
					System.out.println("-------------------------------------------------------");

					test = reports.createTest(CurrentTestCaseSerial);
					listAll.clear(); // Clear the array list of results when new testcase is starting
					RequeueNodeList.clear();// Clear Requeued node list if any of previous testcases
					ReworkNodeList.clear();
					FlushNodeList.clear();
					flushCount = 1;
					requeueCount = 1; // reset requeue count
					reworkCount = 1;
					dontDoAnyAction = false; // reset Action go ahead flag
				} else {

				}

			}
			PrevTestCaseSerial = CurrentTestCaseSerial;
			// Determine what action to do ? tasking ? rework ? requeue ? ...

			try {
				doWhatAction();
				// save all records for the particular task
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else {
			System.out.println("Test Case skipped");
		}

	}

	// All subclasses should implement this function
	public void Assertions(String testCase) throws InterruptedException {

	}

	@Test(priority = 1, dataProvider = "setupdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void initialDataSetup(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		String Ljobcode;
		int NooftasktoAdd;
		Boolean active2 = false;
		Boolean delRethink = false;
		Boolean delMysql = false;
		Boolean delMysqlLog = false;
		String engagement_code;

		try {
			Ljobcode = tdata.get("jobcode");
			job_name = tdata.get("job_name");
			NooftasktoAdd = Integer.parseInt(tdata.get("AddTask").toString());
			active2 = Boolean.parseBoolean(tdata.get("active").toString());
			delRethink = Boolean.parseBoolean(tdata.get("DeleteRethink").toString());
			delMysql = Boolean.parseBoolean(tdata.get("DeleteMysql").toString());
			delMysqlLog = Boolean.parseBoolean(tdata.get("DeleteMysqlLog").toString());
			engagement_code = tdata.get("engagement_code");
			System.out.println("inside data setup");
			if (Loadcounter == 0) { // login only once and get the token ,we can reuse the same token
				login("pratik@imerit.net", "I0001I0001");
				Loadcounter++;
			}

			if (active2 == true) { // if active is set to true in excel then load task

				if (delRethink == true) {
					System.out.println("Delete Rethink started");
					rethink.tableDelete(Ljobcode);
				}

				if (delMysql == true) {
					dbcon.deleteTaskFromMysql(engagement_code, Ljobcode, delMysqlLog);
				}

				if (NooftasktoAdd > 0) {
					tskObj.add("pratik@imerit.net", Ljobcode, NooftasktoAdd, token, job_name);
					Loadcounter++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("initialDataSetup issue " + e);
		}

	}

	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		taskfound = false;
		qcPercentage = 100;
		opTaskSubmitted = 0;
		qcTaskSubmitted = 0;

		// Fetch from Excel
		try {

			opNode = tdata.get("OpNode");
			nodetype = tdata.get("nodetype");
			nooftasks = Integer.parseInt(tdata.get("tasks").toString());
			// System.out.println("no of tasks: " + nooftasks);
			userCode = tdata.get("usercode");
			port = Integer.parseInt(tdata.get("port").toString());
			jobCode = tdata.get("jobcode");
			qcnode = tdata.get("qcnode");
			submit_type = tdata.get("submit_type");
			qcpercent = Integer.parseInt(tdata.get("qcpercent").toString());
			password = tdata.get("password");
			nodeNumber = Integer.parseInt(tdata.get("NodeNumber").toString());
			count = 1;
			System.out.println("count reset " + count);
		} catch (Exception e2) {
			System.out.println("Excel file read issue:" + e2);
			e2.printStackTrace();
		}

		qcPercentage = qcpercent;
		test = reports.createTest(jobCode + " " + nodetype + " QC%" + qcpercent);

		// rethink.tableDelete();

		// taskadd.add("debapriyo@imerit.net", "JC-S1q_ZZK-7", 5);

		if (nodetype.equalsIgnoreCase("op")) {
			try {
				totalOpReadyTasks = rethink.main_task_list_status_count(jobCode, opNode, nodeNumber, "node_status",
						"ready");
				System.out.println("Total OP Ready Tasks: " + totalOpReadyTasks);
				test.info("Total OP Ready Tasks: " + totalOpReadyTasks);

				noOfJobsToBeReady = taskadd.QCjobstoReady(qcPercentage, jobCode, opNode, nooftasks);
				System.out.println("noOfQCTasksToBeReady" + noOfJobsToBeReady);
				test.info("Total Expected QC Ready Tasks: " + noOfJobsToBeReady);

				bypassedTasks = totalOpReadyTasks - noOfJobsToBeReady;
				System.out.println("bypassedTasks: " + bypassedTasks);
				test.info("Total QC Expected bypassed Tasks: " + bypassedTasks);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("rethink.main_task_list_status_count issue " + e);
			}
		}
		// Start Tasking
		// login();
		task_request();
		if (taskfound == true) {
			task_send_to_operator();
		} else {
			test.info(MarkupHelper.createLabel("Task_Send_to_operator API Not called since no task was found for:"
					+ userCode + " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
		}
		if (taskfound == true) {
			task_submit();
			// After task submit assertions start
			if (nodetype.equalsIgnoreCase("op")) {
				System.out.println("Sleeping for QC Preparer Cron...");
				Thread.sleep(200000);// wait for qc cron to ready nodes
				try {
					int qcreadycount = rethink.main_task_list_status_count(jobCode, qcnode, nodeNumber, "node_status",
							"ready");
					System.out.println("qcreadycount: " + qcreadycount);
					test.info("Final qc readycount: " + qcreadycount);

					int bypassed = rethink.main_task_list_status_count(jobCode, qcnode, nodeNumber, "node_status",
							"bypassed");
					System.out.println("Bypassed QC nodes: " + bypassed);
					test.info("Bypassed QC nodes: " + bypassed);

					if (noOfJobsToBeReady == qcreadycount) {

						test.pass("QC Ready count correct for jobCode:" + jobCode + " with QC Percentage: "
								+ qcPercentage);
						System.out.println("QC Ready count correct for jobCode:" + jobCode + " with QC Percentage: "
								+ qcPercentage);
					} else {
						test.fail("QC Ready count NOT correct for jobCode:" + jobCode + " with QC Percentage: "
								+ qcPercentage);
						System.out.println("QC Ready count NOT correct for jobCode:" + jobCode + " with QC Percentage: "
								+ qcPercentage);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("main_task_list_status_count rethink query issue" + e);
				}
			}
			System.out.println("OP Tasks submitted: " + opTaskSubmitted);
			System.out.println("QC Tasks submitted: " + qcTaskSubmitted);
			test.info(MarkupHelper.createLabel("OP Tasks submitted: " + opTaskSubmitted, ExtentColor.LIME));
			test.info(MarkupHelper.createLabel("QC Tasks submitted: " + qcTaskSubmitted, ExtentColor.LIME));
		} else {
			test.info(MarkupHelper.createLabel("Task_Submit API Not called since no task was found for:" + userCode
					+ " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
		}

	}

	public void login() {
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("password", password);
		jsonreq.put("agentMeta", "{}");

		System.out.println("JSON Login Request " + jsonreq);
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/auth/signin/0";
		RestAssured.useRelaxedHTTPSValidation();// Resolved SSL Server certificate validation exceptions
		Response res = null;
		try {
			res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					/* .contentType(ContentType.JSON) */.extract().response();
		} catch (Exception e1) {

			e1.printStackTrace();
			test.fatal("Login API Connection Issue: " + e1);
		}
		ResponseBody body = res.getBody();
		// System.out.println("Response Body is: " + body.asString());
		// Extracting response from JSON API response
		// System.out.println("Login API response: "+res);
		String message = res.body().path("message").toString();
		System.out.println("Login API message: " + message);
		token = "Bearer " + res.body().path("token").toString();

	}

	public void login(String usercode1, String password1) {
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", usercode1);
		jsonreq.put("password", password1);
		jsonreq.put("agentMeta", "{}");

		System.out.println("JSON Login Request " + jsonreq);
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/auth/signin/0";
		RestAssured.useRelaxedHTTPSValidation();// Resolved SSL Server certificate validation exceptions
		Response res = null;
		try {
			res = given().body(jsonreq).when().contentType(ContentType.JSON).post().then()
					/* .contentType(ContentType.JSON) */.extract().response();
		} catch (Exception e1) {

			e1.printStackTrace();
			test.fatal("Login API Connection Issue: " + e1);
		}
		ResponseBody body = res.getBody();
		// System.out.println("Response Body is: " + body.asString());
		// Extracting response from JSON API response
		// System.out.println("Login API response: "+res);
		String message = res.body().path("message").toString();
		System.out.println("Login API message: " + message);
		token = "Bearer " + res.body().path("token").toString();

	}

	@SuppressWarnings("unchecked")
	public void task_request() throws InterruptedException {
		double time1 = 0;
		System.out.println("Task Request starts");
		mapAll = new HashMap<String, String>();
		int c = 0;
		mapAll.put("Engagement_Code", EngagementCode);
		mapAll.put("job_code", jobCode);
		if (nodetype.equalsIgnoreCase("qc")) { // Check if any qc node is ready and wait for max 15 minutes
			time1 = 0;
			System.out.println("Waiting 15sec for QC to be ready... ");
			while ((c == 0) && (time1 < 300000)) {
				c = rethink.main_task_list_status_count(jobCode, qcnode, nodeNumber, "node_status", "ready");
				if (c == 0) {
					System.out.print("+");
					Thread.sleep(15000);
					time1 = time1 + 15000;
				}
			}
			if (time1 >= 300000) {
				taskfound = false;
				dontDoAnyAction = true;
				System.out.println("No Ready node was found");

			}
			mapAll.put("node_id", qcnode);
			mapAll.put("task_type", nodetype);
		} else {
			time1 = 0;
			System.out.println("Waiting 15sec for OP to be ready...");
			while ((c == 0) && (time1 < 300000)) { // Check if any op node is ready else wait

				c = rethink.main_task_list_status_count(jobCode, opNode, nodeNumber, "node_status", "ready");
				if (c == 0) {
					System.out.print("+");
					Thread.sleep(15000);
					time1 = time1 + 15000;
				}
			}
			if (time1 >= 300000) {
				taskfound = false;
				dontDoAnyAction = true;
				test.fail("No Ready node was found.Task Request failed");
				System.out.println("No Ready node was found");
			}
			mapAll.put("node_id", opNode);
		}

		if ((count >= 1) && (dontDoAnyAction==false)) {
			RestAssured.baseURI = PathUtility.RequestAPIUrl;
			RestAssured.useRelaxedHTTPSValidation();// Resolved SSL Server certificate validation exceptions
			JSONObject jsonreq = new JSONObject();

			jsonreq.put("jobCode", jobCode);
			if (nodetype.equalsIgnoreCase("op")) { // Checks nodetype and accordingly prepares the task request json
				jsonreq.put("nodeId", opNode);
				mapAll.put("nodeId", opNode);
			} else {
				jsonreq.put("nodeId", qcnode);
				mapAll.put("nodeId", qcnode);
			}
			jsonreq.put("userCode", userCode);
			jsonreq.put("port", port);

			System.out.println("Task Request JSON Request " + jsonreq);
			// test.info(MarkupHelper.createLabel(("Task_request API Request FOR " +
			// opNode), ExtentColor.BLUE));
			// test.info("JSON Request " + jsonreq);
			// API Execution...

			Response res = null;
			try {
				res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
						.then().contentType(ContentType.JSON).extract().response();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				test.fatal("API Connection Issue: " + e1);
			}

			// test.info(jsonreq.toJSONString());

			int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
			// test.info("Task_request API Response time : " + Integer.toString(time) + "
			// ms");

			// Extracting response from JSON API response
			reques_id = res.body().path("request_id").toString();// this request id is of no use after dispatcher code
																	// change
			user_code = res.body().path("user_code").toString();
			status = res.body().path("status").toString();

			if (status.equalsIgnoreCase("dispatched")) {
				// test.pass("Task Request JSON Response " + res.asString() + " Status code " +
				// res.statusLine());
				System.out.println("Task Request JSON Response " + res.asString() + " Status code " + res.statusLine());

			} else {
				// test.pass("Task Request JSON Response " + res.asString() + " Status code " +
				// res.statusLine());
				System.out.println("Task Request JSON Response " + res.asString() + " Status code " + res.statusLine());
			}

			// ReThink Extracting node_task_id from task_request
			// test.info("------Table Details after Task Request-------");
			LocalDateTime now = LocalDateTime.now();
			Object ob;
			try {
				if (nodetype.equalsIgnoreCase("op")) { // Checks nodetype and accordingly prepares the task request json
					ob = rethink.task_request_node_task_id_extract2(jobCode, opNode, userCode);
				} else {
					ob = rethink.task_request_node_task_id_extract2(jobCode, qcnode, userCode);

				}
				// Object ob = rethink.task_request_node_task_id_extract(reques_id);

				Map map = (Map) ob;
				// test.info("Task Request Table=> request_id:" +
				// map.get("request_id").toString() + " |status: "
				// + map.get("status").toString());

				// Map map = (Map) ob;
				node_tsk_id = map.get("node_task_id").toString();
				System.out.println("node_tsk_id:" + node_tsk_id);
				reques_id = map.get("request_id").toString(); // this is useful requestid
				mapAll.put("user_agent", map.get("agent").toString());
				mapAll.put("node_task_id", node_tsk_id);

				/*
				 * long tim= (long) map.get("request_time"); request_time=Long.toString(tim);
				 */
				request_time = map.get("request_time").toString();
				request_time = unix.convert(request_time);
				mapAll.put("request_time", request_time);
				// mapAll.put("nodeId", node)
				String noderes = null;
				noderes = rethink.node_wise_task_data(reques_id).toString();
				nodewisestatus = (JsonPath.read(noderes, "$.node_wise_status")).toString();
				mapAll.put("nodewisestatus", nodewisestatus);
				
				taskfound = true;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Either Task_request table data not found or task not dispatched");
				test.fatal("Either Task_request table data not found or task not dispatched");
				taskfound = false;
				dontDoAnyAction = true;
				// test.info(MarkupHelper.createLabel(
				// ("Task Not found for: " + userCode + " opNode: " + opNode + " jobcode: " +
				// jobCode),
				// ExtentColor.RED));

			}

			LocalDateTime now1 = LocalDateTime.now();
			// test.info("Time taken to fetch node_task_id " + Duration.between(now,
			// now1).toMillis() + " milliseconds");

			if (taskfound == true) {
				// Node wise task data details
				String noderes = null;
				noderes = rethink.node_wise_task_data(reques_id).toString();
				/*
				 * test.info("Table Node wise task data => node_wise_status: " +
				 * JsonPath.read(noderes, "$.node_wise_status"));
				 */
				task_code = JsonPath.read(noderes, "$.task_code");
				String task_master= JsonPath.read(noderes, "$.task_master_id");
				
				mapAll.put("task_master_id", task_master);
				distribute_time = (JsonPath.read(noderes, "$.time_distributed")).toString();
				mapAll.put("distribute_time", unix.convert(distribute_time));

				mapAll.put("task_code", task_code);
				/*
				 * // main_task_list details String tsklist1 = null; tsklist1 =
				 * rethink.main_task_list().toString();
				 * test.info("Table main_task_list data => C6 distribute_count: " +
				 * JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") +
				 * " |C6 node_status: " + JsonPath.read(tsklist1,
				 * "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: " +
				 * JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") +
				 * " |C10 distribute_count: " + JsonPath.read(tsklist1,
				 * "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: " +
				 * JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") +
				 * " |C10 submit_count: " + JsonPath.read(tsklist1,
				 * "$.dependency_table.[1].c10.submit_count") + " |Task Status: " +
				 * JsonPath.read(tsklist1, "$.task_status"));
				 * 
				 * // user_task table Object ob45 = rethink.user_task(); Map map45 = (Map) ob45;
				 * test.info("Table user_task data=> node_task_id " +
				 * map45.get("node_task_id").toString() + " |status " +
				 * map45.get("status").toString() + " |port" + map45.get("port").toString());
				 */
			}
		} else {
			
			System.out.println("No nodes ready");
			String noderes = null;
			noderes = rethink.node_wise_task_data(reques_id).toString();
			// test.info("Table Node wise task data => node_wise_status: " +
			// JsonPath.read(noderes, "$.node_wise_status"));
			task_code = JsonPath.read(noderes, "$.task_code");

			distribute_time = (JsonPath.read(noderes, "$.time_distributed")).toString();
			mapAll.put("distribute_time", unix.convert(distribute_time));

		}

	}

	@SuppressWarnings("unchecked")
	public void task_send_to_operator() {
		System.out.println("Task send to operator starts");

		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/task/send/to/operator/0";
		// driver.get("https://www.google.com/");
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("nodeTaskId", node_tsk_id);

		// test.info(MarkupHelper.createLabel("task_send_to_operator API Request",
		// ExtentColor.BLUE));
		// test.info("Task_send_to_operator API Request for: " + userCode + " node: " +
		// opNode + jsonreq);
		System.out.println("Task_send_to_operator API Request for: " + userCode + " node: " + opNode + jsonreq);

		// API Execution...

		Response res = null;
		try {
			res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
					.then()
					// .contentType(ContentType.JSON)
					.extract().response();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Send to Operator API FAILED");
			test.fatal("Task send to operator API Issue: " + e);
		}

		// Adding Response JSON in Report

		// test.info(jsonreq.toJSONString());
		int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);
		String message = res.body().path("message").toString();
		String result = res.body().path("result").toString();

		/*
		 * test.info("Status code for " + userCode + " node: " + opNode + " STATUS: " +
		 * res.statusLine() + " JSON RESPONSE " + res.asString());
		 */
		System.out.println("Status code for " + userCode + " node: " + opNode + " STATUS: " + res.statusLine()
				+ " JSON RESPONSE " + res.asString());
		// test.info("API Response time : " + Integer.toString(time) + " ms");

		// Extracting response from JSON API response
		if (message.contains("Task distributed")) {
			/*
			 * test.pass("Task send to operator API Reponse " + " for: " + userCode +
			 * " node: " + opNode + " RESPONSE: " + message);
			 */
			taskfound = true;
		} else {
			test.fail("Task send to operator API Reponse " + " for: " + userCode + " node: " + opNode + " RESPONSE: "
					+ message);
			taskfound = false;
		}

		if (taskfound == true) {
			// test.info("------Table Details after Task Send to Operator-------");
			// Task Request
			/*
			 * Object ob = rethink.task_request(reques_id); Map map = (Map) ob;
			 * test.info("Task Request Table=> request_id:" +
			 * map.get("request_id").toString() + " |status: " +
			 * map.get("status").toString());
			 */

			// Node wise task data
			// test.info("Table Node wise task data " +
			// rethink.node_wise_task_data(reques_id).toString());
			String noderes = null;
			noderes = rethink.node_wise_task_data(reques_id).toString();
			// test.info("Table Node wise task data => node_wise_status: " +
			task_code = JsonPath.read(noderes, "$.task_code");
			ack_time = (JsonPath.read(noderes, "$.ack_time")).toString();
			mapAll.put("ack_time", unix.convert(ack_time));
			mapAll.put("task_code", task_code);
			// Main task list
			// test.info("Table main_task_list data " +
			// rethink.main_task_list().toString());
			/*
			 * String tsklist1 = null; tsklist1 = rethink.main_task_list().toString();
			 * test.info("Table main_task_list data => C6 distribute_count: " +
			 * JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count") +
			 * " |C6 node_status: " + JsonPath.read(tsklist1,
			 * "$.dependency_table.[0].c6.node_status") + " |C6 submit_count: " +
			 * JsonPath.read(tsklist1, "$.dependency_table.[0].c6.submit_count") +
			 * " |C10 distribute_count: " + JsonPath.read(tsklist1,
			 * "$.dependency_table.[1].c10.distribute_count") + " |C10 node_status: " +
			 * JsonPath.read(tsklist1, "$.dependency_table.[1].c10.node_status") +
			 * " |C10 submit_count: " + JsonPath.read(tsklist1,
			 * "$.dependency_table.[1].c10.submit_count") + " |Task Status: " +
			 * JsonPath.read(tsklist1, "$.task_status"));
			 * 
			 * // user_task table Object ob45 = rethink.user_task(); Map map45 = (Map) ob45;
			 * test.info("Table user_task data=> node_task_id: " +
			 * map45.get("node_task_id").toString() + " |status: " +
			 * map45.get("status").toString() + " |port: " + map45.get("port").toString());
			 */
		}

	}

	@SuppressWarnings("unchecked")
	public void task_submit() throws InterruptedException {
		System.out.println("Task submit starts");
		int p_submit_count = 1;
		String message = "SOMETHING";
		Response res = null;
		String jsonmanual = "somevalue";

		System.out.println("nooftasks " + nooftasks + "count " + count);
		while (count <= nooftasks) {

			JSONObject jsonreq = new JSONObject();
			JSONObject ob1 = new JSONObject();
			JSONArray arr = new JSONArray();
			JSONObject ob2 = new JSONObject();
			JSONObject ob3 = new JSONObject();

			ob1.put("checked", "true");
			ob1.put("name", "some_val");
			arr.add(ob1);
			ob2.put("category", arr);

			jsonreq.put("userCode", user_code);
			if(count==1) {
			jsonreq.put("node_task_id", node_tsk_id);
			jsonreq.put("port", port);
			}
			
			jsonreq.put("next", new Boolean(false));
			// If last task dont request next task
			/*
			 * if (count >= nooftasks) { jsonreq.put("next", new Boolean(false)); } else {
			 * jsonreq.put("next", new Boolean(true)); }
			 */

			if (job_name.equalsIgnoreCase("multi_psubmit_flow")) { // For multi P_submit jobs
				while (p_submit_count <= batch_size) {
					if (p_submit_count == 1) { // 1st frame
						jsonmanual = "{\"next\":false,\"node_task_id\":\"" + node_tsk_id + "\",\"port\":" + port
								+ ",\"userCode\":\"" + user_code
								+ "\",\"status\":\"p_submit\",\"submit_data\":{\"key1\":{\"emp_id\":\"debapriyo.halder@imerit.net\",\"record_id\":\"NT-Bk45hZr_Q\",\"annotations\":[{\"Frame_no\":\"1\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03025016_03025017.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":1}},\"annotateddata\":[{\"mapelem\":[\"class\",\"attribute\"],\"id\":\"UpXoP1536658630913\",\"object_type\":\"bbox\",\"class\":[[\"type\",\"Pedestrians\"]],\"attribute\":[[\"occluded\",false]],\"coordinates\":[{\"x\":212.38938053097343,\"y\":280.9734513274336},{\"x\":376.10619469026545,\"y\":280.9734513274336},{\"x\":376.10619469026545,\"y\":418.14159292035396},{\"x\":212.38938053097343,\"y\":418.14159292035396}],\"deleted\":false}],\"formateddata\":[{\"id\":\"UpXoP1536658630913\",\"object_type\":\"bbox\",\"coordinates\":[{\"x\":212.38938053097343,\"y\":280.9734513274336},{\"x\":376.10619469026545,\"y\":280.9734513274336},{\"x\":376.10619469026545,\"y\":418.14159292035396},{\"x\":212.38938053097343,\"y\":418.14159292035396}],\"deleted\":false,\"attribute\":{\"occluded\":false},\"class\":{\"type\":\"Pedestrians\"}}]}},\"comment\":\"\",\"duration\":12327,\"active\":false},{\"Frame_no\":\"2\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03566415_6416.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":2,\"active\":true},{\"Frame_no\":\"3\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03660226.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"4\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03660249.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"5\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03660787.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"6\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03660906.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"7\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03661333.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"8\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03663588.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"9\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03701798.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false},{\"Frame_no\":\"10\",\"judgement\":{\"object\":{\"metainfo\":{\"image\":{\"height\":1000,\"width\":754,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/workfusion/phase2/batch0%2Finvoice/03710001.jpeg\",\"category\":\"batch0/invoice\",\"sub-category\":\"batch0%2Finvoice\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[],\"formateddata\":[]}},\"comment\":\"\",\"duration\":0,\"active\":false}]}}}";
						// jsonreq.put("submit_data",ss.replace("\"", ""));

						RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/operator/task/submit/0";

						// jsonreq.put("status", "p_submit");

					} else if (p_submit_count == batch_size) { // Last frame submit task

						jsonmanual = "{\"next\":false,\"node_task_id\":\"" + node_tsk_id + "\",\"port\":" + port
								+ ",\"userCode\":\"" + user_code
								+ "\",\"status\":\"submit\",\"submit_data\":[{\"path\":{\"key1\":{\"annotations\":{}}},\"position\":"
								+ (p_submit_count - 1)
								+ ",\"data\":{\"duration\":0,\"comment\":\"hello\",\"judgement\":{\"object\":{\"timestamp\":1536234516745,\"metainfo\":{\"image\":{\"height\":10,\"width\":10,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/geo-semiconductor/poc%2Fgeo_semi_sample/file11.avi_15fps.avi_000700.jpeg\",\"category\":\"poc/geo_semi_sample\",\"sub-category\":\"poc%2Fgeo_semi_sample\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[{\"attribute\":[[\"occluded\",false]],\"class\":[[\"type\",\"Pedestrians\"]],\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"mapelem\":[\"class\",\"attribute\"],\"object_type\":\"bbox\"}],\"formateddata\":[{\"attribute\":{\"occluded\":false},\"class\":{\"type\":\"Pedestrians\"},\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"object_type\":\"bbox\"}]}}}},{\"path\":{\"key1\":{}},\"position\":-1,\"data\":{\"active\":{\"activeFrame\":2,\"updateTimestamp\":556}}}]}";
						// jsonreq.put("submit_data",

						RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/operator/task/submit/1";
						// jsonreq.put("status", "submit");

					} else { // All frames between first and last p_submit frames
						jsonmanual = "{\"next\":false,\"node_task_id\":\"" + node_tsk_id + "\",\"port\":" + port
								+ ",\"userCode\":\"" + user_code
								+ "\",\"status\":\"p_submit\",\"submit_data\":[{\"path\":{\"key1\":{\"annotations\":{}}},\"position\":"
								+ (p_submit_count - 1)
								+ ",\"data\":{\"duration\":0,\"comment\":\"hello\",\"judgement\":{\"object\":{\"timestamp\":1536234516745,\"metainfo\":{\"image\":{\"height\":10,\"width\":10,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/geo-semiconductor/poc%2Fgeo_semi_sample/file11.avi_15fps.avi_000700.jpeg\",\"category\":\"poc/geo_semi_sample\",\"sub-category\":\"poc%2Fgeo_semi_sample\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[{\"attribute\":[[\"occluded\",false]],\"class\":[[\"type\",\"Pedestrians\"]],\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"mapelem\":[\"class\",\"attribute\"],\"object_type\":\"bbox\"}],\"formateddata\":[{\"attribute\":{\"occluded\":false},\"class\":{\"type\":\"Pedestrians\"},\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"object_type\":\"bbox\"}]}}}},{\"path\":{\"key1\":{}},\"position\":-1,\"data\":{\"active\":{\"activeFrame\":2,\"updateTimestamp\":556}}}]}";

						/*
						 * jsonreq.put("submit_data",
						 * "[{\"path\":{\"key1\":{\"annotations\":{}}},\"position\":"+(p_submit_count-1)
						 * +
						 * ",\"data\":{\"duration\":0,\"comment\":\"hello\",\"judgement\":{\"object\":{\"timestamp\":1536234516745,\"metainfo\":{\"image\":{\"height\":10,\"width\":10,\"url\":\"https://s3-ap-southeast-1.amazonaws.com/imerit-solution/client/geo-semiconductor/poc%2Fgeo_semi_sample/file11.avi_15fps.avi_000700.jpeg\",\"category\":\"poc/geo_semi_sample\",\"sub-category\":\"poc%2Fgeo_semi_sample\"},\"annotation\":{\"enabled_count\":0}},\"annotateddata\":[{\"attribute\":[[\"occluded\",false]],\"class\":[[\"type\",\"Pedestrians\"]],\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"mapelem\":[\"class\",\"attribute\"],\"object_type\":\"bbox\"}],\"formateddata\":[{\"attribute\":{\"occluded\":false},\"class\":{\"type\":\"Pedestrians\"},\"coordinates\":[{\"x\":77.43362831858407,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":146.01769911504425},{\"x\":404.8672566371681,\"y\":353.98230088495575},{\"x\":77.43362831858407,\"y\":353.98230088495575}],\"deleted\":false,\"id\":\"ab2ob1536311876525\",\"object_type\":\"bbox\"}]}}}},{\"path\":{\"key1\":{}},\"position\":-1,\"data\":{\"active\":{\"activeFrame\":2,\"updateTimestamp\":556}}}]"
						 * );
						 */
						RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/operator/task/submit/1";
						// jsonreq.put("status", "p_submit");
					}

					// API Execution...
					if (count > 1) {
						reques_id = next_reques_id;
						task_request();
						task_send_to_operator();
						jsonreq.put("node_task_id", node_tsk_id);
						RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/operator/task/submit/1";
					}
					System.out.println("Task SUBMIT API Request for: " + userCode + " node: " + opNode + jsonreq);

					try {
						res = given().header("Authorization", token)/* .filter(new RequestLoggingFilter()) */
								.contentType(ContentType.JSON).body(jsonmanual).when().contentType(ContentType.JSON)
								.post().then().contentType(ContentType.JSON).extract().response();

						p_submit_count++;
						count++;// Increase the task count after submit

						message = res.body().path("message").toString();
						System.out.println("Status code: " + res.statusLine() + " JSON Response: " + res.asString());
					} catch (Exception e1) {

						e1.printStackTrace();
						test.fatal("Submit API Issue: " + e1);
					}
				}

			} else { // ***********For NON multi P_submit jobs****************
				jsonreq.put("submit_data", ob2);
				jsonreq.put("status", submit_type);


				// In case of multiple task call task_request and send to operator before
				// calling task submit API
				if (count > 1) {
					reques_id = next_reques_id;
					task_request();
					task_send_to_operator();
					jsonreq.put("node_task_id", node_tsk_id);
					jsonreq.put("port", port);
				}
				RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/operator/task/submit/0";

				// API DEATH Execution...

				System.out.println("Task SUBMIT API Request for: " + userCode + " node: " + opNode + jsonreq);

				try {
					res = given().header("Authorization", token)/*.filter(new RequestLoggingFilter())*/
							.contentType(ContentType.JSON).body(jsonreq)
							.when().contentType(ContentType.JSON).post()
							.then().contentType(ContentType.JSON).extract().response();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					test.fatal("Submit API Issue: " + e1);
				}

				count++;// Increase the task count after submit

				message = res.body().path("message").toString();
				System.out.println("Status code: " + res.statusLine() + " JSON Response: " + res.asString());
			}

			// int time = (int) res.getTimeIn(TimeUnit.MILLISECONDS);

			// Extracting response from JSON API response

			try {
				next_reques_id = res.body().path("result.request_id").toString();
				// test.info("next _request id " + next_reques_id);
			} catch (Exception e) {
				// test.info("request_id not returned by API");
				// e.printStackTrace();
				System.out.println("next task request id not found");
			}

			// user_code = res.body().path("result.user_code").toString();
			if (message.contains("Success")) {
				// test.pass("Task Submit API Reponse " + message + " for: " + userCode + "
				// node: " + opNode);
				if (nodetype.equalsIgnoreCase("op")) {
					opTaskSubmitted++; // Increment OPTask count after submit
					System.out.println("OP Task Submitted " + opTaskSubmitted);
				} else if (nodetype.equalsIgnoreCase("qc")) {
					qcTaskSubmitted++;
					System.out.println("QC Task Submitted " + qcTaskSubmitted);
				}
			} else
				test.fail("Task Submit API Reponse " + message + " for: " + userCode + " node: " + opNode);

			// LocalDateTime now = LocalDateTime.now();

			// test.info(MarkupHelper.createLabel("Table Details After Task Submit",
			// ExtentColor.BLUE));
			// task response

			if (waitForTaskResponseProcess == true) // Frome excel it is set ,if you require to wait for task response
													// then set it to true
			{

				/*
				 * test.info("Table task_response data " +
				 * rethink.task_response_with_status_change_processed(submit_type).toString());
				 */
				System.out.println("Table task_response data "
						+ rethink.task_response_with_status_change_processed(submit_type).toString());

				String noderes = null;
				noderes = rethink.node_wise_task_data(reques_id).toString();
				nodewisestatus = (JsonPath.read(noderes, "$.node_wise_status")).toString();
				mapAll.put("nodewisestatus", nodewisestatus);
			}
			// LocalDateTime now1 = LocalDateTime.now();
			/*
			 * test.info("Time taken to fetch response from Task Response table " +
			 * Duration.between(now, now1).toMillis() + " milliseconds");
			 */

			// Task Request
			Object ob22 = rethink.task_request(reques_id);
			Map map22 = (Map) ob22;
			/*
			 * test.info("Task Request Table=> request_id: " +
			 * map22.get("request_id").toString() + " |status: " +
			 * map22.get("status").toString());
			 */

			// Node wise task data
			String noderes = null;
			noderes = rethink.node_wise_task_data(reques_id).toString();
			// test.info("Table Node wise task data => node_wise_status: " +
			// JsonPath.read(noderes, "$.node_wise_status"));

			mapAll.put("task_code", (JsonPath.read(noderes, "$.task_code")).toString());
			mapAll.put("task_master_id", (JsonPath.read(noderes, "$.task_master_id")).toString());

			if (waitForTaskResponseProcess == true) {
				submit_time = (JsonPath.read(noderes, "$.submit_time")).toString();
				mapAll.put("submit_time", unix.convert(submit_time));

				// Main task list

				String tsklist1 = null;
				tsklist1 = rethink.main_task_list().toString();
				if (nodetype.equalsIgnoreCase("op")) {
					mapAll.put("submit_data", JsonPath.read(tsklist1, "$.task_data." + opNode).toString());
					mapAll.put("task_data",
							JsonPath.read(tsklist1, "$.dependency_table.[0]." + opNode + ".task_data").toString());
				} else {
					mapAll.put("submit_data", JsonPath.read(tsklist1, "$.task_data." + qcnode).toString());
					mapAll.put("task_data",
							JsonPath.read(tsklist1, "$.dependency_table.[1]." + qcnode + ".task_data").toString());
				}
			}

			/*
			 * test.info("Table main_task_list data => C6 distribute_count: " +
			 * JsonPath.read(tsklist1, "$.dependency_table.[0].c6.distribute_count"));
			 * test.info("Main Task List final Task data" + JsonPath.read(tsklist1,
			 * "$.task_data"));
			 */

			// user_task table
			Object ob46 = rethink.user_task();
			Map map46 = (Map) ob46;
			/*
			 * test.info("Table user_task data=> node_task_id: " +
			 * map46.get("node_task_id").toString() + " |status: " +
			 * map46.get("status").toString() + " |port: " + map46.get("port").toString());
			 */

			listAll.add(mapAll); // save all records for the particular task
		}

	}

	@SuppressWarnings("unchecked")
	public void requeue() throws InterruptedException {

		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);
		double time = 0;
		String nodesTMasterid = null;
		String engagmCode = null;
		String nodetaskcode = null;
		Boolean AbortRequeue = false;

		// Finding TaskMasterId of the node/task to be requeued
		int hit = 0;

		for (int i = 0; i < listAll.size(); i++) {

			if (maps[i].get("node_id").toString().equals(opNode)) // Finds the desired node
			{
				hit++;
				if (hit == requeueCount) {
					System.out.println(
							"found the req node :" + maps[i].get("task_master_id") + " " + maps[i].get("task_code"));
					nodesTMasterid = maps[i].get("task_master_id").toString();
					engagmCode = maps[i].get("Engagement_Code").toString();
					nodetaskcode = maps[i].get("task_code").toString();
					RequeueNodeList.add(maps[i].get("task_code").toString());// add the requeued task_code to arraylist
																				// used for assertion later
				}
			}
		}
		// Wait till that task is processed
		String taskMysqlStatus = "nothingbro";
		while ((!taskMysqlStatus.equalsIgnoreCase("processed")) && time < 300000) {
			System.out.println("Waiting for task to be processed..");

			HashMap res = dbcon.engagement_task_table_query_usingTaskMasterId(engagmCode, nodesTMasterid);
			if (res.get("task_status").toString().equalsIgnoreCase("processed")) {
				taskMysqlStatus = "processed";
			} else {
				System.out.print("+");
				Thread.sleep(15000);
				time = time + 15000;

			}
		}
		if (time >= 300000) {
			System.out.print("Task not processed after waiting..Aborting Requeue "+nodesTMasterid+" "+nodetaskcode);
			test.fail("Task not processed after waiting..Aborting Requeue"+nodesTMasterid+" "+nodetaskcode);
			AbortRequeue = true;
		}

		if (AbortRequeue == false) {
			JSONObject jsonreq = new JSONObject();
			jsonreq.put("userCode", userCode);
			jsonreq.put("jobCode", jobCode);
			jsonreq.put("taskCode", nodetaskcode);
			jsonreq.put("option", "REQUEUE");
			jsonreq.put("nodeId", opNode);

			// API Execution...
			RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/task/requeue";
			Response res = null;
			System.out.println("REQUEUE Json req: " + jsonreq);
			try {
				res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
						.then().contentType(ContentType.JSON).extract().response();
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("Requeue Api failed: " + e1);
				test.fatal("Requeue API Issue: " + e1);
			}
			System.out.println(
					"REQUEUE API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());

			if (res.statusLine().contains("200")) {
				requeueCount++;
				System.out.println("Requeue done for taskCode: " + nodetaskcode + " task_master_id: " + nodesTMasterid
						+ " node: " + opNode);
			} else {
				System.out.println("Requeue API failed");
				test.fail("Requeue API failed");
			}
		}

	}

	public void rework() throws InterruptedException {
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);
		double time = 0;
		Boolean AbortRework = false;
		String nodesTMasterid = null;
		String engagmCode = null;
		String nodetaskcode = null;

		// Finding TaskMasterId of the node/task to be requeued
		int hit = 0;

		for (int i = 0; i < listAll.size(); i++) {

			if (maps[i].get("node_id").toString().equals(opNode)) // Finds the desired node
			{
				hit++;
				if (hit == reworkCount) {
					System.out.println(
							"found the req node :" + maps[i].get("task_master_id") + " " + maps[i].get("task_code"));
					nodesTMasterid = maps[i].get("task_master_id").toString();
					engagmCode = maps[i].get("Engagement_Code").toString();
					nodetaskcode = maps[i].get("task_code").toString();
					ReworkNodeList.add(maps[i].get("task_code").toString());// add the requeued task_code to arraylist
																			// used for assertion later
				}
			}
		}
		// Wait till that task is processed

		if (IsTaskMysqlProccessedState == true) { // if it is set to true then wait for the task to be processed in
													// mysql
			String taskMysqlStatus = "nothingbro";
			time = 0;
			System.out.println("Waiting for task to be processed..");
			while ((!taskMysqlStatus.equalsIgnoreCase("processed")) && time < 300000) {
				
				HashMap res = dbcon.engagement_task_table_query_usingTaskMasterId(engagmCode, nodesTMasterid);
				if (res.get("task_status").toString().equalsIgnoreCase("processed")) {
					taskMysqlStatus = "processed";
				} else {
					System.out.print("+");
					Thread.sleep(15000);
					time = time + 15000;
				}
			}

			if (time >= 300000) {
				System.out.println("Task not processed in mysql engagement table ..Aborting Rework API call "+nodesTMasterid+" "+nodetaskcode);
				test.fail("Task not processed in mysql engagement table..Aborting Rework API call "+nodesTMasterid+" "+nodetaskcode);
				AbortRework = true;
			}
		}

		if (AbortRework == false) {

			JSONObject jsonreq = new JSONObject();
			jsonreq.put("userCode", userCode);
			jsonreq.put("jobCode", jobCode);
			jsonreq.put("taskCode", nodetaskcode);
			jsonreq.put("option", "REWORK");

			// API Execution...
			RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/task/requeue";
			Response res = null;
			System.out.println("REWORK Json req: " + jsonreq);
			try {
				res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
						.then().contentType(ContentType.JSON).extract().response();
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println("REWORK Api failed: " + e1);
				test.fatal("REWORK API Issue: " + e1);
			}
			System.out.println(
					"REWORK API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());

			if (res.statusLine().contains("200")) {
				reworkCount++;
				System.out.println("REWORK done for taskCode: " + nodetaskcode + " task_master_id: " + nodesTMasterid
						+ " node: " + opNode);
			} else {
				System.out.println("REWORK API failed");
				test.fail("REWORK API failed");
			}
		}

	}

	public void flushByJob() throws InterruptedException {
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("action", "flushed");
		jsonreq.put("engagementCode", EngagementCode);
		jsonreq.put("jobCode", jobCode);
		double time = 0;

		// API Execution...
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/flush/alltask/0";
		Response res = null;
		System.out.println("flushByJob Json req: " + jsonreq);
		try {
			res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
					.then().contentType(ContentType.JSON).extract().response();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("flushByJob Api failed: " + e1);
			test.fatal("flushByJob API Issue: " + e1);
		}
		System.out.println(
				"flushByJob API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());

		if (res.statusLine().contains("200")) {
			reworkCount++;
			System.out.println("flushByJob done for jobCode: " + jobCode);

			int RethinktaskCount = 999;
			while (!(RethinktaskCount == 0) && time < 300000) {
				RethinktaskCount = rethink.main_task_list_job_count(jobCode);
				Thread.sleep(15000);
				System.out.println("Waiting for flush by Job to clear Rethink tasks for jobcode: " + jobCode);
				time = time + 15000;
			}

			if (time >= 300000) {
				System.out.println("Flush didnot clear rethink for jobcode: " + jobCode);
				test.fail("Flush didnot clear rethink for jobcode: " + jobCode);
			} else {
				System.out.println("Rethink flushed of jobCode: " + jobCode);
				test.info("Rethink flushed of jobCode: " + jobCode);
			}

		} else {
			System.out.println("flushByJob API failed");
			test.fail("flushByJob API failed");
		}
	}

	@SuppressWarnings("unchecked")
	public void flushBytask(String action) throws InterruptedException {
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);

		String nodesTMasterid = null;
		String engagmCode = null;
		String nodetaskcode = null;
		// Getting type of action

		int hit = 0;

		for (int i = 0; i < listAll.size(); i++) {

			if (maps[i].get("node_id").toString().equals(opNode)) // Finds the desired node
			{
				hit++;
				if (hit == flushCount) {
					System.out.println(
							"found the req node :" + maps[i].get("task_master_id") + " " + maps[i].get("task_code"));

					// Check if want flush task or node
					if (action.equalsIgnoreCase("flushBytask")) {
						type = "task";
						nodetaskcode = maps[i].get("task_code").toString();
						FlushNodeList.add(maps[i].get("task_code").toString());// store Nodel
					} else {
						type = "node";
						nodetaskcode = maps[i].get("node_task_id").toString();
						System.out.println(nodetaskcode);
						FlushNodeList.add(maps[i].get("node_task_id").toString());

					}

					System.out.println(nodetaskcode);

				}

			}
		}

		// Wait till that task is processed

		/*
		 * if (IsTaskMysqlProccessedState == true) { // if it is set to true then wait
		 * for the task to be flushed in // mysql String taskMysqlStatus = "nothingbro";
		 * while (!taskMysqlStatus.equalsIgnoreCase("flushed")) { HashMap res =
		 * dbcon.engagement_task_table_query_usingTaskMasterId(engagmCode,
		 * nodesTMasterid); if
		 * (res.get("task_status").toString().equalsIgnoreCase("flushed")) {
		 * taskMysqlStatus = "flushed"; } else {
		 * System.out.println("Waiting 15 sec for task to be flushed..");
		 * Thread.sleep(15000); } } }
		 */
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("id", nodetaskcode);
		jsonreq.put("type", type);
		jsonreq.put("action", "flushed");
		jsonreq.put("comment", "Flushed by Automation testing");

		// API Execution...
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/flush/task/0";
		Response res = null;
		System.out.println("FLUSHED Json req: " + jsonreq);
		try {
			res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
					.then().contentType(ContentType.JSON).extract().response();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("flushByTask Api failed: " + e1);
			test.fatal("flushByTask API Issue: " + e1);
		}
		System.out.println(
				"flushByTask API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());
		double time = 0;
		if (res.body().path("appcode").toString().equalsIgnoreCase("50003")) {
			flushCount++;
			System.out.println("flushByTask done for taskCode: " + nodetaskcode);
			int RethinktaskCount = 999;
			while (!(RethinktaskCount == 0) && time < 150000) {
				RethinktaskCount = rethink.main_task_list_job_count(task_code);
				Thread.sleep(15000);
				System.out.println("Waiting 15 sec for flush to clear Rethink tasks for taskcode: " + task_code);

			}
			if (time >= 150000) {
				System.out.println("Flush didnot clear rethink for taskcode: " + task_code);
				test.fail("Flush didnot clear rethink for jobcode: " + task_code);
			} else {
				System.out.println("Rethink flushed of taskcode: " + task_code);
				test.info("Rethink flushed of taskcode: " + task_code);
			}

		} else {

			System.out.println("flushByTask API failed" + flushCount);
			test.fail("flushByTask API failed");
		}

	}

	public void resetByJob() throws InterruptedException
	{
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("engagementCode", EngagementCode);
		jsonreq.put("jobCode",jobCode );
		jsonreq.put("action", "reset");
		double time = 0;

		// API Execution...
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/flush/alltask/0";
		Response res = null;
		System.out.println("RESET ByJob Json req: " + jsonreq);
		try {
			res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
					.then().contentType(ContentType.JSON).extract().response();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("RESET ByJob Api failed: " + e1);
			test.fatal("RESET ByJob API Issue: " + e1);
		}
		System.out.println(
				"RESET ByJob API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());

		if (res.statusLine().contains("200")) {			
			System.out.println("RESET ByJob done for jobCode: " + jobCode);
		} else {
			System.out.println("flushByJob API failed");
			test.fail("flushByJob API failed");
		}
		
	}
	
	public void resetByTaskCodeorNodetaskId(String taskorNodeTaskID)
	{
		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		
		jsonreq.put("id", EngagementCode);
		jsonreq.put("type","task" );
		jsonreq.put("action", "reset");
		jsonreq.put("comment", "reset karlooo");
		double time = 0;

		// API Execution...
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/platform/flush/task/0";
		Response res = null;
		System.out.println("RESET ByJob Json req: " + jsonreq);
		try {
			res = given().header("Authorization", token).body(jsonreq).when().contentType(ContentType.JSON).post()
					.then().contentType(ContentType.JSON).extract().response();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("RESET ByJob Api failed: " + e1);
			test.fatal("RESET ByJob API Issue: " + e1);
		}
		System.out.println(
				"RESET ByJob API Response Status code: " + res.statusLine() + " JSON Response: " + res.asString());

		if (res.statusLine().contains("200")) {			
			System.out.println("RESET ByJob done for jobCode: " + jobCode);
		} else {
			System.out.println("flushByJob API failed");
			test.fail("flushByJob API failed");
		}
	}
	
	@AfterClass
	public void teardown() throws InterruptedException {
		// Assertions(CurrentTestCaseSerial);
		reports.flush();
		try {
			String Jname = System.getProperty("JenkinsJobName"); // Fetches job name from maven commandline in jenkins
			ExtentManagerObj.copyFile("C:\\Program Files (x86)\\Jenkins\\workspace\\" + Jname + "\\");
			// ExtentManagerObj.copyFile("C:\\Program Files
			// (x86)\\Jenkins\\workspace\\Tasking Job1 Requeue+Rework\\");
		} catch (Exception e) {
			System.out.println("Report copy issue to jenkins workspace " + e);
			e.printStackTrace();
		}

	}

}
