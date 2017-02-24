package com.sds.acube.app.idir.common.vo;

import java.util.LinkedList;
/**
 * @author administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ZipcodeListItems
{
	private int 		m_nZipcodeListItemCount;
	private LinkedList	m_llZipcodeListItems;

	public ZipcodeListItems()
	{
		m_llZipcodeListItems = new LinkedList();
	}
	
	public boolean addZipcodeListItem(ZipcodeListItem zipcodeListItem)
	{
		return m_llZipcodeListItems.add(zipcodeListItem);
	}
	
	public ZipcodeListItems getFormListItem(int nIndex)
	{
		if (nIndex < 0)
			return null;
		if (nIndex >= m_nZipcodeListItemCount)
			return null;
			
		return (ZipcodeListItems) m_llZipcodeListItems.get(nIndex);
	}
	
	public int getFormListItemCount()
	{
		return m_nZipcodeListItemCount;
	}
	
	public void setFormListItemCount(int nFormLIstItemCount)
	{
		m_nZipcodeListItemCount = nFormLIstItemCount;
	}
}
