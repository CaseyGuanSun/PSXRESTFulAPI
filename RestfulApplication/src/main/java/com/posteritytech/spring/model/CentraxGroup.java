package com.posteritytech.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="CentraxGroup")
@Table(name="PSX_TAB_CENTRAX_GROUP")
public class CentraxGroup {

	private long groupId;
	private int servIndex;
	private String centraxNumber;
	private String operationExtension;
	private int extlen;
	private String announcement;
	private String accessAnnounce;
	private String routeAnnounce;
	private int groupRing;
	private int intCallin;
	private int penetrateNumber;
	private int leafNode;
	private int maxCalls;
	private String description;
	
	@Id
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public int getServIndex() {
		return servIndex;
	}
	public void setServIndex(int servIndex) {
		this.servIndex = servIndex;
	}
	public String getCentraxNumber() {
		return centraxNumber;
	}
	public void setCentraxNumber(String centraxNumber) {
		this.centraxNumber = centraxNumber;
	}
	public String getOperationExtension() {
		return operationExtension;
	}
	public void setOperationExtension(String operationExtension) {
		this.operationExtension = operationExtension;
	}
	public int getExtlen() {
		return extlen;
	}
	public void setExtlen(int extlen) {
		this.extlen = extlen;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getAccessAnnounce() {
		return accessAnnounce;
	}
	public void setAccessAnnounce(String accessAnnounce) {
		this.accessAnnounce = accessAnnounce;
	}
	public String getRouteAnnounce() {
		return routeAnnounce;
	}
	public void setRouteAnnounce(String routeAnnounce) {
		this.routeAnnounce = routeAnnounce;
	}
	public int getGroupRing() {
		return groupRing;
	}
	public void setGroupRing(int groupRing) {
		this.groupRing = groupRing;
	}
	public int getIntCallin() {
		return intCallin;
	}
	public void setIntCallin(int intCallin) {
		this.intCallin = intCallin;
	}
	public int getPenetrateNumber() {
		return penetrateNumber;
	}
	public void setPenetrateNumber(int penetrateNumber) {
		this.penetrateNumber = penetrateNumber;
	}
	public int getLeafNode() {
		return leafNode;
	}
	public void setLeafNode(int leafNode) {
		this.leafNode = leafNode;
	}
	public int getMaxCalls() {
		return maxCalls;
	}
	public void setMaxCalls(int maxCalls) {
		this.maxCalls = maxCalls;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
