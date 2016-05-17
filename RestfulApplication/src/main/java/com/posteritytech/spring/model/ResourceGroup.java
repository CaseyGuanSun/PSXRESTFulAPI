package com.posteritytech.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="ResourceGroup")
@Table(name="PSX_TAB_RESOURCE_GROUP")
public class ResourceGroup {
	private int 		rgId;
	private int 		rgType;
	private int 		partition;
	private String 		gatewayIp;
	private String      gatewayPort;
	private int 		applicationFlag;
	private String 		gwprefix;
	private String 		regnumber;
	private String 		token;
	private int 		status;
	private int 		sockIndex;
	private int 		blockgroupid;
	private int			blackgroupid;
	private int			prefercodec;
	private int			mediaDriver;
	private int			ivrDriver;
	private int 		maxCallCounter;
	private int			radiustrans;
	private String		defaultUserNumber;
	private String 		localareacode;
	private String 		domesticprefix;
	private String 		internationalprefix;
	private String 		reserved;
	private String 		olimatch;
	private String 		olinomatch;
	private String 		description;
	
	public ResourceGroup(int rgId, int rgType, int partition, String gatewayIp, String gatewayPort, int applicationFlag,
			String gwprefix, String regnumber, String token, int status, int sockIndex, int blockgroupid,
			int blackgroupid, int prefercodec, int mediaDriver, int ivrDriver, int maxCallCounter, int radiustrans,
			String defaultUserNumber, String localareacode, String domesticprefix, String internationalprefix,
			String reserved, String olimatch, String olinomatch, String description) {
		super();
		this.rgId = rgId;
		this.rgType = rgType;
		this.partition = partition;
		this.gatewayIp = gatewayIp;
		this.gatewayPort = gatewayPort;
		this.applicationFlag = applicationFlag;
		this.gwprefix = gwprefix;
		this.regnumber = regnumber;
		this.token = token;
		this.status = status;
		this.sockIndex = sockIndex;
		this.blockgroupid = blockgroupid;
		this.blackgroupid = blackgroupid;
		this.prefercodec = prefercodec;
		this.mediaDriver = mediaDriver;
		this.ivrDriver = ivrDriver;
		this.maxCallCounter = maxCallCounter;
		this.radiustrans = radiustrans;
		this.defaultUserNumber = defaultUserNumber;
		this.localareacode = localareacode;
		this.domesticprefix = domesticprefix;
		this.internationalprefix = internationalprefix;
		this.reserved = reserved;
		this.olimatch = olimatch;
		this.olinomatch = olinomatch;
		this.description = description;
	}
	@Id
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
	public String getGwprefix() {
		return gwprefix;
	}
	public void setGwprefix(String gwprefix) {
		this.gwprefix = gwprefix;
	}
	public String getRegnumber() {
		return regnumber;
	}
	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getSockIndex() {
		return sockIndex;
	}
	public void setSockIndex(int sockIndex) {
		this.sockIndex = sockIndex;
	}
	public int getBlockgroupid() {
		return blockgroupid;
	}
	public void setBlockgroupid(int blockgroupid) {
		this.blockgroupid = blockgroupid;
	}
	public int getBlackgroupid() {
		return blackgroupid;
	}
	public void setBlackgroupid(int blackgroupid) {
		this.blackgroupid = blackgroupid;
	}
	public int getPrefercodec() {
		return prefercodec;
	}
	public void setPrefercodec(int prefercodec) {
		this.prefercodec = prefercodec;
	}
	public int getMediaDriver() {
		return mediaDriver;
	}
	public void setMediaDriver(int mediaDriver) {
		this.mediaDriver = mediaDriver;
	}
	public int getIvrDriver() {
		return ivrDriver;
	}
	public void setIvrDriver(int ivrDriver) {
		this.ivrDriver = ivrDriver;
	}
	public int getRadiustrans() {
		return radiustrans;
	}
	public void setRadiustrans(int radiustrans) {
		this.radiustrans = radiustrans;
	}
	public String getDefaultUserNumber() {
		return defaultUserNumber;
	}
	public void setDefaultUserNumber(String defaultUserNumber) {
		this.defaultUserNumber = defaultUserNumber;
	}
	public String getLocalareacode() {
		return localareacode;
	}
	public void setLocalareacode(String localareacode) {
		this.localareacode = localareacode;
	}
	public String getDomesticprefix() {
		return domesticprefix;
	}
	public void setDomesticprefix(String domesticprefix) {
		this.domesticprefix = domesticprefix;
	}
	public String getInternationalprefix() {
		return internationalprefix;
	}
	public void setInternationalprefix(String internationalprefix) {
		this.internationalprefix = internationalprefix;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getOlimatch() {
		return olimatch;
	}
	public void setOlimatch(String olimatch) {
		this.olimatch = olimatch;
	}
	public String getOlinomatch() {
		return olinomatch;
	}
	public void setOlinomatch(String olinomatch) {
		this.olinomatch = olinomatch;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
}
