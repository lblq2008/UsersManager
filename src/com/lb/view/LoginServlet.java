package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入用户登录页面...");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>用户登录</TITLE></HEAD>");
		out.println("<H1>用户登录</H1>");
		out.println("<BODY>");
		//action 的写法： /web应用名称/目标Servlet的url
		out.print("<form action='/UsersManager/LoginCLServlet' method='post'>");
		out.print("用户名: <input type='text' name='username'/><br><br>");
		out.print("密　 码: <input type='password' name='password'/><br><br>");
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
