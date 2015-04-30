package org.nercel.edu.extractexcel;


import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelCell {

	/*以下是cell类型的代码
public static final int CELL_TYPE_NUMERIC = 0;
public static final int CELL_TYPE_STRING = 1;
public static final int CELL_TYPE_FORMULA = 2;
public static final int CELL_TYPE_BLANK = 3; 
public static final int CELL_TYPE_BOOLEAN = 4;
public static final int CELL_TYPE_ERROR = 5;
	 */

	private short cellNum;
	private short width;
	private short cellFrom;
	private short cellTo;
	private String value;
	private String formula;
	private int type=HSSFCell.CELL_TYPE_BLANK;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public short getCellNum() {
		return cellNum;
	}
	public void setCellNum(short cellNum) {
		this.cellNum = cellNum;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public short getWidth() {
		return width;
	}
	public void setWidth(short width) {
		this.width = width;
	}
	public short getCellFrom() {
		return cellFrom;
	}
	public void setCellFrom(short cellFrom) {
		this.cellFrom = cellFrom;
	}
	public short getCellTo() {
		return cellTo;
	}
	public void setCellTo(short cellTo) {
		this.cellTo = cellTo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}