package com.sds.acube.app.idir.org.bpm;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.db.DataHandler;
import com.sds.acube.app.idir.org.user.UserAssociatedTableMap;

//TODO tCN_BPM_GROUP의 위치
public class GroupHandler extends DataHandler {

    private String m_strDepartmentColumns = "";
    private String m_strDepartmentTable = "";
    
	final static int DB_TYPE_ORACLE = 0;
	final static int DB_TYPE_MSSQL = 1;
	final static int DB_TYPE_DB2 = 2; 
	final static int DB_TYPE_ALTIBASE = 3; 
	final static int DB_TYPE_SYBASE = 4; 

    public GroupHandler(ConnectionParam connectionParam) {
        super(connectionParam);
        m_strDepartmentTable = "TCN_BPM_GROUP";
        m_strDepartmentColumns = " GROUP_ID, GROUP_NAME, GROUP_OTHER_NAME, GROUP_TYPE, DEPT_ID, MAKE_USER_UID, MAKE_DATE ";
    }

    public Groups getGroups(int nPage, int nRowPerPage, String GROUP_TYPE, String strSearch, String strLanguageType,
            String uid, String DEPT_ID) {
        ResultSet resultSet = null;
        Groups groups = null;
        boolean bResult = false;
        String strQuery = "";
       

        if (nPage < 1) {
            nPage = 1;
        }
        if (nRowPerPage < 1) {
            nRowPerPage = 10;
        }

        int nFirst = (nPage - 1) * nRowPerPage + 1;
        int nLast = nPage * nRowPerPage;

        bResult = m_connectionBroker.openConnection();
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        if (strLanguageType == null || strLanguageType.equals("") || strLanguageType.equals("ko")) {
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, \n"
                    + "     BPM.MAKE_USER_UID, USERINFO.USER_NAME MAKE_USER_NAME, \n"
                    + "     USERINFO.DEPT_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE \n";
            if (super.m_nDBType == DB_TYPE_ORACLE) {
            	strQuery += " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO \n"
                + " WHERE USERINFO.USER_UID(+) = BPM.MAKE_USER_UID \n";
            } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
            	strQuery += " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO \n"
                + " WHERE USERINFO.USER_UID =* BPM.MAKE_USER_UID \n";  
            } else if(super.m_nDBType == DB_TYPE_DB2){
            	strQuery += " FROM TCN_BPM_GROUP BPM LEFT OUTER JOIN TCN_USERINFORMATION_BASIC USERINFO \n" 
            	+"ON BPM.MAKE_USER_UID = USERINFO.USER_UID \n";	
            } else if(super.m_nDBType == DB_TYPE_ALTIBASE){
            	strQuery += " FROM TCN_BPM_GROUP BPM LEFT OUTER JOIN TCN_USERINFORMATION_BASIC USERINFO \n" 
            	+"ON BPM.MAKE_USER_UID = USERINFO.USER_UID \n";	
            } 
            strQuery += "       AND BPM.GROUP_TYPE = '" + GROUP_TYPE + "' \n";
        } else {
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, \n"
                    + "     BPM.MAKE_USER_UID, USERINFO.USER_OTHER_NAME MAKE_USER_NAME, \n"
                    + "     ORG.ORG_OTHER_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE \n";
                    if (super.m_nDBType == DB_TYPE_ORACLE) {
                    	strQuery += " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO, TCN_ORGANIZATIONINFORMATION ORG \n"
                        + " WHERE USERINFO.USER_UID(+) = BPM.MAKE_USER_UID \n";
                    } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
                    	strQuery += " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO, TCN_ORGANIZATIONINFORMATION ORG \n"                   			 
                        + " WHERE USERINFO.USER_UID =* BPM.MAKE_USER_UID \n"; 
                    } else if(super.m_nDBType == DB_TYPE_DB2){
                    	strQuery += "FROM TCN_BPM_GROUP BPM LEFT OUTER JOIN TCN_USERINFORMATION_BASIC USERINFO, TCN_ORGANIZATIONINFORMATION ORG \n"
                    	+ " ON USERINFO.USER_UID = BPM.MAKE_USER_UID \n";
                    } else if(super.m_nDBType == DB_TYPE_ALTIBASE){
                    	strQuery += "FROM TCN_BPM_GROUP BPM LEFT OUTER JOIN TCN_USERINFORMATION_BASIC USERINFO, TCN_ORGANIZATIONINFORMATION ORG \n"
                    	+ " ON USERINFO.USER_UID = BPM.MAKE_USER_UID \n";
                    } 
                    strQuery += "       AND USERINFO.DEPT_ID = ORG.ORG_ID\n"
                        + "       AND BPM.GROUP_TYPE = '" + GROUP_TYPE + "' \n";
        }
        if (GROUP_TYPE != null && GROUP_TYPE.equals("PERSONAL")) {
            strQuery = strQuery + "     AND BPM.MAKE_USER_UID = '" + uid + "' \n";
        }
        if (GROUP_TYPE != null && GROUP_TYPE.equals("DEPT")) {
            strQuery = strQuery + "     AND BPM.DEPT_ID = '" + DEPT_ID + "' \n";
        }
        if (strSearch != null && !strSearch.equals("")) {
            strQuery = strQuery + "     AND BPM.GROUP_NAME LIKE '%" + strSearch + "%'";
        }

        if (super.m_nDBType == DB_TYPE_ORACLE) {
            strQuery += " ORDER BY BPM.GROUP_NAME \n";
            strQuery = "SELECT QUE.*,  QUE_TOT.TOTAL_COUNT TOTAL_COUNT \n" + " FROM \n"
                + "     (SELECT TBASETABLE_.*, ROWNUM ROWCOUNT_ from \n" + "             ( " + strQuery
                + " ) TBASETABLE_ ) QUE, \n" + "     (SELECT COUNT(*) TOTAL_COUNT FROM \n" + "             ( "
                + strQuery + " ) ) QUE_TOT \n" + " WHERE QUE.ROWCOUNT_ BETWEEN " + nFirst + " AND " + nLast;
        } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
            int nTopCount = nPage * nRowPerPage;
            strQuery = "SELECT QUE.*,  QUE_TOT.TOTAL_COUNT TOTAL_COUNT FROM "
                        + " (SELECT TOP " + nTopCount + " TBASETABLE_.* from ( " + strQuery + " ) TBASETABLE_ ORDER BY TBASETABLE_.GROUP_NAME) QUE, "
                        + " (SELECT COUNT(*) TOTAL_COUNT FROM ( " + strQuery + " ) QUE_COUNT ) QUE_TOT";
        } else if(super.m_nDBType == DB_TYPE_DB2){
            strQuery += " ORDER BY BPM.GROUP_NAME \n";
            strQuery = "SELECT QUE.*,  QUE_TOT.TOTAL_COUNT TOTAL_COUNT \n" + " FROM \n"
                + "     (SELECT TBASETABLE_.*, ROWNUM ROWCOUNT_ from \n" + "             ( " + strQuery
                + " ) TBASETABLE_ ) QUE, \n" + "     (SELECT COUNT(*) TOTAL_COUNT FROM \n" + "             ( "
                + strQuery + " ) ) QUE_TOT \n" + " WHERE QUE.ROWCOUNT_ BETWEEN " + nFirst + " AND " + nLast;
        } else if(super.m_nDBType == DB_TYPE_ALTIBASE){
            strQuery += " ORDER BY BPM.GROUP_NAME \n";
            strQuery = "SELECT QUE.*,  QUE_TOT.TOTAL_COUNT TOTAL_COUNT \n" + " FROM \n"
                + "     (SELECT TBASETABLE_.*, ROWNUM ROWCOUNT_ from \n" + "             ( " + strQuery
                + " ) TBASETABLE_ ) QUE, \n" + "     (SELECT COUNT(*) TOTAL_COUNT FROM \n" + "             ( "
                + strQuery + " ) ) QUE_TOT \n" + " WHERE QUE.ROWCOUNT_ BETWEEN " + nFirst + " AND " + nLast;
        }
        bResult = m_connectionBroker.executeQuery(strQuery);

        try {
            resultSet = super.m_connectionBroker.getResultSet();
            if (super.m_nDBType == 1 && nPage > 1 && resultSet != null) {
                resultSet.absolute((nPage-1) * nRowPerPage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!bResult) {
            System.out.println("\n bResult == false ");
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        groups = processData(resultSet);
        m_connectionBroker.clearConnectionBroker();
        return groups;
    }

    public Group getGroup(String GROUP_ID) {
        Groups groups = null;
        Group group = null;
        boolean bResult = false;
        String strQuery = "";
        int nSize = 0;

        bResult = m_connectionBroker.openConnection();
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }

        // strQuery = "SELECT " + m_strDepartmentColumns +
        // " FROM " + m_strDepartmentTable +
        // " WHERE GROUP_ID = '" + GROUP_ID + "'";
        if (super.m_nDBType == DB_TYPE_ORACLE) {
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, "
                + "     BPM.MAKE_USER_UID, USERINFO.USER_NAME MAKE_USER_NAME, USERINFO.DEPT_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE "
                + " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO " + " WHERE BPM.GROUP_ID = '" + GROUP_ID
                + "' AND USERINFO.USER_UID(+) = BPM.MAKE_USER_UID";
        } else if ((super.m_nDBType == DB_TYPE_MSSQL) || (super.m_nDBType == DB_TYPE_SYBASE)) {
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, "
                + "     BPM.MAKE_USER_UID, USERINFO.USER_NAME MAKE_USER_NAME, USERINFO.DEPT_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE "
                + " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO " + " WHERE BPM.GROUP_ID = '" + GROUP_ID
                + "' AND USERINFO.USER_UID =* BPM.MAKE_USER_UID";
        }else if(super.m_nDBType == DB_TYPE_DB2){
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, "
                + "     BPM.MAKE_USER_UID, USERINFO.USER_NAME MAKE_USER_NAME, USERINFO.DEPT_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE "
                + " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO " + " ON USERINFO.USER_UID = BPM.MAKE_USER_UID BPM.GROUP_ID"
                + " AND BPM.GROUP_ID = '" + GROUP_ID+ "'";  
        }else if(super.m_nDBType == DB_TYPE_ALTIBASE){
            strQuery = "SELECT BPM.GROUP_ID, BPM.GROUP_NAME, BPM.GROUP_OTHER_NAME, BPM.GROUP_TYPE, BPM.DEPT_ID, "
                + "     BPM.MAKE_USER_UID, USERINFO.USER_NAME MAKE_USER_NAME, USERINFO.DEPT_NAME MAKE_DEPT_NAME, BPM.MAKE_DATE "
                + " FROM TCN_BPM_GROUP BPM, TCN_USERINFORMATION_BASIC USERINFO " + " ON USERINFO.USER_UID = BPM.MAKE_USER_UID BPM.GROUP_ID"
                + " AND BPM.GROUP_ID = '" + GROUP_ID+ "'";        	
        }
        
        bResult = m_connectionBroker.executeQuery(strQuery);
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        groups = processData(m_connectionBroker.getResultSet());
        if (groups == null) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        nSize = groups.size();
        if (nSize != 1) {
            m_lastError.setMessage("Fail to get correct organizaion infomation.",
                    "OrganizationHandler.getOrganization.LinkedList.size(not unique)", "");
            m_connectionBroker.clearConnectionBroker();
            return null;
        }

        group = groups.get(0);
        m_connectionBroker.clearConnectionBroker();
        return group;
    }

    public boolean insertGroup(Group group) {
        boolean bReturn = false;
        String strQuery = "";
        boolean bResult = false;
        int nResult = -1;

        bResult = m_connectionBroker.openConnection();
        if (!bResult) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bResult;
        }
        strQuery = "INSERT INTO " + m_strDepartmentTable + " (" + m_strDepartmentColumns + ") VALUES('"
                + group.getGROUP_ID() + "', '" + group.getGROUP_NAME() + "', '" + group.getGROUP_OTHER_NAME() + "', '"
                + group.getGROUP_TYPE() + "', '" + group.getDEPT_ID() + "', '" + group.getMAKE_USER_UID()
                + "', " + ((super.m_nDBType == 0) ? " sysdate " : " getDate()")  + ")";
        m_connectionBroker.setAutoCommit(false);
        nResult = m_connectionBroker.executeUpdate(strQuery);
        if (nResult != 1) {
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

    public boolean updateGroup(Group group) {
        boolean bReturn = false;
        String strQuery = "";
        int nResult = -1;

        bReturn = m_connectionBroker.openConnection();
        if (bReturn == false) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bReturn;
        }

        strQuery = "UPDATE " + m_strDepartmentTable + " SET GROUP_NAME = '" + group.getGROUP_NAME() + "',"
                + " GROUP_OTHER_NAME = '" + group.getGROUP_OTHER_NAME() + "'," +
                // " MAKE_USER_UID = '" + group.getMAKE_USER_UID() + "'," +
                " MAKE_DATE = " + ((super.m_nDBType == 0) ? " sysdate " : " getDate()")  + " " + " WHERE GROUP_ID = '" + group.getGROUP_ID() + "'";
        // System.out.println("\n getGroup QUERY ===> " + strQuery);

        m_connectionBroker.setAutoCommit(false);
        nResult = m_connectionBroker.executeUpdate(strQuery);
        if (nResult != 1) {
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

    public boolean deleteGroup(String GROUP_ID) {
        boolean bReturn = false;
        String strQuery = "";
        int nResult = -1;

        bReturn = m_connectionBroker.openConnection();
        if (bReturn == false) {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bReturn;
        }

        strQuery = "DELETE " + m_strDepartmentTable + " WHERE GROUP_ID = '" + GROUP_ID + "'";

        m_connectionBroker.setAutoCommit(false);
        nResult = m_connectionBroker.executeUpdate(strQuery);
        if (nResult != 1) {
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
     * 
     * @param resultSet Query 실행 결과
     * @return Department
     */
    private Groups processData(ResultSet resultSet) {
        Groups groups = null;

        if (resultSet == null) {
            m_lastError.setMessage("NullPoint ResultSet.", "DepartmentHandler.processData", "");
            return null;
        }

        groups = new Groups();

        try {
            while (resultSet.next()) {
                Group group = new Group();

                group.setGROUP_ID(getString(resultSet, "GROUP_ID"));
                group.setGROUP_NAME(getString(resultSet, "GROUP_NAME"));
                group.setGROUP_OTHER_NAME(getString(resultSet, "GROUP_OTHER_NAME"));
                group.setGROUP_TYPE(getString(resultSet, "GROUP_TYPE"));
                group.setDEPT_ID(getString(resultSet, "DEPT_ID"));
                group.setMAKE_USER_UID(getString(resultSet, "MAKE_USER_UID"));
                group.setMAKE_USER_NAME(getString(resultSet, "MAKE_USER_NAME"));
                group.setMAKE_DEPT_NAME(getString(resultSet, "MAKE_DEPT_NAME"));
                group.setMAKE_DATE(getDate(resultSet, "MAKE_DATE", TIMESTAMP_SECOND));
                group.setTOTAL_COUNT(getInt(resultSet, "TOTAL_COUNT"));

                getRawDate(resultSet, UserAssociatedTableMap
                        .getColumnName(UserAssociatedTableMap.SUBSTITUTE_START_DATE), TIMESTAMP_SECOND);

                groups.add(group);
            }
        } catch (Exception e) {
            m_lastError.setMessage("Fail to process department list.", "DepartmentHandler.processData", e.getMessage());
            return null;
        }

        return groups;
    }

}
