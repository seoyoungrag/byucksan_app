package com.sds.acube.app.idir.org.user;

/**
 * ApprCabinets.java
 * 2003-02-25
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.db.*;
import java.util.*;

public class ApprCabinets 
{
	private LinkedList m_lApprCabinetList = null;
	
	// Cabinet Group Type
	public static final int APPROVAL_CABINET = 0;			// 전자결재 관련 cabinet
	public static final int DOCFLOW_CABINET = 1;			// 문서유통 관련 cabinet		
	public static final int GENERAL_CABIENT = 2;			// 환경설정 관련 cabinet
	public static final int T_DOCFLOW_CABINET = 3;			// 구기록물철 관련 cabinet
	public static final int P_DOCFLOW_CABINET = 4;			// 이관대장 관련 cabinet
	
	// Cabinet Type
	public static final int PROCESSING = 0;					// 진행함
	public static final int SUBMITED = 1;					// 기안함
	public static final int COMPLETED = 2;					// 완료함
	public static final int RECEIVED = 3;					// 대기함
	public static final int PRIVATE = 4;					// 개인함
	public static final int REJECTED = 5;					// 반려함
	public static final int DISCARDED = 6;					// 폐기함 
	public static final int DEPTRECEIVED = 7;				// 부서대기함 
	public static final int AFTERAPPROVAL = 8;				// 후열함
	public static final int SUBMITEDAPPROVAL = 9;			// 결재기안함
	public static final int SUBMITEDDOCFLOW	= 10;			// 접수기안함
	public static final int EXCHANGESUBMITED = 11;			// 연계기안함
	public static final int RELATEDAPPROVAL = 12;			// 연관업무함
	public static final int PROCINSPECTION = 13;			// 감사접수함
	public static final int CONCERN = 14;					// 관심문서함
	public static final int INSPECTION = 15;				// 감사대장
	public static final int PREINSPECTION = 16;				// 사전감사대장
	public static final int POSTINSPECTION = 17;			// 사후감사대장
	public static final int INVESTIGATION = 18;				// 심사함
	public static final int SENDING = 19;					// 발송함
	public static final int RECEIVING = 20;					// 접수함 
	public static final int DISTRIBUTION = 21;				// 배부함 
	public static final int REGILEDGER = 22;				// 등록대장				
	public static final int RECVLEDGER = 23;				// 접수대장 
	public static final int DISTLEDGER = 24;				// 배부대장 
	public static final int TRANSFERLEDGER = 25;			// 이송대장 
	public static final int CIVILMANAGEDEPT = 26;			// 민원 사무 처리부 	
	public static final int CIVILRECV = 27;					// 민원사무처리/접수대장
	public static final int CIVILDIST = 28;					// 민원사무처리/배부대장
	public static final int CIVILMOVE1 = 29;				// 대비실 이첩 
	public static final int CIVILMOVE2 = 30;				// 감사원 이첩 
	public static final int CIVILMOVE3 = 31;				// 다수인 이첩
	public static final int DEREGILEDGER = 32;				// 미편철함
	public static final int APPENVIRONMENT = 33;			// 전자 결재 설정
	public static final int GENERALENVIRONMENT = 34;		// 일반 정보 설정 
	public static final int TREGILEDGER = 35;				// 구기록물철 등록대장
	public static final int TRECVLEDGER = 36;				// 구기록물철 접수대장
	public static final int TDISTLEDGER = 37;				// 구기록물철 배부대장
	public static final int TSENDING = 38;					// 구기록물철 발송함
	public static final int TINSPECTION = 39;				// 구기록물철 감사대장
	public static final int TTRANSFERLEDGER = 40;			// 구기록물철 이송대장
	public static final int PREGILEDGER = 41;				// 이관대장 등록대장
	public static final int PRECVLEDGER = 42;				// 이관대장 접수대장
	public static final int PDISTLEDGER = 43;				// 이관대장 배부대장
	public static final int PSENDING = 44;					// 이관대장 발송함
	public static final int PINSPECTION = 45;				// 이관대장 감사대장
	public static final int PTRANSFERLEDGER = 46;			// 이관대장 이송대장
		
	// Cabient DataURL
	private final String m_strDataURL[] =
	{
		"PROCESSING",           // 진행함
		"SUBMITED",				// 기안함
		"COMPLETED",			// 완료함
		"RECEIVED",				// 대기함
		"PRIVATE",				// 개인함
		"REJECTED",				// 반려함
		"DISCARDED",			// 폐기함
		"DEPTRECEIVED",			// 부서대기함
		"AFTERAPPROVAL",		// 사후보고함
		"SUBMITEDAPPROVAL",		// 결재기안함
		"SUBMITEDDOCFLOW",		// 접수기안함
		"EXCHANGESUBMITED",     // 연계기안함
		"RELATEDAPPROVAL",     	// 연관업무함
		"PROCINSPECTION",		// 감사접수함
		"CONCERN",				// 관심문서함
		"INSPECTION",			// 감사대장
		"PREINSPECTION",		// 사전감사대장
		"POSTINSPECTION",		// 사후감사대장
		"INVESTIGATION",		// 심사함
		"SENDING",				// 발송함
		"RECEIVING",			// 접수함
		"DISTRIBUTION",			// 배부함
		"REGILEDGER",			// 등록대장
		"RECVLEDGER",			// 접수대장
		"DISTLEDGER",			// 배부대장
		"TRANSFERLEDGER",		// 이송대장 
		"CIVILMANAGEDEPT",		// 민원 사무 처리부
		"CIVILRECV",			// 민원사무처리/접수대장
		"CIVILDIST",			// 민원사무처리/배부대장
		"CIVILMOVE1",			// 대비실 이첩
		"CIVILMOVE2",			// 감사원 이첩
		"CIVILMOVE3",			// 다수인 이첩
		"DEREGILEDGER",			// 미편철함
		"APPRENV",				// 결재 정보
		"GENERALENV",			// 일반 정보
		"TREGILEDGER",			// 구기록물철-등록대장
		"TRECVLEDGER",			// 구기록물철-접수대장
		"TDISTLEDGER",			// 구기록물철-배부대장
		"TSENDING",				// 구기록물철-발송함
		"TINSPECTION",			// 구기록물철-감사대장
		"TTRANSFERLEDGER",		// 구기록물철 이송대장
		"PREGILEDGER",			// 이관대장-등록대장
		"PRECVLEDGER",			// 이관대장-접수대장
		"PDISTLEDGER",			// 이관대장-배부대장
		"PSENDING",				// 이관대장-발송함
		"PINSPECTION",			// 이관대장-감사대장
		"PTRANSFERLEDGER",		// 이관대장-이송대장
	};
	
	// Cabinet Display Name
	private final String m_strDisplayName[] =
	{
		"진행함",
		"기안함",
		"완료함",
		"대기함",
		"개인함",
		"반려함",
		"폐기함",
		"부서대기함", 
		"사후보고함",
		"결재기안함",
		"접수기안함",
		"연계기안함",
		"연관업무함",
		"감사접수함",
		"관심문서함", 
		"감사대장",
		"사전감사대장",
		"사후감사대장",
		"심사함",
		"발송함",
		"접수함",
		"배부함", 
		"등록대장",				
		"접수대장", 
		"배부대장",
		"이송대장", 
		"민원 사무 처리부", 	
		"민원사무처리/접수대장",
		"민원사무처리/배부대장",
		"대비실 이첩", 
		"감사원 이첩", 
		"다수인 이첩",
		"미편철함",
		"결재 정보",
		"일반 정보",
		"등록대장",								// 구기록물철 관련 				
		"접수대장",								// 구기록물철 관련 	 							
		"배부대장",								// 구기록물철 관련 	
		"발송함",								// 구기록물철 관련 
		"감사대장",								// 구기록물철 관련
		"이송대장",								// 구기록물철 관련
		"등록대장",								// 이관대장 관련 				
		"접수대장",								// 이관대장 관련 	 							
		"배부대장",								// 이관대장 관련 	
		"발송함",								// 이관대장 관련 
		"감사대장",								// 이관대장 관련
		"이송대장"								// 이관대장 관련
	};
	
	// Cabinet Display 여부 
	private final boolean m_bDispaly[] =
	{
		false,	// 진행함
		false,	// 기안함
		false,	// 완료함
		false,	// 대기함
		false,	// 개인함
		false,	// 반려함
		false,	// 폐기함
		false,	// 부서대기함
		false,	// 사후보고함
		false,	// 결재기안함
		false,	// 접수기안함
		false,	// 연계접수함
		false,	// 연관업무함
		false,	// 감사접수함
		false,	// 관심문서함
		false,	// 감사대장
		false,	// 사전감사대장
		false,	// 사후감사대장
		false,	// 심사함
		false,	// 발송함
		false,	// 접수함
		false,	// 배부함
		false,	// 등록대장
		false,	// 접수대장
		false,	// 배부대장
		false,  // 이송대장
		false,	// 민원 사무 처리부
		false,	// 민원사무처리/접수대장
		false,	// 민원사무처리/배부대장
		false,	// 대비실 이첩
		false,	// 감사원 이첩
		false,	// 다수인 이첩
		false,	// 미편철함
		true,	// 결재 정보
		true,	// 일반 정보
		false,	// 구기록물철-등록대장
		false,	// 구기록물철-접수대장
		false,	// 구기록물철-배부대장
		false,	// 구기록물철-발송함
		false,	// 구기록물철-감사대장
		false,	// 구기록물철-이송대장
		false,	// 이관대장-등록대장
		false,	// 이관대장-접수대장
		false,	// 이관대장-배부대장
		false,	// 이관대장-발송함
		false,	// 이관대장-감사대장
		false,	// 이관대장-이송대장
	};
	
	// Cabient Group Type
	private final int m_nGroupType[] =
	{
		APPROVAL_CABINET,	// 진행함
		APPROVAL_CABINET,	// 기안함
		APPROVAL_CABINET,	// 완료함
		APPROVAL_CABINET,	// 대기함
		APPROVAL_CABINET,	// 개인함
		APPROVAL_CABINET,	// 반려함
		APPROVAL_CABINET,	// 폐기함
		APPROVAL_CABINET,	// 부서대기함
		APPROVAL_CABINET,	// 사후보고함
		APPROVAL_CABINET,	// 결재기안함
		APPROVAL_CABINET,	// 접수기안함
		APPROVAL_CABINET,	// 연계접수함
		APPROVAL_CABINET,	// 연관업무함
		APPROVAL_CABINET,	// 감사접수함
		APPROVAL_CABINET,	// 관심문서함
		DOCFLOW_CABINET,	// 감사대장
		DOCFLOW_CABINET,	// 사전감사대장
		DOCFLOW_CABINET,	// 사후감사대장
		DOCFLOW_CABINET,	// 심사함
		DOCFLOW_CABINET,	// 발송함
		DOCFLOW_CABINET,	// 접수함
		DOCFLOW_CABINET,	// 배부함
		DOCFLOW_CABINET,	// 등록대장
		DOCFLOW_CABINET,	// 접수대장
		DOCFLOW_CABINET,	// 배부대장
		DOCFLOW_CABINET, 	// 이송대장 
		DOCFLOW_CABINET,	// 민원 사무 처리부
		DOCFLOW_CABINET,	// 민원사무처리/접수대장
		DOCFLOW_CABINET,	// 민원사무처리/배부대장
		DOCFLOW_CABINET,	// 대비실 이첩
		DOCFLOW_CABINET,	// 감사원 이첩
		DOCFLOW_CABINET,	// 다수인 이첩
		DOCFLOW_CABINET,	// 미편철함
		GENERAL_CABIENT,	// 결재 정보
		GENERAL_CABIENT,	// 일반 정보
		T_DOCFLOW_CABINET,	// 구기록물철-등록대장
		T_DOCFLOW_CABINET,	// 구기록물철-접수대장
		T_DOCFLOW_CABINET,	// 구기록물철-배부대장
		T_DOCFLOW_CABINET,	// 구기록물철-발송함
		T_DOCFLOW_CABINET,	// 구기록물철-감사대장
		T_DOCFLOW_CABINET,	// 구기록물철-이송대장
		P_DOCFLOW_CABINET,	// 이관대장-등록대장
		P_DOCFLOW_CABINET,	// 이관대장-접수대장
		P_DOCFLOW_CABINET,	// 이관대장-배부대장
		P_DOCFLOW_CABINET,	// 이관대장-발송함
		P_DOCFLOW_CABINET,	// 이관대장-감사대장
		P_DOCFLOW_CABINET	// 이관대장-이송대장
	};
	
	// Cabient Option Type
	private final String m_strOptionType[] =
	{
		"B",	// 진행함
		"D",	// 기안함
		"C",	// 완료함
		"A",	// 대기함
		"G",	// 개인함
		"E",	// 반려함
		"H",	// 폐기함
		"I", 	// 부서대기함
		"F", 	// 사후보고함
		"J",	// 결재기안함
		"K",	// 접수기안함
		"N",	// 연계기안함
		"O",	// 연관업무함
		"L",	// 감사접수함
		"M",	// 관심문서함
		"I",	// 감사대장
		"L",	// 사전감사대장
		"M",	// 사후감사대장
		"B",	// 심사함
		"A",	// 발송함
		"C",	// 접수함
		"D", 	// 배부함
		"E",	// 등록대장				
		"F", 	// 접수대장
		"G",	// 배부대장 
		"J",    // 이송대장
		"H", 	// 민원 사무 처리부	
		"",		// 민원사무처리/접수대장
		"",		// 민원사무처리/배부대장
		"",		// 대비실 이첩 
		"", 	// 감사원 이첩
		"",		// 다수인 이첩
		"K",	// 미편철함
		"",		// 결재 정보
		"",		// 일반 정보
		"E",	// 구기록물철-등록대장
		"F",	// 구기록물철-접수대장
		"G",	// 구기록물철-배부대장
		"A",	// 구기록물철-발송함
		"I",	// 구기록물철-감사대장
		"J",	// 구기록물철-이송대장
		"E",	// 이관대장-등록대장
		"F",	// 이관대장-접수대장
		"G",	// 이관대장-배부대장
		"A",	// 이관대장-발송함
		"I",	// 이관대장-감사대장
		"J"		// 이관대장-이송대장
	};
	
	// Business Category Folder Type
	public static final String m_strBizFolderType[] =
	{
		"AB",	// 진행함
		"AD",	// 기안함
		"AC",	// 완료함
		"AA",	// 대기함
		"AG",	// 개인함
		"AE",	// 반려함
		"AH",	// 폐기함
		"AI", 	// 부서대기함
		"AF", 	// 사후보고함
		"AJ",	// 결재기안함
		"AK",	// 접수기안함
		"",		// 연계기안함
		"",		// 연관업무함
		"",		// 감사접수함
		"",		// 관심문서함
		"AI",	// 감사대장
		"AL",	// 사전감사대장
		"AM",	// 사후감사대장
		"AB",	// 심사함
		"LA",	// 발송함
		"LC",	// 접수함
		"LD", 	// 배부함
		"LE",	// 등록대장				
		"LF", 	// 접수대장
		"LG",	// 배부대장 
		"LJ",   // 이송대장
		"LH", 	// 민원 사무 처리부	
		"",		// 민원사무처리/접수대장
		"",		// 민원사무처리/배부대장
		"",		// 대비실 이첩 
		"", 	// 감사원 이첩
		"",		// 다수인 이첩
		"LK",	// 미편철함
		"",		// 결재 정보
		"",		// 일반 정보
		"TE",	// 구기록물철-등록대장
		"TF",	// 구기록물철-접수대장
		"TG",	// 구기록물철-배부대장
		"TA",	// 구기록물철-접수함
		"TI",	// 구기록물철-감사대장
		"TJ",	// 구기록물철-이송대장
		"PE",	// 이관대장-등록대장
		"PF",	// 이관대장-접수대장
		"PG",	// 이관대장-배부대장
		"PA",	// 이관대장-접수함
		"PI",	// 이관대장-감사대장
		"PJ"	// 이관대장-이송대장
	};
	
	// 대결설정시 Cabinet Display 여부 
	private final boolean m_bSubstituteDisplay[] =
	{
		false,	// 진행함
		false,	// 기안함
		false,	// 완료함
		false,	// 대기함
		false,	// 개인함
		false,	// 반려함
		false,	// 폐기함
		false,	// 부서대기함
		false,	// 사후보고함
		false,	// 결재기안함
		false,	// 접수기안함
		false,	// 연계기안함
		false,	// 연관업무함
		false,	// 감사접수함
		false,	// 관심문서함
		false,	// 감사대장
		false,	// 사전감사대장
		false,	// 사후감사대장
		false,	// 심사함
		false,	// 발송함
		false,	// 접수함
		false,	// 배부함
		false,	// 등록대장
		false,	// 접수대장
		false,	// 배부대장
		false,  // 이송대장
		false,	// 민원 사무 처리부
		false,	// 민원사무처리/접수대장
		false,	// 민원사무처리/배부대장
		false,	// 대비실 이첩
		false,	// 감사원 이첩
		false,	// 다수인 이첩
		false,	// 미편철함
		false,	// 결재 정보
		false,	// 일반 정보
		false,	// 구기록물철-등록대장
		false,	// 구기록물철-접수대장
		false,	// 구기록물철-배부대장
		false,	// 구기록물철-발송함
		false,	// 구기록물철-감사대장
		false,	// 구기록물철-이송대장
		false,	// 이관대장-등록대장
		false,	// 이관대장-접수대장
		false,	// 이관대장-배부대장
		false,	// 이관대장-발송함
		false,	// 이관대장-감사대장
		false	// 이관대장-이송대장
	};
	
	public ApprCabinets()
	{
		m_lApprCabinetList = new LinkedList();
	}
	
	/**
	 * List에 UserListItem를 더함.
	 * @param userListItem 리스트 컬럼 하나의 정보  
	 * @return boolean 성공 여부 
	 */
	public boolean add(ApprCabinet apprCabinet)
	{
		return m_lApprCabinetList.add(apprCabinet);
	}
	
	/**
	 * 결재함 List의 size
	 * @return int 결재함 List의 개수  
	 */ 
	public int size()
	{
		return m_lApprCabinetList.size();
	}
	
	/**
	 * 결재함 한개의 정보 
	 * @param  nIndex 결재함 index
	 * @return ApprCabinet 결재함 정보 
	 */
	public ApprCabinet get(int nIndex)
	{
		return (ApprCabinet)m_lApprCabinetList.get(nIndex);
	}
	
	/**
	 * 결재함 Display 여부 반환.
	 * @param  nIndex ApprCabinet index
	 * @return boolean
	 */
	public boolean isDisplay(int nIndex) 
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.isDisplay();
	}
	
	/**
	 * 결재함 Group Type 반환.
	 * @param  nIndex ApprCabinet index
	 * @return int
	 */
	public int getGroupType(int nIndex) 
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.getGroupType();
	}

	/**
	 * 결재함 DataURL 반환.
	 * @param  nIndex ApprCabinet index
	 * @return String
	 */
	public String getDataURL(int nIndex) 
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.getDataURL();
	}

	/**
	 * 결재함 Display Name 반환.
	 * @param  nIndex ApprCabinet index
	 * @return String
	 */
	public String getDisplayName(int nIndex) 
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.getDisplayName();
	}
	
	/**
	 * 결재함 Option Type 반환.
	 * @param  nIndex ApprCabinet index
	 * @return String
	 */
	public String getOptionType(int nIndex) 
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.getOptionType();
	}
	
	/**
	 * 결재함 종류 반환
	 * @param  nIndex ApprCabinet index
	 * @return int
	 */
	public int getCabinetType(int nIndex)
	{
		ApprCabinet apprCabinet = (ApprCabinet)m_lApprCabinetList.get(nIndex);
		return apprCabinet.getCabinetType();	
	}
		
	/**
	 * Default 결재함 정보 Setting
	 */
	public void setDefaultApprCabinetInfo()
	{		
		for (int i = 0 ; i < m_strDataURL.length ; i++)
		{
			ApprCabinet apprCabinet = new ApprCabinet(); 
			
			apprCabinet.setCabinetType(i);
			
			if (m_nGroupType[i] == T_DOCFLOW_CABINET || m_nGroupType[i] == P_DOCFLOW_CABINET)
				apprCabinet.setDisplay(false);
			else
				apprCabinet.setDisplay(true);
			
			apprCabinet.setGroupType(m_nGroupType[i]);
			apprCabinet.setDisplayName(m_strDisplayName[i]);
			apprCabinet.setDataURL(m_strDataURL[i]);
			apprCabinet.setOptionType(m_strOptionType[i]);
			
			m_lApprCabinetList.add(apprCabinet);
		}	
	}
		
	/**
	 * 결재함 Option 정보 Setting (Approval Cabinet Option)
	 * @param strApprovalMOption    	전자결재함들의 Display Option 정보
	 */
	public void setOptionApprovalCabinetInfo(String strApprovalMOption)
	{
		String 		strCabinetDelimiter = "^";
		String 		strDetailDelimiter = ":";
		int 		nDetailInfoCount = 2;
		int 		i = 0;
		
		// 전자 결재함 Display Option Setting
		if (strApprovalMOption != null && strApprovalMOption.length() > 0)
		{
			String 	strApprovalOptions[] = DataConverter.splitString(strApprovalMOption, strCabinetDelimiter);
			
			if (strApprovalOptions != null && strApprovalOptions.length > 0)
			{			
				for (i = 0 ; i < strApprovalOptions.length ; i++)
				{
					String strApprDetailOption = strApprovalOptions[i];
					String strApprDetailInfos[] = DataConverter.splitString(strApprDetailOption, strDetailDelimiter);
				
					if (strApprDetailInfos.length == nDetailInfoCount)
					{
						String strOptionType = strApprDetailInfos[0];
						String strDisplayName  = strApprDetailInfos[1];
						
						setOptionDetailInfo(APPROVAL_CABINET, strOptionType, strDisplayName);			
					}
				}		
			}
		}
	}
	
	/**
	 * 결재함 Option 정보 Setting (Docflow Cabinet Option)
	 * @param strDocflowMOption	문서유통함들의 Option 정보
	 */
	public void setOptionDocflowCabinetInfo(String strDocflowMOption)
	{
		String 	strCabinetDelimiter = "^";
		String 	strDetailDelimiter = ":";
		int 	nDetailInfoCount = 2;
		int 	i = 0;

		// 문서 유통함 Option Setting
		if (strDocflowMOption != null && strDocflowMOption.length() > 0)
		{
			String strDocflowOptions[] = DataConverter.splitString(strDocflowMOption, strCabinetDelimiter);
			
			if (strDocflowOptions != null && strDocflowOptions.length > 0)
			{
				for (i = 0 ; i < strDocflowOptions.length ; i++)
				{
					String strDocflowDetailOption = strDocflowOptions[i];
					String strDocflowDetailInfos[] = DataConverter.splitString(strDocflowDetailOption, strDetailDelimiter);
					
					if (strDocflowDetailInfos.length == nDetailInfoCount)
					{
						String strOptionType = strDocflowDetailInfos[0];
						String strDisplayName = strDocflowDetailInfos[1];
						
						setOptionDetailInfo(DOCFLOW_CABINET, strOptionType, strDisplayName);
					}
				}
			}	
		}				
	} 
	
	/**
	 * 결재함 Option 정보 Setting (TDocflow Cabinet Option)
	 * @param strTDocflowMOption	구기록물철 함들의 Option 정보
	 */
	public void setOptionTDocflowCabinetInfo(String strTDocflowMOption)
	{
		String 	strCabinetDelimiter = "^";
		String 	strDetailDelimiter = ":";
		int 	nDetailInfoCount = 2;
		int 	i = 0;
				
		// 구기록물철 Option Setting
		if (strTDocflowMOption != null && strTDocflowMOption.length() > 0)
		{
			String strTDocflowOptions[] = DataConverter.splitString(strTDocflowMOption, strCabinetDelimiter);
			
			if (strTDocflowOptions != null && strTDocflowOptions.length > 0)
			{
				for (i = 0 ; i < strTDocflowOptions.length ; i++)
				{
					String strTDocflowDetailOption = strTDocflowOptions[i];
					String strTDocflowDetailInfos[] = DataConverter.splitString(strTDocflowDetailOption, strDetailDelimiter);
					
					if (strTDocflowDetailInfos.length == nDetailInfoCount)
					{
						String strOptionType = strTDocflowDetailInfos[0];
						String strDisplayName = strTDocflowDetailInfos[1];
						
						setOptionDetailInfo(T_DOCFLOW_CABINET, strOptionType, strDisplayName);
					}
				}
			}	
		}				
	}
	
	/**
	 * 결재함 Option 정보 Setting (PDocflow Cabinet Option)
	 * @param strPDocflowMOption 이관대장 함들의 Option정보
	 */
	public void setOptionPDocflowCabinetInfo(String strPDocflowMOption)
	{
		String strCabinetDelimiter = "^";
		String strDetailDelimiter = ":";
		int    nDetailInfoCount = 2;
		int    i = 0;

		// 이관대장 Option Setting
		if (strPDocflowMOption != null && strPDocflowMOption.length() > 0)
		{
			String strPDocflowOptions[] = DataConverter.splitString(strPDocflowMOption, strCabinetDelimiter);

			if (strPDocflowOptions != null && strPDocflowOptions.length > 0)
			{
				for (i = 0 ; i < strPDocflowOptions.length ; i++)
				{
					String strPDocflowDetailOption = strPDocflowOptions[i];
					String strPDocflowDetailInfos[] = DataConverter.splitString(strPDocflowDetailOption, strDetailDelimiter);
					
					if (strPDocflowDetailInfos.length == nDetailInfoCount)
					{
						String strOptionType = strPDocflowDetailInfos[0];
						String strDisplayName = strPDocflowDetailInfos[1];
						
						setOptionDetailInfo(P_DOCFLOW_CABINET, strOptionType, strDisplayName);
					}
				}
			}
		}
	} 
		
	/**
	 * 옵션 여부에 따라 결재함 생성
	 * @param nGroupType 		전자결재함, 문서유통함 기준 
	 * @param strOptionType 	결재함의 Option Type
	 * @param strDisplayName 	결재함의 Display Name
	 */
	private void setOptionDetailInfo(int nGroupType,
									 String strOptionType,
									 String strDisplayName)
	{
		for (int i = 0; i < m_strOptionType.length ; i++)
		{
			String strFormOptionType = m_strOptionType[i];
			if (strOptionType.compareTo(strFormOptionType)	== 0)
			{
				if (m_nGroupType[i] == nGroupType)
				{
					ApprCabinet apprCabinet = new ApprCabinet(); 
					
					apprCabinet.setCabinetType(i);
					apprCabinet.setDisplay(true);
					apprCabinet.setGroupType(m_nGroupType[i]);
					apprCabinet.setDisplayName(strDisplayName);
					apprCabinet.setDataURL(m_strDataURL[i]);
					apprCabinet.setOptionType(m_strOptionType[i]);
					
					m_lApprCabinetList.add(apprCabinet);						
				}	
			}
		}
	}
	
	/**
	 * 환경설정 정보 Setting 
	 */
	public void appendEnvCabinetInfo()
	{		
		for (int i = 0 ; i < m_strDataURL.length ; i++)
		{
			if (m_nGroupType[i] == GENERAL_CABIENT)
			{
				ApprCabinet apprCabinet = new ApprCabinet();
			
				apprCabinet.setCabinetType(i);
				apprCabinet.setDisplay(true);
				apprCabinet.setGroupType(m_nGroupType[i]);
				apprCabinet.setDisplayName(m_strDisplayName[i]);
				apprCabinet.setDataURL(m_strDataURL[i]);
				apprCabinet.setOptionType(m_strOptionType[i]);
				
				m_lApprCabinetList.add(apprCabinet);
			}
		}	
	}
	
	/**
	 * 결재함 정보를 얻는 함수 
	 * @param nCabinetType 결재함 종류 
	 * @return ApprCabinet
	 */
	public ApprCabinet getApprCabinetbyCabinetType(int nCabinetType)
	{
		ApprCabinet apprCabinet = null;
		
		for (int i = 0 ; i < m_lApprCabinetList.size() ; i++)
		{
			ApprCabinet apprTempCabinet = (ApprCabinet)m_lApprCabinetList.get(i);
			if (apprTempCabinet.getCabinetType() == nCabinetType)
				apprCabinet = apprTempCabinet;	
		}
		
		return apprCabinet;
	}
	
	/**
	 * 부재설정 결재함 Option 정보 Setting 
	 * @param strApprovalSOption    	전자결재함들의 Display Option 정보
	 * @param nType						0 : 전자결재함 
	 * 									1 : 문서유통함
	 */
	public void setOptionSubstituteCabinetInfo(String strApprovalSOption, int nType)
	{
		String 		strCabinetDelimiter = "^";
		int 		i = 0;
		int			j = 0;

		// 부재설정시 디스플레이 되는 함 셋팅
		if (strApprovalSOption != null && strApprovalSOption.length() > 0)
		{
			String 	strApprovalOptions[] = DataConverter.splitString(strApprovalSOption, strCabinetDelimiter);
			
			if (strApprovalOptions != null && strApprovalOptions.length > 0)
			{			
				for (i = 0 ; i < strApprovalOptions.length ; i++)
				{
					String strApprDetailOption = strApprovalOptions[i];
					
                    for (j = 0 ; j < m_strDataURL.length ; j++)
					{
						if (m_nGroupType[j] == nType && m_strOptionType[j].compareTo(strApprDetailOption) == 0)
						{
							m_bSubstituteDisplay[j] = true;	
						}
					}
				}		
			}
		}
		else  // 부재설정 Default : 전자결재, 문서유통 모든함이 출력
		{
			for (i = 0 ; i < m_strDataURL.length ; i++)
			{
				if (m_nGroupType[i] == nType)
				{
					if (nType == APPROVAL_CABINET)
						m_bSubstituteDisplay[i] = true;
					else
						m_bSubstituteDisplay[i] = false;		
				}
			}	
		}
	}
	
	/**
	 * 부재설정시 결재함 Display 정보 반환.
	 * @param nCabinetType 결재함 종류
	 * @return boolean 출력여부
	 */
	public boolean IsDisplaySubstituteCabinet(int nCabinetType)
	{
		return m_bSubstituteDisplay[nCabinetType];
	}
	
	/**
	 * 부재설정시 결재함 그룹별 Display 정보 반환.
	 * @param nCabinetGroupType 결재함그룹 (전자결재, 문서유통)
	 * @return boolean 출력여부
	 */
	public boolean IsDisplaySubstituteCabGroup(int nCabinetGroupType)
	{
		boolean bReturn = false;
		int		nCount = 0;
		int	    i = 0;
			
		for (i = 0 ; i < m_strDataURL.length ; i++)
		{
			if (m_nGroupType[i] == nCabinetGroupType)
			{
				if (m_bSubstituteDisplay[i])
				{
					nCount++;			
				}		
			}
		}
		
		if (nCount > 0)
			bReturn = true;
			
		return bReturn;
	}
}
