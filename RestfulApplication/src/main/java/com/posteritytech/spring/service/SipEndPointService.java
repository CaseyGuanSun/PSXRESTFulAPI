package com.posteritytech.spring.service;

import com.posteritytech.spring.dao.SipEndPointDAO;

public class SipEndPointService {

	private SipEndPointDAO sipEndPointDao;

	public SipEndPointDAO getSipEndPointDao() {
		return sipEndPointDao;
	}

	public void setSipEndPointDao(SipEndPointDAO sipEndPointDao) {
		this.sipEndPointDao = sipEndPointDao;
	}
	
	public int getGroupIdByNumber(String number){
		return sipEndPointDao.getGroupIdByNumber(number);
	}
	
	public int flagPstnByNumber(String number){
		return sipEndPointDao.flagPstnByNumber(number);
	}
	
	public int checkExistOfNumber(String startNumber, String endNumber){
		return sipEndPointDao.checkExistOfNumber(startNumber, endNumber);
	}
}
