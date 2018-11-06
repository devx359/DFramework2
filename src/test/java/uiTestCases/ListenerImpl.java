package uiTestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.AshotUtil;
import Utilities.DriverUtil;
import Utilities.ExtentManager;

public class ListenerImpl {
	
	DriverUtil drvobj;
	WebDriver driver;
	AshotUtil ash;
	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	
	@BeforeSuite
	public void startup(ITestContext context)
	{
		
		context.setAttribute("ashot", ash);
		
		drvobj = new DriverUtil();
		driver=drvobj.DriverSetup("chrome");
		context.setAttribute("driverobj", driver);
		
		ash= new AshotUtil(driver);
		
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent("dummy");
		
	}
	
	@Test
	public void meth1(ITestContext context)
	{
			
		test = reports.createTest("dummy test");
		context.setAttribute("ExtentObj", test);
		
		driver.get("https://www.google.com");
		driver.findElement(By.id("lst-ib")).sendKeys("search something");
		driver.findElement(By.id("lst-ibz")).sendKeys(Keys.ENTER);
		//Assert.assertEquals(driver.getTitle(), "Googles");
		
		
	}
	
	@AfterTest
	public void shutdown()
	{
	//	driver.quit();
	}

}
