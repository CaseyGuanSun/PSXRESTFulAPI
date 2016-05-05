package com.posteritytech.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="CentraxNumber")
@Table(name="PSX_TAB_CENTRAX_NUMBER")
public class CentraxNumber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private long groupId;
//	private String shortNumber;
	private CentraxNumberPk pk;
	private String longNumber;
	private int enableAccess;
	private String colorRing;
	private String description;
	
	@Id
	public CentraxNumberPk getPk() {
		return pk;
	}
	public void setPk(CentraxNumberPk pk) {
		this.pk = pk;
	}
	public String getLongNumber() {
		return longNumber;
	}
	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
	public int getEnableAccess() {
		return enableAccess;
	}
	public void setEnableAccess(int enableAccess) {
		this.enableAccess = enableAccess;
	}
	public String getColorRing() {
		return colorRing;
	}
	public void setColorRing(String colorRing) {
		this.colorRing = colorRing;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
