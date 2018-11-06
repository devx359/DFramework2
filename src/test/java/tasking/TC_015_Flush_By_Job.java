package tasking;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_015_Flush_By_Job extends MainClass {

	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void Assertions(String testCase) {
		Map hmap;
		Boolean testStatus = false;
		Boolean testStatus2 = false;
		Boolean testStatus3 = false;
		Boolean testStatus4 = false;
		Boolean testStatus5 = false;
		Boolean testStatus6 = false;
		Boolean testStatus7 = false;
		Boolean testStatus8 = false;
		String status = "something";
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);

		switch (testCase) {

		case "TC_1":

			// status=rethink.main_task_list_status_check_taskStatus(maps[0].get("task_code").toString());
			hmap = dbcon.engagement_task_table(EngagementCode, maps[0].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("processed")) {
				testStatus = true;
			} else {
				testStatus = false;
			}
			hmap = dbcon.engagement_task_table(EngagementCode, maps[1].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("processed")) {
				testStatus2 = true;
				System.out.println("TaskCode: " + maps[1].get("task_code").toString() + " MySql Status: " + status);
				test.info("TaskCode: " + maps[1].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus2 = false;
				System.out.println("TaskCode: " + maps[1].get("task_code").toString() + " MySql Status: " + status);
				test.info("TaskCode: " + maps[1].get("task_code").toString() + " MySql Status: " + status);
			}
			hmap = dbcon.engagement_task_table(EngagementCode, maps[4].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus3 = true;
				System.out.println("OP C8 request_only TaskCode: " + maps[4].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[4].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus3 = false;
				System.out.println("OP C8 request_only TaskCode: " + maps[4].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[4].get("task_code").toString() + " MySql Status: "
						+ status);
			}
			hmap = dbcon.engagement_task_table(EngagementCode, maps[5].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus4 = true;
				System.out.println(
						"OP C8 ACK TaskCode: " + maps[5].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[5].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus4 = false;
				System.out.println(
						"OP C8 ACK TaskCode: " + maps[5].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[5].get("task_code").toString() + " MySql Status: " + status);
			}

			if ((testStatus == true) && (testStatus2 == true) && (testStatus3 == true) && (testStatus4 == true)) {
				System.out.println("Flush success for simple flow 1op 1qc");
				test.pass("Flush success for simple flow 1op 1qc");
			} else {
				test.fail("Flush failed for simple flow 1op 1qc");
				System.out.println("Flush failed for simple flow 1op 1qc");
			}

			break;
		case "TC_2":
			
			hmap = dbcon.engagement_task_table(EngagementCode, maps[10].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus = true;
				System.out.println("OP C8 ACK TaskCode: " + maps[10].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[10].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus = false;
				System.out.println("OP C8 ACK TaskCode: " + maps[10].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[10].get("task_code").toString() + " MySql Status: "
						+ status);

			}
			
			hmap = dbcon.engagement_task_table(EngagementCode, maps[11].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus2 = true;
				System.out.println("OP C8 request_only TaskCode: " + maps[11].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus2 = false;
				System.out.println("OP C8 request_only TaskCode: " + maps[11].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: "
						+ status);

			}
			
			hmap = dbcon.engagement_task_table(EngagementCode, maps[12].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus3 = true;
				System.out.println("QC C14 ACK TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 ACK TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus3 = false;
				System.out.println("QC C14 ACK TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 ACK TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);

			}
			
			hmap = dbcon.engagement_task_table(EngagementCode, maps[13].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus4 = true;
				System.out.println("QC C14 request_only TaskCode: " + maps[13].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 request_only TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus4 = false;
				System.out.println("QC C14 request_only TaskCode: " + maps[13].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 request_only TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: "
						+ status);

			}
			
			if ((testStatus == true) && (testStatus2 == true) && (testStatus3 == true) && (testStatus4 == true)) {
				System.out.println("Flush success for simple flow op judgement 2");
				test.pass("Flush success for simple flow op judgement 2");
			} else {
				test.fail("Flush failed for simple flow op judgement 2");
				System.out.println("Flush failed for simple flow op judgement 2");
			}

			
			break;
		case "TC_3":

			hmap = dbcon.engagement_task_table(EngagementCode, maps[8].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus = true;
				System.out.println("OP C14 request_only TaskCode: " + maps[8].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C14 request_only TaskCode: " + maps[8].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus = false;
				System.out.println("OP C14 request_only TaskCode: " + maps[8].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C14 request_only TaskCode: " + maps[8].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[9].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus2 = true;
				System.out.println(
						"OP C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus2 = false;
				System.out.println(
						"OP C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[11].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus3 = true;
				System.out.println(
						"QC C20 ACK TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20 ACK TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus3 = false;
				System.out.println(
						"QC C20 ACK TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20 ACK TaskCode: " + maps[11].get("task_code").toString() + " MySql Status: " + status);
			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[12].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus4 = true;
				System.out.println("QC C20 request_only TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C20 request_only TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);

			} else {
				testStatus4 = false;
				System.out.println("QC C20 request_only TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C20 request_only TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			if ((testStatus == true) && (testStatus2 == true) && (testStatus3 == true) && (testStatus4 == true)) {
				System.out.println("Flush success for simple parallel job");
				test.pass("Flush success for simple parallel job");
			} else {
				test.fail("Flush failed for simple parallel job");
				System.out.println("Flush failed for simple parallel jobc");
			}
			break;
		case "TC_4":
			hmap = dbcon.engagement_task_table(EngagementCode, maps[6].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus = true;
				System.out.println("OP C8 RequestOnly request_only TaskCode: " + maps[6].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus = false;
				System.out.println("OP C8 request_only TaskCode: " + maps[6].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: "
						+ status);

			}
			hmap = dbcon.engagement_task_table(EngagementCode, maps[7].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus2 = true;
				System.out.println(
						"OP C8 ACK TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus2 = false;
				System.out.println(
						"OP C8 ACK TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: " + status);

			}
			hmap = dbcon.engagement_task_table(EngagementCode, maps[12].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus3 = true;
				System.out.println("OP C14 RequestOnly TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C14 RequestOnly TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus3 = false;
				System.out.println("OP C14 RequestOnly TaskCode: " + maps[12].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C14 RequestOnly TaskCode: " + maps[12].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[13].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus4 = true;
				System.out.println(
						"OP C14 ACK TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C14 ACK TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus4 = false;
				System.out.println(
						"OP C14 ACK TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C14 ACK TaskCode: " + maps[13].get("task_code").toString() + " MySql Status: " + status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[16].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus5 = true;
				System.out.println(
						"QC C20 ACK TaskCode: " + maps[16].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20 ACK TaskCode: " + maps[16].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus5 = false;
				System.out.println(
						"QC C20 ACK TaskCode: " + maps[16].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20 ACK TaskCode: " + maps[16].get("task_code").toString() + " MySql Status: " + status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[17].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus6 = true;
				System.out.println(
						"QC C20  request_only: " + maps[17].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20  request_only TaskCode: " + maps[17].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus6 = false;
				System.out.println(
						"QC C20  request_only: " + maps[17].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C20  request_only TaskCode: " + maps[17].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			if ((testStatus == true) && (testStatus2 == true) && (testStatus3 == true) && (testStatus4 == true)
					&& (testStatus5 == true) && (testStatus6 == true)) {
				System.out.println("Flush success for sequential op op qc job");
				test.pass("Flush success for sequential op op qc job");
			} else {
				test.fail("Flush failed for sequential op op qc job");
				System.out.println("Flush failed for sequential op op qc job");
			}

			break;
		case "TC_5":
			hmap = dbcon.engagement_task_table(EngagementCode, maps[6].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus = true;
				System.out.println(
						"OP C8 ACK  TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus = false;
				System.out.println(
						"OP C8 ACK TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: " + status);
				test.info("OP C8 ACK TaskCode: " + maps[6].get("task_code").toString() + " MySql Status: " + status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[7].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus2 = true;
				System.out.println("OP C8 request_only TaskCode: " + maps[7].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus2 = false;
				System.out.println("OP C8 request_only TaskCode: " + maps[7].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("OP C8 request_only TaskCode: " + maps[7].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[9].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus3 = true;
				System.out.println("QC C14 ACK_only TaskCode: " + maps[9].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);
			} else {
				testStatus3 = false;
				System.out.println(
						"QC C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);
				test.info("QC C14 ACK TaskCode: " + maps[9].get("task_code").toString() + " MySql Status: " + status);

			}

			hmap = dbcon.engagement_task_table(EngagementCode, maps[10].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (status.equalsIgnoreCase("flushed")) {
				testStatus4 = true;
				System.out.println("QC C14 request_only TaskCode: " + maps[10].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 request_only TaskCode: " + maps[10].get("task_code").toString() + " MySql Status: "
						+ status);
			} else {
				testStatus4 = false;
				System.out.println("QC C14 request_only TaskCode: " + maps[10].get("task_code").toString()
						+ " MySql Status: " + status);
				test.info("QC C14 request_only TaskCode: " + maps[10].get("task_code").toString() + " MySql Status: "
						+ status);

			}

			if ((testStatus == true) && (testStatus2 == true) && (testStatus3 == true) && (testStatus4 == true)) {
				System.out.println("Flush success for simple flow 50qc");
				test.pass("Flush success for simple flow 50qc");
			} else {
				test.fail("Flush failed for simple flow 50qc");
				System.out.println("Flush failed for simple flow 50qc");
			}

			break;
		}
	}

}
