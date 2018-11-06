
/*
 * This file contains all repetitive actions done in IMPP portal like login logout etc
 * Author: Debapriyo Haldar
 * Date:26-Jan-2018
 * 
 * */
package Keywords;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import PageObjects.Home;
import PageObjects.IMPP_Login;
import Utilities.DriverUtil;


public class impp {
	
	WebDriver driver;
	NgWebDriver ngDriver;
	
	public impp(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	public impp(WebDriver driver)
	{
		this.driver=driver;
		
	}
	

	public void login(String username,String password,IMPP_Login IMPP_Login_obj)
	{
		driver.get("https://itest.imerit.net/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
 		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		
		IMPP_Login_obj.TextBox_Platform_id().sendKeys(username);
		IMPP_Login_obj.TextBox_Password().sendKeys(password);
		IMPP_Login_obj.Button_login().click();
	}
	
	public  void logout(Home homeobj)
	{
		homeobj.Link_logout_dropdown_menu().click();
		homeobj.Link_logout().click();
	}
	
	public  void project_click(String tooltip)
	{
		ngDriver.waitForAngularRequestsToFinish();
		driver.findElement(By.xpath("//span[@data-tooltip='"+tooltip+"']")).click();
	}
	
	public  void project_click2(String subprojectCode)
	{
		ngDriver.waitForAngularRequestsToFinish();
		driver.findElement(By.xpath("//span[contains(text(),'"+subprojectCode+"')]/../preceding-sibling::td[2]/div/span/a")).click();
	}
	
	
}
