package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.AddItemToCartForm;
import com.example.service.AddItemToCartService;

/**
 * カートに商品追加時に操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class AddItemToCartController {

	@Autowired
	private AddItemToCartService addItemToCartService;

	@ModelAttribute
	public AddItemToCartForm setUpForm() {
		return new AddItemToCartForm();
	}

	@Autowired
	private HttpSession session;

	/**
	 * カートに商品を追加する.
	 * 
	 * @param form フォーム
	 * @return カート内一覧表示画面
	 */
	@RequestMapping("/add")
	public String add(AddItemToCartForm form) {

		User user = (User) session.getAttribute("user");
		if (user != null) {
			addItemToCartService.add(form, user.getId(), 0);
		} else {
			addItemToCartService.add(form, 1, 0);
		}
		// 後でカート内一覧表示画面にリダイレクトするような処理を書く
		return "cart_list";
	}
}
