package com.jdbee.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdbee.model.Category;
import com.jdbee.model.SecondCategory;
import com.jdbee.model.ThreeCategory;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午10:02:32
 */

public class JdCategory {

	public static final Logger log = Logger.getLogger(JdCategory.class);

	/**
	 * @Title: getCategory 
	 * @Description: 获取类目
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static List<Category> getCategory() {

		List<SecondCategory> secondList = null;
		List<ThreeCategory> threeList = null;

		String content = HttpUtil.sendGet(Constants.JD_URL);
		List<Category> list = JsoupUtil.getFirstCategory(content);
		Document document = Jsoup.parse(content);

		Elements elements = document.select(".item-title span");

		for (Element element : elements) {
			String text = element.text();
			for (int i = 0; i < list.size(); i++) {
				String name = list.get(i).getName();
				if ("电脑办公".equals(text)) {
					text = "电脑、办公";
				}
				if (name.contains(text)) {

					Element categoryItem = element.parent().parent().parent();
					Elements categories = categoryItem.select("dt a");
					Elements threeCate = categoryItem.select("dd a");
					secondList = new ArrayList<SecondCategory>();

					for (int j = 0; j < categories.size(); j++) {
						SecondCategory cate = new SecondCategory();
						cate.setName(categories.get(j).text());
						cate.setUrl("https:" + categories.get(j).attr("href"));
						secondList.add(cate);

						threeList = new ArrayList<ThreeCategory>();

						for (int k = 0; k < threeCate.size(); k++) {
							ThreeCategory threeCategory = new ThreeCategory();
							threeCategory.setUrl("http:" + threeCate.get(k).attr("href"));
							threeCategory.setName(threeCate.get(k).text());
							threeList.add(threeCategory);
						}
						cate.setThreeCates(threeList);
					}
					list.get(i).setSenondCates(secondList);
				}
			}
		}

		JSON json = (JSON) JSONObject.toJSON(list);
		log.info(json);

		return list;
	}

}
