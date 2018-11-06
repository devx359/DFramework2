package uiTestCases;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utilities.DriverUtil;
import Utilities.IOExcel;

public class Grid {

	public WebDriver driver;
	IOExcel excelUtil;

	@BeforeTest
	public void setup() {

	}

	//@Test(dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	@Test
	public void fun( ITestContext context) {
		try {
			excelUtil = new IOExcel();
			excelUtil.excelSetup("./TestData/grid1.xlsx");
			DriverUtil drivutil = new DriverUtil();

			//Fetch Excel data sheet Row Number from testng xml parameters 
			String row = context.getCurrentXmlTest().getParameter("RowNum");
			String user = excelUtil.getExcelStringData(Integer.parseInt(row), 0,"Sheet1");

			//******************GRID SETUP**********************
			String Node = "http://10.34.1.206:4444/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			// cap.setPlatform(Platform.WIN10);*/
			// cap.setVersion("64");
			// cap.setCapability("applicationName", appname);

			 driver= drivutil.DriverSetup("chrome");
			//driver = new RemoteWebDriver(new URL(Node), cap);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

			//******************TEST STRATS************************
			driver.navigate().to("https://itest.imerit.net/");
			System.out.println("Page opened for " + user + " with Thread Id:- " + Thread.currentThread().getId());

			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@id='icon_platform_id']")).sendKeys(user);
			driver.findElement(By.xpath("//input[@id='icon_password']")).sendKeys("I0001I0001");
			driver.findElement(By.xpath("//button[@id='login']")).click();
			Thread.sleep(30000);
			driver.quit();
			System.out.println("Page closed for " + user + " with Thread Id:- " + Thread.currentThread().getId());
		} catch (Exception e) {

			System.out.println("Some problem : " + e);
			e.printStackTrace();
		}
	}

}
