package com.posteritytech.spring.service;

import com.posteritytech.spring.dao.CentraxGroupDAO;
import com.posteritytech.spring.model.CentraxGroup;

public class CentraxGroupService {
	
	private CentraxGroupDAO centraxGroupDao;

	public CentraxGroupDAO getCentraxGroupDao() {
		return centraxGroupDao;
	}

	public void setCentraxGroupDao(CentraxGroupDAO centraxGroupDao) {
		this.centraxGroupDao = centraxGroupDao;
	}
	
	public void add(CentraxGroup cenGrp){
		centraxGroupDao.add(cenGrp);
	}
	
	public void delete(CentraxGroup cenGrp){
		centraxGroupDao.delete(cenGrp);
	}
	
	public void update(CentraxGroup cenGrp){
		centraxGroupDao.update(cenGrp);
	}
	
	public long getGroupIdByNumber(String number){
		return centraxGroupDao.getGroupIdByNumber(number);
	}
	
	public long getGroupIdByGroupId(long groupId){
		return centraxGroupDao.getGroupIdByGroupId(groupId);
	}
	
	public int checkExistOfGroupId(long startGroupId, long endGroupId){
		return centraxGroupDao.checkExistOfGroupId(startGroupId, startGroupId);
	}
}
