package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAddServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入用户添加页面...");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>用户添加</TITLE></HEAD>");
		out.print("<h3>用户添加页面<h3><br>");
		out.print("<form action='/UsersManager/UserCLServlet?type=doAdd' method='post'");
		out.println("  <BODY>");
		out.print("<table border='1' width='250px'>");
		out.print("<th>名称</th><th>内容</th>");
		out.print("<tr><td>账号</td><td><input type='text' name='username' ></td></tr>");
		out.print("<tr><td>姓名</td><td><input type='text' name='realname' ></td></tr>");
		out.print("<tr><td>密码</td><td><input type='text' name='password' ></td></tr>");
		out.print("<tr><td>邮箱</td><td><input type='text' name='email' ></td></tr>");
		out.print("<tr><td>级别</td><td><input type='number' name='grade'></td></tr>");
		out.println("<table>");
		out.println("<input type='submit' value='提交数据'>");
		out.println("</BODY>");
		out.println("</form>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
