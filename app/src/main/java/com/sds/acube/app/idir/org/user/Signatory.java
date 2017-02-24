package com.sds.acube.app.idir.org.user;

/**
 * Signatory.java 2002-10-12
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class Signatory extends Employee
{
	/**
	 */
	private Substitutes 	m_substitutes = null; 
	private int			m_nSubstituteCount = 0;


	/**
	 * Sets the m_substitutes.
	 * @param m_substitutes The m_substitutes to set
	 */
	public void setSubstitutes(Substitutes substitutes) 
	{
		m_substitutes = substitutes;
	}
	
	/**
	 * Sets the m_substitutesCount.
	 * @param m_substitutuesCount The m_substitutuesCount to set
	 */
	public void setSubstituteCount(int nSustitutueCount)
	{
		m_nSubstituteCount = nSustitutueCount;	
	}
	

	/**
	 * Returns 대결자의 수.
	 * @return int
	 */
	public int getSubstituteCount()
	{
		return m_nSubstituteCount;
	}
						
	/**
	 * Returns 대결자 정보.
	 * @return Substitutes
	 */
	public Substitutes getSubstitutes() 
	{
		return m_substitutes;
	}	
}
