package uiTestCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.paulhammant.ngwebdriver.NgWebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ISuiteResult;
import org.testng.ITestContext;

import UI_Functions.Button;
import UI_Functions.DatePicker;
import UI_Functions.DropDown;
import UI_Functions.KeyboardFunction;
import UI_Functions.Label;
import UI_Functions.Link;
import UI_Functions.TextBox;
import Utilities.DriverUtil;
import Utilities.ExtentManager;
import Utilities.PathUtility;
import Utilities.WebDriverSelector;

public class ProjectCreation {

	WebDriverSelector Webdriver = new WebDriverSelector();
	ISuiteResult result;
	

	DropDown dropdown;

	//Label label = new Label();
	
	WebDriver driver;
	ExtentReports reports;
	ExtentTest test;
	Link link;
	Button button;
	double peopleShift;
	TextBox textbox;
	DatePicker datepicker;
	KeyboardFunction keys;
	KeyboardFunction functions;
	PathUtility path;
	DriverUtil DrvUtil;
	NgWebDriver ngDriver;
	String browser;

	@BeforeClass
	@Parameters("browserType")
	public void StartSuite(@Optional String browserType, ITestContext context)
			throws IOException, InterruptedException {
		// test = reports.createTest("Login Test Case: ");
		// reports = ExtentManager.GetExtent("ReportName");
		path = new PathUtility(context);
		//driver = Webdriver.selectDriver(context.getCurrentXmlTest().getParameter("browserType"));
		browser = context.getCurrentXmlTest().getParameter("browserType");
		DrvUtil = new DriverUtil();
		ngDriver = DrvUtil.getngDriver();
		functions = new KeyboardFunction(driver);
	//	textbox = new TextBox(driver);

		//datepicker = new DatePicker();
		keys = new KeyboardFunction(driver);
		button = new Button(driver,ngDriver);
		dropdown = new DropDown(driver,ngDriver);
	//	link = new Link(driver);
		// System.out.println(context.getCurrentXmlTest().getParameter("browserType"));
		button.GoogleLogin();
		Webdriver.launch(path.weburl);

	}

	@Test(dataProviderClass = Utilities.impp_testdataProvider.class, dataProvider = "testdataProvider")
	public void VerifyLogin(Hashtable<String, String> data)
	// public void VerifyLogin()
	{
		double totalPeopleShift = 0;
		try {

			String ProjectCode = data.get("ProjectCode");
			// Roster Code commented

			link.Click("hambugMenu");
			// datepicker.datepick("datePicker","dates" ,driver);
			link.Click("ContribuAccess");
			link.Click("viewProject");
			textbox.SetText("searchProject", ProjectCode);
			keys.EnterKey();

		}

		catch (Exception e) {
			System.out.println("Unable to execute");
		}
	}

	/*
	 * @AfterClass public void afterClass() throws IOException { reports.flush(); }
	 * 
	 * @AfterSuite public void closeBrowser() throws IOException {
	 * Webdriver.DriverClose(driver); }
	 */

}
