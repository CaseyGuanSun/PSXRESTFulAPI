package com.posteritytech.spring.service;

import com.posteritytech.spring.dao.CentraxNumberDAO;
import com.posteritytech.spring.model.CentraxNumber;

public class CentraxNumberService {
	
	private CentraxNumberDAO centraxNumberDao;

	public CentraxNumberDAO getCentraxNumberDao() {
		return centraxNumberDao;
	}

	public void setCentraxNumberDao(CentraxNumberDAO centraxNumberDao) {
		this.centraxNumberDao = centraxNumberDao;
	}

	public void add(CentraxNumber cenNum){
		centraxNumberDao.add(cenNum);
	}
	
	public void delete(CentraxNumber cenNum){
		centraxNumberDao.delete(cenNum);
	}
	
	public void update(CentraxNumber cenNum){
		centraxNumberDao.update(cenNum);
	}
	
	public int extensionByPk(long grpId,String extNum){
		return centraxNumberDao.extensionByPk(grpId, extNum);
	}
	
	public int extensionByPkAndNum(long grpId,String extNum,String number){
		return centraxNumberDao.extensionByPkAndNum(grpId, extNum,number);
	}
}
