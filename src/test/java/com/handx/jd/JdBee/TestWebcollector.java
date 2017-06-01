package com.handx.jd.JdBee;

import cn.edu.hfut.dmic.webcollector.crawler.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 上午11:08:19
 */

public class TestWebcollector extends BreadthCrawler {

	public static void main(String[] args) throws Exception {
		TestWebcollector crawler = new TestWebcollector("data", true);
		crawler.addSeed("http://baidu.com/");
		crawler.start(5);
	}

	public TestWebcollector(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}

	@Override
	public void visit(Page page, Links nextLinks) {
		System.out.println("正在抽取" + page.getUrl());
		String title = page.getDoc().title();
		System.out.println(title);
	}

}
