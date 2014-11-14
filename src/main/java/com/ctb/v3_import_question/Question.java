package com.ctb.v3_import_question;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.ctb.entity.SingleAnswer;
import com.ctb.util.Json;
import com.ctb.util.RestServer;

/**
 * 解析习题
 * 
 * @author zp
 * 
 */
public class Question {

	/**
	 * 解析文本
	 * 
	 * @throws IOException
	 */
	public static String[] parseText(String file) throws IOException {
		String questions = FileUtils.readFileToString(new File(file), "utf-8");
		if (questions.contains("（~") && questions.contains("(~")) {
			return null;
		} else if (questions.contains("（~")) {
			return questions.split("（~");
		} else if (questions.contains("（~")) {
			return questions.split("（~");
		}
		return null;
	}
	
	public static String Title(String question){
		int position = question.indexOf("{~");
		String title = question.substring(0, position);
		return title;
	}

	/**
	 * 解析题干
	 */
	public static String Stem(String question) {
		int spos = question.indexOf("{~");
		int epos = question.indexOf("A~");
		String stem = question.substring(spos+2, epos);
		return stem;
	}

	/**
	 * 解析选项
	 */
	public static String Option(String question) {
		String[] option = question.split("[A-Z]~");
		List<Map<String, String>> options = new ArrayList<Map<String, String>>();
		int op = 64;
		for(int i=1; i<option.length;i++ ){
			Map<String, String> opA = new HashMap<String, String>();
			opA.put("optionKey", (char)(op+i)+"" );
			opA.put("optionValue", option[i]);
			options.add(opA);
		}
		return Json.toJson(options).toString();
	}

	/**
	 * 解析解析
	 */
	public static String Analyze(String question) {
		String[] analyze = question.split("[A-Z]~");
		List<Map<String, String>> analyzes = new ArrayList<Map<String, String>>();
		int op = 64;
		for(int i=1; i<analyze.length;i++ ){
			Map<String, String> anA = new HashMap<String, String>();
			anA.put("analyzeKey", (char)(op+i)+"" );
			anA.put("analyzeValue", analyze[i]);
			analyzes.add(anA);
		}
		return Json.toJson(analyzes).toString();
	}

	/**
	 * 解析答案
	 */
	public static String Answer(String question) {
		Pattern p = Pattern.compile("[A-Z]");
		Matcher m = p.matcher(question.split("答案")[1]);
		m.find();
		return m.group();
	}

	public static void main(String[] args) throws IOException {
		uploadQuestion("/Users/zp/Downloads/小学数学录入测试题/小学数学录入测试题.txt","");
	}

	/**
	 * 上传习题
	 * 
	 * @throws IOException
	 */
	public static void uploadQuestion(String file,String subject) throws IOException {
		String[] questions = parseText(file);
		if (questions != null) {
			for (int i = 1; i < questions.length; i++) {
				SingleAnswer sa = new SingleAnswer();
				String q = questions[i];
				sa.setTitle(Title(q.split("解析")[0]));
				System.out.println("分析标题完成");
				sa.setStem(Stem(q.split("解析")[0]));
				System.out.println("分析题干完成");
				sa.setOption(Option(q.split("解析")[0]));
				System.out.println("分析选项完成");
				sa.setAnalyze(Analyze(q.split("解析")[1]));
				System.out.println("分析解析完成");
				sa.setAnswer(Answer(q));
				System.out.println("分析答案完成");
				sa.setType(5);
				sa.setSubject(subject);
				sa.setGrade("");
				post(sa);
				System.out.println("习题上传完成....");
			}
		} else {
			System.out.println(file + "格式错误,无法解析");
		}
	}

	public static void post(SingleAnswer sa) {
		RestServer
				.postRest(
						"http://centerback.service.iwrong.cn/ctb_v3_center_questionBank/api/create/single-answer.json",
						sa);
	}
}
