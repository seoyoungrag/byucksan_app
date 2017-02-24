package com.sds.acube.app.idir.org.bpm;

import java.util.StringTokenizer;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : GroupManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.bpm.GroupManager.java
 */
public class GroupManager {
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public GroupManager(ConnectionParam connectionParam) {
		m_connectionParam = connectionParam;		
	}
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError() {
		return m_strLastError;
	}
	
	public Groups getGroups(int nPage, int nRowPerPage, String GROUP_TYPE, String strSearch, String strLanguageType, 
            String uid, String DEPT_ID) {
		GroupHandler groupHandler = new GroupHandler(m_connectionParam);
		Groups groups = null;
		groups = groupHandler.getGroups(nPage, nRowPerPage, GROUP_TYPE, strSearch, strLanguageType, uid, DEPT_ID);
		if (groups == null) {
			m_strLastError = groupHandler.getLastError();
			return null;
		}
		return groups;
	}
    
    public Group getGroup(String GROUP_ID) {
        GroupHandler groupHandler = new GroupHandler(m_connectionParam);
        Group group = null;
        group = groupHandler.getGroup(GROUP_ID);
        if (group == null) {
            m_strLastError = groupHandler.getLastError();
            return null;
        } 
        return group;
    }
    
    public boolean insertGroup(Group group) {
        GroupHandler groupHandler = new GroupHandler(m_connectionParam);
        boolean bReturn = false;
        
        bReturn = groupHandler.insertGroup(group);
        if (!bReturn) {
            m_strLastError = groupHandler.getLastError();
        }
        
        return bReturn;
    }
    
    public boolean updateGroup(Group group) {
        GroupHandler groupHandler = new GroupHandler(m_connectionParam);
        boolean bReturn = false;

        bReturn = groupHandler.updateGroup(group);
        if (!bReturn) {
            m_strLastError = groupHandler.getLastError();
        }
        
        return bReturn;
    }
    
    public boolean deleteGroup(String GROUP_ID_LIST) {
        GroupHandler groupHandler = new GroupHandler(m_connectionParam);
        boolean bReturn = false;
        
        StringTokenizer list = new StringTokenizer(GROUP_ID_LIST, "|");
        while(list.hasMoreTokens()) {
            bReturn = groupHandler.deleteGroup(list.nextToken());
        }
        
        if (!bReturn) {
            m_strLastError = groupHandler.getLastError();
        }
        
        return bReturn;
    }

}
