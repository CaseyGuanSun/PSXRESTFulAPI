package com.posteritytech.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.posteritytech.spring.dao.CdrDAO;
import com.posteritytech.spring.model.Cdr;

public class CdrService {

	private CdrDAO cdrDao;
	
	public List<Cdr> cdrAll(String number, int flag, String startTime, String endTime){
		
		List<?> list=cdrDao.getAllCdr(number, flag, startTime, endTime);
		List<Cdr> cdrs = new  ArrayList<Cdr>();
        
    	Object[] o;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for(int i=0;i<list.size();i++)
        {
        	Cdr tempCdr = new Cdr();
        	o=(Object[])list.get(i);
        	tempCdr.setClgNumber(String.valueOf(o[0]));
        	tempCdr.setCldNumber(String.valueOf(o[1]));
        	try {
				tempCdr.setConnectTime(sdf.parse(String.valueOf(o[2])));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	tempCdr.setDuration(Integer.valueOf(String.valueOf(o[3])));
        	cdrs.add(tempCdr);
        }
        return cdrs;
        
    }

    public CdrDAO getCdrDao() {
        return cdrDao;
    }

    public void setCdrDao(CdrDAO cdrDao) {
        this.cdrDao = cdrDao;
    }
}
