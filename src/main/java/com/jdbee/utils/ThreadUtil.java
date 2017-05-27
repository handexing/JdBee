package com.jdbee.utils;

import com.jdbee.model.FiveCategory;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

	public static final Logger log = Logger.getLogger(ThreadUtil.class);

	private static List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();

	/**
	 * @Title: getCategoryPageUrl 
	 * @Description: 获取类目的分页集合
	 * @param @param fiveCategories
	 * @param @return    设定文件 
	 * @return List<Map<String,List<String>>>    返回类型 
	 * @throws
	 */
	public static  List<Map<String, List<String>>> getCategoryPageUrl(List<FiveCategory> fiveCategories) {

		ExecutorService p = Executors.newFixedThreadPool(Constants.MAX_THREAD_CNT);
		final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();

		try {

			for (final FiveCategory category : fiveCategories) {
				partitions.add(new Callable<Integer>() {
					public Integer call() throws Exception {
						Map<String, List<String>> map = JsoupUtil.getPageUrl(category);
						list.add(map);
						log.info(category.getName() + "已爬完...已爬取" + list.size() + "个类别...");
						return 0;
					}
				});
			}

			p.invokeAll(partitions);
			p.shutdown();
			HttpUtil.killChromDriver();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

}
