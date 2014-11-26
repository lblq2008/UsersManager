package com.lb.appserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.lb.domain.User;
import com.lb.service.UsersService;
import com.lb.utils.JsonPraser;

public class AppLogin extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String param = request.getParameter("param");
		System.out.println("param: " + param);
		User user = JsonPraser.getInstance().parseUser(param);
		boolean isSuccess= new UsersService().checkUser(user);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject  =new JSONObject();
		jsonObject.put("isSuccess", isSuccess);
		if(isSuccess){
			jsonObject.put("message", "登录成功");
		}else{
			jsonObject.put("message", "登录失败,用户名或密码错误");
		}
		out.println(jsonObject.toJSONString());
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
