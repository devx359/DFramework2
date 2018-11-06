package Tasking_UI;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_003_dashboard extends UIBaseClass {
	
	@Test(priority = 1, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void step1(Hashtable<String, String> tdata) {
		try {
			defaultSteps(tdata);
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
			button.GoogleLogin();
			link.navigateToURL(path.weburl);
			button.Click("logintoImpp");
			test.pass("Logged in impp");

		} catch (Exception e) {

			e.printStackTrace();
			test.fail("Unable to login to Impp");
			// Assert.fail();

		}
	}

	@Test(priority = 4, dependsOnMethods = { "step1" })
	public void job_list(ITestContext context) throws InterruptedException {
		int uploadedUI, tasksUI, uploadedDB, tasksDB;
		String jobcode;
		try {
			System.out.println("inside job tab1");
			test = reports.createTest("job_list");
			context.setAttribute("testobj", test);

			link.Click("hambugMenu");
			link.Click("Project_arrow");
			link.Click("viewProject");
			Thread.sleep(5000);
			textbox.SetText("searchProject", sub_project_code);
			link.DataClick("projectName1", "projectName2", sub_project_code);
			link.Click("Link_jobTab");
			Thread.sleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			List<WebElement> ele = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[@ng-if='jobList']/ul/li[@ng-repeat='(key,j) in jobList']")));

			Boolean flag = true;
			for (int i = 1; i <= ele.size(); i++) {
				String text1 = driver.findElement(By.xpath("//div[@ng-if='jobList']/ul/li[" + i + "]/div[5]/span[2]"))
						.getText();
				uploadedUI = Integer.parseInt(text1);
				jobcode = driver.findElement(By.xpath("//div[@ng-if='jobList']/ul/li[" + i + "]/div[1]/span[2]"))
						.getText();
	
			}
			// Thread.sleep(5000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
