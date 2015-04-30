package org.nercel.ccnu.edu.extractdoc.imgext;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.util.Enumeration;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  
public class ImgExt4Docx {  

	/** 
	 * @param args 
	 */  
	static final int BUFFER = 2048;  

	public static void main(String[] args) {  

		String inputFilename = "D:/work/test.docx";  
		String unZipPathname = "D:/work/unZipDir/";  

		ImgExt4Docx ied=new ImgExt4Docx();
		System.out.println(ied.unZipDocx(inputFilename,unZipPathname));

	}

	/*
	 * return the fold of the images  
	 */
	public String unZipDocx(String docxfile,String destDir)
	{
		try{  
			String inputFilename = docxfile;  
			String unZipPathname = destDir; 

			ZipFile zipFile = new ZipFile(inputFilename);  
			Enumeration enu = zipFile.entries();  
			int i = 0;  

			while(enu.hasMoreElements()){  
				ZipEntry zipEntry = (ZipEntry)enu.nextElement();  
				if(zipEntry.isDirectory()){  
					new File(unZipPathname+zipEntry.getName()).mkdirs();  
					continue;  
				}  
				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));  
				File file = new File(unZipPathname+zipEntry.getName());  
				File parent = file.getParentFile();  
				if(parent != null && !parent.exists()){  
					parent.mkdirs();  
				}  
				FileOutputStream fos = new FileOutputStream(file);  
				BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);  

				int count;  
				byte[] array = new byte[BUFFER];  
				while((count = bis.read(array, 0, BUFFER))!=-1){  
					bos.write(array, 0, BUFFER);  
				}  

				bos.flush();  
				bos.close();  
				bis.close();  
			}  
			return destDir+"word/media";

		}catch(Exception e){  
			e.printStackTrace();  
			return null;
		}  
	}
}
