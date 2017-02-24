package com.sds.acube.app.idir.common.constants;

/**
 * FormConstants.java
 * 2003-08-19
 *
 * 양식 관련 상수 정의
 *
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class FormConstants
{
	public static final String FORM_CLASS_SUBMIT = "D";					// 기안양식
	public static final String FORM_CLASS_ENFORCE = "E";				// 시행 양식
	public static final String FORM_CLASS_LEGACY = "L";					// 기간 연동 양식
	public static final String FORM_CLASS_INSPECTION = "I";				// 감사 양식
	public static final String FORM_CLASS_STANDART_ENFORM = "S";		// 표준시행양식(기관간유통)
    public static final String FORM_CLASS_INSPECTION_OPINION = "IO";	// 감사 의견 양식

	public static final String FILE_TYPE_HUN = "gul";					// Editor : 훈민정음
	public static final String FILE_TYPE_HNC = "hwp";					// Editor : 아래아 한글
	public static final String FILE_TYPE_HANGUL_2002 = "han";			// Editor : 한글 2002
	public static final String FILE_TYPE_HANGUL_2004 = "hwp6";			// Editor : 한글 2004
	public static final String FILE_TYPE_WORD = "doc";					// Editor : MS word
	public static final String FILE_TYPE_HTML = "html";					// Editor : HTML
	public static final String FILE_TYPE_TEXT = "txt";					// Editor : Text

	public static final int FORM_USAGE_DRAFT_ENFORCE_CONVERT = 0;		// 시행변환 양식
	public static final int FORM_USAGE_DRAFT_ENFORCE_COMBINATION = 1;	// 시행문 겸용 양식
	public static final int FORM_USAGE_ENFORCE = 2;						// 표제부 양식
	public static final int FORM_USAGE_INTERNAL = 3;					// 내부 문서 양식

	public static final String FILE_USAGE_FORM = "F";					// 양식
	public static final String FILE_USAGE_LOGO = "L";					// 로고
	public static final String FILE_USAGE_SYMBOL = "S";					// 심볼

	public static final int APPROVAL_LINE_TYPE_PUBLIC = 0;				// 공용 결재 라인
	public static final int APPROVAL_LINE_TYPE_PRIVATE = 1;				// 개인 결재 라인

	public static final String BOOLEAN_VALUE_YES = "Y";
	public static final String BOOLEAN_VALUE_NO = "N";
}
