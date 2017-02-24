<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.sds.acube.app.common.vo.UserVO"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : userInfoPop.jsp 
 *  Description : 사용자조회
 *  Modification Information 
 * 
 *   수 정 일 : 2011.08.31 
 *   수 정 자 : 김경훈
 *   수정내용 : 신규개발 
 * 
 *  @author  김경훈
 *  @since 2011.08.31  
 *  @version 1.0 
 *  @see
 */ 
%>

<%
UserVO user = (UserVO)request.getAttribute("resultVO");
String pictureYn = (String)request.getAttribute("pictureYn");

String closeWin 	= messageSource.getMessage("common.button.userInfo.closeWin" , null, langType);

String userId 		= CommonUtil.nullTrim(user.getUserUID());
String userName 	= EscapeUtil.escapeHtmlDisp(user.getUserName());
String engName		= EscapeUtil.escapeHtmlDisp(user.getUserOtherName());
String titlCode 	= CommonUtil.nullTrim(user.getTitleCode());
String titleName	= EscapeUtil.escapeHtmlDisp(user.getTitleName());
String positionCode	= CommonUtil.nullTrim(user.getPositionCode());
String positionName	= EscapeUtil.escapeHtmlDisp(user.getPositionName());
String deptName 	= EscapeUtil.escapeHtmlDisp(user.getDeptName());
String teamName 	= EscapeUtil.escapeHtmlDisp(user.getReserved3());
String eMail 		= EscapeUtil.escapeHtmlDisp(user.getSysMail());
String officeTel 	= EscapeUtil.escapeHtmlDisp(user.getOfficeTel());
String officeTel2 	= EscapeUtil.escapeHtmlDisp(user.getOfficeTel2());
String mobile 		= EscapeUtil.escapeHtmlDisp(user.getMobile());
String mobile2 		= EscapeUtil.escapeHtmlDisp(user.getMobile2());
String homeTel 		= EscapeUtil.escapeHtmlDisp(user.getHomeTel());
String homeTel2 	= EscapeUtil.escapeHtmlDisp(user.getHomeTel2());
String strZipCode 	= EscapeUtil.escapeHtmlDisp(user.getHomeZipCode());
String addr 		= EscapeUtil.escapeHtmlDisp(user.getHomeAddr());
String addr2 		= EscapeUtil.escapeHtmlDisp(user.getHomeDetailAddr());
String homePage 	= EscapeUtil.escapeHtmlDisp(user.getHomePage());
String biz 			= (EscapeUtil.escapeHtmlDisp(user.getDescription())).replace("\r", "<br>");

String afPosition	= "";
String afZipCode 	= "";

if(!"".equals(titlCode) && !"".equals(positionCode))  {
    afPosition = titleName + " / " + positionName;
}else if(!"".equals(titlCode) && "".equals(positionCode))  {
    afPosition = titleName;
}else if("".equals(titlCode) && !"".equals(positionCode))  {
    afPosition = positionName;
}

if (!"".equals(strZipCode.trim()) && strZipCode.length() > 3) {
    afZipCode = strZipCode.substring(0, 3) + "-" + strZipCode.substring(3, strZipCode.length());
}else{
    afZipCode = strZipCode;
}



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="common.title.userInfo" /></title>
<link type="text/css" rel="stylesheet" href="<%=webUri%>/app/ref/css/main.css"/>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
</head> 
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<div class="pop_title02">
	<h3><span><a href="javascript:self.close();" class="icon_close02" title="닫기"></a></span></h3>	
</div>
<acube:outerFrame popup="true">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="pop_table05">
		<tr>
			<td>
		<span class="pop_title77"><spring:message code="common.title.userInfo" /></span>
	</td>
</tr>	
<tr>
    <acube:space between="title_button"/>
</tr>
<!--<tr>
    <td>
        <acube:buttonGroup align="right">
        <acube:menuButton  href="#" onClick="javascript:self.close();" value="<%=closeWin %>"/>
        </acube:buttonGroup>		
	</td>
</tr>
<tr>-->
    <acube:space between="button_content"/>
</tr>
<tr>
  <td height="5">
	<acube:tableFrame class="td_table borderRB">
	<tr>
		<td width="20%" class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.name" /></nobr></td><!--이름  -->
		<td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=userName%></td>
		<td class="tb_tit" rowspan="6" width="10%" style="padding: 5px 5px 5px 5px">
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" valign="middle"><!-- 이미지 -->
							<% if("Y".equals(pictureYn)){ %>
								<img src="<%=webUri%>/app/env/EnvComImage.do?userId=<%=userId%>&nType=0" width="110" height="129" border="0" />
							<%}else{ %>
								<img src="<%=webUri%>/app/ref/image/common/n_no_img.gif" border="0" />
							<% } %>												
						</td>
					</tr>
					<tr>
						<td align="center" valign="middle" height="5px"></td>
					</tr>
				</table>
		</td>
	  </tr>
	  <tr>
		<td class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.engName" /><!-- 영문이름 --></nobr></td>
		<td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=engName %></td>
	  </tr>
	  <tr>
		<td class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.workTitle" /> / <spring:message code="common.subTitle.userInfo.position" /><!-- 직책/직위 --></nobr></td>
		<td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=afPosition %></td>
	  </tr>					  
	  <tr>
		<td class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.dept" /><!-- 부점명 --></nobr></td>
		<td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=deptName%></td>
	  </tr>
	  <tr>
		<td class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.teamName" /><!-- 팀 명 --></nobr></td>
		<td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=teamName%></td>
	  </tr>
	  <tr>
        <td class="tb_tit"><nobr><spring:message code="common.subTitle.userInfo.email" /><!-- e-Mail --></nobr></td>
        <td class="td_bg rb_td" colspan="3" style="padding: 0px 0px 0px 5px"><%=eMail%></td>
      </tr>
      <tr>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.officeNumber" />1<!-- 내선번호 --></nobr></td>
		<td class="td_bg rb_td" width="30%" style="padding: 0px 0px 0px 5px"><%=officeTel%></td>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.officeNumber" />2<!-- 내선번호 --></nobr></td>
		<td class="td_bg rb_td" width="30%" colspan="2" style="padding: 0px 0px 0px 5px"><%=officeTel2%></td>		
      </tr>
      <tr>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.mobile" /><!-- 휴대폰 --></nobr></td>
		<td class="td_bg rb_td" width="30%" style="padding: 0px 0px 0px 5px"><%=mobile%><br><%=mobile2%></td>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.homeTel" /><!-- 자택전화 --></nobr></td>
		<td class="td_bg rb_td" width="30%" colspan="2" style="padding: 0px 0px 0px 5px"><%=homeTel%><br><%=homeTel2%></td>
      </tr>
      <tr>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.addressPost" /><!-- 우편번호 --></nobr></td>
        <td class="td_bg rb_td" width="80%" colspan="4" style="padding: 0px 0px 0px 5px"><%=afZipCode %></td>
      </tr>
      <tr>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.address" /><!-- 주 소 --></nobr></td>
        <td class="td_bg rb_td" width="80%" colspan="4" style="padding: 0px 0px 0px 5px"><%=addr%>&nbsp;<%=addr2%></td>
      </tr>
      <tr>
        <td class="tb_tit" width="20%"><nobr><spring:message code="common.subTitle.userInfo.homepage" /><!-- Home Page --></nobr></td>
        <td class="td_bg rb_td" width="80%" colspan="4" style="padding: 0px 0px 0px 5px"><%=homePage%></td>
      </tr>
      <tr>
        <td class="tb_tit" width="20%" valign="top"><nobr><spring:message code="common.subTitle.userInfo.biz" /><!-- 담당업무 --></nobr></td>
        <td class="td_bg rb_td" width="80%" colspan="4" style="padding: 0px 0px 0px 5px"><%=biz%></td>
      </tr>
	</acube:tableFrame>
  </td>
</tr>
<tr>
    <acube:space between="button_content"/>
</tr>
<tr>
  <td>
	<acube:buttonGroup align="right">
		<acube:menuButton  href="#" onClick="javascript:self.close();" value="<%=closeWin %>"/>
	</acube:buttonGroup>
  </td>
</tr>
</table>

</acube:outerFrame>
</body>	
</html>