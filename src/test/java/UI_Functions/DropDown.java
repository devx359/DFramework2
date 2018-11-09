package UI_Functions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.Locator;
import Utilities.WebDriverSelector;

public class DropDown 
{
	WebDriver driver;
	NgWebDriver ngDriver;
	
	public DropDown(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	
	public  void SelectValue(String strxpath,String dropDownText) //String strxpath2
	{
		try
		{
			By SelectLocator=Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 90);
		    Select Listvalue=new Select(wait.until(ExpectedConditions.presenceOfElementLocated(SelectLocator)));
			Listvalue.selectByVisibleText(dropDownText);
		}
			//input tag dropdown 
			/*else if(xpath.contains("input"))
		    {
				WebElement select=((WebDriver) driver).findElement(By.xpath(xpath));
				select.click();
				Thread.sleep(1000);
				List<WebElement> options = ((WebDriver) driver).findElements(By.tagName("span"));
				
				for(WebElement opt: options )
			      {
					if(strdata.equalsIgnoreCase(opt.getText()))
				    {
				opt.click();
				    }
			      }
		    }
			//input tag dropdown if name contains single quotes 
			else
			{
				String xpath1="//input[@value=\"";
				String xpath3="\"]";
				String xpath4=xpath1+xpath+xpath3;
				WebElement select=((WebDriver) driver).findElement(By.xpath(xpath4));
				select.click();
				Thread.sleep(500);
				List<WebElement> options = ((WebDriver) driver).findElements(By.tagName("span"));
				
				for(WebElement opt: options )
			      {
					if(strdata.equalsIgnoreCase(opt.getText()))
				    {
				opt.click();
				    }
			      }
			}*/
			
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public  void XpathSelectValue(String strxpath,String strdata) //String strxpath2
	{
		try
		{
			By SelectLocator=Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 50);
		    Select Listvalue=new Select(wait.until(ExpectedConditions.presenceOfElementLocated(SelectLocator)));
		    JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", Listvalue);
		    Listvalue.selectByVisibleText(strdata);
		}
					
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
