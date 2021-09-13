package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<List<Order>> ORDER_ROW_MAPPER = (rs) -> {
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;
		int beforeIdNumber = 0;
		int orderItemNumber = 0;
		while (rs.next()) {
			int nowIdNumber = rs.getInt("id");
			if (nowIdNumber != beforeIdNumber) {
				Order order = new Order();
				orderItemList = new ArrayList<>();
				order.setId(rs.getInt("id"));
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}

			if (rs.getInt("oi_id") != 0 && orderItemNumber != rs.getInt("oi_id")) {
				OrderItem orderItem = new OrderItem();
				Item item = new Item();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				String size = rs.getString("oi_size");
				char[] c = size.toCharArray();
				orderItem.setSize(c[0]);
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);
			}

			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));
				orderToppingList.add(orderTopping);
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				toppingList = new ArrayList<>();
				toppingList.add(topping);
				orderTopping.setTopping(topping);
			}
			beforeIdNumber = nowIdNumber;
			orderItemNumber = rs.getInt("oi_id");
		}
		return orderList;
	};

	/**
	 * ステータスとユーザーIDから注文情報を取得する.
	 * 
	 * @param status ステータス
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public Order findByUserIdAndStatus(Integer status, Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT o.id, o.user_id, o.status, o.total_price, o.order_date, o.destination_name, o.destination_email, ");
		sql.append(
				"o.destination_zipcode, o.destination_address, o.destination_tel, o.delivery_time, o.payment_method, ");
		sql.append(
				"oi.id oi_id, oi.item_id oi_item_id, oi.order_id oi_order_id, oi.quantity oi_quantity, oi.size oi_size, ");
		sql.append("i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, ");
		sql.append("i.image_path i_image_path, i.deleted i_deleted, ot.id ot_id, ot.topping_id ot_topping_id, ");
		sql.append(
				"ot.order_item_id ot_order_item_id, t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l ");
		sql.append(
				"FROM orders AS o LEFT OUTER JOIN order_items AS oi ON o.id = oi.order_id LEFT OUTER JOIN items AS i ");
		sql.append(
				"ON oi.item_id = i.id LEFT OUTER JOIN order_toppings AS ot ON ot.order_item_id = oi.id LEFT OUTER JOIN toppings AS t ");
		sql.append("ON t.id = ot.topping_id WHERE o.status = :status AND o.user_id = :userId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("status", status).addValue("userId", userId);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_ROW_MAPPER);
		if (orderList.size() == 0) {
			return null;
		}
		return orderList.get(0);
	}

	/**
	 * 注文情報を挿入する.
	 * 
	 * @param 注文情報
	 * @return 挿入した注文情報
	 */
	public Order insert(Order order) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"INSERT INTO orders (user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, ");
		sql.append(
				"destination_address, destination_tel, delivery_time, payment_method) VALUES (:userId, :status, :totalPrice, :orderDate, :destinationName, ");
		sql.append(
				":destinationEmail, :destinationZipcode, :destinationAddress, :destinationTel, :deliveryTime, :paymentMethod);");

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(sql.toString(), param, keyHolder, keyColumnNames);
		order.setId(keyHolder.getKey().intValue());

		return order;
	}
}
