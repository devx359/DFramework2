package UI_Functions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.Locator;
import Utilities.WebDriverSelector;

public class Checkbox {
	
	//static WebDriverSelector driver;
	public static RemoteWebDriver driver;
	public static void click(String strxpath) throws FileNotFoundException, IOException
	{
		try
		{
		By checkBoxLocator=Locator.getWebElement(strxpath);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(checkBoxLocator)).click();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		}
	public static void isEnable(String strxpath) throws FileNotFoundException, IOException
	{
		try
		{
		By checkBoxLocator=Locator.getWebElement(strxpath);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(checkBoxLocator)).isEnabled();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		}
}
