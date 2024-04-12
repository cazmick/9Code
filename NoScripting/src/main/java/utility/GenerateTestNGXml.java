package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class GenerateTestNGXml {
	Logger logger = LoggerFactory.getLogger(GenerateTestNGXml.class);

	public HashMap<String, String> suitesMap = null;

	public void CreateTestNGXml(String strMainSheetPath, String strSheetName) {
		try {
			ExcelRead exl = new ExcelRead();
			exl.setMainSheetPath(strMainSheetPath);
			suitesMap = exl.readExcelSheet(strMainSheetPath, strSheetName);
			if (suitesMap != null) {
				logger.info("Generating testNG XML.");
				List<XmlSuite> suites = new ArrayList<XmlSuite>();
				for (String suiteName : suitesMap.keySet()) {
					if (suitesMap.get(suiteName).equalsIgnoreCase("Yes")) {
						XmlSuite suite = new XmlSuite();
						logger.info("Test Suite " + suiteName + " added successfully in testng.xml");
						suite.setName(suiteName);
						XmlTest test = new XmlTest(suite);
						test.setName(suiteName + "Test");
						List<XmlClass> classez = new ArrayList<XmlClass>();
						classez.add(new XmlClass("testScript.API"));
						test.setXmlClasses(classez);
						List<XmlTest> tests = new ArrayList<XmlTest>();
						tests.add(test);
						suite.setTests(tests);
						suites.add(suite);
					}
				}
				TestNG testNG = new TestNG();
				testNG.setXmlSuites(suites);
				logger.info("testNG XML generated successfully.");
				testNG.run();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logger.error("Error occur in generating testNG XML." + ex.getMessage());
		}
	}

	public HashMap<String,String> getSuiteMap() {
		return suitesMap;
	}
}
