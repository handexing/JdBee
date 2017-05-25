package com.jdbee.utils;

import com.jdbee.model.Category;
import com.jdbee.model.SecondCategory;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: JsoupUtil
 * @Description: 解析数据工具类
 * @author handx 908716835@qq.com
 * @date 2017年5月24日 下午8:23:17
 *
 */
public class JsoupUtil {

	public static final Logger log = Logger.getLogger(JsoupUtil.class);

	/**
	 * @Title: getCategories 
	 * @Description: 获取一级类别
	 * @param @param content
	 * @param @return    设定文件 
	 * @return List<Category>    返回类型 
	 * @throws
	 */
	public static List<Category> getFirstCategory(String content) {

		Document document = Jsoup.parse(content);
		Elements categories = document.getElementsByClass("categories");
		categories = categories.select("a");

		List<Category> list = new ArrayList<Category>();

		for (int i = 0; i < categories.size(); i++) {
			Category category = new Category();
			category.setName(categories.get(i).text());
			category.setId(i);
			list.add(category);
		}

		return list;
	}

	/**
	 * @param i 
	 * @param content 
	 * @Title: getSecondCategory 
	 * @Description: 获取二级类别
	 * @param @return    设定文件 
	 * @return List<Category>    返回类型 
	 * @throws
	 */
	public static List<Category> getSecondCategory(String content, List<Category> cates) {

		List<SecondCategory> list = null;
		Document document = Jsoup.parse(content);
		Elements elements = document.select(".item-title span");

		for (Element element : elements) {
			String text = element.text();
			for (int i = 0; i < cates.size(); i++) {
				String name = cates.get(i).getName();
				if ("电脑办公".equals(text)) {
					text = "电脑、办公";
				}
				if (name.contains(text)) {

					Element categoryItem = element.parent().parent().parent();
					Elements categories = categoryItem.select("dt a");
					list = new ArrayList<SecondCategory>();

					for (int j = 0; j < categories.size(); j++) {
						SecondCategory cate = new SecondCategory();
						cate.setName(categories.get(j).text());
						cate.setUrl("https:" + categories.get(j).attr("href"));
						list.add(cate);
					}
					cates.get(i).setSenondCates(list);
				}
			}
		}

		return cates;
	}

}
