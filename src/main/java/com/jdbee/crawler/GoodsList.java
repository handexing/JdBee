package com.jdbee.crawler;

import com.jdbee.model.Goods;

import java.util.ArrayList;

import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * @ClassName: GoodsList
 * @Description: 考虑以后会有抽取多个平台数据，方便其他平台继承
 * @author handx 908716835@qq.com
 * @date 2017年6月1日 下午5:54:20
 */
public abstract class GoodsList extends ArrayList<Goods> {

	private static final long serialVersionUID = -7894645047969514212L;

	public abstract void addGoods(Page page);

}
