package uiTestCases;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import Utilities.DriverUtil;
import Utilities.PathUtility;

public class Faltu {

	public WebDriver driver;
	DriverUtil obj;
	Connection conn;
	RethinkDB r;
	
	
	@Test
	public void fun()
	{
		conn=null;
		/*DriverUtil obj = new DriverUtil();
		driver= obj.DriverSetup("Chrome");
		
		driver.get("https://itest.imerit.net/");*/
		
		r = RethinkDB.r;
		// conn =
		// r.connection().hostname("104.196.151.173").port(28015).authKey("passW0rd#123").connect();
		conn = r.connection().hostname("35.165.23.169").port(28015).authKey("passW0rd#123").connect();
		
		Cursor cur2 = r
				.db("impp_alpha").table("task_request").filter(r.hashMap("user_code", "debapriyo.halder@imerit.net")
						.with("job_code", "JC-HJaRdDtIm").with("node_id", "c8").with("status", "dispatched"))
				.run(conn);

		for (Object doc2 : cur2) {

			Map map1 = (Map) doc2;
			String Reqstatus = map1.get("status").toString();
			System.out.println("reqstatus " + Reqstatus);
			if (Reqstatus.equalsIgnoreCase("dispatched")) {
				System.out.println("node_task_id"+map1.get("node_task_id").toString());
			}
		
		
	}
	
}
}
