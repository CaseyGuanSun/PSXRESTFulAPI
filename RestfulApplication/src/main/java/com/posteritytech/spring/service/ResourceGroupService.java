package com.posteritytech.spring.service;

import java.util.List;

import com.posteritytech.spring.dao.ResourceGroupDAO;
import com.posteritytech.spring.model.ResourceGroup;

public class ResourceGroupService {
	private ResourceGroupDAO resourceGroupDao;

	public ResourceGroupDAO getResourceGroupDao() {
		return resourceGroupDao;
	}

	public void setResourceGroupDao(ResourceGroupDAO resourceGroupDao) {
		this.resourceGroupDao = resourceGroupDao;
	}
	
	public boolean checkExistOfResourceGroupById(int rgId){
		return resourceGroupDao.checkExistOfResourceGroupById(rgId);
	}
	
	public List<ResourceGroup> getAllResourceGroup(){
		return resourceGroupDao.getAllResourceGroup();
	}
}
