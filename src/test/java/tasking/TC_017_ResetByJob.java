package tasking;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TC_017_ResetByJob extends MainClass {
	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context)
			throws ParseException, InterruptedException {

		defaultSteps(tdata);

	}

	public void checkStatus(int Mapindex) throws InterruptedException {
		Map hmap;
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);
		int RethinktaskCount;
		int time;
		time = 0;
		String status = "empty";

		//System.out.println("Checking MySql for task to be processed or flushed.." + maps[Mapindex].get("task_code"));
		while (!(status.equalsIgnoreCase("processed") || status.equalsIgnoreCase("flushed")) && time < 300000) {
			hmap = dbcon.engagement_task_table(EngagementCode, maps[Mapindex].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (!(status.equalsIgnoreCase("processed") || status.equalsIgnoreCase("flushed"))) {
				Thread.sleep(15000);
				time = time + 15000;
				System.out.print("+");
			}
		}
		if (time >= 300000) {
			System.out.println("task code not processed or flushed : " + maps[Mapindex].get("task_code") + " "
					+ maps[Mapindex].get("task_master_id"));
			test.fail("task code not processed or flushed : " + maps[Mapindex].get("task_code") + " "
					+ maps[Mapindex].get("task_master_id"));
		} else {
			System.out.println("task code " + maps[Mapindex].get("task_code") + " status: " + status + " "
					+ maps[Mapindex].get("task_master_id"));
			test.info("task code " + maps[Mapindex].get("task_code") + " status: " + status + " "
					+ maps[Mapindex].get("task_master_id"));
		}
	}

	public void checkQueuedStatus(int Mapindex) throws InterruptedException {
		Map hmap;
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);
		int RethinktaskCount;
		int time;
		time = 0;
		String status = "empty";

		//System.out.println("Checking MySql for same  task to j2p after flush.." + maps[Mapindex].get("task_code"));
		while (!(status.equalsIgnoreCase("queued") || status.equalsIgnoreCase("not_processed")) && time < 300000) {
			hmap = dbcon.engagement_task_table_orderbyIdDesc(EngagementCode,
					maps[Mapindex].get("task_code").toString());
			status = hmap.get("task_status").toString();
			if (!(status.equalsIgnoreCase("queued") || status.equalsIgnoreCase("not_processed"))) {
				Thread.sleep(15000);
				time = time + 15000;
				System.out.print("+");
			}
		}
		if (time >= 300000) {
			System.out.println("task NOT ready for j2p or queued : " + maps[Mapindex].get("task_code") + " "
					+ maps[Mapindex].get("task_master_id"));
			test.fail("task NOT ready for j2p or queued : " + maps[Mapindex].get("task_code") + " "
					+ maps[Mapindex].get("task_master_id"));
		} else {
			System.out.println("task ready for j2p or queued for task code " + maps[Mapindex].get("task_code")
					+ " status: " + status + " " + maps[Mapindex].get("task_master_id"));
			test.info("task ready for j2p or queued for task code " + maps[Mapindex].get("task_code") + " status: "
					+ status + " " + maps[Mapindex].get("task_master_id"));
		}
	}

	public void Assertions(String testCase) throws InterruptedException {
		Map hmap;
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);
		int RethinktaskCount;
		int time;

		switch (testCase) {
		case "TC_1":

			test.info("Below task codes should be processed state");
			System.out.println("Below task codes should be processed state");
			// checks below taskcodes for
			checkStatus(0);
			checkStatus(1);
			test.info("Below task codes should be in flushed state");
			System.out.println("Below task codes should be in flushed state");
			checkStatus(4);
			checkStatus(5);
			checkStatus(6);
			checkStatus(7);
			// checks if these tasks are again queued or ready for j2p
			test.info("Below task codes should be in in queued or not_processed state");
			System.out.println("Below task codes should be in in queued or not_processed state");
			checkQueuedStatus(4);
			checkQueuedStatus(5);
			checkQueuedStatus(6);
			checkQueuedStatus(7);

			break;

		case "TC_2":

			test.info("Below task codes should be processed state");
			System.out.println("Below task codes should be processed state");

			// checks below taskcodes for
			checkStatus(6);
			test.info("Below task codes should be in flushed state");
			System.out.println("Below task codes should be in flushed state");
			checkStatus(7);
			checkStatus(8);
			checkStatus(9);
			checkStatus(10);
			// checks if these tasks are again queued or ready for j2p
			test.info("Below task codes should be in in queued or not_processed state");
			System.out.println("Below task codes should be in in queued or not_processed state");
			checkQueuedStatus(7);
			checkQueuedStatus(8);
			checkQueuedStatus(9);
			checkQueuedStatus(10);

			break;

		case "TC_3":

			test.info("Below task codes should be processed state");
			System.out.println("Below task codes should be processed state");

			// checks below taskcodes for
			checkStatus(6);
			test.info("Below task codes should be in flushed state");
			System.out.println("Below task codes should be in flushed state");
			checkStatus(7);
			checkStatus(8);
			checkStatus(9);
			checkStatus(10);
			checkStatus(11);
			checkStatus(12);
			// checks if these tasks are again queued or ready for j2p
			test.info("Below task codes should be in in queued or not_processed state");
			System.out.println("Below task codes should be in in queued or not_processed state");
			checkQueuedStatus(7);
			checkQueuedStatus(8);
			checkQueuedStatus(9);
			checkQueuedStatus(10);
			checkQueuedStatus(11);
			checkQueuedStatus(12);

			break;

		case "TC_4":

			test.info("Below task codes should be processed state");
			System.out.println("Below task codes should be processed state");

			// checks below taskcodes for
			checkStatus(14);
			checkStatus(15);
			test.info("Below task codes should be in flushed state");
			System.out.println("Below task codes should be in flushed state");
			checkStatus(16);
			checkStatus(17);
			checkStatus(6);
			checkStatus(7);
			checkStatus(12);
			checkStatus(13);
			// checks if these tasks are again queued or ready for j2p
			test.info("Below task codes should be in in queued or not_processed state");
			System.out.println("Below task codes should be in in queued or not_processed state");
			checkQueuedStatus(16);
			checkQueuedStatus(17);
			checkQueuedStatus(6);
			checkQueuedStatus(7);
			checkQueuedStatus(12);
			checkQueuedStatus(13);

			break;
			
		case "TC_5":

			test.info("Below task codes should be processed state");
			System.out.println("Below task codes should be processed state");

			// checks below taskcodes for
			checkStatus(8);
			test.info("Below task codes should be in flushed state");
			System.out.println("Below task codes should be in flushed state");
			checkStatus(6);
			checkStatus(7);
			checkStatus(9);
			checkStatus(10);
			// checks if these tasks are again queued or ready for j2p
			test.info("Below task codes should be in in queued or not_processed state");
			System.out.println("Below task codes should be in in queued or not_processed state");
			checkQueuedStatus(6);
			checkQueuedStatus(7);
			checkQueuedStatus(9);
			checkQueuedStatus(10);

			break;
		}
	}

}
