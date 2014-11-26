package com.lb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		
		String checkCode = (String) request.getSession().getAttribute("checkCode");
		if(!checkCode.equals(request.getParameter("checkcode"))){
			//返回登陆页面
			System.out.println("哎呀:验证码错误...");
			request.setAttribute("err", "验证码错误");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			return;
		}else{
			System.out.println("验证码正确...");
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String isSaveLoginInfo = request.getParameter("isSaveLoginInfo");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		User user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		if(new UsersService().checkUser(user)){
			//回写Cookie保存用户名和密码
			if(isSaveLoginInfo!=null && isSaveLoginInfo.equals("true")){
				Cookie cookieUname = new Cookie("username", username);
				cookieUname.setMaxAge(7*24*60*60);
				response.addCookie(cookieUname);
				
				Cookie cookieUpwd = new Cookie("password", password);
				cookieUpwd.setMaxAge(7*24*60*60);
				response.addCookie(cookieUpwd);
				
				Cookie cookieSaveLoginInfo = new Cookie("isSaveLoginInfo", isSaveLoginInfo);
				cookieSaveLoginInfo.setMaxAge(7*24*60*60);
				response.addCookie(cookieSaveLoginInfo);
			}
			//成功则跳转到主页面MainFrame
			this.getServletContext().setAttribute("nums", (Integer.parseInt(this.getServletContext().getAttribute("nums").toString()) + 1) + "");
			User userInfo = new User();
			userInfo.setUserName(username);
			userInfo.setPassWord(password);
			request.getSession().setAttribute("loginuser", userInfo);
			
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
