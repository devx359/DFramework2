package uiTestCases;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.paulhammant.ngwebdriver.NgWebDriver;

import Keywords.impp;
import PageObjects.Home;
import PageObjects.IMPP_Login;
import PageObjects.mom.MOM;
import PageObjects.mom.MOM_Add;
import Utilities.DriverUtil;
import Utilities.ExtentManager;
import Utilities.Log;

public class Mom_tc_2 {
	public WebDriver driver;
	public NgWebDriver ngDriver;
	MOM_Add mom ;
	Home home;
	impp imppob;
	IMPP_Login impp_login_obj;
	MOM momobj;
	Log log;
	DesiredCapabilities cap;
	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	
	//To initialize log4j below code is required
	static{
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
	        System.setProperty("current.date.time", dateFormat.format(new Date()));
	    }
	@BeforeClass
	public void setup()
	{
		//Driver setup
		DriverUtil obj = new DriverUtil();
		driver=obj.GridDriverSetup("chrome");
		//driver=obj.DriverSetup("chrome");
		ngDriver=obj.getngDriver();
		
		//Extent Reports
		ExtentManagerObj= new ExtentManager(); 
		 reports = ExtentManagerObj.GetExtent("Regression MOM TC_2");
		
		
		//start Log4j
		log= new Log();
		log.startLogForThisCase("Angular Regression suite mom_tc_2 ");
		//Initializing Page Objects
		mom = new MOM_Add(driver,ngDriver);
		home= new Home(driver,ngDriver);
		imppob= new impp(driver,ngDriver);
		impp_login_obj=new IMPP_Login(driver,ngDriver);
		momobj= new MOM(driver,ngDriver);
	}
	
	@Test(priority=0)
	public void login()
	{
		test = reports.createTest("Login");
		//System.out.println("inside login--- @ThreadId"+Thread.currentThread().getId());
		imppob.login("robert@imerit.net","I0001I0001",impp_login_obj);
		System.out.println("Logged in successfully");
		log.info("Logged in successfully");
		test.pass("Logged in Successfully");
		
		
	}	
	@Test(priority=1)
	public void E1()
	{
		test = reports.createTest("E1");
		System.out.println("inside E1--- @ThreadId"+Thread.currentThread().getId());
		imppob.project_click("NM Project");
		test.info("Clicked on NM Project");
		log.info("Clicked on NM Project");
		test.pass("In E1");
	}
	@Test(priority=2)
	public void MOM()
	{
		test = reports.createTest("MOM");
		home.MOM_Tab().click();
		test.info("Inside Mom Tab");
		log.info("Inside Mom Tab");
		test.pass("Passed");
	}
	@Test(priority=3)
	public void Add_MOM()
	{
		System.out.println("inside login--- @ThreadId"+Thread.currentThread().getId());
		momobj.Add_MOM().click();
		mom.Textbox_subject().sendKeys("Important Meeting");
		
		
		mom.Meeting_date().click();
		mom.calender_meetingdate_date(2, 3).click();
	
	//	log.error("chope chul");
		
		mom.Link_Click_to_add_venue().click();
		mom.Textbox_venue().sendKeys("Saltlake");
		
		mom.Link_Click_to_add_minutes_taken_by().click();
		mom.Textbox_to_add_minutes_taken_by().sendKeys("Jeff Mills");
		mom.Textbox_to_add_minutes_taken_by().sendKeys(Keys.RETURN);
		//mom.SearchBox_Attendee().click();
		mom.SearchBox_Attendee().sendKeys("Jai Natarajan");
		mom.SearchBox_Attendee().sendKeys(Keys.RETURN);
		mom.Textarea_Agenda().sendKeys("aGENDA 1 ,,,,JHJKHJHLJ");
		//mom.Link_Add_Collaborator().click();
		
		mom.Textarea_discussion_point("0").sendKeys("Newddddddddfdffffffffffffffion hgjhgk");
		
		//calender
		mom.discussion_point_date("0").click();
		mom.calender_next_month().click();
		mom.calender_prev_month().click();
		mom.calender_date(3,5).click();
		
		mom.Select_actionee("0").click();
		mom.Select_actionee("0").sendKeys("Lauren Robinson");
		mom.Select_actionee("0").sendKeys(Keys.RETURN);
		/*Select sel = new Select(mom.Select_actionee("0"));
		sel.selectByVisibleText("Lauren Robinson");*/
		
/*		mom.Button_Add_discussion().click();
		mom.Textarea_discussion_point("1").sendKeys("New ds=iscusion hgjhgk");
		
		mom.discussion_point_date("1").click();		
		mom.calender_date(3,4).click();
		mom.Select_actionee("1").click();
		mom.Select_actionee("1").sendKeys("Lauren Robinson");
		mom.Select_actionee("1").sendKeys(Keys.RETURN);
		
		mom.Button_Add_discussion().click();
		mom.Textarea_discussion_point("2").sendKeys("blahblahljglggl");
		
		mom.discussion_point_date("2").click();		
		mom.calender_date(3,6).click();
		mom.Select_actionee("2").click();
		mom.Select_actionee("2").sendKeys("Rana Saha");
		mom.Select_actionee("2").sendKeys(Keys.RETURN);
		//Home.Link_iMPP().click();
		mom.Button_upload_files().click();
		try {
			Runtime.getRuntime().exec("./Fileupload.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mom.Button_upload_files().click();
		try {
			Runtime.getRuntime().exec("./Fileupload.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mom.Discussion_delete("2").click();
		mom.Attachment_delete("2").click();
*/	//	mom.Button_Create().click();
		
		test.pass("MOM page filled successfully");
		
	}
	@AfterClass
	public void shutdown()
	{
		reports.flush();
		imppob.logout(home);
		driver.close();
		//log.endLoggForThisCase();
	}

}
