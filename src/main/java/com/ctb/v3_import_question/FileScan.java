package com.ctb.v3_import_question;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ctb.util.UpYun;

/**
 * 文件扫描
 * 
 * @author zp
 * 
 */
public class FileScan {
	
	public static Logger log = Logger.getLogger(FileScan.class);

	/**
	 * 扫描目录
	 */
	public static List<File> scanFolder(String filePath, String subject) {
		System.out.println("开始扫描" + subject + "学科目录:" + filePath + "下的文件");
		log.info("开始扫描" + subject + "学科目录:" + filePath + "下的文件");
		File root = new File(filePath + File.separator + subject
				+ File.separator);
		File[] files = root.listFiles();
		List<File> fs = new ArrayList<File>();
		if (files.length > 0) {
			for (File file : files) {
				if (file.isFile() && file.getName().contains(".htm")) {
					fs.add(file);
				}
			}
		}else{
			System.out.println("该"+subject+"学科目录下没有文件");
			log.info("该"+subject+"学科目录下没有文件");
		}
		return fs;
	}

	public static void main(String[] args) {
		scanFolder("/Users/zp/Downloads/question/", "数学");
	}

	/**
	 * Html文件转带格式的文本
	 * 
	 * @throws IOException
	 */

	public static String htmlTotextFile(File file) throws IOException {
		return htmlTotextFile(
				file.getAbsolutePath().substring(0,
						file.getAbsolutePath().lastIndexOf(File.separator))
						+ File.separator, file.getName().replace(".htm", ""));
	}

	public static String htmlTotextFile(String fPath, String fileName)
			throws IOException {
		String filePath = fPath;
		// if(filePath.charAt(filePath.length()-1)!='/'){
		// filePath = filePath + File.separator;
		// }

		StringBuilder sb = new StringBuilder();

		Document doc = Jsoup.parse(new File(filePath + fileName + ".htm"),
				"gb2312");
		doc.select("table").remove();
		Elements p_html = doc.select("p");

		UpYun upyun = new UpYun("iwrong", "iwrong", "iwrong@XGR008");
		String upload_folder = "resources_v3/"
				+ p_html.select("img").attr("src").split("/")[0];
		upyun.mkDir(upload_folder, true);
		System.out.println("UpYun 图片目录创建成功");
		log.info("UpYun 图片目录创建成功");
		System.out.println(fileName + "的图片开始上传....");
		log.info(fileName + "的图片开始上传....");
		int i = 0;
		for (Element p : p_html) {
			if (p.html().contains("img")) {
				for (Element img : p.select("img")) {
					img.appendText("http://iwrong.b0.upaiyun.com/"
							+ img.attr("src"));
					File file = new File(filePath
							+ URLDecoder.decode(img.attr("src"), "utf-8"));
					upyun.setContentMD5(UpYun.md5(file));
					if (upyun.writeFile("resources_v3/" + img.attr("src"), file, true)) {
						System.out.println(fileName + ",图片已上传" + (++i) + "个图片");
						log.info(fileName + ",图片已上传" + (++i) + "个图片");
					} else {
						System.out.println(img.attr("src") + "图片上传失败");
						log.info(img.attr("src") + "图片上传失败");
					}
				}
			}
			sb.append(p.text());
		}
		System.out.println(fileName + "的图片上传完成");
		log.info(fileName + "的图片上传完成");
		String folder = filePath + fileName;
		createFolder(folder);
		System.out.println("创建临时目录" + folder + "完成");
		log.info("创建临时目录" + folder + "完成");
		writeFile(folder + File.separator + fileName + ".txt", sb.toString());
		System.out.println("创建带格式的文本" + folder + File.separator + fileName
				+ ".txt完成");
		log.info("创建带格式的文本" + folder + File.separator + fileName
				+ ".txt完成");

		return folder + File.separator + fileName + ".txt";
	}

	/**
	 * 创建临时目录
	 */
	public static void createFolder(String folderPath) {
		File file = new File(folderPath);
		file.mkdirs();
	}

	/**
	 * 写文件
	 * 
	 * @throws IOException
	 */
	public static void writeFile(String fileName, String data)
			throws IOException {
		FileUtils.writeStringToFile(new File(fileName), data, "utf-8");
	}

}
