package com.sds.acube.app.idir.org.option;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * OptionManager.java 2002-10-30
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class OptionManager 
{
	/**
	 */
	private ConnectionParam 	m_connectionParam = null;
	private String 			m_strLastError = "";
	
	
	public OptionManager(ConnectionParam connectionParam)
	{
		m_connectionParam = connectionParam;	
	}
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError()
	{
		return m_strLastError;
	}
	
	/**
	 * 코드 리스트 반환
	 * @param nType 코드 Type
	 * @return Codes
	 */
	public Codes getCodes(int nType, int aaa)
	{
		CodeHandler codeHandler = new CodeHandler(m_connectionParam);
		Codes 		codes 		= null;
		
		codes = codeHandler.getCodes(nType, aaa);
		if (codes == null)
		{
			m_strLastError = codeHandler.getLastError();
		}
		
		return codes;
	}
	
	/**
	 * 코드 리스트 반환
	 * @param nType 코드 Type
	 * @return Codes
	 */
	public Codes getCodes(int nType, String strCompID)
	{
		CodeHandler codeHandler = new CodeHandler(m_connectionParam);
		Codes 		codes 		= null;
		
		codes = codeHandler.getCodes(nType, strCompID);
		if (codes == null)
		{
			m_strLastError = codeHandler.getLastError();
		}
		
		return codes;
	}

	/**
	 * 서버 정보 리스트 반환
	 * @return RelatedServers
	 */
	public RelatedServers getRelatedServers()
	{
		RelatedServerHandler relatedServerHandler = new RelatedServerHandler(m_connectionParam);
		RelatedServers		 relatedServers = null;
		
		relatedServers = relatedServerHandler.getRelatedServers();
		if (relatedServers == null)
		{
			m_strLastError = relatedServerHandler.getLastError();
		}
		
		return relatedServers;
	}
		
	/**
	 * 회사 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	public Option getCompanyOption(String strOptionID, String strCompID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		Option		  option = null;
		
		option = optionHandler.getCompanyOption(strOptionID, strCompID);
		if (option == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return option;
	}
	
	/**
	 * 회사 Option을 가져오는 함수
	 * @param strOptionID Option ID
	 * @param strUserUID   사용자 ID
	 * @return Option
	 */
	public Option getCompanyOptionByUID(String strOptionID, String strUserUID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		Option 		  option = null;
		
		option = optionHandler.getCompanyOptionByUID(strOptionID, strUserUID);
		if (option == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return option;
	}
	
	/**
	 * 회사 Option들을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return Option
	 */
	public Options getCompanyOptions(String strOptionID, String strCompID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		Options 	  options = null;
		
		options = optionHandler.getCompanyOptions(strOptionID, strCompID);
		if (options == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return options;
	}
	
	/**
	 * 사용자 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strUserUID   User UID
	 * @return Option
	 */
	public Option getUserOption(String strOptionID, String strUserUID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		Option		  option = null;
		
		option = optionHandler.getUserOption(strOptionID, strUserUID);
		if (option == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return option;
	}
	
	/**
	 * 사용자 Option들을을 가져오는 함수.
	 * @param strOptionID 		Option ID  (ex: OptionID^OptionID)
	 * @param strCompID    	Company ID
	 * @param strUserUID   	User UID
	 * @return Options
	 */
	public Options getUserOptions(String strOptionID, String strCompID, String strUserUID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		Options		  options = null;
		
		options = optionHandler.getUserOptions(strOptionID, strCompID, strUserUID);
		if (options == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return options;
	}
	
	/**
	 * 회사 전체 Option을 가져오는 함수 
	 * @param strCompID	CompanyID
	 * @return CompOptions
	 */
	public ApprovalOptions getAllApprovalOptions(String strCompID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		ApprovalOptions	  compOptions = null;
		
		compOptions = optionHandler.getAllApprovalOptions(strCompID);
		if (compOptions == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return compOptions;		
	}
	
	/**
	 * 선택된 Option을 가져오는 함수 
	 * @param strCompID	CompanyID
	 * @param strOptionIDs Option 정보들 
	 * @return CompOptions
	 */
	public ApprovalOptions getApprovalOptions(String strCompID, String strOptionIDs)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		ApprovalOptions	  compOptions = null;
		
		compOptions = optionHandler.getApprovalOptions(strCompID, strOptionIDs);
		if (compOptions == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return compOptions;	
	}
	
	/**
	 * 회사 Option을 가져오는 함수.
	 * @param strOptionID Option ID
	 * @param strCompID   Company ID
	 * @return ApprovalOption
	 */
	public ApprovalOption getApprovalOption(String strCompID, String strOptionID)
	{
		OptionHandler optionHandler = new OptionHandler(m_connectionParam);
		ApprovalOption	  approvalOption = null;
		
		approvalOption = optionHandler.getApprovalOption(strCompID, strOptionID);
		if (approvalOption == null)
		{
			m_strLastError = optionHandler.getLastError();
		}
		
		return approvalOption;	
	}
	
	/**
	 * 주어진 ZipCode를 가지는 우편 정보 반환.
	 * @param strZipCode
	 * @return Addresses
	 */
	public Addresses getAddressesByDONG(String strZipCode)
	{
		AddressHandler addressHandler = new AddressHandler(m_connectionParam);
		Addresses      addresses = null;
		
		addresses = addressHandler.getAddressesByDONG(strZipCode);
		if (addresses == null)
		{
			m_strLastError = addressHandler.getLastError();
		}
		
		return addresses;
	}
	
	/**
	 * Global Information 리스트 반환
	 * @return GlobalInformations
	 */
	public GlobalInformations getGlobalInformations()
	{
		GlobalInfoHandler 	globalInfoHandler = new GlobalInfoHandler(m_connectionParam);
		GlobalInformations 	globalInformations = null;
		
		globalInformations = globalInfoHandler.getGlobalInformations();
		if (globalInformations == null)
		{
			m_strLastError = globalInfoHandler.getLastError();
		}
	
		return globalInformations;
	}
	
	/**
	 * 설치 서버의 OSType을 반환하는 함수. 
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoOSType()
	{
		GlobalInfoHandler	globalInfoHandler = new GlobalInfoHandler(m_connectionParam);
		GlobalInformation 	globalInformation = null;
		
		globalInformation = globalInfoHandler.getGlobalInfoOSType();
		if (globalInformation == null)
		{
			m_strLastError = globalInfoHandler.getLastError();
		}
		
		return globalInformation;
	}
	
	/**
	 * 조직 DB 정보의 사용자 Key를 반환하는 함수. 
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoUserInfoKey()
	{
		GlobalInfoHandler	globalInfoHandler = new GlobalInfoHandler(m_connectionParam);
		GlobalInformation 	globalInformation = null;
		
		globalInformation = globalInfoHandler.getGlobalInfoUserInfoKey();
		if (globalInformation == null)
		{
			m_strLastError = globalInfoHandler.getLastError();
		}
		
		return globalInformation;
	}

	/**
	 * IAM administrator 정보를 반환하는 함수. 
	 * @return GlobalInformation
	 */
	public GlobalInformation getGlobalInfoAdmin()
	{
		GlobalInfoHandler	globalInfoHandler = new GlobalInfoHandler(m_connectionParam);
		GlobalInformation 	adminInfo = null;
		
		adminInfo = globalInfoHandler.getGlobalInfoAdmin();
		if (adminInfo == null)
		{
			m_strLastError = globalInfoHandler.getLastError();
		}
		
		return adminInfo;
	}
}
