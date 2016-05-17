package com.posteritytech.spring.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.posteritytech.spring.model.RouteList;

public class RouteListDAO {
	private static final Logger logger = LoggerFactory.getLogger(RouteListDAO.class);
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean checkExistOfRouteListById(int rtId){
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		String sqlCmd = "select count(rtid) from RouteList where rtid="+rtId;
		Query query = session.createQuery(sqlCmd);
		if(query.uniqueResult() == null){
			return false;
		}else{
			int count = ((Number)(query.uniqueResult())).intValue();
			if(count > 0){
				logger.warn("route list id:"+rtId+",count:"+count);
				return true;
			}else{
				return false;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RouteList> getAllRouteList(){
		Session session  = sessionFactory.getCurrentSession();
		session.clear();
		String sqlCmd = "select new com.posteritytech.spring.model.RouteList(c.rtid, c.routetype, c.rg0, c.pt0, c.rg1, c.pt1, c.rg2, c.pt2, "+
		"c.rg3, c.pt3, c.rg4, c.pt4, c.rg5, c.pt5, c.rg6, c.pt6, c.rg7, c.pt7, c.rg8, c.pt8, c.rg9, c.pt9, c.backuprouteid, c.description) " +
		"from RouteList c";
		Query query = session.createQuery(sqlCmd);
		return query.list();
	}
	
	public RouteList getRouteList(int rtId){
		Session session = sessionFactory.getCurrentSession();
		session.clear();
		String sqlCmd = "select new com.posteritytech.spring.model.RouteList(c.rtid, c.routetype, c.rg0, c.pt0, c.rg1, c.pt1, c.rg2, c.pt2, "+
		"c.rg3, c.pt3, c.rg4, c.pt4, c.rg5, c.pt5, c.rg6, c.pt6, c.rg7, c.pt7, c.rg8, c.pt8, c.rg9, c.pt9, c.backuprouteid, c.description) " +
		"from RouteList c where c.rtid="+rtId;
		Query query = session.createQuery(sqlCmd);
		return (RouteList)query.uniqueResult();
	}
}
