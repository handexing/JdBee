package com.jdbee.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.os.WindowsUtils;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName: HttpUtil
 * @Description:
 * @author handx 908716835@qq.com
 * @date 2017年5月24日 下午3:32:31
 *
 */

/**
 * @ClassName: HttpUtil
 * @Description: httpclient请求数据工具类
 * @author handx 908716835@qq.com
 * @date 2017年5月24日 下午7:46:06
 *
 */
public class HttpUtil {

	public static final Logger log = Logger.getLogger(JsoupUtil.class);

	public static final String TAG = "HttpUtils";
	public static CloseableHttpClient httpClient = HttpClients.createDefault();
	public static HttpClientContext context = new HttpClientContext();

	/**
	 * @Title: getDocumentByUrl 
	 * @Description:使用Selenium模拟浏览器动态获取数据
	 * @param @param url
	 * @param @return    设定文件 
	 * @return Document    返回类型 
	 * @throws
	 */
	public static Document getDocumentByUrl(String url) {
		WebDriver webDriver = null;
		Document document = null;
		try {
			System.getProperties().setProperty("webdriver.chrome.driver",
					"D:\\myWorkspace\\JdBee\\src\\main\\resources\\chromedriver.exe");
			webDriver = new ChromeDriver();
			webDriver.get(url);
			Thread.sleep(1000);// 停止1s模拟网速
			document = Jsoup.parse(webDriver.getPageSource());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.close();
		}
		return document;
	}

	/**
	 * @Title: killChromDriver 
	 * @Description: 杀死chromDriver后台进程,每次都会启动一次很浪费内存，执行完就杀掉
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void killChromDriver() {
		try {
			WindowsUtils.tryToKillByName("chromedriver.exe");
			// Runtime.getRuntime().exec("wmic process where
			// name=\"chromedriver.exe\" call terminate");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: sendGet 
	 * @Description: get请求
	 * @param @param url
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String sendGet(String url) {
		CloseableHttpResponse response = null;
		String content = null;
		try {
			HttpGet get = new HttpGet(url);
			response = httpClient.execute(get, context);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			log.error("get请求获取数据失败，请检查url是否正确！", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	/**
	 * @Title: sendPost 
	 * @Description: post请求
	 * @param @param url
	 * @param @param nvps
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String sendPost(String url, List<NameValuePair> nvps) {
		CloseableHttpResponse response = null;
		String content = null;
		try {
			// HttpClient中的post请求包装类
			HttpPost post = new HttpPost(url);
			// nvps是包装请求参数的list
			if (nvps != null) {
				post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}
			// 执行请求用execute方法，content用来帮我们附带上额外信息
			response = httpClient.execute(post, context);
			// 得到相应实体、包括响应头以及相应内容
			HttpEntity entity = response.getEntity();
			// 得到response的内容
			content = EntityUtils.toString(entity);
			// System.out.println(TAG + "POST:" + content);
			// 关闭输入流
			EntityUtils.consume(entity);
			return content;
		} catch (Exception e) {
			log.error("post请求获取数据失败，请检查url是否正确！", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

}
