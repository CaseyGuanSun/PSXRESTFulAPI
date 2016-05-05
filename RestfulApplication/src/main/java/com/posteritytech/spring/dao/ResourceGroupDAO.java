package com.posteritytech.spring.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.posteritytech.spring.model.ResourceGroup;

public class ResourceGroupDAO {
	private SessionFactory  sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(ResourceGroupDAO.class);
	
	public void setSessionFactory(SessionFactory factory){
		sessionFactory = factory;
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public boolean checkExistOfResourceGroupById(int rgId){
    	Session session = sessionFactory.getCurrentSession();
    	session.clear();
    	String sql = "select count(rgId) from ResourceGroup where rgId ="+rgId;
    	Query query = session.createQuery(sql);
    
    	if(query.uniqueResult() == null){
    		return false;
    	}else{
    		int count = ((Number)(query.uniqueResult())).intValue();
    		if(count > 0)
    		{
    			logger.warn("query rgId count:"+count);
    			return true;
    		}
    		return false;
    	}
	}
	
	@SuppressWarnings("unchecked")
	public List<ResourceGroup> getAllResourceGroup(){
		Session session = getSession();
		session.flush();
		session.clear();
		//String sqlCmd = "select rgId, rgType, partition, gatewayIp, gatewayPort, applicationFlag, maxCallCounter, description from ResourceGroup";
		String sqlCmd = "select new com.posteritytech.spring.model.ResourceGroup(c.rgId, c.rgType, c.partition, c.gatewayIp, c.gatewayPort, c.applicationFlag, c.maxCallCounter, c.description) from ResourceGroup c";
		Query query = session.createQuery(sqlCmd);
		return query.list();
	}
}