package org.nercel.ccnu.edu.extractdoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;

/**
 * microsoft word document extractor extract text and picture
 */

public class MSWordExtractor {
	private HWPFDocument msWord;
	/**
	 * @param input
	 *            InputStream from file system which has word document stream
	 * @throws IOException
	 */
	public MSWordExtractor(InputStream input) throws IOException {
		msWord = new HWPFDocument(input);
	}
	/**
	 * @return all paragraphs of text
	 */
	public String[] extractParagraphTexts() {
		Range range = msWord.getRange();
		int numParagraph = range.numParagraphs();
		String[] paragraphs = new String[numParagraph];
		for (int i = 0; i < numParagraph; i++) {
			Paragraph p = range.getParagraph(i);
			paragraphs[i] = new String(p.text());
		}
		return paragraphs;
	}
	/**
	 * @return all text of a word
	 */
	public String extractMSWordText() {
		Range range = msWord.getRange();
		String msWordText = range.text();
		return msWordText;
	}
	/**
	 * @param directory
	 *            local file directory that store the images
	 * @throws IOException
	 */
	public void extractImagesIntoDirectory(String directory) throws IOException {
		PicturesTable pTable = msWord.getPicturesTable();
		int numCharacterRuns = msWord.getRange().numCharacterRuns();
		for (int i = 0; i < numCharacterRuns; i++) {
			CharacterRun characterRun = msWord.getRange().getCharacterRun(i);
			if (pTable.hasPicture(characterRun)) {
				System.out.println("have picture!");
				Picture pic = pTable.extractPicture(characterRun, false);
				String fileName = pic.suggestFullFileName();
				OutputStream out = new FileOutputStream(new File(directory
						+ File.separator + fileName));
				pic.writeImageContent(out);
			}
		}
	}
}