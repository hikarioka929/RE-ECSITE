package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * カート内商品表示時に業務処理をするサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class ShowItemInCartService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * カート内の商品を表示する.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public Order showItemInCart(Integer userId) {
		Order order = orderRepository.findByUserIdAndStatus(0, userId);
		return order;
	}
}