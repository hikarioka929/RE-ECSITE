package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.DeleteItemInCartService;

/**
 * カート内商品を削除する時に操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class DeleteItemInCartController {

	@Autowired
	private DeleteItemInCartService deleteItemInCartService;

	/**
	 * カート内商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return カート内商品表示画面
	 */
	@RequestMapping("/delete")
	public String delete(Integer orderItemId) {
		deleteItemInCartService.deleteByOrderItemId(orderItemId);
		return "redirect:/item/inCart";
	}
}
