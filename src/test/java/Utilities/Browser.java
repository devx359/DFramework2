package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {

	public static WebDriver driver;
	public static WebDriver getDriver(String browser,Log log)
	{
		if(browser.equalsIgnoreCase("chrome"))
		{
			try
			{
				log.debug("Loading Chrome browser driver ");
				System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
				driver=new ChromeDriver();
				log.debug("Loaded Chrome browser driver ");
				driver.manage().window().maximize();
				return driver;
			}
			catch(Exception e)
			{
				System.out.println("Faile to initialize Chrome Driver "+e);
				return null;
			}
			
			
			
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			try{
			System.setProperty("webdriver.gecko.driver", "F:\\softwares\\selenium\\geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("Loaded FireFox browser driver ");
			driver.manage().window().maximize();
			return driver ;
			}
			catch(Exception e)
			{
				System.out.println("Faile to initialize FireFox Driver "+e);
				return null;
			}
		}
		else
		{
			log.error("Failed to Initiate Driver : Unsupported browser");
			return null;
		}
		
			
		
	}
}
