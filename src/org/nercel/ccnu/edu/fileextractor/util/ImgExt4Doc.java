package org.nercel.ccnu.edu.fileextractor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;



public class ImgExt4Doc {

	public void readWord(String docFile,String imgStoreDir) throws Exception{
		File f = new File(docFile); 
		InputStream in =new FileInputStream(f);
		HWPFDocument msWord=new HWPFDocument(in);
		String text="";
		PicturesTable pTable = msWord.getPicturesTable();
		int numCharacterRuns = msWord.getRange().numCharacterRuns();
		int underlinecode = 0;
		int imgcount = 0;
		OutputStream out = null;
		int startOffset=0,endOffset;
		int enterCount=0;
		for (int i = 0; i < numCharacterRuns; i++) {
			CharacterRun characterRun = msWord.getRange().getCharacterRun(i);
			startOffset= characterRun.getStartOffset();
			characterRun.getUnderlineCode();
			endOffset= characterRun.getEndOffset();
			for(int m =startOffset;m<endOffset;m++){
				Range range = new Range(m,m+1,msWord); 
				int hashCode=range.text().hashCode();
				// System.out.println(range.text()+"--"+hashCode);

				CharacterRun cr=range.getCharacterRun(0); 
				underlinecode = cr.getUnderlineCode();
				if (underlinecode != 0 && range.text() != null) {

					text = text +"_";
				}
				if(m<endOffset&&(hashCode == 13||hashCode==7))
				{
					enterCount++;
				}
				text=text + range.text();

			}
			if (pTable.hasPicture(characterRun)) {
				//是图片则把图片位置替换为image标签，再把图片输出到指定位置
				Picture pic = pTable.extractPicture(characterRun, true);
				String fileName = pic.suggestFullFileName();
				byte[] content=pic.getContent();
				out=new FileOutputStream(new File(imgStoreDir + File.separator + fileName));
				out.write(content);
				out.flush();
				out.close();
				text = text + "<image src='" + fileName + "'>";
				imgcount++;
			} 

		}

		in.close();
		System.out.println(text);
	}

	
	public static void main(String[] args)  throws Exception{
		ImgExt4Doc pt1= new ImgExt4Doc();
		pt1.readWord("E:\\test\\1 选择单题.doc","E:\\test\\imgOutput\\");

	}
}
