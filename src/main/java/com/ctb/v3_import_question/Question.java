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
import org.apache.log4j.Logger;

import com.ctb.entity.SingleAnswer;
import com.ctb.entity.SubjectiveItem;
import com.ctb.util.Json;
import com.ctb.util.RestServer;

/**
 * 解析习题
 * 
 * @author zp
 * 
 */
public class Question {

	public static Logger log = Logger.getLogger(Question.class);

	/**
	 * 解析文本
	 * 
	 * @throws IOException
	 */
	public static String[] parseText(String file) throws IOException {
		String questions = FileUtils.readFileToString(new File(file), "utf-8");
		System.out.println(questions);
		log.info(questions);
		if (questions.contains("（~") && questions.contains("(~")) {
			return null;
		} else if (questions.contains("（~")) {
			return questions.split("（~");
		} else if (questions.contains("(~")) {
			return questions.split("[(]~");
		}
		return null;
	}

	public static String Title(String question) {
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
		String stem = question.substring(spos + 2, epos);
		return stem;
	}

	public static String StemZG(String question) {
		int spos = question.indexOf("{~");
		int epos = question.indexOf("答案");
		String stem = question.substring(spos + 2, epos);
		return stem;
	}

	/**
	 * 解析选项
	 */
	public static String Option(String question) {
		String[] option = question.split("[A-Z]~");
		List<Map<String, String>> options = new ArrayList<Map<String, String>>();
		int op = 64;
		for (int i = 1; i < option.length; i++) {
			Map<String, String> opA = new HashMap<String, String>();
			opA.put("optionKey", (char) (op + i) + "");
			opA.put("optionValue", option[i]);
			options.add(opA);
		}
		return Json.toJson(options).toString();
	}

	/**
	 * 解析解析
	 */
	public static String Analyze(String question) {
		question = question.substring(0, question.indexOf("答案"));
		String[] analyze = question.split("[A-Z]~");
		List<Map<String, String>> analyzes = new ArrayList<Map<String, String>>();
		int op = 64;
		for (int i = 1; i < analyze.length; i++) {
			Map<String, String> anA = new HashMap<String, String>();
			anA.put("analyzeKey", (char) (op + i) + "");
			anA.put("analyzeValue", analyze[i]);
			analyzes.add(anA);
		}
		return Json.toJson(analyzes).toString();
	}

	public static String AnalyzeZG(String question) {
		List<Map<String, String>> analyzes = new ArrayList<Map<String, String>>();
		Map<String, String> anA = new HashMap<String, String>();
		anA.put("analyzeKey", null);
		anA.put("analyzeValue", question);
		analyzes.add(anA);
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

	public static String AnswerZG(String question) {
		String split_str = "答案";
		if (question.indexOf("答案:") == -1) {
			split_str += "：";
		} else {
			split_str += ":";
		}
		return question.substring(question.indexOf(split_str) + 3);
	}

	/**
	 * 上传主观题
	 * 
	 * @param file
	 * @param subject
	 * @throws IOException
	 */
	public static void uploadZGQuestion(String file, String subject) throws IOException {
		String[] questions = parseText(file);
		if (questions != null) {
			for (int i = 0; i < questions.length; i++) {
				if (questions[i] == null || questions[i].equals("")) {
					continue;
				}
				try {

					SubjectiveItem sa = new SubjectiveItem();
					String q = questions[i];
					System.out.println(q);
					log.info(q);
					String split_str = "解析";
					if (q.indexOf("解析:") == -1) {
						split_str += "：";
					} else {
						split_str += ":";
					}
					System.out.println(split_str);

					sa.setTitle(Title(q.split(split_str)[0]));
					System.out.println("分析标题完成");
					log.info("分析标题完成");

					sa.setStem(StemZG(q.split(split_str)[0]));
					System.out.println("分析题干完成");
					log.info("分析题干完成");

					sa.setAnswer(AnswerZG(q.split(split_str)[0]));
					System.out.println("分析答案完成");
					log.info("分析答案完成");

					sa.setAnalyze(AnalyzeZG(q.split(split_str)[1]));
					System.out.println("分析解析完成");
					log.info("分析解析完成");

					sa.setType(13);
					sa.setSubject(subject);
					sa.setGrade("");
					log.info(Json.toJson(sa));

					String result = post(sa, 1);
					System.out.println(result);
					log.info(result);
					System.out.println("主观习题上传完成....");
					log.info("主观习题上传完成....");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(file + "的第" + i + "题出现错误,忽略");
					log.info(file + "的第" + i + "题出现错误,忽略");
					continue;
				}
			}
		} else {
			System.out.println(file + "格式错误,无法解析");
			log.info(file + "格式错误,无法解析");
		}
	}

	/**
	 * 上传习题
	 * 
	 * @throws IOException
	 */
	public static void uploadQuestion(String file, String subject) throws IOException {
		String[] questions = parseText(file);
		if (questions != null) {
			for (int i = 0; i < questions.length; i++) {
				if (questions[i] == null || questions[i].equals("")) {
					continue;
				}
				try {

					SingleAnswer sa = new SingleAnswer();
					String q = questions[i];
					System.out.println(q);
					log.info(q);
					String split_str = "解析";
					if (q.indexOf("解析:") == -1) {
						split_str += "：";
					} else {
						split_str += ":";
					}
					System.out.println(split_str);

					sa.setTitle(Title(q.split(split_str)[0]));
					System.out.println("分析标题完成");
					log.info("分析标题完成");
					sa.setStem(Stem(q.split(split_str)[0]));
					System.out.println("分析题干完成");
					log.info("分析题干完成");
					sa.setOption(Option(q.split(split_str)[0]));
					System.out.println("分析选项完成");
					log.info("分析选项完成");
					sa.setAnalyze(Analyze(q.split(split_str)[1]));
					System.out.println("分析解析完成");
					log.info("分析解析完成");
					sa.setAnswer(Answer(q));
					System.out.println("分析答案完成");
					log.info("分析答案完成");
					sa.setType(5);
					sa.setSubject(subject);
					sa.setGrade("");
					String result = post(sa, 0);
					System.out.println(result);
					log.info(result);
					System.out.println("习题上传完成....");
					log.info("习题上传完成....");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(file + "的第" + i + "题出现错误,忽略");
					log.info(file + "的第" + i + "题出现错误,忽略");
					continue;
				}
			}
		} else {
			System.out.println(file + "格式错误,无法解析");
			log.info(file + "格式错误,无法解析");
		}
	}

	// "http://centerback.service.iwrong.cn/ctb_v3_center_questionBank/api/create/single-answer.json",
	public static String post(Object sa, int op) {
		String xz = "http://product.service.iwrong.cn/iwrong-service-v3/api/create/single-answer.json";
		String zg = "http://product.service.iwrong.cn/iwrong-service-v3/api/create/subjective-item.json";
		String url = "";

		switch (op) {
		case 0:
			url = xz;
			break;
		case 1:
			url = zg;
			break;
		}
		return RestServer.postRest(url, sa);
	}
}
