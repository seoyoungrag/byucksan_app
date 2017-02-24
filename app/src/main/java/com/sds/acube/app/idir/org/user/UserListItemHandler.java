package com.sds.acube.app.idir.org.user;

/**
 * UserListItemHandler.java
 * 2002-10-23
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.common.constants.*;
import com.sds.acube.app.idir.org.option.*;
import com.sds.acube.app.idir.org.db.*;

public class UserListItemHandler extends DataHandler
{
	private static final int LABEL = 0;
	private static final int SIZE = 1;
	private static final int SORT_TYPE = 2;
	
	private static final String DELIMITER = "^";
	
	private static final String OPTION_LIST_WIDTH = "AIOPT147";	// 리스트 폭 설정
	private static final String OPTION_LIST_COUNT = "AIOPT146";	// 페이지 당 목록 개수
	private static final String OPTION_PAGE_COUNT = "AIOPT145";	// 페이지 개수
	
	private static final int    DEFAULT_LIST_COUNT = 10;			// 기본 페이지당 목록 개수
	private static final int    DEFAULT_PAGE_COUNT = 10; 		// 기본 페이지 개수
		
	public UserListItemHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
	}
	
	/**
	 * 사용자의 List Column 정보를 가져오는 함수 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserListItems(String strUserUID, String strContainerName)
	{
		UserListItems 		userListItems = null;
		UserOption			userOption = null;
		String[][]	  		strListColumns = null;
		String[]			strListLabelArray = null;
		String 				strListLabels = "";
		String 				strDelimiter = "^";
		Option				option = null;
				
		// 개인옵션 적용
		userOption = getUserListOption(strUserUID, strContainerName);
		if (userOption != null)
		{
			userListItems = makeOptionListItems(strContainerName, userOption.getMStringValue());
		}
		
		// default option 적용 
		if (userListItems == null || userListItems.size() == 0)
		{
			strListColumns = UserListMap.getDefaultCabListInfo(strContainerName);
			userListItems = new UserListItems();
			
			for (int i = 0; i < strListColumns.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				
				userListItem.setListLabel(strListColumns[i][LABEL]);
				userListItem.setListSize(strListColumns[i][SIZE]);
				userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
						
				userListItems.add(userListItem);
			}		
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 List Column 정보를 가져오는 함수
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserListItems(String strCompID, String strUserUID, String strContainerName)
	{
		OptionHandler       optionHandler = null;
		UserListItems 		userListItems = null;
		String[][]	  		strListColumns = null;
		String[]			strListLabelArray = null;
		Options				userOptions = null;
		Option 	            userOption = null;
		String 				strListLabels = "";
		String 				strDisplayOptionID = "";
		String 				strSortOptionID = "";
		String 				strSortData = "";
		String 				strOptions = "";
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Company ID.",
								   "UserListItemHandler.getUserListItems.Empty Company ID,",
								   "");
			return userListItems;
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "UserListItemHandler.getUserListItems.Empty User UID,",
								   "");
			return userListItems;
		}
		
		if (strContainerName == null || strContainerName.length() == 0)
		{
			m_lastError.setMessage("Fail to get ContainerName.",
								   "UserListItemHandler.getUserListItems.Empty ContainerName,",
								   "");
			return userListItems;
		}
		
		optionHandler = new OptionHandler(m_connectionBroker.getConnectionParam());
		
		strDisplayOptionID = UserListMap.getOptionID(strContainerName);
		if (strDisplayOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getUserListItems.UserListMap.getListOptionID",
								   "");
			return userListItems;
		}
		
		strSortOptionID = UserListMap.getDefaultSortOptionID(strContainerName);
		if (strSortOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getUserListItems.UserListMap.getListOptionID",
								   "");
			return userListItems;
		}
		
			
		// 반영할 옵션 리스트
		strOptions = OPTION_LIST_COUNT + "^" +
		             OPTION_LIST_WIDTH + "^" +
		             OPTION_PAGE_COUNT + "^" +
		             strDisplayOptionID + "^" +
		             strSortOptionID + "^";
		
		// 개인옵션 적용
		userOptions = optionHandler.getUserOptions(strOptions, strCompID, strUserUID);
			
		if (userOptions != null)
		{
			// DisplayOption
			userOption = userOptions.getOptionByID(strDisplayOptionID);
			if (userOption != null)
				userListItems = makeOptionListItems(strContainerName, userOption.getMStringValue());
			
		   
			// 리스트 Width
			userOption = userOptions.getOptionByID(OPTION_LIST_WIDTH);
			if (userOption != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setListWidth(userOption.getIntegerValue());
			}
			
			// 리스트 페이지
			userOption = userOptions.getOptionByID(OPTION_LIST_COUNT);
			if (userOption != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setListCount(userOption.getIntegerValue());
			}
			
			// 리스트 목록 개수 
			userOption = userOptions.getOptionByID(OPTION_PAGE_COUNT);
			if (userOption != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setPageCount(userOption.getIntegerValue());
			}

			
			// Default Sort 정보
			userOption = userOptions.getOptionByID(strSortOptionID);
			if (userOption != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				strSortData = userOption.getMStringValue();
			}
		}
				
		// default option 적용 
		if (userListItems == null || userListItems.size() == 0)
		{
			strListColumns = UserListMap.getDefaultCabListInfo(strContainerName);
			userListItems = new UserListItems();
			
			for (int i = 0; i < strListColumns.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				
				userListItem.setListLabel(strListColumns[i][LABEL]);
				userListItem.setListSize(strListColumns[i][SIZE]);
				userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
						
				userListItems.add(userListItem);
			}		
		}
		
		// Sort 정보 셋팅
		if (strSortData != null && strSortData.length() > 0)
		{
			UserListItem userSortItem = makeDefaultSortOptionItem(strContainerName, strSortData);
			
			if (userListItems != null && userSortItem != null)
			{
				for (int i = 0 ; i < userListItems.size() ; i++)
				{
					UserListItem tempUserListItem = userListItems.get(i);
					if (tempUserListItem.getListLabel().compareTo(userSortItem.getListLabel()) == 0)
					{
						tempUserListItem.setSortFlag(userSortItem.getSortFlag());
						tempUserListItem.setIsDefault(true);
					}
				}
			}		
		}
		
		if (userListItems.getListCount() == 0)
		{
			userListItems.setListCount(DEFAULT_LIST_COUNT);
		}
		
		if (userListItems.getPageCount() == 0)
		{
			userListItems.setPageCount(DEFAULT_PAGE_COUNT);
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 List Column 정보를 Array로 가져오는 함수(편철용)
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public String[][] getUserListItemArray(String strCompID, String strUserUID, String strContainerName)
	{
		UserListItems userListItems = null;
		UserListItem  sortListItem = null;
		String[][] 	  arrUserListItem = null;
		String 		  strListTitle = "";
		String		  strPageIndex = "";
		String 		  strSortInfo = "";
		int			  nItemSize = 0;
		int			  nIndex = 0;
		
		if (strContainerName == null ||
			(strContainerName.compareTo("DOCREGILEDGER") != 0 && 
			 strContainerName.compareTo("DOCDISTLEDGER") != 0))
		{
			m_lastError.setMessage("Fail to get correct container name",
								   "UserListItemHandler.getUserListItemArray.incorrect container Name",
								   "");
			return arrUserListItem;
		}  
		
		// 사용자의 User List 설정 정보를 추출
		userListItems = getUserListItems(strCompID, strUserUID, strContainerName);
		if (userListItems == null)
		{
			return arrUserListItem;
		}
		
		nItemSize = userListItems.size();
		nItemSize += 4;
		
		arrUserListItem = new String[nItemSize][3];
		
		// Setting List basic information
		arrUserListItem[nIndex][0] = "LIST_COUNT";
		arrUserListItem[nIndex][1] = "리스트개수";
		arrUserListItem[nIndex++][2] = Integer.toString(userListItems.getListCount());
		
		arrUserListItem[nIndex][0] = "LIST_WIDTH";
		arrUserListItem[nIndex][1] = "리스트넓이";
		arrUserListItem[nIndex++][2] = Integer.toString(userListItems.getListWidth());
		
		arrUserListItem[nIndex][0] = "PAGE_COUNT";
		arrUserListItem[nIndex][1] = "페이지개수";
		arrUserListItem[nIndex++][2] = Integer.toString(userListItems.getPageCount());
		
		// Setting List Title Information		
		if (strContainerName.compareTo("DOCREGILEDGER") == 0)
		{
			strListTitle = "기록물등록대장";
			strPageIndex = "REC";		
		}
		else if (strContainerName.compareTo("DOCDISTLEDGER") == 0)
		{
			strListTitle = "기록물배부대장";
			strPageIndex = "DSC";		
		}
		
		sortListItem = userListItems.getUserSortListItem();
		if (sortListItem != null)
		{
			strSortInfo = sortListItem.getListSortType() + " " + sortListItem.getSortFlag().toLowerCase();	
		}
		else
		{
			strSortInfo = "DATE1 desc";
		}
		
		arrUserListItem[nIndex][0] = strPageIndex;
		arrUserListItem[nIndex][1] = strListTitle;
		arrUserListItem[nIndex++][2] = strSortInfo;
		
		// set column information
		for (int i = 0 ; i < userListItems.size(); i++)
		{
			UserListItem userListItem = userListItems.get(i);
			
			arrUserListItem[nIndex][0] = userListItem.getListSortType();
			arrUserListItem[nIndex][1] = userListItem.getListLabel();
			arrUserListItem[nIndex++][2] = userListItem.getListSize();		
		}
			
		return arrUserListItem;
	}
	
	/**
	 * 사용자의 기본 List Data를 가져오는 함수 
	 * @param strCompID            회사 ID
	 * @param strUserUID 			사용자 UID
	 * @param strContainerName     함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserBasicListItems(String strCompID, 
												String strUserUID)
	{
		OptionHandler optionHandler = null;
		UserListItems userListItems = null;
		Options		  options = null;
		Option 	      option = null;
		String 		  strOptions = "";
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Company ID.",
								   "UserListItemHandler.getUserBasicListItems.Empty Company ID,",
								   "");
			return userListItems;
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "UserListItemHandler.getUserBasicListItems.Empty User UID,",
								   "");
			return userListItems;
		}
		
		optionHandler = new OptionHandler(m_connectionBroker.getConnectionParam());
		
			
		// 반영할 옵션 리스트
		strOptions = OPTION_LIST_COUNT + "^" +
		             OPTION_LIST_WIDTH + "^" +
		             OPTION_PAGE_COUNT + "^";
		
		// 개인옵션 적용
		options = optionHandler.getUserOptions(strOptions, strCompID, strUserUID);
		
		if (options != null)
		{		   
			// 리스트 Width
			option = options.getOptionByID(OPTION_LIST_WIDTH);
			if (option != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setListWidth(option.getIntegerValue());
			}
			
			// 리스트 페이지
			option = options.getOptionByID(OPTION_LIST_COUNT);
			if (option != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setListCount(option.getIntegerValue());
			}
			
			// 리스트 목록 개수 
			option = options.getOptionByID(OPTION_PAGE_COUNT);
			if (option != null)
			{
				if (userListItems == null)
					userListItems = new UserListItems();
						
				userListItems.setPageCount(option.getIntegerValue());
			}		
		}
		
		if (userListItems == null)
		{
			userListItems = new UserListItems();
		}	
		
		if (userListItems.getListCount() == 0)
		{
			userListItems.setListCount(DEFAULT_LIST_COUNT);
		}
		
		if (userListItems.getPageCount() == 0)
		{
			userListItems.setPageCount(DEFAULT_PAGE_COUNT);
		}
		
		return userListItems;	
	}
	
	/**
	 * 사용자의 Default Sort 정보를 가져오는 함수
	 * @param strCompID 			회사 ID 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItem getUserSortListItem(String strCompID, String strUserUID, String strContainerName)
	{
		UserListItem 		userListItem = null;
		UserOption			userOption = null;
		String[][]	  		strListColumns = null;
		String[]			strListLabelArray = null;
		String 				strListLabels = "";
		String 				strDelimiter = "^";
		Option				option = null;
		
		// 개인옵션 적용
		userOption = getUserDefaultSortOption(strUserUID, strContainerName);
		if (userOption != null)
		{
			userListItem = makeDefaultSortOptionItem(strContainerName, userOption.getMStringValue());
		}
		
		// 회사옵션적용
		if (userListItem == null)
		{
			option = getCompDefaultSortOption(strCompID, strContainerName);
			if (option != null)
			{
				userListItem = makeDefaultSortOptionItem(strContainerName, option.getMStringValue());
			}					
		}		
				
		return userListItem;
	}
	
	/**
	 * 사용자의 Qubelet List Column 정보를 가져오는 함수 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserQubeletListItems(String strUserUID, String strContainerName)
	{
		UserListItems 		userListItems = null;
		UserOption			userOption = null;
		String[][]	  		strListColumns = null;
		String[]			strListLabelArray = null;
		String 				strListLabels = "";
		String 				strDelimiter = "^";
		
		if (userListItems == null || userListItems.size() == 0)
		{
			strListColumns = UserQubeletListMap.getDefaultCabListInfo(strContainerName);
			userListItems = new UserListItems();
			
			for (int i = 0; i < strListColumns.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				
				userListItem.setListLabel(strListColumns[i][LABEL]);
				userListItem.setListSize(strListColumns[i][SIZE]);
				userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
						
				userListItems.add(userListItem);
			}		
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 Mobile List Column 정보를 가져오는 함수 
	 * @param strUserUID			사용자 UID
	 * @param strContainerName		함 식별자
	 * @return UserListItems
	 */
	public UserListItems getUserMobileListItems(String strUserUID, String strContainerName)
	{
		UserListItems 		userListItems = null;
		UserOption			userOption = null;
		String[][]	  		strListColumns = null;
		String[]			strListLabelArray = null;
		String 				strListLabels = "";
		String 				strDelimiter = "^";
		
		if (userListItems == null || userListItems.size() == 0)
		{
			strListColumns = UserMobileListMap.getDefaultCabListInfo(strContainerName);
			userListItems = new UserListItems();
			
			for (int i = 0; i < strListColumns.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				
				userListItem.setListLabel(strListColumns[i][LABEL]);
				userListItem.setListSize(strListColumns[i][SIZE]);
				userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
						
				userListItems.add(userListItem);
			}		
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 Default List Column 정보를 가져오는 함수 
	 * @param strContainerName
	 * @return UserListItems
	 */
	public UserListItems getDefaultListItems(String strContainerName)
	{
		UserListItems userListItems = null;
		String[][]	  strListColumns = null;

				
		// Default 정보를 가져오는 부분  
		if (strListColumns == null)
		{
			strListColumns = UserListMap.getDefaultCabListInfo(strContainerName);
			userListItems = new UserListItems();
			
			for (int i = 0; i < strListColumns.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				
				userListItem.setListLabel(strListColumns[i][LABEL]);
				userListItem.setListSize(strListColumns[i][SIZE]);
				userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
						
				userListItems.add(userListItem);
			}		
		}
		
		return userListItems;
	}
	
	/**
	 * 사용자의 Default List Column 정보를 가져오는 함수 
	 * @param strCompID          회사명 
	 * @param strContainerName
	 * @return UserListItems
	 */
	public UserListItems getDefaultListItems(String strCompID,
											  String strContainerName)
	{
		UserListItems userListItems = null;
		String[][]	  strListColumns = null;
		Option        option = null;
		
		option = getCompListOption(strCompID, strContainerName);
		if (option != null)
		{
			userListItems = makeOptionListItems(strContainerName, option.getMStringValue());
		}
		
		if (userListItems == null || userListItems.size() == 0)
		{		
			// Default 정보를 가져오는 부분  
			if (strListColumns == null)
			{
				strListColumns = UserListMap.getDefaultCabListInfo(strContainerName);
				userListItems = new UserListItems();
				
				if (strListColumns != null) {
					for (int i = 0; i < strListColumns.length ; i++)
					{
						UserListItem userListItem = new UserListItem();
						
						userListItem.setListLabel(strListColumns[i][LABEL]);
						userListItem.setListSize(strListColumns[i][SIZE]);
						userListItem.setListSortType(strListColumns[i][SORT_TYPE]);
								
						userListItems.add(userListItem);
					}	
				}
			}
		}
		
		return userListItems;
	}
	
	/**
	 * ListOption 정보를 가져오는 함수 
	 * @param strUserUID 		사용자 UID
	 * @param strCotainerName 	함 이름 
	 * @return UserOption
	 */
	private UserOption getUserListOption(String strUserUID, String strContainerName)
	{
		UserOptionHandler 	userOptionHandler = null;
		UserOption 			userOption = null;
		String 	   			strOptionID = "";
		
		strOptionID = UserListMap.getOptionID(strContainerName);
		if (strOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getUserListOption.UserListMap.getDefaultOptionID",
								   "");
			return userOption;
		}
		
		userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
												  
		userOption = userOptionHandler.getUserOption(strUserUID, strOptionID);								
		if (userOption == null)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
		}
		
		return userOption;
	}
		
	/**
	 * ListOption 정보를 가져오는 함수
	 * @param strCompanyID	  	회사 ID
	 * @param strContainerName 함 이름 
	 * @return  Options
	 */
	private Option getCompListOption(String strCompanyID, String strContainerName)
	{
		OptionHandler optionHandler = null;
		Option		  option = null;
		String 		  strOptionID = "";
		
		strOptionID = UserListMap.getOptionID(strContainerName);
		if ((strOptionID == null) || (strOptionID.length() == 0))
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getCompListOption.UserListMap.getListOptionID",
								   "");
			return option;
		}
		
		
		optionHandler = new OptionHandler(m_connectionBroker.getConnectionParam());
		
		option = optionHandler.getCompanyOption(strOptionID, strCompanyID);
		if (option == null)
		{
			m_lastError.setMessage(optionHandler.getLastError());
		}
		
		return option;
	}
	
	/**
	 * List Display Option을 변환하는 함수
	 * @param strContainerName 결재함명 
	 * @param strOptionValue 옵션값
	 * @return UserListItems
	 */
	private UserListItems makeOptionListItems(String strContainerName, String strOptionValue)
	{
		UserListItems userListItems = null;
		String[]	  arListItems = null;
		String 		  strDelimiter = "^";
		String 		  strSubDelimiter = ":";
		
		if (strOptionValue == null || strOptionValue.length() == 0)
		{
			m_lastError.setMessage("Fail to get option value.",
								   "UserListItemHandler.makeOptionListItems.Empty Option Value",
								   "");
			return userListItems;
		}

		arListItems = DataConverter.splitString(strOptionValue, strDelimiter);
		
		if (arListItems != null)
		{
			userListItems = new UserListItems();
			
			for (int i = 0 ; i < arListItems.length ; i++)
			{
				String[] arListItem = null;
				
				arListItem = DataConverter.splitString(arListItems[i], strSubDelimiter);
				if (arListItem != null && arListItem.length == 3)
				{
					UserListItem userListItem = null;
					
					userListItem = 	UserListMap.getDefaultListItem(strContainerName,
																   arListItem[LABEL]);
					
					if (userListItem != null)
					{											   
						userListItem.setListSize(arListItem[SIZE]);	
																						   
						userListItems.add(userListItem);
					}															   							   
				} 
			}	
		}
		
		return userListItems;
	}
	
	/**
	 * Default Sort ListOption 정보를 가져오는 함수 
	 * @param strUserUID 		사용자 UID
	 * @param strCotainerName 	함 이름 
	 * @return UserOption
	 */
	private UserOption getUserDefaultSortOption(String strUserUID, String strContainerName)
	{
		UserOptionHandler 	userOptionHandler = null;
		UserOption 			userOption = null;
		String 	   			strOptionID = "";
		
		strOptionID = UserListMap.getDefaultSortOptionID(strContainerName);
		if ((strOptionID == null) || (strOptionID.length() == 0))
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getUserDefaultSortOption.UserListMap.getDefaultOptionID",
								   "");
			return userOption;
		}
		
		userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
												  
		userOption = userOptionHandler.getUserOption(strUserUID, strOptionID);								
		if (userOption == null)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
		}
		
		return userOption;
	}
	
	/**
	 * Default Sort ListOption 정보를 가져오는 함수
	 * @param strCompanyID	  	회사 ID
	 * @param strContainerName 함 이름 
	 * @return  Options
	 */
	private Option getCompDefaultSortOption(String strCompanyID, String strContainerName)
	{
		OptionHandler optionHandler = null;
		Option		  option = null;
		String 		  strOptionID = "";
		
		strOptionID = UserListMap.getDefaultSortOptionID(strContainerName);
		if ((strOptionID == null) || (strOptionID.length() == 0))
		{
			m_lastError.setMessage("Fail to get Option ID",
								   "UserListItemHandler.getCompDefaultSortListOption.UserListMap.getListOptionID",
								   "");
			return option;
		}
		
		
		optionHandler = new OptionHandler(m_connectionBroker.getConnectionParam());
		
		option = optionHandler.getCompanyOption(strOptionID, strCompanyID);
		if (option == null)
		{
			m_lastError.setMessage(optionHandler.getLastError());
		}
		
		return option;
	}
	
	/**
	 * List Default Sort Option을 변환하는 함수
	 * @param strContainerName 결재함명 
	 * @param strOptionValue 옵션값
	 * @return UserListItem
	 */
	private UserListItem makeDefaultSortOptionItem(String strContainerName, String strOptionValue)
	{
		UserListItem  userListItem = null;
		String[]	  arListItems = null;
		String 		  strDelimiter = "^";
		String 		  strSubDelimiter = ":";
		
		if (strOptionValue == null || strOptionValue.length() == 0)
		{
			m_lastError.setMessage("Fail to get option value.",
								   "UserListItemHandler.makeDefaultSortOptionListItem.Empty Option Value",
								   "");
			return userListItem;
		}
		
		arListItems = DataConverter.splitString(strOptionValue, strDelimiter);
		
		if (arListItems != null)
		{	
			for (int i = 0 ; i < arListItems.length ; i++)
			{
				String[] arListItem = null;
				
				arListItem = DataConverter.splitString(arListItems[i], strSubDelimiter);
				if (arListItem != null && arListItem.length == 2)
				{
					userListItem = 	UserListMap.getDefaultListItem(strContainerName,
																   arListItem[LABEL]);
					
					if (userListItem != null)
					{											   
						userListItem.setSortFlag(arListItem[1]);																						  
					}															   							   
				} 
			}	
		}
		
		return userListItem;
	}


	/**
	 * ListOption 정보를 등록하는 함수 
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strListLabels 	List 제목 
	 * @return boolean
	 */
	public boolean registerListOption(String strUserUID,
									    String strContainerName,
									    String strListLabels)
	{
		UserOptionHandler userOptionHandler = null;
		UserOption		  userOption = null;
		boolean 		  bReturn = false;
		String[] 		  arListLabels = null;
		String	 		  strOptionID = "";
		int			  nOptionType = 2;		// MultiString
		
		strOptionID = UserListMap.getOptionID(strContainerName);
		if (strOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID.",
								   "UserListItemHandler.registerListOption.UserListMap.getOptionID",
								   "");
			return bReturn;
								   
		}
				
		userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
												  
		userOption = new UserOption();
		
		userOption.setUserUID(strUserUID);
		userOption.setOptionID(strOptionID);
		userOption.setOptionType("O_INHERIT");
		userOption.setValueType(nOptionType);
		userOption.setMStringValue(strListLabels);
		
		bReturn = userOptionHandler.registerUserOption(userOption);
		if (!bReturn)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
		}
		
		return bReturn;
	} 
	
	/**
	 * ListOption 정보를 등록하는 함수 
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strListLabels 	List 제목 
	 * @return boolean
	 */
	public boolean registerListOption(String strCompID,
										String strUserUID,
									    String strContainerName,
									    String strListLabels)
	{
		UserOptionHandler userOptionHandler = null;
		UserListItems	  defaultListItems = null;
		UserListItems 	  userListItems = null;
		UserOption		  userOption = null;
		boolean 		  bReturn = false;
		String[] 		  arListLabels = null;
		String	 		  strOptionID = "";
		String 			  strListData = "";
		int			  nOptionType = 2;		// MultiString
		
		strOptionID = UserListMap.getOptionID(strContainerName);
		if (strOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID.",
								   "UserListItemHandler.registerListOption.UserListMap.getOptionID",
								   "");
			return bReturn;
								   
		}
		
		defaultListItems = getDefaultListItems(strCompID, strContainerName);
				
		// Option 등록
		arListLabels = DataConverter.splitString(strListLabels, DELIMITER);
		if (arListLabels != null && arListLabels.length > 0)
		{
			userListItems = new UserListItems();
			
			for (int i = 0 ; i < arListLabels.length ; i++)
			{
				UserListItem userListItem = new UserListItem();
				UserListItem defaultListItem = defaultListItems.getUserListItembyLabel(arListLabels[i]);
				
				userListItem.setListLabel(arListLabels[i]);
				
				if (defaultListItem != null)
				{
					userListItem.setListSize(defaultListItem.getListSize());
					userListItem.setListSortType(defaultListItem.getListSortType());
				}
				
				userListItems.add(userListItem);
			}		
		}
		
		if (userListItems != null && userListItems.size() > 0)
		{	
			for (int j = 0 ; j < userListItems.size() ; j++)
			{
				UserListItem userListItem = userListItems.get(j);
				
				strListData += userListItem.getListLabel() + ":" +
							   userListItem.getListSize() + ":" +
							   userListItem.getListSortType().replace('^','/') + "^";	
			}
			
		    if (strListData != null && strListData.length() > 0)
		    {	
				userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
														  
				userOption = new UserOption();
				
				userOption.setUserUID(strUserUID);
				userOption.setOptionID(strOptionID);
				userOption.setOptionType("O_INHERIT");
				userOption.setValueType(nOptionType);
				userOption.setMStringValue(strListData);
				
				bReturn = userOptionHandler.registerUserOption(userOption);
				if (!bReturn)
				{
					m_lastError.setMessage(userOptionHandler.getLastError());
				}
		    }
		    else
		    {
		    	bReturn = true;
		    }
		}
		
		return bReturn;
	}
	
	/**
	 * ListOption Default Sort정보를 등록하는 함수 
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strSortData 		Sort정보 
	 * @return boolean
	 */
	public boolean registerListSortOption(String strCompID,
											String strUserUID,
									    	String strContainerName,
									    	UserListItem userListItem)
	{
		UserOptionHandler userOptionHandler = null;
		UserOption		  userOption = null;
		boolean 		  bReturn = false;
		String	 		  strOptionID = "";
		String 			  strOptionData = "";
		int			  nOptionType = 2;		// MultiString
		
		if (userListItem == null)
		{
			m_lastError.setMessage("Fail to get user List Item.",
								   "UserListItemHandler.registerListSortOption.Empty userListItem",
								   "");
			return bReturn;			
		}
		
		strOptionID = UserListMap.getDefaultSortOptionID(strContainerName);
		if (strOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Option ID.",
								   "UserListItemHandler.registerListSortOption.UserListMap.getOptionID",
								   "");
			return bReturn;
								   
		}
		
		strOptionData = userListItem.getListLabel() + ":" + userListItem.getSortFlag() + "^";
				
	    if (strOptionData != null && strOptionData.length() > 0)
	    {	
			userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
													  
			userOption = new UserOption();
			
			userOption.setUserUID(strUserUID);
			userOption.setOptionID(strOptionID);
			userOption.setOptionType("O_INHERIT");
			userOption.setValueType(nOptionType);
			userOption.setMStringValue(strOptionData);
			
			bReturn = userOptionHandler.registerUserOption(userOption);
			if (!bReturn)
			{
				m_lastError.setMessage(userOptionHandler.getLastError());
			}
	    }
	    else
	    {
	    	bReturn = true;
	    }
		
		return bReturn;
	}
	
	
	/**
	 * 리스트에 관련된 모든 정보를 지우는 함수
	 * @param strUserUID
	 * @return boolean
	 */
	public boolean deleteAllUserListOptions(String strUserUID)
	{
		UserOptionHandler userOptionHandler = null;
		UserOptions userOptions = null;
		boolean 	bReturn = false;
		int         i;
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User UID.",
								   "UserListItemHandler.deleteAllUserListOptions.Empty User ID",
								   "");
			return bReturn;			
		}
		
		userOptions = new UserOptions();
		
		
		for (i = 0; i < UserListMap.m_strOptionIDArray.length ; i++)
		{
			UserOption userOption = new UserOption();
			
			userOption.setOptionID(UserListMap.m_strOptionIDArray[i]);
			
			userOptions.add(userOption);
		}
		
		for (i = 0; i < UserListMap.m_strOptionIDArray.length ; i++)
		{
			UserOption userOption = new UserOption();
			
			userOption.setOptionID(UserListMap.m_strSortOptionIDArray[i]);
			
			userOptions.add(userOption);
		}
		
		// 리스트 폭 설정
		UserOption userWidthOption = new UserOption();
		userWidthOption.setOptionID(OPTION_LIST_WIDTH);
		userOptions.add(userWidthOption);
		
		// 페이지 당 목록 개수
		UserOption userListOption = new UserOption();
		userListOption.setOptionID(OPTION_LIST_COUNT);
		userOptions.add(userListOption);
		
		// 페이지 개수
		UserOption userPageOption = new UserOption();
		userPageOption.setOptionID(OPTION_PAGE_COUNT);
		userOptions.add(userPageOption);
			
		userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
		
		bReturn = userOptionHandler.deleteUserOptions(strUserUID, userOptions);
		if (!bReturn)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
		}
			
		return bReturn;	
	}
	
	/**
	 * List Item의 기본 List Data를 등록하는 함수
	 * @param strCompID		회사 ID
	 * @param strUserUID		사용자 UID
	 * @param strContainerName 함 이름 
	 * @param strSortData 		Sort정보 
	 * @return boolean
	 */
	public boolean registerListBasicData(String strCompID,
										   String strUserUID,
									       UserListItems userListItems)
	{
		UserOptionHandler userOptionHandler = null;
		UserOption		  userOption = null;
		boolean 		  bReturn = false;
		int			  nOptionType = 0;		// MultiString
		
		if (strCompID == null || strCompID.length() == 0)
		{
			m_lastError.setMessage("Fail to get Company ID.",
								   "UserListItemHandler.registerListBasicData.Empty Company ID,",
								   "");
			return bReturn;
		}
		
		if (strUserUID == null || strUserUID.length() == 0)
		{
			m_lastError.setMessage("Fail to get User ID.",
								   "UserListItemHandler.registerListBasicData.Empty User UID,",
								   "");
			return bReturn;
		}
				
		if (userListItems == null)
		{
			m_lastError.setMessage("Fail to get user List Items.",
								   "UserListItemHandler.registerListBasicData.Empty userListItem",
								   "");
			return bReturn;			
		}
		
		userOptionHandler = new UserOptionHandler(m_connectionBroker.getConnectionParam());
		
		// 리스트 폭 설정
		userOption = new UserOption();
		
		userOption.setUserUID(strUserUID);
		userOption.setOptionID(OPTION_LIST_WIDTH);
		userOption.setOptionType("O_INHERIT");
		userOption.setValueType(nOptionType);
		userOption.setIntegerValue(userListItems.getListWidth());
		
		bReturn = userOptionHandler.registerUserOption(userOption);
		if (!bReturn)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
			return bReturn;
		}
				
		// 페이지 당 목록 개수
		userOption.setOptionID(OPTION_LIST_COUNT);
		userOption.setIntegerValue(userListItems.getListCount());
		bReturn = userOptionHandler.registerUserOption(userOption);
		if (bReturn == false)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
			return bReturn;
		}
		
		// 페이지 개수
		userOption.setOptionID(OPTION_PAGE_COUNT);
		userOption.setIntegerValue(userListItems.getPageCount());
		bReturn = userOptionHandler.registerUserOption(userOption);
		if (bReturn == false)
		{
			m_lastError.setMessage(userOptionHandler.getLastError());
			return bReturn;
		}
			
		return bReturn;
	}	
}
