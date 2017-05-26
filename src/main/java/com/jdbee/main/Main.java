package com.jdbee.main;

import com.jdbee.model.Category;
import com.jdbee.model.FiveCategory;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

		final int maxThreadCnt = 5;
		ExecutorService p = Executors.newFixedThreadPool(maxThreadCnt);

		final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();
		for (final FiveCategory category : fiveCategories) {
			partitions.add(new Callable<Integer>() {
				public Integer call() throws Exception {
					Map<String, List<String>> map = JsoupUtil.getPageUrl(category);

					Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator();
					while (iterator.hasNext()) {

						Entry<String, List<String>> next = iterator.next();
						List<String> urls = next.getValue();
						String key = next.getKey();
						System.out.println("\n key:" + key);
						for (String url : urls) {
							System.out.println(url);
						}

					}
					System.out.println(Thread.currentThread().getName() + "&&&&&&&&&&&");
					return 0;
				}
			});
		}
		try {
			p.invokeAll(partitions);
			HttpUtil.killChromDriver();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
