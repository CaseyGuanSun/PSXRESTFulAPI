package com.posteritytech.spring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="SipEndPoint")
@Table(name="PSX_TAB_SIP_END_POINT")
public class SipEndPoint {

	private String userNumber;                                   
	private String userName;                                       
	private String webPasswd;                                      
	private String token;                                          
	private int status;                                             
	private int groupId;                                               
	private int centraxId;                                           
	private String publicIp;                                  
	private int publicPort;                                        
	private String internalIp;                                  
	private int internalPort;                                        
	private int ttl;                                                 
	private Date lastUpdate;                      
	private int maxCalls;                                            
	private int blockGroupId;                                       
	private String colorringBack;                                
	private int enableForward;                                       
	private int forwardTrans;                                         
	private int forwardCondition1;                                    
	private String forwardValue1;                                  
	private String forwardNum1;                                    
	private int forwardCondition2;                                    
	private String forwardValue2;                         
	private String forwardNum2;                                    
	private int forwardCondition3;                                    
	private String forwardValue3;                                  
	private String forwardNum3;                                     
	private int forwardCondition4;                                   
	private String forwardValue4;                                  
	private String forwardNum4;                                     
	private int forwardCondition5;                                     
	private String forwardValue5;                                  
	private String forwardNum5;                                   
	private int forwardCondition6;                                    
	private String forwardValue6;                               
	private String forwardNum6;                                   
	private int socketIndex;                                          
	private int encrypt;                                               
	private String description;             
	private int available;                  
	
	@Id
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWebPasswd() {
		return webPasswd;
	}
	public void setWebPasswd(String webPasswd) {
		this.webPasswd = webPasswd;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getCentraxId() {
		return centraxId;
	}
	public void setCentraxId(int centraxId) {
		this.centraxId = centraxId;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public int getPublicPort() {
		return publicPort;
	}
	public void setPublicPort(int publicPort) {
		this.publicPort = publicPort;
	}
	public String getInternalIp() {
		return internalIp;
	}
	public void setInternalIp(String internalIp) {
		this.internalIp = internalIp;
	}
	public int getInternalPort() {
		return internalPort;
	}
	public void setInternalPort(int internalPort) {
		this.internalPort = internalPort;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getMaxCalls() {
		return maxCalls;
	}
	public void setMaxCalls(int maxCalls) {
		this.maxCalls = maxCalls;
	}
	public int getBlockGroupId() {
		return blockGroupId;
	}
	public void setBlockGroupId(int blockGroupId) {
		this.blockGroupId = blockGroupId;
	}
	public String getColorringBack() {
		return colorringBack;
	}
	public void setColorringBack(String colorringBack) {
		this.colorringBack = colorringBack;
	}
	public int getEnableForward() {
		return enableForward;
	}
	public void setEnableForward(int enableForward) {
		this.enableForward = enableForward;
	}
	public int getForwardTrans() {
		return forwardTrans;
	}
	public void setForwardTrans(int forwardTrans) {
		this.forwardTrans = forwardTrans;
	}
	public int getForwardCondition1() {
		return forwardCondition1;
	}
	public void setForwardCondition1(int forwardCondition1) {
		this.forwardCondition1 = forwardCondition1;
	}
	public String getForwardValue1() {
		return forwardValue1;
	}
	public void setForwardValue1(String forwardValue1) {
		this.forwardValue1 = forwardValue1;
	}
	public String getForwardNum1() {
		return forwardNum1;
	}
	public void setForwardNum1(String forwardNum1) {
		this.forwardNum1 = forwardNum1;
	}
	public int getForwardCondition2() {
		return forwardCondition2;
	}
	public void setForwardCondition2(int forwardCondition2) {
		this.forwardCondition2 = forwardCondition2;
	}
	public String getForwardValue2() {
		return forwardValue2;
	}
	public void setForwardValue2(String forwardValue2) {
		this.forwardValue2 = forwardValue2;
	}
	public String getForwardNum2() {
		return forwardNum2;
	}
	public void setForwardNum2(String forwardNum2) {
		this.forwardNum2 = forwardNum2;
	}
	public int getForwardCondition3() {
		return forwardCondition3;
	}
	public void setForwardCondition3(int forwardCondition3) {
		this.forwardCondition3 = forwardCondition3;
	}
	public String getForwardValue3() {
		return forwardValue3;
	}
	public void setForwardValue3(String forwardValue3) {
		this.forwardValue3 = forwardValue3;
	}
	public String getForwardNum3() {
		return forwardNum3;
	}
	public void setForwardNum3(String forwardNum3) {
		this.forwardNum3 = forwardNum3;
	}
	public int getForwardCondition4() {
		return forwardCondition4;
	}
	public void setForwardCondition4(int forwardCondition4) {
		this.forwardCondition4 = forwardCondition4;
	}
	public String getForwardValue4() {
		return forwardValue4;
	}
	public void setForwardValue4(String forwardValue4) {
		this.forwardValue4 = forwardValue4;
	}
	public String getForwardNum4() {
		return forwardNum4;
	}
	public void setForwardNum4(String forwardNum4) {
		this.forwardNum4 = forwardNum4;
	}
	public int getForwardCondition5() {
		return forwardCondition5;
	}
	public void setForwardCondition5(int forwardCondition5) {
		this.forwardCondition5 = forwardCondition5;
	}
	public String getForwardValue5() {
		return forwardValue5;
	}
	public void setForwardValue5(String forwardValue5) {
		this.forwardValue5 = forwardValue5;
	}
	public String getForwardNum5() {
		return forwardNum5;
	}
	public void setForwardNum5(String forwardNum5) {
		this.forwardNum5 = forwardNum5;
	}
	public int getForwardCondition6() {
		return forwardCondition6;
	}
	public void setForwardCondition6(int forwardCondition6) {
		this.forwardCondition6 = forwardCondition6;
	}
	public String getForwardValue6() {
		return forwardValue6;
	}
	public void setForwardValue6(String forwardValue6) {
		this.forwardValue6 = forwardValue6;
	}
	public String getForwardNum6() {
		return forwardNum6;
	}
	public void setForwardNum6(String forwardNum6) {
		this.forwardNum6 = forwardNum6;
	}
	public int getSocketIndex() {
		return socketIndex;
	}
	public void setSocketIndex(int socketIndex) {
		this.socketIndex = socketIndex;
	}
	public int getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(int encrypt) {
		this.encrypt = encrypt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	 
}
