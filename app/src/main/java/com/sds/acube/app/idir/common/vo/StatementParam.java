package com.sds.acube.app.idir.common.vo;

/**
 * StatementParam.java
 * 2003-03-05
 *
 * PreparedStatement 또는 CallableStatement에 설정할 Parameter Class
 *  
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class StatementParam
{
	public static final int	SP_PARAMETER_INDEX_DEFAULT = 0;
	
	public static final int	SP_TYPE_ARRAY = 0;
	public static final int	SP_TYPE_ASCII_STREAM = 1;
	public static final int	SP_TYPE_BIG_DECIMAL = 2;
	public static final int	SP_TYPE_BINARY_STREAM = 3;
	public static final int	SP_TYPE_BLOB = 4;
	public static final int	SP_TYPE_BOOLEAN = 5;
	public static final int	SP_TYPE_BYTE = 6;
	public static final int	SP_TYPE_BYTES = 7;
	public static final int	SP_TYPE_CHARACTER_STREAM = 8;
	public static final int	SP_TYPE_CLOB = 9;
	public static final int	SP_TYPE_DATE = 10;
	public static final int	SP_TYPE_DATE_CALENDAR = 11;
	public static final int	SP_TYPE_DOUBLE = 12;
	public static final int	SP_TYPE_FLOAT = 13;
	public static final int	SP_TYPE_INT = 14;
	public static final int	SP_TYPE_LONG = 15;
	public static final int	SP_TYPE_NULL = 16;
	public static final int	SP_TYPE_NULL_TYPE_NAME = 17;
	public static final int	SP_TYPE_OBJECT = 18;
	public static final int	SP_TYPE_OBJECT_TARGET_SQL_TYPE = 19;
	public static final int	SP_TYPE_OBJECT_TARGET_SQL_TYPE_SCALE = 20;
	public static final int	SP_TYPE_REF = 21;
	public static final int	SP_TYPE_SHORT = 22;
	public static final int	SP_TYPE_STRING = 23;
	public static final int	SP_TYPE_TIME = 24;
	public static final int	SP_TYPE_TIME_CALENDAR = 25;
	public static final int	SP_TYPE_TIMESTAMP = 26;
	public static final int	SP_TYPE_TIMESTAMP_CALENDAR = 27;
	public static final int	SP_TYPE_UNICODE_STREAM = 28;
	public static final int	SP_TYPE_URL = 29;

	private int		m_nParameterIndex;
	private int		m_nType;
	private Object	m_objValue;
	private Object	m_objValue2;
	private Object	m_objValue3;
	
	/**
	 * Constructor
	 * @deprecated 메모리 사용이 비효율적이고, wrapping하여 얻을 수 있는 장점이 적어 Deprecated 됩니다.
	 */
	public StatementParam()
	{
		m_nParameterIndex = SP_PARAMETER_INDEX_DEFAULT;
		m_nType = SP_TYPE_STRING;
		m_objValue = null;
		m_objValue2 = null;
		m_objValue3 = null;
	}
	
	/**
	 * Returns the ParameterIndex.
	 * @return int
	 */
	public int getParameterIndex()
	{
		return m_nParameterIndex;
	}

	/**
	 * Returns the Type.
	 * @return int
	 */
	public int getType()
	{
		return m_nType;
	}

	/**
	 * Returns the Value.
	 * @return Object
	 */
	public Object getValue()
	{
		return m_objValue;
	}

	/**
	 * Returns the Value2.
	 * @return Object
	 */
	public Object getValue2()
	{
		return m_objValue2;
	}

	/**
	 * Returns the Value3.
	 * @return Object
	 */
	public Object getValue3()
	{
		return m_objValue3;
	}

	/**
	 * Sets the ParameterIndex.
	 * @param nParameterIndex The ParameterIndex to set
	 */
	public void setParameterIndex(int nParameterIndex)
	{
		m_nParameterIndex = nParameterIndex;
	}

	/**
	 * Sets the Type.
	 * @param nType The Type to set
	 */
	public void setType(int nType)
	{
		m_nType = nType;
	}

	/**
	 * Sets the Value.
	 * @param objValue The Value to set
	 */
	public void setValue(Object objValue)
	{
		m_objValue = objValue;
	}

	/**
	 * Sets the Value2.
	 * @param objValue2 The Value2 to set
	 */
	public void setValue2(Object objValue2)
	{
		m_objValue2 = objValue2;
	}

	/**
	 * Sets the Value3.
	 * @param objValue3 The Value3 to set
	 */
	public void setValue3(Object objValue3)
	{
		m_objValue3 = objValue3;
	}

}
