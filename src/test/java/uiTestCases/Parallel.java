package uiTestCases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestContext;

import Utilities.IOExcel;

public class Parallel {
	
	WebDriver driver;
	int col=1;
	int row=1;
	
	@Parameters("Excelsheet")
	@BeforeTest
	public void setup(String Excelsheet)
	{
		
		System.setProperty("webdriver.chrome.driver","F:\\softwares\\selenium\\chromedriver_1.exe");
	
	}
	
	@Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
	public void fun1(Hashtable<String,String> tdata,ITestContext context) throws InterruptedException
	{
	//	System.out.println("inside @Test--- @ThreadId"+Thread.currentThread().getId());
		driver=new ChromeDriver();	
		
		driver.get("https://www.google.co.in");
		driver.findElement(By.xpath("//input[@id=\"lst-ib\"]")).sendKeys(tdata.get("searchtext"));

		driver.findElement(By.xpath("//input[@id=\"lst-ib\"]")).sendKeys(Keys.RETURN);
		System.out.println("Running test @ThreadId"+Thread.currentThread().getId()+"with data:"+tdata.get("searchtext"));
		
		IOExcel obj = (IOExcel) context.getAttribute("Excelobj");
		obj.setExcelStringData(col, row, tdata.get("searchtext")+"successfully searched", "Sheet2");
		row++;
		Thread.sleep(2000);
		
		driver.close();
		
		
	}

}
