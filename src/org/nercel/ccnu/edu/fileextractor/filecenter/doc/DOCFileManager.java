package org.nercel.ccnu.edu.fileextractor.filecenter.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.nercel.ccnu.edu.fileextractor.filecenter.FileHelper;


public class DOCFileManager implements FileHelper{

	private FileInputStream fis;

	public HWPFDocument getHWPFDocument(String filePath){
		File file = new File(filePath);
		HWPFDocument wordDocument = null;
		try {
			fis = new FileInputStream(file);
			wordDocument = new HWPFDocument(fis);
		}catch(FileNotFoundException e){
			e.printStackTrace();  
		}catch (IOException e) {  
			e.printStackTrace();  
		}   
		return wordDocument;
	}

	//获取文档的文字内容
	public FileInputStream getContent(String filePath) {
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fis;
	}

	public boolean extractPicture(String filePath, String imagePath){
		List<Picture> picList = new ArrayList<Picture>();

		File file = new File(filePath);
		HWPFDocument wordDocument = null;
		try {
			fis = new FileInputStream(file);
			wordDocument = new HWPFDocument(fis);
			PicturesTable picturesTable = wordDocument.getPicturesTable();
			picList = picturesTable.getAllPictures();
			int numCharacterRuns = wordDocument.getRange().numCharacterRuns();
			for (int i = 0; i < numCharacterRuns; i++) {
				CharacterRun characterRun = wordDocument.getRange().getCharacterRun(i);
				if (picturesTable.hasPicture(characterRun)) {
					Picture pic = picturesTable.extractPicture(characterRun, false);
					String fileName = pic.suggestFullFileName();
					OutputStream out;
					out = new FileOutputStream(new File(imagePath
							+ File.separator + fileName));
					pic.writeImageContent(out);
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();  
		}catch (IOException e) {  
			e.printStackTrace();  
		} 
		if(picList.size() > 0)
			return true;
		return false;
	}

	//获取文档的文字内容
	//		public String getWordText(String filePath) {
	//			HWPFDocument wordDocument = getHWPFDocument(filePath);
	//			StringBuffer stringBuffer=new StringBuffer();  
	//
	//			Range range = wordDocument.getRange();
	//			int paragraphCount = range.numParagraphs();
	//			for(int i=0; i<paragraphCount; i++)  
	//			{  
	//				Paragraph paragraph=range.getParagraph(i);  
	//				stringBuffer.append(paragraph.text());  
	//				System.out.println(stringBuffer.toString());  
	//			}  
	//			return stringBuffer.toString().trim();
	//		}

	//获取文档中的图片，并存储到storagePath目录

	//获取文档的段落数
	//	public int getParagraphCount(String filePath){
	//		HWPFDocument wordDocument = getHWPFDocument(filePath);
	//		Range range = wordDocument.getRange();
	//		int paragraphCount = range.numParagraphs();
	//		return paragraphCount;
	//	}
}
