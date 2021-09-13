package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.AddItemToCartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * 商品をカートに追加時に業務処理をするサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class AddItemToCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * カートに商品を追加する.
	 * 
	 * @param form   商品追加時に使用するフォーム.
	 * @param userId ユーザーID
	 */
	public void add(AddItemToCartForm form, Integer userId, Integer status) {
		OrderItem orderItem = new OrderItem();
		Order returnOrder = orderRepository.findByUserIdAndStatus(status, userId);

		if (returnOrder == null) {
			// Orderのinsert
			Order order = new Order();
			order.setUserId(userId);
			order.setStatus(status);
			order.setTotalPrice(0);
			order = orderRepository.insert(order);

			// OrderItemのinsert
			orderItem.setItemId(form.getItemId());
			orderItem.setOrderId(order.getId());
			orderItem.setQuantity(form.getQuantity());
			orderItem.setSize(form.getSize());
			orderItem = orderItemRepository.insert(orderItem);
		} else {
			// OrderItemのinsert
			orderItem.setItemId(form.getItemId());
			orderItem.setOrderId(returnOrder.getId());
			orderItem.setQuantity(form.getQuantity());
			orderItem.setSize(form.getSize());
			orderItem = orderItemRepository.insert(orderItem);
		}
		// OrderToppingのinsert
		List<Integer> toppingList = form.getToppingList();
		if (toppingList != null) {
			for (Integer topping : toppingList) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping.setToppingId(topping);
				orderToppingRepository.insert(orderTopping);
			}
		}
	}
}
