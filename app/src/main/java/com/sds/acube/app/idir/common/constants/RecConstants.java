package com.sds.acube.app.idir.common.constants;

/**
 * RecConstants.java
 * 2003-01-05
 *
 * 기록물철 관련 Constant 정의
 *
 * @author Jack
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public final class RecConstants
{
	public static final String REC_TYPE_GENERAL_DRAFT					= "1";
	public static final String REC_TYPE_GENERAL_ENFORCE					= "2";
	public static final String REC_TYPE_DRAWING_DRAFT					= "3";
	public static final String REC_TYPE_DRAWING_ENFORCE					= "4";
	public static final String REC_TYPE_PICTURE							= "5";
	public static final String REC_TYPE_RECORDING						= "6";
	public static final String REC_TYPE_CARD_REGISTRATION				= "7";
	public static final String REC_TYPE_CARD_TRANSFER					= "8";

	public static final String REC_ETC_TYPE								= "NNNNN";

	public static final String REC_BOOL_FALSE							= "0";
	public static final String REC_BOOL_TRUE							= "1";

	public static final String REC_RCV_STATE_INBOUND_NULL				= "A0";
	public static final String REC_RCV_STATE_INBOUND_SEND				= "A1";
	public static final String REC_RCV_STATE_INBOUND_ACCEPT				= "A2";
	public static final String REC_RCV_STATE_INBOUND_ARRIVE				= "A3";
	public static final String REC_RCV_STATE_INBOUND_FAIL				= "A4";
	public static final String REC_RCV_STATE_INBOUND_REQ_RESEND			= "A5";
	public static final String REC_RCV_STATE_INBOUND_NORMAL_RESEND		= "A6";
	public static final String REC_RCV_STATE_INBOUND_RECEIVE			= "A7";
	public static final String REC_RCV_STATE_INBOUND_PUBLIC				= "Ab";
	public static final String REC_RCV_STATE_INBOUND_CHARGER_ASSIGNED	= "Ac";
	public static final String REC_RCV_STATE_INBOUND_FLOW_COMPLETED		= "Ad";
	public static final String REC_RCV_STATE_INBOUND_CHARGER_REJECTED	= "Ae";
	public static final String REC_RCV_STATE_INBOUND_PUBLIC_REJECTED	= "Af";
	public static final String REC_RCV_STATE_INBOUND_PUBLIC_POSTED		= "Ag";
	public static final String REC_RCV_STATE_INBOUND_SHIFTED			= "Ah";
	public static final String REC_RCV_STATE_INBOUND_RETURNED			= "Ai";
	public static final String REC_RCV_STATE_INBOUND_ENFORCE_CANCELED	= "Aj";
	public static final String REC_RCV_STATE_INBOUND_PASS_THROUGH		= "Ak";
	public static final String REC_RCV_STATE_INBOUND_WAITING_SEND		= "Al";
	public static final String REC_RCV_STATE_OUTBOUND_NULL				= "B0";
	public static final String REC_RCV_STATE_OUTBOUND_SEND				= "B1";
	public static final String REC_RCV_STATE_OUTBOUND_ACCEPT			= "B2";
	public static final String REC_RCV_STATE_OUTBOUND_ARRIVE			= "B3";
	public static final String REC_RCV_STATE_OUTBOUND_FAIL				= "B4";
	public static final String REC_RCV_STATE_OUTBOUND_REQ_RESEND		= "B5";
	public static final String REC_RCV_STATE_OUTBOUND_NORMAL_RESEND		= "B6";
	public static final String REC_RCV_STATE_OUTBOUND_RECEIVE			= "B7";
	public static final String REC_RCV_STATE_OUTBOUND_PUBLIC			= "Bb";
	public static final String REC_RCV_STATE_OUTBOUND_CHARGER_ASSIGNED	= "Bc";
	public static final String REC_RCV_STATE_OUTBOUND_FLOW_COMPLETED	= "Bd";
	public static final String REC_RCV_STATE_OUTBOUND_CHARGER_REJECTED	= "Be";
	public static final String REC_RCV_STATE_OUTBOUND_PUBLIC_REJECTED	= "Bf";
	public static final String REC_RCV_STATE_OUTBOUND_PUBLIC_POSTED		= "Bg";
	public static final String REC_RCV_STATE_OUTBOUND_SHIFTED			= "Bh";
	public static final String REC_RCV_STATE_OUTBOUND_RETURNED			= "Bi";
	public static final String REC_RCV_STATE_OUTBOUND_ENFORCE_CANCELED	= "Bj";
	public static final String REC_RCV_STATE_OUTBOUND_PASS_THROUGH		= "Bk";
	public static final String REC_RCV_STATE_OUTBOUND_WAITING_SEND		= "Bl";
	public static final String REC_RCV_STATE_COMPOSITE_NULL				= "D0";
	public static final String REC_RCV_STATE_COMPOSITE_SEND				= "D1";
	public static final String REC_RCV_STATE_COMPOSITE_ACCEPT			= "D2";
	public static final String REC_RCV_STATE_COMPOSITE_ARRIVE			= "D3";
	public static final String REC_RCV_STATE_COMPOSITE_FAIL				= "D4";
	public static final String REC_RCV_STATE_COMPOSITE_REQ_RESEND		= "D5";
	public static final String REC_RCV_STATE_COMPOSITE_NORMAL_RESEND	= "D6";
	public static final String REC_RCV_STATE_COMPOSITE_RECEIVE			= "D7";
	public static final String REC_RCV_STATE_COMPOSITE_PUBLIC			= "Db";
	public static final String REC_RCV_STATE_COMPOSITE_CHARGER_ASSIGNED	= "Dc";
	public static final String REC_RCV_STATE_COMPOSITE_FLOW_COMPLETED	= "Dd";
	public static final String REC_RCV_STATE_COMPOSITE_CHARGER_REJECTED	= "De";
	public static final String REC_RCV_STATE_COMPOSITE_PUBLIC_REJECTED	= "Df";
	public static final String REC_RCV_STATE_COMPOSITE_PUBLIC_POSTED	= "Dg";
	public static final String REC_RCV_STATE_COMPOSITE_SHIFTED			= "Dh";
	public static final String REC_RCV_STATE_COMPOSITE_RETURNED			= "Di";
	public static final String REC_RCV_STATE_COMPOSITE_ENFORCE_CANCELED	= "Dj";
	public static final String REC_RCV_STATE_COMPOSITE_PASS_THROUGH		= "Dk";
	public static final String REC_RCV_STATE_COMPOSITE_WAITING_SEND		= "Dl";
	public static final String REC_RCV_STATE_BATCH_NULL					= "F0";
	public static final String REC_RCV_STATE_BATCH_SEND					= "F1";
	public static final String REC_RCV_STATE_BATCH_ACCEPT				= "F2";
	public static final String REC_RCV_STATE_BATCH_ARRIVE				= "F3";
	public static final String REC_RCV_STATE_BATCH_FAIL					= "F4";
	public static final String REC_RCV_STATE_BATCH_REQ_RESEND			= "F5";
	public static final String REC_RCV_STATE_BATCH_NORMAL_RESEND		= "F6";
	public static final String REC_RCV_STATE_BATCH_RECEIVE				= "F7";
	public static final String REC_RCV_STATE_BATCH_PUBLIC				= "Fb";
	public static final String REC_RCV_STATE_BATCH_CHARGER_ASSIGNED		= "Fc";
	public static final String REC_RCV_STATE_BATCH_FLOW_COMPLETED		= "Fd";
	public static final String REC_RCV_STATE_BATCH_CHARGER_REJECTED		= "Fe";
	public static final String REC_RCV_STATE_BATCH_PUBLIC_REJECTED		= "Ff";
	public static final String REC_RCV_STATE_BATCH_PUBLIC_POSTED		= "Fg";
	public static final String REC_RCV_STATE_BATCH_SHIFTED				= "Fh";
	public static final String REC_RCV_STATE_BATCH_RETURNED				= "Fi";
	public static final String REC_RCV_STATE_BATCH_ENFORCE_CANCELED		= "Fj";
	public static final String REC_RCV_STATE_BATCH_PASS_THROUGH			= "Fk";
	public static final String REC_RCV_STATE_BATCH_WAITING_SEND			= "Fl";
	public static final String REC_RCV_STATE_UPDATE_SEND				= "1";
	public static final String REC_RCV_STATE_UPDATE_ACCEPT				= "2";
	public static final String REC_RCV_STATE_UPDATE_ARRIVE				= "3";
	public static final String REC_RCV_STATE_UPDATE_FAIL				= "4";
	public static final String REC_RCV_STATE_UPDATE_REQ_RESEND			= "5";
	public static final String REC_RCV_STATE_UPDATE_NORMAL_RESEND		= "6";
	public static final String REC_RCV_STATE_UPDATE_RECEIVE				= "7";
	public static final String REC_RCV_STATE_UPDATE_PUBLIC				= "b";
	public static final String REC_RCV_STATE_UPDATE_CHARGER_ASSIGNED	= "c";
	public static final String REC_RCV_STATE_UPDATE_FLOW_COMPLETED		= "d";
	public static final String REC_RCV_STATE_UPDATE_CHARGER_REJECTED	= "e";
	public static final String REC_RCV_STATE_UPDATE_PUBLIC_REJECTED		= "f";
	public static final String REC_RCV_STATE_UPDATE_PUBLIC_POSTED		= "g";
	public static final String REC_RCV_STATE_UPDATE_SHIFTED				= "h";
	public static final String REC_RCV_STATE_UPDATE_RETURNED			= "i";
	public static final String REC_RCV_STATE_UPDATE_ENFORCE_CANCELED	= "j";
	public static final String REC_RCV_STATE_UPDATE_PASS_THROUGH		= "k";
	public static final String REC_RCV_STATE_UPDATE_WAITING_SEND		= "l";

	public static final String DSC_RCV_STATE_NULL						= "0";
	public static final String DSC_RCV_STATE_DISTRIBUTED				= "1";
	public static final String DSC_RCV_STATE_RETURNED					= "2";
	public static final String DSC_RCV_STATE_SHIFTED					= "3";
	public static final String DSC_RCV_STATE_PASS_THROUGH				= "4";

	public static final String DSC_LOG_STATE_DISTRIBUTE					= "1";
	public static final String DSC_LOG_STATE_REQUEST_REDISTRIBUTE		= "2";
	public static final String DSC_LOG_STATE_REDISTRIBUTE				= "3";

	public static final String REC_BOOL_NEW								= "1";
	public static final String REC_BOOL_OLD								= "2";

	public static final String SIMPLE_DATE_FORMAT_YYYY					= "yyyy";
	public static final String SIMPLE_DATE_FORMAT_YYYYMMDD				= "yyyyMMdd";
	public static final String SIMPLE_DATE_FORMAT_YYYYMMDDHHMM			= "yyyyMMddHHmm";
	public static final String SIMPLE_DATE_FORMAT_YYYYMMDDHHMMSS		= "yyyyMMddHHmmss";

	public static final String REC_BOOL_YES								= "Y";
	public static final String REC_BOOL_NO								= "N";
}
