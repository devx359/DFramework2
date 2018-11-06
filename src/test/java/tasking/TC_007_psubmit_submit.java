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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import Keywords.Keyword;
import Utilities.ExtentManager;
import lib.Rethink_query;

public class TC_007_psubmit_submit {
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
		String category1,category2;
		Keyword partial;
		ObjectMapper om;
		HashMap<String,String>  taskRequest, taskSendOp,taskSubmit,taskRequest1, taskSendOp1,taskSubmit1;
		
		@BeforeClass
		public void setup()
		{
			//partial.setup();
		rethink = new Rethink_query();
		ExtentManagerObj = new ExtentManager();
		reports = ExtentManagerObj.GetExtent("Psubmit_submit");
		test = reports.createTest("Tasks Refresh Scenario");
		partial =new Keyword(test,reports,rethink);
		}		
		
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    @Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
		public void refresh_Task_after_pSubmit(Hashtable<String,String> tdata,ITestContext context)
		
	  {
	    	
	    	taskSendOp= new HashMap<String,String>() ;
	    	taskRequest = new HashMap<String,String>();
	    	taskSubmit=new HashMap<String,String>() ;
	    	taskSendOp1= new HashMap<String,String>() ;
	    	taskSubmit1=new HashMap<String,String>() ;
			int l=0;
			String userCode=tdata.get("userCode_C6");
	    	String nodeId=tdata.get("nodeId");
	    	String status=tdata.get("status_1");
	    	String status2=tdata.get("status_2");
			test.info("**********Submit after multiple time partial submit********");
	//	try
		//{
			
				taskRequest=partial.task_request(userCode,nodeId);	
				taskSendOp= partial.task_send_to_operator(userCode);
				taskSubmit=partial.task_submit(userCode,status);
				taskRequest1=partial.task_request(userCode,nodeId);	
				taskSendOp1= partial.task_send_to_operator(userCode);
				taskSubmit1=partial.task_submit(userCode,status2);
				
				
			//jsonarray=partial.task_submit("namita.singh@imerit.net","submit");
		   
			//Tables after Task request 
				
			String Rq_NodewiseTask=taskRequest.get("Table Node wise task data");
			String Rq_maintask=taskRequest.get("Table main_task_list data");
			String Rq_userTask=taskRequest.get("Table user_task data");
			
			// Table after send to operator
			String St_nodetaskid1=taskSendOp.get("nodetaskid");
			String St_nodetaskid2=taskSendOp1.get("nodetaskid");
			//String St_category1=taskSendOp.get("result").toString();
			String St_NodewiseTask=taskSendOp.get("Table Node wise task data");
			String St_maintask=taskSendOp.get("Table main_task_list data");
			String St_userTask=taskSendOp.get("Table user_task data");
			String St_category2=taskSendOp1.get("result").toString();
			
			
			//Tables after submit
			String Sb_taskResponse=taskSubmit.get("Table Task Response");
			String Sb_NodewiseTask=taskSubmit.get("Table Node wise task data");
			String Sb_maintask=taskSubmit.get("Table main_task_list data");
			String Sb_userTask=taskSubmit.get("Table user_task data");
			String Sb_SubmitData=taskSubmit.get("Submitdata");		

			
			System.out.println("SubmitData"+Sb_SubmitData);
			
			System.out.println("Data submitted by partial submit"+Sb_SubmitData+"Partial data after submit"+St_category2);
			
			test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(Rq_NodewiseTask, "$.node_wise_status"));
			//Node wise task detail after task request
			
			test.info("Table Node wise task data after task request=>Ack_time:"+JsonPath.read(Rq_NodewiseTask, "$.ack_time")+""
			+ "|node_task_id:"+JsonPath.read(Rq_NodewiseTask, "$.node_task_id")+
			"|job_code:"+JsonPath.read(Rq_NodewiseTask, "$.job_code")+"|Node_id:"
			+JsonPath.read(Rq_NodewiseTask, "$.node_id")
			+ "|request_id:"+JsonPath.read(Rq_NodewiseTask, "$.request_id")+
			"|user_code:"+JsonPath.read(Rq_NodewiseTask, "$.user_code"));
			
			//Nodewise task detail after task send to operator
			test.info("Table Node wise task data after send task to operator=>Ack_time:"+JsonPath.read(St_NodewiseTask, "$.ack_time")+""
					+ "|node_task_id:"+JsonPath.read(St_NodewiseTask, "$.node_task_id")+
					"|job_code:"+JsonPath.read(St_NodewiseTask, "$.job_code")+"|Node_id:"
					+JsonPath.read(St_NodewiseTask, "$.node_id")
					+ "|request_id:"+JsonPath.read(St_NodewiseTask, "$.request_id")+
					"|user_code:"+JsonPath.read(St_NodewiseTask, "$.user_code"));
			
			//Nodewise task detail after task submit
			test.info("Table Node wise task data after task submit =>Ack_time:"+JsonPath.read(Sb_NodewiseTask, "$.ack_time")+""
					+ "|node_task_id:"+JsonPath.read(Sb_NodewiseTask, "$.node_task_id")+
					"|job_code:"+JsonPath.read(Sb_NodewiseTask, "$.job_code")+"|Node_id:"
					+JsonPath.read(Sb_NodewiseTask, "$.node_id")
					+ "|request_id:"+JsonPath.read(Sb_NodewiseTask, "$.request_id")+
					"|user_code:"+JsonPath.read(Sb_NodewiseTask, "$.user_code"));
			
			//Main task list Detail after task request...
			//test.info(maintask);
			
			test.info("Table main_task_list data after task request=> C6 distribute_count:"
			+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.distribute_count")
			+" |C6 node_status: "+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.node_status")
			+" |C6 submit_count: "+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.submit_count")
			+" |C10 node_status: "+JsonPath.read(Rq_maintask, "$.dependency_table.[1].c10.node_status")
			+" |C10 submit_count: "+JsonPath.read(Rq_maintask, "$.dependency_table.[1].c10.submit_count")
			+" |Task Status: "+JsonPath.read(Rq_maintask, "$.task_status"));
			
			//main task list detail after send task to operator
			
			test.info("Table main_task_list data after send task to operator=> C6 distribute_count:"
					+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.distribute_count")
					+" |C6 node_status: "+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.node_status")
					+" |C6 submit_count: "+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.submit_count")
					+" |C10 node_status: "+JsonPath.read(St_maintask, "$.dependency_table.[1].c10.node_status")
					+" |C10 submit_count: "+JsonPath.read(St_maintask, "$.dependency_table.[1].c10.submit_count")
					+" |Task Status: "+JsonPath.read(St_maintask, "$.task_status"));
			
			//main task after task submit
			test.info("Table main_task_list data after task submit => C6 distribute_count:"
					+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.distribute_count")
					+" |C6 node_status: "+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.node_status")
					+" |C6 submit_count: "+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.submit_count")
					+" |C10 node_status: "+JsonPath.read(Sb_maintask, "$.dependency_table.[1].c10.node_status")
					+" |C10 submit_count: "+JsonPath.read(Sb_maintask, "$.dependency_table.[1].c10.submit_count")
					+" |Task Status: "+JsonPath.read(Sb_maintask, "$.task_status"));
					
			
			//Task Response Detail
			
				 nodeTaskid1=St_nodetaskid1;	 
				 nodeTaskid2=St_nodetaskid2;
				 
				 System.out.println("nodeTaskid1---------"+nodeTaskid1);
				 System.out.println("nodeTaskid2------"+nodeTaskid2);
			
			
			//Node_task_id verification
			if(nodeTaskid1!=null && nodeTaskid2!=null)
			{
			if(nodeTaskid2.equalsIgnoreCase(nodeTaskid1))
			
			{
				
				test.pass("nodeTaskid1==>"+nodeTaskid1+"	nodeTaskid2==>"+nodeTaskid2+"......."+"Node task id is same");
				//test.pass("category1==>"+category1+"		category2==>"+category2+"......."+"Submited data is same");
			}
			else
			{
				test.info("nodeTaskid1==>"+nodeTaskid1+"	nodeTaskid2==>"+nodeTaskid2);
				//test.pass("category1==>"+category1+"		category2==>"+category2+"......."+"Submited data is not same");
				
			}
			}
			//Category verification
			if(St_category2!=null)
			{
			        try {
			       
			        	 JsonParser parser = new JsonParser();
			        	 JsonElement subData = parser.parse(Sb_SubmitData);
			        	 JsonElement catData = parser.parse(St_category2);
			        	 System.out.println(subData.equals(catData)); 
			        	 
			            if(subData.equals(catData))
			            {
			            	test.pass("Partial data after submit==>"+catData+"		Data submitted by partial submit==>"+subData+"......."+"Submited data is same");	
			            }
			            else
			            {
			            	test.fail("Partial data after submit==>"+catData+"		Data submitted by partial submit==>"+subData+"......."+"Submited data is not same");
			            }
			        } catch (Exception e) 
			        {
			            e.printStackTrace();
			        }
				}
			
			System.out.println(Sb_taskResponse);
			test.info("taskresponse ==>Table after task submit"+Sb_taskResponse);
			
			
			
			//userTask table detail
			test.info("UserTask==>Table after submit task"+Rq_userTask);
			
			test.info("UserTask==>Table after submit task"+St_userTask);
			test.info("UserTask==>Table after send task to operator"+Sb_userTask);
		    
		/*}
		  catch(Exception e)
		   {
			//System.out.println("Unable to submit after multiple Partial submit"+e);
			test.fail("Unable to submit after multiple Partial submit"+e);
		   }
		   */
	  }
	    @AfterClass
	    public void teardown()
	    {
	    	test.info("**********Submit after multiple Partial submit info ends********");
	    	partial.teardown();
	    	
	    }
	    
	    
}
