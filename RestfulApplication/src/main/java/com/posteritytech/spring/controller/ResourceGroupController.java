package com.posteritytech.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.posteritytech.spring.model.AckResult;
import com.posteritytech.spring.model.ResourceGroup;
import com.posteritytech.spring.model.ResourceGroupElement;
import com.posteritytech.spring.model.ResourceGroupResponseResult;
import com.posteritytech.spring.service.ResourceGroupService;

@Controller
public class ResourceGroupController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceGroupController.class);
	
	@Resource(name="resourceGroupService")
	private ResourceGroupService resourceGroupService;
	
	@RequestMapping(value = ApplicationURIConstants.GET_ALLRESOURCEGROUP, produces = "application/json;charset=UTF-8")
	public @ResponseBody ResourceGroupResponseResult getAllResourceGroup(){
		logger.info("get all Resource Group");
		ResourceGroupResponseResult ackResult = new ResourceGroupResponseResult();
		List<ResourceGroup> rgList = resourceGroupService.getAllResourceGroup();
		ackResult.setResult(0);
		ackResult.setTotalCount(rgList.size());
		logger.info("Resource Group list size:"+rgList.size());
		ackResult.setRgList(new ArrayList<ResourceGroupElement>());
		for(ResourceGroup group: rgList){
			ResourceGroupElement element = new ResourceGroupElement();
			element.setRgId(group.getRgId());
			element.setRgType(group.getRgType());
			element.setPartition(group.getPartition());
			element.setApplicationFlag(group.getApplicationFlag());
			element.setStatus(group.getStatus());
			element.setSockIndex(group.getSockIndex());
			element.setBlockGroupId(group.getBlackgroupid());
			element.setPreferCodec(group.getPrefercodec());
			element.setGatewayIp(group.getGatewayIp());
			element.setGatewayPort(group.getGatewayPort());
			element.setMaxCallCounter(group.getMaxCallCounter());
			element.setDescription(group.getDescription());
			logger.info("rgId:"+group.getRgId()+",partition:"+group.getPartition());
			ackResult.getRgList().add(element);
		}
		return ackResult;
	}
	
	@RequestMapping(value = ApplicationURIConstants.GET_RESOURCEGROUP, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody ResourceGroupResponseResult getResourceGroup(@PathVariable int rgId){
		logger.info("start get resource group");
		ResourceGroupResponseResult ackResult = new ResourceGroupResponseResult();
		ResourceGroup rg = resourceGroupService.getResourceGroup(rgId);
		if(rg == null){
			ackResult.setResult(0);
			ackResult.setTotalCount(0);
		}else{
			ackResult.setResult(0);
			ackResult.setTotalCount(1);
			ResourceGroupElement element = new ResourceGroupElement();
			element.setRgId(rg.getRgId());
			element.setRgType(rg.getRgType());
			element.setPartition(rg.getPartition());
			element.setApplicationFlag(rg.getApplicationFlag());
			element.setStatus(rg.getStatus());
			element.setSockIndex(rg.getSockIndex());
			element.setBlockGroupId(rg.getBlackgroupid());
			element.setPreferCodec(rg.getPrefercodec());
			element.setGatewayIp(rg.getGatewayIp());
			element.setGatewayPort(rg.getGatewayPort());
			element.setMaxCallCounter(rg.getMaxCallCounter());
			element.setDescription(rg.getDescription());
			logger.debug("rgId:"+rg.getRgId()+",Partition:"+rg.getPartition()+",gatewayIp:"+rg.getGatewayIp());
			ackResult.getRgList().add(element);
		}
		return ackResult;
	}

	@RequestMapping(value = ApplicationURIConstants.ADD_RESOURCEGROUP)
	public @ResponseBody AckResult addResourceGroup(@RequestBody List<ResourceGroupElement> rgArray){
		logger.info("Start add Resource Group.");
		AckResult ackRet = new AckResult();
		
		if(rgArray.size() <= 0){
			ackRet.setResult(-1);
			ackRet.setData("no data");
			return ackRet;
		}
		
		for(int j=0;j<rgArray.size();j++)
		{
			int rgId = rgArray.get(j).getRgId();
			boolean ret = resourceGroupService.checkExistOfResourceGroupById(rgId);
			if(ret)
			{
				logger.warn("The resource group "+rgId+" is exist in database");
				ackRet.setResult(-1);
				ackRet.setData("RGID "+rgId+" exist");
				return ackRet;
			}
			//logger.info("the resource group "+rgId+" not exist!");
		}
		
		for(int i=0;i<rgArray.size();i++)
		{
			int 	rgId = rgArray.get(i).getRgId();
			int		rgType = rgArray.get(i).getRgType();
			int 	partition = rgArray.get(i).getPartition();
			String	gatewayIp = rgArray.get(i).getGatewayIp();
			String 	gatewayPort = rgArray.get(i).getGatewayPort();
			int     applicationFlag = rgArray.get(i).getApplicationFlag();
			int 	status = rgArray.get(i).getStatus();
			int 	sockIndex = rgArray.get(i).getSockIndex();
			int 	blockGroupId = rgArray.get(i).getBlockGroupId();
			int 	preferCodec = rgArray.get(i).getPreferCodec();
			int 	maxCallCounter = rgArray.get(i).getMaxCallCounter();
			String	desc = rgArray.get(i).getDescription();
			
			String sqlCommand = new String();
			sqlCommand = "insert into PSX_TAB_RESOURCE_GROUP (rgid,rgtype,partition,gatewayip,gatewayport,applicationflag,"+
			"gwprefix,regnumber,token,status,sockindex,blockgroupid,blackgroupid,prefercodec,mediadriver,ivrdriver,"+
			"maxcallcounter,radiustrans,defaultusernumber,localareacode,domesticprefix,internationalprefix,reserved,"+
			"description,olimatch,olinomatch) values ( '"+rgId+"','"+rgType+"','"+partition+"','"+gatewayIp+"','"+gatewayPort+
			"','"+applicationFlag+"','','','',"+status+","+sockIndex+","+blockGroupId+",'0',"+preferCodec+",'','','"+maxCallCounter+"','0','','','','','','"+
			desc+"','','')";
			if(ConnectSipServer.sendStatement(sqlCommand).equalsIgnoreCase("0"))
			{
				String sockResult = ConnectSipServer.receiveStatement(sqlCommand);
				if(sockResult.equals("ACK00"))
				{
					logger.info(sqlCommand);
					ConnectSipServer.closeConnection(sqlCommand);
				}
				else
				{
					int ret = Integer.parseInt(String.valueOf(sockResult.charAt(4)));
					ackRet.setResult(ret);
					ackRet.setData("Error");
					for(int k=0;k<i;k++)
					{
						String sqlCommand1 = new String();
						sqlCommand1 = "delete from PSX_TAB_RESOURCE_GROUP where rgid='"+rgArray.get(k).getRgId()+"'";
						if(ConnectSipServer.sendStatement(sqlCommand1).equalsIgnoreCase("0"))
						{
							String sockResult1 = ConnectSipServer.receiveStatement(sqlCommand1);
							if(sockResult1.equals("ACK00"))
							{
								logger.info(sqlCommand1);
								ConnectSipServer.closeConnection(sqlCommand1);
							}
						}
					}
					return ackRet;
				}
			}
			else
			{
				logger.info("Fail to send insert Resource Group SQL statemnet to OAMP.");
				ackRet.setResult(-1);
				ackRet.setData("SendSQLToOAMPFailed");
				return ackRet;
			}
		}	
		
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.DELETE_RESOURCEGROUP, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody AckResult deleteResourceGroup(@PathVariable int rgId){
		logger.info("start delete resource group, regId:"+rgId);

		AckResult ackRet = new AckResult();
		boolean result = resourceGroupService.checkExistOfResourceGroupById(rgId);
		if(!result){
			ackRet.setResult(-1);
			ackRet.setData("RGID:"+rgId+" not exist!");
			return ackRet;
		}

		String sqlCommand = new String();
		sqlCommand = "delete from PSX_TAB_RESOURCE_GROUP where rgid="+rgId;
		if(ConnectSipServer.sendStatement(sqlCommand).equalsIgnoreCase("0")){
			String sockResult = ConnectSipServer.receiveStatement(sqlCommand);
			if(sockResult.equals("ACK00")){
				logger.info("success to exec delete Resource Group sqlCommand:"+sqlCommand);
				ConnectSipServer.closeConnection(sqlCommand);
			}else{
				int ret = Integer.parseInt(String.valueOf(sockResult.charAt(4)));
				ackRet.setResult(ret);
				ackRet.setData("failed to delete rgId:"+rgId);
				return ackRet;
			}
		}else{
			logger.error("Error: failed to send delete Resource Group sqlCommand to OAMP");
			ackRet.setData("failed to send delete Command");
			ackRet.setResult(-1);
			return ackRet;
		}
		
		ackRet.setResult(0);
		ackRet.setData("success");
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.UPDATE_RESOURCEGROUP, produces = "application/json; charset=UTF-8")
	public @ResponseBody AckResult updateResourceGroup(@RequestBody ResourceGroupElement rgElement){
		logger.info("begin to update resouce group");
		AckResult result = new AckResult();
		boolean res = resourceGroupService.checkExistOfResourceGroupById(rgElement.getRgId());
		if(!res){
			result.setResult(-1);
			result.setData("RGID:"+rgElement.getRgId()+" not exist!");
			return  result;
		}
		String sqlCommand = new String();
		sqlCommand = "update PSX_TAB_RESOURCE_GROUP set rgid="+rgElement.getRgId()+", rgtype="+rgElement.getRgType()+", partition="+rgElement.getPartition()+",gatewayip='"+
		rgElement.getGatewayIp()+"', gatewayport='"+rgElement.getGatewayPort()+"',applicationflag="+rgElement.getApplicationFlag()+
		",status='"+rgElement.getStatus()+"',sockindex='"+rgElement.getSockIndex()+"',blockgroupid='"+rgElement.getBlockGroupId()+
		"',prefercodec='"+rgElement.getPreferCodec()+"',maxcallcounter='"+
		rgElement.getMaxCallCounter()+"',description='"+rgElement.getDescription()+"' where rgid="+rgElement.getRgId();
		
		if(ConnectSipServer.sendStatement(sqlCommand).equalsIgnoreCase("0")){
			String sockResult = ConnectSipServer.receiveStatement(sqlCommand);
			if(sockResult.equals("ACK00")){
				logger.info("success to exec update Resource Group sqlCommand:"+sqlCommand);
				ConnectSipServer.closeConnection(sqlCommand);
			}else{
				int ret = Integer.parseInt(String.valueOf(sockResult.charAt(4)));
				result.setResult(ret);
				logger.error("failed to update Resource Group for OAMP, rgId:"+rgElement.getRgId());
				result.setData("failed to update Resource Group rgId:"+rgElement.getRgId());
				return result;
			}
		}else{
			logger.error("Error: failed to send update Resource Group sqlCommand to OAMP");
			result.setData("failed to send update command,rgId:"+rgElement.getRgId());
			result.setResult(-1);
			return result;
		}
		result.setResult(0);
		result.setData("success");
		return result;
	}
	
}
