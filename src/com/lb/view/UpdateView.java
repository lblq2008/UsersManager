package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.domain.User;
import com.lb.service.UsersService;

public class UpdateView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		User user = new UsersService().getUserById(request.getParameter("id") );
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("  <h2>用户修改</h2>");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>用户修改</TITLE></HEAD>");
		out.print("<form action='/UsersManager/UserCLServlet?type=doUpdate' method='post'");
		out.println("  <BODY>");
		out.print("<table border='1' width='250px'>");
		out.print("<th>名称</th><th>内容</th>");
		out.print("<tr><td>编号</td><td><input type='text' name='id' readonly value='" + user.getId() + "'></td></tr>");
		out.print("<tr><td>账号</td><td><input type='text' name='username' value='" + user.getUserName() + "'></td></tr>");
		out.print("<tr><td>姓名</td><td><input type='text' name='realname' value='" + user.getRealName() + "'></td></tr>");
		out.print("<tr><td>密码</td><td><input type='text' name='password' value='" + user.getPassWord() + "'></td></tr>");
		out.print("<tr><td>邮箱</td><td><input type='text' name='email' value='" + user.getEmail() + "'></td></tr>");
		out.print("<tr><td>级别</td><td><input type='number' name='grade' value='" + user.getGrade() + "'></td></tr>");
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
