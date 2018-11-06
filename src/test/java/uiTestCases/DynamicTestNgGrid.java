package uiTestCases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class DynamicTestNgGrid {



	@Test

	public void createTestNgxml() {
	int numberOfTests = 3;

		XmlSuite mySuite = new XmlSuite();
		mySuite.setName("MyParalleSuite");
		mySuite.setParallel(XmlSuite.ParallelMode.TESTS);

		List<XmlTest> myTests = new ArrayList<XmlTest>();

		for (int i = 1; i <= numberOfTests; i++) {
			// Create an instance of XmlTest and assign a name for it.
			XmlTest myTest = new XmlTest(mySuite);
			myTest.setName("Test" + i);
			myTest.addParameter("RowNum", Integer.toString(i));
			myTest.addParameter("environment", "itest");

			// Add Class under
			List<XmlClass> myClasses = new ArrayList<XmlClass>();
			myClasses.add(new XmlClass(uiTestCases.Grid.class));
			myTest.setXmlClasses(myClasses);

			// add tests to array list
			myTests.add(myTest);

			System.out.println("Test added: Test" + i);

		}
		// add the list of tests to your Suite.
		mySuite.setTests(myTests);

		// Add the suite to the list of suites.
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		mySuites.add(mySuite);

		// Create an instance on TestNG
		TestNG myTestNG = new TestNG();
		myTestNG.setXmlSuites(mySuites);
		mySuite.setFileName("myTemp.xml");
		// mySuite.setThreadCount(3);

		createXmlFile(mySuite);
		myTestNG.run();
	}

	
	
	// This method will create an Xml file based on the XmlSuite data
	public void createXmlFile(XmlSuite mSuite) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("./myTemp.xml"));
			writer.write(mSuite.toXml());
			writer.flush();
			writer.close();
			System.out.println("TestNg xml file created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void createXmlFile(XmlSuite mSuite,int serial) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("./testngXML/TaskingJob"+serial+".xml"));
			writer.write(mSuite.toXml());
			writer.flush();
			writer.close();
			System.out.println("TestNg xml file created");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
