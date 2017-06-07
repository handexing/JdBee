package com.handx.jd.JdBee;

import com.jdbee.model.Goods;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.PageUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TestGoodsDetail {

	public static final Logger log = Logger.getLogger(TestGoodsDetail.class);

	/**
	 * @Title: addGoods 
	 * @Description: 以前老方法
	 * @param @param url 
	 * @return void
	 * @throws
	 */
	public static void addGoods(String url) {

		WebDriver driver = null;
		try {
			driver = PageUtils.getWebDriver(url);
			List<WebElement> eles = driver.findElements(By.cssSelector("li.gl-item"));
			if (!eles.isEmpty()) {
				for (WebElement ele : eles) {
					Goods g = new Goods();
					g.setPlatform(Constants.JD);
					String priceStr = ele.findElement(By.className("p-price")).findElement(By.className("J_price"))
							.findElement(By.tagName("i")).getText();
					if (!StringUtils.isBlank(priceStr) && !"null".equals(priceStr)) {
						g.setPrice(priceStr);
					} else {
						g.setPrice("-1");
					}
					g.setName(ele.findElement(By.className("p-name")).findElement(By.tagName("em")).getText());
					g.setUrl(ele.findElement(By.className("p-name")).findElement(By.tagName("a")).getAttribute("href"));
					String commitCnt = ele.findElement(By.className("p-commit")).findElement(By.tagName("a")).getText();
					if (!StringUtils.isBlank(commitCnt) && !"null".equals(commitCnt)) {
						g.setCommitCnt(commitCnt);
					} else {
						g.setCommitCnt("-1");
					}
				}
			} else {
				log.info("无商品列表！");
			}
		} catch (Exception e) {
			log.warn("爬取异常！！！");
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	/**
	 * @Title: addGoods1 
	 * @Description: 新方法
	 * @param @param url 
	 * @return void
	 * @throws
	 */
	public static void addGoods1(String url) {
		try {
			Document document = Jsoup.parse(HttpUtil.sendGet(url));
			Elements list = document.select("li.gl-item");
			for (Element element : list) {
				String price = element.select(".p-price").select(".J_price").eq(0).select("i").text();
				String title = element.select(".p-name a em").text();
				String href = element.select(".p-name a").attr("href");
				String commit = element.select(".p-commit a").text();
				getGoodsDetail("http:" + href);
			}
		} catch (Exception e) {
			log.warn("爬取异常！！！");
		}
	}

	public static void getGoodsDetail(String url) {

		try {

			String[] cateArrs = new String[5];
			String[] scoreArrs = new String[3];
			String[] imgArrs = new String[6];

			Document document = Jsoup.parse(HttpUtil.sendGet(url));
			Element cates = document.getElementById("crumb-wrap");
			Elements elements = cates.getElementsByTag("a");

			for (int i = 0; i < elements.size(); i++) {
				Element element = elements.get(i);
				String attr = element.attr("clstag");
				if (!StringUtils.isEmpty(attr)) {
					String str = attr.substring(attr.length() - 7, attr.length());
					if (str.contains("mbNav")) {
						cateArrs[i] = element.text() + "&";
					}
				}
				Elements evaluateDetail = element.getElementsByClass("score-detail");
				for (int j = 0; j < evaluateDetail.size(); j++) {
					scoreArrs[j] = evaluateDetail.get(j).getElementsByTag("em").text() + "&";
				}
			}
			
//			http://img14.360buyimg.com/n5/jfs/t3817/341/1304817369/214434/516e0b83/582aaa29Nbb46e619.jpg
			
			Elements productIntroDoc = document.getElementsByClass("product-intro");
			
			for (Element element : productIntroDoc) {
				Elements imgDoc = element.select("#spec-list img");
				for (int i = 0; i < imgDoc.size(); i++) {
					imgArrs[i] = "http:" + imgDoc.get(i).attr("src");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.warn("爬取异常！！！");
		}
	}

	// 19664 6080
	public static void main(String[] args) {
		// https://list.jd.com/list.html?cat=1320,2641,2642
		long start = System.currentTimeMillis();
		// getGoodsDetail("http://item.jd.com/10120597276.html");
		addGoods1("https://list.jd.com/list.html?cat=1320,2641,2642");
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}
