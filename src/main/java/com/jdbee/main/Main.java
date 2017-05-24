package com.jdbee.main;

import com.jdbee.model.Category;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;

import org.apache.log4j.Logger;

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

	public static void main(String[] args) {

		String content = HttpUtil.sendGet(Constants.JD_URL);
		List<Category> list = JsoupUtil.getFirstCategory(content);
		list = JsoupUtil.getSecondCategory(content, list);

		for (Category category : list) {
			log.debug(category.toString());
		}


	}

}
