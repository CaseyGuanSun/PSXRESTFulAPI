package com.posteritytech.spring.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CdrDAO {
	
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("rawtypes")
    public List getAllCdr(String number, int flag, String startTime, String endTime){
        String hsql="";
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        if(flag==0)
        	hsql="select clgNumber,cldNumber,connectTime,duration from Cdr where clgNumber='"+number+"'"+"and connectTime>='"+startTime+"'"+"and connectTime<='"+endTime+"'";
        else if(flag==1)
        	hsql="select clgNumber,cldNumber,connectTime,duration from Cdr where cldNumber='"+number+"'"+"and connectTime>='"+startTime+"'"+"and connectTime<='"+endTime+"'";

        Query query = session.createQuery(hsql);
        
        return query.list();
        
    }

}
