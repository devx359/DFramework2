package tasking;

import java.util.Hashtable;

import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_016_Psubmit extends MainClass {
	
	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void Assertions(String testCase) {
		switch (testCase) {
		case "TC_1":
			break;
		}
	}

}
