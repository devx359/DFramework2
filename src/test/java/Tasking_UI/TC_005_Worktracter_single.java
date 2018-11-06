package Tasking_UI;

import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import Utilities.RetryCountIfFailed;

public class TC_005_Worktracter_single extends UIBaseClass {
	Screen scrn;

	@Test(priority = 1, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void step1(Hashtable<String, String> tdata) {
		try {
			defaultSteps(tdata);
			scrn = new Screen();//Sikuli Obj
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	@Test(priority = 2, dependsOnMethods = { "step1" })
	// @RetryCountIfFailed(4)
	public void impp_login(ITestContext context) {
		try {
			test = reports.createTest("TC_1");
			context.setAttribute("testobj", test);
			//button.GoogleLogin();
			driver.get("https://itest.imerit.net/");
			textbox.SetText("login_userID",username);
			textbox.SetText("login_password",password);
			button.Click("login_button");
			
		//	link.navigateToURL(path.weburl);
			//button.Click("logintoImpp");
			test.pass("Logged in impp");

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Unable to login to Impp");
			// Assert.fail();

		}
	}

	@Test(priority = 3, dependsOnMethods = { "step1" })
	@RetryCountIfFailed(4)
	public void TC_2(ITestContext context) {
		int j = 0,nooftask=3;
		try {
			
			test = reports.createTest("TC_2");
			context.setAttribute("testobj", test);

			link.Click("hambugMenu");
			link.Click("Contributor");
			link.Click("work_tracker");
			Thread.sleep(2000);
			link.XpathDataClick("itest_project", project);
			Thread.sleep(2000);
			link.XpathDataClick("itest_flow", FlowName);
			Thread.sleep(2000);
			dropdown.SelectValue("itest_job", JobName);
			link.Click("op_start");
			Thread.sleep(2000);
			otherFunctions.switchFrame("tool_area");
			
			while(j<nooftask)
			{
			//scrn.click(running);
			//link.JSClick("tool_polygn_Btn");
			WebDriverWait waits = new WebDriverWait(driver, 30);
			waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[name()='svg']//*[name()='image'][@style='display: block;']")));

			
			Pattern running = new Pattern("./image/poly2");
			Match p = scrn.wait(running,20);
			p.click();

			for (int i = 1; i < 10; i++) {
				WebDriverWait waits2 = new WebDriverWait(driver, 30);
				WebElement annotations2 = waits2.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[name()='svg']//*[name()='image'][@style='display: block;']")));

				annotation.annotation_poly_rndm(annotations2);
				keyboardfunct.keyPressed("annotatedLoc", "E");
				Thread.sleep(15000);
				link.JSClick("Tool_nextButton");
				//Thread.sleep(3000);
				// driver.switchTo().defaultContent();
			}
			Thread.sleep(8000);
			link.JSClick("tool_submit");
			link.JSClick("toos_submit_ok");
			}
			test.pass("Successfully completed task");

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Failed to enter tasking page");

		}
	}

}
