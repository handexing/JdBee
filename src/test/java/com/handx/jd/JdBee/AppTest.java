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
}
