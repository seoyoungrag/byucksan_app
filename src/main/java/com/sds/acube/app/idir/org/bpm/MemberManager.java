package com.sds.acube.app.idir.org.bpm;

import java.util.StringTokenizer;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * Class Name  : MemberManager.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.bpm.MemberManager.java
 */
public class MemberManager {
	/**
	 */
	private ConnectionParam m_connectionParam = null;
	private String 		 m_strLastError = "";
	
	public MemberManager(ConnectionParam connectionParam) {
		m_connectionParam = connectionParam;		
	}
	
	/**
	 * Get last Error
	 * @return String Error Message
	 */
	public String getLastError() {
		return m_strLastError;
	}
	
	public Members getMembers(String GROUP_ID, String IS_DELETED) {
		MemberHandler memberHandler = new MemberHandler(m_connectionParam);
		Members members = null;

        members = memberHandler.getMembers(GROUP_ID, IS_DELETED);
		if (members == null) {
			m_strLastError = memberHandler.getLastError();
			return null;
		}
		return members; 
	}
    
    public boolean insertMember(String GROUP_ID, String USER_UID_LIST) {
        MemberHandler memberHandler = new MemberHandler(m_connectionParam);
        boolean bReturn = false;
        bReturn = memberHandler.insertMember(GROUP_ID, USER_UID_LIST);
        if (!bReturn) {
            m_strLastError = memberHandler.getLastError();
        }
        
        return bReturn;
    }
    
    public boolean deleteMember(String GROUP_ID_LIST) {
        MemberHandler memberHandler = new MemberHandler(m_connectionParam);
        boolean bReturn = false;
        
        StringTokenizer list = new StringTokenizer(GROUP_ID_LIST, "|");
        while(list.hasMoreTokens()) {
            bReturn = memberHandler.deleteMember(list.nextToken());
        }
        if (!bReturn) {
            m_strLastError = memberHandler.getLastError();
        }
        
        return bReturn;
    }

}
