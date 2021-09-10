package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ユーザーログインを操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/user")
public class LoginUserController {
	
	@Autowired
	private LoginUserService loginUserService;
	
	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		return new LoginUserForm();
	}
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ユーザーログイン画面を登録する.
	 * 
	 * @return ユーザーログイン画面
	 */
	@RequestMapping("/toSignIn")
	public String toSignIn() {
		return "login";
	}
	
	/**
	 * ログインする.
	 * 
	 * @param form ログインフォーム
	 * @return 商品一覧画面
	 */
	@RequestMapping("/signIn")
	public String singIn( LoginUserForm form, Model model ) {
		
		User user = loginUserService.searchByEmailAndPassword(form.getEmail(), form.getPassword());
		
		if( user != null ) {
			session.setAttribute("user", user);
			//後でitem_list_curryにredirectするような処理を書く
			return "item_list_curry";
		} else {
			model.addAttribute("errorMessage", "メールアドレス、またはパスワードが間違っています");
			return toSignIn();
		}
	}
}
