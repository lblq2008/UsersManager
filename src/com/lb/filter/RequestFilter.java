package com.lb.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lb.domain.User;
/**
 * 1.必须在web.xml里进行filter进行配置，否则无法使用
 * 2.继承httpservlet并实现filter接口
 * 3.在doFilter中写入逻辑业务代码
 */
public class RequestFilter extends HttpServlet implements Filter {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("--RequestFilter checked--");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request ;
		String uri = httpServletRequest.getRequestURI();
		System.out.println("请求URI：" + uri);
		if(uri.startsWith("/UsersManager/CreateCheckCode")||uri.startsWith("/UsersManager/imgs")||uri.startsWith("/UsersManager/Login")||uri.startsWith("/UsersManager/AppLogin")){
			//放行
			chain.doFilter(request, response);
		}else{
			HttpSession session = httpServletRequest.getSession();
			User user = (User) session.getAttribute("loginuser");
			if(user != null){
				//该用户合法，放行
				chain.doFilter(request, response);
			}else{
				request.setAttribute("err", "非法用户，请重新登录");
				httpServletRequest.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
		}
	}

}
