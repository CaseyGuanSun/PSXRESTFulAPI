package com.posteritytech.spring.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.posteritytech.spring.model.Dnis;

public class DnisDAO {
	private SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(DnisDAO.class);
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public boolean checkExistOfDnisTranslation(int partition, String dialedNumber){
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		String sqlCommand = "select count(*) from DnisTable where partition="+partition+" and dialednumber ='"+dialedNumber+"'";
		Query query = session.createQuery(sqlCommand);
		if(query.uniqueResult() == null){
			return false;
		}else{
			int count = ((Number)(query.uniqueResult())).intValue();
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<Dnis> getAllDnis(){
		Session session = getSession();
		session.flush();
		session.clear();
		
		String sqlCmd = "select new com.posteritytech.spring.model.Dnis(c.partition, c.dialednumber, c.transednumber,"+
		"c.routelist, c.description) from DnisTable c";
		Query query = session.createQuery(sqlCmd);
		return query.list();
	}
	
}
