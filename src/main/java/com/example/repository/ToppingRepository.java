package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

/**
 * toppingsテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class ToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER
	= new BeanPropertyRowMapper<>(Topping.class);
	
	/**
	 * 全トッピング情報を取得する.
	 * 
	 * @return 全トッピング情報
	 */
	public List<Topping> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, price_m, price_l FROM toppings;");
		return template.query(sql.toString(), TOPPING_ROW_MAPPER);
	}
}
