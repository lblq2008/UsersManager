package com.lb.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitDataServlet extends HttpServlet {

	
	public void destroy() {
		super.destroy(); 
		// Web应用关闭时调用
		writeCounter();
	}


	private void writeCounter() {
		String path = this.getServletContext().getRealPath("counter.txt");
		FileWriter fileWriter = null ;
		BufferedWriter bufferedWriter = null ;
		try {
			fileWriter = new FileWriter(path);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(this.getServletContext().getAttribute("nums").toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(bufferedWriter != null){
					bufferedWriter.close();
				}
				if(fileWriter != null){
					fileWriter.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

	
	public void init() throws ServletException {
		// Web应用启动时调用
		readCounter();
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				writeCounter();
			}
		}).start();
	}


	private void readCounter() {
		//读取网站计数的内容
		String path = this.getServletContext().getRealPath("counter.txt");
		FileReader fileReader= null ;
		BufferedReader bufferedReader= null ;
		try {
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);
			String nums = bufferedReader.readLine();
			this.getServletContext().setAttribute("nums", nums);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bufferedReader != null){
					bufferedReader.close();
				}
				if(fileReader != null){
					fileReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
