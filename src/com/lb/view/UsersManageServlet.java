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
import com.lb.utils.MyTools;

public class UsersManageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("进入用户管理页面...");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function confirmAlert(){return window.confirm('真的要删除吗?');}");
		out.println("</script>");
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>用户管理</TITLE></HEAD>");
		out.print("<h2>用户管理页面<h2><hr>");
		out.println("<BODY>");
		out.println("<table border='1' width='500px'>");
		out.println("<th>编号</th><th>账号</th><th>姓名</th><th>密码</th><th>邮箱</th><th>权限</th><th>删除</th><th>修改</th>");

		//定义数据库操作类对象
		UsersService usersService = new UsersService();
		// 定义分页变量
		int pageNow = 1;// 当前页面
		int pageSize = 3;// 每页的条数
		int pageCount = 0;// 总共的页数
		pageCount = usersService.getPageCount(pageSize);
		String sPageNow = request.getParameter("pageNow");
		if (sPageNow != null) {
			pageNow = Integer.parseInt(sPageNow);
		}
		pageNow = (pageNow = pageNow >= pageCount ? pageCount : pageNow) > 0 ? pageNow : 1;
		System.out.println("当前显示第 " + pageNow + " 页...");
		
		List<User> list = usersService.getResultSetObject((pageNow - 1) * pageSize, pageSize );
		System.out.println("当前显示:  " + list.size() + " 条数据...");
		
		for (User user : list) {
			out.println("<tr><td>" + user.getId() + "</td><td>"
					+ user.getUserName() + "</td><td>" + user.getRealName()
					+ "</td><td>" + user.getPassWord() + "</td><td>"
					+ user.getEmail() + "</td><td>" + user.getGrade()
					+ "</td> <td><a onClick='confirmAlert()' href='/UsersManager/UserCLServlet?type=delete&id=" + user.getId()+ "'>删除</a></td> <td><a href='/UsersManager/UserCLServlet?type=updateView&id=" + user.getId()+ "'>修改</a></td></tr>");
		}

		out.println("</table>");
		if (pageNow > 1) {
			// 显示上一页
			out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
					+ (pageNow - 1) + "'> " + "上一页 " + " </a>");
		}
		for (int i = 1; i <= pageCount; i++) {
			if(i == pageNow){//当前页码显示成红色
				out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
						+ i + "'> <<font color='red'>" + i + "</font>> </a>");
			}else{
				out.println("<a href='/UsersManager/UsersManageServlet?pageNow="
						+ i + "'> <" + i + "> </a>");
			}
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

		out.println("</BODY><hr>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
