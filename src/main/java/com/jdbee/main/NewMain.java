package com.jdbee.main;

import com.jdbee.crawler.JDGoodsList;
import com.jdbee.crawler.RetailersCrawler;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * @ClassName: NewMain
 * @Description: 程序入口
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午9:47:17
 *
 */
public class NewMain extends RetailersCrawler {

	public static final Logger log = Logger.getLogger(NewMain.class);

	public static void main(String[] args) throws Exception {

		// 目前只是用一个类目测试
		NewMain crawler = new NewMain("data", "http://list.jd.com/list.html?cat=1319,1523,7052&page=%s&go=0&JL=6_0_0");
		crawler.setThreads(5);// 抓取启动线程数
		crawler.start(1);// 层数

		crawler.print();

		// 获取类目列表
		// List<Category> category = JdCategory.getCategory();
	}

	private JDGoodsList goodsList;

	public NewMain(String crawlPath, String seekFormat) {
		super(crawlPath, seekFormat);
		goodsList = new JDGoodsList();
	}

	@Override
	public int getTotalPage(Page page) {
		Element ele = page.getDoc().select("div#J_bottomPage").select("span.p-skip>em").first().select("b").first();
		return ele == null ? 0 : Integer.parseInt(ele.text());
	}

	private void print() {
		log.info(goodsList.size());
	}

	@Override
	public void visit(Page page, Links links) {
		goodsList.addGoods(page);
	}

}
