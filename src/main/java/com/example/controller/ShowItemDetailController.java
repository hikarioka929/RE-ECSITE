package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;

/**
 * 商品詳細を表示する操作をするコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class ShowItemDetailController {
	
	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	/**
	 * 商品詳細画面を表示する.
	 * 
	 * @param id 商品ID
	 * @return 商品詳細画面
	 */
	@RequestMapping("/")
	public String showDetail(Integer id, Model model) {
		Item item = showItemDetailService.searchById(id);
		model.addAttribute("item", item);
		return "item_detail";
	}
}