package com.lb.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lb.domain.User;
import com.lb.utils.SqlHelper;

public class UsersService {
	/**
	 * 判断用户名和 密码是否合法
	 * @return
	 */
	public boolean checkUser(User user){
		boolean isChecked = false ;
		String sql = "select * from users where username = ? and password = ?" ;
		String[] parameters = {user.getUserName(),user.getPassWord()};
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		try {
			if(rs!=null){
				isChecked = rs.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		return isChecked ;
	}
	
	public int getRowCounts(){
		int rowCount = 0 ;
		String count_sql = "select count(*) from users" ;
		ResultSet rs = SqlHelper.executeQuery(count_sql, null);;
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		return rowCount ;
	}


	/**
	 * 分页获取数据
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<User> getResultSetObject(int start ,int pageSize){
		String sql = "select * from users limit ?,?" ;
		Object[] parameters = {start,pageSize };
		List<User> list = new ArrayList<User>();
		ResultSet rs = SqlHelper.executeQueryObject(sql, parameters);
		try {
			while (rs.next()) {
				User user = new User() ;
//				user.setId(rs.getInt(rs.getString("id")));
//				user.setUserName(rs.getString(rs.getString("username")));
//				user.setRealName(rs.getString(rs.getString("realname")));
//				user.setPassWord(rs.getString(rs.getString("password")));
//				user.setEmail(rs.getString(rs.getString("email")));
//				user.setGrade(rs.getInt(rs.getString("grade")));
				user.setId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setRealName(rs.getString(3));
				user.setPassWord(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setGrade(rs.getInt(6));
				list.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		return list;
	}
	
}
