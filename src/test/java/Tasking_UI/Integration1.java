package Tasking_UI;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.paulhammant.ngwebdriver.NgWebDriver;

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
import Utilities.IOExcel;
import Utilities.PathUtility;
import Utilities.RetryCountIfFailed;
import lib.Rethink_query;
import lib.TaskAdd;

public class Integration1 {
	public WebDriver driver;
	IOExcel excelUtil;
	Screen scrn;
	ExtentManager ExtentManagerObj;
	ExtentReports reports;
	DriverUtil DrvUtil;
	NgWebDriver ngDriver;
	KeyboardFunction keyboardfunct;
	TextBox textbox;
	Button button;
	OtherFunctions otherFunctions;
	AnnotationClass annotation;
	Link link;
	DropDown dropdown;
	Label label;
	PathUtility path;
	ExtentTest test;
	String username;
	String project;
	String flowName;
	String jobName;
	String testCaseNo;
	int numberOfFrames;

	@BeforeTest
	public void setup(ITestContext context) {
		path = new PathUtility(context);
		scrn = new Screen();// Sikuli Obj
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent(this.getClass().getSimpleName().toString());
		DrvUtil = new DriverUtil();
		driver = DrvUtil.DriverSetup("chrome");
		ngDriver = DrvUtil.getngDriver();

		keyboardfunct = new KeyboardFunction(driver);
		textbox = new TextBox(driver, ngDriver);
		button = new Button(driver, ngDriver);
		// ashot = new AshotUtil(driver);
		// context.setAttribute("ashotobj", ashot);
		// datepicker = new DatePicker(driver);
		otherFunctions = new OtherFunctions(driver, ngDriver);
		annotation = new AnnotationClass(driver, ngDriver);
		link = new Link(driver, ngDriver);
		dropdown = new DropDown(driver, ngDriver);
		label = new Label(driver);
		excelUtil = new IOExcel();
		excelUtil.excelSetup("./TestData/MasterDataSheetUI.xlsx");

	}

	@Test(priority = 1)
	public void login(ITestContext context) {
		try {

			

			// Fetch Excel data sheet Row Number from testng xml parameters
			String row = context.getCurrentXmlTest().getParameter("RowNum");
			username = excelUtil.getExcelStringData(Integer.parseInt(row), 0, "job");
			project = excelUtil.getExcelStringData(Integer.parseInt(row), 7, "job");
			flowName = excelUtil.getExcelStringData(Integer.parseInt(row), 9, "job");
			jobName = excelUtil.getExcelStringData(Integer.parseInt(row), 10, "job");
			testCaseNo = excelUtil.getExcelStringData(Integer.parseInt(row), 2, "job");
			
			test = reports.createTest(testCaseNo);
			context.setAttribute("testobj", test);

			// ******************GRID SETUP**********************
			String Node = "http://10.34.1.206:4444/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			// cap.setPlatform(Platform.WIN10);*/
			// cap.setVersion("64");
			// cap.setCapability("applicationName", appname);
			// driver = new RemoteWebDriver(new URL(Node), cap);

			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

			// ******************TEST STRATS************************

			driver.navigate().to("https://itest.imerit.net/");
			System.out.println("https://itest.imerit.net Page opened for " + username + " with Thread Id:- "
					+ Thread.currentThread().getId());

			Thread.sleep(3000);
			textbox.SetText("login_userID", username);
			textbox.SetText("login_password", "I0001I0001");
			button.Click("login_button");

			System.out.println("Logged in to IMPP : "+username);

		} catch (Exception e) {

			System.out.println("Some problem : " + e);
			e.printStackTrace();
		}
	}

	@Test(priority = 2, dependsOnMethods = { "login" })

	public void TC_2(ITestContext context) {
		int nooftask;
		try {
			nooftask=Integer.parseInt(System.getProperty("tasks")); //How many tasks to do ?
			//nooftask = 3;
			numberOfFrames=Integer.parseInt(System.getProperty("numberOfFrames"));//How many frames this task has ?
			int j = 0;

			link.Click("hambugMenu");
			link.Click("Contributor");
			link.Click("work_tracker");
			Thread.sleep(5000);
			link.XpathDataClick("itest_project", project);
			Thread.sleep(5000);
			link.XpathDataClick("itest_flow", flowName);
			Thread.sleep(5000);
			dropdown.SelectValue("itest_job", jobName);
			link.Click("op_start");
			Thread.sleep(2000);

			// ************Tasking starts*********************

			otherFunctions.switchFrame("tool_area");

			for (int k = 1; k <= nooftask; k++) {

				System.out.println("*********Task "+k+" starts****** for "+username+" : "+jobName);
				WebDriverWait waits = new WebDriverWait(driver, 40);
				waits.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[name()='svg']//*[name()='image'][@style='display: block;']")));

				Pattern running = new Pattern("./image/poly2");
				Match p = scrn.wait(running, 30);
				p.click();

				for (int i = 1; i <= numberOfFrames; i++) {
					System.out.println("image webdriver wait..");
					WebDriverWait waits2 = new WebDriverWait(driver, 30);
					WebElement annotations2 = waits2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[name()='svg']//*[name()='image'][@style='display: block;']")));

					annotation.annotation_poly_rndm(annotations2);
					keyboardfunct.keyPressed("annotatedLoc", "E");
					System.out.println("Sleep after pressing E..");
					Thread.sleep(3000);
					if(i!=10)
					{
					link.JSClick("Tool_nextButton");
					System.out.println("Submitted task for frame: " + i);
					test.info("Submitted task for frame: " + i);
					System.out.println("Sleep after next button..");
					Thread.sleep(10000);
					}
										
					
				}
				System.out.println("Sleeping 10 sec before final submit ..");
				Thread.sleep(10000);
				link.JSClick("tool_submit");
				
				Pattern sync = new Pattern("./image/submit_sync.png");
				Match p2 = scrn.wait(sync, 5);
				while(p2.exists(sync,5)!=null)
				{
					Pattern syncok = new Pattern("./image/submit_sync_ok.png");
					Match p3 = scrn.wait(syncok, 5);
					p3.click();
					Thread.sleep(5000);
					link.JSClick("tool_submit");					
				}
				System.out.println("**Final submit*** ");
				
				
			}
			System.out.println("Successfully completed task");
			test.pass("Successfully completed task");

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Failed to enter tasking page");

		}

		reports.flush();
		driver.quit();
	}

	@Test(priority = 3)
	public void burndown() {

	}

}
