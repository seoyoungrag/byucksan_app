package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.hierarchy.*;
import com.sds.acube.app.idir.org.relation.*;
import com.sds.acube.app.idir.org.option.*;
import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.org.orginfo.*;
import com.sds.acube.app.idir.common.GUID;
import java.text.SimpleDateFormat;
import com.sds.acube.app.idir.org.db.*;
import java.sql.*;
import java.util.*;

/**
 * UserBoxHandler.java 2002-10-14
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserBoxHandler extends DataHandler
{
	// Ref Type
	private final static int MINE = 0;					// 자기 결재함
	private final static int CONCURRENT = 1;			// 겸직 결재함 
	private final static int PROXY = 2;				// 직무 대리 결재함 
	private final static int DELEGATE = 3;				// 파견 결재함
	private final static int SUBSTITUTE = 4;			// 대결 결재함
	
	// Role Type
	private final static int COMP_ORG_ADMIN = 0;     	      // 회사 조직 관리자
	private final static int CIVIL_CHARGER = 1;		      // 민원 사무 담당
	private final static int CIVIL_MOVER1 = 2;			      // 대비실 이첩 민원사무 심사관
	private final static int CIVIL_MOVER2 = 3;			      // 감사원 이첩 민원사무 심사관
	private final static int CIVIL_MOVER3 = 4;			      // 다수인 이첩 민원사무 심사관
	private final static int BULLETIN_MANAGER = 5;		      // 공지사항 관리자
	private final static int JUDGE = 6;					  // 심사자
	private final static int FORM_MANAGER = 7;				  // 양식 관리자
	private final static int PUBLIC_RECIP_GROUP_MANAGER = 8; // 공용 수신처 그룹 관리자 
	private final static int P_DOC_CHARGER = 9;		      // 처리과 문서 담당자
	private final static int D_DOC_CHARGER = 10;		      // 문서과 문서 담당자
	private final static int CABINET_MANAGER = 11;			  // 기록물 관리자
	private final static int DEPT_CHIEF = 12;				  // 부서장
	private final static int RELATED_JOB_MANAGER = 13;		  // 관련업무함 관리자
	
	private final String m_strRoleType[] =
	{
		"1001",			// 회사 조직 관리자
		"1002", 		// 민원 사무 담당
		"1003",			// 대비실 이첩 민원사무 심사관
		"1004",			// 감사원 이첩 민원사무 심사관
		"1005",			// 다수인 이첩 민원사무 심사관
		"1006",			// 공지 사항 관리자
		"1007",			// 심사자
		"1008",			// 양식 관리자
		"1009", 		// 공용 수신처그룹 관리자
		"2001",			// 처리과 문서담당자
		"2002",			// 문서과 문서담당자 
		"2003", 		// 기록물 관리자
		"2004",			// 부서장
		"2005"			// 관련업무함 관리자
	};
	
	private final boolean m_bUserRole[] = 
	{
		false,			// 회사 조직 관리자
		false, 			// 민원 사무 담당
		false,			// 대비실 이첩 민원사무 심사관
		false,			// 감사원 이첩 민원사무 심사관
		false,			// 다수인 이첩 민원사무 심사관
		false,			// 공지 사항 관리자
		false,			// 심사자
		false,			// 양식 관리자
		false,			// 공용 수신처 그룹 관리자
		false,			// 처리과 문서담당자
		false,			// 문서과 문서담당자 
		false,			// 기록물 관리자
		false,			// 부서장
		false			// 관련업무함 관리자
	};
	
	private final boolean m_bRangeRecvLedger[] = 
	{
		false,			// 회사 조직 관리자
		false, 			// 민원 사무 담당
		false,			// 대비실 이첩 민원사무 심사관
		false,			// 감사원 이첩 민원사무 심사관
		false,			// 다수인 이첩 민원사무 심사관
		false,			// 공지 사항 관리자
		false,			// 심사자
		false,			// 양식 관리자
		false,			// 공용 수신처 그룹 관리자
		false,			// 처리과 문서담당자
		false,			// 문서과 문서담당자 
		false,			// 기록물 관리자
		false,			// 부서장
		false			// 관련업무함 관리자
	};
	
	private final boolean m_bRangeDistLedger[] = 
	{
		false,			// 회사 조직 관리자
		false, 			// 민원 사무 담당
		false,			// 대비실 이첩 민원사무 심사관
		false,			// 감사원 이첩 민원사무 심사관
		false,			// 다수인 이첩 민원사무 심사관
		false,			// 공지 사항 관리자
		false,			// 심사자
		false,			// 양식 관리자
		false,			// 공용 수신처 그룹 관리자
		false,			// 처리과 문서담당자
		false,			// 문서과 문서담당자 
		false,			// 기록물 관리자
		false,			// 부서장
		false			// 관련업무함 관리자
	};
				
	// Classification
	private final int APPROVAL = 0;				// 전자결재
	private final int DOCFLOW = 1;				// 문서유통
	private final int ENVIRONMENT = 2; 			// 환경설정
	private final int TDOCFLOW = 3;				// 구기록물철
	private final int PDOCFLOW = 4;				// 이관대장
	private final int PUBLICPOST = 5;			// 공람게시
	
	private final String m_strClassDataURL[] =
	{
		"APPROVAL",
		"DOCFLOW",
		"ENVIRONMENT",
		"TDOCFLOW",
		"PDOCFLOW",
		"PUBLICPOST"
	};
	
	private final String m_strClassDisplayName[] =
	{
		"전자결재",
		"문서유통",
		"환경설정",
		"구기록물철",
		"이관대장",
		"공람게시"
	};
	
	private final String m_strClassDisplayOption[] =
	{
		"A",
		"B",
		"F",
		"D",
		"C",
		"E"
	};
			
	// User Type Status
	private final int DELETED = 0;
	private final int DELAY = 1;
	private final int USE = 2;
	
	/**
	 */
	private ApprCabinets	m_apprCabinets = null;
	private String 			m_strUserViewColumns = "";
	private String 			m_strUserViewTable = "";
	private String			m_strTreeDisplay = "";				// Tree Display Option
	private int 			m_nUserInspection = 0;
	private boolean 		m_bUseDocCabinet = false;   		// 편철 사용 여부 
	private boolean			m_bUseTDocCabinet = false;			// 구기록물철 사용 여부
	private boolean			m_bUsePDocCabinet = false;			// 이관대장 사용 여부
	private int				m_nRangeRecvCabinet = 1;			// 접수함 열람 범위
	private int				m_nRangeDeptCabinet = 1;			// 부서대기함 열람범위
	private int 			m_nRangeRecvLedger = 0; 			// 접수대장 열람 범위
	private int 			m_nRangeDistLedger = 0;				// 배부대장 열람 범위
	private boolean			m_bPermitDeregistration = false;	// 접수시 미편철접수 허용
	private int				m_nSelectedCabinetType = -1;		// 왼쪽 트리를 그릴 때 디폴트로 펄쳐질 결재함
 		
	public UserBoxHandler(ConnectionParam connectionParam)
	{
		super(connectionParam);
		
		m_strUserViewTable = TableDefinition.getTableName(TableDefinition.USER_LIST_VIEW);
		m_strUserViewColumns = UserViewTableMap.getColumnName(UserViewTableMap.USER_ID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_UID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME) + "," + 
							   UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_CODE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME) + "," + 
							   UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY) + "," +
							   UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER);
	}
		
	/**
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @param nType  	 0 : 일반 결재자 정보 / 1 : 대결자 정보 
	 * @return UserBoxes
	 */
	private UserBoxes processData(ResultSet resultSet, int nType)
	{
		UserBoxes  		userBoxes = null;
		boolean 		bAdditional = false;
		boolean	 		bExistence = false;
		int				nIsConCurrent = 0;
		int 			nIsProxy = 0;
		int 			nIsDelegate = 0;
		
		if (resultSet == null)
		{
			m_lastError.setMessage("NullPoint ResultSet.",
								   "UserBoxHandler.processData",
								   "");
			
			return null;
		}
		
		userBoxes = new UserBoxes();
		
		try
		{
			while(resultSet.next())
			{
				// 대결자의 경우 Time Check
				if (nType == 1)
				{
					java.util.Date currentDate = getCurrentDate("yyyy-MM-dd HH:mm:ss");	
					java.util.Date startDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);
					java.util.Date endDate = getRawDate(resultSet, UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE), TIMESTAMP_SECOND);
								
					if (currentDate.before(startDate) || currentDate.after(endDate))
					{
						continue;
					}
				}
				
				bExistence = getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE));
				nIsConCurrent = getInt(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT));
				nIsProxy = getInt(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY));
				nIsDelegate = getInt(resultSet,UserViewTableMap.getColumnName( UserViewTableMap.IS_DELEGATE));
				
				bAdditional = false;
				
				if (bExistence)
				{
					bAdditional = true;
				}
				else if (nIsConCurrent == USE || nIsProxy == USE || nIsDelegate == USE)
				{
					bAdditional = true;
				}
		 
				if (bAdditional)
				{	
					UserBox userBox = new UserBox();
										
					// set Employee information
					userBox.setUserUID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_UID)));
					userBox.setUserName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME)));
					userBox.setUserOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.USER_OTHER_NAME)));
					userBox.setDeptID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID)));
					userBox.setDeptName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME)));
					userBox.setDeptOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEPT_OTHER_NAME)));
					userBox.setUserTitle(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME)));
					userBox.setUserOtherTitle(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.GRADE_OTHER_NAME)));
					userBox.setCompID(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID)));
					userBox.setCompName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_NAME)));
					userBox.setCompOtherName(getString(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.COMP_OTHER_NAME)));
				
					if (!bExistence)
					{	
						if (nIsConCurrent == USE)
							userBox.setRefType(CONCURRENT);
						else if (nIsProxy == USE)
							userBox.setRefType(PROXY);
						else if (nIsDelegate == USE)
							userBox.setRefType(DELEGATE);
					}
					
			//		MVController mvController = new MVController();
					userBox.setRoleCodes(MVController.getRoleCodes(resultSet.getString("ROLE_CODE")));
					
					// 대결자가 아닐 경우 디폴트로 펼쳐지는 정보 설정
					if (nType != 1)
					{
						userBox.setIsDefaultUser(getBoolean(resultSet, UserViewTableMap.getColumnName(UserViewTableMap.DEFAULT_USER)));
					}
	 												
					userBoxes.add(userBox);
				}

			}
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to make UserBox classList.",
								   "UserBoxHandler.processData",
								   e.getMessage());
			
			return null;
		}	
		
		return userBoxes;				
	}
	
	/**
	 * 선택한 사용자의 Tree정보를 가져오는 함수
	 * @param strUserID 사용자 UID
	 * @param nSelectedCabinetType 디폴트로 펼쳐질 결재함 종류
	 * @return UserBoxes
	 */
	public UserBoxes getUserBoxesTree(String strUserUID, int nSelectedCabinetType) 
	{	
		// 디폴트로 펼쳐져야 할 트리를 지정하는 부분.
		m_nSelectedCabinetType = nSelectedCabinetType;
		
		// 트리를 만드는 부분.
		return getUserBoxesTree(strUserUID);
	}
	
		
	/**
	 * 선택한 사용자의 Tree정보를 가져오는 함수
	 * @param strUserID 사용자 UID
	 * @return UserBoxes
	 */
	public UserBoxes getUserBoxesTree(String strUserUID)
	{
		UserBoxes 	userBoxes = null;
		UserBoxes 	sortUserBoxes = null;
		boolean		bResult = false;
		boolean		bFound = false;
		String 		strQuery = "";
		
		bResult = m_connectionBroker.openConnection();
		if (bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
				
		strQuery = "SELECT " + m_strUserViewColumns + 
		   		   " FROM " + m_strUserViewTable + 
		   		   " WHERE ( USER_UID = '" + strUserUID + "'" +
		   		   "         OR USER_RID = '" + strUserUID + "')" +
		   		   "   AND IS_DELETED = " + INOFFICE;
	   		   		
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		userBoxes = processData(m_connectionBroker.getResultSet(), 0);
		if (userBoxes == null)
		{
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		m_connectionBroker.clearQuery();
		
		bResult = appendSubstitutes(userBoxes);
		if (bResult == false)
		{
			m_connectionBroker.clearConnectionBroker();	
			return null;
		}
		
		// Default로 펼쳐지는 트리 설정
		if (userBoxes != null && userBoxes.size() > 0)
		{
			
			for (int i = 0 ; i < userBoxes.size() ; i++)
			{
				UserBox userBox = userBoxes.get(i);
				if (userBox.getIsDefaultUser() == true)
				{
					bFound = true;	
				}
			}
			
			// Default 트리가 없을 경우 원직자 트리를 자동으로 지정
			if (bFound == false)
			{
				for (int i = 0 ; i < userBoxes.size() ; i++)
				{
					if (userBoxes.getRefType(i) == MINE)
					{
						userBoxes.get(i).setIsDefaultUser(true);	
					}
				}	
			}
		}
					
		// 트리 관련 조직 옵션 설정
		setOrganizationData(userBoxes);
		 	
		for (int i = 0 ; i < userBoxes.size() ; i++)
		{
			// 결재함 Option Setting
			setCabinetOption(userBoxes.getUserUID(i));
		
			// initialize User Role
			for (int j = 0 ; j < m_bUserRole.length ; j++)
			{
				m_bUserRole[j] = false;
			}
			
			bResult = makeUserBoxTree(userBoxes.get(i), strUserUID);
			if (bResult == false)
			{
				m_connectionBroker.clearConnectionBroker();	
				return null;
			}
			
			if (m_bUseDocCabinet == true)
			{
				// 기관 설정 코드 
				OrgManager orgManager = new OrgManager(m_connectionBroker.getConnectionParam());
													   	
				UserBox userBox = userBoxes.get(i);
				if (userBox != null)
				{
					Organization organization = orgManager.getOrganization(userBox.getDeptID());
					if (organization != null)
					{
						// if (organization.getIsODCD() == true)
						{
							RelationManager relationManager = new RelationManager(m_connectionBroker.getConnectionParam());
							
							ApprOrganization apprOrganization = relationManager.getApprOrganizationbyOrgID(userBox.getDeptID());												  
							
							if (apprOrganization != null)
							{
							   String strInstitutionID = apprOrganization.getInstitutionCode();
							   userBox.setInstitutionID(strInstitutionID);		   
							}
						}	
					}
				}	
			}								   		
		}
				
		sortUserBoxes = sortUserBoxes(userBoxes);
								  
		m_connectionBroker.clearConnectionBroker();  
		
		return sortUserBoxes;			
	} 
	
	/**
	 * 함 정보 List에 대결자 정보를 더함 
	 * @param UserBoxes append할 함 정보
	 * @param UserID 사용자 ID
	 * @return boolean 
	 */
	private boolean appendSubstitutes(UserBoxes userBoxes)
	{
		UserBoxes	userSubstituteBoxes = null;
		UserBoxes   substituteBoxes = new UserBoxes();
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strAssociatedColumns = "";
		String   	strAssociatedTable = "";
		String 	 	strQuery = "";
		String 		strUserUID = "";
			
		if (m_connectionBroker.IsConnectionClosed() == false)
		{
			strAssociatedColumns = m_strUserViewTable + "." + UserViewTableMap.getColumnName(UserViewTableMap.USER_ID)+ "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.USER_UID) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.USER_NAME) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.GRADE_NAME) + "," + 
									 UserViewTableMap.getColumnName(UserViewTableMap.GRADE_CODE) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.DEPT_NAME) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.ROLE_CODE) +  "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.IS_EXISTENCE) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.IS_CONCURRENT) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.IS_PROXY) + "," +
									 UserViewTableMap.getColumnName(UserViewTableMap.IS_DELEGATE) + "," +
									 UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE) + "," +
									 UserAssociatedTableMap.getColumnName(UserAssociatedTableMap.SUBSTITUTE_END_DATE);
									 
			strAssociatedTable = TableDefinition.getTableName(TableDefinition.USER_ASSOCIATED);
					   		
		   	if (userBoxes != null)
		   	{  
		   		for (int i = 0 ; i < userBoxes.size() ; i++)
		   		{ 
		   			UserBox userBox = userBoxes.get(i);
		   			strUserUID = userBox.getUserUID();
		   				   
					strQuery = "SELECT " + strAssociatedColumns + 
				   		   	   " FROM " + m_strUserViewTable + "," + strAssociatedTable +  
				   		   	   " WHERE " + strAssociatedTable + ".SUBSTITUTE_ID = '" + strUserUID + "'" +
				   		   	   	 " AND " + strAssociatedTable +".USER_ID = " + m_strUserViewTable + ".USER_UID" +
				   		   	   	 " AND " + m_strUserViewTable +".IS_DELETED = 0";
				   		   	   	 
				   		   	   		    
					bResult = m_connectionBroker.executeQuery(strQuery);
					if(bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return false;
					}
					
					userSubstituteBoxes = processData(m_connectionBroker.getResultSet(), 1);
					if (userSubstituteBoxes == null)
					{
						m_connectionBroker.clearQuery();
						return true;
					}
						
					for (int j = 0 ; j  < userSubstituteBoxes.size() ; j++)
					{
						UserBox substituteBox = userSubstituteBoxes.get(j);
						substituteBox.setRefType(SUBSTITUTE);
					}
						
					substituteBoxes.addAll(userSubstituteBoxes);
					
					m_connectionBroker.clearQuery();
		   		}
		   	}
		   	
		   	userBoxes.addAll(substituteBoxes);
			bReturn = true;
		}
		
		return bReturn;			
	}
	
	/**
	 * 사용자 함 트리를 만드는 함수
	 * @param userBoxe  사용자 함정보 
	 * @param strUserUID 사용자 UID
	 * @return boolean
	 */
	private boolean makeUserBoxTree(UserBox userBox, String strUserUID)
	{
		boolean bReturn = true;
		GUID	 guid    = new GUID();
		
		if (userBox != null)
		{
			setUserRoles(userBox.getRoleCodes());
			
			userBox.setUserBoxID(guid.toString());
			
			// Display option 초기화
			if (m_strTreeDisplay == null || m_strTreeDisplay.length() == 0)
			{
				m_strTreeDisplay =  m_strClassDisplayOption[APPROVAL] + "^" +
									m_strClassDisplayOption[DOCFLOW] + "^" +
									m_strClassDisplayOption[PDOCFLOW] + "^" +
									m_strClassDisplayOption[TDOCFLOW] + "^" +
									m_strClassDisplayOption[PUBLICPOST]  + "^" +
									m_strClassDisplayOption[ENVIRONMENT] + "^";
									
			}

			String arrTreeDisplay[] = DataConverter.splitString(m_strTreeDisplay, "^");
			if ((arrTreeDisplay != null) && (arrTreeDisplay.length > 0))
			{
				for (int i = 0; i < arrTreeDisplay.length; i++) 
				{
					Boxes boxes = null;
					
					if (arrTreeDisplay[i].equals(m_strClassDisplayOption[APPROVAL]))
						boxes = makeApprovalBoxTree(userBox, strUserUID);
					else if (arrTreeDisplay[i].equals(m_strClassDisplayOption[DOCFLOW]))
						boxes = makeDocflowBoxTree(userBox, strUserUID);
					else if (arrTreeDisplay[i].equals(m_strClassDisplayOption[TDOCFLOW]))
						boxes = makeTDocflowBoxTree(userBox, strUserUID);
					else if (arrTreeDisplay[i].equals(m_strClassDisplayOption[PDOCFLOW]))
						boxes = makePDocflowBoxTree(userBox, strUserUID);
					else if (arrTreeDisplay[i].equals(m_strClassDisplayOption[ENVIRONMENT]))
						boxes = makeEnvironmentTree(userBox, strUserUID);
					else if (arrTreeDisplay[i].equals(m_strClassDisplayOption[PUBLICPOST]))
						boxes = makeGeneralTree(userBox, arrTreeDisplay[i], strUserUID);
					
					if (i == 0) {
						if (userBox.getIsDefaultUser() == true)
						{
							boxes.get(0).setIsExpand(1);
						}
					}
					
					if (boxes != null)
						userBox.appendBoxes(boxes);
				}
			}
		
		}
		
		return bReturn;
	} 
	
	/**
	 * 사용자 결재함 트리를 만드는 함수 
	 * @param userBoxe  사용자 함정보 
	 * @param strUserID 사용자 ID
	 * @return Boxes
	 */
	private Boxes makeApprovalBoxTree(UserBox userBox, String strUserID)
	{
		boolean	 bIsDefaultUser = false;
		Boxes 	 boxes = new Boxes();
		Box		 appBox = new Box();
		int 	 nRefType = 0;
		int	     nCount = 0;
		
		nRefType = userBox.getRefType(); // User Reference Type
		bIsDefaultUser = userBox.getIsDefaultUser();	// Tree Default User
		
		// 대결자의 경우 옵션 check
		if (nRefType == SUBSTITUTE && m_apprCabinets.IsDisplaySubstituteCabGroup(ApprCabinets.APPROVAL_CABINET) == false)
		{
			return boxes;
		}
		
		appBox.setBoxID(m_strClassDataURL[APPROVAL]);
		appBox.setDisplayName(m_strClassDisplayName[APPROVAL]);
		appBox.setDataURL(m_strClassDataURL[APPROVAL]);
	
		boxes.add(appBox);
		
		// 결재함을 구성 
		if (m_apprCabinets != null)
		{
			for (int i = 0 ; i < m_apprCabinets.size() ; i++)
			{
				ApprCabinet apprCabient = m_apprCabinets.get(i);
				
				int nCabinetType = apprCabient.getCabinetType();
				int nGroupType = apprCabient.getGroupType();
				
				if (nGroupType == ApprCabinets.APPROVAL_CABINET)
				{
					nCount = appendApprCabinet(userBox, boxes, nCabinetType, appBox.getBoxID(), nRefType,
											   nCount, nGroupType, bIsDefaultUser, 0, "");
				}
			}
		}
			
		return boxes;
	}
	
	/**
	 * 결재함 Display 여부를 결정하는 함수 
	 * @param userBox 사용자 함 정보 
	 * @param boxes 함 정보 List
	 * @param strParentID 상위 Cabinet ID
	 * @param strDataURL Data URL
	 * @param nRefType 사용자 함의 Reference Type 
	 * @param nCount 현재 추가된 결재함의 개수
	 * @param nGroupType 결재 그룹 Type (전자결재, 문서유통)
	 * @param bIsDefaultUser default롤 펼쳐질 사용자인지 정보 
	 * @param bIsBizCategory 하위 업무 관련 Category인지 여부 
	 * @param strBusinessID  업무 소속 Business ID
	 * @param nOrder 출력 순서
	 * @return int
	 */
	private int appendApprCabinet(UserBox userBox, Boxes boxes, int nBoxType, String strParentID, 
						   		  int nRefType, int nCount, int nGroupType, 
						   		  boolean bIsDefaultUser, int nIsBizCategory,
						   		  String strBusinessID)
	{
		boolean bAppend  = false;
		int 	nElementCount = 0;
		
		switch(nBoxType)
		{
			case ApprCabinets.RECEIVED:						// 대기함 
			case ApprCabinets.PROCESSING:					// 진행함
			case ApprCabinets.COMPLETED:					// 완료함
			case ApprCabinets.SUBMITED:						// 기안함
			case ApprCabinets.SUBMITEDAPPROVAL:				// 결재기안함
			case ApprCabinets.SUBMITEDDOCFLOW:				// 접수기안함
			case ApprCabinets.EXCHANGESUBMITED:				// 연계기안함
			case ApprCabinets.AFTERAPPROVAL:				// 사후보고함
			case ApprCabinets.CONCERN:						// 관심문서함
			case ApprCabinets.TRANSFERLEDGER:				// 이송대장 
			case ApprCabinets.REGILEDGER:					// 등록대장 
			case ApprCabinets.TTRANSFERLEDGER:				// 구기록물철 이송대장
			case ApprCabinets.TSENDING:						// 구기록물철 발송함
			case ApprCabinets.TREGILEDGER:					// 구기록물철 등록대장
			case ApprCabinets.PTRANSFERLEDGER:				// 이관대장 이송대장
			case ApprCabinets.PSENDING:						// 이관대장 발송함
			case ApprCabinets.PREGILEDGER:					// 이관대장 등록대장
			case ApprCabinets.APPENVIRONMENT:				// 환경설정 결재정보
					bAppend = true;	
					break;
			case ApprCabinets.REJECTED:						// 반려함
			case ApprCabinets.PRIVATE:						// 개인함
			case ApprCabinets.DISCARDED:					// 폐기함 
					if (nRefType != SUBSTITUTE)
					{
						bAppend = true;	
					}
					break;
			case ApprCabinets.DEPTRECEIVED:					// 부서대기함
					if (m_nRangeDeptCabinet == 0)
					{
						bAppend = true;						// 부서 사용자에게 부서대기함 오픈 
					}
					else
					{			
						if (m_bUserRole[D_DOC_CHARGER] == true || m_bUserRole[P_DOC_CHARGER] == true)
						{
							bAppend = true;		// 문서 담당자에게 부서대기함 오픈
						}
					}
					break;
			case ApprCabinets.SENDING:						// 발송함
					if (m_bUserRole[P_DOC_CHARGER] == true)
					{
						bAppend = true;	
					}
					break;
			case ApprCabinets.RECEIVING:					// 접수함
					if (m_nRangeRecvCabinet == 0)			
					{
						bAppend = true;			// 부서 사용자에게 접수함 오픈 
					}
					else
					{
						if (m_bUserRole[P_DOC_CHARGER] == true)
						{
							bAppend = true;		// 문서 담당자에게 접수함 오픈 
						}
					}
					break;
			case ApprCabinets.INVESTIGATION:				// 심사함 
					if (m_bUserRole[JUDGE] == true)
					{
						bAppend = true;	
					}
					break;
			case ApprCabinets.DISTRIBUTION:					// 배부함
					if (m_bUserRole[D_DOC_CHARGER] == true)
					{
						bAppend = true;	
					}
					break;
			case ApprCabinets.RECVLEDGER:					// 접수대장
			case ApprCabinets.TRECVLEDGER:					// 구기록물철 접수대장
			case ApprCabinets.PRECVLEDGER:					// 이관대장 접수대장
					if (m_nRangeRecvLedger == 1)			// 처리과 문서 담당자에게 접수대장 오픈  
					{
						for (int i = 0 ; i < m_bUserRole.length ; i++)
						{
							if (m_bUserRole[i] == true && m_bRangeRecvLedger[i] == true)
							{
								bAppend = true;
								break;					
							}
						}
					}
					else									// 부서 사용자에게 접수대장 오픈
					{
						bAppend = true;		
					}
					break;
			case ApprCabinets.DISTLEDGER:					// 배부대장 
			case ApprCabinets.TDISTLEDGER:					// 구기록물철 배부대장
			case ApprCabinets.PDISTLEDGER:					// 이관대장 배부대장
					if (m_nRangeDistLedger == 1)			// 문서과 문서 담당자에게 배부대장 오픈
					{
						if (m_bUserRole[D_DOC_CHARGER] == true)
						{
							bAppend = true;
						}	
					}
					else								    // 문서과 사용자에게 배부대장 오픈
					{
						if (m_bUserRole[D_DOC_CHARGER] == true || userBox.getIsODCD() == true)
						{
							bAppend = true;
						}	
					}
					break;
			case ApprCabinets.INSPECTION:					// 감사대장
			case ApprCabinets.PREINSPECTION:				// 사전감사대장
			case ApprCabinets.POSTINSPECTION:				// 사후감사대장
			case ApprCabinets.TINSPECTION:					// 구기록물철 감사대장
			case ApprCabinets.PINSPECTION:					// 이관대장 감사대장
			case ApprCabinets.PROCINSPECTION:				// 감사접수함
					if (m_nUserInspection!=0 && userBox.getIsInspection() == true)
					{
						bAppend = true;
					}
					break;
			case ApprCabinets.DEREGILEDGER:					// 미편철함
					if (m_bPermitDeregistration == true)
					{
						bAppend = true;
					}
					break;
			case ApprCabinets.GENERALENVIRONMENT:			// 환경설정 일반정보
					if (nRefType == MINE)			
					{
						bAppend = true;
					}
					break;
			case ApprCabinets.RELATEDAPPROVAL:				// 연관업무함
					if (m_bUserRole[RELATED_JOB_MANAGER] == true)
					{
						bAppend = true;
					}
					break;	
		}
		
		// 대결자의 경우 결재함 옵션 check
		if (bAppend == true)
		{
			if (nRefType == SUBSTITUTE && m_apprCabinets.IsDisplaySubstituteCabinet(nBoxType) == false)
				bAppend = false;
		}
				
		if (bAppend == true)
		{
			nElementCount = createAndAppendBox(boxes, nBoxType, strParentID, nRefType, nCount, 
			                                   nGroupType, bIsDefaultUser, 
			                                   nIsBizCategory, strBusinessID);	 	
			                                   
			// 업무 관련 트리 노드 추가 
			if (nBoxType == ApprCabinets.REGILEDGER)      // 등록대장 
			{
				nElementCount = createAndAppendBizCategory(boxes, nBoxType, nElementCount);
			}
		}
		else
		{
			nElementCount = nCount;
		}
			
		return nElementCount;
	}
	
	/**
	 * 사용자 문서유통함 트리를 만드는 함수 
	 * @param userBoxe  사용자 함정보 
	 * @param strUserUID 사용자 UID
	 * @return Boxes
	 */
	private Boxes makeDocflowBoxTree(UserBox userBox, String strUserUID)
	{
		boolean		bIsDefaultUser = false;
		Boxes 	 	boxes = new Boxes();
		Box		 	docflowBox = new Box();
		int	 		nDepth = 0;
		int 	 	nRefType = 0;
		int	 		nCount = 0;
	
//		if (userBox.getRefType() != SUBSTITUTE)
		{
			nRefType = userBox.getRefType();
			bIsDefaultUser = userBox.getIsDefaultUser();
			
			// 대결자의 경우 옵션 check
			if (nRefType == SUBSTITUTE && m_apprCabinets.IsDisplaySubstituteCabGroup(ApprCabinets.DOCFLOW_CABINET) == false)
			{
				return boxes;
			}
							
			docflowBox.setBoxID(m_strClassDataURL[DOCFLOW]);
			docflowBox.setDisplayName(m_strClassDisplayName[DOCFLOW]);
			docflowBox.setDataURL(m_strClassDataURL[DOCFLOW]);
						
			boxes.add(docflowBox);
			nDepth++;
					
			// 문서유통함을 구성 
			if (m_apprCabinets != null)
			{
				for (int i = 0 ; i < m_apprCabinets.size() ; i++)
				{
					ApprCabinet apprCabient = m_apprCabinets.get(i);
					
					int nCabinetType = apprCabient.getCabinetType();
					int nGroupType = apprCabient.getGroupType();
					
					if (nGroupType == ApprCabinets.DOCFLOW_CABINET)
					{
						nCount = appendApprCabinet(userBox, boxes, nCabinetType, docflowBox.getBoxID(), nRefType,
												   nCount, nGroupType, bIsDefaultUser, 0, "");
					}
				}
			}
		}
		
		return boxes;
	}
	
	/**
	 * 사용자 구기록물철 트리를 만드는 함수 
	 * @param userBoxe  사용자 함정보 
	 * @param strUserUID 사용자 UID
	 * @return Boxes
	 */
	private Boxes makeTDocflowBoxTree(UserBox userBox, String strUserUID)
	{
		boolean  bIsDefaultUser = false;
		Boxes 	 boxes = null;
		int	 	 nDepth = 0;
		int 	 nRefType = 0;
		int	     nCount = 0;
	
		if (m_bUseTDocCabinet == true && userBox.getRefType()==MINE)
		{
			boxes =  new Boxes();
			Box		 docflowBox = new Box();
					
			docflowBox.setBoxID(m_strClassDataURL[TDOCFLOW]);
			docflowBox.setDisplayName(m_strClassDisplayName[TDOCFLOW]);
			docflowBox.setDataURL(m_strClassDataURL[TDOCFLOW]);
						
			boxes.add(docflowBox);
			nDepth++;
			
			nRefType = userBox.getRefType();
			bIsDefaultUser = userBox.getIsDefaultUser();
			
			// 문서유통함을 구성 
			if (m_apprCabinets != null)
			{
				for (int i = 0 ; i < m_apprCabinets.size() ; i++)
				{
					ApprCabinet apprCabient = m_apprCabinets.get(i);
					
					int nCabinetType = apprCabient.getCabinetType();
					int nGroupType = apprCabient.getGroupType();
					
					if (nGroupType == ApprCabinets.T_DOCFLOW_CABINET)
					{
						nCount = appendApprCabinet(userBox, boxes, nCabinetType, docflowBox.getBoxID(), nRefType,
												   nCount, nGroupType, bIsDefaultUser, 0, "");
					}
				}
			}

		}
		
		return boxes;
	}
	
	/**
	 * 사용자 이관대장 트리를 만드는 함수.
	 * @param userBox 사용자 함 정보
	 * @param strUserUID 사용자 UID
	 * @return Boxes
	 */
	private Boxes makePDocflowBoxTree(UserBox userBox, String strUserUID)
	{
		boolean bIsDefaultUser = false;
		Boxes   boxes = null;
		int     nDepth = 0;
		int     nRefType = 0;
		int     nCount = 0;
		
		if (m_bUsePDocCabinet == true && userBox.getRefType() == MINE)
		{
			boxes = new Boxes();
			
			Box docflowBox = new Box();
			
			docflowBox.setBoxID(m_strClassDataURL[PDOCFLOW]);
			docflowBox.setDisplayName(m_strClassDisplayName[PDOCFLOW]);
			docflowBox.setDataURL(m_strClassDataURL[PDOCFLOW]);	
			
			boxes.add(docflowBox);
			nDepth++;
			
			nRefType = userBox.getRefType();
			bIsDefaultUser = userBox.getIsDefaultUser();
			
			// 이관대장의 문서유통함을 구성 
			if (m_apprCabinets != null)
			{
				for (int i = 0 ; i < m_apprCabinets.size() ; i++)
				{
					ApprCabinet apprCabient = m_apprCabinets.get(i);
					
					int nCabinetType = apprCabient.getCabinetType();
					int nGroupType = apprCabient.getGroupType();
					
					if (nGroupType == ApprCabinets.P_DOCFLOW_CABINET)
					{
						nCount = appendApprCabinet(userBox, boxes, nCabinetType, docflowBox.getBoxID(), nRefType,
												   nCount, nGroupType, bIsDefaultUser, 0, "");
					}
				}
			}
		}

		return boxes;
	} 
	
	/**
	 * 사용자 환경설정 트리를 만드는 함수 
	 * @param userBoxe  사용자 함정보 
	 * @param strUserUID 사용자 UID
	 * @return Boxes
	 */
	private Boxes makeEnvironmentTree(UserBox userBox, String strUserUID)
	{
		boolean		bIsDefaultUser = false;
		Boxes 	 	boxes = null;
		int	 		nDepth = 0;
		int 	 	nRefType = 0;
		int	 		nCount = 0;
	
		if (userBox.getRefType() != SUBSTITUTE)
		{
			boxes =  new Boxes();
			Box		 envBox = new Box();
					
			envBox.setBoxID(m_strClassDataURL[ENVIRONMENT]);
			envBox.setDisplayName(m_strClassDisplayName[ENVIRONMENT]);
			envBox.setDataURL(m_strClassDataURL[ENVIRONMENT]);
			
			boxes.add(envBox);
			nDepth++;
			
			nRefType = userBox.getRefType();
			bIsDefaultUser = userBox.getIsDefaultUser();
			
			// 문서유통함을 구성 
			if (m_apprCabinets != null)
			{
				for (int i = 0 ; i < m_apprCabinets.size() ; i++)
				{
					ApprCabinet apprCabient = m_apprCabinets.get(i);
					
					int nCabinetType = apprCabient.getCabinetType();
					int nGroupType = apprCabient.getGroupType();
					
					if (nGroupType == ApprCabinets.GENERAL_CABIENT)
					{
						nCount = appendApprCabinet(userBox, boxes, nCabinetType, envBox.getBoxID(), nRefType,
												   nCount, nGroupType, bIsDefaultUser, 0, "");
					}
				}
			}
		}
				
		return boxes;
	}
	
	/**
	 * 일반적인 그룹을 만드는 함수 
	 * @param userBoxe  사용자 함정보 
	 * @param strOption	옵션 정보 
	 * @param strUserUID 사용자 UID
	 * @return Boxes
	 */
	private Boxes makeGeneralTree(UserBox userBox, String strOption, String strUserUID)
	{
		Boxes boxes =  new Boxes();
		Box	  box = new Box();
		String strDataURL = "";
		String strDisplayName = "";
		
		if (strOption.equals(m_strClassDisplayOption[PUBLICPOST])) {
			strDataURL = m_strClassDataURL[PUBLICPOST];
			strDisplayName = m_strClassDisplayName[PUBLICPOST];
		}
		
		box.setBoxID(strDataURL);
		box.setDisplayName(strDisplayName);
		box.setDataURL(strDataURL);
		
		boxes.add(box);
					
		return boxes;
	}
	
	/** 
	 * 사용자 함 정보를 만들어 append하는 함수  
	 * @param boxes 함 정보 List
	 * @param strParentID 상위 Cabinet ID
	 * @param strDataURL Data URL
	 * @param nRefType 사용자 함의 Reference Type 
	 * @param nCount 현재 추가된 결재함의 개수
	 * @param nGroupType 결재 그룹 Type (전자결재, 문서유통)
	 * @param bIsDefaultUser default롤 펼쳐질 사용자인지 정보 
	 * @param bIsBizCategory 하위 업무 관련 Category인지 여부 
	 * @param strBusinessID  업무 소속 Business ID
	 * @return int
	 */
	private int createAndAppendBox(Boxes boxes, int nBoxType, String strParnetID, 
						   		   int nRefType, int nCount, int nGroupType, 
						   		   boolean bIsDefaultUser, int nIsBizCategory, 
						   		   String strBusinessID)
	{
		ApprCabinet apprCabinet = null;
		Box 		box = new Box();
		int 		nIsExpand = 0;
		int 	    nIsSelected = 0;
		
		apprCabinet = m_apprCabinets.getApprCabinetbyCabinetType(nBoxType);
		if (apprCabinet != null && apprCabinet.isDisplay() == true)
		{
			box.setBoxID(apprCabinet.getDataURL());
			box.setDisplayName(apprCabinet.getDisplayName());
			box.setParentBoxID(strParnetID);
			box.setDataURL(apprCabinet.getDataURL());
			box.setIsBizCategory(nIsBizCategory);
			
			// Selected 여부 
			if ((nGroupType == APPROVAL) && (m_nSelectedCabinetType != -1)) 
			{
				if (nBoxType == m_nSelectedCabinetType)
					nIsSelected = 1;	
			} 
			else 
			{
				if (nCount == 0)
					nIsSelected = 1;
			}
			
			// Expand 여부 	
			if (nGroupType != ENVIRONMENT &&
				nGroupType != TDOCFLOW &&
				nGroupType != PDOCFLOW &&
				bIsDefaultUser == true &&	// Default로 펼쳐지는 사용자로 정의한 경우
				nRefType != SUBSTITUTE &&
				nCount == 0)
			{
				nIsExpand = 1;
			}
			
			box.setIsExpand(nIsExpand);
			box.setIsSelected(nIsSelected);
			
			// 업무 ID 관련 정보 셋팅
			if (nIsBizCategory == 1)
			{
				box.setBusinessID(strBusinessID);
			}
			
			nCount++;
			
			boxes.add(box);	
			
		}
				
		return nCount;	
	}
		
	/**
	 * 사용자의 Role 정보를 setting해주는 함수 
	 * @param roleCodes 사용자 Role Code List
	 * @return boolean
	 */
	private void setUserRoles(RoleCodes roleCodes)
	{
		int i = 0;
		int j = 0;
		
		if (roleCodes != null)
		{
			// initialize
			for (i = 0 ; i < m_bUserRole.length ; i++)
			{
				m_bUserRole[i] = false;	
			}
			
			for (i = 0; i < roleCodes.size() ; i++)
			{
				String strRoleCode = roleCodes.getRoleCode(i);
				
				for (j = 0 ; j < m_strRoleType.length ; j++)
				{
					if (m_strRoleType[j].compareTo(strRoleCode) == 0)
					{
						m_bUserRole[j] = true;
					}
				}
			}
		} 
	}
	
	/**
	 * 사용자 함 정보를 순서에 맞게 재정렬 해주는 함수 
	 * @param userBoxes 사용자 함 정보 List
	 * @return UserBoxes
	 */
	private UserBoxes sortUserBoxes(UserBoxes userBoxes)
	{
		UserBoxes sortUserBoxes = null;
		
		if (userBoxes != null)
		{
			sortUserBoxes = new UserBoxes();
			
			for (int i = 0 ; i < 5 ; i++)
			{
				for (int j = 0 ; j < userBoxes.size() ; j++)
				{
					UserBox userBox = userBoxes.get(j);
					if (userBox.getRefType() == i)
						sortUserBoxes.add(userBox);
				}
			}
		}
		
		return sortUserBoxes;
	}
		
	/**
	 * 결재함 Option 정보 Setting (Company Option 기준)
	 * @param strUserUID	사용자 UID	
	 */
	private void setCabinetOption(String strUserUID)
	{
		ResultSet   resultSet = null;
		boolean 	bResult = false;
		boolean 	bIsCompUserInspectionOpt = false;
		boolean     bIsCompUseDocCabientOpt = false;
		boolean		bIsCompUseTCabinetOpt = false;
		boolean		bIsCompUseTDocCabNameOpt = false;
		boolean		bIsCompUseTDocflowOption = false;
		boolean 	bIsCompRangeRecvCabinetOpt = false;
		boolean		bIsCompRangeDeptCabinetOpt = false;
		boolean		bIsCompRangeRecvLedgerOpt = false;
		boolean  	bIsCompRangeDistLedgerOpt = false;
		boolean		bIsCompPermitDeregiOpt = false;
		boolean 	bIsCompUsePCabinetOpt = false;
		boolean		bIsCompUsePDocCabNameOpt = false;
		boolean		bIsCompUsePDocflowOption = false;
		boolean 	bIsCompUseTreeDisplayOpt = false;
		String 		strQuery = "";
		String 		strCompanyID = "";
		String		strDeptID = "";
		String 		strApprovalOptionID = "AIOPT52";			// 결재함 명칭 변경 옵션
		String 		strDocflowOptionID = "AIOPT54";				// 유통함 명칭 변경 옵션
		String 		strInspectionOptionID = "AIOPT74";			// 감사대장 사용 여부 옵션
		String 		strDocflowCabinetOptID = "AIOPT56";			// 문서유통 명칭 변경 옵션
		String 		strDocCabinetOptID = "AIOPT148";			// 편철 사용 여부 설정 옵션
		String 		strTDocCabinetOptID = "AIOPT154";			// 구기록물철 사용 여부 설정 옵션
		String 		strTDocCabNameOptID = "AIOPT156";			// 구기록물철 명칭 변경 옵션
		String		strTDocflowOptionID = "AIOPT157";			// 구기록물철 함 명칭 변경 옵션
		String 		strPDocCabinetOptID = "AIOPT28";			// 이관대장 사용 여부 설정 옵션
		String 		strPDocCabNameOptID = "AIOPT203";			// 이관대장 명칭 변경 옵션
		String 		strPDocflowOptionID = "AIOPT204";			// 이관대장 함 명칭 변경 옵션
		String 		strRangeRecvCabinetOptID = "AIOPT47";		// 접수함 열람 범위 옵션
		String 		strRangeDeptCabinetOptID = "AIOPT48";		// 부서대기함 열람 범위 옵션
		String 		strRangeRecvLedgerOptID = "AIOPT173";		// 접수대장 열람 범위
		String		strRangeDistLedgerOptID = "AIOPT174";		// 배부대장 열람 범위
		String 		strPermitDeregiOptID = "AIOPT179";			// 편철 사용시 미편철 접수 허용 옵션
		String      strSubstituteAOptionID = "AIOPT189";		// 부재설정시 전자결재함 설정 옵션
		String 		strSubstituteDOptionID = "AIOPT190";		// 부재설정시 문서유통함 설정 옵션
		String 		strTreeDisplayOptionID = "AIOPT207";		// 트리출력 순서 관련 옵션
		String      strDocflowCabinetName = "";					// 옵션 값 : AIOPT56  
		String		strTDocflowCabinetName = "";				// 옵션 값 : AIOPT156
		String 		strPDocflowCabinetName = "";				// 옵션 값 : AIOPT00002 
		String 		strApprovalMString = "";					// 옵션 값 : AIOPT52 
		String 		strDocflowMString = "";						// 옵션 값 : AIOPT54 
		String 		strTDocflowMString = "";					// 옵션 값 : AIOPT157
		String 		strPDocflowMString = "";					// 옵션 값 : AIOPT00003 
		String 		strApprovalSString = "";					// 옵션 값 : AIOPT189 
		String		strDocflowSString = "";						// 옵션 값 : AIOPT190 
		String 		strRangeRecvLedger = "";					// 옵션 값 : AIOPT173
		String 		strTreeDisplayMString = "";					// 옵션 값 : AIOPT207 
		int 		nIsInspection = 0;							// 옵션 값 : AIOPT74					
		int 		nIsUseDocCabient = 0;						// 옵션 값 : AIOPT148
		int 		nIsUseTDocCabinet = 0;						// 옵션 값 : AIOPT154
		int 		nIsUsePDocCabinet = 0;						// 옵션 값 : AIOPT00001
		int			nRangeRecvCabinet = 1;						// 옵션 값 : AIOPT47
		int			nRangeDeptCabinet = 2;						// 옵션 값 : AIOPT48
		int			nRangeDistLedger = 0;						// 옵션 값 : AIOPT174
		int 		nPermitDeregistration = 0;					// 옵션 값 : AIOPT179
		
		// 조직 옵션을 초기화하는 부분
		initializeCabinetOption();
		
		try
		{
			if (m_connectionBroker.IsConnectionClosed() == false)
			{
				// get user company ID
				strQuery = "SELECT " + UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID) + "," +
									   UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID) +							 
						   " FROM " + m_strUserViewTable +
						   " WHERE USER_UID = '" + strUserUID + "'";
						   
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return;					
				}	
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return;									
				}
				
				while (resultSet.next())
				{
					strCompanyID = DataConverter.toString(resultSet.getString(UserViewTableMap.getColumnName(UserViewTableMap.COMP_ID))); 
					strDeptID = DataConverter.toString(resultSet.getString(UserViewTableMap.getColumnName(UserViewTableMap.DEPT_ID)));
				}
				
				m_connectionBroker.clearQuery();
				
				// get cabinet option
				strQuery = "SELECT " + OptionTableMap.getColumnName(OptionTableMap.OPTION_ID) + "," +
									   OptionTableMap.getColumnName(OptionTableMap.COMP_ID) + "," +
									   OptionTableMap.getColumnName(OptionTableMap.MSTRING_VALUE) + "," +
									   OptionTableMap.getColumnName(OptionTableMap.STRING_VALUE) + "," +
									   OptionTableMap.getColumnName(OptionTableMap.INTEGER_VALUE) + 
						   " FROM " + TableDefinition.getTableName(TableDefinition.OPTION) +
						   " WHERE OPTION_ID IN ('" + strApprovalOptionID + "','" + strDocflowOptionID + "','" + strInspectionOptionID + "','" 
						   							+ strDocflowCabinetOptID +"','" + strDocCabinetOptID + "','" + strTDocCabinetOptID + "','"
						   							+ strTDocCabNameOptID + "','" + strTDocflowOptionID + "','" + strRangeRecvCabinetOptID + "','"
						   							+ strRangeDeptCabinetOptID + "','" + strRangeRecvLedgerOptID + "','" + strRangeDistLedgerOptID + "','"
						   							+ strPermitDeregiOptID + "','" + strSubstituteAOptionID + "','" + strSubstituteDOptionID +"','" 
						   							+ strPDocCabinetOptID + "','" + strPDocCabNameOptID  + "','" + strPDocflowOptionID  + "','"
						   							+ strTreeDisplayOptionID + "')";
						   													   
				if (strCompanyID != null && strCompanyID.length() > 0)
				{
					strQuery += " AND COMP_ID IN ('DEFAULT','" + strCompanyID + "')";
				}		   
				else
				{
					strQuery += " AND COMP_ID = 'DEFAULT'";
				}
				
				bResult = m_connectionBroker.excuteQuery(strQuery);
				if (bResult == false)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return;
				}
				
				resultSet = m_connectionBroker.getResultSet();
				if (resultSet == null)
				{
					m_lastError.setMessage(m_connectionBroker.getLastError());
					m_connectionBroker.clearQuery();
					return;
				}
				
				while (resultSet.next())
				{
					String strOptionID = DataConverter.toString(resultSet.getString(OptionTableMap.getColumnName(OptionTableMap.OPTION_ID)));
					String strOptCompID = DataConverter.toString(resultSet.getString(OptionTableMap.getColumnName(OptionTableMap.COMP_ID)));
					String strMStringValue = DataConverter.toString(resultSet.getString(OptionTableMap.getColumnName(OptionTableMap.MSTRING_VALUE)));
					String strStringValue = DataConverter.toString(resultSet.getString(OptionTableMap.getColumnName(OptionTableMap.STRING_VALUE)));
					int    nIntegerValue = resultSet.getInt(OptionTableMap.getColumnName(OptionTableMap.INTEGER_VALUE));

					if (strOptCompID.compareTo("DEFAULT") != 0) // 회사옵션
					{
						if (strMStringValue != null && strMStringValue.length() > 0)
						{
							if (strOptionID.compareTo(strApprovalOptionID) == 0)
							{
								strApprovalMString = strMStringValue;	
							}
							else if (strOptionID.compareTo(strDocflowOptionID) == 0)
							{
								strDocflowMString = strMStringValue;	
							}
							else if (strOptionID.compareTo(strSubstituteAOptionID) == 0)
							{
								strApprovalSString = strMStringValue;
							}
							else if (strOptionID.compareTo(strSubstituteDOptionID) == 0)
							{
								strDocflowSString = strMStringValue;	
							}
						}
						
						// 감사대장 사용 여부 
						if (strOptionID.compareTo(strInspectionOptionID) == 0 )
						{
							m_nUserInspection = nIntegerValue;
							bIsCompUserInspectionOpt = true;
						}
						
						// 문서유통 캐비넷 명 
						if (strOptionID.compareTo(strDocflowCabinetOptID) == 0)
						{
							strDocflowCabinetName = strStringValue;
						}
						
						// 편철 사용 여부 
						if (strOptionID.compareTo(strDocCabinetOptID) == 0 )
						{
							nIsUseDocCabient = nIntegerValue;
							bIsCompUseDocCabientOpt = true;
						}
						
						// 구기록물철 사용여부 
						if (strOptionID.compareTo(strTDocCabinetOptID) == 0 )
						{
							nIsUseTDocCabinet = nIntegerValue;
							bIsCompUseTCabinetOpt = true;
						}
						
						// 구기록물철 명칭 설정
						if (strOptionID.compareTo(strTDocCabNameOptID) == 0 )
						{
							strTDocflowCabinetName = strStringValue;
							bIsCompUseTDocCabNameOpt = true;	
						} 
						
						// 구기록물철 함 설정
						if (strOptionID.compareTo(strTDocflowOptionID) == 0 )
						{
							strTDocflowMString = strMStringValue;
							bIsCompUseTDocflowOption = true;
						}
						
						// 이관대장 사용여부 
						if (strOptionID.compareTo(strPDocCabinetOptID) == 0 )
						{
							nIsUsePDocCabinet = nIntegerValue;
							bIsCompUsePCabinetOpt = true;
						}
						
						// 이관대장 명칭 셜정
						if (strOptionID.compareTo(strPDocCabNameOptID) == 0 )
						{
							strPDocflowCabinetName = strStringValue;
							bIsCompUsePDocCabNameOpt = true;
						}
						
						// 이관대장 함 설정
						if (strOptionID.compareTo(strPDocflowOptionID) == 0)
						{
							strPDocflowMString = strMStringValue;
							bIsCompUsePDocflowOption = true;
						}
											
						// 접수함 열람 범위 설정 
						if (strOptionID.compareTo(strRangeRecvCabinetOptID) == 0 )
						{
							nRangeRecvCabinet = nIntegerValue;
							bIsCompRangeRecvCabinetOpt = true;	
						}
						
						// 부서대기함 열람 범위 설정
						if (strOptionID.compareTo(strRangeDeptCabinetOptID) == 0 )
						{
							nRangeDeptCabinet = nIntegerValue;
							bIsCompRangeDeptCabinetOpt = true;
						}
						
						// 접수대장 열람 범위 설정
						if (strOptionID.compareTo(strRangeRecvLedgerOptID) == 0 )
						{
							strRangeRecvLedger = strMStringValue;
							bIsCompRangeRecvLedgerOpt = true;
						}
						
						// 배부대장 열람 범위 설정
						if (strOptionID.compareTo(strRangeDistLedgerOptID) == 0 )
						{
							nRangeDistLedger = nIntegerValue;	
							bIsCompRangeDistLedgerOpt = true;
						}
						
						// 편철 사용시 미편철 접수 허용
						if (strOptionID.compareTo(strPermitDeregiOptID) == 0)
						{
							nPermitDeregistration = nIntegerValue;
							bIsCompPermitDeregiOpt = true;	
						}
						
						// 트리 순서 Display 옵션 
						if (strOptionID.compareTo(strTreeDisplayOptionID) == 0)
						{
							strTreeDisplayMString = strMStringValue;
							bIsCompUseTreeDisplayOpt = true;	
						}
					}
					else	// Default
					{
						if (strMStringValue != null && strMStringValue.length() > 0)
						{
							if (strOptionID.compareTo(strApprovalOptionID) == 0 && strApprovalMString.length() == 0)
							{
								strApprovalMString = strMStringValue;	
							}
							else if (strOptionID.compareTo(strDocflowOptionID) == 0 && strDocflowMString.length() == 0)
							{
								strDocflowMString = strMStringValue;	
							}
							else if (strOptionID.compareTo(strSubstituteAOptionID) == 0 && strApprovalSString.length() == 0)
							{
								strApprovalSString = strMStringValue;
							}
							else if (strOptionID.compareTo(strSubstituteDOptionID) == 0 && strDocflowSString.length() == 0)
							{
								strDocflowSString = strMStringValue;	
							}
						}
		
						// 감사대장 옵션 
						if (strOptionID.compareTo(strInspectionOptionID) == 0 && bIsCompUserInspectionOpt == false)
						{
							m_nUserInspection = nIntegerValue;
						}
						
						// 문서유통 캐비넷 명 
						if (strOptionID.compareTo(strDocflowCabinetOptID) == 0 && strDocflowCabinetName.length() == 0)
						{
							strDocflowCabinetName = strStringValue;
						}
						
						// 편철 사용 여부 
						if (strOptionID.compareTo(strDocCabinetOptID) == 0 && bIsCompUseDocCabientOpt == false)
						{
							nIsUseDocCabient = nIntegerValue;
						}	
						
						// 구기록물철 사용여부 
						if (strOptionID.compareTo(strTDocCabinetOptID) == 0 && bIsCompUseTCabinetOpt == false) 
						{
							nIsUseTDocCabinet = nIntegerValue;	
						}
						
						// 구기록물철 명칭 설정
						if (strOptionID.compareTo(strTDocCabNameOptID) == 0 && bIsCompUseTDocCabNameOpt == false)
						{
							strTDocflowCabinetName = strStringValue;
						} 
						
						// 구기록물철 함 설정
						if (strOptionID.compareTo(strTDocflowOptionID) == 0 && bIsCompUseTDocflowOption == false)
						{
							strTDocflowMString = strMStringValue;	
						}
						
						// 이관대장 사용 여부 
						if (strOptionID.compareTo(strPDocCabinetOptID) == 0 && bIsCompUsePCabinetOpt == false)
						{
							nIsUsePDocCabinet = nIntegerValue;
						}
						
						// 이관대장 명칭 설정
						if (strOptionID.compareTo(strPDocCabNameOptID) == 0 && bIsCompUsePDocCabNameOpt == false)
						{
							strPDocflowCabinetName = strStringValue;
						}
						
						// 이관대장 함 설정
						if (strOptionID.compareTo(strPDocflowOptionID) == 0 && bIsCompUsePDocflowOption == false)
						{
							strPDocflowMString = strMStringValue;
						}
						
						// 접수함 열람 범위 설정 
						if (strOptionID.compareTo(strRangeRecvCabinetOptID) == 0  && bIsCompRangeRecvCabinetOpt == false)
						{
							nRangeRecvCabinet = nIntegerValue;
						}
						
						// 부서대기함 열람 범위 설정
						if (strOptionID.compareTo(strRangeDeptCabinetOptID) == 0 && bIsCompRangeDeptCabinetOpt == false)
						{
							nRangeDeptCabinet = nIntegerValue;
						}
						
						// 접수대장 열람 범위 설정
						if (strOptionID.compareTo(strRangeRecvLedgerOptID) == 0 && bIsCompRangeRecvLedgerOpt == false)
						{
							strRangeRecvLedger = strMStringValue;
						}
						
						// 배부대장 열람 범위 설정
						if (strOptionID.compareTo(strRangeDistLedgerOptID) == 0 && bIsCompRangeDistLedgerOpt == false)
						{
							nRangeDistLedger = nIntegerValue;
						}
						
						// 편철 사용시 미편철 접수 허용
						if (strOptionID.compareTo(strPermitDeregiOptID) == 0 && bIsCompPermitDeregiOpt == false)
						{
							nPermitDeregistration = nIntegerValue;
						}
						
						// 트리 순서 Display 옵션 
						if (strOptionID.compareTo(strTreeDisplayOptionID) == 0 && bIsCompUseTreeDisplayOpt == false)
						{
							strTreeDisplayMString = strMStringValue;
						}
					} 	
				}
				
				m_connectionBroker.clearQuery();
								
				// 전자결재 관련 결재함들 옵션 설정 
				if (strApprovalMString.length() > 0)
				{
					m_apprCabinets.setOptionApprovalCabinetInfo(strApprovalMString);
				}
				
				// 문서유통 관련 결재함들 옵션 설정
				if (strDocflowMString.length() > 0)
				{
					m_apprCabinets.setOptionDocflowCabinetInfo(strDocflowMString);
				}
				
				// 구기록물철 관련 결재함들 옵션 설정
				if (strTDocflowMString.length() > 0)
				{
					m_apprCabinets.setOptionTDocflowCabinetInfo(strTDocflowMString);
				}
				
				// 이관대장 관련 결재함들 옵션 설정
				if (strPDocflowMString.length() > 0)
				{
					m_apprCabinets.setOptionPDocflowCabinetInfo(strPDocflowMString);
				}
				
				// 대결설정 전자결재 관련 Display 옵션설정
				m_apprCabinets.setOptionSubstituteCabinetInfo(strApprovalSString, ApprCabinets.APPROVAL_CABINET);	
				
				// 대결설정 문서관리 관련 Display 옵션설정
				m_apprCabinets.setOptionSubstituteCabinetInfo(strDocflowSString, ApprCabinets.DOCFLOW_CABINET);
				
				// 옵션이 없을 경우 기본 함 정보 출력
				if (m_apprCabinets.size() == 0)
				{
					m_apprCabinets.setDefaultApprCabinetInfo();		
				}
				
				// 환경설정 정보 옵션 설정
				m_apprCabinets.appendEnvCabinetInfo();
					
				// 문서유통함 명칭 변경
				if (strDocflowCabinetName != null && strDocflowCabinetName.length() > 0)
				{
					m_strClassDisplayName[DOCFLOW] = strDocflowCabinetName;	
				}	
				
				// 구기록물철 명칭 변경
				if (strTDocflowCabinetName != null && strTDocflowCabinetName.length() > 0)
				{
					m_strClassDisplayName[TDOCFLOW] = strTDocflowCabinetName;	
				}
				
				// 이관대장 명칭 변경
				if (strPDocflowCabinetName != null && strPDocflowCabinetName.length() > 0)
				{
					m_strClassDisplayName[PDOCFLOW] = strPDocflowCabinetName;
				}
				
				// 편철 사용여부 설정
				if (nIsUseDocCabient == 1)
				{
					m_bUseDocCabinet = true;
				}
				else
				{
					m_bUseDocCabinet = false;
				}
				
				// 구기록물철 사용여부 설정
				if (nIsUseTDocCabinet == 1)	
				{
					m_bUseTDocCabinet = true;
				}
				else
				{
					m_bUseTDocCabinet = false;
				}
				
				// 이관대장 사용 여부 설정
				if (nIsUsePDocCabinet == 1)
				{
					m_bUsePDocCabinet = true;
				}
				else
				{
					m_bUsePDocCabinet = false;
				}
					
				// 접수함 열람 범위 설정
				if (nRangeRecvCabinet >= 0)
				{ 
					m_nRangeRecvCabinet = nRangeRecvCabinet;
				}
				
				// 부서대기함 열람 범위 설정
				if (nRangeDeptCabinet >= 0)
				{
					m_nRangeDeptCabinet = nRangeDeptCabinet;
				}	

				// 접수대장 열람 범위 설정
				if (strRangeRecvLedger!= null && strRangeRecvLedger.length() > 0)
				{
					String arrRangeRecvLedger[] = DataConverter.splitString(strRangeRecvLedger, "^");
					if (arrRangeRecvLedger != null && arrRangeRecvLedger.length > 0)
					{
						m_nRangeRecvLedger = Integer.parseInt(arrRangeRecvLedger[0]);
						if (m_nRangeRecvLedger == 1)	 // 접수대장을 열람할수 있는 사용자 역할을 직접 입력한 경우
						{
							if (arrRangeRecvLedger.length == 2)
							{
								setLedgerRole(arrRangeRecvLedger[1], 0);
							}	
						}
					}
				}	
				
				// 배부대장 열람 범위 설정
				if (nRangeDistLedger >= 0)
				{
					m_nRangeDistLedger = nRangeDistLedger;
				}	
				
				// 편철 사용시 미편철 접수 허용
				if (nPermitDeregistration == 1)
				{
					m_bPermitDeregistration = true;
				}	
				else
				{
					m_bPermitDeregistration = false;	
				}
				
				// 트리 Display 옵션 설정
				if (strTreeDisplayMString == null || strTreeDisplayMString.length() == 0)
					strTreeDisplayMString = "A^B^C^D^E^F^";	
					
				m_strTreeDisplay = strTreeDisplayMString;		
			}
			else 
			{
				m_lastError.setMessage("Fail to get open connection.",
									   "UserBoxHandler.setCabinetOption.Closed Connection",
									   "");
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to set cabinet option.",
								   "UserBoxHandler.setCabinetOption",
								   e.getMessage());
			
			return;
		}	
	}
	
	/**
	 * Cabinet Option을 초가화하는 부분.
	 */
	private void initializeCabinetOption()
	{
		m_apprCabinets = null;
		m_apprCabinets = new ApprCabinets();
		
		m_nUserInspection = 0;
		m_bUseDocCabinet = false;
		m_bUseTDocCabinet = false;
		m_bUsePDocCabinet = false;
		m_nRangeRecvCabinet = 0;
		m_nRangeDeptCabinet = 0;
		m_nRangeDistLedger = 0;
		m_bPermitDeregistration = false;
		m_strTreeDisplay = "A^B^C^D^E^F^";	
	}
	
	/**
	 * 트리 관련 조직 옵션 설정
	 * @param userBoxs 결재함 트리 
	 * @return boolean
	 */
	private boolean setOrganizationData(UserBoxes userBoxes)
	{
		ResultSet 	resultSet = null;
		boolean 	bReturn = false;
		boolean 	bResult = false;
		String 	 	strQuery = "";
		int		nIsODCD = 0;
		int 		nIsInspection = 0;
		
		if (userBoxes == null)
		{
			m_lastError.setMessage("Fail to get UserBoxes Object",
								   "UserBoxHandler.setOrganizationData.Empty UserBoxes",
								   "");
			return bReturn;
		}
		
		try
		{
			if (m_connectionBroker.IsConnectionClosed() == false)
			{
				for (int i = 0 ; i < userBoxes.size() ; i++)
				{
					UserBox userBox = userBoxes.get(i);
					String  strDeptID = userBox.getDeptID();
					
					strQuery = "SELECT " + OrgTableMap.getColumnName(OrgTableMap.IS_ODCD) + "," +
					                       OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION) +
					           " FROM " + TableDefinition.getTableName(TableDefinition.ORGANIZATION) +
					           " WHERE ORG_ID = '" + strDeptID + "'";
					           
					bResult = m_connectionBroker.executeQuery(strQuery);
					if (bResult == false)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return bReturn;
					}
					
					resultSet = m_connectionBroker.getResultSet();
					if (resultSet == null)
					{
						m_lastError.setMessage(m_connectionBroker.getLastError());
						m_connectionBroker.clearQuery();
						return bReturn;									
					}
					
					while (resultSet.next())
					{
						nIsODCD = resultSet.getInt(OrgTableMap.getColumnName(OrgTableMap.IS_ODCD));
						nIsInspection = resultSet.getInt(OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION));
					}
					
					if (nIsODCD == 0)
						userBox.setIsODCD(false);
					else
						userBox.setIsODCD(true);
					
					if (nIsInspection == 0)
						userBox.setIsInspection(false);
					else
						userBox.setIsInspection(true);
					
					m_connectionBroker.clearQuery();						
				}
			}
			else
			{
				m_lastError.setMessage("Fail to get open connection.",
									   "UserBoxHandler.setOrganizationData.Closed Connection",
									   "");				
			}
		}
		catch(SQLException e)
		{
			m_lastError.setMessage("Fail to set organization data.",
								   "UserBoxHandler.setOrganizationData.SQLException",
								   e.getMessage());
		}
		finally
		{		
			return bReturn;
		}
	}
	
	/**
	 * 소속함의 Business Category 정보를 append 하는 함수.
	 * @param userBox 		사용자 함 정보 
	 * @param nBoxType	 	소속 결재 함 Type
	 * @param nCount		현재 결재함의 개수
	 * @return int
	 */
	private int createAndAppendBizCategory(Boxes boxes, int nBoxType, int nCount)
	{
		HierarchyManager hierachyMagager = null;
		LedgerCategories ledgerCategories = null;
		ApprCabinet 	 apprCabinet = null;
		String			 strFolderType = "";
		
		strFolderType = ApprCabinets.m_strBizFolderType[nBoxType];
		if (strFolderType == null || strFolderType.length() == 0)
		{
			m_lastError.setMessage("Fail to get folder type.",
								   "UserBoxHandler.createAndAppendBizCategory.Empty Folder Type",
								   "");
			return nCount;
		}
		
		apprCabinet = m_apprCabinets.getApprCabinetbyCabinetType(nBoxType);
		if (apprCabinet != null)
		{	
			// 대장별 업무 Category 추출
			hierachyMagager = new HierarchyManager(m_connectionBroker.getConnectionParam());
			ledgerCategories = hierachyMagager.getLedgerCategoriesByFolderType(strFolderType);
	
			if (ledgerCategories != null)
			{
				for (int i = 0 ; i < ledgerCategories.size() ; i++)
				{
					LedgerCategory ledgerCategory = ledgerCategories.get(i);
					Box 		   box = new Box();
	
					box.setBoxID(ledgerCategory.getCategoryID());
					box.setDisplayName(ledgerCategory.getCategoryName());
					box.setParentBoxID(apprCabinet.getDataURL());
					box.setDataURL(apprCabinet.getDataURL());
					box.setIsBizCategory(1);
					box.setBusinessID(ledgerCategory.getBusinessID());
	
					// Selected 여부 
					if (i == 0)
					{
						box.setIsSelected(1);	
					}
										
					nCount++;
					
					boxes.add(box);	
				}	
			}	
		}
		
		return nCount;	
	}
	
	/**
	 * 접수대장과 배부대장을 보여줄지 여부를 설정.
	 * @param strRoles		대장을 보여주어야 하는 역할
	 * @param nLedgerType 	대장 Type ( 0 : 접수대장, 1 : 배부대장)
	 * @return boolean
	 */
	private boolean setLedgerRole(String strRoleCodes, int nLedgerType)
	{
		boolean bReturn = false;
		String  arrRole[] = null;
		
		// check input condition
		if (strRoleCodes == null || strRoleCodes.length() == 0)
		{
			m_lastError.setMessage("Fail to get ledger role information.",
								   "UserBoxHandler.setLedgerRoles.Empty Role",
								   "");
			return bReturn;
		}

		// split role string
		arrRole = DataConverter.splitString(strRoleCodes, ":");
		if (arrRole != null)
		{
			for (int i = 0 ; i < arrRole.length ; i++)
			{
				String strRoleCode = arrRole[i];
				for (int j = 0 ; j < m_strRoleType.length ; j++)
				{
					if (m_strRoleType[j].compareTo(strRoleCode) == 0)
					{
						if (nLedgerType == 0) 		// 접수대장
						{
							m_bRangeRecvLedger[j] = true;
						}
						else if (nLedgerType == 1)	// 배부대장
						{
							m_bRangeDistLedger[j] = true;	
						}
					}
				}
			}	
		}
				
		bReturn = true;
		return bReturn;
	}
	
	/**
	 * 결재함 명칭을 가져오는 함수 추가
	 * @param String strCompID   사용자 UID
	 * @param int	 nCabinetType 결재함 타입	(0: 결재함, 1: 문서함)
	 * @param String strCabinetID 결재함 ID
	 * @return String
	 */
	public String getCabinetName(String strCompID, int nCabinetType, String strCabOptionID)
	{
		OptionManager 	optionManager = null;
		Option			option = null;
		String 			strCabinetName = "";
		String			strApprovalCabOptionID = "AIOPT52";
		String			strDocflowCabOptionID = "AIOPT54";
		String 			strOptionID = "";
		String 			strOptionValue = "";
		String 			strCabinetDelimiter = "^";
		String 			strDetailDelimiter = ":";
		int 			nDetailInfoCount = 2;
		
		if (strCabOptionID == null || strCabOptionID.length() == 0)
		{
			m_lastError.setMessage("Fail to get cabinet ID.",
								   "UserBoxHandler.getCabinetName.Empty Cabinet ID",
								   "");
			return strCabinetName;	
		}
		
		optionManager = new OptionManager(m_connectionBroker.getConnectionParam());
		
		if (nCabinetType == 1)
		{
			strOptionID = strDocflowCabOptionID;
		}
		else
		{
			strOptionID = strApprovalCabOptionID;
			
		}
		
		option = optionManager.getCompanyOption(strOptionID, strCompID);
		if (option == null)
		{
			m_lastError.setMessage(optionManager.getLastError());
			return strCabinetName;
		}
		
		strOptionValue = option.getMStringValue();

		// 전자 결재함 옵션 파싱
		if (strOptionValue != null && strOptionValue.length() > 0)
		{
			String 	strApprovalOptions[] = DataConverter.splitString(strOptionValue, strCabinetDelimiter);
			
			if (strApprovalOptions != null && strApprovalOptions.length > 0)
			{			
				for (int i = 0 ; i < strApprovalOptions.length ; i++)
				{
					String strApprDetailOption = strApprovalOptions[i];
					String strApprDetailInfos[] = DataConverter.splitString(strApprDetailOption, strDetailDelimiter);
				
					if (strApprDetailInfos.length == nDetailInfoCount)
					{
						String strOptionType = strApprDetailInfos[0];
						String strDisplayName  = strApprDetailInfos[1];
						
						if (strOptionType.compareTo(strCabOptionID) == 0)
						{
							strCabinetName = strDisplayName;	
						}
					}
				}		
			}
		}	
	
		return strCabinetName;
	}	
}
