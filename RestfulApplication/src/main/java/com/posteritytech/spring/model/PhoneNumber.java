package com.posteritytech.spring.model;

public class PhoneNumber {

	private String number;
	private String password;
	private int groupId;
	private String pstnNumber;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPstnNumber() {
		return pstnNumber;
	}
	public void setPstnNumber(String pstnNumber) {
		this.pstnNumber = pstnNumber;
	}
	
}
