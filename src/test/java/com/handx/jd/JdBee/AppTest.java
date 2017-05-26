package com.handx.jd.JdBee;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class AppTest {

	@Test
	public void testCmd(){
		try {
			Runtime.getRuntime().exec("wmic process where name=\"chromedriver.exe\" call terminate");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelenium() throws InterruptedException {
		WebDriver webDriver = null;
		try {
			System.getProperties().setProperty("webdriver.chrome.driver",
					"D:\\myWorkspace\\JdBee\\src\\main\\resources\\chromedriver.exe");

			webDriver = new ChromeDriver();
			webDriver.get("https://channel.jd.com/1320-5019.html");
			Thread.sleep(1000);

			String str = webDriver.getPageSource();
			Document parse = Jsoup.parse(str);
			System.out.println(parse.getElementsByClass("food_nav"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.close();
		}
	}

	@Test
	public void testUrl() {
		// Document document = HttpUtil.getDocumentByUrl(
		// "https://search.jd.com/Search?keyword=%E8%BF%9B%E5%8F%A3%E6%A6%B4%E8%8E%B2%E5%A8%81%E5%8C%96&enc=utf-8&wq=%E8%BF%9B%E5%8F%A3%E6%A6%B4%E8%8E%B2%E5%A8%81%E5%8C%96&pvid=63028a7071c84281a09c47cb9ed707e2");
		// Element elementById = document.getElementById("J_bottomPage");
		// if (elementById.childNodeSize() > 0) {
		// System.out.println(elementById.select(".p-skip b").text());
		// } else {
		// System.out.println("==========");
		// }

		// Document document = HttpUtil.getDocumentByUrl(
		// "https://detail.tmall.com/item.htm?spm=a220m.1000858.1000725.6.HOWjLB&id=540016761844&skuId=3201176746543&areaId=310100&user_id=3000674726&cat_id=2&is_b=1&rn=e380807ac88b9acccf0385aa2d128038");
		// System.out.println(document.getElementById("J_DetailMeta"));
	}
}
