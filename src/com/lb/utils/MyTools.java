package com.lb.utils;

import java.io.UnsupportedEncodingException;

public class MyTools {
	public static String getUtf8(String text){
		try {
			return new String(text.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text ;
	}
	
	public static String getGBK(String text){
		try {
			return new String(text.getBytes("iso-8859-1"), "gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text ;
	}
}
