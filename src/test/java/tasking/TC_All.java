package tasking;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.jsonpath.JsonPath;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import Keywords.Keyword;
import Utilities.ExtentManager;
import lib.Rethink_query;

public class TC_All
{
//Refresh flow 
	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	WebDriver driver;
	Connection conn;
	RethinkDB r;
	int i=0;
	String node_tsk_id = null;
	String task_code = null;
	String reques_id = null;
	String user_code = null;
	String scenario =null;
	String response_status = "Submit";
	Rethink_query rethink;
	String next_reques_id = null;
	String nodeTaskid1,nodeTaskid2;
	String userCode,nodeId,submit,nodeType;
	String nodetaskid,NodewiseTask,maintask,userTask;
	Keyword keywords;
	HashMap<String,String>  taskRequest,taskSendOp, taskSubmit;
	
	
	
	@BeforeSuite
	public void setup()
	{
		//keywords.setup();
	rethink = new Rethink_query();
	ExtentManagerObj = new ExtentManager();
	reports = ExtentManagerObj.GetExtent("TC_All");
	
	
	}	
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
	public void TaskRequest(Hashtable<String,String> tdata,ITestContext context)
	
  {
    	taskRequest= new HashMap<String,String>() ;
    	taskSendOp= new HashMap<String,String>() ;
    	taskSubmit= new HashMap<String,String>() ;
    	test = reports.createTest("Tasks Refresh just after request Scenario");
    	keywords =new Keyword(test,reports,rethink);
    	
    	nodeType=tdata.get("nodeType");
    	scenario=tdata.get("scenario");
    	
    	if(nodeType.equalsIgnoreCase("op"))
    	{
    		
        	nodeId=tdata.get("nodeId");
    	}
    	else
    	{
    		nodeId=tdata.get("nodeId");
    	}
    	
    	
        userCode=tdata.get("userCode");
    	submit=tdata.get("status");
    
	test.info("**********Page refresh just after task request********");
		//Refresh just after task request, before getting response .Reload
	//try {
	switch(scenario)
	{
	
	case "Refresh":
		
		while(i<2)
		{		
		test.info("Refresh request case--------"+i);
		//Send request
		taskRequest=keywords.task_request(userCode,nodeId);
		
		String nodetaskid=taskRequest.get("nodetaskid");
		String NodewiseTask=taskRequest.get("Table Node wise task data");
		String maintask=taskRequest.get("Table main_task_list data");
		String userTask=taskRequest.get("Table user_task data");			
		System.out.println("nodetaskid"+nodetaskid);
	i++;
		}
		
		break;
		
	case "P_submit":
	
	//Send request
	taskRequest=keywords.task_request(userCode,nodeId);

	taskRequest=keywords.task_request(userCode,nodeId);	
	taskSendOp= keywords.task_send_to_operator(userCode);
	taskSubmit=keywords.task_submit(userCode,"p_submit");
	
	 nodetaskid=taskRequest.get("nodetaskid");
	 NodewiseTask=taskRequest.get("Table Node wise task data");
	 maintask=taskRequest.get("Table main_task_list data");
	 userTask=taskRequest.get("Table user_task data");			
	break;
	
	case "submit":
	
	//Send request
	taskRequest=keywords.task_request(userCode,nodeId);

	taskRequest=keywords.task_request(userCode,nodeId);	
	taskSendOp= keywords.task_send_to_operator(userCode);
	taskSubmit=keywords.task_submit(userCode,"submit");
	
	nodetaskid=taskRequest.get("nodetaskid");
	NodewiseTask=taskRequest.get("Table Node wise task data");
	maintask=taskRequest.get("Table main_task_list data");
	userTask=taskRequest.get("Table user_task data");	
	break;
	}
	
	
 
	
  }

  @AfterSuite
    public void teardown()
    {
    	
    	keywords.teardown();
    	
    }
    
    
}

