package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemService;

/**
 * 商品一覧表示を操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/item")
public class ShowItemController {
	
	@Autowired
	private ShowItemService showItemService;
	
	/**
	 * 商品一覧画面を表示する.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/all")
	public String showList(Model model) {
		List<Item> itemList = showItemService.showList();
		model.addAttribute("itemList", itemList);
		return "item_list_curry";
	}
	
	/**
	 * 商品を曖昧検索する.
	 * 
	 * @param name 検索した名前
	 * @return 検索結果
	 */
	@RequestMapping("/search")
	public String searchByLikeName(String name, Model model) {
		List<Item> itemList = showItemService.searchByLikeName(name);
		model.addAttribute("itemList", itemList);
		return "item_list_curry";
	}
}
