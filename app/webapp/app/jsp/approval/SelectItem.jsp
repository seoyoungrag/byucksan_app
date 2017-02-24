<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%
/** 
 *  Class Name  : SelectItem.jsp 
 *  Description : 양식 또는 안건 선택
 *  Modification Information 
 * 
 *   수 정 일 :  
 *   수 정 자 : 
 *   수정내용 : 
 * 
 *  @author  
 *  @since 2014. 10. 14 
 *  @version 1.0 
 *  @see
 */ 
%>
<%
	// 버튼명
	String confirmBtn = messageSource.getMessage("approval.button.confirm", null, langType); 
	String closeBtn = messageSource.getMessage("approval.button.close", null, langType); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title><spring:message code='approval.title.listitem'/></title>
<link rel="stylesheet" type="text/css" href="<%=webUri%>/app/ref/css/main.css" />
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<jsp:include page="/app/jsp/common/common.jsp" />
<script type="text/javascript">
var itemWin = null;
var itemWindow = null;

// 확인
// 선택한 버튼에 따른 분기
function confirmItem() {
	
	var radio = $("input:radio:checked");
	var itemcount = opener.getItemCount();
	
	// 양식 선택
	if("1" == radio.val()) {
		itemWindow = openWindow("itemWindow", "<%=webUri%>/app/env/ListEnvFormPopAddList.do?itemcount=" + itemcount, 700, 420);
	// 안 선택
	} else {
		if(itemcount == 1) {
			if (opener != null && opener.setItem) {
				opener.setItem(itemcount);
				window.close();
			}
		}
		else {
			itemWin = openWindow("itemWin", "<%=webUri%>/app/approval/createItemList.do", 250, 200);
		}
	}	
}

// 취소
function closeItem() {
		window.close();	
}

// 새마을금고전자결재고도화 시행문 양식일 경우 수신처 지정 여부 체크 formType 추가 2014.12.24.JJE
function setItem(formFileName, formType) {
	opener.setItemAdd(formFileName, formType);
	window.close();
}
</script>
</head>
<body>
<acube:outerFrame>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><acube:titleBar><spring:message code='approval.button.append.item'/></acube:titleBar></td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:tableFrame>
					<tr bgcolor="#ffffff">
						<td width="40%" class="tb_tit"><spring:message code='approval.button.append.item'/></td>
						<td width="60%" class="tb_left_bg">
							<input type="radio" name="listitem" value="1" checked="checked"/>양식 선택<br />
							<input type="radio" name="listitem" value="2"/>동일 양식 추가
						</td>
					</tr>
				</acube:tableFrame>
			</td>
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<acube:space between="title_button" />
		</tr>
		<tr>
			<td>
				<acube:buttonGroup>
					<acube:button onclick="confirmItem();return(false);" value="<%=confirmBtn%>" type="2" class="gr" />
					<acube:space between="button" />
					<acube:button onclick="closeItem();return(false);" value="<%=closeBtn%>" type="2" class="gr" />
				</acube:buttonGroup> 
			</td>
		</tr>
	</table>
</acube:outerFrame>
</body>
</html>