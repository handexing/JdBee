package com.jdbee.model;

/**
 * 
 * @ClassName: FiveCategory
 * @Description: 五级类目
 * @author handx 908716835@qq.com
 * @date 2017年5月25日 下午2:48:33
 *
 */
public class FiveCategory {

	private String name;
	private String url;

	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "name=" + name + ", url=" + url;
	}



}
