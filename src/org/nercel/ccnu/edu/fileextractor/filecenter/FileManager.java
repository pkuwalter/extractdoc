package org.nercel.ccnu.edu.fileextractor.filecenter;

import java.io.FileInputStream;
import java.util.Observable;
import java.util.Observer;

import org.nercel.ccnu.edu.fileextractor.exceptioncenter.ExceptionManager;
import org.nercel.ccnu.edu.fileextractor.filecenter.doc.DOCFileManager;
import org.nercel.ccnu.edu.fileextractor.filecenter.xls.XLSFileManager;
import org.nercel.ccnu.edu.fileextractor.util.FileUtil;

public class FileManager implements Observer {

	private String filePath;
	private String imagePath;
	private FileHelper helper;

	public FileManager(FileRecognizer recognizer) {
		recognizer.addObserver(this);
	}

	@Override
	public void update(Observable subject, Object obj) {
		filePath = ((FileRecognizer) subject).getFilePath();
		imagePath = ((FileRecognizer) subject).getImagePath();

		switch (((FileRecognizer) subject).getFileType()) {
			case FileUtil.FileType.XLS:
				if (!(helper instanceof XLSFileManager)) {
					helper = new XLSFileManager();
				}
				break;
			case FileUtil.FileType.DOC:
				if (!(helper instanceof DOCFileManager)) {
					helper = new DOCFileManager();
				}
				break;
			default:
				ExceptionManager.throwUnsupportedFileTypeException();
		}
	}
	
	public FileInputStream getContent() {
		return helper.getContent(filePath);
	}
	
	public boolean extractPicture(){
		return helper.extractPicture(filePath, imagePath);
	}

}
