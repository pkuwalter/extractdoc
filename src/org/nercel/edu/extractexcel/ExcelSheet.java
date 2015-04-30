package org.nercel.edu.extractexcel;

import java.util.List;

public class ExcelSheet {
	
	private String sheetName;
	
	private int sheetNum;
	
	private List<ExcelMergion> excelMergion;
	
	private List<ExcelRow> excelRow;

	public List<ExcelRow> getExcelRow() {
		return excelRow;
	}

	public void setExcelRow(List<ExcelRow> excelRow) {
		this.excelRow = excelRow;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<ExcelMergion> getExcelMergion() {
		return excelMergion;
	}

	public void setExcelMergion(List<ExcelMergion> excelMergion) {
		this.excelMergion = excelMergion;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}
}