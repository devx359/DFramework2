package tasking;

import static com.jayway.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.PathUtility;
import Utilities.UnixTimeStampConverter;
import lib.Rethink_query;
import lib.TaskAdd;

public class TC_011_Data_Export extends MainClass {

	
	Connection con;
	HashMap resp;
	String engagement_code = null;
	JsonParser parser = new JsonParser();

	

	@Test(priority = 2, dataProvider = "testdataProvider", dataProviderClass = Utilities.impp_testdataProvider.class)
	public void myScript(Hashtable<String, String> tdata, ITestContext context) throws ParseException, InterruptedException {
		count = 1;
		taskfound = false;
		qcPercentage = 100;
		opTaskSubmitted = 0;
		qcTaskSubmitted = 0;

		// Fetch from Excel
		

		qcPercentage = qcpercent;
		/*test = reports.createTest(userCode + " " + submit_type + ":" + jobCode + ":" + nodetype + ":QC%" + qcpercent
				+ " nooftasks: " + nooftasks);*/
		defaultSteps(tdata);

		// Start Tasking
		//login();
		task_request();
		if (taskfound == true) {

			task_send_to_operator();
		} else {
			test.info(MarkupHelper.createLabel("Task_Send_to_operator API Not called since no task was found for:"
					+ userCode + " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
		}
		if (taskfound == true) {
			task_submit();
		} else {
			test.info(MarkupHelper.createLabel("Task_Submit API Not called since no task was found for:" + userCode
					+ " opNode: " + opNode + " jobcode: " + jobCode, ExtentColor.RED));
		}

	}

	@Test(priority = 2)
	public void assertions() throws InterruptedException {
		HashMap rs;
		System.out.println("Sleeping for 150 sec ");
		Thread.sleep(150000);

		int i = 0;
		Map[] maps = listAll.toArray(new HashMap[listAll.size()]);

		for (i = 0; i < listAll.size(); i++) {
			System.out.println(maps[i].get("task_code") + ":" + maps[i].get("nodeId") + " submit_time "
					+ maps[i].get("submit_time") + " distribute_time: " + maps[i].get("distribute_time") + " ack_time"
					+ maps[i].get("ack_time") + " Request_time:" + maps[i].get("request_time") + " submit_data:"
					+ maps[i].get("submit_data") + " user_agent" + maps[i].get("user_agent") + " task_data:"
					+ maps[i].get("task_data"));

			test.info(maps[i].get("task_code") + ":" + maps[i].get("nodeId") + " submit_time "
					+ maps[i].get("submit_time") + " distribute_time: " + maps[i].get("distribute_time") + " ack_time"
					+ maps[i].get("ack_time") + " Request_time:" + maps[i].get("request_time") + " submit_data:"
					+ maps[i].get("submit_data") + " user_agent" + maps[i].get("user_agent") + " task_data:"
					+ maps[i].get("task_data"));
			// MYSQL end assertions starts **************************************
			try {
				// check mysql export_task table
				resp = dbcon.export_task(maps[i].get("node_task_id").toString());

				String mysqldistribute_time = (String) resp.get("distribute_time");
				System.out.println("mysqldistribute_time" + mysqldistribute_time);
				String mysqlack_time = (String) resp.get("ack_time");
				String mysqlsubmit_time = (String) resp.get("submit_time");
				String mysqlrequest_time = (String) resp.get("request_time");
				String task_data = "["+(String) resp.get("task_data")+"]";
				System.out.println(task_data+task_data);
				String user_agent = (String) resp.get("user_agent");
				String submit_data = "["+(String) resp.get("submit_data")+"]";
				System.out.println(submit_data+submit_data);

				// check mysql main task table
				rs = dbcon.engagement_task_table(engagement_code, maps[i].get("task_code").toString());
				String task_status = (String) rs.get("task_status");

				// check rethink
				Object main_task_list_status = rethink.main_task_list(maps[i].get("task_code").toString());
				Object nodetaskStatus = rethink
						.node_wise_task_data_usingNodeTaskId(maps[i].get("node_task_id").toString());
				Object task_request_status = rethink
						.task_request_usingNodeTaskId(maps[i].get("node_task_id").toString());
				Object task_response_status = rethink
						.task_response_usingNodeTaskId(maps[i].get("node_task_id").toString());

				if ((mysqldistribute_time.equals(maps[i].get("distribute_time")))
						&& (mysqlack_time.equals(maps[i].get("ack_time").toString()))
						&& (mysqlsubmit_time.equals(maps[i].get("submit_time").toString()))
						&& (mysqlrequest_time.equals(maps[i].get("request_time").toString()))
						&& (parser.parse(task_data).equals(parser.parse(maps[i].get("task_data").toString().replace("\\",""))))
						//&& (parser.parse(user_agent).equals(parser.parse(maps[i].get("user_agent").toString())))
						&& (parser.parse(submit_data).equals(parser.parse(maps[i].get("submit_data").toString())))
						&& (task_status.equalsIgnoreCase("processed"))
				// && (main_task_list_status.toString().equalsIgnoreCase("no_data"))
				// && (nodetaskStatus.toString().equalsIgnoreCase("no_data"))
				// && (task_request_status.toString().equalsIgnoreCase("no_data"))
				// && (task_response_status.toString().equalsIgnoreCase("no_data"))

				) {

					test.pass(MarkupHelper.createLabel("Mysql Exported Data matched for task_code:" + maps[i].get("task_code") + " node: "
							+ maps[i].get("nodeId") + " Submit Type: " + submit_type + " for " + userCode,ExtentColor.GREEN));
				} else {
					test.fail(MarkupHelper.createLabel("Mysql Exported Data NOT matched for task_code:" + maps[i].get("task_code") + " node: "
							+ maps[i].get("nodeId") + " Submit Type: " + submit_type + " for " + userCode,ExtentColor.RED));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("mysql db query issue" + e);
			}

			// Rethink assertions *********************************************

		}
		// Extract Mysql Table data

		// resp = dbcon.export_task(task_code, opNode); //
		// System.out.println(resp.get("task_code") + " " + resp.get("task_data") + " "
		// + resp.get("request_time")); // resp=dbcon.export_task_count(jobCode);
		// //
		// System.out.println("count" + resp.get("count"));

	}

}
