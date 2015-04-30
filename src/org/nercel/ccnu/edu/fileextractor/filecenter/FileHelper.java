package org.nercel.ccnu.edu.fileextractor.filecenter;

import java.io.FileInputStream;

public interface FileHelper {

	public FileInputStream getContent(String filePath);
	public boolean extractPicture(String filePath, String imagePath);

}
