package api_Lib;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import report.ExtentReportInitialization;
import utility.GenericFunctions;

public class APIRequest {

	RequestSpecification request;

	Logger logger = LoggerFactory.getLogger(APIRequest.class);

	public APIRequest() {
		this.request = RestAssured.given();
	}

	public void setBaseURI(String strURI) {
		// Setting Base URI
		request.baseUri(strURI);
	}

	public void setBasePath(String StrBasePath, String pathParams) {
		// Setting Base Path
		request.basePath(StrBasePath).pathParams(GenericFunctions.getMapFromString(pathParams));
	}

	public void setBasePath(String StrBasePath) {
		// Setting Base Path
		request.basePath(StrBasePath);
	}

	public void setHeaders(String headers) {
		Map<String, Object> headersMap = GenericFunctions.getMapFromString(headers);
		ExtentReportInitialization.test.log(LogStatus.INFO, "Set Headers", "Setting up Header as: " + headersMap);
		request.headers(headersMap);
		logger.info("Step: Setting up headers: " + headersMap);
		headersMap = null;
	}

	public void setCookies(String Cookies) {
		Map<String, Object> cookiesMap = GenericFunctions.getMapFromString(Cookies);
		ExtentReportInitialization.test.log(LogStatus.INFO, "Set Cookies", "Setting up cookies as: " + cookiesMap);
		request.cookies(cookiesMap);
		logger.info("Step: Setting up cookies: " + cookiesMap);
		cookiesMap = null;
	}

	public void setQueryParameters(String queryParams) {
		Map<String, Object> queryParameterMap = GenericFunctions.getMapFromString(queryParams);
		ExtentReportInitialization.test.log(LogStatus.INFO, "Set Query Params",
				"Setting up query Params as: " + queryParameterMap);
		request.queryParams(queryParameterMap);
		logger.info("Step: Setting up query Parameters: " + queryParameterMap);
		queryParameterMap = null;
	}

	public void setBody(String strBody) {
		request.body(strBody);
		logger.info("Step: Setting up body: " + strBody);
	}
}
