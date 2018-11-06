package tasking;

import java.util.Hashtable;

import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_014_Rework extends MainClass {
	
	String opstatus, qcstatus,opstatus1;
	Boolean teststatus = false;
	Boolean teststatus1 = false;
	Boolean teststatus2 = false;
	String ReworkTaskCode;
	
	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void Assertions(String testCase) {
		switch (testCase) {
		case "TC_1":
			
			teststatus = false;
			System.out.println("Assertion of TC_1 starts");
			// 1st Rework without task being in processed state in mysql
			ReworkTaskCode = ReworkNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}
				
				// 2nd Rework with task being in processed state in mysql
				ReworkTaskCode = ReworkNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus1 = true;
					System.out.println("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true)) {
					System.out.println("rework of c8 and c14 for simple flow 1OP 1QC was correct");
					test.pass("rework of c8 and c14 for simple flow 1OP 1QC was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple flow 1OP 1QC was incorrect");
					test.fail("rework of c8 and c14 for simple flow 1OP 1QC FAILED");
				}
			}
			catch(Exception e)
			{
				System.out.println("TC_1 assertion issue "+e);
				e.printStackTrace();
			}
			
			break;
			
		case "TC_2":
			teststatus = false;
			System.out.println("Assertion of TC_2 starts");
			// 1st Rework without task being in processed state in mysql
			ReworkTaskCode = ReworkNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}
				
				// 2nd Rework with task being in processed state in mysql
				ReworkTaskCode = ReworkNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus1 = true;
					System.out.println("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true))  {
					System.out.println("rework of c8 and c14 for simple flow op judgement 2 was correct");
					test.pass("rework of c8 and c14 for simple flow op judgement 2 was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple flow op judgement 2  FAILED");
					test.fail("rework of c8 and c14 for simple flow op judgement 2 FAILED");
				}
			}
			catch(Exception e)
			{
				System.out.println("TC_2 assertion issue "+e);
				e.printStackTrace();
			}
			
			break;
			
		case "TC_3":
			
			teststatus = false;
			System.out.println("Assertion of TC_3 starts");
			// 1st Rework without task not in processed state in mysql
			ReworkTaskCode = ReworkNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
			opstatus1 = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 2);
			qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c20", 1);

			try {

				if (opstatus.equalsIgnoreCase("ready") && opstatus1.equalsIgnoreCase("ready")&& qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.fail("rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}
				
				// 2nd Rework with task in processed state in mysql
				ReworkTaskCode = ReworkNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 2);
				opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("ready") && opstatus1.equalsIgnoreCase("ready")&& qcstatus.equalsIgnoreCase("buffered")) {
					teststatus1 = true;
					System.out.println("rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.fail("rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true))  {
					System.out.println("rework of c8 and c14 for simple parallel job was correct");
					test.pass("rework of c8 and c14 for simple parallel job was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple parallel job was FAILED");
					test.fail("rework of c8 and c14 for simple parallel job FAILED");
				}
			}
			catch(Exception e)
			{
				System.out.println("TC_3 assertion issue "+e);
				e.printStackTrace();
			}
			
			break;
			
		case "TC_4":
			
			teststatus = false;
			System.out.println("Assertion of TC_4 starts");
			// 1st Rework without task not in processed state in mysql
			ReworkTaskCode = ReworkNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}
				
				// 2nd Rework with task in processed state in mysql
				ReworkTaskCode = ReworkNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus1 = true;
					System.out.println("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true))  {
					System.out.println("rework of c8 and c14 for sequential op op qc job was correct");
					test.pass("rework of c8 and c14 for sequential op op qc job was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for sequential op op qc job  FAILED");
					test.fail("rework of c8 and c14 for sequential op op qc job FAILED");
				}
			}
			catch(Exception e)
			{
				System.out.println("TC_4 assertion issue "+e);
				e.printStackTrace();
			}
			
			break;
			
		case "TC_5":
			
			teststatus = false;
			System.out.println("Assertion of TC_5 starts");
			// 1st Rework without task not in processed state in mysql
			ReworkTaskCode = ReworkNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 rework  ReworkTaskCode:" + ReworkTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c8 rework failed for ReworkTaskCode:" + ReworkTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}
				
				// 2nd Rework with task in processed state in mysql
				ReworkTaskCode = ReworkNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(ReworkTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus1 = true;
					System.out.println("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 rework for ReworkTaskCode" + ReworkTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.fail("c14 rework failed for ReworkTaskCode " + ReworkTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true))  {
					System.out.println("rework of c8 and c14 for simple flow 50qc was correct");
					test.pass("rework of c8 and c14 for simple flow 50qc  was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple flow 50qc   FAILED");
					test.fail("rework of c8 and c14 for simple flow 50qc FAILED");
				}
			}
			catch(Exception e)
			{
				System.out.println("TC_5 assertion issue "+e);
				e.printStackTrace();
			}
			
			break;
		
		}

}
}
