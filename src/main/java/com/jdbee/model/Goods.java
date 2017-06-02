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
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img5;

	private String skuId;
	private String venderId;
	private String shopId;
	private String plusPrice;// 会员价

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

	public String getImg1() {
		return img1;
	}

	public String getImg2() {
		return img2;
	}

	public String getImg3() {
		return img3;
	}

	public String getImg4() {
		return img4;
	}

	public String getImg5() {
		return img5;
	}

	public String getName() {
		return name;
	}

	public String getPlatform() {
		return platform;
	}

	public String getPlusPrice() {
		return plusPrice;
	}

	public String getPrice() {
		return price;
	}

	public String getShopId() {
		return shopId;
	}

	public String getSkuId() {
		return skuId;
	}

	public String getUrl() {
		return url;
	}

	public String getVenderId() {
		return venderId;
	}

	public void setCommitCnt(String commitCnt) {
		this.commitCnt = commitCnt;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public void setImg4(String img4) {
		this.img4 = img4;
	}

	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public void setPlusPrice(String plusPrice) {
		this.plusPrice = plusPrice;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}
	@Override
	public String toString() {
		return "Goods [platform=" + platform + ", url=" + url + ", name=" + name + ", price=" + price + ", commitCnt="
				+ commitCnt + "]";
	}



}
