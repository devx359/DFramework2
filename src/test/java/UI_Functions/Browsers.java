package UI_Functions;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import Utilities.WebDriverSelector;

public class Browsers {

	
	WebDriver driver;

	public Browsers(WebDriver drivers) {
		driver = drivers;
	}


	public void navigateToURL(String actualURL) {
		try {

			driver.get(actualURL);

		} catch (TimeoutException e) {
			System.out.println("Page: " + actualURL + " did not load within 45 seconds!");

		} catch (Exception e) {
			System.out.println("Unable to open " + actualURL);
			Assert.fail();
		}
	}
	
	public void navigateTolocation(String actualURL) {
		try {

			driver.get(actualURL);

		} catch (TimeoutException e) {
			System.out.println("Page: " + actualURL + " did not load within 45 seconds!");

		} catch (Exception e) {
			System.out.println("Unable to open " + actualURL);
			Assert.fail();
		}
	}
}
