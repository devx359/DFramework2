package UI_Functions;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.Locator;
import Utilities.ORFile;
import Utilities.WebDriverSelector;

public class Button {

	WebDriver driver;
	NgWebDriver ngDriver;

	public Button(WebDriver drivers, NgWebDriver ngDriver) {
		driver = drivers;
		this.ngDriver = ngDriver;
	}

	WebDriverSelector webdriver = new WebDriverSelector();

	public void Click(String strxpath)  {

		try {
			ngDriver.waitForAngularRequestsToFinish();
			By buttonLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(buttonLocator)).click();

		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}
	
	public void jsUploadFile(String strxpath,String filepath)  {

		try {
			By LinkLocator = Locator.getWebElement(strxpath);
			WebElement elementToClick = driver.findElement(LinkLocator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", elementToClick);
			elementToClick.sendKeys(filepath);

		} catch (Exception e) {
			e.printStackTrace();
		
		}

	}

	public boolean isPresent(String strxpath) {

		try {

			By buttonLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator));

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public void isEnable(String strxpath) {

		try {

			By buttonLocator = Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.presenceOfElementLocated(buttonLocator)).isEnabled();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void GoogleLogin() throws IOException, InterruptedException {
	//	System.out.println("Hello");
		try {
			driver.get("https://www.google.com");

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.ignoring(ElementNotVisibleException.class);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='gb_70']"))).click();		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("identifier"))).sendKeys("namita.singh@imerit.net");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Next')]"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("na@123456");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Next')]"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='lst-ib']"))).sendKeys("sound check");
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
			Assert.fail();
		}

	}

	public void newWindowClick() throws IOException, InterruptedException {

		try {
			// By WindowLocator=Locator.getWebElement(strxpath);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='g-overlay']")))).click();
			String parentWindow = driver.getWindowHandle();
			// System.out.println("Parent Window ID is : " + parentWindow);

			Set<String> allWindow = driver.getWindowHandles();

			// int count = allWindow.size();
			// System.out.println("Total Window : " + count);

			for (String child : allWindow) {
				if (!parentWindow.equalsIgnoreCase(child)) {
					driver.switchTo().window(child);
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					driver.findElement(By.name("identifier")).sendKeys("namita.singh@imerit.net");
					driver.findElement(By.xpath("//span[contains(.,'Next')]")).click();
					driver.findElement(By.name("password")).sendKeys("na@123456");
					// WebDriverWait wait = new WebDriverWait(driver, 10);
					WebElement element = wait
							.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'Next')]")));
					element.click();

					/*
					 * driver.findElement(By.
					 * xpath("//div[@class='aCsJod oJeWuf']//../input[@type='email']")).sendKeys(
					 * "namita.singh@imerit.net");
					 * driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
					 * wait.until(ExpectedConditions.presenceOfElementLocated((By.
					 * xpath("//input[@aria-label='Enter your password']")))).sendKeys("na@123456");
					 * 
					 * driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
					 */
				}
				driver.switchTo().window(parentWindow);
				// driver.findElement(By.xpath("//input[contains(text(),'Sign In']")).click();
				driver.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
