package org.nercel.ccnu.edu.fileextractor;

import org.nercel.ccnu.edu.fileextractor.extractor.DataExtractor;
import org.nercel.ccnu.edu.fileextractor.filecenter.FileManager;
import org.nercel.ccnu.edu.fileextractor.filecenter.FileRecognizer;
import org.nercel.ccnu.edu.fileextractor.model.doc.DocContent;

public class Test {
	public static void main(String[] args){
		FileRecognizer fileRecognizer;
		fileRecognizer = new FileRecognizer();
		FileManager manager = new FileManager(fileRecognizer);
		DataExtractor extractor = new DataExtractor(fileRecognizer);
	
		String path = "./resources/1 选择单题.doc";
		String imagePath = "./pic";
		
		fileRecognizer.setFilePath(path);
		fileRecognizer.setImagePath(imagePath);

		extractor.setContent(manager.getContent());
		manager.extractPicture();
		
		DocContent content = extractor.getContent();
		
		System.out.println(content.toString());
	}
}