package com.ctb.v3_import_question;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctb.util.UpYun;

public class WordCollect {

	public static Logger logger = LoggerFactory
			.getLogger(WordCollect.class);

	/**
	 * 题库信息
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static String collect() throws IOException {

		StringBuilder sb = new StringBuilder();

		Document doc = Jsoup.parse(new File("/Users/zp/Downloads/小学数学录入测试题.htm"),
				"gb2312");
		doc.select("table").remove();
		Elements p_html = doc.select("p");
		UpYun upyun = new UpYun("iwrong", "iwrong", "iwrong@XGR008");
		String upload_folder = p_html.select("img").attr("src").split("/")[0];
		upyun.mkDir(upload_folder,true);
		for (Element p : p_html) {
			if (p.html().contains("img")) {
				for (Element img : p.select("img")) {
					img.appendText("{&http://iwrong.b0.upaiyun.com/"+ img.attr("src"));
					File file = new File("/Users/zp/Downloads/"+URLDecoder.decode(img.attr("src"),"utf-8"));
				    upyun.setContentMD5(UpYun.md5(file));
					System.out.println(upyun.writeFile("/"+img.attr("src"), file,true));
				}
			}
			sb.append(p.text());
			
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	

	public static void main(String[] args) {
		try {
			collect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}