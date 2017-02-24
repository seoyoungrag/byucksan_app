package com.sds.acube.app.idir.org.user;

import java.io.Serializable;

/**
 * UserCommon.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserCommon 
{
	protected String m_strUserID = "";
	protected String m_strUserName = "";
	protected String m_strUserOtherName = "";
	protected String m_strUserUID = "";
	protected String m_strGroupID = "";
	protected String m_strGroupName = "";
	protected String m_strGroupOtherName = "";
	protected String m_strCompID = "";
	protected String m_strCompName = "";
	protected String m_strCompOtherName = "";
	protected String m_strDeptID = "";
	protected String m_strDeptName = "";
	protected String m_strDeptOtherName = "";
	protected String m_strPartID = "";
	protected String m_strPartName = "";
	protected String m_strPartOtherName = "";
	protected String m_strOrgDisplayName = "";
	protected String m_strOrgDisplayOtherName = "";
	
	/**
	 * Sets the m_strUserOtherName
	 * @param strUserOtherName The m_strUserOtherName to set
	 */
	public void setUserOtherName(String strUserOtherName) 
	{
		m_strUserOtherName = strUserOtherName;
	}
	
	/**
	 * Sets the m_strGroupOtherName
	 * @param strGroupOtherName The m_strGroupOtherName to set
	 */
	public void setGroupOtherName(String strGroupOtherName)
	{
		m_strGroupOtherName = strGroupOtherName;
	}
	
	/**
	 * Sets the m_strCompOtherName
	 * @param strCompOtherName The m_strCompOtherName to set
	 */
	public void setCompOtherName(String strCompOtherName)
	{
		m_strCompOtherName = strCompOtherName;
	}
	
	/**
	 * Sets the m_strDeptOtherName
	 * @param strDeptOtherName The m_strDeptOtherName to set
	 */
	public void setDeptOtherName(String strDeptOtherName)
	{
	   m_strDeptOtherName = strDeptOtherName;
	}
	
	/**
	 * Sets the m_strPartOtherName
	 * @param strPartOtherName The m_strPartOtherName to set
	 */
	public void setPartOtherName(String strPartOtherName)
	{
		m_strPartOtherName = strPartOtherName;
	}
	
	/**
	 * Sets the m_strOrgDisplayOtherName
	 * @param strOrgDisplayOtherName The m_strOrgDisplayOtherName to set
	 */
	public void setOrgDisplayOtherName(String strOrgDisplayOtherName)
	{
		m_strOrgDisplayOtherName = strOrgDisplayOtherName;
	}
	
	/**
	 * Sets the m_strCompID.
	 * @param strCompID The m_strCompID to set
	 */
	public void setCompID(String strCompID) 
	{
		m_strCompID = strCompID;
	}

	/**
	 * Sets the m_strCompName.
	 * @param strCompName The m_strCompName to set
	 */
	public void setCompName(String strCompName) 
	{
		m_strCompName = strCompName;
	}

	/**
	 * Sets the m_strDeptID.
	 * @param strDeptID The m_strDeptID to set
	 */
	public void setDeptID(String strDeptID) 
	{
		m_strDeptID = strDeptID;
	}

	/**
	 * Sets the m_strDeptName.
	 * @param strDeptName The m_strDeptName to set
	 */
	public void setDeptName(String strDeptName) 
	{
		m_strDeptName = strDeptName;
	}

	/**
	 * Sets the m_strGroupID.
	 * @param strGroupID The m_strGroupID to set
	 */
	public void setGroupID(String strGroupID) 
	{
		m_strGroupID = strGroupID;
	}

	/**
	 * Sets the m_strGroupName.
	 * @param strGroupName The m_strGroupName to set
	 */
	public void setGroupName(String strGroupName) 
	{
		m_strGroupName = strGroupName;
	}

	/**
	 * Sets the m_strOrgDisplayName.
	 * @param strOrgDisplayName The m_strOrgDisplayName to set
	 */
	public void setOrgDisplayName(String strOrgDisplayName) 
	{
		m_strOrgDisplayName = strOrgDisplayName;
	}

	/**
	 * Sets the m_strPartID.
	 * @param strPartID The m_strPartID to set
	 */
	public void setPartID(String strPartID) 
	{
		m_strPartID = strPartID;
	}

	/**
	 * Sets the m_strPartName.
	 * @param strPartName The m_strPartName to set
	 */
	public void setPartName(String strPartName) 
	{
		m_strPartName = strPartName;
	}

	/**
	 * Sets the m_strUserID.
	 * @param m_strUserID The m_strUserID to set
	 */
	public void setUserID(String strUserID) 
	{
		m_strUserID = strUserID;
	}

	/**
	 * Sets the m_strUserName.
	 * @param strUserName The m_strUserName to set
	 */
	public void setUserName(String strUserName) 
	{
		m_strUserName = strUserName;
	}

	/**
	 * Sets the m_strUserUID.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * Returns the m_strCompID.
	 * @return String
	 */
	public String getCompID() 
	{
		return m_strCompID;
	}

	/**
	 * Returns the m_strCompName.
	 * @return String
	 */
	public String getCompName() 
	{
		return m_strCompName;
	}

	/**
	 * Returns the m_strDeptID.
	 * @return String
	 */
	public String getDeptID() 
	{
		return m_strDeptID;
	}

	/**
	 * Returns the m_strDeptName.
	 * @return String
	 */
	public String getDeptName() 
	{
		return m_strDeptName;
	}

	/**
	 * Returns the m_strGroupID.
	 * @return String
	 */
	public String getGroupID() 
	{
		return m_strGroupID;
	}

	/**
	 * Returns the m_strGroupName.
	 * @return String
	 */
	public String getGroupName() 
	{
		return m_strGroupName;
	}

	/**
	 * Returns the m_strOrgDisplayName.
	 * @return String
	 */
	public String getOrgDisplayName() 
	{
		return m_strOrgDisplayName;
	}

	/**
	 * Returns the m_strPartID.
	 * @return String
	 */
	public String getPartID() 
	{
		return m_strPartID;
	}

	/**
	 * Returns the m_strPartName.
	 * @return String
	 */
	public String getPartName() 
	{
		return m_strPartName;
	}

	/**
	 * Returns the m_strUserID.
	 * @return String
	 */
	public String getUserID() 
	{
		return m_strUserID;
	}

	/**
	 * Returns the m_strUserName.
	 * @return String
	 */
	public String getUserName() 
	{
		return m_strUserName;
	}

	/**
	 * Returns the m_strUserUID.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
	
	/**
	 * Returns the m_strUserOtherName
	 * @return String
	 */
	public String getUserOtherName() 
	{
		return m_strUserOtherName;
	}
	
	/**
	 * Returns the m_strGroupOtherName
	 * @return String
	 */
	public String getGroupOtherName()
	{
		return m_strGroupOtherName;
	}
	
	/**
	 * Returns the m_strCompOtherName
	 * @return String
	 */
	public String getCompOtherName()
	{
		return m_strCompOtherName;
	}
	
	/**
	 * Returns the m_strDeptOtherName
	 * @return String
	 */
	public String getDeptOtherName()
	{
	   return m_strDeptOtherName;
	}
	
	/**
	 * Returns the m_strPartOtherName
	 * @return String
	 */
	public String getPartOtherName() 
	{
		return m_strPartOtherName;
	}
	
	/**
	 * Returns the m_strOrgDisplayOtherName
	 * @return String
	 */
	public String getOrgDisplayOtherName() 
	{
		return m_strOrgDisplayOtherName;
	}
}
