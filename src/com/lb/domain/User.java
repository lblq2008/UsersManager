package com.lb.domain;

public class User {
	private int id ;
	private String userName ;
	private String realName ;
	private String passWord ;
	private String email ;
	private int grade ;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public User(int id, String userName, String realName, String passWord,
			String email, int grade) {
		this.id = id;
		this.userName = userName;
		this.realName = realName;
		this.passWord = passWord;
		this.email = email;
		this.grade = grade;
	}
	public User() {
		super();
	}
	public User(String userName, String realName, String passWord,
			String email, int grade) {
		this.userName = userName;
		this.realName = realName;
		this.passWord = passWord;
		this.email = email;
		this.grade = grade;
	}
	
}
