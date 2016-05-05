package com.posteritytech.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Cdr")
@Table(name="PSX_TAB_CDR")
public class Cdr {
	
	private int id;
	private String clgNumber;
	private String clgIp;
	private String clgName;
	private String cldNumber;
	private String oneNumber;
	private String diversion;
	private int	clgRg;
	private int cldRg;
	private String serverIp;
	private Date connectTime;
	private Date answerTime;
	private int	duration;
	private String codeId;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	@Column(name="clgnumber")
	public String getClgNumber(){
		return clgNumber;
	}
	public void setClgNumber(String clgNumber){
		this.clgNumber = clgNumber;
	}
//	@Column(name="clgip")
	public String getClgIp(){
		return clgIp;
	}
	public void setClgIp(String clgIp){
		this.clgIp = clgIp;
	}
//	@Column(name="clgname")
	public String getClgName(){
		return clgName;
	}
	public void setClgName(String clgName){
		this.clgName = clgName;
	}
//	@Column(name="cldnumber")
	public String getCldNumber(){
		return cldNumber;
	}
	public void setCldNumber(String cldNumber){
		this.cldNumber = cldNumber;
	}
//	@Column(name="onenumber")
	public String getOneNumber(){
		return oneNumber;
	}
	public void setOneNumber(String oneNumber){
		this.oneNumber = oneNumber;
	}
//	@Column(name="diversion")
	public String getDiversion(){
		return diversion;
	}
	public void setDiversion(String diversion){
		this.diversion = diversion;
	}
//	@Column(name="clgrg")
	public int getClgRg(){
		return clgRg;
	}
	public void setClgRg(int clgRg){
		this.clgRg = clgRg;
	}
//	@Column(name="cldrg")
	public int getCldRg(){
		return cldRg;
	}
	public void setCldRg(int cldRg){
		this.cldRg = cldRg;
	}
//	@Column(name="serverip")
	public String getServerIp(){
		return serverIp;
	}
	public void setServerIp(String serverIp){
		this.serverIp = serverIp;
	}
//	@Column(name="connecttime")
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
//	@Column(name="answertime")
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
//	@Column(name="duration")
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
//	@Column(name="codeid")
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

}
