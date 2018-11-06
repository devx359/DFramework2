package Tasking_UI;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Utilities.DriverUtil;

public class test {
	DriverUtil drv = new DriverUtil();
	WebDriver driver;
	
	//Create a new tab and switch
	@Test
	public void fun() throws InterruptedException
	{
		driver = drv.DriverSetup("chrome");
		driver.get("https://www.google.com");
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		String NewTabLink = Keys.chord(Keys.CONTROL,Keys.RETURN); 
		driver.findElement(By.id("lst-ib")).sendKeys(NewTabLink );
		Set<String> handles = driver.getWindowHandles();
		ArrayList <String> tabs= new ArrayList<String>(handles);
		driver.switchTo().window(tabs.get(0));
		
		Integer.parseInt("0");
		Integer.toString(1);
		
		
		Thread.sleep(5000);
	}
	

}
