package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入用户登录页面...");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		//将Session的id写回到浏览器的cookie里面
		Cookie cookieQ = new Cookie("JSESSIONID",request.getSession().getId());
		cookieQ.setMaxAge(30*60);
		response.addCookie(cookieQ);
		
		String username = "";
		String password = "";
		String isSaveLoginInfo = "";
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie:cookies) {
				if("username".equals(cookie.getName())){
					username = cookie.getValue();
				}else if("password".equals(cookie.getName())){
					password = cookie.getValue();
				}else if("isSaveLoginInfo".equals(cookie.getName())){
					isSaveLoginInfo = cookie.getValue();
				}
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>用户登录</TITLE></HEAD>");
		out.println("<H1>用户登录</H1>");
		out.println("<BODY>");
		//action 的写法： /web应用名称/目标Servlet的url
		out.print("<form action='/UsersManager/LoginCLServlet' method='post'>");
		out.print("用户名: <input type='text' name='username' value='" + username + "'/><br><br>");
		out.print("密　码: <input type='password' name='password' value='" +password + "'/><br><br>");
		out.print("验证码: <input type='text' name='checkcode' /><img src='/UsersManager/CreateCheckCode'/><br><br>");
		out.print("<input type='checkbox' name='isSaveLoginInfo' "+ (isSaveLoginInfo.equals("true")?"checked":"") +" value='true'/>记住用户名和密码<br><br>");
		Object err = request.getAttribute("err");
		if(err !=null){
			out.print("<font color='red'/>" + err +"</font><br><br>");
		}
		request.setAttribute("err", null);
		out.print("<input type='submit' value='登录'/><br>");
		out.println("</form>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
