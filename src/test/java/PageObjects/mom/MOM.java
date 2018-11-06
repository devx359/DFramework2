package PageObjects.mom;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.DriverUtil;

public class MOM {
	
	WebDriver driver;
	NgWebDriver ngDriver;
	
	public MOM(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	public MOM(WebDriver driver)
	{
		this.driver=driver;
		
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
	
	
	public  WebElement Add_MOM()
	{
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30)				  
			    .pollingEvery(1, TimeUnit.SECONDS)			 
			    .ignoring(NoSuchElementException.class);
		
		WebElement ele=null;
			 
			try {
				ele=(WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[contains(@href,\"project_details/mom_add\")]"))));
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
		
		return ele;
	}

}
