package com.lb.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.domain.User;
import com.lb.service.UsersService;

public class UserCLServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// 如果能使用forward就是用forward，否则使用sendRedirect
		UsersService usersService = new UsersService();
		String type = request.getParameter("type");
		if ("delete".equals(type)) {//删除处理
			forword(usersService.deleteUser(request.getParameter("id")), "恭喜你,删除成功!","对不起,删除失败!", request, response);
		}else if("updateView".equals(type)){//转到更新页面
			request.getRequestDispatcher("/UpdateView?id=" + request.getParameter("id")).forward(request,response);
		}else if("doUpdate".equals(type)){//数据更新
			String id = request.getParameter("id");
			String username = request.getParameter("username");
			String realname = request.getParameter("realname");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			User user = new User(Integer.parseInt(id), username, realname, password, email, Integer.parseInt(grade));
			forword(usersService.updateUser(user), "恭喜你,修改成功!","对不起,修改失败!", request, response);
		}else if("doAdd".equals(type)){//数据添加
			String username = request.getParameter("username");
			String realname = request.getParameter("realname");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String grade = request.getParameter("grade");
			User user = new User(username, realname, password, email, Integer.parseInt(grade));
			forword(usersService.addUser(user), "恭喜你,添加成功!","对不起,添加失败!", request, response);
		}
	}

	private void forword(boolean flag,String ok , String error,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (flag) {
			request.getRequestDispatcher("/OKServlet?propmt=" + ok).forward(request,response);
		} else {
			request.getRequestDispatcher("/ErrorServlet?propmt=" + error).forward(request,response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
