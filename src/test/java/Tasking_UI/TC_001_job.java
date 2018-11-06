package Tasking_UI;

import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import Utilities.Locator;
import Utilities.RetryCountIfFailed;

public class TC_001_job extends UIBaseClass {

	@Test(priority = 1, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void step1(Hashtable<String, String> tdata) {
		try {
			defaultSteps(tdata);
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 2, dependsOnMethods = { "step1" })
	//@RetryCountIfFailed(4)
	public void impp_login(ITestContext context) {
		try {
			test = reports.createTest("TC_1");
			context.setAttribute("testobj", test);

			button.GoogleLogin();
			Thread.sleep(5000);
			link.navigateToURL(path.weburl);
			Thread.sleep(5000);
			button.Click("logintoImpp");

			test.pass("Logged in impp");

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Unable to login to Impp");
			// Assert.fail();

		}
	}

	@Test(priority = 3, dependsOnMethods = { "step1" })
	public void create_flow(ITestContext context) {
		try {
			test = reports.createTest("TC_2");
			context.setAttribute("testobj", test);

			link.Click("hambugMenu");
			link.Click("Project_arrow");
			link.Click("viewProject");
			Thread.sleep(2000);
			textbox.SetText("searchProject", sub_project_code);
			// textbox.enterKeyAfterSendKeys("searchProject");

			link.DataClick("projectName1", "projectName2", sub_project_code);
			link.Click("Link_jobTab");
			link.Click("Link_flowTab");
			link.Click("Link_createNewFlow");
			Thread.sleep(3000);
			link.Click("Button_createFlow");
			Thread.sleep(3000);

			// Create Simple Flow
			createSimpleFlow();
			Thread.sleep(25000);
			try {
			Alert myalert=  driver.switchTo().alert();
			myalert.accept();
			}
			catch(Exception e)
			{
				System.out.println("no popup found");
			}

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Failed to enter job tab");

		}

	}

	@Test(priority=4, dependsOnMethods = { "step1" })
	public void job_tab(ITestContext context) {
		System.out.println("inside job tab1");
		link.navigateToURL(path.impp_home);

	}
	
	@Test(priority=5, dependsOnMethods = { "step1" })
	public void job_tab2(ITestContext context) {
		System.out.println("inside job tab2");
		link.navigateToURL(path.impp_home);

	}

}
