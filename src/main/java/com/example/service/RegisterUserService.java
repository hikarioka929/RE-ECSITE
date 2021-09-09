package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー登録時に業務処理を行うサービスクラス.
 * 
 * @author okahikari
 * 
 */
@Service
@Transactional
public class RegisterUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザーを登録する.
	 * メールアドレスが既に登録されていたら、コントローラに返す.
	 * 
	 * @param user 登録するユーザー情報
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User searchByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
