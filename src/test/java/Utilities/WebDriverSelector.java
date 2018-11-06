package Utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;


public  class WebDriverSelector {
public  WebDriver driver;
public  NgWebDriver ngDriver;
	
    
	public WebDriver selectDriver(String browserType) throws IOException
	{ 
		
		//String browserType=ORFile.getlocator(browser);
		System.out.println(browserType);
		try
		{
			// if Browser is IE
			if(browserType.equalsIgnoreCase("Chrome"))
			{
				System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver_238.exe");
				driver=new ChromeDriver();	
				driver.manage().window().maximize();
				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				ngDriver.waitForAngularRequestsToFinish();
			
		}
			// if Browser is Firefox
			else if(browserType.equalsIgnoreCase("Firefox"))
			{
			System.setProperty("webdriver.gecko.driver","E:\\Driver\\geckodriver.exe");
			driver=new FirefoxDriver();	
			
			driver.manage().window().maximize();
			ngDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngDriver.waitForAngularRequestsToFinish();
			}
			// if Browser is Chrome
			else
			{
				System.setProperty("webdriver.ie.driver", "E:\\IEDriverServer_x64_2.53.1\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();	
				driver.manage().window().maximize();
				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				ngDriver.waitForAngularRequestsToFinish();
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return driver;
		}
	public WebElement findElement(By id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void launch(String url) throws FileNotFoundException, IOException
	{
		//String TestUrl=ORFile.getlocator(url);
		//driver.get(url);
		driver.navigate().to(url);
		WebDriverWait wait = new WebDriverWait(driver, 30);
	 	wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='g-overlay']")))).click();
		
	}
	public void DriverClose(WebDriver driver)
	{
		driver.close();
	}
	 
	
	}

