package com.jdbee.crawler;

import com.jdbee.main.Main;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

/**
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午5:56:24
 */
public abstract class RetailersCrawler extends DeepCrawler {

	public final Logger log = Logger.getLogger(Main.class);

	private String seedFormat;// 种子格式化
	protected RegexRule regexRule;// 正则匹配

	public RetailersCrawler(String crawlPath, String seedFormat) {
		super(crawlPath);
		this.seedFormat = seedFormat;
		this.regexRule = new RegexRule();
	}

	/**
	 * @Title: addRegex 
	 * @Description: 添加正则
	 * @param @param urlRegex    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void addRegex(String urlRegex) {
		this.regexRule.addRule(urlRegex);
	}

	/**
	 * @Title: addSeed 
	 * @Description: 添加一个种子url
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
    private void addSeed() throws Exception{  
        int totalPage=getTotalPage(getPage(getSeed(seedFormat, 1)));  
		for (int page = 1; page <= totalPage; page++) {
            this.addSeed(getSeed(seedFormat, page));  
        }  
    }

    /**
     * @Title: getPage 
     * @Description: 根据url获取Page实例
     * @param @param url
     * @param @throws Exception    设定文件 
     * @return Page    返回类型 
     * @throws
     */
	private Page getPage(String url) throws Exception {
		HttpRequest httpRequest = new HttpRequest(url);
		HttpResponse response = httpRequest.getResponse();
		Page page = new Page();
		page.setUrl(url);
		page.setHtml(response.getHtmlByCharsetDetect());
		page.setResponse(response);
		return page;
	}


	/**
	 * @Title: getSeed 
	 * @Description: 获取seed url 
	 * @param @param seedFormat
	 * @param @param page
	 * @return String    返回类型 
	 * @throws
	 */
	public String getSeed(String seedFormat, Object... page) {
		return String.format(seedFormat, page);
	}

	/**
	 * @Title: getTotalPage 
	 * @Description:获取查询商品总页数
	 * @param @param page
	 * @return int    返回类型 
	 * @throws
	 */
	public abstract int getTotalPage(Page page);

	@Override
	public void start(int depth) throws Exception {
		addSeed();
		super.start(depth);
	}

	public abstract void visit(Page page, Links links);

	public Links visitAndGetNextLinks(Page page) {
        Links nextLinks = new Links();  
        String conteType = page.getResponse().getContentType();  
        if (conteType != null && conteType.contains("text/html")) {  
			Document doc = page.getDoc();
			if (doc != null) {
				nextLinks.addAllFromDocument(page.getDoc(), regexRule);
			}
        }  
        try {  
            visit(page, nextLinks);  
        } catch (Exception ex) {  
			log.info("Exception", ex);
        }  
        return nextLinks;  
    }
}
