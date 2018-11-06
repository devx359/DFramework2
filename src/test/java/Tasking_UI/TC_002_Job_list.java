package Tasking_UI;

import java.io.IOException;
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

public class TC_002_Job_list extends UIBaseClass {
	String created_job_name="testjob";

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
			// Thread.sleep(5000);
			// waitForLoad(driver);

			link.navigateToURL(path.weburl);
			// Thread.sleep(5000);
			// waitForLoad(driver);
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
			// link.navigateToURL(path.impp_home);
			test = reports.createTest("job_list");
			context.setAttribute("testobj", test);

			link.Click("hambugMenu");
			link.Click("Project_arrow");
			link.Click("viewProject");
			Thread.sleep(5000);
			textbox.SetText("searchProject", sub_project_code);

			// textbox.enterKeyAfterSendKeys("searchProject");
			//otherFunctions.presenceOfElement("NextButton");
			link.DataClick("projectName1", "projectName2", sub_project_code);
			link.Click("Link_jobTab");
			Thread.sleep(5000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			List<WebElement> ele = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//div[@ng-if='jobList']/ul/li[@ng-repeat='(key,j) in jobList']")));
			// List<WebElement> ele =
			// driver.findElements(By.xpath("//div[@ng-if='jobList']/ul/li[@ng-repeat='(key,j)
			// in jobList']"));
			Boolean flag = true;
			for (int i = 1; i <= ele.size(); i++) {
				String text1 = driver.findElement(By.xpath("//div[@ng-if='jobList']/ul/li[" + i + "]/div[5]/span[2]"))
						.getText();
				uploadedUI = Integer.parseInt(text1);
				jobcode = driver.findElement(By.xpath("//div[@ng-if='jobList']/ul/li[" + i + "]/div[1]/span[2]"))
						.getText();

				HashMap res = dbcon.engagement_Uploaded_task_count("EC-Hyk08EM7m", jobcode);
				uploadedDB = Integer.parseInt(res.get("count").toString());
				if (uploadedUI != uploadedDB)

				{
					test.fail("Uploaded Task count failed" + "jobcode: " + jobcode + " uploadedUI: " + uploadedUI
							+ "DB: " + uploadedDB);

				}

				HashMap res2 = dbcon.engagement_Total_task_count("EC-Hyk08EM7m", jobcode);
				tasksDB = Integer.parseInt(res2.get("count").toString());

				String text2 = driver.findElement(By.xpath("//div[@ng-if='jobList']/ul/li[" + i + "]/div[6]/span[2]"))
						.getText();
				tasksUI = Integer.parseInt(text2);

				if (tasksUI != tasksDB)

				{
					test.fail(
							"Total Task count failed" + "jobcode: " + jobcode + " UI: " + tasksUI + " DB: " + tasksDB);
				}
				System.out.println("jobcode: " + jobcode + " uploadedUI: " + uploadedUI + "DB: " + uploadedDB
						+ " tasksUI: " + tasksUI + " DB: " + tasksDB);
				test.info("jobcode: " + jobcode + " uploadedUI: " + uploadedUI + "DB: " + uploadedDB + " tasksUI: "
						+ tasksUI + " DB: " + tasksDB);
			}
			// Thread.sleep(5000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(priority = 5, dependsOnMethods = { "step1" })
	public void add_job(ITestContext context) throws InterruptedException {
		link.Click("addJob");
		textbox.SetText("textBoxJobTitle", created_job_name);
		dropdown.SelectValue("flowSelectDropDown", "simple flow 1op 1qc");
		button.Click("buttonSaveJob");

		WebDriverWait wait = new WebDriverWait(driver, 30);
		Boolean res = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+created_job_name+"')]")))
				.isDisplayed();

		if (res == true) {
			System.out.println("Job created");
		} else {
			System.out.println("Job not created");
		}

	}

	@Test(priority = 6, dependsOnMethods = { "add_job" })
	public void archive_job(ITestContext context) throws InterruptedException {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//span[contains(.,'"+created_job_name+"')]/../following-sibling::div[5]/a[3]/i")));
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", ele);
			
			
			//otherFunctions.alertAccept();
			//button.Click("ButtonOk");
			link.JSClick("ButtonOk");
			
			Boolean res = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+created_job_name+"')]")))
					.isDisplayed();

			if (res == false) {
				System.out.println("Job not archived");
			} else {
				System.out.println("Job  archived");
			} 
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}
}
