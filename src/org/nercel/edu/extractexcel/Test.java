package org.nercel.edu.extractexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class Test {
	public static void main(String[] args){
		ExcelCenter excelCenter = new ExcelCenter();
		String excelPath = "./resources/11.xls";
		HSSFWorkbook workBook = excelCenter.readExcel(excelPath);
		Sheet sheet = workBook.getSheetAt(0);
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		System.out.println(cell.getStringCellValue());
	}
}