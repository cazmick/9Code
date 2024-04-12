package api_Lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import report.ExtentReportInitialization;
import utility.GenericFunctions;
import io.restassured.module.jsv.JsonSchemaValidator;

public class APIResponse {

	Logger logger = LoggerFactory.getLogger(APIResponse.class);
	Response response = null;

	public APIResponse(APIRequest api, String strOperation) {
		try {
			if (strOperation.equalsIgnoreCase("GET")) {
				this.response = api.request.get();
			} else if (strOperation.equalsIgnoreCase("POST")) {
				this.response = api.request.post();
			} else if (strOperation.equalsIgnoreCase("PUT")) {
				this.response = api.request.put();
			} else if (strOperation.equalsIgnoreCase("DELETE")) {
				this.response = api.request.delete();
			}
			ExtentReportInitialization.test.log(LogStatus.INFO, "Send Request", "Send " + strOperation + " request");
			logger.info(strOperation + " request hits.");
		} catch (Exception ex) {
			ExtentReportInitialization.test.log(LogStatus.ERROR, "Send Request",
					"Send " + strOperation + " request, getting error as: " + ex.getMessage());
			logger.error("On hitting " + strOperation + " request, error occurs: " + ex.getMessage());
		}
	}

	public int getResponseStatusCode() {
		return response.getStatusCode();
	}

	public String getCookie(String strCookieName) {
		return response.getCookie(strCookieName);
	}

	public String getKeyValueFromHTMLResponse(String strKey) {
		return response.getBody().asString().split(strKey)[1].split("\"")[1];
	}

	public void saveCookies(String strCookies) {
		String str = "";
		try {
			String[] arrCookies = strCookies.split(";");
			for (String cookie : arrCookies) {
				String value = getCookie(cookie);
				GenericFunctions.storeGlobalValue(cookie, value);
				str = str + cookie + ":" + value;
			}
			ExtentReportInitialization.test.log(LogStatus.INFO, "Cookies Saved", str);
			logger.info("Cookies Saved: " + str);
		} catch (Exception ex) {
			ExtentReportInitialization.test.log(LogStatus.ERROR, ex.getMessage());
			logger.error("Cookies not saved, getting error as " + ex.getMessage());
		}
	}

	public void saveHTMLResponseBodyField(String strFields) {
		String str = "";
		try {
			String[] arrFields = strFields.split(";");
			for (String field : arrFields) {
				String value = getKeyValueFromHTMLResponse(field);
				GenericFunctions.storeGlobalValue(field, value);
				str = str + field + ":" + value;
			}
			ExtentReportInitialization.test.log(LogStatus.INFO, "Field Saved", str);
			logger.info("fields from response Body got saved: " + str);
		} catch (Exception ex) {
			ExtentReportInitialization.test.log(LogStatus.ERROR, ex.getMessage());
			logger.error("Field from response body not saved, getting error as " + ex.getMessage());
		}
	}
	


}
