package com.posteritytech.spring.model;

import java.util.ArrayList;
import java.util.Collection;

public class DnisResponseResult {
	private int	result;
	private int totalCount;
	private String reason;
	private Collection<Dnis> rgList = new ArrayList<Dnis>();
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
	public Collection<Dnis> getRgList() {
		return rgList;
	}
	public void setRgList(Collection<Dnis> rgList) {
		this.rgList = rgList;
	}

	
}
