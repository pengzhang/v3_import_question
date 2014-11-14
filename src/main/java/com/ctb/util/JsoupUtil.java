package com.ctb.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jsoup工具类
 * @author zp
 *
 */
public class JsoupUtil {

	private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

	/**
	 * 通过url获取Document
	 * 
	 * @param url
	 * @return
	 */
	public static Document getDoc(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get();
			return doc;
		} catch (Exception e) {
			logger.info("collect url error, url:" + url);
		}
		return null;
	}

	/**
	 * 通过html获取Document
	 * 
	 * @param html
	 * @return
	 */
	public static Document htmlToDoc(String html) {
		return Jsoup.parse(html);
	}

	/**
	 * 通过文件获取Document
	 * 
	 * @param html
	 * @return
	 */
	public static Document fileToDoc(File file) {
		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8");
		} catch (IOException e) {
			logger.info("read file error, file path:" + file.getPath());
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 解析html集合,例如 li
	 * 
	 * @param doc
	 * @param expression
	 * @return
	 */
	public static Elements getElements(Document doc, String expression) {
		return doc.select(expression);
	}

	/**
	 * 解析html片段
	 * 
	 * @param html
	 * @return
	 */
	public static Element parseBodyFragment(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		return doc.body();
	}

	/**
	 * 解析A标签,获取href和Text
	 * @param link
	 * @return
	 */
	public static Map<String, String> parseATag(Element link) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("href", link.attr("href"));
		a.put("text", link.text());
		return a;
	}
}
