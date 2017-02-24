package com.sds.acube.app.idir.common.vo;

/**
 * @author administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ZipcodeListItem
{
	private String m_strZipcode;
	private String m_strAddress;
	
	public ZipcodeListItem()
	{
		m_strZipcode = "";
		m_strAddress = "";
	}
	
	public String getZipcode()
	{
		return m_strZipcode;
	}

	public String getAddress()
	{
		return m_strAddress;
	}
	
	public void setZipcode(String strZipcode)
	{
		m_strZipcode = strZipcode;
	}

	public void setAddress(String strAddress)
	{
		m_strAddress = strAddress;
	}

}
