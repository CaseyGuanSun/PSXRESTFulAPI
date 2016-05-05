package com.posteritytech.spring.model;


public class CdrResult {

	private String clgNumber;
	private String cldNumber;
	private String time;
	private int duration;
	public String getClgNumber() {
		return clgNumber;
	}
	public void setClgNumber(String clgNumber) {
		this.clgNumber = clgNumber;
	}
	public String getCldNumber() {
		return cldNumber;
	}
	public void setCldNumber(String cldNumber) {
		this.cldNumber = cldNumber;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
