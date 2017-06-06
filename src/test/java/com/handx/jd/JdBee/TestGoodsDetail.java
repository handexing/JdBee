package com.handx.jd.JdBee;

import com.jdbee.utils.PageUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

public class TestGoodsDetail {

	public static final Logger log = Logger.getLogger(TestGoodsDetail.class);

	public static void getGoodsDetail(String url) {

		WebDriver driver = null;

		try {
			driver = PageUtils.getWebDriver(url);
			Document document = Jsoup.parse(driver.getPageSource());
			Element cates = document.getElementById("crumb-wrap");
			Elements elements = cates.getElementsByTag("a");
			for (Element element : elements) {
				String attr = element.attr("clstag");
				if (!StringUtils.isEmpty(attr)) {
					String str = attr.substring(attr.length() - 7, attr.length());
					if (str.contains("mbNav")) {
						System.out.println(element.text());
					}
				}
				Elements evaluateDetail = element.getElementsByClass("score-detail");
				for (Element e : evaluateDetail) {
					System.out.println(e.getElementsByTag("em").text());
				}
			}

		} catch (Exception e) {
			log.warn("爬取异常！！！");
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	public static void main(String[] args) {

		getGoodsDetail("http://item.jd.com/10120597276.html");

	}

}
