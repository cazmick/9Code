package report;

import java.util.HashMap;

import utility.GenericFunctions;

public class CustomReport {

	public void getTestResults(HashMap<String, String> testResults) {

		for (String suiteName : testResults.keySet()) {
			if (testResults.get(suiteName).equalsIgnoreCase("Yes")) {
				System.out.println(GenericFunctions.getTestResults(suiteName));
			}
		}

	}

}
