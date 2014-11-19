package com.lb.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlHelper {

	// 定义静态变量
	private static Connection conn = null;
	private static PreparedStatement ptmt = null;// 防止sql注入
	private static ResultSet rs = null;
	private static CallableStatement cst = null;

	// 数据库连接参数
	private static String url = "";
	private static String driver = "";
	private static String username = "";
	private static String password = "";

	// 读取配置文件
	private static Properties pp = null;
	private static InputStream fis = null;

	// 只需执行一次
	static {
		try {
			pp = new Properties();
			//fis = new FileInputStream("dbinfo.properties");
			//当是java web项目的时候，读取文件需要使用类加载器【因为类加载器读取资源的时候，的默认目录是src】
			fis = SqlHelper.class.getClassLoader().getResourceAsStream("dbinfo.properties");
			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			password = pp.getProperty("password");
			driver = pp.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fis = null;
			}
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnect() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static CallableStatement callPro2(String sql, String[] inparameters,
			Integer[] outparamerters) {
		try {
			conn = getConnect();
			cst = conn.prepareCall(sql);
			if (inparameters != null) {
				for (int i = 0; i < inparameters.length; i++) {
					cst.setObject(i + 1, inparameters[i]);
				}
			}
			if (outparamerters != null) {
				for (int i = 0; i < outparamerters.length; i++) {
					cst.registerOutParameter(inparameters.length + 1 + i,
							outparamerters[i]);
				}
			}
			cst.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cst;
	}

	public static void callPro1(String sql, String[] parameters) {
		try {
			conn = getConnect();
			cst = conn.prepareCall(sql);
			// ？号赋值
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					cst.setObject(i + 1, parameters[i]);
				}
			}
			cst.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cst, conn);
		}
	}

	public static ResultSet executeQuery(String sql, String[] parameters) {
		try {
			conn = getConnect();
			ptmt = conn.prepareStatement(sql);
			if (parameters != null && !parameters.equals("")) {
				for (int i = 0; i < parameters.length; i++) {
					ptmt.setString(i + 1, parameters[i]);
				}
			}
			rs = ptmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			//close(rs, ptmt, conn);
		}
		return rs;
	}
	
	public static ResultSet executeQueryInt(String sql, int[] parameters) {
		try {
			conn = getConnect();
			ptmt = conn.prepareStatement(sql);
			if (parameters != null && !parameters.equals("")) {
				for (int i = 0; i < parameters.length; i++) {
					ptmt.setInt(i + 1, parameters[i]);
				}
			}
			rs = ptmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			//close(rs, ptmt, conn);
		}
		return rs;
	}
	
	public static ResultSet executeQueryObject(String sql, Object[] parameters) {
		try {
			conn = getConnect();
			ptmt = conn.prepareStatement(sql);
			if (parameters != null && !parameters.equals("")) {
				for (int i = 0; i < parameters.length; i++) {
					ptmt.setObject(i + 1, parameters[i]);
				}
			}
			rs = ptmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			//close(rs, ptmt, conn);
		}
		return rs;
	}

	// 如果有多个update/delete/insert [需要考虑事务]
	public static void executeUDIS(String[] sql, String[][] parameters) {
		try {
			// 获取连接
			conn = getConnect();
			// 因为可能传入多个sql语句
			conn.setAutoCommit(false);
			for (int i = 0; i < sql.length; i++) {
				if (parameters[i] != null) {
					ptmt = conn.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++) {
						ptmt.setObject(j + 1, parameters[i][j]);
					}
					ptmt.executeUpdate();
				}
			}
			// int i = 9/0 ;
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 回滚
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			close(rs, cst, conn);
		}
	}

	public static void executeUDI(String sql, String[] parameters) {
		try {
			// 获取连接
			conn = getConnect();
			ptmt = conn.prepareStatement(sql);
			// 给？赋值
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ptmt.setObject(i + 1, parameters[i]);
				}
			}
			// 执行
			ptmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cst, conn);
		}
	}

	public static void close(ResultSet rs, Statement st,
			Connection conn) {
		// TODO Auto-generated method stub
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rs = null;
		}

		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			st = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static Connection getCt() {
		return conn;
	}

	public static PreparedStatement getPtmt() {
		return ptmt;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static CallableStatement getCst() {
		return cst;
	}
}
