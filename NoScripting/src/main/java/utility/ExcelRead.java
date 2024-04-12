package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelRead {

	Logger logger = LoggerFactory.getLogger(ExcelRead.class);

	public String strError = "";

	private static String strMainSheetPath = "";

	public void setMainSheetPath(String strPath) {
		strMainSheetPath = strPath;
	}

	public Object[][] getSheetData(String strSheetName) {
		Object[][] obj = null;
		try {
			logger.info("Reading the Excel Sheet named as " + strSheetName + " at given path " + strMainSheetPath);
			File file = new File(strMainSheetPath);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheet(strSheetName);
			int rowCount = sh.getLastRowNum();
			int colCount = sh.getRow(0).getLastCellNum();
			obj = new Object[rowCount][1];
			for (int i = 0; i < rowCount; i++) {
				HashMap<Object, Object> dataMap = new HashMap<>();
				for (int j = 0; j < colCount; j++) {
					dataMap.put(sh.getRow(0).getCell(j).toString(), sh.getRow(i + 1).getCell(j).toString());
				}
				obj[i][0] = dataMap;
			}
			logger.info("Excel Sheet named as " + strSheetName + " readed successfully.");
		} catch (Exception ex) {
			obj = null;
			strError = "Getting error in execl sheet: " + strSheetName + " at path: " + strMainSheetPath
					+ "More Details: " + ex.getMessage();
			logger.error(strError);
		}

		return obj;
	}

	public HashMap<String, String> readExcelSheet(String strpath, String strSheetName) {
		HashMap<String, String> suitesData = new HashMap<String, String>();
		try {
			logger.info("Reading the Excel Sheet named as " + strSheetName + " at given path " + strMainSheetPath);
			File file = new File(strpath);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheet(strSheetName);
			int rowCount = sh.getLastRowNum();
			for (int i = 1; i <= rowCount; i++) {
				suitesData.put(sh.getRow(i).getCell(0).toString(), sh.getRow(i).getCell(1).toString());
			}
		} catch (Exception ex) {
			suitesData = null;
			strError = "Getting error in excel sheet: " + strSheetName + " at path: " + strpath + "More Details: "
					+ ex.getMessage();
			logger.error(strError);
		}

		logger.info("Excel Sheet named as " + strSheetName + " readed successfully.");
		return suitesData;
	}

	public String getErrorDetail() {
		return strError;
	}

}
