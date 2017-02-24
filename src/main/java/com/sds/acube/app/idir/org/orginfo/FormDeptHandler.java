package com.sds.acube.app.idir.org.orginfo;

/**
 * FormDeptHandler.java
 * 2002-10-11
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

public class FormDeptHandler extends DataHandler
{
	private String m_strFormDeptColumns = "";
	private String m_strFormDeptTable = "";
	
	public FormDeptHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		m_strFormDeptTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
		m_strFormDeptColumns = OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
		                     OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME) + "," +
							 OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
							 OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID) + "," +
							 OrgTableMap.getColumnName(OrgTableMap.FORMBOX_INFO) ;							 
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return FormDepts
	 */
	private FormDepts processData(ResultSet resultSet)
	{
		FormDepts  	formDepts = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "FormDeptHandler.processData",
								   "");
			
			return null;
		}
		
		formDepts = new FormDepts();
		
		try
		{
			while(resultSet.next())
			{
				FormDept formDept = new FormDept();
									
				// set 양식 사용 Department information
		
				formDept.setOrgID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_ID)));
				formDept.setOrgName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_NAME)));
				formDept.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME)));
				formDept.setOrgParentID(getString(resultSet, OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID)));
				formDept.setFormBoxInfo(getBoolean(resultSet, OrgTableMap.getColumnName(OrgTableMap.FORMBOX_INFO)));
							
				formDepts.add(formDept);
			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to process department list.",
								   "FormDeptHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return formDepts;				
	} 
	
	/**
	 * 양식 사용 부서 리스트
	 * @param String strUserUID
	 * @return FormDepts formDepts
	 */
	public FormDepts getFormDeptsTree(String strUserUID)
	{
		FormDepts				formDepts = null;
		FormDepts				subFormDepts = null;
		FormDept				formDept = null;
		ResultSet				resultSet = null;
		String 					strQuery = "";
		String 					strDeptID = "";
		String 					strData = "";
		boolean				bResult = false;
		
		bResult = m_connectionBroker.openConnection();
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// get Dept ID
		strQuery = "SELECT DEPT_ID " +
				   " FROM TCN_USERINFORMATION_BASIC" +
				   " WHERE USER_UID='"+ strUserUID + "'";
		
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		resultSet = m_connectionBroker.getResultSet();	
		try
		{
			while(resultSet.next())
			{
				strData = resultSet.getString("DEPT_ID");
				if (strData != null)
					strDeptID = strData;
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to get user dept ID.",
						 			"FormDeptHandler.getUserDeptID ("+ strUserUID +")",
									 e.getMessage());
			
		}	
		
		m_connectionBroker.clearQuery();
		
		formDepts = new FormDepts();
		
		while(strDeptID.compareTo("ROOT") != 0)
		{
			strQuery = "SELECT " + m_strFormDeptColumns	+
					   " FROM " + m_strFormDeptTable +
					   " WHERE ORG_ID = '" +  strDeptID + "'";
 					   
			bResult = m_connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(m_connectionBroker.getLastError());
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
				
			subFormDepts = processData(m_connectionBroker.getResultSet());
			strDeptID = "";
			
			if ((subFormDepts != null) && (subFormDepts.size() == 1))
			{
				formDept = subFormDepts.get(0);
				strDeptID = formDept.getOrgParentID();
				
				if (formDept.getFormBoxInfo())
					formDepts.addFirst(formDept);			
			}
										
			if (strDeptID.length() == 0)
			{
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
														
			m_connectionBroker.clearQuery();			
		}	
						
		m_connectionBroker.clearConnectionBroker();
		
		// 항상 자기 부서가 Default로 펼쳐지도록 셋팅  
		if (formDepts != null && formDepts.size() > 0)
		{
			formDepts.get(formDepts.size()-1).setIsSelected(1);
		}
				
 		return formDepts;		
	}
}
