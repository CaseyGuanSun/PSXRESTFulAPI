package com.posteritytech.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.posteritytech.spring.model.CentraxNumber;
import com.posteritytech.spring.model.CentraxNumberPk;

public class CentraxNumberDAO {
	
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public void add(CentraxNumber cenNum){
    	
    	this.getSession().save(cenNum);
    }
    
    public void delete(CentraxNumber cenNum){
    	
    	this.getSession().delete(cenNum);
    }
    
    public void update(CentraxNumber cenNum){
    	
    	this.getSession().update(cenNum);
    }
    
    public int extensionByPk(long grpId,String extNum){
    	Session session = sessionFactory.getCurrentSession();
    	session.flush();
        session.clear();
        CentraxNumberPk centraxPk = new CentraxNumberPk();
        centraxPk.setGroupId(grpId);
        centraxPk.setShortNumber(extNum);
        CentraxNumber cenNum = (CentraxNumber)session.get(CentraxNumber.class, centraxPk);
    	if(cenNum == null)
    		return -1;
    	else return 1;
    }

    public int extensionByPkAndNum(long grpId,String extNum,String number){
    	Session session = sessionFactory.getCurrentSession();
    	session.flush();
        session.clear();
        CentraxNumberPk centraxPk = new CentraxNumberPk();
        centraxPk.setGroupId(grpId);
        centraxPk.setShortNumber(extNum);
        CentraxNumber cenNum = (CentraxNumber)session.get(CentraxNumber.class, centraxPk);
    	if(cenNum == null)
    		return -1;
    	else if(cenNum.getLongNumber().equals(number))
    		return 1;
    	else return -1;
    }
}
