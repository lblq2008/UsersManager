package com.lb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lb.service.UsersService;

public class DeleteCLServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//如果能使用forward就是用forward，否则使用sendRedirect
		if (new UsersService().deleteUser(request.getParameter("id"))) {
			request.getRequestDispatcher("/OKServlet").forward(request, response);
		}else{
			request.getRequestDispatcher("/ErrorServlet").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
