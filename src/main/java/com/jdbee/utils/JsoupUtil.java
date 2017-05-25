package com.jdbee.utils;

import com.jdbee.model.Category;
import com.jdbee.model.FiveCategory;
import com.jdbee.model.FourCategory;
import com.jdbee.model.SecondCategory;
import com.jdbee.model.ThreeCategory;

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


	public static void getGoodsUrlList(List<Category> list) {
		for (Category category : list) {
			if ("食品饮料、保健食品".equals(category.getName())) {
				List<SecondCategory> senondCates = category.getSenondCates();
				for (SecondCategory secondCategory : senondCates) {
					if ("进口食品".equals(secondCategory.getName())) {
						List<ThreeCategory> threeCates = secondCategory.getThreeCates();
						for (ThreeCategory threeCategory : threeCates) {
							List<FourCategory> fourCates = threeCategory.getFourCates();
							for (FourCategory fourCategory : fourCates) {
								List<FiveCategory> fiveCates = fourCategory.getFiveCates();
								for (FiveCategory fiveCategory : fiveCates) {
									Document document = HttpUtil.getDocumentByUrl(fiveCategory.getUrl());
									Element element = document.getElementById("J_bottomPage");
									System.out.println(
											fiveCategory.getName() + "::" + element.select(".p-skip b").text());
								}
							}
						}
					}
				}
			}
		}
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

	/**
	 * @Title: getThreeCategory 
	 * @Description: 获取3,4,5级类目
	 * @param @param list
	 * @param @return    设定文件 
	 * @return List<Category>    返回类型 
	 * @throws
	 */
	public static List<Category> getThreeCategory(List<Category> list) {

		List<ThreeCategory> threeCategories = null;
		List<FourCategory> fourCategories = null;
		List<FiveCategory> fiveCategories = null;

		for (Category category : list) {
			if ("食品饮料、保健食品".equals(category.getName())) {
				List<SecondCategory> senondCates = category.getSenondCates();

				for (SecondCategory secondCategory : senondCates) {
					if ("进口食品".equals(secondCategory.getName())) {

						threeCategories = new ArrayList<ThreeCategory>();

						Document document = HttpUtil.getDocumentByUrl(secondCategory.getUrl());
						Elements foodNav = document.getElementsByClass("food_nav");
						Elements titles = foodNav.select(".item_header_title");
						for (Element element : titles) {
							ThreeCategory threeCategory = new ThreeCategory();
							threeCategory.setName(element.select("a").text());
							threeCategory.setUrl(element.select("a").attr("href"));
							threeCategories.add(threeCategory);

							Element item = element.parent().parent();
							Elements foodNavSubs = item.select(".food_nav_sub_item");
							fourCategories = new ArrayList<FourCategory>();

							for (Element sub : foodNavSubs) {
								FourCategory fourCategory = new FourCategory();
								fourCategory.setName(sub.select("a").eq(0).text());
								fourCategory.setUrl(sub.select("a").eq(0).attr("href"));
								fourCategories.add(fourCategory);

								Elements navSubMains = sub.select(".food_nav_sub_main a");
								fiveCategories = new ArrayList<FiveCategory>();

								for (Element a : navSubMains) {
									FiveCategory fiveCategory = new FiveCategory();
									fiveCategory.setName(a.text());
									fiveCategory.setUrl(a.attr("href"));
									fiveCategories.add(fiveCategory);
								}
								fourCategory.setFiveCates(fiveCategories);
							}
							threeCategory.setFourCates(fourCategories);

						}
						secondCategory.setThreeCates(threeCategories);
					}
				}

			}
		}
		return list;
	}

}
