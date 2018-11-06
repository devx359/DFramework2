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

public class TC_003_Refresh 
{
//Refresh flow 
	ExtentReports reports;
	ExtentTest test;
	ExtentManager ExtentManagerObj;
	WebDriver driver;
	Connection conn;
	RethinkDB r;
	String node_tsk_id = null;
	String task_code = null;
	String reques_id = null;
	String user_code = null;
	String response_status = "Submit";
	Rethink_query rethink;
	String next_reques_id = null;
	String nodeTaskid1,nodeTaskid2;
	Keyword keywords;
	HashMap<String,String>  jsonarray ;
	
	@BeforeSuite
	public void setup()
	{
		//keywords.setup();
	rethink = new Rethink_query();
	ExtentManagerObj = new ExtentManager();
	reports = ExtentManagerObj.GetExtent("TC_003");
	
	
	}	
	
			
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
	public void TaskRequest(Hashtable<String,String> tdata,ITestContext context)
	
  {
    	test = reports.createTest("Tasks Refresh just after request Scenario");
    	keywords =new Keyword(test,reports,rethink);
    	String userCode=tdata.get("userCode_C6");
    	String nodeId=tdata.get("nodeId");
    	
    	jsonarray= new HashMap<String,String>() ;
		int i=0;
	test.info("**********Page refresh just after task request********");
		//Refresh just after task request, before getting response .Reload
	//try {
	while(i<3)
		{		
		test.info("Refresh request case--------"+i);
		//Send request
		jsonarray=keywords.task_request(userCode,nodeId);	
		String nodetaskid=jsonarray.get("nodetaskid");
		String NodewiseTask=jsonarray.get("Table Node wise task data");
		String maintask=jsonarray.get("Table main_task_list data");
		String userTask=jsonarray.get("Table user_task data");
		
		
		test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(NodewiseTask, "$.node_wise_status"));
		//Node wise task detail
		
		test.info("Table Node wise task Detail =>Ack_time:"+JsonPath.read(NodewiseTask, "$.ack_time")+""
		+ "		|	node_task_id:"+JsonPath.read(NodewiseTask, "$.node_task_id")+
		"|		job_code:"+JsonPath.read(NodewiseTask, "$.job_code")+"		|		Node_id:"
		+JsonPath.read(NodewiseTask, "$.node_id")
		+ "		|	request_id:"+JsonPath.read(NodewiseTask, "$.request_id")+
		"		|		user_code:"+JsonPath.read(NodewiseTask, "$.user_code"));
		
		if(JsonPath.read(NodewiseTask, "$.ack_time")!= null)
		{
			test.fail("Aknowledgement sent");
		}
		else
		{
			test.pass("Aknowledgement not sent");
		}
		
		if(i>0)
		{
			System.out.println("i:"+i);
			 nodeTaskid1=nodetaskid;
			 System.out.println("nodeTaskid1---------"+nodeTaskid1);
			 
		}
		else
		{
			System.out.println("i:"+i);
			 nodeTaskid2=nodetaskid;
			 System.out.println("nodeTaskid2------"+nodeTaskid2);
		}
		
		test.info("Task send to operator API Reponse " +nodetaskid );
		
		//Verify node task id
		if(nodeTaskid1!=null && nodeTaskid2!=null)
		{
		if(nodeTaskid2.equalsIgnoreCase(nodeTaskid1))
		
		{
			
			test.pass("nodeTaskid1==>"+nodeTaskid1+"nodeTaskid2==>"+nodeTaskid2+"......."+"Node task id is same");
		}
		else
		{
			test.info("nodeTaskid1==>"+nodeTaskid1+"nodeTaskid2==>"+nodeTaskid2);
			test.fail("nodeTaskid1==>"+nodeTaskid1+"nodeTaskid2==>"+nodeTaskid2+"......."+"Node task id is not same");
			
		}
		}
		//Main task list Detail
		//test.info(maintask);
		
		test.info("Task detail from main_task_list => C6 distribute_count:"
		+JsonPath.read(maintask, "$.dependency_table.[0].c6.distribute_count")
		+" 	|		C6 node_status: "+JsonPath.read(maintask, "$.dependency_table.[0].c6.node_status")
		+" 	|	C6 submit_count: "+JsonPath.read(maintask, "$.dependency_table.[0].c6.submit_count")
		+" 	|	C10 node_status: "+JsonPath.read(maintask, "$.dependency_table.[1].c10.node_status")
		+" 	|	C10 submit_count: "+JsonPath.read(maintask, "$.dependency_table.[1].c10.submit_count")
		+" |	Task Status: "+JsonPath.read(maintask, "$.task_status"));
		
		//userTask table detail
		
		test.info("Task detail from user_task table " + userTask);
				
	i++;
	
	
		}
  /*}
	catch(Exception e)
	   {
		System.out.println("Unable to Refresh just after request"+e);
	   }*/
	


	 
    	
		
  }

  @AfterSuite
    public void teardown()
    {
    	
    	keywords.teardown();
    	
    }
    
    
}

