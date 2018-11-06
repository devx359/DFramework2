package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

public class IMPP_Login {
	WebDriver driver;
	NgWebDriver ngDriver;
	
	 WebDriverWait wait ;
	 public IMPP_Login(WebDriver driver, NgWebDriver ngDriver)
		{
			this.driver=driver;
			this.ngDriver=ngDriver;
		}
		public IMPP_Login(WebDriver driver)
		{
			this.driver=driver;
			
		}
		

	
	public  WebElement TextBox_Platform_id()
	{
		wait = new WebDriverWait(driver, 10);
		WebElement ele=null;
		try {
			ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='icon_platform_id']")));

			// ele=driver.findElement(By.xpath("//input[@id='icon_platform_id']"));
			
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}
	
	public  WebElement TextBox_Password()
	{
		wait = new WebDriverWait(driver, 10);
		WebElement ele=null;
		try {
			
			ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='icon_password']")));

		//	 ele=driver.findElement(By.xpath("//input[@id='icon_password']"));
			
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}
	
	public  WebElement Button_login()
	{
		
		WebElement ele=null;
		try {
			 ele=driver.findElement(By.xpath("//button[@id='login']"));
			
		} catch (Exception e) {
		
			System.out.println("WebElement issues"+e);
		}
		return ele;
	}

}
