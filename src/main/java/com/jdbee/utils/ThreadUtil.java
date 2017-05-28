package com.jdbee.utils;

import com.jdbee.model.FiveCategory;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

	public static final Logger log = Logger.getLogger(ThreadUtil.class);

	private static List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
	private static List<List<String>> skus = new ArrayList<List<String>>();

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

	/**
	 * @Title: getGoodsSkuIdByCatePages 
	 * @Description: 多线程根据分页url获取sku
	 * @param @param categoryPageUrl
	 * @return List<List<String>>    返回类型 
	 * @throws
	 */
	public static List<List<String>> getGoodsSkuIdByCatePages(List<Map<String, List<String>>> categoryPageUrl) {

		ExecutorService p = Executors.newFixedThreadPool(Constants.MAX_THREAD_CNT);
		final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();

		try {
			for (Map<String, List<String>> map : categoryPageUrl) {
				Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, List<String>> next = iterator.next();
					List<String> urls = next.getValue();
					for (final String url : urls) {
						partitions.add(new Callable<Integer>() {
							public Integer call() throws Exception {
								List<String> goodsSkus = JsoupUtil.getGoodsSku(url);
								skus.add(goodsSkus);
								log.info("正在爬取：" + url + "界面共爬取sku个数：" + goodsSkus.size());
								return 0;
							}
						});
					}
				}
			}
			p.invokeAll(partitions);
			p.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return skus;
	}

}
