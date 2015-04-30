package org.nercel.ccnu.edu.fileextractor.extractor.doc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.nercel.ccnu.edu.fileextractor.extractor.DataHelper;
import org.nercel.ccnu.edu.fileextractor.model.doc.DocContent;

public class DOCExtractor implements DataHelper{

	private DocContent docContent;
	private FileInputStream fileInputStream;
	private List<Picture> pictsList = null;
	HWPFDocument document;
	Range range ;
	private String imagePath;
	// 用来标记是否存在图片
	boolean hasPic = false;


	public void setContent(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
		doExtract();
	}
	public InputStream getFileInputStream(){
		return this.fileInputStream;
	}

	public DocContent getContent() {
		return docContent;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePath() {
		return this.imagePath;
	}

	public boolean extractPicture(String imagePath) {
		this.imagePath = imagePath;
		return extractPictures();
	}


	/**
	 * 抽取Word文档中的内容存入数据库，此处有几个问题需要解决
	 * 1.试题中包含图片的应该如何分别存？
	 * 2.学科概念部分的内容如何存入到对应的表中？
	 * 3.选择中的“单选”、“多选”对应哪个字段？
	 */
	public DocContent doExtract() {
		docContent = new DocContent();

		try {
			document = new HWPFDocument(getFileInputStream());

			range = document.getRange();// 得到文档的读取范围

			//抽取文档中的图片并存储到指定位置
			extractPictures();
			//			if(numPictures()>0)
			//				writePictures(imagePath);

			TableIterator tableIterator = new TableIterator(range);
			// 迭代文档中的表格
			while (tableIterator.hasNext()) {
				Table table = (Table) tableIterator.next();
				for (int i = 0; i < table.numRows(); i++) {
					TableRow tr = table.getRow(i);
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);// 取得单元格
						// 取得单元格的内容
						String s = "";
						Paragraph para = null;
						for (int k = 0; k < td.numParagraphs(); k++) {
							para = td.getParagraph(k);
							s = para.text().trim();
						} 
						//如果某个单元格的内容为标注内容，则将下一个与之对应的单元格内容写入数据库
						if(s.contains("题型")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setQuestionType(s);
						}
						if(s.contains("题库类型")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setQuestionDBType(s);
						}
						if(s.contains("题库年度")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setQuestionDBYear(s);
						}
						if(s.contains("适用年级")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setApplicableClassId(s);
						}
						if(s.contains("能力指标")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setSkill(s);
						}
						if(s.contains("预估解题时间")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setEstimatedTime(s);
						}
						if(s.contains("常考题")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s = para.text().trim();
							}
							System.out.println("changkaoti:	" + s);
							int ofenSet = Integer.parseInt(s);
							docContent.setIsOfenTest(ofenSet);
						}
						if(s.contains("版型")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setModelType(s);
						}
						//需要处理题目中的图片
						if(s.contains("题目文字")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setQuestionStem(s);
						}
						if(s.contains("题目图片")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setImagePath(s);
						}
						if(s.contains("详解")){//如何判断此处是否有图片？
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setExplainImgUri(s);
						}
						if(s.contains("打印版内容")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setPrinter(s);
						}
						if(s.contains("试题备注")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setNote(s);
						}
						if(s.contains("命题者")){
							td = tr.getCell(j + 1);
							s = "";
							for (int k = 0; k < td.numParagraphs(); k++) {
								para = td.getParagraph(k);
								s += para.text().trim();
							}
							docContent.setAuthor(s);
						}
					} 
				}
			}
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return docContent;
	}

	public boolean extractPictures() {
		pictsList = new ArrayList<Picture>();

		// 得到文档的数据流		
		byte[] dataStream = document.getDataStream();
		int numChar = range.numCharacterRuns();

		PicturesTable pTable = new PicturesTable(document, dataStream, dataStream);
		for (int j = 0; j < numChar; ++j) {
			CharacterRun cRun = range.getCharacterRun(j);
			// 是否有图片
			boolean has = pTable.hasPicture(cRun);
			if (has) {
				Picture pic = pTable.extractPicture(cRun, true);
				pictsList.add(pic);
				System.out.println();
				range.replaceText("EMBED Equation.DSMT4", "${pic_" + pic.suggestFullFileName() + "}$");
				hasPic = true;
			}
		}


		return hasPic;
	}

	/**
	 * word文档里有几张图片，使用这个函数之前，
	 * 必须先使用函数 extractPictures()
	 * @return
	 */
	public int numPictures() {
		if (!hasPic)
			return 0;
		return pictsList.size();
	}

	/**
	 * 把提取的图片保存到用户指定的位置
	 * @param picNames， 图片要保存的路径,最好完整地写上图片类型
	 * @return
	 */
	//	public boolean writePictures(String imagePath) {
	//		int size = pictsList.size();
	//		if (size == 0)
	//			return false;
	//
	//		for (int i = 0; i < size; ++i) {
	//			Picture p = (Picture) pictsList.get(i);
	//			try {
	//				FileOutputStream output = new FileOutputStream(new File(imagePath + i + "." + p.suggestFileExtension()));
	//				output.write( p.getContent() );
	//				output.close(); 
	//			} catch (FileNotFoundException e) {
	//				e.printStackTrace();
	//			} catch (IOException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//		return true;
	//	}
}
