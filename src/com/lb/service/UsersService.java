package com.lb.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lb.domain.User;
import com.lb.utils.SqlHelper;

/**
 * 对User表进行操作
 *@author Administrator
 */
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
	
	/**
	 * 获取数据总条数
	 * @return
	 */
	public int getRowCount(){
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
	 * 获取数据总条数
	 * @return
	 */
	public int getSearchRowCount(String searchName){
		int rowCount = 0 ;
		String count_sql = "select count(*) from users where realname=" + searchName ;
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
	 * 获取页数
	 * @param pageSize
	 * @return
	 */
	public int getSearchPageCount(int pageSize , String searchName){
		int rowCount = 0 ;
		String count_sql = "" ;
		String[] parameters = {searchName} ;
		if(searchName == null){
			count_sql = "select count(*) from users" ;
			parameters = null ;
		}else{
			count_sql = "select count(*) from users where realname=?";
		}
		ResultSet rs = SqlHelper.executeQuery(count_sql, parameters);;
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		//(rowCount -1)/pageSize + 1;
		return rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
	}
	
	public int getSearchFuzzyPageCount(int pageSize , String searchName){
		int rowCount = 0 ;
		String count_sql = "" ;
		String[] parameters = {searchName} ;
		if(searchName == null){
			count_sql = "select count(*) from users" ;
			parameters = null ;
		}else{
			count_sql = "select count(*) from users where realname like '%?%' ";
		}
		ResultSet rs = SqlHelper.executeQuery(count_sql, parameters);;
		try {
			rs.next();
			rowCount = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		//(rowCount -1)/pageSize + 1;
		return rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
	}
	
	
	
	/**
	 * 获取页数
	 * @param pageSize
	 * @return
	 */
	public int getPageCount(int pageSize){
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
		//(rowCount -1)/pageSize + 1;
		return rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user){
		String sql= "insert into users(username , realname , password , email , grade) values(?,?,?,?,?)";
		String[] parameters={user.getUserName(),user.getRealName(),user.getPassWord(),user.getEmail(),user.getGrade()+""};
		try {
			SqlHelper.executeUDI(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false ;
		}
		return true ;
	}
	
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public boolean deleteUser(String id){
		String sql = "delete from users where id = ?" ;
		String[] parameters = {id};
		try {
			SqlHelper.executeUDI(sql, parameters );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false ;
		}
		return true ;
	}
	
	
	/**
	 * 更新用户信息
	 * @param user 用户数据
	 * @return
	 */
	public boolean updateUser(User user){
		String sql= "update users set username=? , realname=? , password=? , email=? , grade=? where id=?";
		String[] parameters={user.getUserName(),user.getRealName(),user.getPassWord(),user.getEmail(),user.getGrade()+"",user.getId() + ""};
		try {
			SqlHelper.executeUDI(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false ;
		}
		return true ;
	}
	
	/**
	 * 查询数据
	 * @param id  用户id
	 * @return
	 */
	public User getUserById(String id){
		String sql = "select * from users where id = ?" ;
		User user = new User() ;
		Object[] parameters = {id};
		ResultSet rs = SqlHelper.executeQueryObject(sql, parameters);
		try {
			if (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUserName(rs.getString(2));
				user.setRealName(rs.getString(3));
				user.setPassWord(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setGrade(rs.getInt(6));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			SqlHelper.close(rs, SqlHelper.getPtmt(), SqlHelper.getCt());
		}
		return user ;
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
	
	/**
	 * 精确查询分页获取数据
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<User> getQueryPrecise(String realName,int start ,int pageSize ){
		String sql = " select * from users where realname = ? limit ?,? " ;
		Object[] parameters = {realName,start,pageSize};
		List<User> list = new ArrayList<User>();
		ResultSet rs = SqlHelper.executeQueryObject(sql, parameters);
		try {
			while (rs.next()) {
				User user = new User() ;
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
	
	/**
	 * 精确查询分页获取数据
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<User> getQueryFuzzy(String realName,int start ,int pageSize ){
		String sql = " select * from users where realname like '%?%' limit ?,? " ;
		Object[] parameters = {realName,start,pageSize};
		List<User> list = new ArrayList<User>();
		ResultSet rs = SqlHelper.executeQueryObject(sql, parameters);
		try {
			while (rs.next()) {
				User user = new User() ;
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
