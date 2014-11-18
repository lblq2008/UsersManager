package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.domain.User;
import com.lb.service.UsersService;

public class UsersManageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("进入用户管理页面...");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>用户管理</TITLE></HEAD>");
		out.print("<h2>用户管理页面<h2>");
		out.println("<BODY>");
		out.println("<table border='1' width='500px'>");
		out
				.println("<th>编号</th><th>账号</th><th>姓名</th><th>密码</th><th>邮箱</th><th>权限</th>");

		// 定义分页变量
		int pageNow = 1;// 当前页面

		int pageSize = 3;// 每页的条数
		int pageCount = 0;// 总共的页数
		int rowCount = 0;// 数据总条数

		rowCount = new UsersService().getRowCounts();
		System.out.println("共有:  " + rowCount + " 条数据...");
		pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;

		String sPageNow = request.getParameter("pageNow");
		if (sPageNow != null) {
			pageNow = Integer.parseInt(sPageNow);

		}
		pageNow = (pageNow = pageNow >= pageCount ? pageCount : pageNow) > 0 ? pageNow : 1;

		System.out.println("当前显示第 " + pageNow + " 页...");

		List<User> list = new UsersService().getResultSetInt((pageNow - 1) * pageSize, pageSize );
		
		System.out.println("当前显示:  " + list.size() + " 条数据...");
		
		for (User user : list) {
			out.println("<tr><td>" + user.getId() + "</td><td>"
					+ user.getUserName() + "</td><td>" + user.getRealName()
					+ "</td><td>" + user.getPassWord() + "</td><td>"
					+ user.getEmail() + "</td><td>" + user.getGrade()
					+ "</td></tr>");
		}

		out.println("</table>");
		if (pageNow > 1) {
			// 显示上一页
			out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
					+ (pageNow - 1) + "'> " + "上一页 " + " </a>");
		}
		for (int i = 1; i <= pageCount; i++) {
			out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
					+ i + "'> <" + i + "> </a>");
		}
		if (pageNow < pageCount) {
			// 显示下一页
			out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
					+ (pageNow + 1) + "'> " + "下一页 " + " </a>");
		}
		// 显示 当前页/总页数
		out.println("&nbsp;&nbsp;&nbsp;[" + pageNow + "/" + pageCount + "]");
		// 显示 跳转到的功能
		out
				.println(""
						+ "<form action='/UsersManager/UsersManageServlet' method='post'>跳转到:&nbsp;<input type='number' name='pageNow'/>"
						+ "<input type='submit' value='GO'/></form>");

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
