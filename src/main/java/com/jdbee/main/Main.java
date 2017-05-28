package com.jdbee.main;

import com.jdbee.model.Category;
import com.jdbee.model.FiveCategory;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;
import com.jdbee.utils.ThreadUtil;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		// List<Map<String, List<String>>> pageMap =
		// JsoupUtil.getPageUrlList(list);
		// JSON json = (JSON) JSONObject.toJSON(pageMap);
		// System.out.println(json.toJSONString());

		List<FiveCategory> fiveCategories = JsoupUtil.getLastCategory(list, "食品饮料、保健食品", "进口食品");

		// 获取类目分页信息
		List<Map<String, List<String>>> categoryPageUrl = ThreadUtil.getCategoryPageUrl(fiveCategories);


		int i = 0;

		for (Map<String, List<String>> map : categoryPageUrl) {
			Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, List<String>> next = iterator.next();
				List<String> urls = next.getValue();
				String key = next.getKey();
				System.err.println("\n key:" + key);
				for (String url : urls) {
					System.out.println(url);
					i++;
				}
			}
		}
		System.out.println("共有" + i + "页数据...");

		List<List<String>> skus = ThreadUtil.getGoodsSkuIdByCatePages(categoryPageUrl);

		System.out.println("共爬取界面：" + skus.size() + "个！");

		int j = 0;
		for (List<String> lists : skus) {
			for (String str : lists) {
				System.err.println(str);
				j++;
			}
		}
		System.out.println("共有SKU" + j + "个...");


	}

}
