package PageObjects.mom;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.DriverUtil;

public class MOM_Add {
	
	WebDriver driver;
	NgWebDriver ngDriver;
	
	public MOM_Add(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	public MOM_Add(WebDriver driver)
	{
		this.driver=driver;
		
	}
	
	public  WebElement Textbox_subject() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//input[@id='mom_subject']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Textarea_Agenda() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//textarea[@id='agenda']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Link_Click_to_add_venue() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//a[contains(text(),\"Click to add venue\")]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Textbox_venue() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='mom_venue']")));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Link_Click_to_add_minutes_taken_by() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver
					.findElement(By.xpath("//a[contains(text(),\"Click to add minutes taken by\")]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Textbox_to_add_minutes_taken_by() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//input[@value='Select minutes taken by']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Link_Add_External_POC() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@href,\"#modal1\")]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_create_mom() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//button[contains(text(),'Create')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_discard() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(),'discard')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_upload_files() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//button[contains(text(),'Upload Files')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement SearchBox_Attendee() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//input[@value='Select attendee from the list']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_Add_discussion() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[@ng-click='addRow()']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	// enter the serial of discussion point.If 1st discussion then its 0 next will
	// be 1 and so on
	public  WebElement Textarea_discussion_point(String serial) {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//textarea[@id='mom_discussion_" + serial + "']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	// enter the serial of discussion point.If 1st discussion then its 0 next will
	// be 1 and so on
	public  WebElement discussion_point_date(String serial) {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//input[@name='mom_disscussion_date_" + serial + "']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement calender_prev_month() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[@data-handler='prev']/span"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	/*
	 * provide week and day of the month to select a day from calender .It can be 5
	 * weeks with 7days each.It depends on monthly calender
	 */
	public  WebElement calender_date(int week_of_the_month, int day) {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr["
							+ week_of_the_month + "]/td[" + day + "]"))));
			System.out.println(ele.getText());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement calender_next_month() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[@data-handler='next']/span"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	// select dropdown for actionee
	public  WebElement Select_actionee(String serial) {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			// ele=driver.findElement(By.xpath("//select[@name='mom_actionee_"+serial+"']"));
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
					By.xpath("//select[@name='mom_actionee_" + serial + "']/following-sibling::div/ul/li/input"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Meeting_date() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//input[@ng-model='mom.momMeetingDate']"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement calender_meetingdate_prev_month() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver
					.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[@title='Prev']/span"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	/*
	 * provide week and day of the month to select a day from calender .It can be 5
	 * weeks with 7days each.It depends on monthly calender
	 */
	public  WebElement calender_meetingdate_date(int week_of_the_month, int day) {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr["
							+ week_of_the_month + "]/td[" + day + "]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_Create() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.xpath("//button[@type='submit' and contains(.,'Create')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Button_Discard() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(),'discard')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Link_AddExternal_POC() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[contains(.,'EXTERNAL')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Link_Add_Collaborator() {
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions
					.elementToBeClickable(driver.findElement(By.xpath("//a[contains(.,'COLLAB')]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Discussion_delete(String discussion_point_serial) // serial starts with 2
	{
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
					By.xpath("(//div[@ng-repeat=\"row in meetingDetails\"])[" + discussion_point_serial + "]"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

	public  WebElement Attachment_delete(String attachment_serial) // serial starts with 1
	{
		ngDriver.waitForAngularRequestsToFinish();
		Wait wait = new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		WebElement ele = null;

		try {
			ele = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(
					By.xpath("(//li[@ng-repeat=\"(key,val) in momAttachments\"])[" + attachment_serial + "]/a"))));
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ele;
	}

}
