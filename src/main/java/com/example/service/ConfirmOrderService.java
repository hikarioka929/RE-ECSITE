package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文確認画面を表示する時に業務処理を行うサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class ConfirmOrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * カート内商品を表示する.
	 * 
	 * @param userId ユーザーID return 注文情報
	 */
	public Order showItemInCart(Integer userId) {
		Order order = orderRepository.findByUserIdAndStatus(0, userId);
		return order;
	}
}
