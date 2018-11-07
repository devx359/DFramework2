package UI_Functions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.Locator;
import Utilities.ORFile;
import Utilities.WebDriverSelector;

public class Link {

	WebDriver driver;
	NgWebDriver ngDriver;
	
	public Link(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}

	public void navigateToURL(String actualURL) {
		try {

			driver.get(actualURL);

		} catch (TimeoutException e) {
			System.out.println("Page: " + actualURL + " did not load within 45 seconds!"+e);

		} catch (Exception e) {
			System.out.println("Unable to open " + actualURL+ e);
			
		}
	}

	public void Click(String strxpath) {
		try {
			ngDriver.waitForAngularRequestsToFinish();
			
			By LinkLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(LinkLocator));
			wait.until(ExpectedConditions.elementToBeClickable(LinkLocator)).click();
		} catch (Exception e) {
			System.out.println("Unable to click on link: " + strxpath +" "+e);
			
			Assert.fail();
		}

	}

	public void JSClick(String strxpath) {
		try {
			//ngDriver.waitForAngularRequestsToFinish();
			
			By LinkLocator = Locator.getWebElement(strxpath);
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement elementToClick=wait.until(ExpectedConditions.visibilityOfElementLocated(LinkLocator));
			//System.out.println(strxpath+elementToClick.isDisplayed());
			//System.out.println(strxpath+elementToClick.isEnabled());
		
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			System.out.println("Clicked on "+strxpath);
	
		} catch (Exception e) {
			System.out.println("Unable to click on link " + strxpath +e);
			e.printStackTrace();
			Assert.fail();
			
		}

	}

	public void XpathClick(String strxpath) {
		try {
			
			ngDriver.waitForAngularRequestsToFinish();

			WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.click();
		} catch (Exception e) {
			System.out.println("Unable to click on link" + strxpath);
			//Assert.fail();
		}

	}

	public void XpathDataClick(String strxpath1,String dynamicdata) {
		try {
			String strXpath1 = ORFile.getlocator(strxpath1).trim();
			String locatorValue = strXpath1.split(";")[1];
			
			String strxpath = locatorValue + dynamicdata + "')]";
			System.out.println(strxpath);
			WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.click();
		} catch (Exception e) {
			System.out.println("Unable to click on link" + strxpath1);
			//Assert.fail();
		}

	}
	/*Dynamic xpath 
	 * Useful only when you need to pass 1 dynamic data inside a xpath 
	Ex: xpath1 +'pass dynamic value'+xpath2 */
	public void DataClick(String strxpaths1,String strxpaths2, String dynamicdata) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			
			String strXpath1 = ORFile.getlocator(strxpaths1).trim();
			String locatorValue = strXpath1.split(";")[1];
			String strXpath2 = ORFile.getlocator(strxpaths2).trim();
			String locatorValue2 = strXpath2.split(";")[1];
			String strxpath = locatorValue + dynamicdata + locatorValue2;
			//System.out.println(strxpath);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(strxpath))).click();
			/*WebElement elementToClick = driver.findElement(By.xpath(strxpath));

			elementToClick.click();*/
		} catch (Exception e) {
			System.out.println("Unable to click on link: " + strxpaths1+strxpaths2+" "+e);
			Assert.fail();
		}

	}
	
	public void DataClick(String strxpath1, String dynamicdata) {
		try {

			String strXpath1 = ORFile.getlocator(strxpath1).trim();
			String locatorValue = strXpath1.split(";")[1];
			
			String strxpath = locatorValue + dynamicdata + ")]";
			System.out.println(strxpath);
			WebElement elementToClick = driver.findElement(By.xpath(strxpath));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.click();
		} catch (Exception e) {
			System.out.println("Unable to click on link" + strxpath1);
			//Assert.fail();
		}

	}

	public void isPresent(String strxpath) {
		try {
			By LinkLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(LinkLocator));
		} catch (Exception e) {
			System.out.println("Link is not present " + strxpath);
		}

	}

	public void isEnable(String strxpath) {
		try {
			By LinkLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(LinkLocator)).isEnabled();
		} catch (Exception e) {
			System.out.println("Link is not enabled");
		}

	}
}
