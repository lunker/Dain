package com.dain;


public class UserProfile {
	public final static int male=0;
	public final static int female=1;
	String id;
	String nickname;
	String password;
	String birth;
	int gender;
	
	public UserProfile(String id, String nickname, String password,
			String birth, int gender) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.birth = birth;
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getBirth() {
		return birth;
	}

	public int getGender() {
		return gender;
	}
	
	
	

}
