package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ユーザーログアウトを操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/user")
public class LogoutUserController {
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ログアウトをする.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/signOut")
	public String signOut() {
		session.invalidate();
		return "redirect:/item/all";
	}
}
