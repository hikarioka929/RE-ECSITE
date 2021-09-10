package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author okahikari
 * 
 */
@Repository
public class UserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<User> USER_ROW_MAPPER
	= new BeanPropertyRowMapper<>(User.class);
	
	/**
	 * ユーザー情報を挿入する.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO users (name, email, password, zipcode, address, telephone) VALUES (:name, :email, :password, :zipcode, :address, :telephone);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql.toString(), param);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE email = :email;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if( userList.size() == 0 ) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * メールアドレスとパスワードからユーザー情報を取得する.
	 * 
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public User findByEmailAndPassword(String email, String password) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE email = :email AND password = :password");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if( userList.size() == 0 ) {
			return null;
		}
		return userList.get(0);
	}
}