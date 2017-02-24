<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code="approval.title.applines" /></title>

<STYLE>
<!--
P.HStyle0, LI.HStyle0, DIV.HStyle0
	{style-name:"바탕글"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:10.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle1, LI.HStyle1, DIV.HStyle1
	{style-name:"본문16"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:16.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle2, LI.HStyle2, DIV.HStyle2
	{style-name:"본문울룽도16"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:16.0pt; font-family:HY울릉도M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle3, LI.HStyle3, DIV.HStyle3
	{style-name:"제목16"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:16.0pt; font-family:한양신명조; letter-spacing:0.8pt; font-weight:"bold"; font-style:"normal"; color:#000000;}
P.HStyle4, LI.HStyle4, DIV.HStyle4
	{style-name:"참고13(중고딕)"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:13.0pt; font-family:한양중고딕; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle5, LI.HStyle5, DIV.HStyle5
	{style-name:"표15"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:130%; font-size:15.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle6, LI.HStyle6, DIV.HStyle6
	{style-name:"표13"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:13.0pt; font-family:한양신명조; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle7, LI.HStyle7, DIV.HStyle7
	{style-name:"표12"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:130%; font-size:12.0pt; font-family:한양신명조; letter-spacing:0.6pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle8, LI.HStyle8, DIV.HStyle8
	{style-name:"붙임신명조13"; margin-left:2.0pt; margin-right:2.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:130%; font-size:13.0pt; font-family:한양신명조; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle9, LI.HStyle9, DIV.HStyle9
	{style-name:"문단사이"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:100%; font-size:6.0pt; font-family:바탕; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle10, LI.HStyle10, DIV.HStyle10
	{style-name:"제목17"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:17.0pt; font-family:휴먼명조; letter-spacing:0.0pt; font-weight:"bold"; font-style:"normal"; color:#000000;}
P.HStyle11, LI.HStyle11, DIV.HStyle11
	{style-name:"제목20로마"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:20.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle12, LI.HStyle12, DIV.HStyle12
	{style-name:"제목18아라"; margin-left:32.2pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:-32.2pt; line-height:140%; font-size:18.0pt; font-family:HY울릉도M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle13, LI.HStyle13, DIV.HStyle13
	{style-name:"14"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:14.0pt; font-family:한양신명조; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle14, LI.HStyle14, DIV.HStyle14
	{style-name:"표14헤드라인"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:130%; font-size:14.0pt; font-family:HY헤드라인M; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle15, LI.HStyle15, DIV.HStyle15
	{style-name:"목차제목"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:160%; font-size:25.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle16, LI.HStyle16, DIV.HStyle16
	{style-name:"목차18"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:180%; font-size:18.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle17, LI.HStyle17, DIV.HStyle17
	{style-name:"목차16"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:140%; font-size:14.0pt; font-family:한양신명조; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle18, LI.HStyle18, DIV.HStyle18
	{style-name:"표제32"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:160%; font-size:35.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle19, LI.HStyle19, DIV.HStyle19
	{style-name:"표제28(날자)"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:160%; font-size:28.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle20, LI.HStyle20, DIV.HStyle20
	{style-name:"표제24(날자)"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:160%; font-size:24.0pt; font-family:HY태고딕; letter-spacing:-5.0pt; font-weight:"bold"; font-style:"normal"; color:#000000;}
P.HStyle21, LI.HStyle21, DIV.HStyle21
	{style-name:"제목18(울룽도)"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:160%; font-size:18.0pt; font-family:HY울릉도M; letter-spacing:0.0pt; font-weight:"bold"; font-style:"normal"; color:#000000;}
P.HStyle22, LI.HStyle22, DIV.HStyle22
	{style-name:"제목17헤드라인"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:17.0pt; font-family:HY헤드라인M; letter-spacing:0.0pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle23, LI.HStyle23, DIV.HStyle23
	{style-name:"표12항목"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:120%; font-size:12.0pt; font-family:한양중고딕; letter-spacing:-1.2pt; font-weight:"normal"; font-style:"normal"; color:#282828;}
P.HStyle24, LI.HStyle24, DIV.HStyle24
	{style-name:"@표상단"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:center; text-indent:0.0pt; line-height:150%; font-size:1.5pt; font-family:-윤고딕130; letter-spacing:0.2pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
P.HStyle25, LI.HStyle25, DIV.HStyle25
	{style-name:"16제목"; margin-left:0.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:16.0pt; font-family:휴먼명조; letter-spacing:0.0pt; font-weight:"bold"; font-style:"normal"; color:#000000;}
P.HStyle26, LI.HStyle26, DIV.HStyle26
	{style-name:"--원아이콘"; margin-left:20.0pt; margin-right:0.0pt; margin-top:0.0pt; margin-bottom:0.0pt; text-align:justify; text-indent:0.0pt; line-height:160%; font-size:13.0pt; font-family:-윤명조130; letter-spacing:0.7pt; font-weight:"normal"; font-style:"normal"; color:#000000;}
-->

</STYLE>
</head> 
<body>
<div>
	<P CLASS=HStyle0 STYLE='text-align:center;'>
		<TABLE border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;border:none;'>
			<TR>
				<TD width="548" height="38" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 2.0pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 2.0pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;'><SPAN STYLE='font-size:15.0pt;font-family:"HY헤드라인M";line-height:160%;'>최종 조치구분 설명표</SPAN></P>
				</TD>
			</TR>
		</TABLE>
	</P>
	
	<P CLASS=HStyle0 STYLE='text-align:center;'>
		<TABLE border="1" cellspacing="0" cellpadding="0" style='border-collapse:collapse;border:none;'>
			<TR>
				<TD width="57" height="33" valign="middle" bgcolor="#e9ef00" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>종류</SPAN></P>
				</TD>
				<TD width="70" height="33" valign="middle" bgcolor="#e9ef00" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>구분</SPAN></P>
				</TD>
				<TD width="510" height="33" valign="middle" bgcolor="#e9ef00" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 1.1pt;border-bottom:double #000000 1.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:130%;'>내     용</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD rowspan="4" width="57" height="220" valign="middle" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>수용</SPAN></P>
				</TD>
				<TD width="65" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>완료</SPAN></P>
				</TD>
				<TD width="510" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:double #000000 1.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>피신청기관이 위원회 권고 등의 내용대로 조치&#65381;완료한 경우</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="65" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:10.8pt;font-family:"HY중고딕";letter-spacing:0.6pt;font-weight:"bold";line-height:130%;'>일부완료</SPAN></P>
				</TD>
				<TD width="510" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>시정조치권고 등의 내용 중 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>일부만 조치&#65381;완료</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>한 경우</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="65" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>조치중</SPAN></P>
				</TD>
				<TD width="510" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>권고 등을 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>수용하기 위해 현재 처리중</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>에 있는 경우</SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:21.0pt;margin-right:5.0pt;text-indent:-18.0pt;line-height:150%;'><SPAN STYLE='font-size:11.0pt;line-height:150%;'>※ 보상과 관련된 민원의 경우 예산을 확보 등 구체적인 조치 요망</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="65" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>미조치</SPAN></P>
				</TD>
				<TD width="510" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>수용의사는 통보</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>했으나, </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>구체적인 조치를 취하지 않고 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>있</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>는</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'> 경우</SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:21.0pt;margin-right:3.0pt;text-indent:-18.0pt;line-height:150%;'><SPAN STYLE='font-size:11.0pt;line-height:150%;'>※ 예산이 확보되지 않은 경우</SPAN><SPAN STYLE='font-size:11.0pt;line-height:150%;'>에는</SPAN><SPAN STYLE='font-size:11.0pt;line-height:150%;'> 미조치로 처리</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="57" height="200" valign="middle" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>불수용</SPAN></P>
				</TD>
				<TD width="65" height="200" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>불수용</SPAN></P>
				</TD>
				<TD width="510" height="200" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>피신청기관이 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>명백하게 수용불가</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>를 통보한 경우</SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>※ 불수용 유형 </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>① 법령규정상 곤란 </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>② 공익목적&#8228; 정책상 곤란 </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>③ 소송&#8228;행정심판 결과와 상이</SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>④ 타 위원회 등의 심의결과와 상이 </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>⑤ 예산상 곤란 </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>⑥ 기타</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD rowspan="3" width="57" height="140" valign="middle" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>미확정</SPAN></P>
				</TD>
				<TD width="65" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:10.8pt;font-family:"HY중고딕";letter-spacing:-1.3pt;font-weight:"bold";line-height:130%;'>미결</SPAN></P>
				</TD>
				<TD width="510" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>피신청기관이 독자적으로 수용여부를 결정하기 곤란하여 </SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>일정한 절차</SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:150%;'>경과 후 그 결과(예: 소송결과)에 따라 처리</SPAN><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>하겠다고 통보한 경우</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="65" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:10.8pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>이의신청</SPAN></P>
				</TD>
				<TD width="510" height="45" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 0.4pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>권고사항에 대하여 이의 제기를 한 경우</SPAN></P>
				</TD>
			</TR>
			<TR>
				<TD width="65" height="35" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>미회신</SPAN></P>
				</TD>
				<TD width="510" height="35" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 0.4pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'> </SPAN></P>
				</TD>
			</TR>
			
			<TR>
				<TD width="57" height="60" valign="middle" style='border-left:solid #000000 1.1pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'>기타</SPAN></P>
				</TD>
				<TD width="65" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 0.4pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='text-align:center;line-height:130%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";font-weight:"bold";line-height:130%;'> </SPAN></P>
				</TD>
				<TD width="510" height="60" valign="middle" style='border-left:solid #000000 0.4pt;border-right:solid #000000 1.1pt;border-top:solid #000000 1.1pt;border-bottom:solid #000000 1.1pt;padding:1.4pt 1.4pt 1.4pt 1.4pt'>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>신청인이 민원을 철회하거나 의결내용과 다르게 민원이 해결된 경우, </SPAN></P>
					<P CLASS=HStyle0 STYLE='margin-left:3.0pt;margin-right:3.0pt;line-height:150%;'><SPAN STYLE='font-size:12.0pt;font-family:"HY중고딕";line-height:150%;'>이의신청 인용으로 권고가 취소된 경우 등&nbsp; </SPAN></P>
				</TD>
			</TR>
		</TABLE>
	</P>
</div>
</body>
</html>