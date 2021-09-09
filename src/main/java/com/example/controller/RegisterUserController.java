package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録を操作するコントローラ.
 * 
 * @author okahikari
 * 
 */
@Controller
@RequestMapping("/user")
public class RegisterUserController {
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	/**
	 * ユーザー登録画面を表示する.
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/toSignUp")
	public String toSignUp() {
		return "register_user";
	}
	
	/**
	 * ユーザーを登録する.
	 * 
	 * @param user ユーザー情報
	 * @return ログイン画面
	 */
	@RequestMapping("/signUp")
	public String signUp(@Validated RegisterUserForm form, BindingResult result, Model model) {
		
		User user = new User();
		BeanUtils.copyProperties(form, user);
		
		//メールアドレスが既に登録されている場合にエラーを追加
		if( registerUserService.searchByEmail(user.getEmail()) != null ) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}
		
		//パスワードと確認用パスワードが一致しなかったらエラーを追加
		if( !(form.getPassword().equals(form.getConfirmPassword())) ) {
			result.rejectValue("confirmPassword", "", "パスワードと確認用パスワードが不一致です");
		}
		
		//エラーがあった場合にユーザー登録画面をreturnする
		if( result.hasErrors() ) {
			return toSignUp();
		}
		
		registerUserService.insert(user);
		return "login";
	}
}
