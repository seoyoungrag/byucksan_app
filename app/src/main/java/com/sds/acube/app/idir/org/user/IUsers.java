package com.sds.acube.app.idir.org.user;

import java.io.Serializable;
import java.util.*;

/**
 * IUsers.java
 * 2003-04-29
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class IUsers implements Serializable  
{
	private LinkedList  m_lUserList = null;
	private int			m_nSearchTotalCount = 0;

	public IUsers()
	{
		m_lUserList = new LinkedList();
	}
	
	/**
	 * List에 User를 더함.
	 * @param Employee 결재자 한 명의 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(IUser iUser)
	{
		return m_lUserList.add(iUser);
	}
	
	/**
	 * 사용자 List의 Size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lUserList.size();
	}
	
	/**
	 * 사용자 검색시 총계 설정
	 * @param nSearchTotalCount 
	 */
	public void setSearchTotalCount(int nSearchTotalCount)
	{
		m_nSearchTotalCount = nSearchTotalCount;	
	}
	
	/**
	 * 사용자 검색시 총계 반환
	 * @return int
	 */
	public int getSearchTotalCount()
	{
		return m_nSearchTotalCount;
	}
	
	/**
	 * 사용자 한명의 정보
	 * @param  사용자 index
	 * @return IUser
	 */
	public IUser get(int nIndex)
	{
		return (IUser)m_lUserList.get(nIndex);
	}
		
	/**
	 * 겸직 여부 반환.
	 * @param  사용자 index
	 * @return boolean
	 */
	public boolean isConcurrent(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.isConcurrent();
	}

	/**
	 * 파견 여부 반환.
	 * @param  사용자 index
	 * @return boolean
	 */
	public boolean isDelegate(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.isDelegate();
	}

	/**
	 * 삭제 여부 반환.
	 * @param  사용자 index
	 * @return boolean
	 */
	public boolean isDeleted(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.isDeleted();
	}

	/**
	 * 실사용자 여부 반환.
	 * @param  사용자 index
	 * @return boolean
	 */
	public boolean isExistence(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.isExistence();
	}

	/**
	 * 파견 여부 반환.
	 * @param  사용자 index
	 * @return boolean
	 */
	public boolean isProxy(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.isProxy();
	}

	/**
	 * 직급 순서 반환.
	 * @param  사용자 index
	 * @return int
	 */
	public int getGradeOrder(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGradeOrder();
	}

	/**
	 * 직위 순서 반환.
	 * @param  사용자 index
	 * @return int
	 */
	public int getPositionOrder(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPositionOrder();
	}

	/**
	 * 보안 등급 반환.
	 * @param  사용자 index
	 * @return int
	 */
	public int getSecurityLevel(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getSecurityLevel();
	}

	/**
	 * 직책 순서 반환.
	 * @param  사용자 index
	 * @return int
	 */
	public int getTitleOrder(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getTitleOrder();
	}

	/**
	 * 사용자 순서 반환.
	 * @param  사용자 index
	 * @return int
	 */
	public int getUserOrder(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserOrder();
	}

	/**
	 * 회사 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getCompID();
	}

	/**
	 * 회사명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getCompName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getCompName();
	}

	/**
	 * 부서 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDeptID();
	}

	/**
	 * 부서명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getDeptName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDeptName();
	}

	/**
	 * 설명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getDescription(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDescription();
	}

	/**
	 * 업무 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getDuty(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDuty();
	}

	/**
	 * 개인 이메일 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getEmail(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getEmail();
	}

	/**
	 * 사번 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getEmployeeID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getEmployeeID();
	}

	/**
	 * 직급 약어 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGradeAbbrName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGradeAbbrName();
	}

	/**
	 * 직급 코드 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGradeCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGradeCode();
	}

	/**
	 * 직급명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGradeName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGradeName();
	}

	/**
	 * 타언어 직급명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGradeOtherName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGradeOtherName();
	}

	/**
	 * 그룹 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGroupID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGroupID();
	}

	/**
	 * 그룹 명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getGroupName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getGroupName();
	}

	/**
	 * 집주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeAddr(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeAddr();
	}

	/**
	 * 집 상세 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeDetailAddr(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeDetailAddr();
	}

	/**
	 * 집 팩스 번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeFax(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeFax();
	}

	/**
	 * 홈 페이지 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomePage(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomePage();
	}

	/**
	 * 집 전화번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeTel(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeTel();
	}

	/**
	 * 집 전화번호2 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeTel2(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeTel2();
	}

	/**
	 * 집 우편 번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getHomeZipCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getHomeZipCode();
	}

	/**
	 * 핸드폰 번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getMobile(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getMobile();
	}

	/**
	 * 핸드폰 번호2 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getMobile2(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getMobile2();
	}

	/**
	 * 사무실 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeAddr(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeAddr();
	}

	/**
	 * 사무실 상세 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeDetailAddr(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeDetailAddr();
	}

	/**
	 * 사무실 팩스 번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeFax(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeFax();
	}

	/**
	 * 사무실 전화번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeTel(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeTel();
	}

	/**
	 * 사무실 전화번호2 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeTel2(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeTel2();
	}

	/**
	 * 사무실 우편번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOfficeZipCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOfficeZipCode();
	}

	/**
	 * 기관명 포함 조직명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getOrgDisplayName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getOrgDisplayName();
	}

	/**
	 * 호출기 번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPager(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPager();
	}

	/**
	 * 파트 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPartID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPartID();
	}

	/**
	 * 파트 명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPartName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPartName();
	}

	/**
	 * PC 통신 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPCOnlineID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPCOnlineID();
	}

	/**
	 * 직위 약어 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPositionAbbrName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPositionAbbrName();
	}

	/**
	 * 직위 코드 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPositionCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPositionCode();
	}

	/**
	 * 직위명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPositionName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPositionName();
	}

	/**
	 * 타언어 직위명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getPositionOtherName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getPositionOtherName();
	}

	/**
	 * 예약필드1 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getReserved1(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getReserved1();
	}

	/**
	 * 예약필드2 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getReserved2(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getReserved2();
	}

	/**
	 * 예약필드3 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getReserved3(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getReserved3();
	}

	/**
	 * 주민등록번호 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getResidentNo(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getResidentNo();
	}

	/**
	 * Role Code 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getRoleCodes(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getRoleCodes();
	}

	/**
	 * 메일 주소 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getSysMail(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getSysMail();
	}

	/**
	 * 직책 코드 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getTitleCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getTitleCode();
	}

	/**
	 * 직책명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getTitleName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getTitleName();
	}

	/**
	 * 타언어 직책명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getTitleOtherName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getTitleOtherName();
	}

	/**
	 * 사용자 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserID();
	}

	/**
	 * 사용자명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserName();
	}

	/**
	 * 타언어 사용자명 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserOtherName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserOtherName();
	}

	/**
	 * 실사용자 ID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserRID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserRID();
	}

	/**
	 * 사용자 상태 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserStatus(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserStatus();
	}

	/**
	 * 사용자 UID 반환.
	 * @param  사용자 index
	 * @return String
	 */
	public String getUserUID(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getUserStatus();
	}
	
	/**
	 * 마지막 암호 변경일 반환
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getChangedPWDDate(int nIndex)
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getChangedPWDDate();
	}
	
	/**
	 * 로그인 결과 반환
	 * @param nIndex 사용자 index
	 * @return int
	 */
	public int getLoginResult(int nIndex)
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getLoginResult();
	}
	
	/**
	 * 직무 순서 반환.
	 * @param nIndex 사용자 index
	 * @return int
	 */
	public int getDutyOrder(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDutyOrder();
	}

	/**
	 * 직무 코드 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getDutyCode(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDutyCode();
	}

	/**
	 * 직무 명 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getDutyName(int nIndex) 
	{
		IUser iUser = (IUser)m_lUserList.get(nIndex);
		return iUser.getDutyName();
	}
	
	/**
	 * 상세 직위 반환.
	 * @param nIndex 사용자 index
	 * @return String
	 */
	public String getOptionalGTPName(int nIndex) 
	{
		IUser iUser = (IUser) m_lUserList.get(nIndex);
		return iUser.getOptionalGTPName();
	}
}
