package com.sds.acube.app.idir.org.system;

/**
 * LegacyKeys.java
 * 2002-11-26
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import java.util.*;

public class LegacyKeys 
{
	private LinkedList m_lLegacyKeyList = null;
	
	public LegacyKeys()
	{
		m_lLegacyKeyList = new LinkedList();	
	}
		
	/**
	 * List에 LegacyKey를 더함.
	 * @param legacyKey LegacyKey 정보 
	 * @return boolean
	 */
	public boolean add(LegacyKey legacyKey)
	{
		return m_lLegacyKeyList.add(legacyKey);
	}
	
	/**
	 * LegacyKey 리스트의 size
	 * @return int 
	 */ 
	public int size()
	{
		return m_lLegacyKeyList.size();
	}
	
	/**
	 * LegacyKey 정보 
	 * @param  nIndex LegacyKey List Index
	 * @return LegacyKey
	 */
	public LegacyKey get(int nIndex)
	{
		return (LegacyKey)m_lLegacyKeyList.get(nIndex);
	}
	
	/**
	 * Legacy Key Set
	 * @param nIndex LegacyKey List Index
	 * @param legacyKey Legacy Key 정보 
	 * @return LegacyKey
	 */
	public LegacyKey set(int nIndex, LegacyKey legacyKey)
	{
		return (LegacyKey)m_lLegacyKeyList.set(nIndex, legacyKey);
	}
	
	/**
	 * Legacy Key Type 반환.
	 * @param  nIndex LegacyKey List Index
	 * @return int
	 */
	public int getLegacyKeyType(int nIndex) 
	{
		LegacyKey legacyKey = (LegacyKey)m_lLegacyKeyList.get(nIndex);
		return legacyKey.getLegacyKeyType();
	}

	/**
	 * Legacy Key 반환.
	 * @param  nIndex LegacyKey List Index
	 * @return String
	 */
	public String getLegacyKey(int nIndex) 
	{
		LegacyKey legacyKey = (LegacyKey)m_lLegacyKeyList.get(nIndex);
		return legacyKey.getLegacyKey();
	}
	
	/**
	 * 주어진 Key Type을 갖는 Legacy Key 정보 반환
	 * @param nKeyType Key Type 정보 
	 * @return LegacyKey
	 */
	public LegacyKey getLegacyKeybyKeyType(int nKeyType)
	{
		LegacyKey legacyKey = null;
		
		for (int i = 0 ; i < m_lLegacyKeyList.size() ; i++)
		{
			LegacyKey tempLegacyKey = (LegacyKey)m_lLegacyKeyList.get(i);
			if (tempLegacyKey.getLegacyKeyType() == nKeyType)
				legacyKey = tempLegacyKey;
		}
		
		return legacyKey;
	}
}
