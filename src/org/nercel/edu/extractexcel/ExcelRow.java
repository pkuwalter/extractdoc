package org.nercel.edu.extractexcel;

import java.util.List;

public class ExcelRow {
	
	private short height;
	private int rowNum;
	private List<ExcelCell> excelCell;

	public List<ExcelCell> getExcelCell() {
		return excelCell;
	}

	public void setExcelCell(List<ExcelCell> excelCell) {
		this.excelCell = excelCell;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}
}

