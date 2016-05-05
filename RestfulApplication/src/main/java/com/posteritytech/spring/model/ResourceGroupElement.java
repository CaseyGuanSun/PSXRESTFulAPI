package com.posteritytech.spring.model;

public class ResourceGroupElement {
	private int 	rgId;
	private int 	rgType;
	private int 	partition;
	private String 	gatewayIp;
	private String 	gatewayPort;
	private int 	applicationFlag;
	private int		maxCallCounter;
	private String 	description;
	
	public int getRgId() {
		return rgId;
	}
	public void setRgId(int rgId) {
		this.rgId = rgId;
	}
	public int getRgType() {
		return rgType;
	}
	public void setRgType(int rgType) {
		this.rgType = rgType;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public String getGatewayIp() {
		return gatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	public String getGatewayPort() {
		return gatewayPort;
	}
	public void setGatewayPort(String gatewayPort) {
		this.gatewayPort = gatewayPort;
	}
	public int getApplicationFlag() {
		return applicationFlag;
	}
	public void setApplicationFlag(int applicationFlag) {
		this.applicationFlag = applicationFlag;
	}
	public int getMaxCallCounter() {
		return maxCallCounter;
	}
	public void setMaxCallCounter(int maxCallCounter) {
		this.maxCallCounter = maxCallCounter;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
