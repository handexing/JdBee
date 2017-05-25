package com.handx.jd.JdBee;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {

	@Test
	public void testSelenium() throws InterruptedException {

		System.getProperties().setProperty("webdriver.chrome.driver",
				"D:\\myWorkspace\\JdBee\\src\\main\\resources\\chromedriver.exe");

		WebDriver webDriver = new ChromeDriver();

		webDriver.get("https://channel.jd.com/1320-5019.html");

		Thread.sleep(1000);

		String str = webDriver.getPageSource();
		Document parse = Jsoup.parse(str);
		System.out.println(parse.getElementsByClass("food_nav"));
		webDriver.close();
	}
}
