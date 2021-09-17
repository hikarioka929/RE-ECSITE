package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * order_toppingsテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class OrderToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER
	= new BeanPropertyRowMapper<>(OrderTopping.class);
	
	/**
	 * 注文トッピングを挿入する.
	 * 
	 * @param orderTopping 注文トッピング情報
	 */
	public void insert(OrderTopping orderTopping) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO order_toppings (topping_id, order_item_id) VALUES (:toppingId, :orderItemId);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql.toString(), param);
	}
}
