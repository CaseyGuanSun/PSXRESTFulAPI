package com.posteritytech.spring.model;

import java.util.ArrayList;
import java.util.Collection;

public class RouteListResponseResult {
	private int result;
	private int totalCount;
	private String reason;
	private Collection<RouteList> rtList = new ArrayList<RouteList>();
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Collection<RouteList> getRtList() {
		return rtList;
	}
	public void setRtList(Collection<RouteList> rtList) {
		this.rtList = rtList;
	}
	
	
}
