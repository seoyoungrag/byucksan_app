package com.sds.acube.app.idir.org.user;

/**
 * SubstituteHandler.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.TableDefinition;
import com.sds.acube.app.idir.org.db.DataHandler;

public class SubstituteHandler extends DataHandler
{
	private static final int SUBSTITUTE_ALL = 0;
	private static final int SUBSTITUTE_VALID = 1;
	private static final int SUBSTITUTE_INVALID = 2;
	private static final int SUBSTITUTE_CURRENT = 3;	
	
	private String m_strEmployeeColumns = "";
	private String m_strEmployeeTable = "";
	private String m_strAssociatedColumns = "";
	private String m_strAssociatedTableColumns = "";
	private String m_strAssociatedTable = "";	
	
	public SubstituteHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strEmployeeTable = TableDefinition.getTableName(TableDefinition.USER_LIST_VIEW);
		m_strEmployeeColumns = m_strEmployeeTable + "." + UserViewTableMap.getColumnName(UserViewTableMap.USER_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_UID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GROUP_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_ID) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.PART_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_OTHER_NAME) +","+ 
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.TITLE_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ORDER) +","+
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_ORDER) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_RID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.SERVERS) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED1) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED2) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.RESERVED3);
							   
		m_strAssociatedTable = TableDefinition.getTableName(TableDefinition.USER_ASSOCIATED);
		m_strAssociatedColumns = UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE) + "," +
							 	UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE);
							 	
		m_strAssociatedTableColumns = UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.USER_ID) + "," +
									  UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE) + "," +
									  UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE) + "," +
									  UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_ID);
									  
	}
			
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType     0 : 일반 / 1 : Time Check (valid) / 2: Time Check (Invalid)
	 * @return Substitutes     
	 */
	private Substitutes processData(ResultSet resultSet, int nType)
	{
		Substitutes  	substitutes = null;
		boolean		bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "SubstituteHandler.processData",
								   "");
			
			return null;
		}
		
		substitutes = new Substitutes();
		
		try
		{
			while(resultSet.next())
			{
				// Time Check 유효한 대결자 정보
				if (nType == SUBSTITUTE_VALID)
				{
					java.util.Date currentDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");	
					java.util.Date startDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date endDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
													
					if (currentDate.after(endDate))
					{
						continue;
					}		
				}
				else if (nType == SUBSTITUTE_INVALID) // Time Check 만료된 대결자 정보 
				{
					java.util.Date currentDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");	
					java.util.Date startDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date endDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
													
					if (currentDate.before(endDate))
					{
						continue;
					}							
				}
				else if (nType == SUBSTITUTE_CURRENT) // Current Substitute 정보 가져오기
				{
					java.util.Date currentDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");	
					java.util.Date startDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date endDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
					boolean 	   bAttached = false;
			
					if (startDate.before(currentDate) && 
					    endDate.after(currentDate))
					{
						bAttached = true;
					}	
					
					if (!bAttached)
					{
						continue;
					}	
				}
					
				Substitute 		substitute = new Substitute();
													
				// set Substitute information(UserBasic)
				substitute.setUserID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_ID)));
				substitute.setUserName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME)));
				substitute.setUserOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME)));
				substitute.setUserUID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_UID)));
				substitute.setGroupID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_ID)));
				substitute.setGroupName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_NAME)));
				substitute.setGroupOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GROUP_OTHER_NAME)));
				substitute.setCompID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID)));
				substitute.setCompName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME)));
				substitute.setCompOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME)));
				substitute.setDeptID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID)));
				substitute.setDeptName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME)));
				substitute.setDeptOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME)));
				substitute.setPartID(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_ID)));
				substitute.setPartName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_NAME)));
				substitute.setPartOtherName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.PART_OTHER_NAME)));
				substitute.setOrgDisplayName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_NAME)));
				substitute.setOrgDisplayOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.ORG_DISPLAY_OTHER_NAME)));
				substitute.setGradeCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_CODE)));
				substitute.setGradeName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME)));
				substitute.setGradeOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME)));
				substitute.setGradeAbbrName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ABBR_NAME)));
				substitute.setGradeOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.GRADE_ORDER)));
				substitute.setTitleCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_CODE)));
				substitute.setTitleName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.TITLE_NAME)));
				substitute.setTitleOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.TITLE_OTHER_NAME)));
				substitute.setTitleOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.TITLE_ORDER)));
				substitute.setPositionCode(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_CODE)));
				substitute.setPositionName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_NAME)));
				substitute.setPositionOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.POSITION_OTHER_NAME)));
				substitute.setPositionAbbrName(getString(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ABBR_NAME)));
				substitute.setPositionOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.POSITION_ORDER)));
				substitute.setUserOrder(getInt(resultSet,UserViewTableMap.getColumnName(UserViewTableMap.USER_ORDER)));
				substitute.setRoleCodes(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE)));
				substitute.setIsConcurrent(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT)));
				substitute.setIsProxy(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY)));
				substitute.setIsDelegate(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE)));
				substitute.setIsExistence(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE)));
				substitute.setUserRID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_RID)));
				substitute.setOptionalGTPName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.OPTIONAL_GTP_NAME)));
				substitute.setIsDefaultUser(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER)));
				substitute.setServers(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.SERVERS)));
				substitute.setReserved1(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED1)));
				substitute.setReserved2(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED2)));
				substitute.setReserved3(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.RESERVED3)));
							
				// set Substitute information(UserStatus)
				substitute.setStartDate(getDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND));
				substitute.setEndDate(getDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND));
										
				substitutes.add(substitute);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make Substitute classList.",
								   "SubstituteHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return substitutes;				
	} 
	
	/**
	 * 주어진 사용자의 대결자 정보
	 * @param strUserUID 사용자 UID
	 * @return Sustitutes
	 */
	public Substitutes getSubstitutes(String strUserUID)
	{
		Substitutes 	substitutes = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   " FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable +".USER_ID = ?" +
		   		   " AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".SUBSTITUTE_ID" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";
		   		   
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;	
		} 
	
		m_connectionBroker.setString(1, strUserUID); 
			   		   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), 0);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		  
		m_connectionBroker.clearConnectionBroker();   
		return substitutes;	
	}
		
	/**
	 * 주어진 사용자의 대결자 정보(Time Check)
	 * @param strUserUID 사용자 UID
	 * @param nType	0: All 
	 * 					1: Time Check (valid) 
	 * 					2: Time Check (Invalid)
	 * 					3: Time Check (Current Substitute)
	 * @return Sustitutes
	 */
	public Substitutes getSubstitutes(String strUserUID, int nType)
	{
		Substitutes 	substitutes = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   " FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable +".USER_ID = ?" +
		   		   " AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".SUBSTITUTE_ID" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";

		bResult = m_connectionBroker.prepareStatement(strQuery);
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;	
		} 
		
		m_connectionBroker.setString(1, strUserUID); 
		   		   		   		   
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), nType);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		  
		m_connectionBroker.clearConnectionBroker();   
		return substitutes;	
	}
			
	/**
	 * 대결자 정보 등록 By UID
	 * @param strUserUID 	사용자 UID
	 * @param substitute   대결자 정보
	 * @return boolean
	 */
	public boolean registerSubstitute(String strUserUID, Substitute substitute)
	{
		ResultSet   resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		boolean    bFound = false;
		String 	 	strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		strQuery = "SELECT " + m_strAssociatedTableColumns +
				   " FROM " + m_strAssociatedTable +
				   " WHERE USER_ID = '" + strUserUID + "'" +
				     " AND SUBSTITUTE_ID = '" + substitute.getUserUID() + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			while(resultSet.next())
			{
				bFound = true;	
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "SubstituteHandler.registerUserStatus.next",
								   e.getMessage());
			
		}
		
		m_connectionBroker.clearQuery();
		if (bFound == false)
		{
			// insert
			bReturn = insertSubstitute(strUserUID, substitute);
		}	
		else
		{
			// update
			bReturn = updateSubstitute(strUserUID, substitute);	
		}
		
		m_connectionBroker.clearConnectionBroker();		   
				
		return bReturn;		
	}
		
	/**
	 * 주어진 사용자의 위임자 정보 By UID
	 * @param strUserUID 사용자 UID
	 * @return Sustitutes
	 */
	public Substitutes getMandators(String strUserUID)
	{
		Substitutes 	substitutes = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
   		   	   	 							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   " FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable +".SUBSTITUTE_ID = '" + strUserUID + "'" +
		   		   " AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".USER_ID";
		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), 0);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		  
		m_connectionBroker.clearConnectionBroker();   
		return substitutes;	
	}
		
	/**
	 * 주어진 사용자의 위임자 정보 By UID
	 * @param strUserUID 사용자 ID
	 * @param nType 	 0: All 
	 * 					 1: Time Check(valid) 	
	 * 					 2: Time Check (Invalid)
	 * 					 3: Time Check (Current)
	 * @return Sustitutes
	 */
	public Substitutes getMandators(String strUserUID, int nType)
	{
		Substitutes 	substitutes = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
   		   	   	 							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   " FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable +".SUBSTITUTE_ID = '" + strUserUID + "'" +
		   		   " AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".USER_ID";
		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), nType);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		  
		m_connectionBroker.clearConnectionBroker();   
		return substitutes;	
	}
			
	/**
	 * 대결자 정보 등록(insert) by UserUID
	 * @param strUserUID	사용자 ID
	 * @param substitute	대결자 정보  
	 * @return boolean
	 */
	private boolean insertSubstitute(String strUserUID, Substitute substitute)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int 		nResult = -1;

		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
	
			strQuery = "INSERT INTO " + m_strAssociatedTable + 
								" (" + m_strAssociatedTableColumns + ")" +
				   	   " VALUES ('" + strUserUID + "'," +
				   	   				  getDateFormat(substitute.getStartDate()) + "," +
				   	   				  getDateFormat(substitute.getEndDate()) + "," +
				   	   			"'" + substitute.getUserUID() + "')"; 
		 		
			nResult = m_connectionBroker.executeUpdate(strQuery);
			if(nResult == -1)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();
			m_connectionBroker.commit();	
			bReturn = true;						   
		}
		
		return bReturn;
	}
		
	/**
	 * 대결자 정보 변경(update) by UID
	 * @param strUserUID	사용자 UID
 	 * @param substitute	대결자 정보 
	 * @return boolean
	 */
	private boolean updateSubstitute(String strUserUID, Substitute substitute)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			// Transaction 관리
			m_connectionBroker.setAutoCommit(false);
			
			strQuery = "UPDATE " + m_strAssociatedTable + 
				   	   " SET SUBSTITUTE_START_DATE = " + getDateFormat(substitute.getStartDate()) +"," +
			   	   	    	"SUBSTITUTE_END_DATE = " + getDateFormat(substitute.getEndDate()) + 
			   	       " WHERE USER_ID = '" + strUserUID + "'" +
			   	       	 " AND SUBSTITUTE_ID = '" + substitute.getUserUID() + "'";
							   	   	   								
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(bResult == false)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.rollback();
				m_connectionBroker.clearQuery();
				return bReturn;
			}
			
			m_connectionBroker.clearQuery();	
			m_connectionBroker.commit();		
			bReturn = true;
		}
	
		return bReturn;		
	}
	
	/**
	 * 대결자 정보 해제 
	 * @param strUserUID    		위임자 ID
	 * @param strSubstituteUID		대결자 ID
	 * @return boolean
	 */
	public boolean deleteSubstitute(String strUserUID, String strSubstituteUID)
	{
		boolean bReturn = false;
		boolean bResult = false;
		String 	 strQuery = "";
		int 	 nResult = -1;
			
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
				

		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		strQuery = "DELETE " + 
			   	   " FROM " + m_strAssociatedTable +
			   	   " WHERE USER_ID = '" + strUserUID + "'" +
			   	   	 " AND SUBSTITUTE_ID = '" + strSubstituteUID + "'";
		
		nResult = m_connectionBroker.executeUpdate(strQuery);
		if(nResult == -1)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.rollback();
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();				
		bReturn = true;
	
		return bReturn;
	}
	
	/**
	 * 대결자의 시간 정보 Check
	 * @param strMandatorUID	위임자 UID
	 * @param strSubstituteUID	대결자 UID
	 * @return boolean
	 */
	public boolean validateSubstitute(String strMandatorUID, String strSubstituteUID)
	{
		Substitutes substitutes = null;
		ResultSet	resultSet = null;
		boolean 	bReturn = false;
		boolean	bResult = false;
		String 		strQuery = "";
		int		nType = 1;
		int		nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;			
		}
		 		   		   	   	 							
		strQuery = "SELECT " + m_strAssociatedTableColumns + 
		   		   " FROM " + m_strAssociatedTable +
		   		   " WHERE USER_ID = '" + strMandatorUID + "'" +
		   		   	 " AND SUBSTITUTE_ID = '" + strSubstituteUID + "'";
		   		   	 		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		
		try
		{
			if (resultSet != null)
			{
				while(resultSet.next())
				{
					java.util.Date currentDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");	
					java.util.Date startDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date endDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
													
					if (currentDate.before(startDate) == true ||
						currentDate.after(endDate) == true)
					{
						continue;
					}	
					
					nCount++;						
				}
			}	
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "SubstituteHandler.validateSubstitute.next",
								   e.getMessage());
			
		}		  
		m_connectionBroker.clearConnectionBroker();   		
		
		if (nCount > 0)
			bReturn = true;
			
		return bReturn;
	}
	
	/**
	 * 대결자의 시간 정보 Check
	 * @param strMandatorUID	위임자 UID
	 * @param strLoginUID		로그인한 사람 UID
	 * @return boolean
	 */
	public boolean validateSubstituteByLoginID(String strMandatorUID, String strLoginUID)
	{
		Substitutes substitutes = null;
		ResultSet	resultSet = null;
		boolean 	bReturn = false;
		boolean		bResult = false;
		String 		strQuery = "";
		int			nType = 1;
		int			nCount = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;			
		}
		
		// 현재 유효한 대결자 정보 추출 		   		   	   	 							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   "  FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable + ".USER_ID = '" + strMandatorUID + "'" +
		   		   "   AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".SUBSTITUTE_ID" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";
		   		   	 		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), SUBSTITUTE_CURRENT);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		// 대결자 정보에서 원 사용자 정보 추출 
		for (int i = 0 ; i < substitutes.size() ; i++)
		{
			String strSubstituteUID = substitutes.getUserUID(i);
			
			if (strLoginUID.compareTo(strSubstituteUID) == 0)
			{
				bReturn = true;
			}
			else
			{
				strQuery = "SELECT USER_RID " +
						   " FROM TCN_USERINFORMATION_BASIC " +
						   " WHERE USER_UID = '" + strSubstituteUID + "'";
						   
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearConnectionBroker();
					return bReturn;
				}
				
				resultSet = m_connectionBroker.getResultSet();		
				try
				{
					if (resultSet != null)
					{
						while(resultSet.next())
						{
							String strUserRID = getString(resultSet, "USER_RID");			
							if (strLoginUID.compareTo(strUserRID) == 0)
							{
								bReturn = true;
							}
						}
					}	
				}
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get next recordset",
										   "SubstituteHandler.validateSubstituteByLoginID.next",
										   e.getMessage());
					
				}
				finally
				{
					m_connectionBroker.clearQuery();	
				}			
			}
		}
		
		m_connectionBroker.clearConnectionBroker();   
			
		return bReturn;
	}
	
	/**
	 * 만료된 위임자와 결재자 정보 삭제 
	 * @param strUserUID	사용자 UID
	 * @return boolean
	 */
	public boolean deleteExpiredSubstitues(String strUserUID)
	{
		Substitutes substitutes = null;
		Substitutes mandators = null;
		boolean 	bReturn = false;
		boolean    bResult = false;
		String 		strQuery = "";
		int 		nType = 2;
		int 		nResult = -1;
		
		substitutes = getSubstitutes(strUserUID, nType);  // expired substitutes
		mandators = getMandators(strUserUID, nType);		 // expired mandators
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return bReturn;
		}
		
		// Transaction 관리
		m_connectionBroker.setAutoCommit(false);
		
		// Delete expired substitutes	
		if (substitutes != null)
		{
			for (int i = 0 ; i < substitutes.size() ; i++)
			{
				Substitute substitutue = substitutes.get(i);
				
				strQuery = "DELETE " + 
				   	   		" FROM " + m_strAssociatedTable +
				   	   		" WHERE USER_ID = '" + strUserUID + "'" +
				   	   	 	" AND SUBSTITUTE_ID = '" + substitutue.getUserUID() + "'";
				   	   	 	
				   	   	 	
				nResult = m_connectionBroker.executeUpdate(strQuery);
				if(nResult == -1)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearConnectionBroker();	
					return bReturn;
				}									
			}
		}
		
		// Delete expired mandator
		if (mandators != null)
		{
			for (int i = 0 ; i < mandators.size(); i++)
			{
				Substitute mandator = mandators.get(i);
				
				strQuery = "DELETE " + 
				   	   		" FROM " + m_strAssociatedTable +
				   	   		" WHERE USER_ID = '" + mandator.getUserUID() + "'" +
				   	   	 	" AND SUBSTITUTE_ID = '" + strUserUID + "'";	
				   	   	 	
				nResult = m_connectionBroker.executeUpdate(strQuery);
				if (nResult == -1)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.rollback();
					m_connectionBroker.clearConnectionBroker();
				}
			}
		}
			
		m_connectionBroker.commit();
		m_connectionBroker.clearConnectionBroker();				
		bReturn = true;
	
		return bReturn;
	}
	
	/**
	 * 주어진 기간과 겹치는 대결자의 대결기간이 있는지 확인
	 * @param strSubstituteUID 	대결자 UID
	 * @param strStartDate 		대결시작 시간 (yyyy-mm-dd)
	 * @param strEndDate 			대결만료 시간 (yyyy-mm-dd)
	 * @return boolean 
	 */
	public boolean validateSubstitutePeriod(String strSubstituteUID,
											  String strStartDate,
											  String strEndDate)
	{
		SimpleDateFormat 	sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date 		startDate = null;
		java.util.Date 		endDate = null;
		ResultSet			resultSet = null;
		boolean 			bReturn = false;
		boolean 			bResult = false;
		String 	 			strQuery = "";
		int				nCount = 0;
		int 				nValidCount = 0;
		
		try
		{
			// String을 Date형으로 변환 
			startDate = sdf.parse(strStartDate);
			endDate = sdf.parse(strEndDate);
		}
		catch(ParseException e)
		{
			m_lastError.setMessage("Fail to transfer text to date.",
						 		   "SubstituteHandler.validateSubstitutePeriod.DateFormat.parse",
									e.getMessage());
									
			return bReturn;			
		}
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}	
		
		strQuery = "SELECT " + m_strAssociatedTableColumns + 
		   		   " FROM " + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable +".USER_ID = '" + strSubstituteUID + "'" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";
		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return bReturn;
		}
		
		resultSet = m_connectionBroker.getResultSet();
		try
		{
			if (resultSet != null)
			{
				while(resultSet.next())
				{
					java.util.Date subStartDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date subEndDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
													
					if (endDate.before(subStartDate) == true ||
						startDate.after(subEndDate) == true)
					{
						nValidCount++;
					}	
					
					nCount++;						
				}
			}	
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get next recordset",
								   "SubstituteHandler.validateSubstitutePeriod.next",
								   e.getMessage());
			
		}
		
		if (nValidCount == nCount)
			bReturn = true;
				  
		m_connectionBroker.clearConnectionBroker();	
		
		return bReturn;
	}
	
	/**
	 * 주어진 위임자와 대결자를 만족하는 대결정보
	 * @param strMandatorUID 위임자 UID
	 * @param strSubstituteUID 대결자 UID
	 * @param nType 	 0: All 
	 * 					 1: Time Check(valid) 	
	 * 					 2: Time Check (Invalid)
	 * 					 3: Time Check (Current)
	 * @return Sustitute
	 */
	public Substitute getSubstitute(String strMandatorUID, 
									String strSubstituteUID,
									int nType)
	{
		Substitutes 	substitutes = null;
		Substitute		substitute = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int				nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   "  FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable + ".USER_ID = '" + strMandatorUID + "'" +
		   		   "   AND " + m_strAssociatedTable + ".SUBSTITUTE_ID = '" + strSubstituteUID + "'" +
		   		   "   AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".SUBSTITUTE_ID" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";
		   		   		   		   		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), nType);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = substitutes.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get correct substitute Information infomation.", 
								   "SubstituteHandler.getSubstitute.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		 
		substitute = substitutes.get(0);
		
		m_connectionBroker.clearConnectionBroker();   
		return substitute;	
	}
	
	/**
	 * 주어진 위임자의 현재 유효한 대결자 정보
	 * @param strMandatorUID 위임자 UID
	 * @return Sustitute
	 */
	public Substitute getValidSubstitute(String strMandatorUID)
	{
		Substitutes 	substitutes = null;
		Substitute		substitute = null;
		boolean 		bResult = false;
		String 			strQuery = "";
		int				nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
							
		strQuery = "SELECT " + m_strEmployeeColumns + "," + m_strAssociatedColumns +
		   		   "  FROM " + m_strEmployeeTable + "," + m_strAssociatedTable +
		   		   " WHERE " + m_strAssociatedTable + ".USER_ID = '" + strMandatorUID + "'" +
		   		   "   AND " + m_strEmployeeTable + ".USER_UID = " + m_strAssociatedTable + ".SUBSTITUTE_ID" +
		   		   " ORDER BY SUBSTITUTE_START_DATE";
		   		  		   		   		   		   		   		   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		substitutes = processData(m_connectionBroker.getResultSet(), SUBSTITUTE_CURRENT);
		if (substitutes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();
			return null;
		}
		
		nSize = substitutes.size();
		if (nSize != 1)
		{
			m_lastError.setMessage("Fail to get valid substitute Information infomation.", 
								   "SubstituteHandler.getValidSubstitute.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();
			return null;			
		}
		 
		substitute = substitutes.get(0);
		
		m_connectionBroker.clearConnectionBroker();   
		return substitute;	
	}
	

}
