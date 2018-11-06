package tasking;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.jsonpath.JsonPath;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import Keywords.Keyword;
import Utilities.ExtentManager;
import lib.Rethink_query;

public class TC_006_ack_refresh 
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
	Keyword partial;
	HashMap<String,String>  jsonarray ;
	
	@BeforeClass
	public void setup()
	{
		//Refresh just after ACK,before submit .Reload
	rethink = new Rethink_query();
	ExtentManagerObj = new ExtentManager();
	reports = ExtentManagerObj.GetExtent("ACK_Refresh,before submit");
	test = reports.createTest("Tasks refresh Scenario");
	partial =new Keyword(test,reports,rethink);
	}		
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
    public void TaskRequest(Hashtable<String,String> tdata,ITestContext context)
  {

    	String userCode=tdata.get("userCode");
    	String nodeId=tdata.get("nodeId");
    	
    	jsonarray= new HashMap<String,String>() ;
		int i=0,j=0;
		test.info("**********Page refresh just after ACK********");
	

	try {
		
	while(j<2)
		{
		test.info("Refresh request case--------"+j);
		jsonarray=partial.task_request(userCode,nodeId);	
		jsonarray= partial.task_send_to_operator(userCode);
		
		j++;
		String nodetaskid=jsonarray.get("nodetaskid");
		String NodewiseTask=jsonarray.get("Table Node wise task data");
		String maintask=jsonarray.get("Table main_task_list data");
		String userTask=jsonarray.get("Table user_task data");
		
		
		test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(NodewiseTask, "$.node_wise_status"));
		//Node wise task detail
		
		test.info("Table Node wise task data =>Ack_time:"+JsonPath.read(NodewiseTask, "$.ack_time")
		+ "	|	node_task_id:"+JsonPath.read(NodewiseTask, "$.node_task_id")+
		"	|	job_code:"+JsonPath.read(NodewiseTask, "$.job_code")+"	|	Node_id:"
		+JsonPath.read(NodewiseTask, "$.node_id")
		+ "	|	request_id:"+JsonPath.read(NodewiseTask, "$.request_id")+
		"	|	user_code:"+JsonPath.read(NodewiseTask, "$.user_code"));
		
		//Main task list Detail
		//test.info(maintask);
		System.out.println(maintask);
		test.info("Table main_task_list data => C6 distribute_count:"
		+JsonPath.read(maintask, "$.dependency_table.[0].c6.distribute_count")
		+"	|	C6 node_status: "+JsonPath.read(maintask, "$.dependency_table.[0].c6.node_status")
		+"	|	C6 submit_count: "+JsonPath.read(maintask, "$.dependency_table.[0].c6.submit_count")
		+"	|	C10 node_status: "+JsonPath.read(maintask, "$.dependency_table.[1].c10.node_status")
		+"	|	C10 submit_count: "+JsonPath.read(maintask, "$.dependency_table.[1].c10.submit_count")
		+"	|	Task Status: "+JsonPath.read(maintask, "$.task_status"));
		
		//userTask table detail
		if(JsonPath.read(NodewiseTask, "$.ack_time")!= null)
		{
			test.pass("Aknowledgement sent");
		}
		else
		{
			test.fail("Aknowledgement not sent");
		}
		
		test.info("Task send to operator==>" +nodetaskid );
		
		if(j>0)
		{
			System.out.println("j:"+j);
			 nodeTaskid1=nodetaskid;
			 System.out.println("nodeTaskid1---------"+nodeTaskid1);
			 
		}
		else
		{
			System.out.println("j:"+j);
			 nodeTaskid2=nodetaskid;
			 System.out.println("nodeTaskid2------"+nodeTaskid2);
		}
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
		
		test.info("Table user_task data " + userTask);
		
		test.info("**********ACK Page Refresh info ends********");
		}
	}
	catch(Exception e)
	   {
		System.out.println("Unable to Refresh after Partial submit"+e);
	   }
}
    @AfterClass
    public void teardown()
    {
    	partial.teardown();
    	
    }
    
    
}
