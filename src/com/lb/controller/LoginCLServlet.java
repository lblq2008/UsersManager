package com.lb.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginCLServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("登录处理中...");
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		String mysql_url = "jdbc:mysql://localhost:3306/lb_database?user=root&password=admin";
		String querySQL = "select * from users where username = ? and password = ?" ;
		Connection conn = null ;
		PreparedStatement ptmt = null ;
		ResultSet rs = null ;
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//得到链接
			conn = DriverManager.getConnection(mysql_url);
			//创建PreparedStatement
			ptmt = conn.prepareStatement(querySQL);
			ptmt.setObject(1, username);
			ptmt.setObject(2, password);
			//执行操作
			rs = ptmt.executeQuery();
			//结果处理
			if(rs.next()){
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
			rs.close();
			ptmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs = null ;
			}
			
			if(ptmt != null){
				try {
					ptmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ptmt = null ;
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
