package PageObjects;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.DriverUtil;

public class Home {
	WebDriver driver;
	NgWebDriver ngDriver;
	
	public Home(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	public Home(WebDriver driver)
	{
		this.driver=driver;
		
	}
	
	public  WebElement Link_logout_dropdown_menu()
	{
		ngDriver.waitForAngularRequestsToFinish();
		
		Wait wait = new WebDriverWait(driver, 30)		 
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
		try 
		{
			ele=(WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//ul[@id='nav-mobile']/li[2]"))));
		//	driver.findElement(By.xpath("//ul[@id='nav-mobile']/li[2]"));		
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}
	public  WebElement Link_logout()
	{
		ngDriver.waitForAngularRequestsToFinish();
		
		Wait wait = new WebDriverWait(driver, 30)				 
			    .withTimeout(30, TimeUnit.SECONDS)			 
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
		try {
			 
			ele=(WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@class='blue-text text-accent-3']"))));
		//	driver.findElement(By.xpath("//a[@class='blue-text text-accent-3']"));	
			
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}
	
	
	public  WebElement Link_iMPP()
	{
		ngDriver.waitForAngularRequestsToFinish();
		
		Wait wait = new WebDriverWait(driver, 30)				 
			    .withTimeout(30, TimeUnit.SECONDS)			 
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
		try {
			 
			ele=(WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'iMPP')]"))));
		//	driver.findElement(By.xpath("//a[@class='blue-text text-accent-3']"));	
			
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}
	

	public  WebElement MOM_Tab()
	{
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30)				  
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
			 
			try {
				ele=(WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[@class='tab enhance']"))));
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
		
		return ele;
	}
	
	public  WebElement Details_tab()
	{
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30)				  
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
			 
			try {
				ele=(WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[@class='tab details']"))));
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
		
		return ele;
	}
	

}
