package com.jdbee.main;

import com.jdbee.model.Category;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @ClassName: Main
 * @Description: 程序入口
 * @author handx 908716835@qq.com
 * @date 2017年5月24日 下午8:05:02
 *
 */
public class Main {

	public static final Logger log = Logger.getLogger(Main.class);

	// https://channel.jd.com/1320-5019.html

	public static void main(String[] args) throws IOException {

		// 获取网页数据
		String content = HttpUtil.sendGet(Constants.JD_URL);
		// 获取一级类目
		List<Category> list = JsoupUtil.getFirstCategory(content);
		// 获取二级类目
		list = JsoupUtil.getSecondCategory(content, list);
		// 获取三,四,五级类目
		list = JsoupUtil.getThreeCategory(list);
		// 获取商品url
		JsoupUtil.getGoodsUrlList(list);

		// wmic process where name="chromedriver.exe" call terminate

		// for (Category category : list) {
		// JSON json = (JSON) JSONObject.toJSON(category);
		// System.out.println(json.toJSONString());
		// }
		
		// Document document = HttpUtil.getDocumentByUrl(
		// "https://search.jd.com/search?keyword=%E8%BF%9B%E5%8F%A3%E6%B5%B7%E8%8B%94&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&offset=3&wq=%E8%BF%9B%E5%8F%A3%E6%B5%B7%E8%8B%94&ev=3765_9689%40&uc=0#J_searchWrap");
		// Element elementById = document.getElementById("J_bottomPage");
		// System.out.println(elementById.select(".p-skip b").text());

	}

}
