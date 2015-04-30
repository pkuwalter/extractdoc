package org.nercel;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Picture;

public class Test {

	public static void main(String[] args) {
		try {
			String path = "./resources/1 选择单题.doc";
			FileInputStream in = new FileInputStream(new File(path));
			HWPFDocument doc = new HWPFDocument(in);
			// doc.
			PicturesTable pic = doc.getPicturesTable();

			List pictureList = pic.getAllPictures();
			System.out.println(pictureList.size());
			BufferedOutputStream output = null;
			for (int i = 0; i < pictureList.size(); i++) {
				Picture p = (Picture) pictureList.get(i);
				
//				System.out.println(p.get());
				p.getAspectRatioX();//x坐标
				p.getAspectRatioY();//y坐标
				p.getHeight();//高度
				p.getWidth();//宽读
				output = new BufferedOutputStream(new FileOutputStream(
						"./data/" + (i + 1)+ p.suggestFullFileName()));
				output.write(p.getContent());
				output.flush();
				output.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
