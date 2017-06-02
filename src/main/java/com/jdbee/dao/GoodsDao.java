package com.jdbee.dao;

import com.jdbee.model.Goods;

import org.springframework.stereotype.Service;

/**
 * @author handx 908716835@qq.com
 * @date 2017年6月2日 上午10:55:25
 */

@Service
public class GoodsDao extends BaseDao {

	public void createGoods(Goods goods) {
		String sql = "INSERT INTO GOODS(PLATFORM,URL,NAME,PRICE,COMMITCNT) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, goods.getPlatform(),
				goods.getUrl(), goods.getName(), goods.getPrice(), goods.getCommitCnt());
	 }

	public String getGoodsName(Long id) {
		String sql = "SELECT NAME FROM GOODS WHERE ID=?";
		return jdbcTemplate.queryForObject(sql, String.class, id);
	}


}
