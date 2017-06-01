package com.jdbee.model;

/**
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午4:52:29
 */
public class Goods {

	private String platform;// 平台
	private String url;// 请求路径
	private String name;// 名称
	private String price;// 价格，因为有部分是“暂无报价”
	private String commitCnt;// 评论数量

	public Goods() {
		super();
	}

	public Goods(String platform, String url, String name, String price, String commitCnt) {
		super();
		this.platform = platform;
		this.url = url;
		this.name = name;
		this.price = price;
		this.commitCnt = commitCnt;
	}

	public String getCommitCnt() {
		return commitCnt;
	}

	public String getName() {
		return name;
	}
	public String getPlatform() {
		return platform;
	}

	public String getPrice() {
		return price;
	}
	public String getUrl() {
		return url;
	}

	public void setCommitCnt(String commitCnt) {
		this.commitCnt = commitCnt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Goods [platform=" + platform + ", url=" + url + ", name=" + name + ", price=" + price + ", commitCnt="
				+ commitCnt + "]";
	}



}
