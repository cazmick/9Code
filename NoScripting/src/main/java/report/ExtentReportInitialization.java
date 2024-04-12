package report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportInitialization {

	public static ExtentTest test;
	public static ExtentReports report;

	static Logger logger = LoggerFactory.getLogger(ExtentReportInitialization.class);

	public static void StartReporting(String strSuiteName) {
		try {
			logger.info("Reporting start for Test Suite: " + strSuiteName);
			report = new ExtentReports(
					System.getProperty("user.dir") + "\\Reports\\SuiteWise\\" + strSuiteName + ".html");
		} catch (Exception ex) {
			logger.error("Error occur in intializing exten report. More Details: " + ex.getMessage());
		}
	}

	public static ExtentTest startTest(String strTestName) {
		test = report.startTest(strTestName);
		return test;
	}

	public static void EndTest() {
		report.endTest(test);
	}

	public static void flushReport() {
		report.flush();
	}

}
