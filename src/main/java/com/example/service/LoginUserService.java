package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
  * ユーザーログイン時に業務処理を行うサービスクラス.
  * 
  * @author okahikari
  * 
  */
@Service
@Transactional
public class LoginUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * メールアドレスとパスワードからユーザーを検索する.
	 * 
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public User searchByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
}