package com.sds.acube.app.idir.org.bpm;

import java.sql.ResultSet;
import java.util.StringTokenizer;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.db.DataHandler;

//TODO TCN_BPM_GROUP_MEMBER 위치
public class MemberHandler extends DataHandler {
	
	public MemberHandler(ConnectionParam connectionParam)	{
		super(connectionParam);
	}	

	public Members getMembers(String GROUP_ID, String IS_DELETED) {
        Members members = null;
		boolean	bResult = false;
		String 		strQuery = "";
		
		final int DB_TYPE_ORACLE = 0;
		final int DB_TYPE_MSSQL = 1;
		final int DB_TYPE_DB2 = 2; 
		final int DB_TYPE_ALTIBASE = 3; 
		final int DB_TYPE_SYBASE = 4; 

		
        bResult = m_connectionBroker.openConnection();
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker(); 
            return null;
        }

        strQuery = " SELECT MEMBER.GROUP_ID, \n"
            +  "      USERINFO.USER_ID, USERINFO.USER_NAME, USERINFO.USER_OTHER_NAME, USERINFO.USER_UID, \n"
            +  "      USERINFO.COMP_ID, USERINFO.COMP_NAME, USERINFO.DEPT_ID, USERINFO.DEPT_NAME, USERINFO.PART_ID, \n"
            +  "      USERINFO.PART_NAME, USERINFO.ORG_DISPLAY_NAME, USERINFO.GRADE_CODE, USERINFO.TITLE_CODE, \n"
            +  "      USERINFO.POSITION_CODE, USERINFO.USER_ORDER, USERINFO.SECURITY_LEVEL, USERINFO.ROLE_CODE, \n"
            +  "      USERINFO.RESIDENT_NO, USERINFO.EMPLOYEE_ID, USERINFO.SYSMAIL, USERINFO.SERVERS, USERINFO.IS_CONCURRENT, \n"
            +  "      USERINFO.IS_PROXY, USERINFO.IS_DELEGATE, USERINFO.IS_EXISTENCE, USERINFO.USER_RID, USERINFO.IS_DELETED, \n"
            +  "      USERINFO.DESCRIPTION, USERINFO.RESERVED1, USERINFO.RESERVED2, USERINFO.RESERVED3, USERINFO.OPTIONAL_GTP_NAME, \n"
            +  "      USERINFO.DISPLAY_ORDER, USERINFO.DEFAULT_USER, USERINFO.CERTIFICATION_ID, USERINFO.DUTY_CODE, \n"
            +  "      ADDR.EMAIL, ADDR.DUTY, ADDR.PCONLINE_ID, ADDR.HOMEPAGE, ADDR.OFFICE_TEL, ADDR.OFFICE_TEL2, ADDR.OFFICE_ADDR,  \n"
            +  "      ADDR.OFFICE_DETAIL_ADDR, ADDR.OFFICE_ZIPCODE, ADDR.OFFICE_FAX, ADDR.MOBILE, ADDR.MOBILE2, ADDR.PAGER,  \n"
            +  "      ADDR.HOME_ADDR, ADDR.HOME_DETAIL_ADDR, ADDR.HOME_ZIPCODE, ADDR.HOME_TEL, ADDR.HOME_TEL2, ADDR.HOME_FAX,  \n"
            +  "      GRADE.GRADE_NAME, GRADE.GRADE_OTHER_NAME, \n"
            +  "      ORG.ORG_OTHER_NAME, ORG2.ORG_OTHER_NAME COMP_OTHER_NAME, \n"
            +  "      POSITION.POSITION_OTHER_NAME \n";

            if (super.m_nDBType == DB_TYPE_ORACLE) {  
            	strQuery +=  " FROM TCN_USERINFORMATION_BASIC USERINFO, TCN_GRADEINFORMATION GRADE, TCN_BPM_GROUP_MEMBER MEMBER, \n"
                +  "      TCN_USERINFORMATION_ADDR ADDR, TCN_ORGANIZATIONINFORMATION ORG, TCN_ORGANIZATIONINFORMATION ORG2, \n"
                +  "      TCN_POSITIONINFORMATION POSITION \n"
                +  " WHERE " +
                        "MEMBER.GROUP_ID = '" + GROUP_ID + "' AND USERINFO.USER_UID = MEMBER.USER_UID \n"
                +  "      AND GRADE.GRADE_ID(+) = USERINFO.GRADE_CODE AND  USERINFO.USER_UID = ADDR.USER_ID \n"
                +  "      AND USERINFO.DEPT_ID = ORG.ORG_ID AND ORG2.ORG_ID(+) = USERINFO.COMP_ID \n";
                
            } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
            	strQuery +=  " FROM TCN_USERINFORMATION_BASIC USERINFO, TCN_GRADEINFORMATION GRADE, TCN_BPM_GROUP_MEMBER MEMBER, \n"
                    +  "      TCN_USERINFORMATION_ADDR ADDR, TCN_ORGANIZATIONINFORMATION ORG, TCN_ORGANIZATIONINFORMATION ORG2, \n"
                    +  "      TCN_POSITIONINFORMATION POSITION \n"
                    +  " WHERE " +
                            "MEMBER.GROUP_ID = '" + GROUP_ID + "' AND USERINFO.USER_UID = MEMBER.USER_UID \n"
                    +  "      AND GRADE.GRADE_ID =* USERINFO.GRADE_CODE AND  USERINFO.USER_UID = ADDR.USER_ID \n"
                    +  "      AND USERINFO.DEPT_ID = ORG.ORG_ID AND ORG2.ORG_ID =* USERINFO.COMP_ID \n";
            }else if(super.m_nDBType == DB_TYPE_DB2){
                strQuery += "FROM TCN_USERINFORMATION_BASIC USERINFO \n"
                    +  "	INNER JOIN TCN_BPM_GROUP_MEMBER MEMBER \n"
                    +  "	ON  USERINFO.USER_UID = MEMBER.USER_UID"
                    +  "	LEFT OUTER JOIN TCN_GRADEINFORMATION GRADE"
                    +  "    ON USERINFO.GRADE_CODE = GRADE.GRADE_ID"
                    +  "	INNER JOIN TCN_USERINFORMATION_ADDR ADDR"
                    +  "	ON USERINFO.USER_UID = ADDR.USER_ID"
                    +  "	INNER JOIN TCN_ORGANIZATIONINFORMATION ORG"
                    +  "  	ON USERINFO.DEPT_ID = ORG.ORG_ID"
                    +  "	LEFT OUTER JOIN TCN_ORGANIZATIONINFORMATION ORG2"
                    +  "	ON USERINFO.COMP_ID = ORG2.ORG_ID ";
            }else if(super.m_nDBType == DB_TYPE_ALTIBASE){
                strQuery += "FROM TCN_USERINFORMATION_BASIC USERINFO \n"
                    +  "	INNER JOIN TCN_BPM_GROUP_MEMBER MEMBER \n"
                    +  "	ON  USERINFO.USER_UID = MEMBER.USER_UID"
                    +  "	LEFT OUTER JOIN TCN_GRADEINFORMATION GRADE"
                    +  "    ON USERINFO.GRADE_CODE = GRADE.GRADE_ID"
                    +  "	INNER JOIN TCN_USERINFORMATION_ADDR ADDR"
                    +  "	ON USERINFO.USER_UID = ADDR.USER_ID"
                    +  "	INNER JOIN TCN_ORGANIZATIONINFORMATION ORG"
                    +  "  	ON USERINFO.DEPT_ID = ORG.ORG_ID"
                    +  "	LEFT OUTER JOIN TCN_ORGANIZATIONINFORMATION ORG2"
                    +  "	ON USERINFO.COMP_ID = ORG2.ORG_ID ";                    
            }
        if (IS_DELETED.equals("0")) {
        	strQuery +=  "      AND USERINFO.IS_DELETED = '0' \n";
        }
        if (super.m_nDBType == DB_TYPE_ORACLE) {
            strQuery +=  "      AND POSITION.POSITION_ID(+) = USERINFO.POSITION_CODE \n"
                +  " ORDER BY USERINFO.USER_NAME \n";
            
        } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
            strQuery +=  "      AND POSITION.POSITION_ID =* USERINFO.POSITION_CODE \n"
                +  " ORDER BY USERINFO.USER_NAME \n";
        } else if (super.m_nDBType == DB_TYPE_DB2){
            strQuery +=  "      LEFT OUTER JOIN TCN_POSITIONINFORMATION POSITION ON USERINFO.POSITION_CODE =POSITION.POSITION_ID"
                +  " ORDER BY USERINFO.USER_NAME \n";   
        } else if (super.m_nDBType == DB_TYPE_ALTIBASE){
            strQuery +=  "      LEFT OUTER JOIN TCN_POSITIONINFORMATION POSITION ON USERINFO.POSITION_CODE =POSITION.POSITION_ID"
                +  " ORDER BY USERINFO.USER_NAME \n"; 
        }
        
//		System.out.println(" getMembers ==> \n" + strQuery);
        bResult = m_connectionBroker.executeQuery(strQuery);
        if(!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker(); 
            return null;
        }
        members = processData(m_connectionBroker.getResultSet());
        m_connectionBroker.clearConnectionBroker();
        return members;
	}
    


    public boolean insertMember(String GROUP_ID, String USER_UID_LIST) {
        boolean     bReturn = false;
        String      strQuery = "";
        boolean     bResult = false;
        int         nResult = -1;
        
        bResult = m_connectionBroker.openConnection();
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker(); 
            return bResult;
        }
        
        strQuery = "DELETE TCN_BPM_GROUP_MEMBER WHERE GROUP_ID = '" + GROUP_ID + "' ";
        //System.out.println("insertMember ==> " + strQuery);
        m_connectionBroker.setAutoCommit(false);
        nResult = m_connectionBroker.executeUpdate(strQuery);
        StringTokenizer list = new StringTokenizer(USER_UID_LIST, "|");
        while(list.hasMoreTokens()) {
            strQuery = "INSERT INTO TCN_BPM_GROUP_MEMBER (GROUP_ID, USER_UID) VALUES('"
                + GROUP_ID + "', '" + list.nextToken() + "' )";
            //System.out.println("insertMember ==> " + strQuery);
            nResult = m_connectionBroker.executeUpdate(strQuery);
        }
        if(nResult < 0) {
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

    public boolean deleteMember(String GROUP_ID) {
        boolean     bReturn = false;
        String      strQuery = "";
        int         nResult = -1;
        
        bReturn = m_connectionBroker.openConnection();
        if (!bReturn) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker(); 
            return bReturn;
        }
        strQuery = "DELETE TCN_BPM_GROUP_MEMBER WHERE GROUP_ID = '" + GROUP_ID + "' ";

        m_connectionBroker.setAutoCommit(false);
        nResult = m_connectionBroker.executeUpdate(strQuery);
        if(nResult < 0) {
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
	 * ResultSet을 Data Linked List로 변환 
	 * @param resultSet Query 실행 결과 
	 * @return Department
	 */
	private Members processData(ResultSet resultSet) {
        Members  	members = null;
		
		if (resultSet == null) {
			m_lastError.setMessage("NullPoint ResultSet.", "DepartmentHandler.processData", "");
			return null;
		}
		
        members = new Members();
		
		try {
			while(resultSet.next()) {
				Member member = new Member();

                member.setGROUP_ID           (getString(resultSet, "GROUP_ID"));
                member.setUSER_ID            (getString(resultSet, "USER_ID"));
                member.setUSER_NAME          (getString(resultSet, "USER_NAME"));
                member.setUSER_OTHER_NAME    (getString(resultSet, "USER_OTHER_NAME"));
                member.setUSER_UID           (getString(resultSet, "USER_UID"));
                member.setCOMP_ID            (getString(resultSet, "COMP_ID"));
                member.setCOMP_NAME          (getString(resultSet, "COMP_NAME"));
                member.setCOMP_OTHER_NAME    (getString(resultSet, "COMP_OTHER_NAME"));
                member.setDEPT_ID            (getString(resultSet, "DEPT_ID"));
                member.setDEPT_NAME          (getString(resultSet, "DEPT_NAME"));
                member.setPART_ID            (getString(resultSet, "PART_ID"));
                member.setPART_NAME          (getString(resultSet, "PART_NAME"));
                member.setORG_DISPLAY_NAME   (getString(resultSet, "ORG_DISPLAY_NAME"));
                member.setGRADE_CODE         (getString(resultSet, "GRADE_CODE"));
                member.setTITLE_CODE         (getString(resultSet, "TITLE_CODE"));
                member.setPOSITION_CODE      (getString(resultSet, "POSITION_CODE"));
                member.setUSER_ORDER         (getString(resultSet, "USER_ORDER"));
                member.setSECURITY_LEVEL     (getString(resultSet, "SECURITY_LEVEL"));
                member.setROLE_CODE          (getString(resultSet, "ROLE_CODE"));
                member.setRESIDENT_NO        (getString(resultSet, "RESIDENT_NO"));
                member.setEMPLOYEE_ID        (getString(resultSet, "EMPLOYEE_ID"));
                member.setSYSMAIL            (getString(resultSet, "SYSMAIL"));
                member.setSERVERS            (getString(resultSet, "SERVERS"));
                member.setIS_CONCURRENT      (getString(resultSet, "IS_CONCURRENT"));
                member.setIS_PROXY           (getString(resultSet, "IS_PROXY"));
                member.setIS_DELEGATE        (getString(resultSet, "IS_DELEGATE"));
                member.setIS_EXISTENCE       (getString(resultSet, "IS_EXISTENCE"));
                member.setUSER_RID           (getString(resultSet, "USER_RID"));
                member.setIS_DELETED         (getString(resultSet, "IS_DELETED"));
                member.setDESCRIPTION        (getString(resultSet, "DESCRIPTION"));
                member.setRESERVED1          (getString(resultSet, "RESERVED1"));
                member.setRESERVED2          (getString(resultSet, "RESERVED2"));
                member.setRESERVED3          (getString(resultSet, "RESERVED3"));
                member.setOPTIONAL_GTP_NAME  (getString(resultSet, "OPTIONAL_GTP_NAME"));
                member.setDISPLAY_ORDER      (getString(resultSet, "DISPLAY_ORDER"));
                member.setDEFAULT_USER       (getString(resultSet, "DEFAULT_USER"));
                member.setCERTIFICATION_ID   (getString(resultSet, "CERTIFICATION_ID"));
                member.setDUTY_CODE          (getString(resultSet, "DUTY_CODE"));
                member.setGRADE_NAME         (getString(resultSet, "GRADE_NAME"));
                member.setGRADE_OTHER_NAME   (getString(resultSet, "GRADE_OTHER_NAME"));
                member.setORG_OTHER_NAME     (getString(resultSet, "ORG_OTHER_NAME"));
                member.setPOSITION_OTHER_NAME(getString(resultSet, "POSITION_OTHER_NAME"));
                member.setEMAIL              (getString(resultSet, "EMAIL"));
                member.setDUTY               (getString(resultSet, "DUTY"));
                member.setPCONLINE_ID        (getString(resultSet, "PCONLINE_ID"));
                member.setHOMEPAGE           (getString(resultSet, "HOMEPAGE"));
                member.setOFFICE_TEL         (getString(resultSet, "OFFICE_TEL"));
                member.setOFFICE_TEL2        (getString(resultSet, "OFFICE_TEL2"));
                member.setOFFICE_ADDR        (getString(resultSet, "OFFICE_ADDR"));
                member.setOFFICE_DETAIL_ADDR (getString(resultSet, "OFFICE_DETAIL_ADDR"));
                member.setOFFICE_ZIPCODE     (getString(resultSet, "OFFICE_ZIPCODE"));
                member.setOFFICE_FAX         (getString(resultSet, "OFFICE_FAX"));
                member.setMOBILE             (getString(resultSet, "MOBILE"));
                member.setMOBILE2            (getString(resultSet, "MOBILE2"));
                member.setPAGER              (getString(resultSet, "PAGER"));
                member.setHOME_ADDR          (getString(resultSet, "HOME_ADDR"));
                member.setHOME_DETAIL_ADDR   (getString(resultSet, "HOME_DETAIL_ADDR"));
                member.setHOME_ZIPCODE       (getString(resultSet, "HOME_ZIPCODE"));
                member.setHOME_TEL           (getString(resultSet, "HOME_TEL"));
                member.setHOME_TEL2          (getString(resultSet, "HOME_TEL2"));
                member.setHOME_FAX           (getString(resultSet, "HOME_FAX"));
                member.setTOTAL_COUNT		 (getInt(resultSet, "TOTAL_COUNT"));

                members.add(member);
			}
		} catch(Exception e) {
			m_lastError.setMessage("Fail to process department list.", "DepartmentHandler.processData", e.getMessage());
			return null;
		}
		
		return members;				
	} 
	
}
