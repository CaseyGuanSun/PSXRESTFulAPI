package com.posteritytech.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="RouteList")
@Table(name="PSX_TAB_ROUTE_LIST")
public class RouteList {
	private int 	rtid;
	private int 	routetype;
	private int 	rg0;
	private int 	pt0;
	private int 	rg1;
	private int 	pt1;
	private int 	rg2;
	private int 	pt2;
	private int 	rg3;
	private int 	pt3;
	private int 	rg4;
	private int 	pt4;
	private int 	rg5;
	private int 	pt5;
	private int		rg6;
	private int 	pt6;
	private int 	rg7;
	private int 	pt7;
	private int		rg8;
	private int 	pt8;
	private int		rg9;
	private int		pt9;
	private int 	backuprouteid;
	private String	description;
	
	public RouteList(int rtid, int routetype, int rg0, int pt0, int rg1, int pt1, int rg2, int pt2, int rg3, int pt3,
			int rg4, int pt4, int rg5, int pt5, int rg6, int pt6, int rg7, int pt7, int rg8, int pt8, int rg9, int pt9,
			int backuprouteid, String description) {
		super();
		this.rtid = rtid;
		this.routetype = routetype;
		this.rg0 = rg0;
		this.pt0 = pt0;
		this.rg1 = rg1;
		this.pt1 = pt1;
		this.rg2 = rg2;
		this.pt2 = pt2;
		this.rg3 = rg3;
		this.pt3 = pt3;
		this.rg4 = rg4;
		this.pt4 = pt4;
		this.rg5 = rg5;
		this.pt5 = pt5;
		this.rg6 = rg6;
		this.pt6 = pt6;
		this.rg7 = rg7;
		this.pt7 = pt7;
		this.rg8 = rg8;
		this.pt8 = pt8;
		this.rg9 = rg9;
		this.pt9 = pt9;
		this.backuprouteid = backuprouteid;
		this.description = description;
	}
	
	@Id
	public int getRtid() {
		return rtid;
	}
	public void setRtid(int rtid) {
		this.rtid = rtid;
	}
	public int getRoutetype() {
		return routetype;
	}
	public void setRoutetype(int routetype) {
		this.routetype = routetype;
	}
	public int getRg0() {
		return rg0;
	}
	public void setRg0(int rg0) {
		this.rg0 = rg0;
	}
	public int getPt0() {
		return pt0;
	}
	public void setPt0(int pt0) {
		this.pt0 = pt0;
	}
	public int getRg1() {
		return rg1;
	}
	public void setRg1(int rg1) {
		this.rg1 = rg1;
	}
	public int getPt1() {
		return pt1;
	}
	public void setPt1(int pt1) {
		this.pt1 = pt1;
	}
	public int getRg2() {
		return rg2;
	}
	public void setRg2(int rg2) {
		this.rg2 = rg2;
	}
	public int getPt2() {
		return pt2;
	}
	public void setPt2(int pt2) {
		this.pt2 = pt2;
	}
	public int getRg3() {
		return rg3;
	}
	public void setRg3(int rg3) {
		this.rg3 = rg3;
	}
	public int getPt3() {
		return pt3;
	}
	public void setPt3(int pt3) {
		this.pt3 = pt3;
	}
	public int getRg4() {
		return rg4;
	}
	public void setRg4(int rg4) {
		this.rg4 = rg4;
	}
	public int getPt4() {
		return pt4;
	}
	public void setPt4(int pt4) {
		this.pt4 = pt4;
	}
	public int getRg5() {
		return rg5;
	}
	public void setRg5(int rg5) {
		this.rg5 = rg5;
	}
	public int getPt5() {
		return pt5;
	}
	public void setPt5(int pt5) {
		this.pt5 = pt5;
	}
	public int getRg6() {
		return rg6;
	}
	public void setRg6(int rg6) {
		this.rg6 = rg6;
	}
	public int getPt6() {
		return pt6;
	}
	public void setPt6(int pt6) {
		this.pt6 = pt6;
	}
	public int getRg7() {
		return rg7;
	}
	public void setRg7(int rg7) {
		this.rg7 = rg7;
	}
	public int getPt7() {
		return pt7;
	}
	public void setPt7(int pt7) {
		this.pt7 = pt7;
	}
	public int getRg8() {
		return rg8;
	}
	public void setRg8(int rg8) {
		this.rg8 = rg8;
	}
	public int getPt8() {
		return pt8;
	}
	public void setPt8(int pt8) {
		this.pt8 = pt8;
	}
	public int getRg9() {
		return rg9;
	}
	public void setRg9(int rg9) {
		this.rg9 = rg9;
	}
	public int getPt9() {
		return pt9;
	}
	public void setPt9(int pt9) {
		this.pt9 = pt9;
	}
	public int getBackuprouteid() {
		return backuprouteid;
	}
	public void setBackuprouteid(int backuprouteid) {
		this.backuprouteid = backuprouteid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
