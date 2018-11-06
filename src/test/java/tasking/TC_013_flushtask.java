package tasking;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Cursor;

import Utilities.PathUtility;
import lib.TaskAdd;

public class TC_013_flushtask extends MainClass {
	String taskstatus, qcstatus,opstatus,op2status,nodestatus;
	Boolean teststatus = false,teststatus1 =false;
	String FlushTaskCode;
	Map[] maps ;
	Map hmap,hmap1;
	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void Assertions(String testCase) {
		
		maps = listAll.toArray(new HashMap[listAll.size()]);
		double time=0;	
		switch (testCase) {
		case "TC_1":
			
			teststatus = false;
			System.out.println("Assertion of TC_1 starts");
			// Assertion of c8 flush
			
			try {
				
			status = "pending";
			
            	 while(!(status.equalsIgnoreCase("flushed")) && time<=30000)
 				{
 					hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
 					status = hmap.get("task_status").toString();
 					Thread.sleep(5000);
 					System.out.println("Waiting for engagement table status change for 5-Sec...");
 					time=time+5000;	
 				}
 				if (status.equalsIgnoreCase("flushed")) 
 				{
 					teststatus = true;
 				}
 				
 				else
 				{
 					teststatus = false;
 				}
          			
 				//Verify whether test pass or fail.....
					if (teststatus == true) {
						System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
						test.pass("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
					} else {
						System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC was incorrect");
						test.fail("Flush of c8 and c14 for simple flow 1OP 1QC FAILED");
					}
					}
				 catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_1");
				}
			

			break;
		//Verify Flushed task OP node is dispatched	
		case "TC_2":
			System.out.println("Assertion of TC_2 starts");
			teststatus = false;
			try {
				
				status="queued";
				nodestatus="pending";
				
				while(!((status.equalsIgnoreCase("flushed")) && (nodestatus.equalsIgnoreCase("flushed"))) && time<30000)
				{
					
					System.out.println("Waiting for engagement table status change for 15-Sec...");
					Thread.sleep(15000);
					hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
					status = hmap.get("task_status").toString();
					hmap = dbcon.export_task_using_taskCode(maps[0].get("task_code").toString());
					nodestatus = hmap.get("submit_data").toString();
					System.out.println(status+nodestatus);
					time=time+5000;	
						
				}
				
				if (status.equalsIgnoreCase("flushed") && nodestatus.equalsIgnoreCase("flushed")) {
					teststatus = true;
				} else {
					teststatus = false;
				}
								
				//Verify whether test pass or fail.....
				if (teststatus == true) {
					System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
					test.pass("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
				} else {
					System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC was incorrect");
					test.fail("Flush of c8 and c14 for simple flow 1OP 1QC FAILED");
				}
				}
			 catch (Exception e) {
				e.printStackTrace();
				System.out.println("issue in tc_2");
			}
			break;
			
			//Verify Flush task when OP node is ACK
		case "TC_3":
			teststatus = false;
			System.out.println("Assertion of TC_3 starts");
			// Assertion of c8 flush
			
			try {
				
			status = "pending";
			while(!(status.equalsIgnoreCase("flushed"))&& time<30000)
				{
					hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
					status = hmap.get("task_status").toString();
					System.out.println("Waiting for engagement table status change for 15-Sec...");
					Thread.sleep(15000);
					
					time=time+5000;	
				}
				if (status.equalsIgnoreCase("flushed")) 
				{
					teststatus = true;
				}
				
				else
				{
					teststatus = false;
				}
					
								
					//Verify whether test pass or fail.....
					if (teststatus == true) {
						System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC with judgement was correct");
						test.pass("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
					} else {
						System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC with judgement was incorrect");
						test.fail("Flush of c8 and c14 for simple flow 1OP 1QC FAILED");
					}
					}
				 catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_2");
				}
			break;
		case "TC_4":
			System.out.println("Assertion of TC_4 starts");
			teststatus = false;
			try {
				
				status="queued";
				nodestatus="pending";
				
				while(!((status.equalsIgnoreCase("flushed")) && (nodestatus.equalsIgnoreCase("flushed"))) && time<30000)
				{
					
					System.out.println("Waiting for engagement table status change for 15-Sec...");
					Thread.sleep(15000);
					hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
					status = hmap.get("task_status").toString();
					hmap = dbcon.export_task_using_taskCode(maps[0].get("task_code").toString());
					nodestatus = hmap.get("submit_data").toString();
					System.out.println(status+nodestatus);
					time=time+5000;	
						
				}
				
				if (status.equalsIgnoreCase("flushed") && nodestatus.equalsIgnoreCase("flushed")) {
					teststatus = true;
				} else {
					teststatus = false;
				}
								
				//Verify whether test pass or fail.....
				if (teststatus == true) {
					System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC with judgement2  was correct");
					test.pass("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
				} else {
					System.out.println("Flush of c8 and c14 for simple flow 1OP 1QC with judgement2 was incorrect");
					test.fail("Flush of c8 and c14 for simple flow 1OP 1QC FAILED");
				}
				}
			 catch (Exception e) {
				e.printStackTrace();
				System.out.println("issue in tc_4");
			}
			break;
			// Flush task after QC distribution...	
		case "TC_5":

			teststatus = false;
			System.out.println("Assertion of TC_5 starts");
			// Assertion of c8 flush
			
			try {
				
			status = "pending";
			while(!(status.equalsIgnoreCase("flushed")) && time<30000)
				{
					hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
					status = hmap.get("task_status").toString();
					Thread.sleep(15000);
					System.out.println("Waiting for engagement table status change for 15-Sec...");
					time=time+5000;	
				}
				if (status.equalsIgnoreCase("flushed")) 
				{
					teststatus = true;
				}
				
				else
				{
					teststatus = false;
				}
					
								
					//Verify whether test pass or fail.....
					if (teststatus == true) {
						System.out.println("Flush of c8,c14 and c20 for Parallel Flow was correct");
						test.pass("Flush of c8,c14 and c20 for Parallel Flow was correct");
					} else {
						System.out.println("Flush of c8,c14 and c20 for Parallel Flow was incorrect");
						test.fail("Flush of c8,c14 and c20 for Parallel Flow FAILED");
					}
					}
				 catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_5");
				}
		break;
		// Flush task after QC node dispatched starts...
		 case "TC_6" :
            
			 System.out.println("Assertion of TC_6 starts");
				teststatus = false;
				try {
					
					status="queued";
					nodestatus="pending";
					for(int i=0;i<maps.length;i++)
					{
					while(!((status.equalsIgnoreCase("flushed")) && (nodestatus.equalsIgnoreCase("flushed")))&& time<30000)
					{
						
						System.out.println("Waiting for engagement table status change for 15-Sec...");
						Thread.sleep(15000);
						hmap = dbcon.engagement_task_table(EngagementCode, maps[i].get("task_code").toString());
						status = hmap.get("task_status").toString();
						
						hmap = dbcon.export_task_using_taskCode(maps[i].get("task_code").toString());
						nodestatus = hmap.get("submit_data").toString();
						System.out.println(status+nodestatus);
						time=time+5000;	
						}
							
					}
					
					if (status.equalsIgnoreCase("flushed") && nodestatus.equalsIgnoreCase("flushed")) {
						teststatus = true;
					} else {
						teststatus = false;
					}
									
					//Verify whether test pass or fail.....
					if (teststatus == true) {
						System.out.println("Flush of c8,c14 and c20 for Parallel Flow  was correct");
						test.pass("Flush of c8 and c14 for simple flow 1OP 1QC was correct");
					} else {
						System.out.println("Flush of c8,c14 and c20 for Parallel Flow was incorrect");
						test.fail("Flush of c8 and c14 for simple flow 1OP 1QC FAILED");
					}
					}
				 catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_6");
				}
           break;
		//Verify Flush task when QC node is ACK
		 case "TC_7":

				teststatus = false;
				System.out.println("Assertion of TC_7 starts");
				// Assertion of c8 flush
				
				try {
					
				status = "pending";
				while(!(status.equalsIgnoreCase("flushed")) && time<30000)
					{
						hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
						status = hmap.get("task_status").toString();
						Thread.sleep(15000);
						System.out.println("Waiting for engagement table status change for 15-Sec...");
						time=time+5000;	
					}
					if (status.equalsIgnoreCase("flushed")) 
					{
						teststatus = true;
					}
					
					else
					{
						teststatus = false;
					}
						
									
						//Verify whether test pass or fail.....
						if (teststatus == true) {
							System.out.println("Flush of c8,c14 and c20 for Sequential Flow was correct");
							test.pass("Flush of c8,c14 and c20 for Sequential Flow was correct");
						} else {
							System.out.println("Flush of c8,c14 and c20 for Sequential Flow was incorrect");
							test.fail("Flush of c8,c14 and c20 for Sequential Flow FAILED");
						}
						}
					 catch (Exception e) {
						e.printStackTrace();
						System.out.println("issue in tc_7");
					}
			break;
			// Flush task after QC node dispatched starts...
			 case "TC_8" :
	            
				 System.out.println("Assertion of TC_4 starts");
					teststatus = false;
					try {
						
						status="queued";
						nodestatus="pending";
						
						while(!((status.equalsIgnoreCase("flushed")) && (nodestatus.equalsIgnoreCase("flushed"))) && time<30000)
						{
							
							System.out.println("Waiting for engagement table status change for 15-Sec...");
							Thread.sleep(15000);
							hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
							status = hmap.get("task_status").toString();
							hmap = dbcon.export_task_using_taskCode(maps[0].get("task_code").toString());
							nodestatus = hmap.get("submit_data").toString();
							System.out.println(status+nodestatus);
							time=time+5000;	
								
						}
						if (status.equalsIgnoreCase("flushed") && nodestatus.equalsIgnoreCase("flushed")) {
							teststatus = true;
						} else {
							teststatus = false;
						}
										
						//Verify whether test pass or fail.....
						if (teststatus == true) {
							System.out.println("Flush of c8,c14 and c20 for Sequential Flow  was correct");
							test.pass("Flush of  c8,c14 and c20 for Sequential Flow was correct");
						} else {
							System.out.println("Flush of c8,c14 and c20 for Sequential Flow was incorrect");
							test.fail("Flush of  c8,c14 and c20 for Sequential Flow FAILED");
						}
						}
					 catch (Exception e) {
						e.printStackTrace();
						System.out.println("issue in tc_8");
					}
	           break;
			 case "TC_9":

					teststatus = false;
					System.out.println("Assertion of TC_9 starts");
					// Assertion of c8 flush
					
					try {
						
					status = "pending";
					while(!(status.equalsIgnoreCase("flushed")) && time<30000)
						{
							hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
							status = hmap.get("task_status").toString();
							Thread.sleep(15000);
							System.out.println("Waiting for engagement table status change for 15-Sec...");
							time=time+5000;	
						}
						if (status.equalsIgnoreCase("flushed")) 
						{
							teststatus = true;
						}
						
						else
						{
							teststatus = false;
						}
							
										
							//Verify whether test pass or fail.....
							if (teststatus == true) {
								System.out.println("Flush of c8,c14 and c20 for Sequential Flow was correct");
								test.pass("Flush of c8,c14 and c20 for Sequential Flow was correct");
							} else {
								System.out.println("Flush of c8,c14 and c20 for Sequential Flow was incorrect");
								test.fail("Flush of c8,c14 and c20 for Sequential Flow FAILED");
							}
							}
						 catch (Exception e) {
							e.printStackTrace();
							System.out.println("issue in tc_9");
						}
				break;
				// Flush task after QC node dispatched starts...
				 case "TC_10" :
		            
					 System.out.println("Assertion of TC_10 starts");
						teststatus = false;
						try {
							
							status="queued";
							nodestatus="pending";
							
							while(!((status.equalsIgnoreCase("flushed")) && (nodestatus.equalsIgnoreCase("flushed"))) && time<30000)
							{
								
								System.out.println("Waiting for engagement table status change for 15-Sec...");
								Thread.sleep(15000);
								hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
								status = hmap.get("task_status").toString();
								hmap = dbcon.export_task_using_taskCode(maps[0].get("task_code").toString());
								nodestatus = hmap.get("submit_data").toString();
								System.out.println(status+nodestatus);
								time=time+5000;	
							}
							
							if (status.equalsIgnoreCase("flushed") && nodestatus.equalsIgnoreCase("flushed")) {
								teststatus = true;
							} else {
								teststatus = false;
							}
											
							//Verify whether test pass or fail.....
							if (teststatus == true) {
								System.out.println("Flush of c8,c14 and c20 for Sequential Flow  was correct");
								test.pass("Flush of  c8,c14 and c20 for Sequential Flow was correct");
							} else {
								System.out.println("Flush of c8,c14 and c20 for Sequential Flow was incorrect");
								test.fail("Flush of  c8,c14 and c20 for Sequential Flow FAILED");
							}
							}
						 catch (Exception e) {
							e.printStackTrace();
							System.out.println("issue in tc_10");
						}
		           break;
			 
		}
		
	}    
	}


