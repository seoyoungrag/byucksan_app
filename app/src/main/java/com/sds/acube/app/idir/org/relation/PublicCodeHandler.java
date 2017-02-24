package com.sds.acube.app.idir.org.relation;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;

/**
 * PublicCodeHandler.java
 * 2003-08-14
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class PublicCodeHandler extends DataHandler
{
	private String m_strPublicCodeColumns = "";
	private String m_strPublicCodeTable = "";
	
	public PublicCodeHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strPublicCodeTable = "TCN_PUBLIC_ORGAN_CODE";
		m_strPublicCodeColumns = 	"ORGAN_CODE, " +
									"ORGAN_FULL_NAME, " +   
									"LAST_ORGAN_NAME, " +   
									"ORGAN_DEGREE, " +   
									"ORGAN_ORDER, " +   
									"MY_ORGAN_DEGREE, " +   
									"PARENT_ORGAN_CODE, " +
									"TOP_ORGAN_CODE, " +
									"REPRESENTATION_ORGAN_CODE, " +
									"ORGAN_TYPE1, " +
									"ORGAN_TYPE2, " +
									"ORGAN_TYPE3, " +
									"ZIPCODE, " +
									"ADMINISTRATION_LOC_CODE, " +
									"LOCATION_CODE, " +
									"LOT_NUMBER, " +
									"TELEPHONE, " +
									"FAX, " +
									"CREATE_DATE, " +
									"DISUSE_DATE, " +
									"IS_EXISTENCE, " +
									"IS_OPRDPT ";
	}
	
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return PublicCodes
	 */
	private PublicCodes processData(ResultSet resultSet)
	{
		PublicCodes  	publicCodes = null;
		boolean			bResult = false;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "PublicCodeHandler.processData",
								   "");
			
			return null;
		}
		
		publicCodes = new PublicCodes();
		
		try
		{
			while(resultSet.next())
			{
				PublicCode publicCode = new PublicCode();
									
				// set Public Code information
				publicCode.setOrganCode(getString(resultSet, "ORGAN_CODE"));
				publicCode.setOrganFullName(getString(resultSet,"ORGAN_FULL_NAME"));
				publicCode.setLastOrganName(getString(resultSet,"LAST_ORGAN_NAME"));
				publicCode.setOrganDegree(getInt(resultSet, "ORGAN_DEGREE"));
				publicCode.setOrganOrder(getInt(resultSet, "ORGAN_ORDER"));
				publicCode.setMyOrganDegree(getInt(resultSet, "MY_ORGAN_DEGREE"));
				publicCode.setParentOrganCode(getString(resultSet, "PARENT_ORGAN_CODE"));
				publicCode.setTopOrganCode(getString(resultSet, "TOP_ORGAN_CODE"));
				publicCode.setReprentationOrganCode(getString(resultSet, "REPRESENTATION_ORGAN_CODE"));
				publicCode.setOrganType1(getInt(resultSet, "ORGAN_TYPE1"));
				publicCode.setOrganType2(getInt(resultSet, "ORGAN_TYPE2"));
				publicCode.setOrganType3(getInt(resultSet, "ORGAN_TYPE3"));
				publicCode.setZipCode(getString(resultSet, "ZIPCODE"));
				publicCode.setAdministrationLogCode(getString(resultSet, "ADMINISTRATION_LOC_CODE"));
				publicCode.setLocationCode(getString(resultSet, "LOCATION_CODE"));
				publicCode.setLotNumber(getString(resultSet, "LOT_NUMBER"));
				publicCode.setTelePhone(getString(resultSet, "TELEPHONE"));
				publicCode.setFax(getString(resultSet,"FAX"));
				publicCode.setCreateDate(getString(resultSet, "CREATE_DATE"));
				publicCode.setDisuseDate(getString(resultSet, "DISUSE_DATE"));
				publicCode.setIsExistence(getString(resultSet, "IS_EXISTENCE"));
				publicCode.setIsOPRDPT(getString(resultSet, "IS_OPRDPT"));
				
			//	System.out.println("========================================");
																				
				publicCodes.add(publicCode);

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make publicCodes.",
								   "PublicCodeHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return publicCodes;				
	}
	
	/**
	 * 주어진 Organ Code 하위의 기관 코드 
	 * @param strOrganCode 조직 코드 
	 * @return PublicCodes
	 */
	public PublicCodes getSubPublicCodes(String strOrganCode)	
	{
		PublicCodes 		publicCodes = null;
		boolean  			bResult = false;
		String	  			strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strPublicCodeColumns +
				   " FROM " + m_strPublicCodeTable +
				   " WHERE PARENT_ORGAN_CODE=" + "'"+ strOrganCode + "'" +
				   " ORDER BY ORGAN_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		publicCodes = processData(m_connectionBroker.getResultSet());
		if (publicCodes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return publicCodes;
	}
	
	/**
	 * 기관 코드 테이블에서 Root 코드들을 가져오는 함수
	 * @return PublicCodes
	 */
	public PublicCodes getRootPublicCodes()	
	{
		PublicCodes 		publicCodes = null;
		boolean  			bResult = false;
		String	  			strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strPublicCodeColumns +
				   " FROM " + m_strPublicCodeTable +
				   " WHERE ( PARENT_ORGAN_CODE='0000000' OR PARENT_ORGAN_CODE='0')" +
				   " ORDER BY ORGAN_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		publicCodes = processData(m_connectionBroker.getResultSet());
		if (publicCodes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return publicCodes;
	}
	
	/**
	 * 주어진 Degree 기관 코드 
	 * @param nDegree 차수 
	 * @return PublicCodes
	 */
	public PublicCodes getPublicCodesByDegree(int nDegree)	
	{
		PublicCodes 		publicCodes = null;
		boolean  			bResult = false;
		String	  			strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		strQuery = "SELECT " + m_strPublicCodeColumns +
				   " FROM " + m_strPublicCodeTable +
				   " WHERE ORGAN_DEGREE=" + Integer.toString(nDegree) +
				   " ORDER BY ORGAN_ORDER";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		publicCodes = processData(m_connectionBroker.getResultSet());
		if (publicCodes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
				  
		m_connectionBroker.clearConnectionBroker();
		return publicCodes;
	}
	
	/**
	 * 주어진 부서 ID로 부터 펼쳐진 트리를 그리는 함수 
	 * @param strDeptID 부서 ID
	 * @return PublicCodes
	 */
	public PublicCodes getPublicCodeTree(String strDeptID)	
	{
		PublicCodes 		publicCodes = null;
		PublicCodes 		childCodes = null;
		PublicCodes			parentCodes = null;
		PublicCodes			resultCodes = null;        
		PublicCode			myPublicCode = null;
		boolean  			bResult = false;
		boolean				bFind = false;
		String	  			strQuery = "";
	
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		// 자기 조직 정보를 가져오는 함수
		strQuery = "SELECT " + m_strPublicCodeColumns +
				   " FROM " + m_strPublicCodeTable +
				   " WHERE ORGAN_CODE='" + strDeptID + "'";
				   				 
		bResult = m_connectionBroker.excuteQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		publicCodes = processData(m_connectionBroker.getResultSet());
		if (publicCodes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		if (publicCodes.size() != 1)
		{
			m_lastError.setMessage("Fail to get unique public code.",
								   "PulbicCodeHandler.getPublicCodeTree.not unique public code",
								   "");	
			m_connectionBroker.clearConnectionBroker();	 
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		myPublicCode = publicCodes.get(0);
		int	nDegree = myPublicCode.getOrganDegree();
		String strParentOrgCode = myPublicCode.getParentOrganCode();
		
		resultCodes=makeTreeList(strParentOrgCode, nDegree);
					  
		m_connectionBroker.clearConnectionBroker();
		return resultCodes;
	}
	
	/**
	 * 사용자가 속한 부서부터 root까지 펼져지는 treeList
	 * @param strParentOrgCode	상위부서 ID
	 * @param nDegree 
	 * @return PublicCodes
	 */
	private PublicCodes makeTreeList(String strParentOrgCode, int nDegree)
	{
		PublicCodes publicCodeTree = null;
		PublicCodes	publicCodes = null;
		PublicCodes parentPublicCodes = null;
		PublicCodes childPublicCodes = null;
		PublicCodes resultPublicCodes = null;
		PublicCodes	rootPublicCodes = null;
		String 		strQuery = "";
		String 		strNextParentOrgCode = "";
		String 		strInsertParentOrgCode = "";
		boolean 	bResult = false;
		int			nDepth = 0;

		if (m_connectionBroker.IsConnectionClosed() != true)
		{		
			while(strParentOrgCode.compareTo("0000000") != 0)
			{
				// make sub tree list
				strQuery = "SELECT " + m_strPublicCodeColumns +
						   " FROM " + m_strPublicCodeTable +
						   " WHERE PARENT_ORGAN_CODE='"+ strParentOrgCode + "'" +
						   " ORDER BY ORGAN_ORDER";
						   
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if(bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return null;
				}
						
				parentPublicCodes = processData(m_connectionBroker.getResultSet());
				if (parentPublicCodes == null || parentPublicCodes.size() == 0)
				{
					m_connectionBroker.clearQuery();
					return null;
				}
				
				setListDepth(parentPublicCodes, nDepth++);
				
				m_connectionBroker.clearQuery();
				
				// inset sub tree list
				resultPublicCodes = insertSubTreeList(parentPublicCodes, strInsertParentOrgCode, childPublicCodes);
				
				// set next step condition			
				strQuery = "SELECT PARENT_ORGAN_CODE , ORGAN_DEGREE " + 
						   " FROM TCN_PUBLIC_ORGAN_CODE" +
						   " WHERE ORGAN_CODE='" + strParentOrgCode + "'" +
						   " ORDER BY ORGAN_ORDER";

				bResult = m_connectionBroker.excuteQuery(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());	
					m_connectionBroker.clearQuery();
					return null;
				}
				
				nDegree = -1;
				strNextParentOrgCode = "";	
				
				try
				{
					ResultSet resultSet = m_connectionBroker.getResultSet();
					while(resultSet.next())
					{
						nDegree = resultSet.getInt("ORGAN_DEGREE");
						strNextParentOrgCode = resultSet.getString("PARENT_ORGAN_CODE");
					}
				}
				catch(SQLException e)
				{
					m_lastError.setMessage("Fail to get user next parent ID.",
								 			"PublicCodeHandler.getString (ORG_PARENT_ID)",
											 e.getMessage());
					
				}	
				
				m_connectionBroker.clearQuery();
				
				childPublicCodes = resultPublicCodes;
				strInsertParentOrgCode  = strParentOrgCode;
				strParentOrgCode = strNextParentOrgCode;	
		
				if (strParentOrgCode == null || strParentOrgCode.length() == 0)
				{
					m_lastError.setMessage("Fail to get parent organization ID.",
					                       "PublicCodeHandler.makeTreeList.Empty parent Org ID",
					                       "");
					return null;
				}			
			}
			
			if (strParentOrgCode.compareTo("0000000") == 0)
			{
				strQuery = "SELECT " + m_strPublicCodeColumns +
							   " FROM " + m_strPublicCodeTable +
							   " WHERE PARENT_ORGAN_CODE='0000000' " +
							   " ORDER BY ORGAN_ORDER";
							   
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());	
					m_connectionBroker.clearQuery();
					return null;
				}
				
				rootPublicCodes=processData(m_connectionBroker.getResultSet());
				if (rootPublicCodes == null)
				{
					m_connectionBroker.clearQuery();
					return null;
				}
				
				setListDepth(rootPublicCodes, nDepth);
									
				resultPublicCodes = insertSubTreeList(rootPublicCodes, strInsertParentOrgCode, childPublicCodes);
								
				publicCodeTree = resultPublicCodes; 
			}
		}
		
		return publicCodeTree;
	}
	
	/**
	 * Child 리스트를 Parent 리스트에 insert
	 * @param parentPublicCodes 
	 * @param strParentOrganCode parent Public Codes 중 insert를 부서 ID
	 * @param childPublicCodes 
	 * @return Departments
	 */	
	private PublicCodes insertSubTreeList(PublicCodes parentPublicCodes, 
										   String strParentOrganCode,
										   PublicCodes childPublicCodes)
	{
		int nListLength = 0;

		if (parentPublicCodes == null)
		{
			m_lastError.setMessage("Null point parent publicCodess.",
								   "PublicCodeHandler.insertSubTreeList",
								   "");
			return null;	
		}
		
		if (childPublicCodes == null)
			return parentPublicCodes;
	
		nListLength = parentPublicCodes.size();
							
		for (int i = 0; i < nListLength ; i++)
		{
			PublicCode publicCode = parentPublicCodes.get(i);	
			
			if (publicCode != null)
			{
				String strOrgID = publicCode.getOrganCode();
					
				if (strOrgID.compareTo(strParentOrganCode) == 0)
				{	
					publicCode.setHasChild("Y");
					if (i + 1 >= nListLength)
					{
						parentPublicCodes.addAll(childPublicCodes);
					}
					else
					{
						parentPublicCodes.addAll(i+1, childPublicCodes);
					}
				}		
			}
		}
		
		return parentPublicCodes;
		
	}
	
	/**
	 * PublicCodes에 Display Depth 설정 
	 * @param publicCodes Public Code List
	 * @param depth Display Depth
	 */
	private void setListDepth(PublicCodes publicCodes, int nDepth)
	{
		if (publicCodes != null)
		{
			int nListLength = publicCodes.size();
			for (int i = 0 ; i < nListLength ; i++)
			{
				PublicCode publicCode = publicCodes.get(i);
				
				if (publicCode != null)
				{
					publicCode.setDepth(nDepth);
				}
			}	
		}
	}
	
	public void printPublicCode(PublicCode publicCode)
	{
		if (publicCode != null)
		{
			System.out.println("[ORGAN_CODE] " + publicCode.getOrganCode());
			System.out.println("[ORGAN_FULL_NAME] " + publicCode.getOrganFullName());  
			System.out.println("[LAST_ORGAN_NAME] " +  publicCode.getLastOrganName()); 
			System.out.println("[ORGAN_DEGREE] " + publicCode.getOrganDegree());  
			System.out.println("[ORGAN_ORDER] " +  publicCode.getOrganOrder()); 
			System.out.println("[MY_ORGAN_DEGREE] " +  publicCode.getMyOrganDegree()); 
			System.out.println("[PARENT_ORGAN_CODE] " + publicCode.getParentOrganCode());
			System.out.println("[TOP_ORGAN_CODE] " + publicCode.getTopOrganCode());
			System.out.println("[REPRESENTATION_ORGAN_CODE] " + publicCode.getReprentationOrganCode());
			System.out.println("[ORGAN_TYPE1] " + publicCode.getOrganType1());
			System.out.println("[ORGAN_TYPE2] " + publicCode.getOrganType2());
			System.out.println("[ORGAN_TYPE3] " + publicCode.getOrganType3());
			System.out.println("[ZIPCODE] " + publicCode.getZipCode());
			System.out.println("[ADMINISTRATION_LOC_CODE] " + publicCode.getAdministrationLogCode());
			System.out.println("[LOCATION_CODE] " + publicCode.getLocationCode());
			System.out.println("[LOT_NUMBER] " + publicCode.getLotNumber());
			System.out.println("[TELEPHONE] " + publicCode.getTelePhone());
			System.out.println("[FAX] " + publicCode.getFax());
			System.out.println("[CREATE_DATE] " + publicCode.getCreateDate());
			System.out.println("[DISUSE_DATE] " + publicCode.getDisuseDate());
			System.out.println("[IS_EXISTENCE] " + publicCode.getIsExistence());
			System.out.println("[IS_OPRDPT] " + publicCode.getIsOPRDPT());
		}
	}
}
