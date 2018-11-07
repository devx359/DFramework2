package UI_Functions;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Locator;

public class Label {
	
	WebDriver driver;

	public Label(WebDriver drivers) {
		driver = drivers;
	}

	public String getText(String strxpath) throws IOException, InterruptedException
	{
		String strData = null;
		try
		{
			
			By textboxLocator=Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			strData=wait.until(ExpectedConditions.presenceOfElementLocated(textboxLocator)).getText();
				
		}
		catch(Exception e)
		{
			System.out.println("Unable to gettext"+strxpath);
		}
		return  strData;
		
	}
	
	public String	xPathGetText(String strxpath,WebDriver driver) throws IOException, InterruptedException
	{
		String strData = null;
		try
		{
		
			WebDriverWait wait = new WebDriverWait(driver, 60);
			strData=wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(strxpath)))).getText();
				
		}
		catch(Exception e)
		{
			System.out.println("Unable to gettext "+strxpath);
		}
		return  strData;
		
	}
	
	public boolean	isPresent(String strxpath) 
	{
		WebElement element = null;
		boolean status = false;
		try
		{
			System.out.println("webdriver wait for "+strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			element=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strxpath)));
			//status=element.isDisplayed();
			status=true;
				
		}
		catch(Exception e)
		{
			System.out.println("isPresent Failed for  "+strxpath+e);
			e.printStackTrace();
		}
		return  status;
		
	}
	
	
}
