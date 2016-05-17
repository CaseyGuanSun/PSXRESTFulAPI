package com.posteritytech.spring.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.posteritytech.spring.model.Dnis;
import com.posteritytech.spring.model.DnisResponseResult;
import com.posteritytech.spring.service.DnisService;

@Controller
public class DNISTranslationController {
	private static final Logger logger = LoggerFactory.getLogger(DNISTranslationController.class);
	
	@Resource(name="dnisService")
	private DnisService dnisService;
	
	@RequestMapping(value = ApplicationURIConstants.GET_ALL_DNIS, produces = "application/json;charset=UTF-8")
	public @ResponseBody DnisResponseResult getAllDnis(){
		logger.info("get all dnis translation");
		DnisResponseResult result = new DnisResponseResult();
		List<Dnis> dnisList = dnisService.getAllDnis();
		
		if(dnisList == null){
			result.setTotalCount(0);
		}else{
			result.setTotalCount(dnisList.size());
			result.setRgList(dnisList);
		}
		return result;
	}
}
