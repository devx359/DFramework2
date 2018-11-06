package UI_Functions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;




import Utilities.ORFile;
import Utilities.WebDriverSelector;

public class DatePicker {
	
	WebDriver driver;

	public DatePicker(WebDriver drivers) {
		driver = drivers;
	}
	
	
	public  void Datepicker(String strxpath,String strdata,WebDriver driver) throws ParseException 
	{
		
		try
		{
			String TstXpath=Utilities.ORFile.getlocator(strxpath);
			String YearXpath="//select[@class='picker__select--year browser-default']";
			String MonXpath="//select[@class='picker__select--month browser-default']";
			SimpleDateFormat FormatDate=new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat f1=new SimpleDateFormat("dd");
			SimpleDateFormat f2=new SimpleDateFormat("MMMM");
			SimpleDateFormat f3=new SimpleDateFormat("yyyy");
			
			//Date tdate=new Date();
			//Calendar cur_cal=Calendar.getInstance();
			//cur_cal.setTime(tdate);
			Date g_date=FormatDate.parse(strdata);
		    //Calendar g_cal=Calendar.getInstance();
			//g_cal.setTime(g_date);
			
			String g_Date=f1.format(g_date);
			String g_Month=f2.format(g_date);
			String g_Year=f3.format(g_date);
			
			System.out.println("g_Date"+g_Date);
			System.out.println("g_Month"+g_Month);
			System.out.println("g_Year"+g_Year);
			
			WebDriverWait wait = new WebDriverWait(driver, 60);// 1 minute 
			WebElement dt=driver.findElement(By.xpath(TstXpath));
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TstXpath)));
			Point pt=dt.getLocation();
			System.out.println(pt);
			//Click on Date field
			int X=pt.getX();
			int Y=pt.getY();
			System.out.println("    X"+X+"    Y"+Y);
			Actions ac = new Actions(driver);
			ac.moveToElement(dt,X,Y).click(dt).perform();
			//dt.click();
			//Select Year
			//New script add 
			WebElement sel_year=driver.findElement(By.xpath(YearXpath));
			Select Yearval=new Select(sel_year);
			Yearval.selectByVisibleText(g_Year);
					
			//Select month
			WebElement sel_mon=driver.findElement(By.xpath(MonXpath));
			Select Monthval=new Select(sel_mon);
			Monthval.selectByVisibleText(g_Month);
						
			//select Date
			String sel_date="//div[contains(text(),'"+g_Date+"')]";
			System.out.println(sel_date);
			WebElement date=driver.findElement(By.xpath(sel_date));
			date.click();
			driver.findElement(By.xpath("//button[contains(text(),'Close')]")).click();
						
		}
			
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}
	//Datapick function ends(When Month Year are not drop down 
	public  void datepick(String strxpath,String strdata) throws InterruptedException, ParseException, FileNotFoundException, IOException
	{
	
	int Cal_mon=0;
	String TstXpath=ORFile.getlocator(strxpath);
	SimpleDateFormat FormatDate=new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat f1=new SimpleDateFormat("dd");
	SimpleDateFormat f2=new SimpleDateFormat("MM");
	SimpleDateFormat f3=new SimpleDateFormat("yyyy");
	SimpleDateFormat f4=new SimpleDateFormat("MMMM");
	//String dt="6/2/2017";
	//dr.get("http://jqueryui.com/datepicker/");
	//driver.switchTo().frame(0);
	Thread.sleep(4000);
	WebElement txtbx=driver.findElement(By.xpath(TstXpath));
	txtbx.click();
	
	Date tdate=new Date();
	Calendar cur_cal=Calendar.getInstance();
	cur_cal.setTime(tdate);
	Date g_date=FormatDate.parse(strdata);
    Calendar g_cal=Calendar.getInstance();
	g_cal.setTime(g_date);
		
	//Change Given date in dd/mm/yyyy	
	String g_Date=f1.format(g_date);
	int g_Month=Integer.parseInt(f2.format(g_date));
	int g_Year=Integer.parseInt(f3.format(g_date));
	int cur_mon=Integer.parseInt(f2.format(tdate));
	
	String Cur_month=f4.format(tdate);
	System.out.println("Given Date"+g_Date);
	System.out.println("Given Month"+g_Month);
	System.out.println("Given Year"+g_Year);
	
	//Dynamic Xpath creation 
	String xpath1="//div[contains(text(),";
	String xpath2="'"+Cur_month+"')]";
	String xpath=xpath1+xpath2;
	WebElement dt_month=driver.findElement(By.xpath(xpath));
	WebElement dt_Year=driver.findElement(By.xpath("//div[@class='picker__year']"));
	
	//Calculate Date difference to count number of Hits
	
	int Next_diffYear = g_cal.get(Calendar.YEAR) - cur_cal.get(Calendar.YEAR);
	int Next_diffMonth = Next_diffYear * 12 + g_cal.get(Calendar.MONTH) - cur_cal.get(Calendar.MONTH);
	System.out.println("Next Total month"+Next_diffMonth);
	
	int Prev_diffYear=cur_cal.get(Calendar.YEAR)-g_cal.get(Calendar.YEAR);
	int Prev_diffMonth = Prev_diffYear * 12 + cur_cal.get(Calendar.MONTH) - g_cal.get(Calendar.MONTH);
	System.out.println("Previous Total month"+Prev_diffMonth);
	
	//Count Calendar month
	if(dt_month.isDisplayed())
	{
	Cal_mon=cur_mon;
	}
	int Cal_Year=Integer.parseInt(dt_Year.getText());
	
	String d_xpath1="//div[contains(text(),'";
	String d_xpath=d_xpath1+g_Date+"')]";
	
	System.out.println("Calendar month"+Cal_mon);
	System.out.println("Calendar year"+Cal_Year);
	
	//If Given month & Calendar month and Given year & Calendar year are same
	if((g_Year==Cal_Year)&&(g_Month==Cal_mon))
	{
		System.out.println("If part");
		driver.findElement(By.xpath("//tr")).click();
		//driver.findElement(By.xpath("//tr")).click();
		WebElement dates=driver.findElement(By.xpath(d_xpath));
		dates.click();
		/*for(WebElement dateval: dates)
		{
			System.out.println(dateval.getText());
			String date1=dateval.getText();
			String date=get_Date(date1);
			System.out.println(date);
			if(g_Date.equals(date))
			{
				dateval.click();
				break;
			}
		
		}*/
	}
	//If Given month is greater than Calendar month and Given year is greater than Calendar year are same
		else if(((g_Year>Cal_Year)&&(g_Month>Cal_mon))||((g_Year>Cal_Year)&&(g_Month<Cal_mon))||(g_Year==Cal_Year)&&(g_Month>Cal_mon))
				{
			         System.out.println("else if part");
				Thread.sleep(2000);
			 for(int i=1;i<=Next_diffMonth;i++)
			          {
				        System.out.println("Value of I:"+i);
				        
				        WebElement dt_next=driver.findElement(By.xpath("//div[@title='Next month']"));
					    dt_next.click();
			          }  
			 driver.findElement(By.xpath("//tr")).click();
	        //List<WebElement> dates=driver.findElements(By.tagName("a"));
			WebElement dates=driver.findElement(By.xpath(d_xpath));
			dates.click();
		   /* for(WebElement dateval: dates)
			  {
		    	System.out.println(dateval.getText());
				String date1=dateval.getText();
				String date=get_Date(date1);
		    	
		    	if(g_Date.equals(date))
				{
					System.out.println("Match Date is "+date);
					dateval.click();
					break;
				}
			
			  }*/
			
		 }
	//If Given month is less than Calendar month and Given year is less than Calendar year are same
		else if((g_Year<Cal_Year)||(g_Month<Cal_mon))
		{
			System.out.println("else part");
			for(int i=1;i<=Prev_diffMonth;i++)
			    {
				 System.out.println("Else Value of I:"+i);
				 WebElement dt_next=driver.findElement(By.xpath("//div[@title='Previous month']"));
		         dt_next.click();	
		        }
			driver.findElement(By.xpath("//tr")).click();
			WebElement dates=driver.findElement(By.xpath(d_xpath));
			dates.click();
			
			/*for(WebElement dateval: dates)
			{
				String date1=dateval.getText();
				String date=get_Date(date1);
				if(g_Date.equals(date))
				//if(g_Date.equals(date1.getText()))
				{
					System.out.println(dateval.getText());
					dateval.click();
					break;
				}	
	        } */ 		
		}
			
	driver.findElement(By.xpath("//button[contains(text(),'Close')]")).click();	
		}	//Datapick function ends(When Month Year are not drop down 
	
	public String get_Date(String date)
	
	{
	   if(!date.equalsIgnoreCase("Prev")&&!date.equalsIgnoreCase("Next"))
	   {
		if(Integer.parseInt(date)<10)
		{
			date="0"+date;
			
		}
	   }		
		return date;
	}
}
