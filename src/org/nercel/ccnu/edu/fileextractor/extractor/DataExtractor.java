package org.nercel.ccnu.edu.fileextractor.extractor;

import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

import org.nercel.ccnu.edu.fileextractor.extractor.doc.DOCExtractor;
import org.nercel.ccnu.edu.fileextractor.extractor.xls.XLSExtractor;
import org.nercel.ccnu.edu.fileextractor.filecenter.FileRecognizer;
import org.nercel.ccnu.edu.fileextractor.model.doc.DocContent;
import org.nercel.ccnu.edu.fileextractor.util.FileUtil;


public class DataExtractor implements Observer {

	private DataHelper helper;

	public DataExtractor(FileRecognizer recognizer) {
		recognizer.addObserver(this);
	}

	public void update(Observable subject, Object obj) {
		switch (((FileRecognizer) subject).getFileType()) {
			case FileUtil.FileType.XLS:
				if (!(helper instanceof XLSExtractor)) {
					helper = new XLSExtractor();
				}
				break;
			case FileUtil.FileType.DOC:
				if (!(helper instanceof DOCExtractor)) {
					helper = new DOCExtractor();
				}
				break;
		}
	}

	public void setContent(FileInputStream fileInputStream) {
		helper.setContent(fileInputStream);
	}
	
	public DocContent getContent(){
		return helper.getContent();
	}
	
	public boolean extractPicture(String imagePath){
		return helper.extractPicture(imagePath);
	}
}
