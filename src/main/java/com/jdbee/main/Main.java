package com.jdbee.main;

import com.jdbee.model.Category;
import com.jdbee.model.SecondCategory;
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

	public static void main(String[] args) throws IOException {

		String content = HttpUtil.sendGet(Constants.JD_URL);
		List<Category> list = JsoupUtil.getFirstCategory(content);
		list = JsoupUtil.getSecondCategory(content, list);

		for (Category category : list) {
			if ("食品饮料、保健食品".equals(category.getName())) {
				List<SecondCategory> senondCates = category.getSenondCates();
				for (SecondCategory secondCategory : senondCates) {
					if ("进口食品".equals(secondCategory.getName())) {
						log.debug(secondCategory.toString());
					}
				}
			}
		}


	}

}
