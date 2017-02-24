package com.sds.acube.app.idir.org.hierarchy;

/**
 * ClassificationHandler.java
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

public class ClassificationHandler extends DataHandler
{
	private String m_strClassificationColumns = "";
	private String m_strClassificationTable = "";
	private String m_strFavoriteClassificationTable = "";
	
	public ClassificationHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strClassificationTable = TableDefinition.getTableName(TableDefinition.CLASSIFICATION);
		m_strClassificationColumns = ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_ID) +","+
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_NAME) +","+
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.RETENTION_DATE) +","+
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_PARENT_ID) +","+
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.COMP_ID) +","+
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.DESCRIPTION) + "," +
							   		ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_CODE);
							   
		m_strFavoriteClassificationTable = TableDefinition.getTableName(TableDefinition.FAVORITE_CLASSIFICATION);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType 	0 : 일반적인 분류 체계 정보 / 1 : 자주 쓰는 분류 체계 정보
	 * @return Classifications
	 */
	private Classifications processData(ResultSet resultSet, int nType)
	{
		Classifications  classifications = null;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "ClassificationHandler.processData",
								   "");
			
			return null;
		}
		
		classifications = new Classifications();
		
		try
		{
			while(resultSet.next())
			{
				Classification classification = new Classification();
									
				// set Classification information
				classification.setClassificationID(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_ID)));
				classification.setClassificationName(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_NAME)));
				classification.setRetentionDate(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.RETENTION_DATE)));
				classification.setClassificationParentID(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_PARENT_ID)));
				classification.setCompID(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.COMP_ID)));
				classification.setDescription(getString(resultSet,ClassificationTableMap.getColumnName(ClassificationTableMap.DESCRIPTION)));
				classification.setClassificationCode(getString(resultSet, ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_CODE)));
				
				if (nType == 1) 	// 자주쓰는 분류 체계의 경우
				{
					boolean bHasChild = getBoolean(resultSet, FavoriteClassificationTableMap.getColumnName(FavoriteClassificationTableMap.HAS_SUB_CLASSIFICATION));
					if (bHasChild)
						classification.setHasChild("Y");
					else
						classification.setHasChild("N");	
				}	
									
				classifications.add(classification);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make classifications.",
								   "ClassificationHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return classifications;				
	} 
	
	/**
	 * 선택한  classificationID를 가지는 분류 체계 정보 얻음
	 * @param strClassificationID 분류 체계 ID
	 * @return Classification
	 */	
	public Classification getClassification(String strClassificationID)
	{
		Classifications clssifications = null;
		Classification  classification = null;
		boolean	    bResult = false;
		String		    strQuery = "";
		int 		    nSize = 0;
		
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strClassificationColumns +
				   " FROM " + m_strClassificationTable +
				   " WHERE CLASSIFICATION_ID = '" + strClassificationID + "'";
				   				   
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
			
		clssifications = processData(m_connectionBroker.getResultSet(), 0);
		if (clssifications == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		nSize = clssifications.size();
		if (nSize != 1)
		{			
			m_lastError.setMessage("Fail to get correct classification information.", 
								   "ClassificationHandler.getClassification.LinkedList.size(not unique)", 
								   "");
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		classification = clssifications.get(0);
		
		m_connectionBroker.clearConnectionBroker();	 
		
		return classification;
	}
	
	/**
	 * 선택한  classificationID의 하위 분류 체계 정보 얻음
	 * @param strClassificationID 분류 체계 ID
	 * @return Classifications
	 */
	public Classifications getSubClassifications(String strClassificationID)	
	{
		Classifications classifications = null;
		boolean  		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strClassificationColumns +
				   " FROM " + m_strClassificationTable +
				   " WHERE CLASSIFICATION_PARENT_ID=" + "'"+ strClassificationID + "'" +
				   " ORDER BY CLASSIFICATION_ID";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		classifications = processData(m_connectionBroker.getResultSet(), 0);
		if (classifications == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return classifications;
	}
	
	/**
	 * 최상위 분류 체계 정보 얻음
	 * @return Classifications
	 */
	public Classifications getRootClassifications()	
	{
		return getSubClassifications("ROOT");		
	}
	
	/**
	 * 트리에 하위 분류체계 append 
	 * @param classifications 분류 체계 리스트 
	 * 		   strParentClassificationID 상위 분류 체계 ID
	 * 		   nCurrentDepth 현재 분류체계 tree Depth
	 * 		   nDepth 최종적으로 구현해야 하는 tree depth
	 * @return Classifications
	 */
	private Classifications appendSubClassifications(Classifications parentClassifications,
														 String strParentClassificationID,
														 int nCurrentDepth,
														 int nDepth)
	{
		Classifications childClassifications = null;
		
		if (nCurrentDepth > nDepth)
			return parentClassifications;
		else
		{
			
			childClassifications = getSubClassifications(strParentClassificationID);
			
			if (childClassifications != null)
			{
				if (parentClassifications == null)
					parentClassifications = new Classifications();
					
				for (int i = 0 ; i < childClassifications.size() ; i++)
				{
					Classification classification = (Classification)childClassifications.get(i);
					
					classification.setDepth(nCurrentDepth);
					if ((i == 0) && (parentClassifications.size() > 0))
					{
						Classification lastParent = (Classification)parentClassifications.getLast();
						
						if (lastParent != null)
							lastParent.setHasChild("Y");
					}
								
					parentClassifications.add(classification);
					
					// next child list
					parentClassifications = appendSubClassifications(parentClassifications,
															 		classification.getClassificationID(),
															 		nCurrentDepth + 1,
															 		nDepth);			
				}
			}
			
			return parentClassifications;	
		}
	}
	
	/**
	 * 주어진 depth만큼 분류 체계 리스트 생성 
	 * @param nDepth Tree Depth
	 * @return Classifications
	 */
	public Classifications getClassificationsTree(int nDepth)
	{
		Classifications classifications = null;
		
		classifications = appendSubClassifications(null,
												  "ROOT",
												   0,
												   nDepth);
															
		return classifications;
	}	
	
	/**
	 * 부서별로 자주 사용하는 분류 체계를 설정해 놓은 정보 
	 * @param strDeptID 부서 ID
	 * @return Classifications
	 */
	public Classifications getFavoriteClassifications(String strOrgID)	
	{
		Classifications classifications = null;
		boolean  		bResult = false;
		String	  		strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strClassificationTable + "." + ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_ID) +","+
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_NAME) +","+
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.RETENTION_DATE) +","+
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_PARENT_ID) +","+
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.COMP_ID) +","+
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.DESCRIPTION) + "," +
							 	ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_CODE) + "," +
							    FavoriteClassificationTableMap.getColumnName(FavoriteClassificationTableMap.HAS_SUB_CLASSIFICATION) +
				   " FROM " + m_strClassificationTable + ","  + m_strFavoriteClassificationTable +
				   " WHERE " + m_strFavoriteClassificationTable + ".ORG_ID=" + "'"+ strOrgID + "'" +
				   "   AND " + m_strFavoriteClassificationTable + ".CLASSIFICATION_ID=" + m_strClassificationTable + ".CLASSIFICATION_ID" +
				   " ORDER BY DISPLAY_ORDER";
				   				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		classifications = processData(m_connectionBroker.getResultSet(), 1);
		if (classifications == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return classifications;
	}	 	
}
