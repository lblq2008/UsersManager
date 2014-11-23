package com.lb.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCheckCode extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//禁止浏览器缓存图片
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		//通知浏览器以图片的方式打开发送过去的数据
		response.setHeader("Content-Type", "image/jpeg");
		
		//1.在内存中创建图片
		BufferedImage image = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
		//2.像图片中写入数据
		Graphics g = image.getGraphics();//生成画布
		g.setColor(Color.BLACK);//设置背景色
		g.fillRect(0, 0, 80, 30);//设置宽高
		
		g.setColor(Color.RED);//设置写入数据的颜色
		g.setFont(new Font(null, Font.BOLD, 20));//设置写入数据的字体
		
		String num = createNum();
		//把要写入的数据被容保存到Session
		request.getSession().setAttribute("checkCode", num);
		g.drawString(num, 0, 20);//向图片上写数据
		ImageIO.write(image, "jpg", response.getOutputStream());//把数据写回给浏览器
	}

	/**
	 * 生成随机数
	 * @return
	 */
	private String createNum() {
		// TODO Auto-generated method stub
		String num = new Random().nextInt(9999) + "" ;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4-num.length(); i++) {
			sb.append("0");
		}
		return sb.toString() + num;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
