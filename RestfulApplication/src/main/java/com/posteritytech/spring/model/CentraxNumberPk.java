package com.posteritytech.spring.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CentraxNumberPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long groupId;
	private String shortNumber;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getShortNumber() {
		return shortNumber;
	}
	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}
	
	@Override  
    public boolean equals(Object obj) {  
        if(obj instanceof CentraxNumberPk){  
        	CentraxNumberPk pk = (CentraxNumberPk)obj;  
            if(this.groupId==pk.getGroupId()&&this.shortNumber.equals(pk.getShortNumber()))  
                return true;  
        }  
        return false;  
    }  
  
   @Override  
	 public int hashCode() {  
        return this.shortNumber.hashCode();  
    }  
}
