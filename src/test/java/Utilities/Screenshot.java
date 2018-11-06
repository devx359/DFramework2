/**
 * 
 */
package Utilities;

import java.io.File;
import java.sql.Timestamp;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author debo
 *
 */
public class Screenshot {
	public  String takeScreenShot(WebDriver driver,String filename)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//String filepath="./Screenshot/Test/"+filename+"_"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".png";
		String filepath="D:\\testdata\\Screenshots\\"+filename+"_"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".png";
		//System.out.println("screenshot: "+filepath);
		try {
			TakesScreenshot ts = (TakesScreenshot)driver;
			File source=ts.getScreenshotAs(OutputType.FILE);
			File destfile = new File(filepath);
			FileUtils.copyFile(source, destfile);
		//	System.out.println("screenshot taken");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return filepath.replace("/","\\\\");
	}


}
