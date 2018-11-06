package tasking;
import java.util.HashMap;
import java.util.Hashtable;

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

	public class TC_009_submit_psubmit {
		//psubmit page Refresh
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
			public String Category1;
			HashMap<String,String>  taskRequest, taskSendOp,taskSubmit,taskPsubmit;
			
			@BeforeClass
			public void setup()
			{
				//partial.setup();
			rethink = new Rethink_query();
			ExtentManagerObj = new ExtentManager();
			reports = ExtentManagerObj.GetExtent("Reports");
			test = reports.createTest("Serial tasks Scenario");
			partial =new Keyword(test,reports,rethink);
			}		
			
		    @SuppressWarnings({ "unchecked", "rawtypes" })
		    @Test(dataProvider="testdataProvider", dataProviderClass=Utilities.impp_testdataProvider.class)
			public void refresh_Task_after_pSubmit(Hashtable<String,String> tdata,ITestContext context)
			
		  {
		    	
		    	taskSendOp= new HashMap<String,String>() ;
		    	taskRequest = new HashMap<String,String>();
		    	taskSubmit=new HashMap<String,String>() ;
		    	taskPsubmit=new HashMap<String,String>() ;
				int k=0;
				test.info("**********Page refresh just after partial Submit********");
		//	try
			//{
				taskRequest=partial.task_request("namita1.singh@imerit.net","c10");	
				taskSendOp= partial.task_send_to_operator("namita1.singh@imerit.net");
				
				taskSubmit=partial.task_submit("namita1.singh@imerit.net","submit");
				taskPsubmit=partial.task_submit("namita1.singh@imerit.net","p_submit");
		    	//Tables after Task request 
				
				String Rq_NodewiseTask=taskRequest.get("Table Node wise task data");
				String Rq_maintask=taskRequest.get("Table main_task_list data");
				String Rq_userTask=taskRequest.get("Table user_task data");
				
				// Table after send to operator
				String St_nodetaskid=taskSendOp.get("nodetaskid");
				//String St_category=taskSendOp.get("result");
				String St_NodewiseTask=taskSendOp.get("Table Node wise task data");
				String St_maintask=taskSendOp.get("Table main_task_list data");
				String St_userTask=taskSendOp.get("Table user_task data");
				
				
				//Tables after submit
				String Sb_taskResponse=taskSubmit.get("Table Task Response");
				String Sb_NodewiseTask=taskSubmit.get("Table Node wise task data");
				String Sb_maintask=taskSubmit.get("Table main_task_list data");
				String Sb_userTask=taskSubmit.get("Table user_task data");
				//String Sb_SubmitData=taskSubmit.get("Submitdata");
				
				//Tables after submit
				String Sb_taskResponse1=taskPsubmit.get("Table Task Response");
				String Sb_NodewiseTask1=taskPsubmit.get("Table Node wise task data");
				String Sb_maintask1=taskPsubmit.get("Table main_task_list data");
				String Sb_userTask1=taskPsubmit.get("Table user_task data");
				System.out.println(St_nodetaskid);

				test.info("Table Node wise task data => node_wise_status: " + JsonPath.read(Rq_NodewiseTask, "$.node_wise_status"));
				
				//Node wise task detail after task request
				test.info("Table Node wise task data =>Ack_time:"+JsonPath.read(Rq_NodewiseTask, "$.ack_time")+""
				+ "|node_task_id:"+JsonPath.read(Rq_NodewiseTask, "$.node_task_id")+
				"|job_code:"+JsonPath.read(Rq_NodewiseTask, "$.job_code")+"|Node_id:"
				+JsonPath.read(Rq_NodewiseTask, "$.node_id")
				+ "|request_id:"+JsonPath.read(Rq_NodewiseTask, "$.request_id")+
				"|user_code:"+JsonPath.read(Rq_NodewiseTask, "$.user_code"));
				
				//UserTask  after task request
				test.info("Table:UserTask==>Table after task request"+Rq_userTask);
				
				//Main task list after task request
				test.info("Table:Table main_task_list data => C6 distribute_count:"
						+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.distribute_count")
						+" |C6 node_status: "+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.node_status")
						+" |C6 submit_count: "+JsonPath.read(Rq_maintask, "$.dependency_table.[0].c6.submit_count")
						+" |C10 node_status: "+JsonPath.read(Rq_maintask, "$.dependency_table.[1].c10.node_status")
						+" |C10 submit_count: "+JsonPath.read(Rq_maintask, "$.dependency_table.[1].c10.submit_count")
						+" |Task Status: "+JsonPath.read(Rq_maintask, "$.task_status"));
				
			        	//Nodewise task detail after task send to operator
				       test.info("Table:Table Node wise task data =>Ack_time:"+JsonPath.read(St_NodewiseTask, "$.ack_time")+""
						+ "|node_task_id:"+JsonPath.read(St_NodewiseTask, "$.node_task_id")+
						"|job_code:"+JsonPath.read(St_NodewiseTask, "$.job_code")+"|Node_id:"
						+JsonPath.read(St_NodewiseTask, "$.node_id")
						+ "|request_id:"+JsonPath.read(St_NodewiseTask, "$.request_id")+
						"|user_code:"+JsonPath.read(St_NodewiseTask, "$.user_code"));
				       
				       //User task after  task send to operator
				    	test.info("Table:UserTask==>Table after send task to operator"+Sb_userTask);
				
				    	//Send task 
				    	test.info("Task send to operator API Reponse " +St_nodetaskid );
				    	
                      //main task list detail after send task to operator
				
				    	
				      test.info("Table:Table main_task_list data after send task to operator=> C6 distribute_count:"
						+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.distribute_count")
						+" |C6 node_status: "+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.node_status")
						+" |C6 submit_count: "+JsonPath.read(St_maintask, "$.dependency_table.[0].c6.submit_count")
						+" |C10 node_status: "+JsonPath.read(St_maintask, "$.dependency_table.[1].c10.node_status")
						+" |C10 submit_count: "+JsonPath.read(St_maintask, "$.dependency_table.[1].c10.submit_count")
						+" |Task Status: "+JsonPath.read(St_maintask, "$.task_status"));
				
				       //Nodewise task detail after task submit
				       test.info("Table:Table Node wise task data =>Ack_time:"+JsonPath.read(Sb_NodewiseTask, "$.ack_time")
						+ "|node_task_id:"+JsonPath.read(Sb_NodewiseTask, "$.node_task_id")+
						"|job_code:"+JsonPath.read(Sb_NodewiseTask, "$.job_code")+"|Node_id:"
						+JsonPath.read(Sb_NodewiseTask, "$.node_id")
						+ "|request_id:"+JsonPath.read(Sb_NodewiseTask, "$.request_id")+
						"|user_code:"+JsonPath.read(Sb_NodewiseTask, "$.user_code"));
				
				
				        //main task after task submit
				         test.info("Table:Table main_task_list data after task submit => C6 distribute_count:"
						+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.distribute_count")
						+" |C6 node_status: "+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.node_status")
						+" |C6 submit_count: "+JsonPath.read(Sb_maintask, "$.dependency_table.[0].c6.submit_count")
						+" |C10 node_status: "+JsonPath.read(Sb_maintask, "$.dependency_table.[1].c10.node_status")
						+" |C10 submit_count: "+JsonPath.read(Sb_maintask, "$.dependency_table.[1].c10.submit_count")
						+" |Task Status: "+JsonPath.read(Sb_maintask, "$.task_status"));
					
				
				        
				
				
				System.out.println(Sb_taskResponse);
				test.info("Table:Taskresponse ==>Table after task submit"+Sb_taskResponse);
				
				//userTask table detail
				
				test.info("Table:UserTask==>Table after submit task"+St_userTask);
			
			/*}
			  catch(Exception e)
			   {
				System.out.println("Unable to Refresh after Partial submit"+e);
				test.fail("Unable to Refresh after Partial submit");
			   }*/
			   
		  }
		    @AfterClass
		    public void teardown()
		    {
		    	test.info("**********Partial Submit Page Refresh info ends********");
		    	partial.teardown();
		    	
		    }
		    
		    
	}


