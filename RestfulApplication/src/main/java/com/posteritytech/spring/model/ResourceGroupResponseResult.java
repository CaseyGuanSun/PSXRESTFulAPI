package com.posteritytech.spring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceGroupResponseResult {
	private int	result;
	private int totalCount;
	private String reason;
	private Collection<ResourceGroupElement> rgList = new ArrayList<ResourceGroupElement>();
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
	public Collection<ResourceGroupElement> getRgList() {
		return rgList;
	}
	public void setRgList(Collection<ResourceGroupElement> arrayList) {
		this.rgList = arrayList;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
