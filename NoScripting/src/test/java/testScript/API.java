package testScript;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import api_Lib.APIRequest;
import api_Lib.APIResponse;
import report.ExtentReportInitialization;
import utility.ExcelRead;
import utility.GenericFunctions;

public class API {

	Logger logger = LoggerFactory.getLogger(API.class);
	String strSuiteName = "";
	HashMap<String, String> suiteTestCasesMap = new HashMap<String, String>();

	@BeforeSuite
	public void reporting(ITestContext ctx) {
		strSuiteName = ctx.getCurrentXmlTest().getSuite().getName();
		ExtentReportInitialization.StartReporting(strSuiteName);
		logger.info("Start Executing Suite : " + strSuiteName);
	}

	@Test(dataProvider = "getInput")
	public void runAPITest(Map<Object, Object> map) {
		try {
			String strTestCaseName = map.get("TestCaseName").toString();
			String strDependableTest = map.get("DependOnTest").toString();
			ExtentTest test = null;
			test = ExtentReportInitialization.startTest(strTestCaseName);

			boolean bVerify = false;
			if (strDependableTest.equalsIgnoreCase("NA")) {
				bVerify = true;
			} else {
				if (suiteTestCasesMap.get(strDependableTest).equalsIgnoreCase("Failed")
						|| suiteTestCasesMap.get(strDependableTest).equalsIgnoreCase("Skipped")) {
					suiteTestCasesMap.put(strTestCaseName, "Skipped");
					bVerify = false;
					test.log(LogStatus.SKIP, strTestCaseName + " got skipped.",
							"Executing of the Test Case:" + strTestCaseName + "got skipped as Dependent Test Case: "
									+ strDependableTest + " got failed or skipped.");
					logger.info("Executing of the Test Case:" + strTestCaseName
							+ " got skipped as Dependent test case got failed or skipped.");
					ExtentReportInitialization.EndTest();
					throw new SkipException("Skipping this exception");
				} else {
					bVerify = true;
				}
			}

			if (bVerify) {
				logger.info("Executing Start for the TestCase :" + strTestCaseName);
				try {
					String strRandomVariable = map.get("RandomValues").toString();
					String strRequestType = map.get("RequestType").toString();
					String strBaseURI = map.get("BaseURI").toString();
					String strBasePath = map.get("BasePath").toString();
					String strRequestHeaders = map.get("RequestHeaders").toString();
					String strRequestCookies = map.get("RequestCookies").toString();
					String strQueryParameters = map.get("QueryParameters").toString();
					String strPathParameters = map.get("PathParameters").toString();
					String strRequestBody = map.get("RequestBody").toString();
					String strResponseType = map.get("ResponseType").toString();
					String strResponseCookiesToBeSaved = map.get("ResponseCookiesToBeSaved").toString();
					String strResponseFieldToBeSaved = map.get("ResponseFieldToBeSaved").toString();
					String strResponseCode = map.get("ResponseCode").toString();

					APIRequest api = new APIRequest();
					if (!strRandomVariable.equalsIgnoreCase("NA")) {
						logger.info("Generating random string value.");
						GenericFunctions.setRandomStringValue(strRandomVariable);
					}

					test.log(LogStatus.INFO, "Set Base URI", "Set Base URI as " + strBaseURI);
					logger.info("Step: Setting up base URI: " + strBaseURI);
					api.setBaseURI(strBaseURI);

					if (strPathParameters.equalsIgnoreCase("NA")) {
						test.log(LogStatus.INFO, "Set Base Path", "Set Base Path as " + strBasePath);
						logger.info("Step: Setting up base Path: " + strBasePath);
						api.setBasePath(strBasePath);
					} else {
						api.setBasePath(strBasePath, strPathParameters);
						logger.info("Step: Setting up base Path: " + strBasePath + " with path parameters as "
								+ strPathParameters);
						test.log(LogStatus.INFO, "Set Base Path", "Set Base Path as " + strBasePath
								+ " with path Parameter values as " + strPathParameters);
					}

					if (!strRequestCookies.equalsIgnoreCase("NA")) {
						api.setCookies(strRequestCookies);
					}

					if (!strRequestHeaders.equalsIgnoreCase("NA")) {
						api.setHeaders(strRequestHeaders);
					}

					if (!strQueryParameters.equalsIgnoreCase("NA")) {
						api.setQueryParameters(strQueryParameters);
					}

					if (!strRequestBody.equalsIgnoreCase("NA")) {
						api.setBody(strRequestBody);
						test.log(LogStatus.INFO, "Set Request Body", "Set Request Body as " + strRequestBody);
					}

					APIResponse apiResponse = new APIResponse(api, strRequestType);

					int ActualResponseCode = apiResponse.getResponseStatusCode();

					boolean flag = Integer.parseInt(strResponseCode) == ActualResponseCode;
					if (flag) {
						Assert.assertTrue(flag);
						test.log(LogStatus.PASS, "Request is successful with ", "response code: " + ActualResponseCode);
						logger.info("Request is successful with response code: " + ActualResponseCode);
						if (!strResponseCookiesToBeSaved.equalsIgnoreCase("NA")) {
							apiResponse.saveCookies(strResponseCookiesToBeSaved);
						}

						if (strResponseType.equalsIgnoreCase("HTML")) {

							if (!strResponseFieldToBeSaved.equalsIgnoreCase("NA")) {
								apiResponse.saveHTMLResponseBodyField(strResponseFieldToBeSaved);
							}

						} else if (strResponseType.equalsIgnoreCase("JSON")) {

						}

					} else {
						test.log(LogStatus.FAIL, "Request is unsuccessful with ",
								"response code: " + ActualResponseCode);
						logger.error("Request is unsuccessful with response code: " + ActualResponseCode);
						ExtentReportInitialization.EndTest();
						logger.error("Executing completed with error for the Test Case:" + strTestCaseName);
						suiteTestCasesMap.put(strTestCaseName, "Failed");
						Assert.assertTrue(flag);
					}
				} catch (Exception ex) {
					ExtentReportInitialization.EndTest();
					logger.error("Executing completed with error for the Test Case:" + strTestCaseName);
					test.log(LogStatus.ERROR, ex.getMessage());
					suiteTestCasesMap.put(strTestCaseName, "Failed");
					Assert.assertTrue(false);
				}
				ExtentReportInitialization.EndTest();
				logger.info("Executing Completed for the Test Case:" + strTestCaseName);
				suiteTestCasesMap.put(strTestCaseName, "Passed");
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@DataProvider
	public Object[][] getInput() {
		ExcelRead er = new ExcelRead();
		return er.getSheetData(strSuiteName);
	}

	@AfterMethod()
	public void afterMethod() {
		GenericFunctions.storeTestResults(strSuiteName, suiteTestCasesMap);
	}

	@AfterSuite
	public void afterSuite() {
		ExtentReportInitialization.flushReport();
	}
}
