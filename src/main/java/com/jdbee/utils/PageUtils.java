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
	 * 获取webcollector 自带 htmlUnitDriver实例(模拟默认浏览器)
	 * 
	 * @param page
	 * @return
	 */
	public static HtmlUnitDriver getDriver(String url) {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(url);
		return driver;
	}

	/**
	 * 直接调用原生phantomJS(即不通过selenium)
	 * 
	 * @param page
	 * @return
	 * @throws IOException
	 */
	public static String getPhantomJSDriver(String url) throws IOException {
		InputStream in = null;
		try {
			Process process = Runtime.getRuntime()
					.exec(PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH)
					+ " " + PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_JS) + " " + url);
			in = process.getInputStream();
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
		}finally {
			in.close();
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
				PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
		WebDriver driver = new PhantomJSDriver();
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * @Title: getWebDriver 
	 * @Description: 根据url获取html
	 * @param @param url
	 * @param @return 
	 * @return WebDriver
	 * @throws
	 */
	public static WebDriver getWebDriver(String url) {
		System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);
		return driver;
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtils.getProperty(PropertiesUtils.PHANTOMJS_DRIVER_PATH));
	}
}
