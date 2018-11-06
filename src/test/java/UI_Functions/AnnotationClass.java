package UI_Functions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.Locator;
import Utilities.WebDriverSelector;
import junit.framework.Assert;

public class AnnotationClass {

	WebDriver driver;
	NgWebDriver ngDriver;
	
	public AnnotationClass(WebDriver driver, NgWebDriver ngDriver)
	{
		this.driver=driver;
		this.ngDriver=ngDriver;
	}
	
	public void annotation_poly_rndm(WebElement AnnotatedPath) {
		try {
			
			Actions act = new Actions(driver);				
			{
				
                    Point p=AnnotatedPath.getLocation();
					int x=p.getX();
					int y=p.getY();
					System.out.println("X"+x+"Y"+y);
					act.moveToElement(AnnotatedPath,x+80, y+100).click().build().perform();
					act.moveToElement(AnnotatedPath,x+90, y+80).click().build().perform();
					act.moveToElement(AnnotatedPath,x+100, y+180).click().build().perform();
					System.out.println("Annotation done");
					
					Thread.sleep(10000);
			}
				//	act.moveToElement(AnnotatedPath,150,250).click().build().perform();
					//act.moveToElement(AnnotatedPath,350,360).click().build().perform();
					//act.moveToElement(AnnotatedPath,350,360).click().build().perform();
				/*				
				
				for(int i=200,j=200;i>150 && j<250;i=i-5,j=j+5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();	
					act.moveToElement(AnnotatedPath,i-5,j).click().build().perform();	
				}
				
				for(int i=150,j=250;i>100 && j>200;i=i-5,j=j-5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();
					act.moveToElement(AnnotatedPath,i-1,j).click().build().perform();
				}
				
				for(int i=100,j=200;i<150 && j>150;i=i+5,j=j-5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();
					act.moveToElement(AnnotatedPath,i,j-1).click().build().perform();
				}
				
*/
		} catch (Exception e) {
			System.out.println("Unable to annotate"+e);
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void annotation_create(WebElement AnnotatedPath) {
		try {
				for(int i=150;i<200;i=i+5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,i).click().build().perform();
					act.moveToElement(AnnotatedPath,i+1,i).click().build().perform();
					
				}
				
				
				for(int i=200,j=200;i>150 && j<250;i=i-5,j=j+5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();	
					act.moveToElement(AnnotatedPath,i-5,j).click().build().perform();	
				}
				
				for(int i=150,j=250;i>100 && j>200;i=i-5,j=j-5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();
					act.moveToElement(AnnotatedPath,i-1,j).click().build().perform();
				}
				
				for(int i=100,j=200;i<150 && j>150;i=i+5,j=j-5) {
					Actions act = new Actions(driver);
					act.moveToElement(AnnotatedPath,i,j).click().build().perform();
					act.moveToElement(AnnotatedPath,i,j-1).click().build().perform();
				}
				

		} catch (Exception e) {
			System.out.println("Unable to annotate");
		}
	}
	
public void JS_annotation(WebElement AnnotatedPath)
	
	{
		int i;
		try {
		// Point ele=AnnotatedPath.getLocation();
			//int x=ele.getX();
			//int y=ele.getY();
			//System.out.println("X"+x+"Y"+y);
			for ( i=100;i<250;i=i+50)
			{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		   js.executeScript("window.scrollTo(0, "+(AnnotatedPath.getLocation().y+i)+")");   
		   AnnotatedPath.click();
			}
		} catch (Exception e) {
			System.out.println("Unable to annotate");
		}
	}
}
