package com.jdbee.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * @ClassName: PageUtils
 * @Description: PhantomJS工具类
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午5:53:10
 */
public class PageUtils {

	/**
	 * 获取webcollector 自带 htmlUnitDriver实例(模拟默认浏览器)
	 * 
	 * @param page
	 * @return
	 */
	public static HtmlUnitDriver getDriver(Page page) {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * 获取webcollector 自带htmlUnitDriver实例
	 * 
	 * @param page
	 * @param browserVersion
	 *            模拟浏览器
	 * @return
	 */
	public static HtmlUnitDriver getDriver(Page page, BrowserVersion browserVersion) {
		HtmlUnitDriver driver = new HtmlUnitDriver(browserVersion);
		driver.setJavascriptEnabled(true);
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * 直接调用原生phantomJS(即不通过selenium)
	 * 
	 * @param page
	 * @return
	 */
	public static String getPhantomJSDriver(Page page) {
		Runtime rt = Runtime.getRuntime();
		Process process = null;
		try {
			process = rt.exec("D:\\myWorkspace\\JdBee\\src\\main\\resources\\phantomjs.exe"
					+ "D:/myWorkspace/JdBee/src/main/resources/parser.js " + page.getUrl().trim());
			InputStream in = process.getInputStream();
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sbf = new StringBuffer();
			String tmp = "";
			while ((tmp = br.readLine()) != null) {
				sbf.append(tmp);
			}
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取PhantomJsDriver(可以爬取js动态生成的html)
	 * 
	 * @param page
	 * @return
	 */
	public static WebDriver getWebDriver(Page page) {
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"D:\\myWorkspace\\JdBee\\src\\main\\resources\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.get(page.getUrl());
		return driver;
	}
}
