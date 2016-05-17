package com.posteritytech.spring.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="DnisTable")
@Table(name="PSX_TAB_DNIS_TABLE")
public class Dnis {
	private int 	partition;
	private String 	dialednumber;
	private String	transednumber;
	private int 	routelist;
	private String	description;
	
	public Dnis(int partition, String dialednumber, String transednumber, int routelist, String description) {
		super();
		this.partition = partition;
		this.dialednumber = dialednumber;
		this.transednumber = transednumber;
		this.routelist = routelist;
		this.description = description;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public String getDialednumber() {
		return dialednumber;
	}
	public void setDialednumber(String dialednumber) {
		this.dialednumber = dialednumber;
	}
	public String getTransednumber() {
		return transednumber;
	}
	public void setTransednumber(String transednumber) {
		this.transednumber = transednumber;
	}
	public int getRoutelist() {
		return routelist;
	}
	public void setRoutelist(int routelist) {
		this.routelist = routelist;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
