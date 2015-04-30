package org.nercel.ccnu.edu.fileextractor.filecenter;

import java.util.Observable;

import org.nercel.ccnu.edu.fileextractor.util.FileUtil;

/**
 * 
 * @author Walter
 *
 */
public class FileRecognizer extends Observable {

	private String filePath;
	private String imagePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		if (this.filePath == null || !this.filePath.equals(filePath)) {
			this.filePath = filePath;
			setChanged();
		}
		notifyObservers();
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		if (this.imagePath == null || !this.imagePath.equals(imagePath)) {
			this.imagePath = imagePath;
			setChanged();
		}
		notifyObservers();
	}

	/**
	 * Here use filename extension to make assumptions about the file type.
	 * 
	 * @return
	 */
	public int getFileType() {
		if (filePath.endsWith(".xls")) {
			return FileUtil.FileType.XLS;
		}else if(filePath.endsWith(".doc")){
			return FileUtil.FileType.DOC;
		}else{
			return FileUtil.FileType.ELSE;
		}
	}

}
