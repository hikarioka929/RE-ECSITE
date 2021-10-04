package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * カート内の商品を削除する時に業務処理をするサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class DeleteItemInCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * 注文商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 */
	public void deleteByOrderItemId(Integer orderItemId) {
		orderToppingRepository.deleteByOrderItemId(orderItemId);
		orderItemRepository.deleteById(orderItemId);
	}
}
