package utility;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class GenericFunctions {

	public static Map<String, Object> globalMap = null;

	public static Map<String, HashMap<String, String>> mapTestResults = new HashMap<>();

	public static Map<String, Object> getMapFromString(String strValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] strPairs = strValue.split(";");
		for (String pair : strPairs) {
			String Key = pair.split("=")[0].trim();
			String Value = pair.split("=")[1].trim();
//			if (Value.equalsIgnoreCase("$")) {
//				Value = (String) getGlobalValue(Key);
//			} else 
			if (Value.charAt(0) == ('$') && Value.charAt(1) == ('$')) {
				Value = (String) getGlobalValue(Value.replace("$$", ""));
			}
			map.put(Key, Value);
		}

		return map;
	}

	public static void setRandomStringValue(String strVariables) {
		String[] arrVariables = strVariables.split(";");
		for (String variable : arrVariables) {
			Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
			GenericFunctions.storeGlobalValue(variable, timestamp1.getTime() + "random");
		}
	}

	public static void storeGlobalValue(String strKey, Object oValue) {
		if (globalMap == null) {
			globalMap = new HashMap<String, Object>();
		}
		globalMap.put(strKey, oValue);
	}

	public static Object getGlobalValue(String strValue) {
		return globalMap.get(strValue);
	}

	public static void storeTestResults(String strKey, HashMap<String, String> hm) {
		mapTestResults.put(strKey, hm);
	}

	public static HashMap<String, String> getTestResults(String strSuiteName) {
		return mapTestResults.get(strSuiteName);
	}

}
