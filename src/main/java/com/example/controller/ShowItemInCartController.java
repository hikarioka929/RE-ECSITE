package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.service.ShowItemInCartService;

/**
 * カート内商品表示時に操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class ShowItemInCartController {

	@Autowired
	private ShowItemInCartService showItemInCartService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/inCart")
	public String inCart(Model model) {
		Order order = new Order();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			order = showItemInCartService.showItemInCart(user.getId());
		} else {
			order = showItemInCartService.showItemInCart(1);
		}

		if (order == null) {
			model.addAttribute("nullMessage", "カートに商品がありません");
		} else if (order.getOrderItemList().size() == 0) {
			model.addAttribute("nullMessage", "カートに商品がありません");
		} else {
			int totalPrice = 0;
			for (OrderItem orderItem : order.getOrderItemList()) {
				totalPrice += orderItem.getSubTotal();
			}
			order.setTotalPrice(totalPrice);
			if (order.getTotalPrice() == 0) {
				model.addAttribute("nullMessage", "カートに商品がありません");
			}
		}
		model.addAttribute("order", order);
		return "cart_list";
	}
}
