package com.jdbee.model;

import java.util.List;

/**
 * @ClassName: Category
 * @Description: 一级类别
 * @author handx 908716835@qq.com
 * @date 2017年5月24日 下午1:45:25
 */

public class Category {

	private Integer id;
	private String name;

	private List<Category> senondCates;// 二级类别

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Category> getSenondCates() {
		return senondCates;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSenondCates(List<Category> senondCates) {
		this.senondCates = senondCates;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", senondCates=" + senondCates + "]";
	}


}
