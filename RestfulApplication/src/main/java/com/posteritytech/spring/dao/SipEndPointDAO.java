package com.posteritytech.spring.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SipEndPointDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public int getGroupIdByNumber(String number){
		Session session = sessionFactory.getCurrentSession();
    	session.flush();
        session.clear();
    	String hsql = "select groupId from SipEndPoint where userNumber='" + number + "'";
    	Query query = session.createQuery(hsql);
    	if(query.uniqueResult() == null)
    		return -1;
    	else return (Integer) query.uniqueResult();
	}
	
	public int flagPstnByNumber(String number){
		Session session = sessionFactory.getCurrentSession();
    	session.flush();
        session.clear();
    	String hsql = "select enableForward from SipEndPoint where userNumber='" + number + "'";
    	Query query = session.createQuery(hsql);
    	return (Integer) query.uniqueResult();
	}
	
	public int checkExistOfNumber(String startNumber, String endNumber){
		Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.clear();
		String hsql = "select count(userNumber) from  SipEndPoint where userNumber>='" + startNumber + "'" 
		+ " and userNumber <= '" + endNumber + "'";
		Query query = session.createQuery(hsql);
		if(query.uniqueResult() == null){
			return -1;
		}else{
			return ((Number)(query.uniqueResult())).intValue();
		}
		
	}
	
}
