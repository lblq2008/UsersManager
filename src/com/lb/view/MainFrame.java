package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.domain.User;

public class MainFrame extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入主页面...");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		Cookie[] cookies = request.getCookies();
		boolean isFirstLogin = true ;
		String lastLoginTime = "" ;
		if(cookies !=null){
			for (Cookie cookie : cookies) {
				if("lastLoginTime".equals(cookie.getName())){
					lastLoginTime = cookie.getValue();
					isFirstLogin = false ;
					cookie.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					cookie.setMaxAge(7*24*60*60);
					response.addCookie(cookie);
					break;
				}
			}
		}
		
		if(isFirstLogin){
			Cookie cookie = new Cookie("lastLoginTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			cookie.setMaxAge(7*24*60*60);
			response.addCookie(cookie);
		}
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>主界面</TITLE></HEAD>");
		out.println("  <H2>主界面</H2>");
		out.println("  <BODY>");
		out.print(" 欢迎你: " + request.getSession().getAttribute("username") + "");
		out.print(isFirstLogin?"欢迎您第一次登录<br>":" 上次登录时间: " + lastLoginTime + "本网站一共有" + this.getServletContext().getAttribute("nums") + "次访问"+ "<br>");
		out.print(" <br> <a href='/UsersManager/UsersManageServlet'>管理用户</a><br> ");
		out.print(" <br> <a href='/UsersManager/UserAddServlet'>添加用户</a><br> ");
		out.print(" <br> <a href='/UsersManager/UserSerachServlet'>查找用户</a><br> ");
		out.print(" <br> <a href='/UsersManager/LoginServlet'>退出登录</a><br> ");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
