package com.sds.acube.app.idir.org.user;

/**
 * UserBasic.java
 * 2002-11-18
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class UserBasics 
{
	private LinkedList m_lUserBasicList = null;
	
	public UserBasics()
	{
		m_lUserBasicList = new LinkedList();
	}
	
	/**
	 * List에 UserBasic을 더함.
	 * @param UserBasic 사용자 한 명의 기본 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(UserBasic userBasic)
	{
		return m_lUserBasicList.add(userBasic);
	}
	
	/**
	 * UserBasic 리스트의 size
	 * @return int 결재자의 수
	 */ 
	public int size()
	{
		return m_lUserBasicList.size();
	}
	
	/**
	 * 사용자 한명의 정보
	 * @param  nIndex   사용자 index
	 * @return Employee 결재자 정보 
	 */
	public UserBasic get(int nIndex)
	{
		return (UserBasic)m_lUserBasicList.get(nIndex);
	}
	
	/**
	 * 겸직 여부 반환.
	 * @param nIndex 사용자 Index
	 * @return int
	 */
	public int getIsConcurrent(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getIsConcurrent();
	}

	/**
	 * 파견 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getIsDelegate(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getIsDelegate();
	}

	/**
	 * 삭제 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getIsDeleted(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getIsDeleted();
	}

	/**
	 * 실사용자 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getIsExistence(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getIsExistence();
	}

	/**
	 * 직무 대리 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getIsProxy(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getIsProxy();
	}

	/**
	 * 보안 둥급 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getSecurityLevel(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getSecurityLevel();
	}

	/**
	 * 정렬 순서 반환.
	 * @param  nIndex   사용자 index
	 * @return int
	 */
	public int getUserOrder(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserOrder();
	}

	/**
	 * 사용자 Role 반환.
	 * @param  nIndex   사용자 index
	 * @return RoleCodes
	 */
	public RoleCodes getRoleCodes(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getRoleCodes();
	}

	/**
	 * 서버정보 반환.
	 * @param  nIndex   사용자 index
	 * @return Servers
	 */
	public Servers getServers(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getServers();
	}

	/**
	 * 회사 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getCompID();
	}

	/**
	 * 회사명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getCompName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getCompName();
	}

	/**
	 * 부서 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDeptID();
	}

	/**
	 * 부서명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getDeptName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDeptName();
	}

	/**
	 * Description 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDescription();
	}

	/**
	 * 사번 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getEmployeeID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getEmployeeID();
	}

	/**
	 * 직급 코드 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getGradeCode(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getGradeCode();
	}

	/**
	 * 그룹 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getGroupID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getGroupID();
	}

	/**
	 * 그룹명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getGroupName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getGroupName();
	}

	/**
	 * 기관명 포함 조직명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getOrgDisplayName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getOrgDisplayName();
	}

	/**
	 * 파트 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getPartID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getPartID();
	}

	/**
	 * 파트명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getPartName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getPartName();
	}

	/**
	 * 직위 코드 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getPositionCode(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getPositionCode();
	}

	/**
	 * Reserved1 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getReserved1(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getReserved1();
	}

	/**
	 * Reserved2 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getReserved2(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getReserved2();
	}

	/**
	 * Reserved3 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getReserved3(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getReserved3();
	}

	/**
	 * 주민번호 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getResidentNo(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getResidentNo();
	}

	/**
	 * 메일 주소 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getSysMail(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getSysMail();
	}

	/**
	 * 직책 코드 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getTitleCode(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getTitleCode();
	}

	/**
	 * 사용자 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserID();
	}

	/**
	 * 사용자 명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserName();
	}

	/**
	 * 타언어권 사용자명 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserOtherName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserOtherName();
	}

	/**
	 * 실사용자 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserRID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserRID();
	}

	/**
	 * 사용자 UID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getUserUID();
	}
	
	/**
	 * 사용자 통합직함명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getOptionalGTPName(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getOptionalGTPName();
	}
	
	/**
	 * 트리 디스플레이 순서 반환.
	 * @param nIndex 사용자 index
	 * @return int
	 */
	public int getDisplayOrder(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDisplayOrder();
	}
	
	/**
	 * 트리에서 펼져지는 사용자 반환.
	 * @param nIndex 사용자 index
	 * @return int
	 */
	public int getDefaultUser(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDefaultUser();
	}
	
	/**
	 * 타언어 그룹명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getGroupOtherName(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getGroupOtherName();
	}
	
	/**
	 * 타언어 회사명 반환.
	 * @param nIndex 사용자 index
	 * @param String
	 */
	public String getCompOtherName(int nIndex) 
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getCompOtherName();
	}
	
	/**
	 * 타언어 부서명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getDeptOtherName(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getDeptOtherName();
	}
	
	/**
	 * 타언어 파트명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getPartOtherName(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getPartOtherName();
	}
	
	/**
	 * 타언어 기관명 포함 조직명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getOrgDisplayOtherName(int nIndex)
	{
		UserBasic userBasic = (UserBasic)m_lUserBasicList.get(nIndex);
		return userBasic.getOrgDisplayOtherName();
	}
}
