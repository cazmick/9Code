package launch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import report.CustomReport;
import utility.GenerateTestNGXml;

public class Master {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Master.class);
		logger.info("Reading command line arguments");
		String argument1 = args[0].trim();
		String argument2 = args[1].trim();
		if (argument1 != null && argument1.length() > 5 && argument2 != null && argument2.length() > 5) {
			logger.info("1st Argument will used as an Excel File Path with value as " + argument1);
			logger.info("2nd Argument will used as a Sheet Name with value as " + argument2);
			GenerateTestNGXml gtx = new GenerateTestNGXml();
			gtx.CreateTestNGXml(argument1, argument2);
			System.out.println("Test Results:");
			CustomReport cr = new CustomReport();
			cr.getTestResults(gtx.getSuiteMap());

		} else {
			logger.error("Please pass both the arguments correctly.");
		}
	}
}
