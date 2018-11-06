package UI_Functions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import Utilities.WebDriverSelector;

public class RadioButton {
	WebDriverSelector driver;
	
	 public void ClickRadio(String strdata)
	 {
			
		 try
		 {
			 String xpath1="//label[@for='";
			 String xpath=xpath1+strdata+"']";
			 WebElement OradioButton=driver.findElement(By.xpath(xpath));
			 if(OradioButton.isEnabled())
			 {
				 OradioButton.click();
			 }
		 }catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
	 }
}
