package com.handx.jd.JdBee.dao;

import com.jdbee.dao.GoodsDao;
import com.jdbee.model.Goods;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestGoodsDao {

	GoodsDao goodsDao;

	@Before
	public void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("springJdbcContext.xml");
		goodsDao = (GoodsDao) context.getBean("goodsDao");
	}

	@Test
	public void testFindGoods() {
		String goodsName = goodsDao.getGoodsName(1L);
		System.out.println(goodsName);
	}

	@Test
	public void testSaveGoods() {
		Goods goods = new Goods();
		goods.setPlatform("JD");
		goods.setCommitCnt("10000");
		goods.setName("AD钙奶");
		goods.setPrice("100");
		goods.setUrl("http://xxxxx");
		goodsDao.createGoods(goods);
	}
}
