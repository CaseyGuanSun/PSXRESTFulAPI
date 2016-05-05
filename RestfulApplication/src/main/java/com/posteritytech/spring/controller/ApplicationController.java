package com.posteritytech.spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.posteritytech.spring.controller.ApplicationController;
import com.posteritytech.spring.controller.ApplicationURIConstants;
import com.posteritytech.spring.model.AckResult;
import com.posteritytech.spring.model.BatchApartment;
import com.posteritytech.spring.model.BatchGate;
import com.posteritytech.spring.model.Cdr;
import com.posteritytech.spring.model.CdrCondition;
import com.posteritytech.spring.model.CdrResult;
import com.posteritytech.spring.model.CentraxGroup;
import com.posteritytech.spring.model.CentraxNumber;
import com.posteritytech.spring.model.CentraxNumberPk;
import com.posteritytech.spring.model.ExtensionNumber;
import com.posteritytech.spring.model.GateNumber;
import com.posteritytech.spring.model.PhoneNumber;
import com.posteritytech.spring.model.ResourceGroup;
import com.posteritytech.spring.model.ResourceGroupElement;
import com.posteritytech.spring.model.ResourceGroupResponseResult;
import com.posteritytech.spring.model.SipNumber;
import com.posteritytech.spring.model.UpdateApartment;
import com.posteritytech.spring.model.UpdateGate;
import com.posteritytech.spring.model.UpdatePhone;
import com.posteritytech.spring.model.combinateApartment;
import com.posteritytech.spring.service.CdrService;
import com.posteritytech.spring.service.CentraxGroupService;
import com.posteritytech.spring.service.CentraxNumberService;
import com.posteritytech.spring.service.ResourceGroupService;
import com.posteritytech.spring.service.SipEndPointService;


@Controller
public class ApplicationController {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	//private static final Logger logger = Logger.getLogger(ApplicationController.class.getName());
	
	@Resource(name="cdrService")
	private CdrService service;
	
	@Resource(name="centraxGroupService")
	private CentraxGroupService cenGrpService;
	
	@Resource(name="centraxNumberService")
	private CentraxNumberService cenNumService;
	
	@Resource(name="sipEndPointService")
	private SipEndPointService sipService;
	
	@Resource(name="resourceGroupService")
	private ResourceGroupService resourceGroupService;
	
	@RequestMapping(value = ApplicationURIConstants.ADD_RESOURCEGROUP)
	public @ResponseBody AckResult addResourceGroup(@RequestBody List<ResourceGroupElement> rgArray){
		logger.info("Start add Resource Group.");
		AckResult ackRet = new AckResult();
		
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
			int 	maxCallCounter = rgArray.get(i).getMaxCallCounter();
			String	desc = rgArray.get(i).getDescription();
			
			String sqlCommand = new String();
			sqlCommand = "insert into PSX_TAB_RESOURCE_GROUP (rgid,rgtype,partition,gatewayip,gatewayport,applicationflag,"+
			"gwprefix,regnumber,token,status,sockindex,blockgroupid,blackgroupid,prefercodec,mediadriver,ivrdriver,"+
			"maxcallcounter,radiustrans,defaultusernumber,localareacode,domesticprefix,internationalprefix,reserved,"+
			"description,olimatch,olinomatch) values ( '"+rgId+"','"+rgType+"','"+partition+"','"+gatewayIp+"','"+gatewayPort+
			"','"+applicationFlag+"','','','','1','','0','0','-1','','','"+maxCallCounter+"','0','','','','','','"+
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
	
	@RequestMapping(value = ApplicationURIConstants.GET_RESOURCEGROUP, produces = "application/json;charset=UTF-8")
	public @ResponseBody ResourceGroupResponseResult getResourceGroup(){
		logger.info("get Resource Group");
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
			element.setGatewayIp(group.getGatewayIp());
			element.setGatewayPort(group.getGatewayPort());
			element.setMaxCallCounter(group.getMaxCallCounter());
			element.setDescription(group.getDescription());
			logger.info("rgId:"+group.getRgId()+",partition:"+group.getPartition());
			ackResult.getRgList().add(element);
		}
		return ackResult;
	}
	
	@RequestMapping(value = ApplicationURIConstants.ADD_GATE)
	public @ResponseBody AckResult addGate(@RequestBody List<GateNumber> gateNums){
		logger.info("Start add gate");
		AckResult ackRet = new AckResult();
		for(int j=0;j<gateNums.size();j++)
		{
			String userNum = gateNums.get(j).getNumber();
			int grpId1 = sipService.getGroupIdByNumber(userNum);
			if(grpId1>0)
			{
				logger.warn("The Gate Number "+userNum+" is exist.");
				ackRet.setResult(3);
				ackRet.setData(userNum);
				return ackRet;
			}
		}
		for(int i=0;i<gateNums.size();i++)
		{
			String number = gateNums.get(i).getNumber();
			String password = gateNums.get(i).getPassword();
			int grpId = gateNums.get(i).getGroupId();
			
			String sqlCommand = new String();
			sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback) values ('"+number+"',null,'"+password+"',1,"+String.valueOf(grpId)+",'')";
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
					ackRet.setData(number);
					for(int k=0;k<i;k++)
					{
						String sqlCommand1 = new String();
						sqlCommand1 = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+gateNums.get(k).getNumber()+"'";
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
				logger.info("Fail to send SQL statemnet to OAMP.");
				ackRet.setResult(1);
				ackRet.setData(number);
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.BATCH_ADD_GATE)
	public @ResponseBody AckResult addBatchGate(@RequestBody BatchGate batchGate){
		logger.info("Start batch add gate.");
		AckResult ackRet = new AckResult();
		String startNum = batchGate.getFirstNumber();
		String endNum = batchGate.getLastNumber();
		String password = batchGate.getPassword();
		int grpId = batchGate.getGroupId();
		if(Long.parseLong(startNum)<=Long.parseLong(endNum))
		{
			for(long j=Long.parseLong(startNum);j<=Long.parseLong(endNum);j++)
			{
				int grpId1 = sipService.getGroupIdByNumber(String.valueOf(j));
				if(grpId1>0)
				{
					logger.warn("The Gate Id "+String.valueOf(j)+" is exist.");
					ackRet.setResult(3);
					ackRet.setData(String.valueOf(j));
					return ackRet;
				}
			}
			for(long t=Long.parseLong(startNum);t<=Long.parseLong(endNum);t++)
			{
				String sqlCommand = new String();
				sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback) values ('"+String.valueOf(t)+"',null,'"+password+"',1,"+String.valueOf(grpId)+",'')";
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
						ackRet.setData(String.valueOf(t));
						for(long l=Long.parseLong(startNum);l<t;l++)
						{
							String sqlCommand1 = new String();
							sqlCommand1 = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+String.valueOf(l)+"'";
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
					logger.info("Fail to send SQL statemnet to OAMP.");
					ackRet.setResult(1);
					ackRet.setData(String.valueOf(t));
					return ackRet;
				}
			}
			ackRet.setResult(0);
			return ackRet;
		}
		logger.warn("The startNum is bigger than endNum");
		ackRet.setResult(5);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.UPDATE_GATE)
	public @ResponseBody AckResult updateGate(@RequestBody UpdateGate gateInfo){
		logger.info("Start update gate.");
		AckResult ackRet = new AckResult();
		String number = gateInfo.getNumber();
		String password = gateInfo.getPassword();
		int grpId = sipService.getGroupIdByNumber(number);
		if(grpId<0)
		{
			logger.warn("The Gate Number "+number+" is not exist in database.");
			ackRet.setResult(5);
			return ackRet;
		}
		String sqlCommand = new String();
		sqlCommand = "update PSX_TAB_SIP_END_POINT set usernumber='"+number+"',username=null,token ='"+password+"',status=1,groupid="+String.valueOf(grpId)+",colorringback='' where usernumber='"+number+"'";
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
				return ackRet;
			}
		}
		else
		{
			logger.info("Fail to send SQL statemnet to OAMP.");
			ackRet.setResult(1);
			return ackRet;
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.DELETE_GATE)
	public @ResponseBody AckResult deleteGate(@RequestBody List<SipNumber> gateNums){
		logger.info("Start delete gate.");
		AckResult ackRet = new AckResult();
		for(int i=0;i<gateNums.size();i++)
		{
			int grpId = sipService.getGroupIdByNumber(gateNums.get(i).getNumber());
			if(grpId<0)
			{
				logger.warn("The Gate Number "+gateNums.get(i).getNumber()+" is not exist in database.");
				ackRet.setResult(3);
				ackRet.setData(gateNums.get(i).getNumber());
				return ackRet;
			}
		}
		for(int j=0;j<gateNums.size();j++)
		{
			String sqlCommand = new String();
			sqlCommand = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+gateNums.get(j).getNumber()+"'";
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
					ackRet.setData(gateNums.get(j).getNumber());
					return ackRet;
				}
			}
			else
			{
				logger.info("Fail to send SQL statemnet to OAMP.");
				ackRet.setResult(1);
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.ADD_PHONE)
	public @ResponseBody AckResult addPhoneNumber(@RequestBody List<PhoneNumber> phoneNums){
		logger.info("Start add phone number.");
		AckResult ackRet = new AckResult();
		for(int j=0;j<phoneNums.size();j++)
		{
			String userNum = phoneNums.get(j).getNumber();
			int grpId1 = sipService.getGroupIdByNumber(userNum);
			if(grpId1>0)
			{
				logger.warn("The Phone Number "+userNum+" is exist in database");
				ackRet.setResult(3);
				ackRet.setData(userNum);
				return ackRet;
			}
		}
		for(int i=0;i<phoneNums.size();i++)
		{
			String userNum = phoneNums.get(i).getNumber();
			String password = phoneNums.get(i).getPassword();
			String pstnNum = phoneNums.get(i).getPstnNumber();
			int grpId = phoneNums.get(i).getGroupId();
			String sqlCommand = new String();
			if(pstnNum==null||pstnNum.length()<2)
				sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback) values ('"+userNum+"',null,'"+password+"',1,"+String.valueOf(grpId)+",'')";
			else
				sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback,enableforward,forwardtrans,forwardcondition1,forwardvalue1,forwardnum1) values ('"+userNum+"',null,'"+password+"',1,"+String.valueOf(grpId)+",'',1,1,1,'','"+pstnNum+"')";
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
					ackRet.setData(userNum);
					for(int k=0;k<i;k++)
					{
						String sqlCommand1 = new String();
						sqlCommand1 = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+phoneNums.get(k).getNumber()+"'";
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
				logger.info("Fail to send SQL statemnet to OAMP.");
				ackRet.setResult(1);
				ackRet.setData(userNum);
				return ackRet;
			}
		}		
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.BATCH_ADD_PHONE)
	public @ResponseBody AckResult addBatchPhone(@RequestBody BatchGate batchPhone){
		logger.info("Start add batch phone.");
		AckResult ackRet = new AckResult();
		String startNum = batchPhone.getFirstNumber();
		String endNum = batchPhone.getLastNumber();
		String password = batchPhone.getPassword();
		int grpId = batchPhone.getGroupId();
		if(Long.parseLong(startNum)<=Long.parseLong(endNum))
		{
			/*
			for(long j=Long.parseLong(startNum);j<=Long.parseLong(endNum);j++)
			{
				int grpId1 = sipService.getGroupIdByNumber(String.valueOf(j));
				if(grpId1>0)
				{
					logger.warn("The Phone Number %s is exist in database", String.valueOf(j));
					ackRet.setResult(3);
					ackRet.setData(String.valueOf(j));
					return ackRet;
				}
			}*/
			logger.info("begin check the sip number");
			int count = sipService.checkExistOfNumber(startNum, endNum);
			if(count > 0)
			{
				logger.warn("The Phone number is exit in database");
				ackRet.setResult(3);
				ackRet.setData(startNum+","+endNum);
				return ackRet;
			}
			logger.info("end check the sip number");
			for(long t=Long.parseLong(startNum);t<=Long.parseLong(endNum);t++)
			{
				String sqlCommand = new String();
				sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback) values ('"+String.valueOf(t)+"',null,'"+password+"',1,"+String.valueOf(grpId)+",'')";
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
						ackRet.setData(String.valueOf(t));
						for(long l=Long.parseLong(startNum);l<t;l++)
						{
							String sqlCommand1 = new String();
							sqlCommand1 = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+String.valueOf(l)+"'";
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
					logger.info("Fail to send SQL statemnet to OAMP.");
					ackRet.setResult(1);
					ackRet.setData(String.valueOf(t));
					return ackRet;
				}
			}
			logger.debug("end insert the sip number");
			ackRet.setResult(0);
			return ackRet;
		}
		logger.warn("The startNum is bigger than endNum");
		ackRet.setResult(5);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.UPDATE_PHONE)
	public @ResponseBody AckResult updatePhoneNum(@RequestBody UpdatePhone phoneNum){
		logger.info("Start update phone number.");
		AckResult ackRet = new AckResult();
		String userNum = phoneNum.getNumber();
		String password = phoneNum.getPassword();
		String pstnNum = phoneNum.getPstnNumber();
		int grpId = sipService.getGroupIdByNumber(userNum);
		if(grpId<0)
		{
			logger.warn("The Phone Number "+userNum+" is not exist in database");
			ackRet.setResult(5);
			return ackRet;
		}
		String sqlCommand = new String();
		if(pstnNum==null||pstnNum.length()<2)
			sqlCommand = "update PSX_TAB_SIP_END_POINT set usernumber='"+userNum+"',username=null,token='"+password+"',status=1,groupid="+String.valueOf(grpId)+",colorringback='',enableforward=0,forwardtrans=0,forwardcondition1=1,forwardvalue1='',forwardnum1='' where usernumber='"+userNum+"'";
		else
			sqlCommand = "update PSX_TAB_SIP_END_POINT set usernumber='"+userNum+"',username=null,token='"+password+"',status=1,groupid="+String.valueOf(grpId)+",colorringback='',enableforward=1,forwardtrans=1,forwardcondition1=1,forwardvalue1='',forwardnum1='"+pstnNum+"' where usernumber='"+userNum+"'";
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
				return ackRet;
			}
		}
		else
		{
			logger.info("Fail to send SQL statemnet to OAMP.");
			ackRet.setResult(1);
			return ackRet;
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.DELETE_PHONE)
	public @ResponseBody AckResult deletePhoneNum(@RequestBody List<SipNumber> phoneNums){
		logger.info("Start delete phone number.");
		AckResult ackRet = new AckResult();
		for(int i=0;i<phoneNums.size();i++)
		{
			int grpId = sipService.getGroupIdByNumber(phoneNums.get(i).getNumber());
			if(grpId<0)
			{
				logger.warn("The Phone Number "+phoneNums.get(i).getNumber()+" is not exist in database");
				ackRet.setResult(3);
				ackRet.setData(phoneNums.get(i).getNumber());
				return ackRet;
			}
		}
		for(int j=0;j<phoneNums.size();j++)
		{
			String sqlCommand = new String();
			sqlCommand = "delete from PSX_TAB_SIP_END_POINT where usernumber='"+phoneNums.get(j).getNumber()+"'";
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
					ackRet.setData(phoneNums.get(j).getNumber());
					return ackRet;
				}
			}
			else
			{
				logger.info("Fail to send SQL statemnet to OAMP.");
				ackRet.setResult(1);
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.ADD_APARTMENT)
	public @ResponseBody AckResult addApartment(@RequestBody List<SipNumber> apartNums){
		logger.info("Start add apartment");
		AckResult ackRet = new AckResult();
		for(int k=0;k<apartNums.size();k++)
		{
			long grpId1 = cenGrpService.getGroupIdByNumber(apartNums.get(k).getNumber());
			if(grpId1>0)
			{
				logger.warn("The Apartment Number "+apartNums.get(k).getNumber()+" is exist in database");
				ackRet.setResult(3);
				ackRet.setData(apartNums.get(k).getNumber());
				return ackRet;
			}
		}
		for(int i=0;i<apartNums.size();i++)
		{
			CentraxGroup cenGrp = new CentraxGroup();
			long grpId = Long.parseLong(apartNums.get(i).getNumber());
			cenGrp.setGroupId(grpId);
			cenGrp.setServIndex(0);
			cenGrp.setCentraxNumber(apartNums.get(i).getNumber());
			cenGrp.setOperationExtension("");
			cenGrp.setExtlen(4);
			cenGrp.setAnnouncement("");
			cenGrp.setAccessAnnounce("");
			cenGrp.setRouteAnnounce("");
			cenGrp.setGroupRing(1);
			cenGrp.setIntCallin(1);
			cenGrp.setPenetrateNumber(0);
			cenGrp.setLeafNode(0);
			cenGrp.setMaxCalls(0);
			cenGrp.setDescription("");
			try{
				cenGrpService.add(cenGrp);
			}
			catch(Exception e){
				logger.warn("The Apartment Number "+apartNums.get(i).getNumber()+" added failed.");
				ackRet.setResult(3);
				ackRet.setData(apartNums.get(i).getNumber());
				for(int j=0;j<i;j++)
				{
					CentraxGroup cenGrp1 = new CentraxGroup();
					cenGrp1.setGroupId(Long.parseLong(apartNums.get(j).getNumber()));
					cenGrp1.setServIndex(0);
					cenGrp1.setCentraxNumber(apartNums.get(j).getNumber());
					cenGrp1.setOperationExtension("");
					cenGrp1.setExtlen(4);
					cenGrp1.setAnnouncement("");
					cenGrp1.setAccessAnnounce("");
					cenGrp1.setRouteAnnounce("");
					cenGrp1.setGroupRing(1);
					cenGrp1.setIntCallin(1);
					cenGrp1.setPenetrateNumber(0);
					cenGrp1.setLeafNode(0);
					cenGrp1.setMaxCalls(0);
					cenGrp1.setDescription("");
					cenGrpService.delete(cenGrp1);
				}
				return ackRet;
			}
			
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.BATCH_ADD_APARTMENT)
	public @ResponseBody AckResult addBatchApartment(@RequestBody BatchApartment batApart){
		logger.info("Start batch add apartment");
		AckResult ackRet = new AckResult();
		String startNum = batApart.getFirstNumber();
		String endNum = batApart.getLastNumber();
		if(Long.parseLong(startNum)<=Long.parseLong(endNum))
		{
			/*
			for(long n=Long.parseLong(startNum);n<=Long.parseLong(endNum);n++)
			{
				//long grpId1 = cenGrpService.getGroupIdByNumber(Long.toString(n));
				long grpId1 = cenGrpService.getGroupIdByGroupId(n);
				if(grpId1>0)
				{
					logger.warn("The Apartment Number %s is exist in database", Long.toString(n));
					ackRet.setResult(3);
					ackRet.setData(Long.toString(n));
					return ackRet;
				}
			}
			*/
			int count = cenGrpService.checkExistOfGroupId(Long.parseLong(startNum), Long.parseLong(endNum));
			if(count > 0)
			{
				logger.error("The apartment number is exist in database");
				ackRet.setResult(3);
				ackRet.setData(startNum+","+endNum);
				return ackRet;
			}
			for(long t=Long.parseLong(startNum);t<=Long.parseLong(endNum);t++)
			{
				CentraxGroup cenGrp = new CentraxGroup();
				cenGrp.setGroupId(t);
				cenGrp.setServIndex(0);
				cenGrp.setCentraxNumber(Long.toString(t));
				cenGrp.setOperationExtension("");
				cenGrp.setExtlen(4);
				cenGrp.setAnnouncement("");
				cenGrp.setAccessAnnounce("");
				cenGrp.setRouteAnnounce("");
				cenGrp.setGroupRing(1);
				cenGrp.setIntCallin(1);
				cenGrp.setPenetrateNumber(0);
				cenGrp.setLeafNode(0);
				cenGrp.setMaxCalls(0);
				cenGrp.setDescription("");
				try{
					cenGrpService.add(cenGrp);
				}
				catch(Exception e){
					for(long l=Long.parseLong(startNum);l<t;l++)
					{
						CentraxGroup cenGrp1 = new CentraxGroup();
						cenGrp1.setGroupId(l);
						cenGrp1.setServIndex(0);
						cenGrp1.setCentraxNumber(Long.toString(l));
						cenGrp1.setOperationExtension("");
						cenGrp1.setExtlen(4);
						cenGrp1.setAnnouncement("");
						cenGrp1.setAccessAnnounce("");
						cenGrp1.setRouteAnnounce("");
						cenGrp1.setGroupRing(1);
						cenGrp1.setIntCallin(1);
						cenGrp1.setPenetrateNumber(0);
						cenGrp1.setLeafNode(0);
						cenGrp1.setMaxCalls(0);
						cenGrp1.setDescription("");
						cenGrpService.delete(cenGrp1);
					}
					logger.warn("The Apartment Number "+Long.toString(t)+" added failed.");
					ackRet.setResult(3);
					ackRet.setData(Long.toString(t));
					return ackRet;
				}
			}
			ackRet.setResult(0);
			return ackRet;
		}
		logger.warn("The startNum is bigger than endNum");
		ackRet.setResult(5);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.UPDATE_APARTMENT)
	public @ResponseBody AckResult updateApartment(@RequestBody UpdateApartment updateApart){
		logger.info("Start update apartment");
		AckResult ackRet = new AckResult();
		long grpId = cenGrpService.getGroupIdByNumber(updateApart.getNumber());
		if(grpId<0)
		{
			logger.warn("The Apartment Number "+updateApart.getNumber()+" is not exist in database.");
			ackRet.setResult(5);
			return ackRet;
		}
		else
		{
			CentraxGroup cenGrp = new CentraxGroup();
			cenGrp.setGroupId(grpId);
			cenGrp.setServIndex(0);
			cenGrp.setCentraxNumber(updateApart.getNewNumber());
			cenGrp.setOperationExtension("");
			cenGrp.setExtlen(4);
			cenGrp.setAnnouncement("");
			cenGrp.setAccessAnnounce("");
			cenGrp.setRouteAnnounce("");
			cenGrp.setGroupRing(1);
			cenGrp.setIntCallin(1);
			cenGrp.setPenetrateNumber(0);
			cenGrp.setLeafNode(0);
			cenGrp.setMaxCalls(0);
			cenGrp.setDescription("");
			try{
				cenGrpService.update(cenGrp);
			}
			catch(Exception e){
				logger.warn("The Apartment Number "+updateApart.getNumber()+" update failed.");
				ackRet.setResult(3);
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.DELETE_APARTMENT)
	public @ResponseBody AckResult deleteApartment(@RequestBody List<SipNumber> sipNums){
		logger.info("Start delete apartment");
		AckResult ackRet = new AckResult();
		for(int i=0;i<sipNums.size();i++)
		{
			long grpId1 = cenGrpService.getGroupIdByNumber(sipNums.get(i).getNumber());
			if(grpId1<0)
			{
				logger.warn("The Apartment Number "+sipNums.get(i).getNumber()+" is not exist in database");
				ackRet.setResult(3);
				ackRet.setData(sipNums.get(i).getNumber());
				return ackRet;
			}
		}
		for(int j=0;j<sipNums.size();j++)
		{
			long grpId = cenGrpService.getGroupIdByNumber(sipNums.get(j).getNumber());
			CentraxGroup cenGrp = new CentraxGroup();
			cenGrp.setGroupId(grpId);
			cenGrp.setServIndex(0);
			cenGrp.setCentraxNumber(sipNums.get(j).getNumber());
			cenGrp.setOperationExtension("");
			cenGrp.setExtlen(4);
			cenGrp.setAnnouncement("");
			cenGrp.setAccessAnnounce("");
			cenGrp.setRouteAnnounce("");
			cenGrp.setGroupRing(1);
			cenGrp.setIntCallin(1);
			cenGrp.setPenetrateNumber(0);
			cenGrp.setLeafNode(0);
			cenGrp.setMaxCalls(0);
			cenGrp.setDescription("");
			try{
				cenGrpService.delete(cenGrp);
			}
			catch(Exception e){
				logger.warn("The Apartment Number "+sipNums.get(j).getNumber()+" delete failed." );
				ackRet.setResult(3);
				ackRet.setData(sipNums.get(j).getNumber());
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.ADD_EXTENSION)
	public @ResponseBody AckResult addExtensionNumber(@RequestBody List<ExtensionNumber> extNums){
		logger.info("Start add extension");
		AckResult ackRet = new AckResult();
		for(int j=0;j<extNums.size();j++)
		{
			long grpId1 = cenGrpService.getGroupIdByNumber(extNums.get(j).getApartment());
			int grpId2 = sipService.getGroupIdByNumber(extNums.get(j).getNumber());
			if(grpId1<0 || grpId2<0)
			{
				logger.warn("The Apartment Number "+extNums.get(j).getApartment()+" or Phone Number "+extNums.get(j).getNumber()+" is not exist in database");
				ackRet.setResult(5);
				return ackRet;
			}
			int ret = cenNumService.extensionByPkAndNum(grpId1, extNums.get(j).getExtension(), extNums.get(j).getNumber());
			if(ret>0)
			{
				logger.warn("The extension is exist in database, Apartment number "+extNums.get(j).getApartment()+", extension number is "+extNums.get(j).getExtension()+".");
				ackRet.setResult(3);
				String dataStr = new String();
				dataStr = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
				ackRet.setData(dataStr);
				return ackRet;
			}
		}
		for(int i=0;i<extNums.size();i++)
		{
			long grpId = cenGrpService.getGroupIdByNumber(extNums.get(i).getApartment());
			if(grpId<0)
			{
				ackRet.setResult(5);
				return ackRet;
			}
			else
			{
				CentraxNumber cenNum = new CentraxNumber();
				CentraxNumberPk cenPk = new CentraxNumberPk();
				cenPk.setGroupId(grpId);
				cenPk.setShortNumber(extNums.get(i).getExtension());
				cenNum.setPk(cenPk);
				cenNum.setColorRing("");
				cenNum.setLongNumber(extNums.get(i).getNumber());
				cenNum.setDescription("");
				cenNum.setEnableAccess(0);
				try{
					cenNumService.add(cenNum);
				}
				catch(Exception e){
					logger.warn("The Apartment Number "+extNums.get(i).getApartment()+", extension "+extNums.get(i).getExtension()+" add failed.");
					ackRet.setResult(3);
					String dataStr = new String();
					dataStr = "apartment:"+extNums.get(i).getApartment()+"extension:"+extNums.get(i).getExtension();
					ackRet.setData(dataStr);
					for(int k=0;k<i;k++)
					{
						long grpId1 = cenGrpService.getGroupIdByNumber(extNums.get(k).getApartment());
						CentraxNumber cenNum1 = new CentraxNumber();
						CentraxNumberPk cenPk1 = new CentraxNumberPk();
						cenPk.setGroupId(grpId1);
						cenPk.setShortNumber(extNums.get(k).getExtension());
						cenNum1.setPk(cenPk1);
						cenNum1.setColorRing("");
						cenNum1.setLongNumber(extNums.get(k).getNumber());
						cenNum1.setDescription("");
						cenNum1.setEnableAccess(0);
						cenNumService.delete(cenNum1);
					}
					return ackRet;
				}			
			}					
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.UPDATE_EXTENSION)
	public @ResponseBody AckResult updateExtensionNum(@RequestBody ExtensionNumber extNum){
		logger.info("Start update extension");
		AckResult ackRet = new AckResult();
		long grpId = cenGrpService.getGroupIdByNumber(extNum.getApartment());
		if(grpId<0)
		{
			logger.warn("The extension's apartment number "+extNum.getApartment()+" is not exist in database.");
			ackRet.setResult(5);
			return ackRet;
		}
		else
		{
			int ret = cenNumService.extensionByPk(grpId, extNum.getExtension());
			if(ret<0)
			{
				logger.warn("The extension is not exist in database.");
				ackRet.setResult(5);
				return ackRet;
			}
			CentraxNumber cenNum = new CentraxNumber();
			CentraxNumberPk cenPk = new CentraxNumberPk();
			cenPk.setGroupId(grpId);
			cenPk.setShortNumber(extNum.getExtension());
			cenNum.setPk(cenPk);
			cenNum.setColorRing("");
			cenNum.setLongNumber(extNum.getNumber());
			cenNum.setDescription("");
			cenNum.setEnableAccess(0);
			try{
				cenNumService.update(cenNum);
			}
			catch(Exception e){
				logger.warn("The extension update failed.");
				ackRet.setResult(3);
				return ackRet;
			}	
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.DELETE_EXTENSION)
	public @ResponseBody AckResult deleteExtensionNum(@RequestBody List<ExtensionNumber> extNums){
		logger.info("Start delete extension number");
		AckResult ackRet = new AckResult();
		for(int j=0;j<extNums.size();j++)
		{
			long grpId1 = cenGrpService.getGroupIdByNumber(extNums.get(j).getApartment());
			if(grpId1<0)
			{
				logger.warn("The extension's apartment number "+extNums.get(j).getApartment()+" is not exist in database.");
				ackRet.setResult(5);
				String dataStr1 = new String();
				dataStr1 = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
				ackRet.setData(dataStr1);
				return ackRet;
			}
			int ret = cenNumService.extensionByPkAndNum(grpId1,extNums.get(j).getExtension(),extNums.get(j).getNumber());
			if(ret<0)
			{
				logger.warn("The extension is not exist in database.");
				ackRet.setResult(5);
				String dataStr2 = new String();
				dataStr2 = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
				ackRet.setData(dataStr2);
				return ackRet;
			}
		}
		for(int i=0;i<extNums.size();i++)
		{
			long grpId = cenGrpService.getGroupIdByNumber(extNums.get(i).getApartment());
			CentraxNumber cenNum = new CentraxNumber();
			CentraxNumberPk cenPk = new CentraxNumberPk();
			cenPk.setGroupId(grpId);
			cenPk.setShortNumber(extNums.get(i).getExtension());
			cenNum.setPk(cenPk);
			cenNum.setColorRing("");
			cenNum.setLongNumber(extNums.get(i).getNumber());
			cenNum.setDescription("");
			cenNum.setEnableAccess(0);
			try{
				cenNumService.delete(cenNum);
			}
			catch(Exception e){
				logger.warn("The extension delete failed.");
				ackRet.setResult(3);
				String dataStr3 = new String();
				dataStr3 = "apartment:"+extNums.get(i).getApartment()+"extension:"+extNums.get(i).getExtension();
				ackRet.setData(dataStr3);
				return ackRet;
			}	
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.RELATED_DELETE_EXTENSION)
	public @ResponseBody AckResult relatedDeleteExtensionNum(@RequestBody List<ExtensionNumber> extNums){
		logger.info("Start delete extension number");
		AckResult ackRet = new AckResult();
		for(int j=0;j<extNums.size();j++)
		{
			long grpId1 = cenGrpService.getGroupIdByNumber(extNums.get(j).getApartment());
			if(grpId1<0)
			{
				logger.warn("The extension's apartment number "+extNums.get(j).getApartment()+" is not exist in database." );
				ackRet.setResult(5);
				String dataStr1 = new String();
				dataStr1 = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
				ackRet.setData(dataStr1);
				return ackRet;
			}
			int ret1 = cenNumService.extensionByPkAndNum(grpId1,extNums.get(j).getExtension(),extNums.get(j).getNumber());
			if(ret1<0)
			{
				logger.warn("The extension is not exist in database.");
				ackRet.setResult(5);
				String dataStr2 = new String();
				dataStr2 = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
				ackRet.setData(dataStr2);
				return ackRet;
			}
			int flag = sipService.flagPstnByNumber(extNums.get(j).getNumber());
			if(flag==1)
			{
				int grpId2 = sipService.getGroupIdByNumber(extNums.get(j).getNumber());
				String sqlCommand = new String();
				sqlCommand = "update PSX_TAB_SIP_END_POINT set usernumber='"+extNums.get(j).getNumber()+"',status=1,groupid="+String.valueOf(grpId2)+",enableforward=0 where usernumber='"+extNums.get(j).getNumber()+"'";
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
						int ret2 = Integer.parseInt(String.valueOf(sockResult.charAt(4)));
						ackRet.setResult(ret2);
						String dataStr3 = new String();
						dataStr3 = "apartment:"+extNums.get(j).getApartment()+"extension:"+extNums.get(j).getExtension();
						ackRet.setData(dataStr3);
						return ackRet;
					}
				}
				else
				{
					logger.info("Fail to send SQL statemnet to OAMP.");
					ackRet.setResult(1);
					return ackRet;
				}
			}
		}
		for(int i=0;i<extNums.size();i++)
		{
			long grpId = cenGrpService.getGroupIdByNumber(extNums.get(i).getApartment());
			CentraxNumber cenNum = new CentraxNumber();
			CentraxNumberPk cenPk = new CentraxNumberPk();
			cenPk.setGroupId(grpId);
			cenPk.setShortNumber(extNums.get(i).getExtension());
			cenNum.setPk(cenPk);
			cenNum.setColorRing("");
			cenNum.setLongNumber(extNums.get(i).getNumber());
			cenNum.setDescription("");
			cenNum.setEnableAccess(0);
			try{
				cenNumService.delete(cenNum);
			}
			catch(Exception e){
				logger.warn("The extension delete failed.");
				ackRet.setResult(3);
				String dataStr4 = new String();
				dataStr4 = "apartment:"+extNums.get(i).getApartment()+"extension:"+extNums.get(i).getExtension();
				ackRet.setData(dataStr4);
				return ackRet;
			}
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.COMBINATE_ADD_APARTMENT)
	public @ResponseBody AckResult combinateAddApartment(@RequestBody List<combinateApartment> comAparts){
		logger.info("Start combinate add apartment.");
		AckResult ackRet = new AckResult();
		for(int i=0;i<comAparts.size();i++)
		{
			long cenGrpId = cenGrpService.getGroupIdByNumber(comAparts.get(i).getApartment());
			if(cenGrpId>0)
			{
				int ret = cenNumService.extensionByPkAndNum(cenGrpId,comAparts.get(i).getExtension(),comAparts.get(i).getSipNumber());
				if(ret==1)
				{
					logger.warn("The extension is exist in database.");
					ackRet.setResult(3);
					String dataStr1 = new String();
					dataStr1 = "apartment:"+comAparts.get(i).getApartment()+"extension:"+comAparts.get(i).getExtension();
					ackRet.setData(dataStr1);
					return ackRet;
				}
			}			
		}
		for(int j=0;j<comAparts.size();j++)
		{
			long cenGrpId1 = cenGrpService.getGroupIdByNumber(comAparts.get(j).getApartment());
			if(cenGrpId1<0)
			{
				CentraxGroup cenGrp = new CentraxGroup();
				long grpId = Long.parseLong(comAparts.get(j).getApartment());
				cenGrpId1 = grpId;
				cenGrp.setGroupId(grpId);
				cenGrp.setServIndex(0);
				cenGrp.setCentraxNumber(comAparts.get(j).getApartment());
				cenGrp.setOperationExtension("");
				cenGrp.setExtlen(4);
				cenGrp.setAnnouncement("");
				cenGrp.setAccessAnnounce("");
				cenGrp.setRouteAnnounce("");
				cenGrp.setGroupRing(1);
				cenGrp.setIntCallin(1);
				cenGrp.setPenetrateNumber(0);
				cenGrp.setLeafNode(0);
				cenGrp.setMaxCalls(0);
				cenGrp.setDescription("");
				try{
					cenGrpService.add(cenGrp);
				}
				catch(Exception e){
					logger.warn("The apartment "+comAparts.get(j).getApartment()+" add failed.");
					ackRet.setResult(3);
					String str1 = new String();
					str1 = "apartment:"+comAparts.get(j).getApartment()+"extension:"+comAparts.get(j).getExtension();
					ackRet.setData(str1);
					return ackRet;
				}
			}
			int grpId = sipService.getGroupIdByNumber(comAparts.get(j).getSipNumber());
			if(grpId<0)
			{
				String userNum = comAparts.get(j).getSipNumber();
				String password = comAparts.get(j).getPassword();
				String pstnNum = comAparts.get(j).getPstnNumber();
				int grpId1 = comAparts.get(j).getGroupId();
				String sqlCommand = new String();
				if(pstnNum==null||pstnNum.length()<2)
					sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback) values ('"+userNum+"',null,'"+password+"',1,"+String.valueOf(grpId1)+",'')";
				else
					sqlCommand = "insert into PSX_TAB_SIP_END_POINT (usernumber,username,token,status,groupid,colorringback,enableforward,forwardtrans,forwardcondition1,forwardvalue1,forwardnum1) values ('"+userNum+"',null,'"+password+"',1,"+String.valueOf(grpId1)+",'',1,1,1,'','"+pstnNum+"')";
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
						String str2 = new String();
						str2 = "apartment:"+comAparts.get(j).getApartment()+"extension:"+comAparts.get(j).getExtension();
						ackRet.setData(str2);
						return ackRet;
					}
				}
				else
				{
					logger.info("Fail to send SQL statemnet to OAMP.");
					ackRet.setResult(1);
					String str3 = new String();
					str3 = "apartment:"+comAparts.get(j).getApartment()+"extension:"+comAparts.get(j).getExtension();
					ackRet.setData(str3);
					return ackRet;
				}
			}
			CentraxNumber cenNum = new CentraxNumber();
			CentraxNumberPk cenPk = new CentraxNumberPk();
			cenPk.setGroupId(cenGrpId1);
			cenPk.setShortNumber(comAparts.get(j).getExtension());
			cenNum.setPk(cenPk);
			cenNum.setColorRing("");
			cenNum.setLongNumber(comAparts.get(j).getSipNumber());
			cenNum.setDescription("");
			cenNum.setEnableAccess(0);
			try{
				cenNumService.add(cenNum);
			}
			catch(Exception e){
				logger.warn("The extension add failed.");
				ackRet.setResult(3);
				String dataStr = new String();
				dataStr = "apartment:"+comAparts.get(j).getApartment()+"extension:"+comAparts.get(j).getExtension();
				ackRet.setData(dataStr);
				return ackRet;
			}					
		}
		ackRet.setResult(0);
		return ackRet;
	}
	
	@RequestMapping(value = ApplicationURIConstants.GET_CDR)
	public @ResponseBody List<CdrResult> getCDR(@RequestBody CdrCondition cdrCon){
		logger.info("Start get CDR");
		List<CdrResult> cdrResults = new ArrayList<CdrResult>();
		
		String number = cdrCon.getNumber();
		int flag = cdrCon.getFlag();
		String startTime =cdrCon.getStartTime();
		String endTime = cdrCon.getEndTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Cdr> cdrData = service.cdrAll(number,flag,startTime,endTime);
		for(int i=0;i<cdrData.size();i++)
		{
			CdrResult cdrResult = new CdrResult();
			cdrResult.setClgNumber(cdrData.get(i).getClgNumber());
			cdrResult.setCldNumber(cdrData.get(i).getCldNumber());
			cdrResult.setTime(sdf.format(cdrData.get(i).getConnectTime()));
			cdrResult.setDuration(cdrData.get(i).getDuration());
			cdrResults.add(cdrResult);
		}
		return cdrResults;
	}
	
}
