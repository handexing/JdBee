package com.jdbee.utils;

import com.jdbee.model.FiveCategory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreadUtil implements Runnable {

	private static List<FiveCategory> list = null;

	public ThreadUtil(List<FiveCategory> list) {
		ThreadUtil.list = list;
	}

	public void run() {
		for (FiveCategory category : list) {

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
		}
	}

}
