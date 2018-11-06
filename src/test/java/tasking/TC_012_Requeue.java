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

public class TC_012_Requeue extends MainClass {
	String opstatus, qcstatus,op2status;
	Boolean teststatus = false;
	Boolean teststatus1 = false;
	Boolean teststatus2 = false;
	String RequeuedTaskCode;
	
	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void Assertions(String testCase) {
		switch (testCase) {
		case "TC_1":
			
			teststatus = false;
			teststatus1 = false;
			System.out.println("Assertion of TC_1 starts");
			// Assertion of c8 requeue
			RequeuedTaskCode = RequeueNodeList.get(0);//get the 1st requeued task
			opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}

				// Assertion of c14 requeue
				RequeuedTaskCode = RequeueNodeList.get(1);//get the 2nd requeued task
				qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("submitted")
						&& (qcstatus.equalsIgnoreCase("preparing") || qcstatus.equalsIgnoreCase("ready"))) {
					teststatus1 = true;
					System.out.println("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1==true)) {
					System.out.println("Requeue of c8 and c14 for simple flow 1OP 1QC was correct");
					test.pass("Requeue of c8 and c14 for simple flow 1OP 1QC was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple flow 1OP 1QC was incorrect");
					test.fail("Requeue of c8 and c14 for simple flow 1OP 1QC FAILED");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("issue in tc_1");
			}

			break;
			
		case "TC_2":
			System.out.println("Assertion of TC_2 starts");
			
			 teststatus = false;
			 teststatus1 = false;
			
			// Assertion of c8 requeue
			RequeuedTaskCode = RequeueNodeList.get(0);
			opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
			qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
			try {

				if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")) {
					teststatus = true;
					System.out.println("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				} else {
					teststatus = false;
					System.out.println("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:"
							+ opstatus + " qc status: " + qcstatus);
					test.info("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:" + opstatus
							+ " qc status: " + qcstatus);
				}

				// Assertion of c14 requeue
				RequeuedTaskCode = RequeueNodeList.get(1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
				opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
				if (opstatus.equalsIgnoreCase("submitted")
						&& (qcstatus.equalsIgnoreCase("preparing") || qcstatus.equalsIgnoreCase("ready"))) {
					teststatus1 = true;
					System.out.println("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
							+ opstatus + " qc status: " + qcstatus);
				} else {
					teststatus1 = false;
					System.out.println("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
							+ opstatus + " qc status: " + qcstatus);
					test.info("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
							+ " qc status: " + qcstatus);
				}

				if ((teststatus == true)&&(teststatus1 == true)) {
					System.out.println("Requeue of c8 and c14 for simple flow op judgement 2 was correct");
					test.pass("Requeue of c8 and c14 for simple flow op judgement 2 was correct");
				} else {
					System.out.println("Requeue of c8 and c14 for simple flow op judgement 2 was incorrect");
					test.fail("Requeue of c8 and c14 for simple flow op judgement 2 FAILED");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("issue in tc_2");
			}
			break;
		case "TC_3":
			System.out.println("Assertion of TC_3 starts");
			 teststatus = false;
			 teststatus1 = false;
			 teststatus2 = false;
				
				// Assertion of c8 requeue
				RequeuedTaskCode = RequeueNodeList.get(0);
				System.out.println("RequeuedTaskCode:"+RequeuedTaskCode);
				opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
				op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 2);
				qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 1);
				try {

					if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")&& op2status.equalsIgnoreCase("submitted")) {
						teststatus = true;
						System.out.println("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:"
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:" + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					} else {
						teststatus = false;
						System.out.println("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:"
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:" + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					}

					// Assertion of c14 requeue
					RequeuedTaskCode = RequeueNodeList.get(1);
					System.out.println("RequeuedTaskCode:"+RequeuedTaskCode);
					op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 2);
					opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
					qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 1);
					if (opstatus.equalsIgnoreCase("submitted")&& op2status.equalsIgnoreCase("ready")
							&& (qcstatus.equalsIgnoreCase("buffered"))) {
						teststatus1 = true;
						System.out.println("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus);
						test.info("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus);
					} else {
						teststatus1 = false;
						System.out.println("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
								+ opstatus + " qc status: " + qcstatus);
						test.info("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
								+ " qc status: " + qcstatus);
					}
					
					// Assertion of c20 requeue
					RequeuedTaskCode = RequeueNodeList.get(2);
					System.out.println("RequeuedTaskCode:"+RequeuedTaskCode);
					op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 2);
					opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
					qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 1);
					if (opstatus.equalsIgnoreCase("submitted")&& op2status.equalsIgnoreCase("submitted")
							&& (qcstatus.equalsIgnoreCase("preparing") || qcstatus.equalsIgnoreCase("ready"))) {
						teststatus2 = true;
						System.out.println("c20 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c20 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
					} else {
						teststatus2 = false;
						System.out.println("c20 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c20 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					}

					if ((teststatus == true)&&(teststatus1 == true)&&(teststatus2 == true)) {
						System.out.println("Requeue of c8 and c14 and c20 for parallel OP 1QC was correct");
						test.pass("Requeue of c8 and c14 c20 for parallel OP 1QC was correct");
					} else {
						System.out.println("Requeue of c8 and c14 and c20 for parallel OP 1QC was incorrect");
						test.fail("Requeue of c8 and c14 c20 for parallel OP 1QCFAILED");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_3");
				}
			break;
		case "TC_4":
			System.out.println("Assertion of TC_4 starts");
			 teststatus = false;
				
				// Assertion of c8 requeue
				RequeuedTaskCode = RequeueNodeList.get(0);
				opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
				op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
				qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 2);
				try {

					if (opstatus.equalsIgnoreCase("ready") && qcstatus.equalsIgnoreCase("buffered")&& op2status.equalsIgnoreCase("buffered")) {
						teststatus = true;
						System.out.println("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:"
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c8 requeue  RequeuedTaskCode:" + RequeuedTaskCode + " was correct opstatus:" + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					} else {
						teststatus = false;
						System.out.println("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:"
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c8 requeue failed for RequeuedTaskCode:" + RequeuedTaskCode + " opstatus:" + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					}

					// Assertion of c14 requeue
					RequeuedTaskCode = RequeueNodeList.get(1);
					op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
					opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
					qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 2);
					if (opstatus.equalsIgnoreCase("submitted")&& op2status.equalsIgnoreCase("ready")
							&& (qcstatus.equalsIgnoreCase("buffered"))) {
						teststatus1 = true;
						System.out.println("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus);
						test.info("c14 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus);
					} else {
						teststatus1 = false;
						System.out.println("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
								+ opstatus + " qc status: " + qcstatus);
						test.info("c14 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
								+ " qc status: " + qcstatus);
					}
					
					// Assertion of c20 requeue
					RequeuedTaskCode = RequeueNodeList.get(2);
					op2status = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c14", 1);
					opstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c8", 0);
					qcstatus = rethink.main_task_list_status_check_with_TMid(RequeuedTaskCode, "c20", 2);
					if (opstatus.equalsIgnoreCase("submitted")&& op2status.equalsIgnoreCase("submitted")
							&& (qcstatus.equalsIgnoreCase("preparing") || qcstatus.equalsIgnoreCase("ready"))) {
						teststatus2 = true;
						System.out.println("c20 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c20 requeue for RequeuedTaskCode" + RequeuedTaskCode + " was correct.opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
					} else {
						teststatus2 = false;
						System.out.println("c20 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: "
								+ opstatus + " qc status: " + qcstatus+" op2status "+op2status);
						test.info("c20 requeue failed for RequeuedTaskCode " + RequeuedTaskCode + " opstatus: " + opstatus
								+ " qc status: " + qcstatus+" op2status "+op2status);
					}

					if  ((teststatus == true)&&(teststatus1 == true)&&(teststatus2 == true)) {
						System.out.println("Requeue of c8 and c14 and c20 for sequential op op qc  was correct");
						test.pass("Requeue of c8 and c14 c20 for sequential op op qc  was correct");
					} else {
						System.out.println("Requeue of c8 and c14 and c20 for sequential op op qc  FAILED");
						test.fail("Requeue of c8 and c14 c20 for sequential op op qc FAILED");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("issue in tc_3");
				}
			break;

		}

	}

}
