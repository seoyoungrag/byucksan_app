package com.sds.acube.app.idir.common.constants;

/**
 * EventConstants.java
 * 2002-10-10
 * 
 * 이벤트 로그 관련 상수 정의
 * 
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public final class EventConstants
{
	public static final int EVENT_TABLE_GENERAL		= 0;
	public static final int EVENT_TABLE_MESSENGER	= 1;
	public static final int EVENT_TABLE_GEAR		= 2;
	public static final int EVENT_TABLE_ORG			= 3;
	public static final int EVENT_TABLE_STORAGE		= 4;
	public static final int EVENT_TABLE_LEGACY		= 5;
	public static final int EVENT_TABLE_COUNT		= 6;

	public static final String[] EVENT_TABLE_LIST =
	{
		"TGW_EVENT_LOG",
		"TGW_EVENT_LOG_MESSENGER",
		"TGW_EVENT_LOG_GEAR",
		"TGW_EVENT_LOG_ORG",
		"TGW_EVENT_LOG_STORAGE",
		"TGW_EVENT_LOG_LEGACY"
	};
	
	public static final int EVENT_TYPE_DEBUG		= 0;
	public static final int EVENT_TYPE_INFORMATION	= 1;
	public static final int EVENT_TYPE_WARNING		= 2;
	public static final int EVENT_TYPE_ERROR		= 3;
	public static final int EVENT_TYPE_COUNT		= 4;

	public static final String[] EVENT_TYPE_LIST = 
	{
		"디버그",
		"정보",
		"경고",
		"오류"
	};
	
	public static final int SERIOUSNESS_DEFAULT		= 0;
	
	public static final String[] SERIOUSNESS_LIST = 
	{
		"N/A"
	};
}
