package com.tiny.publicAccount.wechatsupport;

/**
 * 
 * the user object is correspond to the user object in weixin
 */
public class WeChatUser {
	
	public final static int SEX_FEMALE =0;//女
	public final static int SEX_MALE =1;//1男
	private String openid;
	private String nickname;
	private int sex;
	private String language;
	private String province;
	private String country;
	private String headimgurl;//微信头像的路径
	private String []privilege;//权限，优先级
	
	
	
	
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String[] getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}
	public static int getSexFemale() {
		return SEX_FEMALE;
	}
	public static int getSexMale() {
		return SEX_MALE;
	}
	
	

}
