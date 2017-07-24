/**
 * 
 */
package com.sds.acube.app.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jconfig.Configuration;

import com.sds.acube.app.idir.common.GUID;
import com.sds.acube.app.idir.common.vo.ConnectionParam;
import com.sds.acube.app.idir.org.common.TableDefinition;
import com.sds.acube.app.idir.org.db.DataConverter;
import com.sds.acube.app.idir.org.db.DataHandler;
import com.sds.acube.app.idir.org.hierarchy.Classification;
import com.sds.acube.app.idir.org.hierarchy.ClassificationTableMap;
import com.sds.acube.app.idir.org.hierarchy.FavoriteClassificationTableMap;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.idir.org.orginfo.OrgImageTableMap;
import com.sds.acube.app.idir.org.orginfo.OrgTableMap;
import com.sds.acube.app.idir.org.user.Employee;
import com.sds.acube.app.idir.org.user.EmployeeHandler;
import com.sds.acube.app.idir.org.user.Employees;
import com.sds.acube.app.idir.org.user.UserDetailViewTableMap;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;

/**
 * Class Name  : OrgUtil.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 21. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 4. 21.
 * @version  1.0 
 * @see  com.sds.acube.app.common.util.OrgUtil.java
 */

public class OrgUtil extends DataHandler {

    private String m_strLastError;

    public static final String INOFFICE = "0";			// 사용자 재직 상태
    public static final String SUSPENSION = "1"; 		// 사용자 정직 상태
    public static final String RETIREMENT = "2"; 		// 사용자 퇴직 상태

    public static final String ORG_IS_DELETED_NO = "0";		// 정상 부서 상태	
    public static final String ORG_IS_DELETED_YES = "1";	// 폐기 부서 상태

    public static final String TIMESTAMP_DAY = "yyyy-MM-dd";   
    public static final String TIMESTAMP_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String DB_NLS_DATE_FORMAT = "YYYY-MM-DD HH24:MI:SS";

    public static final String DISPLAY_STATUS = "0";		// 트리 Display 상태
    public static final String NON_DISPLAY_STATUS = "1";	// 트리 Display 되지 않는 상태

    public static final int BUFFER_SIZE = 4096; 

    public static final char SEARCH_LIKE = '*'; 

    public static final int SEARCH_SCOPE_INOFFICE = 0;			// 재직 사용자만 검색
    public static final int SEARCH_SCOPE_SUSPENSION = 1;		// 정직 사용자만 검색
    public static final int SEARCH_SCOPE_RETIREMENT = 2;		// 퇴직 사용자만 검색
    public static final int SEARCH_SCOPE_INOFFICE_N__SUSPENSION = 3;	// 재직, 정직 사용자 검색
    public static final int SEARCH_SCOPE_ALL = 4;			// 모든 사용자 검색

    private String m_strOrganizationColumns = "";
    private String m_strOrganizationTable = "";
    private String m_strDetailUserColumns = "";
    private String m_strDetailUserTable = "";
    private String m_strClassificationColumns = "";
    private String m_strClassificationTable = "";
    
    

    private String displayPositionOrder = "";

    public OrgUtil(ConnectionParam connectionParam)
    {
	super(connectionParam);
    }

    public OrgUtil(ConnectionParam connectionParam, String displayPositionOrder)
    {
	super(connectionParam);
	setDisplayPositionOrder(displayPositionOrder);
    }

    public String getLastError()
    {
	return m_strLastError;
    }

    /**
	 * <pre>  설명 </pre>
	 * @param displayPositionOrder
	 * @see   
	 */
    public void setDisplayPositionOrder(String displayPositionOrder)
    {
	this.displayPositionOrder = displayPositionOrder;
    }
    
    private void setOrganizationTable() {
	m_strOrganizationTable = TableDefinition.getTableName(TableDefinition.ORGANIZATION);
	m_strOrganizationColumns = 	OrgTableMap.getColumnName(OrgTableMap.ORG_NAME) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_OTHER_NAME) + "," + 
					OrgTableMap.getColumnName(OrgTableMap.ORG_ABBR_NAME) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_ID) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_CODE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_PARENT_ID) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_ORDER) + "," +
					OrgTableMap.getColumnName(OrgTableMap.SERVERS) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ORG_TYPE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.DESCRIPTION) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ODCD_CODE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_ODCD) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_INSTITUTION) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_INSPECTION) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ADDR_SYMBOL) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_PROXY_DOC_HANDLE_DEPT) + "," +
					OrgTableMap.getColumnName(OrgTableMap.PROXY_DOC_HANDLE_DEPT_CODE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.CHIEF_POSITION) + "," +
					OrgTableMap.getColumnName(OrgTableMap.FORMBOX_INFO) + "," +
					OrgTableMap.getColumnName(OrgTableMap.RESERVED1) + "," +
					OrgTableMap.getColumnName(OrgTableMap.RESERVED2) + "," +
					OrgTableMap.getColumnName(OrgTableMap.RESERVED3) + "," +
					OrgTableMap.getColumnName(OrgTableMap.OUTGOING_NAME) + "," +
					OrgTableMap.getColumnName(OrgTableMap.COMPANY_ID) + "," +
					OrgTableMap.getColumnName(OrgTableMap.INSTITUTION_DISPLAY_NAME) + "," +
					OrgTableMap.getColumnName(OrgTableMap.HOMEPAGE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.EMAIL) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ADDRESS) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ADDRESS_DETAIL) + "," +
					OrgTableMap.getColumnName(OrgTableMap.ZIP_CODE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.TELEPHONE) + "," +
					OrgTableMap.getColumnName(OrgTableMap.FAX) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_DELETED) + "," +
					OrgTableMap.getColumnName(OrgTableMap.IS_PROCESS);
    }
    
    private void setUserTable() {
	m_strDetailUserTable = TableDefinition.getTableName(TableDefinition.USERINFO_DETAIL_VIEW);
	m_strDetailUserColumns = m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ORDER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SECURITY_LEVEL) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ROLE_CODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESIDENT_NO) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMPLOYEE_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SYSMAIL) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SERVERS) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_CONCURRENT) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_PROXY) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELEGATE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_EXISTENCE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_RID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DESCRIPTION) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED1) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED2) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED3) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_CODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ABBR_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ORDER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_CODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ABBR_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ORDER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_CODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_ORDER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMAIL) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PCONLINE_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOMEPAGE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ADDR) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_DETAIL_ADDR) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ZIPCODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_FAX) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PAGER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ADDR) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_DETAIL_ADDR) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ZIPCODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_FAX) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_STATUS) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.WHEN_CHANGED_PASSWORD) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.CERTIFICATION_ID) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_CODE) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_OTHER_NAME) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_ORDER) + "," +
	m_strDetailUserTable + "." + UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OPTIONAL_GTP_NAME) + "," +
	m_strDetailUserTable + ".EMPTY_SET," +
	m_strDetailUserTable + ".EMPTY_REASON," +
	m_strDetailUserTable + ".EMPTY_START_DATE," +
	m_strDetailUserTable + ".EMPTY_END_DATE";
    }

    private void setClassificationTable() {
	m_strClassificationTable = TableDefinition.getTableName(TableDefinition.CLASSIFICATION);
	m_strClassificationColumns =	ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_ID) +","+
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_NAME) +","+
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.RETENTION_DATE) +","+
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_PARENT_ID) +","+
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.COMP_ID) +","+
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.DESCRIPTION) + "," +
		   			ClassificationTableMap.getColumnName(ClassificationTableMap.CLASSIFICATION_CODE);
    }
    
    
    /**
     * ResultSet을 List로 변환 
     * @param resultSet Query 실행 결과 
     * @return List<OrganizationVO>
     */
    private List<OrganizationVO> processDataOrgList(ResultSet resultSet)
    {
		List<OrganizationVO> organizationVOList = null;
		if(resultSet == null)
		{
		    m_lastError.setMessage("NullPoint ResultSet.", "OrgUtil.processDataOrgList", "");
		    return null;
		}
		organizationVOList = new ArrayList<OrganizationVO>();
		try
		{
		    OrganizationVO organizationVO;
		    for(; resultSet.next(); organizationVOList.add(organizationVO))
		    {
			organizationVO = new OrganizationVO();
			organizationVO.setOrgName(getString(resultSet, OrgTableMap.getColumnName(0)));
			organizationVO.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(1)));
			organizationVO.setOrgAbbrName(getString(resultSet, OrgTableMap.getColumnName(2)));
			organizationVO.setOrgID(getString(resultSet, OrgTableMap.getColumnName(3)));
			organizationVO.setOrgCode(getString(resultSet, OrgTableMap.getColumnName(4)));
			organizationVO.setOrgParentID(getString(resultSet, OrgTableMap.getColumnName(5)));
			organizationVO.setOrgOrder(getInt(resultSet, OrgTableMap.getColumnName(6)));
			organizationVO.setOrgType(getInt(resultSet, OrgTableMap.getColumnName(8)));
			organizationVO.setDescription(getString(resultSet, OrgTableMap.getColumnName(9)));
			organizationVO.setODCDCode(getString(resultSet, OrgTableMap.getColumnName(10)));
			organizationVO.setIsODCD(getBoolean(resultSet, OrgTableMap.getColumnName(11)));
			organizationVO.setIsInstitution(getBoolean(resultSet, OrgTableMap.getColumnName(12)));
			organizationVO.setIsInspection(getBoolean(resultSet, OrgTableMap.getColumnName(13)));
			organizationVO.setAddrSymbol(getString(resultSet, OrgTableMap.getColumnName(14)));
			organizationVO.setIsProxyDocHandleDept(getBoolean(resultSet, OrgTableMap.getColumnName(15)));
			organizationVO.setProxyDocHandleDeptCode(getString(resultSet, OrgTableMap.getColumnName(16)));
			organizationVO.setChiefPosition(getString(resultSet, OrgTableMap.getColumnName(17)));
			organizationVO.setFormBoxInfo(getBoolean(resultSet, OrgTableMap.getColumnName(18)));
			organizationVO.setReserved1(getString(resultSet, OrgTableMap.getColumnName(19)));
			organizationVO.setReserved2(getString(resultSet, OrgTableMap.getColumnName(20)));
			organizationVO.setReserved3(getString(resultSet, OrgTableMap.getColumnName(21)));
			organizationVO.setOutgoingName(getString(resultSet, OrgTableMap.getColumnName(22)));
			organizationVO.setCompanyID(getString(resultSet, OrgTableMap.getColumnName(23)));
			organizationVO.setInstitutionDisplayName(getString(resultSet, OrgTableMap.getColumnName(24)));
			organizationVO.setHomepage(getString(resultSet, OrgTableMap.getColumnName(25)));
			organizationVO.setEmail(getString(resultSet, OrgTableMap.getColumnName(26)));
			organizationVO.setAddress(getString(resultSet, OrgTableMap.getColumnName(27)));
			organizationVO.setAddressDetail(getString(resultSet, OrgTableMap.getColumnName(28)));
			organizationVO.setZipCode(getString(resultSet, OrgTableMap.getColumnName(29)));
			organizationVO.setTelephone(getString(resultSet, OrgTableMap.getColumnName(30)));
			organizationVO.setFax(getString(resultSet, OrgTableMap.getColumnName(31)));
			organizationVO.setIsDeleted(getBoolean(resultSet, OrgTableMap.getColumnName(32)));
			organizationVO.setIsProcess(getBoolean(resultSet, OrgTableMap.getColumnName(33)));
		    }
	
		}
		catch(Exception e)
		{
		    m_lastError.setMessage("Fail to process department list.", "OrgUtil.processDataOrgList", e.getMessage());
		    return null;
		}
		return organizationVOList;
    }
    
    /**
     * ResultSet을 List로 변환 
     * @param resultSet Query 실행 결과 
     * @return List<OrganizationVO>
     */
    private List<OrganizationVO> processRootIndexSymbolList(ResultSet resultSet)
    {
    	List<OrganizationVO> organizationVOList = null;
    	if(resultSet == null)
    	{
    		m_lastError.setMessage("NullPoint ResultSet.", "OrgUtil.processDataOrgList", "");
    		return null;
    	}
    	organizationVOList = new ArrayList<OrganizationVO>();
    	try
    	{
    		OrganizationVO organizationVO;
    		for(; resultSet.next(); organizationVOList.add(organizationVO))
    		{
    			organizationVO = new OrganizationVO();
    			organizationVO.setAddrSymbol(getString(resultSet, OrgTableMap.getColumnName(14)));
    			organizationVO.setCompanyID(getString(resultSet, OrgTableMap.getColumnName(23)));
    		}
    		
    	}
    	catch(Exception e)
    	{
    		m_lastError.setMessage("Fail to process department list.", "OrgUtil.processDataOrgList", e.getMessage());
    		return null;
    	}
    	return organizationVOList;
    }
    
    /**
     * ResultSet을 List로 변환 
     * @param resultSet Query 실행 결과 
     * @return List<OrganizationVO>
     */
    private List<OrganizationVO> processDataOrgSymbolList(ResultSet resultSet)
    {
    	List<OrganizationVO> organizationVOList = null;
    	if(resultSet == null)
    	{
    		m_lastError.setMessage("NullPoint ResultSet.", "OrgUtil.processDataOrgList", "");
    		return null;
    	}
    	organizationVOList = new ArrayList<OrganizationVO>();
    	try
    	{
    		OrganizationVO organizationVO;
    		for(; resultSet.next(); organizationVOList.add(organizationVO))
    		{
    			organizationVO = new OrganizationVO();
    			organizationVO.setOrgName(getString(resultSet, OrgTableMap.getColumnName(0)));
    			organizationVO.setOrgOtherName(getString(resultSet, OrgTableMap.getColumnName(1)));
    			organizationVO.setOrgAbbrName(getString(resultSet, OrgTableMap.getColumnName(2)));
    			organizationVO.setOrgID(getString(resultSet, OrgTableMap.getColumnName(3)));
    			organizationVO.setOrgCode(getString(resultSet, OrgTableMap.getColumnName(4)));
    			organizationVO.setOrgParentID(getString(resultSet, OrgTableMap.getColumnName(5)));
    			organizationVO.setOrgOrder(getInt(resultSet, OrgTableMap.getColumnName(6)));
    			organizationVO.setOrgType(getInt(resultSet, OrgTableMap.getColumnName(8)));
    			organizationVO.setDescription(getString(resultSet, OrgTableMap.getColumnName(9)));
    			organizationVO.setODCDCode(getString(resultSet, OrgTableMap.getColumnName(10)));
    			organizationVO.setIsODCD(getBoolean(resultSet, OrgTableMap.getColumnName(11)));
    			organizationVO.setIsInstitution(getBoolean(resultSet, OrgTableMap.getColumnName(12)));
    			organizationVO.setIsInspection(getBoolean(resultSet, OrgTableMap.getColumnName(13)));
    			organizationVO.setAddrSymbol(getString(resultSet, OrgTableMap.getColumnName(14)));
    			organizationVO.setIsProxyDocHandleDept(getBoolean(resultSet, OrgTableMap.getColumnName(15)));
    			organizationVO.setProxyDocHandleDeptCode(getString(resultSet, OrgTableMap.getColumnName(16)));
    			organizationVO.setChiefPosition(getString(resultSet, OrgTableMap.getColumnName(17)));
    			organizationVO.setFormBoxInfo(getBoolean(resultSet, OrgTableMap.getColumnName(18)));
    			organizationVO.setReserved1(getString(resultSet, OrgTableMap.getColumnName(19)));
    			organizationVO.setReserved2(getString(resultSet, OrgTableMap.getColumnName(20)));
    			organizationVO.setReserved3(getString(resultSet, OrgTableMap.getColumnName(21)));
    			organizationVO.setOutgoingName(getString(resultSet, OrgTableMap.getColumnName(22)));
    			organizationVO.setCompanyID(getString(resultSet, OrgTableMap.getColumnName(23)));
    			organizationVO.setInstitutionDisplayName(getString(resultSet, OrgTableMap.getColumnName(24)));
    			organizationVO.setHomepage(getString(resultSet, OrgTableMap.getColumnName(25)));
    			organizationVO.setEmail(getString(resultSet, OrgTableMap.getColumnName(26)));
    			organizationVO.setAddress(getString(resultSet, OrgTableMap.getColumnName(27)));
    			organizationVO.setAddressDetail(getString(resultSet, OrgTableMap.getColumnName(28)));
    			organizationVO.setZipCode(getString(resultSet, OrgTableMap.getColumnName(29)));
    			organizationVO.setTelephone(getString(resultSet, OrgTableMap.getColumnName(30)));
    			organizationVO.setFax(getString(resultSet, OrgTableMap.getColumnName(31)));
    			organizationVO.setIsDeleted(getBoolean(resultSet, OrgTableMap.getColumnName(32)));
    			organizationVO.setIsProcess(getBoolean(resultSet, OrgTableMap.getColumnName(33)));
    		}
    		
    	}
    	catch(Exception e)
    	{
    		m_lastError.setMessage("Fail to process department list.", "OrgUtil.processDataOrgList", e.getMessage());
    		return null;
    	}
    	return organizationVOList;
    }

    /**
     * ResultSet을 List로 변환 
     * @param resultSet Query 실행 결과 
     * @return List<UserVO>
     */
    private List<UserVO> processDataUserList(ResultSet resultSet)
    {
	String[] displayPositionOrderA = displayPositionOrder.split("\\^");
	int displayPositionOrderSize = displayPositionOrderA.length;

	List<UserVO> userVOList = null;

	if (resultSet == null)
	{
	    m_lastError.setMessage("NullPoint ResultSet.",
		    "OrgUtil.processDataUserList",
	    "");

	    return null;
	}

	userVOList = new ArrayList<UserVO>();

	try
	{
	    while(resultSet.next())
	    {
		UserVO userVO = new UserVO();

		// set Employee information
		userVO.setUserID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ID)));
		userVO.setUserName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_NAME)));
		userVO.setUserOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_OTHER_NAME)));
		userVO.setUserUID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_UID)));
		userVO.setGroupID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_ID)));
		userVO.setGroupName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_NAME)));
		userVO.setGroupOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GROUP_OTHER_NAME)));
		userVO.setCompID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_ID)));
		userVO.setCompName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_NAME)));
		userVO.setCompOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.COMP_OTHER_NAME)));
		userVO.setDeptID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_ID)));
		userVO.setDeptName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_NAME)));
		userVO.setDeptOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DEPT_OTHER_NAME)));
		userVO.setPartID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_ID)));
		userVO.setPartName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_NAME)));
		userVO.setPartOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PART_OTHER_NAME)));
		userVO.setOrgDisplayName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_NAME)));
		userVO.setOrgDisplayOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ORG_DISPLAY_OTHER_NAME)));
		userVO.setUserOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_ORDER)));
		userVO.setSecurityLevel(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SECURITY_LEVEL)));
		userVO.setRoleCodes(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.ROLE_CODE)));
		userVO.setResidentNo(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESIDENT_NO)));
		userVO.setEmployeeID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMPLOYEE_ID)));
		userVO.setSysMail(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SYSMAIL)));
		userVO.setConcurrent(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_CONCURRENT)));
		userVO.setProxy(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_PROXY)));
		userVO.setDelegate(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELEGATE)));
		userVO.setExistence(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_EXISTENCE)));
		userVO.setUserRID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_RID)));
		userVO.setDeleted(getBoolean(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED)));
		userVO.setIsDeleted(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.IS_DELETED)));
		userVO.setDescription(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DESCRIPTION)));
		userVO.setReserved1(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED1)));
		userVO.setReserved2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED2)));
		userVO.setReserved3(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.RESERVED3)));
		userVO.setGradeCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_CODE)));
		userVO.setGradeName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_NAME)));
		userVO.setGradeOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_OTHER_NAME)));
		userVO.setGradeAbbrName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ABBR_NAME)));
		userVO.setGradeOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.GRADE_ORDER)));
		userVO.setPositionCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_CODE)));
		userVO.setPositionName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_NAME)));
		userVO.setPositionOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_OTHER_NAME)));
		userVO.setPositionAbbrName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ABBR_NAME)));
		userVO.setPositionOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.POSITION_ORDER)));
		userVO.setTitleCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_CODE)));
		userVO.setTitleName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_NAME)));
		userVO.setTitleOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_OTHER_NAME)));
		userVO.setTitleOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.TITLE_ORDER)));
		userVO.setEmail(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.EMAIL)));
		userVO.setDuty(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY)));
		userVO.setPcOnlineID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PCONLINE_ID)));
		userVO.setHomePage(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOMEPAGE)));
		userVO.setOfficeTel(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL)));
		userVO.setOfficeTel2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_TEL2)));
		userVO.setOfficeAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ADDR)));
		userVO.setOfficeDetailAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_DETAIL_ADDR)));
		userVO.setOfficeZipCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_ZIPCODE)));
		userVO.setOfficeFax(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OFFICE_FAX)));
		userVO.setMobile(getString(resultSet,UserDetailViewTableMap.getColumnName( UserDetailViewTableMap.MOBILE)));
		userVO.setMobile2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.MOBILE2)));
		userVO.setPager(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.PAGER)));
		userVO.setHomeAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ADDR)));
		userVO.setHomeDetailAddr(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_DETAIL_ADDR)));
		userVO.setHomeZipCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_ZIPCODE)));
		userVO.setHomeTel(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL)));
		userVO.setHomeTel2(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_TEL2)));
		userVO.setHomeFax(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.HOME_FAX)));
		userVO.setUserStatus(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.USER_STATUS)));
		userVO.setChangedPWDDate(getDate(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.WHEN_CHANGED_PASSWORD), TIMESTAMP_SECOND));
		userVO.setCertificationID(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.CERTIFICATION_ID)));
		userVO.setDutyCode(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_CODE)));
		userVO.setDutyName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_NAME)));
		userVO.setDutyOtherName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_OTHER_NAME)));
		userVO.setDutyOrder(getInt(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.DUTY_ORDER)));
		userVO.setOptionalGTPName(getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.OPTIONAL_GTP_NAME)));
		userVO.setIsEmpty(getBoolean(resultSet, "EMPTY_SET"));
		userVO.setEmptyReason(getString(resultSet, "EMPTY_REASON"));
		userVO.setEmptyStartDate(getDate(resultSet, "EMPTY_START_DATE", TIMESTAMP_SECOND));
		userVO.setEmptyEndDate(getDate(resultSet, "EMPTY_END_DATE", TIMESTAMP_SECOND));

		// Mail Server Setting
		String strServers = getString(resultSet, UserDetailViewTableMap.getColumnName(UserDetailViewTableMap.SERVERS));
		if (strServers != null && strServers.length() > 0)
		{
		    String arrServers[] = DataConverter.splitString(strServers, "^");
		    if (arrServers != null)
		    {
			for (int i = 0; i < arrServers.length ; i++)
			{
			    String arrServerValue[] = DataConverter.splitString(arrServers[i], "/");
			    if (arrServerValue != null && arrServerValue.length == 2)
			    {
				String strServerType = arrServerValue[0].toUpperCase();
				if (strServerType.compareTo("MAIL") == 0)
				{
				    String strMailServer = arrServerValue[1];
				    if (strMailServer != null && strMailServer.length() > 0)
				    {
					userVO.setMailServer(strMailServer);	
				    }
				}
			    }
			}
		    }
		}

		String displayPosition = "";
		for(int p=0; p< displayPositionOrderSize; p++) {
		    String tempPosition = "";
		    if("optionalGTP".equals(displayPositionOrderA[p])) {	//통합직함
			tempPosition = userVO.getOptionalGTPName();
		    }
		    else if("position".equals(displayPositionOrderA[p])) {	//직위
			tempPosition = userVO.getPositionName();
		    }
		    else if("grade".equals(displayPositionOrderA[p])) {	//직급
			tempPosition = userVO.getGradeName();
		    }
		    else if("title".equals(displayPositionOrderA[p])) {	//직책
			tempPosition = userVO.getTitleName();
		    }
		    else if("duty".equals(displayPositionOrderA[p])) {		//직무
			tempPosition = userVO.getDutyName();
		    }
		    if(tempPosition != null && !"".equals(tempPosition.trim())) {
			displayPosition = tempPosition;
			break;
		    }
		}
		userVO.setDisplayPosition(displayPosition);
		
		userVOList.add(userVO);

	    }
	}
	catch(Exception e)
	{
	    m_lastError.setMessage("Fail to make IUser classList.",
		    "OrgUtil.processDataUserList",
		    e.getMessage());

	    return null;
	}	

	return userVOList;				
    } 
    
    public List<OrganizationVO> getSubAllOrganizationExcept(String strOrgID, List<String> orgIdList)
    {
		if("".equals(m_strOrganizationTable)) {
		    setOrganizationTable();
		}
		
		List<OrganizationVO> organizationVOList = null;
		boolean bResult = false;
		String strQuery = "";
		bResult = m_connectionBroker.openConnection();
		if(!bResult)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearConnectionBroker();
		    return null;
		}
		strQuery = "SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns + "  FROM " + m_strOrganizationTable;
		strQuery = strQuery + " WHERE IS_DELETED = 0";
		strQuery = strQuery + " CONNECT BY PRIOR  ORG_ID = ORG_PARENT_ID ";
	
		String strOrgIds = "";
		for(int i = 0; i < orgIdList.size(); i++)
		{
		    if(!strOrgID.equals(orgIdList.get(i))) {
			strOrgIds = strOrgIds + " AND ORG_ID <> '" + orgIdList.get(i) + "'";
		    }
		}
		strQuery = strQuery + strOrgIds;
		strQuery = strQuery + " START WITH ORG_ID = '" + strOrgID + "'";
		strQuery = strQuery + " ORDER SIBLINGS BY ORG_ORDER ";
	
		bResult = m_connectionBroker.executeQuery(strQuery);
		if(!bResult)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearConnectionBroker();
		    return null;
		}
		organizationVOList = processDataOrgList(m_connectionBroker.getResultSet());
		if(organizationVOList == null)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearConnectionBroker();
		    return null;
		} else
		{
		    m_connectionBroker.clearConnectionBroker();
		    return organizationVOList;
		}
    }
    
    public List<OrganizationVO> getRootIndexByAddrSymPrefix(String companyId, int symbolPrefixLength) // ::
    {
    	if("".equals(m_strOrganizationTable)) {
    		setOrganizationTable();
    	}
    	
    	List<OrganizationVO> organizationVOList = null;
    	boolean bResult = false;
    	StringBuilder strQuery = new StringBuilder("");
    	bResult = m_connectionBroker.openConnection();
    	if(!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	}
    	strQuery.append("SELECT SUBSTR(ADDR_SYMBOL,1, " + symbolPrefixLength + ") ADDR_SYMBOL, COMPANY_ID");
    	strQuery.append("\tFROM " + m_strOrganizationTable);
    	strQuery.append("\tWHERE ADDR_SYMBOL IS NOT NULL");
    	strQuery.append("\tAND ORG_TYPE <> 3");
    	strQuery.append("\tAND IS_DELETED = 0");
    	strQuery.append("\tAND COMPANY_ID = ?");
    	strQuery.append("\tGROUP BY SUBSTR(ADDR_SYMBOL, 1, 1), COMPANY_ID");
    	strQuery.append("\tORDER BY ADDR_SYMBOL");
    	
    	bResult = m_connectionBroker.prepareStatement(strQuery.toString());
    	if(!bResult)
    	{
    	    m_lastError.setMessage(m_connectionBroker.getLastError());
    	    m_connectionBroker.clearConnectionBroker();
    	    return null;
    	}
    	m_connectionBroker.setString(1, companyId);
    	
    	bResult = m_connectionBroker.executePreparedQuery();
    	
    	if(!bResult)
    	{
    	    m_lastError.setMessage(m_connectionBroker.getLastError());
    	    m_connectionBroker.clearConnectionBroker();
    	    return null;
    	}
    	
    	organizationVOList = processRootIndexSymbolList(m_connectionBroker.getResultSet());
    	if(organizationVOList == null)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	} else
    	{
    		m_connectionBroker.clearConnectionBroker();
    		return organizationVOList;
    	}
    }
    
    public List<OrganizationVO> getDepartmentsBySymbolIndexName(String indexName, String companyId) // ::
    {
    	if("".equals(m_strOrganizationTable)) {
    		setOrganizationTable();
    	}
    	
    	List<OrganizationVO> organizationVOList = null;
    	boolean bResult = false;
    	StringBuilder strQuery = new StringBuilder("");
    	bResult = m_connectionBroker.openConnection();
    	if(!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	}
    	strQuery.append("\tSELECT " + m_strOrganizationColumns);
    	strQuery.append("\tFROM " + m_strOrganizationTable);
    	strQuery.append("\tWHERE ORG_TYPE <> 3");
    	strQuery.append("\tAND IS_DELETED = 0");
    	strQuery.append("\tAND COMPANY_ID = ?");
    	strQuery.append("\tAND ADDR_SYMBOL LIKE '" + indexName + "%' ");
    	strQuery.append("\tORDER BY ADDR_SYMBOL");
    	
    	bResult = m_connectionBroker.prepareStatement(strQuery.toString());
    	if(!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	}
    	m_connectionBroker.setString(1, companyId);
    	
    	bResult = m_connectionBroker.executePreparedQuery();
    	
    	if(!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	}
    	
    	organizationVOList = processDataOrgSymbolList(m_connectionBroker.getResultSet());
    	if(organizationVOList == null)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();
    		return null;
    	} else
    	{
    		m_connectionBroker.clearConnectionBroker();
    		return organizationVOList;
    	}
    }

    public List<OrganizationVO> getSubAllOrganizationListByRole(String orgId, String roleCode)
    {
	if("".equals(m_strOrganizationTable)) {
	    setOrganizationTable();
	}
	
	List<OrganizationVO> organizationVOList = null;
	//ResultSet resultSet = null;
	boolean bResult = false;
	String strQuery = "";
	bResult = m_connectionBroker.openConnection();
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}
	String strOrgIds = "";
	if(roleCode.equals(AppConfig.getProperty("role_headoffice", "O003", "role"))) {
	    strOrgIds = " AND ((ORG_ID <> '" + orgId + "' AND IS_HEAD_OFFICE <> 1) OR (IS_HEAD_OFFICE IS NULL))";
	    strOrgIds += " AND (ORG_ID <> '" + orgId + "' AND IS_INSTITUTION <> 1)";  // 본부 검색시 타기관 검색 제한   // jth8172 2012 신결재 TF
/*
	    strQuery = " SELECT ORG_ID  FROM TCN_ORGINFORMATION_ROLE  WHERE ROLE_ID = ?";
	    bResult = m_connectionBroker.prepareStatement(strQuery);
	    if(!bResult)
	    {
		m_lastError.setMessage(m_connectionBroker.getLastError());
		m_connectionBroker.clearConnectionBroker();
		return null;
	    }
	    m_connectionBroker.setString(1, roleCode);
	    bResult = m_connectionBroker.executePreparedQuery();
	    if(!bResult)
	    {
		m_lastError.setMessage(m_connectionBroker.getLastError());
		m_connectionBroker.clearConnectionBroker();
		return null;
	    }
	    resultSet = m_connectionBroker.getResultSet();
	    try
	    {
		while(resultSet.next()) 
		{
		    String org_id = getString(resultSet, "ORG_ID");
		    if(!orgId.equals(org_id)) {
			strOrgIds = strOrgIds + " AND ORG_ID <> '" + org_id + "'";
		    }
		}
	    }
	    catch(SQLException e)
	    {
		m_lastError.setMessage("Fail to get ORG ID.", "OrgUtil.getSubAllOrganizationListByRole.SQLException", e.getMessage());
		m_connectionBroker.clearConnectionBroker();
	    }
*/
	} else if(roleCode.equals(AppConfig.getProperty("role_institution", "O002", "role"))) {
	    strOrgIds = " AND (ORG_ID <> '" + orgId + "' AND IS_INSTITUTION <> 1)";
	}

	strQuery = "SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns + "  FROM " + m_strOrganizationTable;
	strQuery = strQuery + " WHERE IS_DELETED = 0";
	strQuery = strQuery + " CONNECT BY PRIOR  ORG_ID = ORG_PARENT_ID ";
	strQuery = strQuery + strOrgIds;
	strQuery = strQuery + " START WITH ORG_ID = ? ";
	strQuery = strQuery + " ORDER SIBLINGS BY ORG_ORDER ";

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}
	m_connectionBroker.setString(1, orgId);
	bResult = m_connectionBroker.executePreparedQuery();
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}
	organizationVOList = processDataOrgList(m_connectionBroker.getResultSet());
	if(organizationVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	} else
	{
	    m_connectionBroker.clearConnectionBroker();
	    return organizationVOList;
	}
    }

    public List<OrganizationVO> getAllProcessOrganizationList(String compId)
    {
	if("".equals(m_strOrganizationTable)) {
	    setOrganizationTable();
	}
	
	List<OrganizationVO> organizationVOList = null;
	boolean bResult = false;
	String strQuery = "";
	bResult = m_connectionBroker.openConnection();
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	strQuery = "SELECT /*+index(INDX_ORG_ORDER) */ " + m_strOrganizationColumns + "  FROM " + m_strOrganizationTable;
	strQuery = strQuery + " WHERE IS_DELETED = 0";
	strQuery = strQuery + " AND COMPANY_ID = ? ";
	strQuery = strQuery + " AND IS_PROCESS = 1 ";
	strQuery = strQuery + " ORDER BY ORG_ORDER ";

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}
	m_connectionBroker.setString(1, compId);
	bResult = m_connectionBroker.executePreparedQuery();
	if(!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}
	organizationVOList = processDataOrgList(m_connectionBroker.getResultSet());
	if(organizationVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	} else
	{
	    m_connectionBroker.clearConnectionBroker();
	    return organizationVOList;
	}
    }
    
    /**
     * 주어진 이름을 가진 부서 검색.(대소문자 무시, 공백 무시, 검색범위)
     * @param strOrgName 조직명
     * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
     * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
     * @param bIncludePart 파트 포함 여부 (true : 파트 포함 / false : 파트 포함 하지 않음)
     * @param nScope 검색 범위 (0:전체그룹 , 1:회사내, 2:회사외)
     * @param strCompId 회사ID
     * @return List<OrganizationVO>
     */
    public List<OrganizationVO> getOrganizationListByName(String strOrgName, 
	    			boolean bCaseSensitive, boolean bTrim, boolean bIncludePart,
	    			int nScope, String strCompId)
    {
		if("".equals(m_strOrganizationTable)) {
		    setOrganizationTable();
		}
		
		List<OrganizationVO>	organizationVOList = null;
		String 		 	strQuery = "";
		String 		 	strWhereQuery = "";
		String 		 	strSearchOrgName = "";
		boolean	 	 	bResult = false;
	
		bResult = m_connectionBroker.openConnection();
		if (!bResult)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearConnectionBroker();	
		    return null;
		}
	
		if(strOrgName == null) strOrgName = "";
		String orgField = "ORG_NAME";
		// Space 제거 
		if(bTrim) {
		    strOrgName = strOrgName.trim();
		    strOrgName = DataConverter.replace(strOrgName, " ", "");
		    orgField = "TRIM(REPLACE(" + orgField + ", ' '))";
		}
	
		// 대소문자 문시
		if(!bCaseSensitive) {
		    strOrgName = strOrgName.toUpperCase();
		    orgField = "UPPER(" + orgField + ")";
		}
	
	
		if (!"".equals(strOrgName))
		{
		    strSearchOrgName = "%" + strOrgName + "%";
		    strWhereQuery = " WHERE " + orgField + " LIKE '"+ strSearchOrgName + "'" +
		    "   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
		}
		else
		{
		    strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
		}
		
		// 파트 포함 여부
		if(!bIncludePart) {
		    strWhereQuery += " AND ORG_TYPE <> 3";
		}
	
		switch (nScope) {
		    case 1 :
			strWhereQuery += " AND COMPANY_ID = '" + strCompId + "'";
			break;
		    case 2 :
			strWhereQuery += " AND COMPANY_ID <> '" + strCompId + "'";
			break;
		}
		
		strQuery = "SELECT " + m_strOrganizationColumns + 																	
		" FROM " + m_strOrganizationTable +
		strWhereQuery + " ORDER BY ORG_NAME";
	
		bResult = m_connectionBroker.excuteQuery(strQuery); 
		if (!bResult)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearConnectionBroker();	
		    return null;
		}
	
		organizationVOList = processDataOrgList(m_connectionBroker.getResultSet());
		if (organizationVOList == null)
		{
		    m_connectionBroker.clearConnectionBroker();	
		    return null;
		}
	
		m_connectionBroker.clearConnectionBroker();
	
		return organizationVOList;				
    }
    
    /**
     * 주어진 이름을 가진 수신자기호 검색.(대소문자 무시, 공백 무시, 검색범위)
     * @param strSymbolName 수신자기호명
     * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
     * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
     * @param bIncludePart 파트 포함 여부 (true : 파트 포함 / false : 파트 포함 하지 않음)
     * @param nScope 검색 범위 (0:전체그룹 , 1:회사내, 2:회사외)
     * @param strCompId 회사ID
     * @return List<OrganizationVO>
     */
    public List<OrganizationVO> getOrganizationListBySymbol(String strSymbolName, 
    		boolean bCaseSensitive, boolean bTrim, boolean bIncludePart,
    		int nScope, String strCompId)
    {
    	if("".equals(m_strOrganizationTable)) {
    		setOrganizationTable();
    	}
    	
    	List<OrganizationVO>	organizationVOList = null;
    	String 		 	strQuery = "";
    	String 		 	strWhereQuery = "";
    	String 		 	strSearchSymbolName = "";
    	boolean	 	 	bResult = false;
    	
    	bResult = m_connectionBroker.openConnection();
    	if (!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();	
    		return null;
    	}
    	
    	if(strSymbolName == null) strSymbolName = "";
    	String symbolField = "ADDR_SYMBOL";
    	
    	// Space 제거 
    	if(bTrim) {
    		strSymbolName = strSymbolName.trim();
    		strSymbolName = DataConverter.replace(strSymbolName, " ", "");
    		symbolField = "TRIM(REPLACE(" + symbolField + ", ' '))";
    	}
    	
    	// 대소문자 문시
    	if(!bCaseSensitive) {
    		strSymbolName = strSymbolName.toUpperCase();
    		symbolField = "UPPER(" + symbolField + ")";
    	}
    	
    	
    	if (!"".equals(strSymbolName))
    	{
    		strSearchSymbolName = "%" + strSymbolName + "%";
    		strWhereQuery = " WHERE " + symbolField + " LIKE '"+ strSearchSymbolName + "'" +
    		"   AND IS_DELETED = " +  ORG_IS_DELETED_NO;	
    	}
    	else
    	{
    		strWhereQuery = " WHERE IS_DELETED = " +  ORG_IS_DELETED_NO;
    	}
    	
    	// 파트 포함 여부
    	if(!bIncludePart) {
    		strWhereQuery += " AND ORG_TYPE <> 3";
    	}
    	
    	switch (nScope) {
    	case 1 :
    		strWhereQuery += " AND COMPANY_ID = '" + strCompId + "'";
    		break;
    	case 2 :
    		strWhereQuery += " AND COMPANY_ID <> '" + strCompId + "'";
    		break;
    	}
    	
    	strQuery = "SELECT " + m_strOrganizationColumns + 																	
    	" FROM " + m_strOrganizationTable +
    	strWhereQuery + " ORDER BY ORG_NAME";
    	
    	bResult = m_connectionBroker.excuteQuery(strQuery); 
    	if (!bResult)
    	{
    		m_lastError.setMessage(m_connectionBroker.getLastError());
    		m_connectionBroker.clearConnectionBroker();	
    		return null;
    	}
    	
    	organizationVOList = processDataOrgList(m_connectionBroker.getResultSet());
    	if (organizationVOList == null)
    	{
    		m_connectionBroker.clearConnectionBroker();	
    		return null;
    	}
    	
    	m_connectionBroker.clearConnectionBroker();
    	
    	return organizationVOList;				
    }
    
    public String getChiefId(String strOrgId)
    {
		if("".equals(m_strOrganizationTable)) {
		    setOrganizationTable();
		}
	
		String userId = "";
		String strQuery = "";
		if (m_connectionBroker.openConnection()) {
		    strQuery = "SELECT CHIEF_ID, CHIEF_NAME FROM " + m_strOrganizationTable + " WHERE ORG_ID = '" + strOrgId + "'";
	
		    if(m_connectionBroker.executeQuery(strQuery)) {
			ResultSet resultSet = m_connectionBroker.getResultSet();
			if(resultSet != null) {
			    try {
		                while (resultSet.next()) {
		                    userId = getString(resultSet, "CHIEF_ID");
		                }
	                    } catch (SQLException e) {
	            	    	LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
	                    }
			} else {
			    m_lastError.setMessage("NullPoint ResultSet.", "OrgUtil.getChiefInfo", "");
			}
		    } else {
			m_lastError.setMessage(m_connectionBroker.getLastError());
		    }
		    m_connectionBroker.clearConnectionBroker();
		} else {
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		}
	
		return userId;
    }

    public boolean registerOrgImage(OrgImage orgImage)
    {
	String strOrgImageTable = TableDefinition.getTableName(13);

        ResultSet resultSet = null;
        boolean bReturn = false;
        boolean bResult = false;
        boolean bFound = false;
        String strQuery = "";
        if(orgImage.getImageID().length() == 0)
        {
            GUID guid = new GUID();
            orgImage.setImageID(guid.toString());
        }
        bResult = m_connectionBroker.openConnection();
        if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bReturn;
        }
        strQuery = "SELECT " + OrgImageTableMap.getColumnName(0) + " FROM " + strOrgImageTable + " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
        bResult = m_connectionBroker.excuteQuery(strQuery);
        if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bReturn;
        }
        resultSet = m_connectionBroker.getResultSet();
        try
        {
            while(resultSet.next()) {
                bFound = true;
            }
        }
        catch(SQLException e)
        {
            m_lastError.setMessage("Fail to get next recordset", "OrgUtil.registerOrgImage.next", e.getMessage());
        }
        m_connectionBroker.clearQuery();
        if(!bFound)
            bReturn = insertOrgImage(orgImage);
        else
            bReturn = updateOrgImage(orgImage);
        m_connectionBroker.clearConnectionBroker();
        return bReturn;
    }

    private boolean insertOrgImage(OrgImage orgImage)
    {
	String strOrgImageTable = TableDefinition.getTableName(13);
        String strOrgImageColumns = OrgImageTableMap.getColumnName(0) + "," + OrgImageTableMap.getColumnName(1) + "," + OrgImageTableMap.getColumnName(2) + "," + OrgImageTableMap.getColumnName(3) + "," + OrgImageTableMap.getColumnName(4) + "," + OrgImageTableMap.getColumnName(5) + "," + OrgImageTableMap.getColumnName(6) + "," + OrgImageTableMap.getColumnName(7) + "," + OrgImageTableMap.getColumnName(8) + "," + OrgImageTableMap.getColumnName(9) + "," + OrgImageTableMap.getColumnName(10) + "," + OrgImageTableMap.getColumnName(11) + "," + OrgImageTableMap.getColumnName(12) + "," + OrgImageTableMap.getColumnName(13) + "," + OrgImageTableMap.getColumnName(14) + "," + OrgImageTableMap.getColumnName(15) + "," + OrgImageTableMap.getColumnName(16) + "," + OrgImageTableMap.getColumnName(17);
        ResultSet resultSet = null;
        boolean bReturn = false;
        boolean bResult = false;
        String strQuery = "";
        int nResult = -1;
        if(!super.m_connectionBroker.IsConnectionClosed())
        {
            super.m_connectionBroker.setAutoCommit(false);
            strQuery = "INSERT INTO " + strOrgImageTable + "(" + strOrgImageColumns + ")" + " VALUES ('" + orgImage.getImageID() + "'," + "'" + orgImage.getImageName() + "'," + "'" + orgImage.getOrgID() + "'," + Integer.toString(orgImage.getImageType()) + "," + Integer.toString(orgImage.getImageClassification()) + "," + "TO_DATE('" + orgImage.getRegistrationDate() + "', 'yyyy-MM-dd')," + "'" + orgImage.getIssueReason() + "'," + "'" + orgImage.getManagedOrg() + "'," + "'" + orgImage.getRegistrationRemarks() + "'," + DataConverter.toInt(orgImage.getDisuseYN()) + "," + "TO_DATE('" + orgImage.getDisuseDate() + "', 'yyyy-MM-dd')," + "'" + orgImage.getDisuseReason() + "'," + "'" + orgImage.getDisuseRemarks() + "'," + "'" + orgImage.getImageFileType() + "'," + "EMPTY_BLOB()," + Integer.toString(orgImage.getSizeWidth()) + "," + Integer.toString(orgImage.getSizeHeight()) + "," + Integer.toString(orgImage.getImageOrder()) + ")";
            nResult = super.m_connectionBroker.executeUpdate(strQuery);
            if(nResult == -1)
            {
                super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            super.m_connectionBroker.clearQuery();
            strQuery = "SELECT " + strOrgImageColumns + " FROM " + strOrgImageTable + " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
            bResult = super.m_connectionBroker.excuteQuery(strQuery);
            if(!bResult)
            {
                super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            resultSet = super.m_connectionBroker.getResultSet();
            bResult = updateOrgImageInfo(resultSet, orgImage);
            if(!bResult)
            {
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            super.m_connectionBroker.clearQuery();
            super.m_connectionBroker.commit();
            bReturn = true;
        }
        return bReturn;
    }

    private boolean updateOrgImage(OrgImage orgImage)
    {
	String strOrgImageTable = TableDefinition.getTableName(13);
        String strOrgImageColumns = OrgImageTableMap.getColumnName(0) + "," + OrgImageTableMap.getColumnName(1) + "," + OrgImageTableMap.getColumnName(2) + "," + OrgImageTableMap.getColumnName(3) + "," + OrgImageTableMap.getColumnName(4) + "," + OrgImageTableMap.getColumnName(5) + "," + OrgImageTableMap.getColumnName(6) + "," + OrgImageTableMap.getColumnName(7) + "," + OrgImageTableMap.getColumnName(8) + "," + OrgImageTableMap.getColumnName(9) + "," + OrgImageTableMap.getColumnName(10) + "," + OrgImageTableMap.getColumnName(11) + "," + OrgImageTableMap.getColumnName(12) + "," + OrgImageTableMap.getColumnName(13) + "," + OrgImageTableMap.getColumnName(14) + "," + OrgImageTableMap.getColumnName(15) + "," + OrgImageTableMap.getColumnName(16) + "," + OrgImageTableMap.getColumnName(17);
        ResultSet resultSet = null;
        boolean bReturn = false;
        boolean bResult = false;
        String strQuery = "";
        if(!super.m_connectionBroker.IsConnectionClosed())
        {
            super.m_connectionBroker.setAutoCommit(false);
            strQuery = "UPDATE " + strOrgImageTable + " SET IMAGE_NAME = '" + orgImage.getImageName() + "'," + "ORG_ID = '" + orgImage.getOrgID() + "'," + "IMAGE_TYPE = " + Integer.toString(orgImage.getImageType()) + "," + "IMAGE_CLASSIFICATION = " + Integer.toString(orgImage.getImageClassification()) + "," + "REGISTRATION_DATE = " + "TO_DATE('" + orgImage.getRegistrationDate() + "', 'yyyy-MM-dd')," + "ISSUE_REASON = '" + orgImage.getIssueReason() + "'," + "MANAGED_ORG = '" + orgImage.getManagedOrg() + "'," + "REGISTRATION_REMARKS = '" + orgImage.getRegistrationRemarks() + "'," + "DISUSE_YN = " + DataConverter.toInt(orgImage.getDisuseYN()) + "," + "DISUSE_DATE = " + "TO_DATE('" + orgImage.getDisuseDate() + "', 'yyyy-MM-dd')," + "DISUSE_REASON = '" + orgImage.getDisuseReason() + "'," + "DISUSE_REMARKS = '" + orgImage.getDisuseRemarks() + "'," + "IMAGE_FILE_TYPE = '" + orgImage.getImageFileType() + "'," + "IMAGE = EMPTY_BLOB(), " + "SIZE_WIDTH = " + Integer.toString(orgImage.getSizeWidth()) + "," + "SIZE_HEIGHT = " + Integer.toString(orgImage.getSizeHeight()) + "," + "IMAGE_ORDER = " + Integer.toString(orgImage.getImageOrder()) + " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
            bResult = super.m_connectionBroker.excuteQuery(strQuery);
            if(!bResult)
            {
                super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            super.m_connectionBroker.clearQuery();
            strQuery = "SELECT " + strOrgImageColumns + " FROM " + strOrgImageTable + " WHERE IMAGE_ID = '" + orgImage.getImageID() + "'";
            bResult = super.m_connectionBroker.excuteQuery(strQuery);
            if(!bResult)
            {
                super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            resultSet = super.m_connectionBroker.getResultSet();
            bResult = updateOrgImageInfo(resultSet, orgImage);
            if(!bResult)
            {
                super.m_connectionBroker.rollback();
                super.m_connectionBroker.clearQuery();
                return bReturn;
            }
            super.m_connectionBroker.clearQuery();
            super.m_connectionBroker.commit();
            bReturn = true;
        }
        return bReturn;
    }

    private boolean updateOrgImageInfo(ResultSet resultSet, OrgImage orgImage)
    {
        //ConnectionParam connectionParam = null;
        OutputStream outStream = null;
        boolean bReturn = false;
        java.sql.Blob imageBlob = null;
        //int nMethod = 0;
        //connectionParam = super.m_connectionBroker.getConnectionParam();
        //nMethod = connectionParam.getMethod();
        try
        {
            while(resultSet.next()) 
            {
                imageBlob = resultSet.getBlob(OrgImageTableMap.getColumnName(14));
                if(imageBlob != null)
                {
                    outStream = imageBlob.setBinaryStream(1L);
/*                    
                    if(connectionParam.getWASType() == 1)
                        outStream = ((OracleBlob)imageBlob).getBinaryOutputStream();
                    else
                        outStream = ((BLOB)imageBlob).getBinaryOutputStream();
*/                        
                    if(outStream != null)
                    {
                        outStream.write(orgImage.getImage());
                        outStream.close();
                    } else
                    {
                        super.m_lastError.setMessage("Fail to get blob data.", "OrgUtil.updateOrgImageInfo.Blob.getBinayOutPutStream.", "");
                        return bReturn;
                    }
                }
            }
            bReturn = true;
        }
        catch(SQLException e)
        {
            super.m_lastError.setMessage("Fail to update organization image information.", "OrgUtil.updateOrgImageInfo.next", e.getMessage());
            return bReturn;
        }
        catch(IOException e)
        {
            super.m_lastError.setMessage("Fail to update organization image information.", "OrgUtil.updateOrgImageInfo.next", e.getMessage());
            return bReturn;
        }
        return bReturn;
    }
    
    private List<Classification> processDataClassList(ResultSet resultSet, int nType)
    {
        List<Classification> classificationList = null;
        if(resultSet == null)
        {
            m_lastError.setMessage("NullPoint ResultSet.", "OrgUtil.processDataClassList", "");
            return null;
        }
       	classificationList = new ArrayList<Classification>();
       	try
       	{
       	    Classification classification;
       	    for(; resultSet.next(); classificationList.add(classification))
       	    {
        	classification = new Classification();
                classification.setClassificationID(getString(resultSet, ClassificationTableMap.getColumnName(0)));
                classification.setClassificationName(getString(resultSet, ClassificationTableMap.getColumnName(1)));
                classification.setRetentionDate(getString(resultSet, ClassificationTableMap.getColumnName(2)));
                classification.setClassificationParentID(getString(resultSet, ClassificationTableMap.getColumnName(3)));
                classification.setCompID(getString(resultSet, ClassificationTableMap.getColumnName(4)));
                classification.setDescription(getString(resultSet, ClassificationTableMap.getColumnName(5)));
                classification.setClassificationCode(getString(resultSet, ClassificationTableMap.getColumnName(6)));
                if(nType == 1)
                {
                    boolean bHasChild = getBoolean(resultSet, FavoriteClassificationTableMap.getColumnName(2));
                    if(bHasChild)
                        classification.setHasChild("Y");
                    else
                        classification.setHasChild("N");
                }
            }

        }
        catch(Exception e)
        {
            m_lastError.setMessage("Fail to make classifications.", "OrgUtil.processDataClassList", e.getMessage());
            return null;
        }
        return classificationList;
    }

    public List<Classification> getRootClassificationList(String compId)
    {
	if("".equals(m_strClassificationTable)) {
	    setClassificationTable();
	}

	String strClassificationID = "ROOT";
	List<Classification> classificationList = null;
        boolean bResult = false;
        String strQuery = "";
        bResult = m_connectionBroker.openConnection();
        if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        strQuery = "SELECT " + m_strClassificationColumns + " FROM " + m_strClassificationTable + 
        	" WHERE COMP_ID=" + "'" + compId + "'" + " AND CLASSIFICATION_PARENT_ID=" + "'" + strClassificationID + "'" + " ORDER BY CLASSIFICATION_ID";
        bResult = m_connectionBroker.excuteQuery(strQuery);
        if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        }
        classificationList = processDataClassList(m_connectionBroker.getResultSet(), 0);
        if(classificationList == null)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return null;
        } else
        {
            m_connectionBroker.clearConnectionBroker();
            return classificationList;
        }
    }
    
    /**
     * 주어진 ID를 가지는 사용자 정보 
     * @param strUserID
     * @return IUser
     */
    public UserVO getUserByID(String strUserID)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}

	boolean 	bResult = false;
	String 		strQuery = "";
	List<UserVO>	userVOList = null;
	UserVO		userVO = null;
	int			nSize = 0;

	bResult = m_connectionBroker.openConnection();
	if (!bResult)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	strQuery = "SELECT " + m_strDetailUserColumns +
	" FROM " + m_strDetailUserTable +
	" WHERE USER_ID = ?";
	//"   AND IS_DELETED = " + INOFFICE;

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if (bResult == false) 
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.setString(1, strUserID);

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	nSize = userVOList.size();
	if (nSize != 1)
	{			
	    m_lastError.setMessage("Fail to get correct user infomation.", 
		    "OrgUtil.getUserByID.LinkedList.size(not unique)", 
	    "");
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVO = userVOList.get(0);

	m_connectionBroker.clearConnectionBroker();	 

	return userVO;		
    }

    /**
     * 주어진 UID를 가지는 사용자 정보 
     * @param strUserUID
     * @return IUser
     */
    public UserVO getUserByUID(String strUserUID)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}

	boolean 	bResult = false;
	String 		strQuery = "";
	List<UserVO>	userVOList = null;
	UserVO		userVO = null;
	int		nSize = 0;

	bResult = m_connectionBroker.openConnection();
	if (bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	strQuery = "SELECT " + m_strDetailUserColumns +
	" FROM " + m_strDetailUserTable +
	" WHERE USER_UID = ?" +
	"   AND IS_DELETED = " + INOFFICE;

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.setString(1, strUserUID);

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	nSize = userVOList.size();
	if (nSize != 1)
	{			
	    m_lastError.setMessage("Fail to get correct user infomation.", 
		    "OrgUtil.getUserByUID.LinkedList.size(not unique)", 
	    "");
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVO = userVOList.get(0);

	m_connectionBroker.clearConnectionBroker();	 

	return userVO;		
    }

    /**
     * 주어진 이름을 가진 사용자 정보 
     * @param strUserName 사용자 이름 
     * @param nScope 검색 범위 (0:재직자 , 1:휴직자, 2:퇴직자, 3:재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
     * @return IUser
     */
    public List<UserVO> getUsersByName(String strUserName, int nScope)
    {
	return getUsersByNameInCaseSensitive(strUserName, nScope);
    }

    /**
     * 주어진 이름을 가진 사용자 정보 
     * @param strUserName 	 사용자 이름
     * @param bCaseSensative 대소문자 구분 여부 (true:대소문자 구분, false:대소문자 무시) 
     * @return IUser
     */
    public List<UserVO> getUsersByName(String strUserName, boolean bCaseSensative)
    {
	if (bCaseSensative == true) 	// 대소문자 구분
	{
	    return getUsersByNameInCaseSensitive(strUserName, SEARCH_SCOPE_INOFFICE);	
	}
	else							// 대소문자 무시
	{
	    return getUsersByNameInCaseInsensitive(strUserName, SEARCH_SCOPE_INOFFICE);		
	}
    }

    /**
     * 주어진 이름을 가진 사용자 정보 
     * @param strUserName 	 사용자 이름
     * @param bCaseSensative 대소문자 구분 여부 (true:대소문자 구분, false:대소문자 무시) 
     * @param nScope 검색 범위 (0:재직자 , 1:휴직자, 2:퇴직자, 3:재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
     * @return IUser
     */
    public List<UserVO> getUsersByName(String strUserName, boolean bCaseSensative, int nScope)
    {
	if (bCaseSensative == true) 	// 대소문자 구분
	{
	    return getUsersByNameInCaseSensitive(strUserName, nScope);	
	}
	else							// 대소문자 무시
	{
	    return getUsersByNameInCaseInsensitive(strUserName, nScope);		
	}	
    }
    
    /**
     * 주어진 이름을 가진 사용자 정보.(대소문자 구분) 
     * @param strUserName 사용자 이름
     * @param nScope 검색 범위 (0:재직자 , 1:휴직자, 2:퇴직자, 3:재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
     * @return IUser
     */
    private List<UserVO> getUsersByNameInCaseSensitive(String strUserName, int nScope)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}

	boolean 	bResult = false;
	String 		strQuery = "";
	String 		strSearchUserName = "";
	List<UserVO>	userVOList = null;
	//int 		nCount = 0;

	if (strUserName == null || strUserName.length() == 0)
	{
	    m_lastError.setMessage("Fail to get user name.",
		    "OrgUtil.getUsersByNameInCaseSensitive.Empty User Name.",
	    "");
	    return null;
	}

	bResult = m_connectionBroker.openConnection();
	if (bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	if (!"".equals(strUserName))
	{
	    strSearchUserName = "%" + strUserName + "%";
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE (USER_NAME LIKE ? OR "+
	    "        USER_OTHER_NAME LIKE ?) " +
	    "  AND " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";
	}
	else
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";	
	}
/*	
	// count the number of '*'
	if (strUserName.indexOf(SEARCH_LIKE) != -1)
	{
	    nCount = DataConverter.getFindCount(strUserName, SEARCH_LIKE);
	}

	if (strUserName.length() > 0 && nCount != strUserName.length())	     // 모두 * 가 입력되지 않은 경우 
	{
	    if (strUserName.indexOf(SEARCH_LIKE) != -1)
	    {
		// replace search format
		strSearchUserName = getSearchFormat(strUserName);

		strQuery = "SELECT " + m_strDetailUserColumns +
		" FROM " + m_strDetailUserTable +
		" WHERE (USER_NAME LIKE ? OR "+
		"        USER_OTHER_NAME LIKE ?) " +
		"  AND " + getScopeQuery(nScope) +
		"  ORDER BY USER_ORDER, USER_NAME";
	    }
	    else
	    {
		strSearchUserName = strUserName;

		strQuery = "SELECT " + m_strDetailUserColumns +
		" FROM " + m_strDetailUserTable +
		" WHERE (USER_NAME = ? OR "+
		"        USER_OTHER_NAME = ?) " +
		"  AND " + getScopeQuery(nScope) +
		"  ORDER BY USER_ORDER, USER_NAME";
	    }
	}
	else
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";	
	}
*/
	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	//if (strUserName.length() > 0 && nCount != strUserName.length())	
	if (strUserName.length() > 0)	
	{
	    m_connectionBroker.setString(1, strSearchUserName);
	    m_connectionBroker.setString(2, strSearchUserName);
	}

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.clearConnectionBroker();	 

	return userVOList;				
    }

    /**
     * 주어진 이름을 가진 사용자 정보.(대소문자 무시) 
     * @param strUserName 사용자 이름 
     * @param nScope 검색 범위 (0:재직자 , 1:휴직자, 2:퇴직자, 3:재직자 + 휴직자, 4:재직자 + 휴직자 + 퇴직자)
     * @return IUser
     */
    private List<UserVO> getUsersByNameInCaseInsensitive(String strUserName, int nScope)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}

	boolean 	bResult = false;
	String 		strQuery = "";
	String 		strSearchUserName = "";
	String 		strUCUserName = "";
	List<UserVO>	userVOList = null;
	//int 		nCount = 0;

	if (strUserName == null || strUserName.length() == 0)
	{
	    m_lastError.setMessage("Fail to get user name.",
		    "OrgUtil.getUsersByNameInCaseInsensitive.Empty User Name.",
	    "");
	    return null;
	}

	strUCUserName = strUserName.toUpperCase();

	bResult = m_connectionBroker.openConnection();
	if (bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	if (!"".equals(strUCUserName))
	{
	    strSearchUserName = "%" + strUCUserName + "%";
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE (UPPER(USER_NAME) LIKE ? OR "+
	    "        UPPER(USER_OTHER_NAME) LIKE ?)" +
	    "  AND " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";
	}
	else
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";	
	}
/*	
	// count the number of '*'
	if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
	{
	    nCount = DataConverter.getFindCount(strUCUserName, SEARCH_LIKE);
	}

	if (strUCUserName.length() > 0 && nCount != strUCUserName.length())	     // 모두 * 가 입력되지 않은 경우 
	{
	    if (strUCUserName.indexOf(SEARCH_LIKE) != -1)
	    {
		// replace search format
		strSearchUserName = getSearchFormat(strUCUserName);

		strQuery = "SELECT " + m_strDetailUserColumns +
		" FROM " + m_strDetailUserTable +
		" WHERE (UPPER(USER_NAME) LIKE ? OR "+
		"        UPPER(USER_OTHER_NAME) LIKE ?)" +
		"  AND " + getScopeQuery(nScope) +
		"  ORDER BY USER_ORDER, USER_NAME";
	    }
	    else
	    {
		strSearchUserName = strUCUserName;

		strQuery = "SELECT " + m_strDetailUserColumns +
		" FROM " + m_strDetailUserTable +
		" WHERE (UPPER(USER_NAME) = ? OR "+
		"        UPPER(USER_OTHER_NAME) = ?) " +
		"  AND " + getScopeQuery(nScope) +
		"  ORDER BY USER_ORDER, USER_NAME";
	    }
	}
	else
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE " + getScopeQuery(nScope) +
	    "  ORDER BY USER_ORDER, USER_NAME";	
	}
*/
	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	//if (strUCUserName.length() > 0 && nCount != strUCUserName.length())
	if (strUCUserName.length() > 0)
	{
	    m_connectionBroker.setString(1, strSearchUserName);
	    m_connectionBroker.setString(2, strSearchUserName);
	}

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.clearConnectionBroker();	 

	return userVOList;				
    }
    
    /**
     * 검색 범위에 대한 query를 만들어 주는 부분.
     * @param nScope 검색 범위
     * @return String 
     */
    private String getScopeQuery(int nScope) 
    {
	String strQuery = "";

	switch (nScope) {
	    case SEARCH_SCOPE_INOFFICE:
		strQuery = " IS_DELETED = " + INOFFICE;
		break;
	    case SEARCH_SCOPE_SUSPENSION :
		strQuery = " IS_DELETED = " + SUSPENSION;
		break;
	    case SEARCH_SCOPE_RETIREMENT :
		strQuery = " IS_DELETED = " + RETIREMENT;
		break;
	    case SEARCH_SCOPE_INOFFICE_N__SUSPENSION :
		strQuery = " IS_DELETED < " + RETIREMENT;
		strQuery += " AND ";
	    default:
		strQuery += " IS_DELETED > -1 ";
	}

	return strQuery;
    }	
    
    /**
     * 주어진 사용자의 겸직 사용자 정보 
     * @param strUserID 사용자 ID
     * @return IUsers
     */
    public List<UserVO> getConcurrentUsersByID(String strUserID)
    {
	EmployeeHandler employeeHandler = new EmployeeHandler(m_connectionBroker.getConnectionParam());
	Employees	employees = null;
	List<UserVO>	userVOList = null;

	if (strUserID == null || strUserID.length() == 0)
	{
	    m_lastError.setMessage("Fail to get User ID",
		    "OrgUtil.getRelatedUserByID.Empty User ID.",
	    "");
	    return userVOList;
	}

	employees = employeeHandler.getRelatedEmployeesByID(strUserID);
	if (employees == null)
	{
	    m_lastError.setMessage(employeeHandler.getLastError());
	    return userVOList;
	}

	userVOList = new ArrayList<UserVO>();

	String[] displayPositionOrderA = displayPositionOrder.split("\\^");
	int displayPositionOrderSize = displayPositionOrderA.length;

	for (int i = 0 ; i < employees.size() ; i++)
	{
	    Employee employee = employees.get(i);
	    
	    if(!(employee.getIsConcurrent())) {
		continue;
	    }
	    
	    UserVO userVO = new UserVO();

	    // set information		
	    userVO.setUserID(employee.getUserID());
	    userVO.setUserName(employee.getUserName());
	    userVO.setUserUID(employee.getUserUID());
	    userVO.setGroupID(employee.getGroupID());
	    userVO.setGroupName(employee.getGroupName());
	    userVO.setCompID(employee.getCompID());
	    userVO.setCompName(employee.getCompName());
	    userVO.setDeptID(employee.getDeptID());
	    userVO.setDeptName(employee.getDeptName());
	    userVO.setPartID(employee.getPartID());
	    userVO.setPartName(employee.getPartName());
	    userVO.setOrgDisplayName(employee.getOrgDisplayName());
	    userVO.setGradeCode(employee.getGradeCode());
	    userVO.setGradeName(employee.getGradeName());
	    userVO.setGradeAbbrName(employee.getGradeAbbrName());
	    userVO.setGradeOrder(employee.getGradeOrder());
	    userVO.setTitleCode(employee.getTitleCode());
	    userVO.setTitleName(employee.getTitleName());
	    userVO.setTitleOrder(employee.getTitleOrder());
	    userVO.setPositionCode(employee.getPositionCode());
	    userVO.setPositionName(employee.getPositionName());
	    userVO.setPositionAbbrName(employee.getPositionAbbrName());
	    userVO.setPositionOrder(employee.getPositionOrder());
	    userVO.setUserOrder(employee.getUserOrder());
	    userVO.setConcurrent(employee.getIsConcurrent());
	    userVO.setProxy(employee.getIsProxy());
	    userVO.setDelegate(employee.getIsDelegate());
	    userVO.setExistence(employee.getIsExistence());
	    userVO.setUserRID(employee.getUserRID());
	    userVO.setRoleCodes(employee.getRoleCodes());

	    String displayPosition = "";
	    for(int p=0; p< displayPositionOrderSize; p++) {
		String tempPosition = "";
		if("optionalGTP".equals(displayPositionOrderA[p])) {		//통합직함
		    tempPosition = userVO.getOptionalGTPName();
		}
		else if("position".equals(displayPositionOrderA[p])) {		//직위
		    tempPosition = userVO.getPositionName();
		}
		else if("grade".equals(displayPositionOrderA[p])) {		//직급
		    tempPosition = userVO.getGradeName();
		}
		else if("title".equals(displayPositionOrderA[p])) {		//직책
		    tempPosition = userVO.getTitleName();
		}
		else if("duty".equals(displayPositionOrderA[p])) {		//직무
		    tempPosition = userVO.getDutyName();
		}
		if(tempPosition != null && !"".equals(tempPosition.trim())) {
		    displayPosition = tempPosition;
		    break;
		}
	    }
	    userVO.setDisplayPosition(displayPosition);

	    userVOList.add(userVO);
	}

	return userVOList;								
    }

    
    /**
     * 주어진 부서 ID를 가지는 사용자 정보 
     * @param strDeptID 부서ID
     * @param nOrgType  조직 Type
     * @return IUsers
     */
    public List<UserVO> getUserListByDeptID(String strDeptID, int nOrgType)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}
	
	boolean 	bResult = false;
	String 		strQuery = "";
	String		strSortData = "";
	List<UserVO>	userVOList = null;

	bResult = m_connectionBroker.openConnection();
	if (bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	strSortData = getSortData(strDeptID);

	if (nOrgType == 9)
	{	
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE DEPT_ID = ?" +
	    "  AND IS_DELETED = " + INOFFICE +
	    "  ORDER BY PART_ID DESC, " + strSortData;
	}
	else if (nOrgType == 3)
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable + 
	    " WHERE PART_ID = ?" +
	    " AND IS_DELETED = " + INOFFICE +
	    " ORDER BY " + strSortData;
	}
	else
	{
	    strQuery = "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE DEPT_ID = ?" +
	    "  AND IS_DELETED = " + INOFFICE +
	    "  AND (PART_ID IS NULL OR PART_ID = '') " +
	    "  ORDER BY " + strSortData;   
	}

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.setString(1, strDeptID);

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.clearConnectionBroker();	 

	return userVOList;				
    }

    /**
     * 주어진 부서의 하위의 특정한 Role 사용자 정보 
     * @param strOrgID 조직 ID
     * @param strRoleCode RoleCode
     * @param nOrgType 조직 Type   (0:최상위조직 , 1:회사 , 2:부서 , 3:부서(파트포함) , 4:파트)
     * @return Employees
     */
    public List<UserVO> getUserListByRoleCode(String strOrgID, String strRoleCode, int nOrgType)
    {
	if("".equals(m_strDetailUserTable)) {
	    setUserTable();
	}

	List<UserVO>	userVOList = null;
	boolean  	bResult = false;
	String	  	strQuery = "";
	String    	strSortData = "";

	bResult = m_connectionBroker.openConnection();
	if (bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	strSortData = getSortData(strOrgID);

	if (nOrgType == 0)					// Group Search
	{
	    strQuery =  "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE GROUP_ID = ?" +
	    " 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
	    " 	AND IS_DELETED = " + INOFFICE +
	    " ORDER BY " + strSortData;
	}
	else if (nOrgType == 1)					// Company Search
	{
	    strQuery =  "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE COMP_ID = ?" +
	    " 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
	    " 	AND IS_DELETED = " + INOFFICE +
	    " ORDER BY " + strSortData;
	}
	else if (nOrgType == 2)					// Department Search
	{
	    strQuery =  "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE DEPT_ID = ?" +
	    " 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
	    " 	AND IS_DELETED = " + INOFFICE +
	    "   AND (PART_ID IS NULL OR PART_ID = '') " +
	    " ORDER BY " + strSortData;
	}
	else if (nOrgType == 3)					// Department Search(Include Part)
	{
	    strQuery =  "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE DEPT_ID = ?" +
	    " 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
	    " 	AND IS_DELETED = " + INOFFICE +
	    " ORDER BY " + strSortData;
	}
	else							// Part Search
	{
	    strQuery =  "SELECT " + m_strDetailUserColumns +
	    " FROM " + m_strDetailUserTable +
	    " WHERE PART_ID = ? " +
	    " 	AND ROLE_CODE LIKE '%" + strRoleCode +"%'" +
	    " 	AND IS_DELETED = " + INOFFICE +
	    " ORDER BY " + strSortData;
	}

	bResult = m_connectionBroker.prepareStatement(strQuery);
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.setString(1, strOrgID);

	bResult = m_connectionBroker.executePreparedQuery();
	if(bResult == false)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	userVOList = processDataUserList(m_connectionBroker.getResultSet());
	if (userVOList == null)
	{
	    m_lastError.setMessage(m_connectionBroker.getLastError());
	    m_connectionBroker.clearConnectionBroker();
	    return null;
	}

	m_connectionBroker.clearConnectionBroker();
	return userVOList;
    }
    
    
    /**
     * 사용자 정렬 정보 반환.
     * @param strOrgID 조직 정보
     * @return String
     */
    private String getSortData(String strOrgID)
    {
	ResultSet   resultSet = null;
	boolean 	bResult = false;
	String 		strSortData = "";
	String 		strCompID = "";
	String 		strQuery = "";

	if (m_connectionBroker.IsConnectionClosed() == false)
	{
	    try
	    {
		strQuery = "SELECT COMPANY_ID FROM TCN_ORGANIZATIONINFORMATION " +
		" WHERE ORG_ID = '" + strOrgID + "'";

		bResult = m_connectionBroker.executeQuery(strQuery);
		if(bResult == false)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearQuery();
		    return strSortData;
		}

		resultSet = m_connectionBroker.getResultSet();
		if (resultSet == null)
		{
		    m_lastError.setMessage(m_connectionBroker.getLastError());
		    m_connectionBroker.clearQuery();
		    return strSortData;
		}

		while(resultSet.next())
		{
		    strCompID = resultSet.getString("COMPANY_ID");
		}


		m_connectionBroker.clearQuery();		 

		if (strCompID != null && strCompID.length() > 0)
		{
		    strQuery = "SELECT COMP_ID, STRING_VALUE FROM TCN_OPTIONINFORMATION " +
		    " WHERE OPTION_ID = 'AIOPT63'" +
		    " AND COMP_ID IN ('DEFAULT', '" + strCompID + "')";

		    bResult = m_connectionBroker.executeQuery(strQuery);
		    if(bResult == false)
		    {
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearQuery();
			return strSortData;
		    }

		    resultSet = m_connectionBroker.getResultSet();
		    if (resultSet == null)
		    {
			m_lastError.setMessage(m_connectionBroker.getLastError());
			m_connectionBroker.clearQuery();
			return strSortData;
		    }		

		    String strCompOption = "";
		    String strDefaultOption = "";

		    while (resultSet.next())
		    {
			String strOwnerID = resultSet.getString("COMP_ID");
			String strOptionValue = resultSet.getString("STRING_VALUE");

			if (strOwnerID != null && strOwnerID.length() > 0 && 
				strOptionValue != null && strOptionValue.length() > 0)
			{
			    if (strOwnerID.compareTo(strCompID) == 0)
			    {
				strCompOption = strOptionValue;	
			    }
			    else if (strOwnerID.compareTo("DEFAULT") == 0)
			    {
				strDefaultOption = strOptionValue;
			    }
			}
		    }

		    if (strCompOption.length() > 0)
		    {
			strSortData = strCompOption;	
		    }
		    else if (strDefaultOption.length() > 0)
		    {
			strSortData = strDefaultOption;
		    }
		    else
		    {
			strSortData = "USER_ORDER";
		    }			 		
		}
	    }
	    catch(SQLException e)
	    {
		m_lastError.setMessage("Fail to get sort data.",
			"OrgUtil.getSortData.SQLException.",
			e.getMessage());
		return strSortData;
	    }
	}
	else
	{
	    m_lastError.setMessage("Fail to get sort data.",
		    "OrgUtil.getSortData.Closed Connection",
	    "");

	}

	if (strSortData == null || strSortData.length() == 0)
	    strSortData = "USER_ORDER";

	strSortData += ", USER_NAME ";

	return strSortData;
    }
    
    /**
     * 
     * <pre> 
     *  전자결재용 팩스와 주소를 변경한다.
     * </pre>
     * @param userVO
     * @return
     * @see  
     *
     */
    public boolean updateUserAppOfficeInfo(UserVO userVO) throws Exception {
	boolean bReturn = false;
	
	String m_strUserAddrTable = TableDefinition.getTableName(5);

	boolean bResult = false;
	String strQuery = "";
	
	bResult = m_connectionBroker.openConnection();
        if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return bReturn;
        }
        
	if(bResult)
	{
	    super.m_connectionBroker.setAutoCommit(false);
	    strQuery = "UPDATE " + m_strUserAddrTable + " SET OFFICE_FAX = '" + userVO.getOfficeFax() + "'," + 
	    "OFFICE_ADDR = '" + userVO.getOfficeAddr() + "'," +
	    "OFFICE_DETAIL_ADDR = '" + userVO.getOfficeDetailAddr() + "'," + 
	    "OFFICE_ZIPCODE = '" + userVO.getOfficeZipCode() + "' " + 
	    " WHERE USER_ID = '" + userVO.getUserUID() + "'";
	    
	    bResult = super.m_connectionBroker.excuteQuery(strQuery);
	    if(!bResult)
	    {
		super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
		super.m_connectionBroker.rollback();
		super.m_connectionBroker.clearQuery();
		return bReturn;
	    }

	    super.m_connectionBroker.clearQuery();
	    super.m_connectionBroker.commit();
	    bReturn = true;
	}

	
	
	return bReturn;
    }
    
    /*
     * 우편번호를 등록한다.
    */
    public int insertZipcode(List<Map<String,String>> zipcodeList) throws Exception
    {
    	boolean bResult = false;
    	String strQuery = "";
    	
    	bResult = m_connectionBroker.openConnection();
    	
    	if(!bResult)
        {
            m_lastError.setMessage(m_connectionBroker.getLastError());
            m_connectionBroker.clearConnectionBroker();
            return 0;
        }else {
    		super.m_connectionBroker.setAutoCommit(false);
    		
    		for(int i=0; i < zipcodeList.size(); i++){
    			Map<String,String> zipcodeMap = zipcodeList.get(i);
    			
    			strQuery = "INSERT INTO TCN_ZIPCODE(ZIPCODE,SIDO,GUGUN,DONG,BUNJI,SEQ) " +
    					" VALUES(?,?,?,?,?,?)";
    			
    			bResult = m_connectionBroker.prepareStatement(strQuery);
    			if(bResult == false)
    			{
    			    m_lastError.setMessage(m_connectionBroker.getLastError());
    			    m_connectionBroker.clearConnectionBroker();
    			    return 0;
    			}
    			
    			m_connectionBroker.setString(1, zipcodeMap.get("ZIPCODE"));
    			m_connectionBroker.setString(2, zipcodeMap.get("SIDO"));
    			m_connectionBroker.setString(3, zipcodeMap.get("GUGUN"));
    			m_connectionBroker.setString(4, zipcodeMap.get("DONG"));
    			m_connectionBroker.setString(5, zipcodeMap.get("BUNJI"));
    			m_connectionBroker.setString(6, zipcodeMap.get("SEQ"));
    			
    			bResult = m_connectionBroker.executePreparedQuery();
    			
    		    if(!bResult)
    		    {
    		    	if(super.m_connectionBroker.getLastError().indexOf("ORA-00001") > -1){
    		    		continue;
    		    	}else{
    		    		super.m_lastError.setMessage(super.m_connectionBroker.getLastError());
    	    			super.m_connectionBroker.rollback();
    	                m_connectionBroker.clearConnectionBroker();
    	    			return 0;
    		    	}
    		    }
    		}
    		
    		super.m_connectionBroker.commit();
    		m_connectionBroker.clearConnectionBroker();
	    }
    	
    	return 1;
    } 
}
