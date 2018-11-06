package uiTestCases;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.testng.annotations.Test;

public class WiniumDemo {
	
	@Test
	public void fun() throws MalformedURLException
	{
		DesktopOptions option = new DesktopOptions();
		option.setApplicationPath("C:\\Windows\\System32\\calc.exe");
		
		WiniumDriver windriver= new WiniumDriver(new URL("http://localhost:9999"), option);
		windriver.findElementById("num1Button").click();
		windriver.findElementById("plusButton").click();
		windriver.findElementById("num5Button").click();
		windriver.findElementById("equalButton").click();
		String output=windriver.findElementById("CalculatorResults").getAttribute("Name");
		System.out.println(output);
	}
	

}
