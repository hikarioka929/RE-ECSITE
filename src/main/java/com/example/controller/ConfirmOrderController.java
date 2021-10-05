package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.ConfirmOrderService;

/**
 * 注文確認画面を表示する時に操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/confirmOrder")
public class ConfirmOrderController {

	@ModelAttribute
	public OrderForm setUpOrderForm() {
		User user = (User) session.getAttribute("user");
		OrderForm orderForm = new OrderForm();
		orderForm.setDestinationName(user.getName());
		orderForm.setDestinationEmail(user.getEmail());
		orderForm.setDestinationZipcode(user.getZipcode());
		orderForm.setDestinationAddress(user.getAddress());
		orderForm.setDestinationTel(user.getTelephone());
		return orderForm;
	}

	@Autowired
	private HttpSession session;

	@Autowired
	private ConfirmOrderService confirmOrderService;

	/**
	 * 注文確認画面を表示する.
	 * 
	 * @param userId ユーザーID return 注文確認画面
	 */
	@RequestMapping("")
	public String showConfirmOrder(Integer userId, Model model) {
		Order order = confirmOrderService.showItemInCart(userId);
		int totalPrice = 0;
		for (OrderItem orderItem : order.getOrderItemList()) {
			totalPrice += orderItem.getSubTotal();
		}
		order.setTotalPrice(totalPrice);
		model.addAttribute("order", order);
		return "order_confirm";
	}
}
