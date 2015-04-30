package org.nercel.edu.extractexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.util.Region; 

@SuppressWarnings("deprecation")
public class ExcelCenter {

	// 设置cell是否返回公式. false:返回值 true:返回公式
	private boolean returnFormula = false;
	Logger logger = Logger.getLogger(ExcelCenter.class);

	/**
	 * 读取模板 read the template
	 * 
	 * @param templatePath  the template's Path
	 * @return HSSFWorkbook return HSSFWorkbook's variables
	 */
	public HSSFWorkbook readExcel(String excelPath) {
		HSSFWorkbook result = null;
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					excelPath));
			result = new HSSFWorkbook(fs);
		} catch (Exception ex) {
			logger.info("读取Excel失败");
			logger.error(ex);
		}
		return result;
	}

	/**
	 * 保存Excel文件
	 * 
	 * @param excelPath
	 * @param workBook
	 * @return
	 */
	public boolean saveExcel(String excelPath, HSSFWorkbook workBook) {
		boolean result = false;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(excelPath);
			workBook.write(fileOut);
			result = true;
		} catch (Exception e) {
		}finally{
			try {
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				result=false;
			}
		}
		return result;
	}

	/**
	 * 读取指定文件并通过取得execelWorkBook保存文件
	 * 
	 * @param excelPath
	 * @param excelWorkBook
	 * @return
	 */
	public boolean saveExcel(String excelPath, ExcelWorkBook excelWorkBook) {
		boolean result = false;
		HSSFWorkbook workBook = readExcel(excelPath);

		try {
			for (ExcelSheet excelSheet : excelWorkBook.getExcelSheet()) {
				try {
					HSSFSheet sheet = workBook.getSheetAt(excelSheet
							.getSheetNum());
					try {
						for (ExcelRow excelRow : excelSheet.getExcelRow()) {
							HSSFRow row = sheet.getRow(excelRow.getRowNum());
							try {
								for (ExcelCell excelCell : excelRow
										.getExcelCell()) {
									HSSFCell cell = row.getCell(excelCell
											.getCellNum());
									if (cell != null) {
										setCellValue(cell, excelCell.getValue());
									}
								}
							} catch (Exception e) {
								continue;
							}
						}
					} catch (Exception e) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
			}
			result = saveExcel(excelPath, workBook);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return result;
	}

	/**
	 * HSSFWorkbook 转换ExcelWorkBook
	 * 
	 * @param workBook
	 * @return
	 */
	public ExcelWorkBook toExcelWorkBook(HSSFWorkbook workBook) {
		ExcelWorkBook excelWorkBook = new ExcelWorkBook();
		List<ExcelSheet> excelSheetList = new ArrayList<ExcelSheet>();

		try {
			int numberOfSheets = workBook.getNumberOfSheets();
			for (int i = 0; i <= numberOfSheets - 1; i++) {
				String tempSheetName = workBook.getSheetName(i);
				HSSFSheet tempSheet = workBook.getSheetAt(i);
				ExcelSheet excelSheet = toExcelSheet(workBook, tempSheet,
						tempSheetName);
				excelSheet.setSheetName(workBook.getSheetName(i));
				excelSheet.setSheetNum(i);
				excelSheetList.add(excelSheet);
			}
		} catch (Exception e) {
			logger.info("Excel文本读取失败");
			logger.error(e);
			return null;
		}
		excelWorkBook.setExcelSheet(excelSheetList);
		return excelWorkBook;
	}

	/**
	 * HSSFSheet 转换ExcelSheet
	 * 
	 * @param sheet
	 * @return
	 */
	public ExcelSheet toExcelSheet(HSSFWorkbook workbook, HSSFSheet sheet,
			String sheetName) {
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName(sheetName);
		List<ExcelRow> excelRowList = new ArrayList<ExcelRow>();

		for (int i = firstRowNum; i <= lastRowNum; i++) {
			HSSFRow tempRow = sheet.getRow(i);
			ExcelRow row;
			try {
				row = toExcelRow(workbook, sheet, tempRow);
			} catch (Exception e) {
				row = new ExcelRow();
			}
			try {
				for (int j = tempRow.getFirstCellNum(); j < tempRow
						.getLastCellNum(); j++) {
					for (ExcelCell excelCell : row.getExcelCell()) {
						short pxWidth = getCellWidthPx(sheet
								.getColumnWidth(excelCell.getCellNum()));
						excelCell.setWidth(pxWidth);
					}
				}
			} catch (Exception e) {
			}
			try {
				excelSheet.setExcelMergion(toExcelMergion(sheet));
			} catch (Exception e) {
			}
			excelRowList.add(row);
		}

		excelSheet.setExcelRow(excelRowList);
		return excelSheet;
	}

	/**
	 * HSSFRow 转换ExcelRow
	 * 
	 * @param row
	 * @return
	 */
	public ExcelRow toExcelRow(HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFRow row) {
		ExcelRow excelRow = new ExcelRow();
		int firstCellNum;
		int lastCellNum;
		int currNum = 0;
		List<ExcelCell> excelCellList = new ArrayList<ExcelCell>();

		firstCellNum = row.getFirstCellNum();
		lastCellNum = row.getLastCellNum();
		for (currNum = firstCellNum; currNum < lastCellNum; currNum++) {
			try {
				HSSFCell tempCell = row.getCell((short) currNum);
				ExcelCell excelCell = toExcelCell(workbook, sheet, row,
						tempCell);
				excelCellList.add(excelCell);
			} catch (NullPointerException e) {
				ExcelCell excelCell = new ExcelCell();
				excelCell.setCellNum((short) currNum);
				excelCell.setValue("");
				excelCellList.add(excelCell);
				excelRow.setRowNum(row.getRowNum());
				excelRow.setExcelCell(excelCellList);
				logger.info("这是空行");
			}
		}
		short pxHeight = getRowHeightPx(row.getHeight());
		excelRow.setHeight(pxHeight);
		excelRow.setRowNum(row.getRowNum());
		excelRow.setExcelCell(excelCellList);
		return excelRow;
	}

	/**
	 * HSSFCell 转换ExcelCell
	 * 
	 * @param cell
	 * @return
	 */
	public ExcelCell toExcelCell(HSSFWorkbook workbook, HSSFSheet sheet,
			HSSFRow row, HSSFCell cell) {
		ExcelCell excelCell = new ExcelCell();
		try {
			String cellValue = getCellValue(workbook, sheet, row, cell);
			short cellNum = cell.getCellNum();
			excelCell.setCellNum(cellNum);
			excelCell.setValue(cellValue);
		} catch (NullPointerException e) {
			excelCell.setCellNum(cell.getCellNum());
			excelCell.setValue("");
		} catch (Exception e) {
			logger.error(e);
		}
		return excelCell;
	}

	/**
	 * 转换合并表格
	 * 
	 * @param sheet
	 * @return
	 */
	public List<ExcelMergion> toExcelMergion(HSSFSheet sheet) {
		List<ExcelMergion> mergionList = new ArrayList<ExcelMergion>();

		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			ExcelMergion excelMergion = new ExcelMergion();

			Region region = sheet.getMergedRegionAt(i);
			int cellForm = region.getColumnFrom();
			int cellTo = region.getColumnTo();
			int rowFrom = region.getRowFrom();
			int rowTo = region.getRowTo();

			excelMergion.setCellForm(cellForm);
			excelMergion.setCellTo(cellTo);
			excelMergion.setRowFrom(rowFrom);
			excelMergion.setRowTo(rowTo);

			mergionList.add(excelMergion);
		}
		return mergionList;
	}

	/**
	 * 复制Excel文件.当文件已存在则不生成
	 * 
	 * @param templatePath
	 * @param targetPath
	 * @return
	 */
	public boolean cloneExcel(String templatePath, String targetPath) {
		boolean result = false;
		try {
			HSSFWorkbook templateWorkBook = readExcel(templatePath);
			if (notExistExcel(targetPath)) {
				result=saveExcel(targetPath,templateWorkBook);
			} else {
				logger.info("复制Excel文件失败,文件已存在");
			}
		} catch (Exception e) {
			logger.info("复制Excel文件失败");
			logger.error(e);
		} 
		return result;
	}

	/**
	 * 读取指定excel
	 * 
	 * @param response
	 * @param excelPath
	 */
	public void ExcelToHtml(HttpServletResponse response, String excelPath) {
		try {
			HSSFWorkbook workbook = readExcel(excelPath);
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			OutputStream filerOut = (OutputStream) response.getOutputStream();
			workbook.write(filerOut);
			response.flushBuffer();
			filerOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void ExcelToHtml(HttpServletResponse response, String excelPath,
			String sheetName) {
		try {
			HSSFWorkbook workbook = readExcel(excelPath);
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			OutputStream filerOut = (OutputStream) response.getOutputStream();
			workbook.write(filerOut);
			response.flushBuffer();
			filerOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void ExcelToHtml(HttpServletResponse response, String excelPath,
			int sheetIndex) {
		try {
			HSSFWorkbook workbook = getOnlySheetFromExcel(excelPath, sheetIndex);
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			OutputStream filerOut = (OutputStream) response.getOutputStream();
			workbook.write(filerOut);
			response.flushBuffer();
			filerOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 只显示指定的sheet，其他隐藏
	 * @param excelPath
	 * @param sheetIndex
	 * @return
	 */
	public HSSFWorkbook getOnlySheetFromExcel(String excelPath,
			int sheetIndex) {
		HSSFWorkbook workBook;
		try {
			workBook = readExcel(excelPath);
			String sheetName = workBook.getSheetName(sheetIndex);
			workBook.setSelectedTab((short) sheetIndex);
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				if (!workBook.getSheetName(i).equals(sheetName)) {
					workBook.setSheetHidden(i, true);
				}
			}
		} catch (Exception e) {
			workBook = new HSSFWorkbook();
		}
		return workBook;
	}
	public HSSFWorkbook getOnlySheetFromExcel(String excelPath,
			String sheetName) {
		HSSFWorkbook workBook;
		try {
			workBook = readExcel(excelPath);
			int sheetIndex=workBook.getSheetIndex(sheetName);
			workBook.setSelectedTab((short) sheetIndex);
			for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
				if (!workBook.getSheetName(i).equals(sheetName)) {
					workBook.setSheetHidden(i, true);
				}
			}
		} catch (Exception e) {
			workBook = new HSSFWorkbook();
		}
		return workBook;
	}

	/**
	 * 判断Excel文件是否已存在
	 * 
	 * @param targetPath
	 * @return
	 */
	public boolean isExistExcel(String excelPath) {
		boolean result = false;
		File file = null;
		try {
			file = new File(excelPath);
			result = file.exists();
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}

	public boolean notExistExcel(String excelPath) {
		return !isExistExcel(excelPath);
	}

	/**
	 * 判断Sheet是否已存在
	 * 
	 * @param workBook
	 * @param sheetName
	 * @return
	 */
	public boolean isExistSheet(HSSFWorkbook workBook, String sheetName) {
		boolean result = false;
		try {
			HSSFSheet sheet = workBook.getSheet(sheetName);
			sheet.getLastRowNum();
			result = true;
		} catch (Exception e) {
			logger.info("工作表不存在");
			logger.error(e);
		}
		return result;
	}

	public boolean notExistSheet(HSSFWorkbook workBook, String sheetName) {
		return !isExistSheet(workBook, sheetName);
	}

	// 按位置判断Sheet是否存在
	public boolean isExistSheet(HSSFWorkbook workBook, int sheetAt) {
		boolean result = false;
		try {
			HSSFSheet sheet = workBook.getSheetAt(sheetAt);
			sheet.getLastRowNum();
			result = true;
		} catch (Exception e) {
			logger.info("工作表不存在");
			logger.error(e);
		}
		return result;
	}

	public boolean notExistSheet(HSSFWorkbook workBook, int sheetAt) {
		return !isExistSheet(workBook, sheetAt);
	}

	/**
	 * 取得列中的值
	 * 
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public String getCellValue(HSSFWorkbook workBook, HSSFSheet sheet,
			HSSFRow row, HSSFCell cell) throws Exception {

		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(sheet,
				workBook);
		evaluator.setCurrentRow(row);

		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			DecimalFormat formatter = new DecimalFormat(
					"########");
			cellValue = formatter.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			switch (evaluator.evaluateFormulaCell(cell)) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (returnFormula) {
					cellValue = cell.getCellFormula();
				} else {
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
				break;

			}
			break;
		default:
			cellValue = "error";
			break;
		}
		return cellValue;
	}

	/**
	 * 
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	public void setCellValue(HSSFCell cell, String value) throws Exception {
		try {
			int intValue = Integer.parseInt(value);
			cell.setCellValue(intValue);
		} catch (Exception e) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_BLANK:
				HSSFRichTextString blankString = new HSSFRichTextString(value);
				cell.setCellValue(blankString);
				break;
			case HSSFCell.CELL_TYPE_STRING:
				HSSFRichTextString textString = new HSSFRichTextString(value);
				cell.setCellValue(textString);
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				HSSFRichTextString booleanString = new HSSFRichTextString(value);
				cell.setCellValue(booleanString);
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cell.setCellFormula(cell.getCellFormula());
				break;
			}
		}
	}

	// 把excel宽度换算为px
	public short getCellWidthPx(short originalWidth) {
		return (short) (originalWidth / 34.7);
	}

	// 把excel高度换算为px
	public short getRowHeightPx(short originaHeight) {
		return (short) (originaHeight / 14.625);
	}

	public boolean isReturnFormula() {
		return returnFormula;
	}

	public void setReturnFormula(boolean returnFormula) {
		this.returnFormula = returnFormula;
	}

}

