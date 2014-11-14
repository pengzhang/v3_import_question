package com.ctb.v3_import_question;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) throws IOException {
		String path = "";
		if(args.length==0){
			path = "/Users/zp/Downloads/question/";
		}else{
			path = args[0];
		}
		String[] subjects = { "数学", "化学", "物理", "英语" };
		for (String subject : subjects) {
			List<File> fs = FileScan.scanFolder(path, subject);
			for (File file : fs) {
				String file_txt = FileScan.htmlTotextFile(file);
				Question.uploadQuestion(file_txt, subject);
			}
		}

	}
}
