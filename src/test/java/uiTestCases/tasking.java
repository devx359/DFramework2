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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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


public class tasking  {
	
ISuiteResult result;
KeyboardFunction functions ;
TextBox textbox;
Button button;
//DatePicker datepicker=new DatePicker();
KeyboardFunction keys;
Link link;
DropDown dropdown;

//Label label=new Label();
WebDriverSelector Webdriver=new WebDriverSelector();
WebDriver driver;	
ExtentReports reports;
ExtentTest test;
double peopleShift;
DriverUtil DrvUtil;
NgWebDriver ngDriver;
String browser;

	@BeforeClass
	@Parameters("browserType")
	public void StartSuite(@Optional  String browserType,ITestContext context ) throws IOException, InterruptedException 
	{
		//test = reports.createTest("Login Test Case: ");
		//reports = ExtentManager.GetExtent("ReportName");
		PathUtility path=new PathUtility(context);
		//driver=Webdriver.selectDriver(context.getCurrentXmlTest().getParameter("browserType"));
		browser = context.getCurrentXmlTest().getParameter("browserType");
		DrvUtil = new DriverUtil();
		ngDriver = DrvUtil.getngDriver();
		KeyboardFunction functions =new KeyboardFunction(driver);
		keys=new KeyboardFunction(driver);
	//	link=new Link(driver);
		//TextBox textbox =new TextBox(driver);
		Button button =new Button(driver,ngDriver);
	    dropdown=new DropDown(driver,ngDriver);
	//	System.out.println(context.getCurrentXmlTest().getParameter("browserType"));
		button.GoogleLogin();
		Webdriver.launch(path.weburl);
		
		
	}
	
	@Test
	(dataProviderClass=Utilities.impp_testdataProvider.class,dataProvider="testdataProvider")
	public void startTasking(Hashtable<String,String> data) 
	//public void VerifyLogin()
	{	
		double totalPeopleShift=0;
		try {
			
			String FlowName=data.get("FlowName");
			String JobName=data.get("JobName");
			String NodeName=data.get("NodeName");
			String Projectname=data.get("Projectname");
			//Roster Code commented 
			
		    link.Click("hambugMenu");
		//	datepicker.datepick("datePicker","dates" ,driver);
		    //alpha code
		    //link.Click("wt", driver);
		   // keys.mouseClick("body", driver);
		    link.Click("Contributor");
		    link.Click("wotr");
		    link.DataClick("itest_project", Projectname);
		    link.DataClick("itest_flow", FlowName);
		
		    //dropdown.SelectValue("selFlow", FlowName, driver);
		    //dropdown.SelectValue("selNode", NodeName, driver);
		    dropdown.SelectValue("itest_job", JobName);
		    link.Click("op_start");
		    link.Click("tool_but1");
		    keys.mouseClick("tool_area");
		    link.Click("tool_submit");
		    
		   // button.Click("op_start", driver);
		   // String windowHandle = driver.getWindowHandle();
		    //newTab.remove(oldTab);
		    // change focus to new tab
		    //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");	    
		    //button.Click("exitTask", driver);
		   // driver.navigate().to("https://itest.imerit.net/dashboard.html#/task/JC-HyMi3TSXm/c8");
		 //   get();
		    
		    
		
		    }
		
		catch (Exception e) 
		{
		 System.out.println("Unable to execute");
		}
	}
	
	/*@AfterClass
	public void afterClass() throws IOException
	{
		reports.flush();
	}
	@AfterSuite
	public void closeBrowser() throws IOException
	{
		Webdriver.DriverClose(driver);
	}*/
	
    }

 
