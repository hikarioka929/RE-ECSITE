package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemsテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);

	/**
	 * 注文商品を挿入する.
	 * 
	 * @param orderItem 注文商品
	 * @return 挿入した注文商品
	 */
	public OrderItem insert(OrderItem orderItem) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"INSERT INTO order_items (item_id, order_id, quantity, size) VALUES (:itemId, :orderId, :quantity, :size);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };

		template.update(sql.toString(), param, keyHolder, keyColumnNames);
		orderItem.setId(keyHolder.getKey().intValue());
		return orderItem;
	}
}
