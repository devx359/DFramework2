package Tasking_UI;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import org.testng.Assert;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import UI_Functions.AnnotationClass;
import UI_Functions.Button;
import UI_Functions.DatePicker;
import UI_Functions.DropDown;
import UI_Functions.KeyboardFunction;
import UI_Functions.Label;
import UI_Functions.Link;
import UI_Functions.OtherFunctions;
import UI_Functions.TextBox;
import Utilities.AshotUtil;
import Utilities.Dbconnection;
import Utilities.DriverUtil;
import Utilities.ExtentManager;
import Utilities.JSWaiter;
import Utilities.Locator;
import Utilities.PathUtility;
import lib.Rethink_query;
import lib.TaskAdd;
import tasking.ITasking;

public class UIBaseClass {

	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	RethinkDB r;
	Dbconnection dbcon;
	TaskAdd taskadd;
	Boolean active = false;
	String action;
	String userCode;
	String CurrentTestCaseSerial = "empty";
	String PrevTestCaseSerial = "empty";
	Boolean Assert = false;
	String jobCode;
	int nooftasks;
	String EngagementCode;
	int Addtask;
	Boolean DeleteRethink = false;
	Boolean DeleteMysql = false;
	Boolean DeleteMysqlLog = false;
	String node;
	String nodetype;
	String username;
	String password;
	String FlowName;
	String token;
	KeyboardFunction functions;
	TextBox textbox;
	Button button;
	DatePicker datepicker;
	KeyboardFunction keyboardfunct;
	
	Link link;
	DropDown dropdown;
	Label label;
	Rethink_query rethink;
	DriverUtil DrvUtil;
	WebDriver driver;
	String browser;
	PathUtility path;
	String project;
	String sub_project_code;
	String JobName;
	String NodeName;
	String Projectname;
	AshotUtil ashot;
	NgWebDriver ngDriver;
	OtherFunctions otherFunctions;
	AnnotationClass annotation;
//	JSWaiter jswaiter;

	// Pre test configurations are fetched here
	@BeforeTest
	public void beforeEachTestSetup(ITestContext context) {
		path = new PathUtility(context);// Sets up paths according to environment parameter passed in testng
										// xml file
		browser = context.getCurrentXmlTest().getParameter("browserType");

	}

	// Instantiates all the required Utility classes
	@BeforeClass
	public void classLoader(ITestContext context) {

		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent(this.getClass().getSimpleName().toString());
		dbcon = new Dbconnection();
		taskadd = new TaskAdd();
		DrvUtil = new DriverUtil();
		driver = DrvUtil.DriverSetup(browser);
		ngDriver = DrvUtil.getngDriver();

		// Pass the driver and ngwebdriver
/*		jswaiter= new JSWaiter();
		jswaiter.setDriver(driver);*/
		keyboardfunct = new KeyboardFunction(driver);
		textbox = new TextBox(driver, ngDriver);
		button = new Button(driver, ngDriver);
		ashot = new AshotUtil(driver);
		context.setAttribute("ashotobj", ashot);
		datepicker = new DatePicker(driver);
		otherFunctions=new OtherFunctions(driver, ngDriver);
		annotation=new AnnotationClass(driver, ngDriver);
		link = new Link(driver, ngDriver);
		dropdown = new DropDown(driver, ngDriver);
		label = new Label(driver);

	}

	// Reads data from excel and starts new extent report testcase
	public void defaultSteps(Hashtable<String, String> tdata) throws ParseException {
		// count = 1;

		// Fetch from Excel
		try {

			active = Boolean.parseBoolean(tdata.get("Active").toString());
			action = tdata.get("action");
			CurrentTestCaseSerial = tdata.get("TestCaseNo");
			Assert = Boolean.parseBoolean(tdata.get("Assert").toString());
			jobCode = tdata.get("jobcode");
			EngagementCode = tdata.get("engagement_code");
			project = tdata.get("project");
			sub_project_code = tdata.get("sub_project_code");
			FlowName = tdata.get("FlowName");
			JobName = tdata.get("JobName");
			NodeName = tdata.get("NodeName");
			Projectname = tdata.get("Projectname");
			username=tdata.get("username");
			password=tdata.get("password");

			// userCode = tdata.get("usercode");
			// node = tdata.get("Node");
			// IsTaskMysqlProccessedState =
			// Boolean.parseBoolean(tdata.get("IsTaskMysqlProccessedState").toString());
			// nooftasks = Integer.parseInt(tdata.get("tasks").toString());

			// nodetype = tdata.get("nodetype");
			// Addtask = Integer.parseInt(tdata.get("AddTask").toString());
			// DeleteRethink = Boolean.parseBoolean(tdata.get("DeleteRethink").toString());
			// DeleteMysql = Boolean.parseBoolean(tdata.get("DeleteMysql"));
			// DeleteMysqlLog =
			// Boolean.parseBoolean(tdata.get("DeleteMysqlLog").toString());

			// port = Integer.parseInt(tdata.get("port").toString());
			// qcnode = tdata.get("qcnode");
			// qcpercent = Integer.parseInt(tdata.get("qcpercent").toString());
			// submit_type = tdata.get("submit_type");
			 password = tdata.get("password");
			// waitForTaskResponseProcess =
			// Boolean.parseBoolean(tdata.get("waitForTaskResponseProcess").toString());
			// nodeNumber = Integer.parseInt(tdata.get("NodeNumber").toString());
			// System.out.println("nodeNumber " + nodeNumber);

		} catch (Exception e2) {
			System.out.println("Excel file read issue: " + e2);
			/*
			 * e2.printStackTrace(); e2.getMessage();
			 */
		}

		if (active == true) { // If test case is active then execute

			// login();

			/*
			 * if (DeleteRethink == true) {
			 * 
			 * } if (DeleteMysql == true) { System.out.println("Delete MySql started");
			 * dbcon.deleteTaskFromMysql(EngagementCode, jobCode, DeleteMysqlLog); } if
			 * (Addtask > 0) { taskadd.add(userCode, jobCode, Addtask, token); }
			 */

			// Sets Extent test name here
			/*
			 * if (PrevTestCaseSerial.equals("empty")) { test =
			 * reports.createTest(CurrentTestCaseSerial);
			 * System.out.println("-------------------------------------------------------")
			 * ; System.out.println("           " + CurrentTestCaseSerial);
			 * System.out.println("-------------------------------------------------------")
			 * ;
			 * 
			 * } else { if (!CurrentTestCaseSerial.equalsIgnoreCase(PrevTestCaseSerial)) {
			 * // Assertions(PrevTestCaseSerial);
			 * System.out.println("-------------------------------------------------------")
			 * ; System.out.println("           " + CurrentTestCaseSerial);
			 * System.out.println("-------------------------------------------------------")
			 * ;
			 * 
			 * test = reports.createTest(CurrentTestCaseSerial);
			 * 
			 * listAll.clear(); // Clear the array list of results when new testcase is
			 * starting RequeueNodeList.clear();// Clear Requeued node list if any of
			 * previous testcases ReworkNodeList.clear(); FlushNodeList.clear(); flushCount
			 * = 1; requeueCount = 1; // reset requeue count reworkCount = 1;
			 * dontDoAnyAction = false; // reset Action go ahead flag } else {
			 * 
			 * }
			 * 
			 * } PrevTestCaseSerial = CurrentTestCaseSerial;
			 */
			// Determine what action to do ? tasking ? rework ? requeue ? ...

			/*
			 * try { // doWhatAction(); // save all records for the particular task } catch
			 * (InterruptedException e) {
			 * 
			 * e.printStackTrace(); } } else { System.out.println("Test Case skipped"); }
			 */

		}
	}

	// Report
	@AfterClass
	public void teardown() throws InterruptedException {

		reports.flush();

		try {
			String Jname = System.getProperty("JenkinsJobName"); // Fetches job name from maven commandline in
																	// jenkins,so if you dont execute via maven then it
																	// will throw exception
			if (!(Jname == null)) {
				ExtentManagerObj.copyFile("C:\\Program Files (x86)\\Jenkins\\workspace\\" + Jname + "\\");
			}

		} catch (Exception e) {
			System.out.println("Report copy issue to jenkins workspace " + e);
			e.printStackTrace();
		}

	}

	@AfterSuite
	public void afterSuiteTasks() {
		driver.quit();

	}

	// Use this method after clicking create new simple flow 1op 1qc
	public void createSimpleFlow() {
		try {
			link.Click("Link_Jsonsliderclose");

			Actions action = new Actions(driver);
			action.clickAndHold(driver.findElement(Locator.getWebElement("Button_start"))).moveByOffset(350, 200)
					.release().build().perform();
			Thread.sleep(500);
			link.Click("Menu_AddNodes");
			action.clickAndHold(driver.findElement(Locator.getWebElement("Button_op"))).moveByOffset(500, 150).release()
					.build().perform();
			Thread.sleep(500);
			link.Click("Menu_AddNodes");
			action.clickAndHold(driver.findElement(Locator.getWebElement("Button_qc"))).moveByOffset(650, 100).release()
					.build().perform();
			link.Click("Menu_AddNodes");
			action.clickAndHold(driver.findElement(Locator.getWebElement("Button_end"))).moveByOffset(800, -45)
					.release().build().perform();

			link.Click("Link_DrawLine");

			action.moveToElement(driver.findElement(Locator.getWebElement("Created_startNode"))).click()
					.moveToElement(driver.findElement(Locator.getWebElement("Created_op1Node"))).click()
					.moveToElement(driver.findElement(Locator.getWebElement("Created_qc1Node"))).click()
					.moveToElement(driver.findElement(Locator.getWebElement("Created_endNode"))).click().build()
					.perform();

			action.moveToElement(driver.findElement(Locator.getWebElement("Link_DrawLine"))).moveByOffset(-100, 100)
					.click().build().perform(); // clickanywhere outside to stop blinking
			Thread.sleep(2000);

			textbox.SetText("TextBox_FlowTitle", "MymYFlow");
			// set op node
			link.JSClick("Created_op1Node");
			textbox.SetText("TextBox_toolURL", path.simple_OP_tool_url);
			textbox.SetText("Textbox_duration", "5");
			button.jsUploadFile("Button_uploadMeta", path.op_metadata_json_file_path);
			Thread.sleep(2000);
			// set QC node
			link.JSClick("Created_qc1Node");
			textbox.SetText("TextBox_toolURL", path.simple_QC_tool_url);
			textbox.SetText("Textbox_duration", "5");
			button.jsUploadFile("Button_uploadMeta", path.qc_metadata_json_file_path);
			// Save Flow or clear
			/*link.JSClick("Button_Save_toggle");
			link.JSClick("Button_clear");
			link.JSClick("Button_Save");*/
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void loginWithPassword() {

		JSONObject jsonreq = new JSONObject();
		jsonreq.put("userCode", userCode);
		jsonreq.put("password", password);
	//	jsonreq.put("agentMeta", );
	
	/*String asas="ADSD";
	asas.equalsIgnoreCase(anotherString)*/

		// API Execution...
		RestAssured.baseURI = PathUtility.APIurl + "/impp/imerit/auth/signin/0";
		Response res = null;
		System.out.println("Login Json req: " + jsonreq);
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
/*
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
		*/

	}
	
	public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
        System.out.println("dom ready");
    }
}
