package com.sds.acube.app.idir.org.hierarchy;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * GradeHandler.java
 * 2002-11-04
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class GradeHandler extends DataHandler
{
	private String m_strGradeColumns = "";
	private String m_strGradeTable = "";	
	private String m_strRootID = "ROOT";
	
	public GradeHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strGradeTable = TableDefinition.getTableName(TableDefinition.GRADE);
		m_strGradeColumns = GradeTableMap.getColumnName(GradeTableMap.GRADE_ID) +","+
							GradeTableMap.getColumnName(GradeTableMap.GRADE_NAME) +","+
							GradeTableMap.getColumnName(GradeTableMap.GRADE_OTHER_NAME) +","+
							GradeTableMap.getColumnName(GradeTableMap.GRADE_PARENT_ID) +","+
							GradeTableMap.getColumnName(GradeTableMap.COMP_ID) +","+
							GradeTableMap.getColumnName(GradeTableMap.GRADE_ABBR_NAME) +","+
							GradeTableMap.getColumnName(GradeTableMap.GRADE_ORDER) +","+
							GradeTableMap.getColumnName(GradeTableMap.DESCRIPTION);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Grades
	 */
	private Grades processData(ResultSet resultSet)
	{
		Grades  	grades = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "GradeHandler.processData",
								   "");
			
			return null;
		}
		
		grades = new Grades();
		
		try
		{
			while(resultSet.next())
			{
				Grade grade = new Grade();
									
				// set Grade information
				grade.setGradeID(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_ID)));
				grade.setGradeName(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_NAME)));
				grade.setGradeOtherName(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_OTHER_NAME)));
				grade.setGradeParentID(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_PARENT_ID)));
				grade.setCompID(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.COMP_ID)));
				grade.setGradeAbbrName(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_ABBR_NAME)));
				grade.setGradeOrder(getInt(resultSet, GradeTableMap.getColumnName(GradeTableMap.GRADE_ORDER)));
				grade.setDescription(getString(resultSet, GradeTableMap.getColumnName(GradeTableMap.DESCRIPTION)));
												
				grades.add(grade);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make grades.",
								   "GradeHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return grades;				
	}
	
	/**
	 * 선택한  Grade ID를 가지는 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @return Grade
	 */	
	public Grade getGrade(String strGradeID)
	{
		Grades 			grades = null;
		Grade  			grade = null;
		boolean	    bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_connectionBroker.clearConnectionBroker();
			m_lastError.setMessage(m_connectionBroker.getLastError());
			return null;
		}
		
		strQuery = "SELECT " + m_strGradeColumns +
				   " FROM " + m_strGradeTable +
				   " WHERE GRADE_ID = '" + strGradeID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		grades = processData(m_connectionBroker.getResultSet());
		if (grades == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = grades.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct grade information.", 
								   "GradeHandler.getGrade.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		grade = grades.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return grade;
	} 
	
	/**
	 * 선택한  Grade ID를 가지는 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @param connectionBroker Database 연결 정보 
	 * @return Grade
	 */	
	public Grade getGrade(String strGradeID,ConnectionBroker connectionBroker)
	{
		Grades 			grades = null;
		Grade  			grade = null;
		boolean	    bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		if (!connectionBroker.IsConnectionClosed())
		{			
			strQuery = "SELECT " + m_strGradeColumns +
					   " FROM " + m_strGradeTable +
					   " WHERE GRADE_ID = '" + strGradeID + "'";
					   				   
			bResult = connectionBroker.excuteQuery(strQuery);
			if(!bResult)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	 
				return null;
			}
				
			grades = processData(connectionBroker.getResultSet());
			if (grades == null)
			{
				m_lastError.setMessage(connectionBroker.getLastError());
				connectionBroker.clearQuery();	  
				return null;
			}
			
			nSize = grades.size();
			if (nSize != 1)
			{			
				m_lastError.setMessage("Fail to get correct grade information.", 
									   "GradeHandler.getGrade.LinkedList.size(not unique)", 
									   "");
				connectionBroker.clearQuery();	 
				return null;
			}
			
			grade = grades.get(0);
			
			connectionBroker.clearQuery();	 
		}
		
		return grade;
	} 
	
	/**
	 * 선택한  gradeID의 하위 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @return Grades
	 */
	public Grades getSubGrades(String strGradeID)	
	{
		Grades 		grades = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGradeColumns +
				   " FROM " + m_strGradeTable +
				   " WHERE GRADE_PARENT_ID= ?" +
				   " ORDER BY GRADE_ORDER";
		
		bResult = m_connectionBroker.prepareStatement(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.setString(1, strGradeID);
				   				 
		bResult = m_connectionBroker.executePreparedQuery();
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		grades = processData(m_connectionBroker.getResultSet());
		if (grades == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return grades;
	}
	
	/**
	 * 선택한  gradeID의 하위 직급 정보 얻음
	 * @param strGradeID 직급 ID
	 * @param strCompID 회사 ID
	 * @return Grades
	 */
	public Grades getSubGrades(String strGradeID, String strCompID)	
	{
		Grades 		grades = null;
		boolean  	bResult = false;
		String	  	strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strGradeColumns +
				   " FROM " + m_strGradeTable +
				   " WHERE GRADE_PARENT_ID=" + "'"+ strGradeID + "'" +
				   "   AND COMP_ID = '" + strCompID + "'" +
				   " ORDER BY GRADE_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		grades = processData(m_connectionBroker.getResultSet());
		if (grades == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return grades;
	}
	
	/**
	 * 최상위 직급 정보 얻음
	 * @return Grades
	 */
	public Grades getRootGrades()	
	{
		return getSubGrades(m_strRootID);		
	}
}
