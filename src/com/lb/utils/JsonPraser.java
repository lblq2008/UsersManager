package com.lb.utils;

import com.alibaba.fastjson.JSONObject;
import com.lb.domain.User;

public class JsonPraser {
	public static JsonPraser jsonPraser ;
	public static JsonPraser getInstance(){
		if(jsonPraser == null){
			jsonPraser = new JsonPraser();
		}
		return jsonPraser ;
	}
	
	public  User parseUser(String json){
		if(json == null || json.equals("")){
			return null ;
		}
		User user = new User();
		JSONObject jsonObject = JSONObject.parseObject(json);
		user.setUserName(jsonObject.getString("userName"));
		user.setPassWord(jsonObject.getString("passWord"));
		return user ;
	} 
}
