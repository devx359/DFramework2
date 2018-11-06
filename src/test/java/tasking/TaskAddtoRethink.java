package tasking;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;


import lib.TaskAdd;



public class TaskAddtoRethink {
	
	TaskAdd tsk;
	
	
	@Test
	public void meth()
	{
		tsk = new TaskAdd();
		try {
			tsk.add("govind.jha@imerit.net", "JC-B14ylscvX", 1,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250RW1haWwiOiJkZWJhcHJpeW8uaGFsZGVyQGltZXJpdC5uZXQiLCJjb250SUQiOiJkZWJhcHJpeW8uaGFsZGVyQGltZXJpdC5uZXQiLCJtZW1iZXJJRCI6MTIwOSwiY29udEZOYW1lIjoiRGViYXByaXlvIiwiY29udExOYW1lIjoiSGFsZGVyIiwiY29udFByb2ZpbGVQaWMiOm51bGwsImRlcGFydG1lbnQiOiJEZWxpdmVyeSAvIE9wZXJhdGlvbnMiLCJkZXNpZ25hdGlvbiI6IlNvZnR3YXJlIFRlc3RlciIsImlhdCI6MTUzNjEzMTA3OSwiZXhwIjoxNTM2NzM1ODc5fQ.6r0EEETz9QgGdnk_r5qotkBs4C7Gj41RGhTp3JY-dsw","multi_psubmit_flow");
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}

}
