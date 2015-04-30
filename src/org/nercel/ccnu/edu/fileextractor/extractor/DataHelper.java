package org.nercel.ccnu.edu.fileextractor.extractor;

import java.io.FileInputStream;

import org.nercel.ccnu.edu.fileextractor.model.doc.DocContent;


public interface DataHelper {

	public void setContent(FileInputStream FileInputStream);
	public DocContent getContent();
	public boolean extractPicture(String imagePath);
}
