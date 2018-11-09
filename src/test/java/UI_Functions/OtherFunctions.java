package UI_Functions;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.Locator;
/*
generic functions should be added here
Author:Debapriyo 13-oct-2018
*/
public class OtherFunctions {
	WebDriver driver;
	NgWebDriver ngDriver;

	public OtherFunctions(WebDriver drivers, NgWebDriver ngDriver) {
		driver = drivers;
		this.ngDriver = ngDriver;
	}
	
	public void getText(String strxpath)  {

		try {
			
			By Locators = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locators)).getText();

		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
	public Boolean presenceOfElement(String strxpath)  {
		Boolean result=false;

		try {
			
			By Locators = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			result=wait.until(ExpectedConditions.visibilityOfElementLocated(Locators)).isDisplayed();
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result=false;
			return result;
			
		}

	}
	
	public void alertAccept()
	{
		/*WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent());*/
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();

			System.out.println("Clicked OK");
		} catch (Exception e) {
			System.out.println("Failed to switch to Alert: "+e);
			e.printStackTrace();
		}
		
		
	}
	public void alertDismiss()
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().dismiss();
			System.out.println("Clicked cancel");
		} catch (Exception e) {
			System.out.println("Failed to switch to Alert: "+e);
			e.printStackTrace();
		}
		

	}
	
	public void switchFrame(String strXpath)
	{
		try
		{
			By buttonLocator = Locator.getWebElement(strXpath);
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator));
			WebElement frame =driver.findElement(buttonLocator);
		    driver.switchTo().frame(frame); 
		//	driver.switchTo().frame(0); 
		    System.out.println("Frame switched  ");
	}
	catch (Exception e) {
		System.out.println("Failed to switch other Frame "+e);
		e.printStackTrace();
		Assert.fail();
	
	}
		


}
}
