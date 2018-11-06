package UI_Functions;

import java.io.IOException;

import java.util.Properties;
import java.util.Set;

import Utilities.Locator;
import Utilities.ORFile;
import Utilities.WebDriverSelector;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



	public class KeyboardFunction 
	{ 
		WebDriver driver;
		//static Properties OR;
		String strxpath,strdata;
		
		
	public KeyboardFunction(WebDriver drivers)
	{
		driver=drivers;
	}
	
	
	public void EnterKey() throws IOException
	{
	try
		{
		Actions action = new Actions(driver); 
		action.sendKeys(Keys.ENTER).build().perform();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		}
	
	public void mouseClick(String strXpath)
	{
	try
		{
		Actions action = new Actions(driver); 
		By area=Locator.getWebElement(strXpath);
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Point p=wait.until(ExpectedConditions.elementToBeClickable(area)).getLocation();
		int x=p.getX();
		int y=p.getY();
		System.out.println("X"+x+"Y"+y);
		action.moveToElement(driver.findElement(area), 1000, 675);
		action.dragAndDropBy(driver.findElement(area), x,y).build().perform();
		action.release().build().perform();
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		}
	public void keyPressed(String strxpath,String keys) throws IOException
	{
	try
		{
		Actions action = new Actions(driver); 
		action.sendKeys(keys).build().perform();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
		}
	
	}	
			




