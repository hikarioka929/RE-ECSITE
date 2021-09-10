package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemAllService;

/**
 * 商品一覧表示を操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class ShowItemAllController {
	
	@Autowired
	private ShowItemAllService showItemAllService;
	
	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/all")
	public String showList(Model model) {
		List<Item> itemList = showItemAllService.showList();
		model.addAttribute("itemList", itemList);
		return "item_list_curry";
	}
}
