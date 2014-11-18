package com.lb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.domain.User;
import com.lb.service.UsersService;


public class LoginCLServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("登录处理中...");
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		User user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		if(new UsersService().checkUser(user)){
			//成功则跳转到主页面MainFrame
			System.out.println("恭喜:密码验证正确...");
			request.getSession().setAttribute("username", username);
			request.getRequestDispatcher("/MainFrame").forward(request, response);
		}else{
			//返回登陆页面
			System.out.println("哎呀:用户名或密码有误...");
			request.setAttribute("err", "用户名或密码错误");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
