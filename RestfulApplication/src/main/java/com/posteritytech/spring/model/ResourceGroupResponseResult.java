package com.posteritytech.spring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResourceGroupResponseResult {
	private int	result;
	private int totalCount;
	private Collection<ResourceGroupElement> rgList;
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
	
}
