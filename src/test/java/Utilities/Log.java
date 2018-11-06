/**
 * 
 */
package Utilities;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * @author debo
 *
 */
public class Log {
	
	public static Logger log = Logger.getLogger(Log.class.getName());
	
	public  void startLogForThisCase(String testCaseName)
	{
		BasicConfigurator.configure();
	    log.debug("Hello World!");
		info("****************************************************************************************");
	    info("****************************************************************************************");
	    info("$$$$$$$$$$$$$$$$$$$$$      Test Case: "+testCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
	    info("****************************************************************************************");
	    info("****************************************************************************************");
	}
	
	public  void endLoggForThisCase()
	{
		info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
	    info("X");
	    info("X");
	    info("X");
	    info("X");
	}

	public  void info(String string) {
		 log.info(string);
		
	}
	public  void debug(String string) {
		 log.debug(string);		
	}
	
	public  void error(String string) {
		 log.error(string);		
	}

}
