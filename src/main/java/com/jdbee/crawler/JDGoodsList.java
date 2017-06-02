package com.jdbee.crawler;

import com.jdbee.model.Goods;
import com.jdbee.utils.Constants;
import com.jdbee.utils.PageUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * @ClassName: JDGoodsList
 * @Description: 获取京东商品
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午5:55:47
 *
 */
public class JDGoodsList extends GoodsList {

	private static final long serialVersionUID = -6016161025701938903L;
	public final Logger log = Logger.getLogger(JDGoodsList.class);

	@Override
	public void addGoods(Page page) {

		WebDriver driver = null;
		try {
			driver = PageUtils.getWebDriver(page);
			List<WebElement> eles = driver.findElements(By.cssSelector("li.gl-item"));
			if (!eles.isEmpty()) {
				for (WebElement ele : eles) {
					Goods g = new Goods();
					g.setPlatform(Constants.JD);
					// 价格
					String priceStr = ele.findElement(By.className("p-price")).findElement(By.className("J_price"))
							.findElement(By.tagName("i")).getText();
					if (!StringUtils.isBlank(priceStr) && !"null".equals(priceStr)) {
						g.setPrice(priceStr);
					} else {
						g.setPrice("-1");
					}
					// 商品名
					g.setName(ele.findElement(By.className("p-name")).findElement(By.tagName("em")).getText());
					// 商品链接
					g.setUrl(ele.findElement(By.className("p-name")).findElement(By.tagName("a")).getAttribute("href"));
					// 评价
					String commitCnt = ele.findElement(By.className("p-commit")).findElement(By.tagName("a")).getText();
					if (!StringUtils.isBlank(commitCnt) && !"null".equals(commitCnt)) {
						g.setCommitCnt(commitCnt);
					} else {
						g.setCommitCnt("-1");
					}
					System.out.println(g.toString());
					add(g);
				}
			} else {
				System.out.println("else is empty");
			}
		} catch (Exception e) {
			log.warn("爬取异常！！！");
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}
