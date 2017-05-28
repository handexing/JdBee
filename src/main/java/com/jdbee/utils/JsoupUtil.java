package com.jdbee.utils;

import com.jdbee.model.Category;
import com.jdbee.model.FiveCategory;
import com.jdbee.model.FourCategory;
import com.jdbee.model.SecondCategory;
import com.jdbee.model.ThreeCategory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	/**
	 * 
	 * @Title: getGoodsSku 
	 * @Description: 根据分页url获取页面里面的商品sku
	 * @param @param url
	 * @return List<String>    返回类型 
	 * @throws
	 */
	public static List<String> getGoodsSku(String url) {
		
		List<String> skuUrls = new ArrayList<String>();

		Document document = HttpUtil.getDocumentByUrl(url);
		Element element = document.getElementById("J_goodsList");
		Elements sku = element.select("li");

		for (Element skuId : sku) {
			String path = skuId.attr("data-sku");
			if (!StringUtils.isEmpty(path)) {
				skuUrls.add(path);
			}
		}

		return skuUrls;
	}

	/**
	 * @Title: getLastCategory 
	 * @Description: 找到最后一级类别
	 * @param @param list
	 * @param @param firstcate
	 * @param @param secondCate
	 * @param @return    设定文件 
	 * @return List<FiveCategory>    返回类型 
	 * @throws
	 */
	public static List<FiveCategory> getLastCategory(List<Category> list, String firstcate, String secondCate) {

		List<FiveCategory> fiveCate = new ArrayList<FiveCategory>();

		for (Category category : list) {
			if (firstcate.equals(category.getName())) {
				List<SecondCategory> senondCates = category.getSenondCates();
				for (SecondCategory secondCategory : senondCates) {
					if (secondCate.equals(secondCategory.getName())) {
						List<ThreeCategory> threeCates = secondCategory.getThreeCates();
						for (ThreeCategory threeCategory : threeCates) {
							List<FourCategory> fourCates = threeCategory.getFourCates();
							for (FourCategory fourCategory : fourCates) {
								List<FiveCategory> fiveCates = fourCategory.getFiveCates();
								for (FiveCategory fiveCategory : fiveCates) {
									fiveCate.add(fiveCategory);
								}
							}
						}
					}
				}
			}
		}

		return fiveCate;
	}

	
	/**
	 * @Title: getPageUrlList 
	 * @Description: 获取类目
	 * @param @param list
	 * @param @return    设定文件 
	 * @return List<Map<String,List<String>>>    返回类型 
	 * @throws
	 */
	// public static List<Map<String, List<String>>>
	// getPageUrlList(List<Category> list) {
	//
	// List<Map<String, List<String>>> pageMap = new ArrayList<Map<String,
	// List<String>>>();
	//
	// for (Category category : list) {
	// if ("食品饮料、保健食品".equals(category.getName())) {
	// List<SecondCategory> senondCates = category.getSenondCates();
	// for (SecondCategory secondCategory : senondCates) {
	// if ("进口食品".equals(secondCategory.getName())) {
	// List<ThreeCategory> threeCates = secondCategory.getThreeCates();
	// for (ThreeCategory threeCategory : threeCates) {
	// List<FourCategory> fourCates = threeCategory.getFourCates();
	// for (FourCategory fourCategory : fourCates) {
	// List<FiveCategory> fiveCates = fourCategory.getFiveCates();
	// for (FiveCategory fiveCategory : fiveCates) {
	// Map<String, List<String>> map = getPageUrl(fiveCategory);
	// pageMap.add(map);
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// return pageMap;
	// }

	/**
	 * @Title: getPageUrl 
	 * @Description: 获取类别页数
	 * @param @param fiveCategory
	 * @param @return    设定文件 
	 * @return Map<String,List<String>>    返回类型 
	 * @throws
	 */
	public static Map<String, List<String>> getPageUrl(FiveCategory fiveCategory) {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> urls = new ArrayList<String>();

		Document document = HttpUtil.getDocumentByUrl(fiveCategory.getUrl());
		Element element = document.getElementById("J_bottomPage");

		if (element.childNodeSize() > 0) {// 判断是否有分页
			int cnt = Integer.parseInt(element.select(".p-skip b").text());
			for (int i = 1; i < cnt; i++) {
				String url = Constants.JDURL + fiveCategory.getName() + Constants.JDENC + Constants.JDPAGE + i;
				urls.add(url);
			}
			log.info("正在爬取：" + fiveCategory.getName() + "，共" + urls.size() + "页 ，url：" + fiveCategory.getUrl());
			map.put(fiveCategory.getName(), urls);
		} else {
			String url = Constants.JDURL + fiveCategory.getName() + Constants.JDENC + Constants.JDPAGE + 1;
			urls.add(url);
			map.put(fiveCategory.getName(), urls);
		}

		return map;
	}

	/**
	 * @Title: getSecondCategory 
	 * @Description: 获取二级类别
	 * @param @param content
	 * @param @param cates
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
	 * @param list
	 * @return List<Category> 返回类型 
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
