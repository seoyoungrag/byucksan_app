package com.sds.acube.app.idir.org.option;

/**
 * MemberHandler.java
 * 2002-11-05
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

public class MemberHandler extends DataHandler
{
	private String m_strMemberColumns = "";
	private String m_strMemberTable = "";
	
	public MemberHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strMemberTable = TableDefinition.getTableName(TableDefinition.GROUP_COMMONMEMBER);
		m_strMemberColumns = MemberTableMap.getColumnName(MemberTableMap.GROUP_ID) + "," +
							 MemberTableMap.getColumnName(MemberTableMap.MEMBER_ID) + "," +
							 MemberTableMap.getColumnName(MemberTableMap.MEMBER_TYPE);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Members
	 */
	private Members processData(ResultSet resultSet)
	{
		Members  	members = null;
		boolean	bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "MemberHandler.processData",
								   "");
			
			return null;
		}
		
		members = new Members();
		
		try
		{
			while(resultSet.next())
			{
				Member member = new Member();
									
				// set Member information
				member.setGroupID(getString(resultSet, MemberTableMap.getColumnName(MemberTableMap.GROUP_ID)));
				member.setMemberID(getString(resultSet, MemberTableMap.getColumnName(MemberTableMap.MEMBER_ID)));
				member.setMemberType(getInt(resultSet, MemberTableMap.getColumnName(MemberTableMap.MEMBER_TYPE)));
					
				members.add(member);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make member classList.",
								   "MemberHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return members;				
	} 
}
