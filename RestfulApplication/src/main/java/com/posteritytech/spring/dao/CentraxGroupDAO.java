package com.posteritytech.spring.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.posteritytech.spring.model.CentraxGroup;

public class CentraxGroupDAO {
	
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public void add(CentraxGroup cenGrp){
    	
    	this.getSession().save(cenGrp);
    }

    public void delete(CentraxGroup cenGrp){
    	
    	this.getSession().delete(cenGrp);
    }
    
    public void update(CentraxGroup cenGrp){
    	this.getSession().update(cenGrp);
    }
    
    public long getGroupIdByGroupId(long groupId){
    	Session session = sessionFactory.getCurrentSession();
    	session.clear();
    	String sql = "select groupId from CentraxGroup where groupId="+groupId;
    	Query query = session.createQuery(sql);
    	if(query.uniqueResult() == null){
    		return -1L;
    	}else{
    		return (Long)query.uniqueResult();
    	}
    }
    
    public int checkExistOfGroupId(long startGroupId, long endGroupId){
    	Session session = sessionFactory.getCurrentSession();
    	session.clear();
    	String sql = "select count(groupId) from CentraxGroup where groupId>="+startGroupId+" and groupId<="+endGroupId;
    	Query query = session.createQuery(sql);
    	if(query.uniqueResult() == null){
    		return -1;
    	}else{
    		return ((Number)(query.uniqueResult())).intValue();
    	}
    }
    
    public long getGroupIdByNumber(String number){
    	Session session = sessionFactory.getCurrentSession();
    	session.flush();
        session.clear();
    	String hsql = "select groupId from CentraxGroup where centraxNumber='" + number + "'";
    	Query query = session.createQuery(hsql);
    	if(query.uniqueResult() == null)
    		return -1L;
    	else return (Long) query.uniqueResult();
    }
}
