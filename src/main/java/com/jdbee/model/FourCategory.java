package com.jdbee.model;

import java.util.List;

/**
 * @ClassName: FourCategory
 * @Description: 四级类目
 * @author handx 908716835@qq.com
 * @date 2017年5月25日 下午2:27:35
 */
public class FourCategory {

	private String name;
	private String url;

	private List<FiveCategory> fiveCates;// 五级类别

	public List<FiveCategory> getFiveCates() {
		return fiveCates;
	}
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setFiveCates(List<FiveCategory> fiveCates) {
		this.fiveCates = fiveCates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "FourCategory [name=" + name + ", url=" + url + ", fiveCates=" + fiveCates + "]";
	}


}
