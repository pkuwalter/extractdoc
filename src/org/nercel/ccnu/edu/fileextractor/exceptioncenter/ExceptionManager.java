package org.nercel.ccnu.edu.fileextractor.exceptioncenter;

import org.nercel.ccnu.edu.fileextractor.exceptioncenter.exception.UnsupportedFileTypeException;

public class ExceptionManager {

	public static void throwUnsupportedFileTypeException() {
		try {
			throw new UnsupportedFileTypeException();
		} catch (UnsupportedFileTypeException e) {
			e.printStackTrace();
		}
	}
}
