package com.lb.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class UsersManageServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("进入用户管理页面...");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>用户管理</TITLE></HEAD>");
		out.print("<h3>用户管理页面<h3><br>");
		out.println("<BODY>");
		out.println("<table border='1' width='500px'>");
		out.println("<th>编号</th><th>账号</th><th>姓名</th><th>密码</th><th>邮箱</th><th>权限</th>");
		
		//获取数据
		Connection conn = null ;
		PreparedStatement ptmt = null ;
		ResultSet rs = null ;
		String sql_url = "jdbc:mysql://localhost:3306/lb_database?user=root&password=admin" ;
		String sql_query = "select * from users" ;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(sql_url);
			ptmt = conn.prepareStatement(sql_query);
			rs = ptmt.executeQuery();
			while(rs.next()){
				out.println("<tr><td>" + rs.getInt(1) +"</td><td>" 
						+ rs.getString(2) + "</td><td>" 
						+ rs.getString(3) +"</td><td>" 
						+ rs.getString(4) +"</td><td>" 
						+ rs.getString(5) + "</td><td>" 
						+ rs.getInt(6) +"</td></tr>");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rs = null ;
			}
			if(ptmt != null){
				try {
					ptmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ptmt = null ;
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				conn = null ;
			}
		}
		out.println("</table>");
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
