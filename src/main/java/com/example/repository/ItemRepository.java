package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class ItemRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER
	= new BeanPropertyRowMapper<>(Item.class);
	
	/**
	 * 全商品情報を取得する.
	 * 
	 * @return 全商品情報
	 */
	public List<Item> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY price_m;");
		List<Item> itemList = template.query(sql.toString(), ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 商品を曖昧検索する.
	 * 
	 * @param name 検索した名前
	 * @return 検索結果
	 */
	public List<Item> findByLikeName(String name){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE name LIKE :name ORDER BY price_m;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", '%' + name + '%');
		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
		if( itemList.size() == 0 ) {
			return null;
		}
		return itemList;
	}
}
