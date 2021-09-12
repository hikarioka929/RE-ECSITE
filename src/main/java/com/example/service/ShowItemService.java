package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品一覧表示時に業務処理を行うサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class ShowItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 全商品情報を取得する.
	 * 
	 * @return 全商品情報
	 */
	public List<Item> showList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}
	
	/**
	 * 商品を曖昧検索する.
	 * 
	 * @param name 検索した名前
	 * @return 検索結果
	 */
	public List<Item> searchByLikeName(String name){
		if( name == null ) {
			return itemRepository.findAll();
		}
		List<Item> itemList = itemRepository.findByLikeName(name);
		if( itemList.size() == 0 ) {
			return null;
		}
		return itemList;
	}
}
