<%@ page contentType="text/html; charset=EUC-KR" %>
<% 
String businessCode = "TR01";
String redirectUrl  = "";//"http://127.0.0.1:8090/ep/ep/app/LegacyApp/LegacyAppReg.do";
String filePath  	= "";//"D:\\_localDev_lh\\temp\\legacyClient";//request.getParameter("filePath");
String userId 		= "Ua885a54af774032ff8c";
String deptId 		= "Z800";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>�����û�Է�</title>
<link type="text/css" rel="stylesheet" href="/ep/app/ref/css/main.css"/>
<script type="text/javascript">
var exceed = "�Է°����� �ִ���ڼ��� �ʰ��Ͽ����ϴ�.";
var korean = "�ѱ�";
var character = "��";
var englishnumber = "����/����";
var writewithin = "�� �̳��� �ۼ��Ͻʽÿ�.";
var reloadwindow = "����â�� �ݰ� ���� �õ��� �ֽñ� �ٶ��ϴ�.";
var db_korean_byte = 2;


</script>
<script type="text/javascript" src="/ep/app/ref/js/app-common.js"></script>
<script type="text/javascript" src="/ep/app/ref/js/jquery.js"></script>
<script type="text/javascript">
function regForm(){

	if(document.myform.title.value !="" && document.myform.fromDate.value != "" && document.myform.toDate.value != "" &&  document.myform.objective.value != "" &&  document.myform.amount.value != "" ){
		
		//alert("22222222222222");
		var param = $("#myform").serialize();
		$.post("/ep/app/LegacyApp/LegacyApiReg.do", param,function(data) {
			if (data.result.length > 0) {
				alert("���縦 �����Ͽ����ϴ�");
				//listForm();
				openBizDocument(data.result,"test02");
			} else {
				alert("���� ������ ������ �߻��Ͽ����ϴ�");
			}		
		},'json').error(function(data) {
			alert("���� ������ ������ �߻��Ͽ����ϴ�");
		});				
	}
	else{
		alert("����Ÿ�� ��� �Է��ϼ���.");
	}
}

function listForm(){
	location.href = "/ep/app/LegacyTest/LegacyTestList.do";
}

function openBizDocument(appDocId,userId){
		var width = 1200;
		
		if (screen.availWidth < 1200) {	
		    width = screen.availWidth;
		}
		
		var height = 768;
		
		if (screen.availHeight > 768) {	
		    height = screen.availHeight;	
		}
		height = height - 80;
		
		var top = (screen.availHeight - height) / 2;	
		var left = (screen.availWidth - width) / 2; 
		var option = "width="+width+",height="+height+",top="+top+",left="+left+",menubar=no,scrollbars=no,status=yes";
		
		var linkUrl = "http://172.30.127.112:8001/ep/app/jsp/legacy/sendByLegacy.jsp?docId="+appDocId+"&userId="+userId;

		openWindow("selectBizAppDocWin", linkUrl , width, height);
}


function openWindow(winName, url, width, height, scroll, method, status) {
	var winObject = null;
	var data = url.split("?");
	var param = "";
	if (data.length > 1) {
		url = data[0];
		param = data[1];
	}
	
	var left = (screen.availWidth - width) / 2;
	var top = (screen.availHeight - height - 40) / 2;

	if (typeof(scroll) == "undefined") {
		scroll = "no";
	}
	if (typeof(method) == "undefined") {
		method = "post";
	}
	if (typeof(status) == "undefined") {
		status = "yes";
	}

	var option = "left="+left+",top="+top+",width="+width+",height="+height+",scrollbars="+scroll+",status="+status;
	
	try {
		if (param == null || param == "") {
			winObject = window.open(url, winName, option);
		} else if (method == "get") {
			winObject = window.open(url + "?" + param, winName, option);
		} else {
			// ��â ����
			winObject = window.open("", winName, option);
		
			// submit �� form ����/����
			var formwizard = document.getElementById(winName);
			if (formwizard == null) {
				var formwizardscript = "<form id='" + winName + "' style='width:0px;height:0px' method='post' target='" + winName + "' action='" + url + "'>";		
				if (param != "") {
					var params = param.split("&");
					var paramcount = params.length;
					for (var loop = 0; loop < paramcount; loop++) {
						if (params[loop] != "") {
							var paramdata = params[loop].split("=");
							if (paramdata.length == 2 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value='" + escapeJavaScript(paramdata[1]) + "'>";
							} else if (paramdata.length == 1 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value=''>";
							}
						}
					}			
				}
				formwizardscript += "</form>";
				document.body.insertAdjacentHTML("beforeEnd", formwizardscript);
				formwizard = document.getElementById(winName);
			} else {
				formwizard.action = url;
				if (param != "") {
					var formwizardscript = "";
					var params = param.split("&");
					var paramcount = params.length;
					for (var loop = 0; loop < paramcount; loop++) {
						if (params[loop] != "") {
							var paramdata = params[loop].split("=");
							if (paramdata.length == 2 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value='" + escapeJavaScript(paramdata[1]) + "'>";
							} else if (paramdata.length == 1 && paramdata[0] != "") {
								formwizardscript += "<input type='hidden' id='" + paramdata[0] + "' name='" + paramdata[0] + "' value=''>";
							}
						}
					}			
					$("#" + winName).html(formwizardscript);
				}
			}
			// form �߼�
			formwizard.submit();
		}
		winObject.focus();
		return winObject;
	} catch (error) {
		try {
			if (param == null || param == "") {
				winObject = window.open(url, "winName", option);
			} else {
				winObject = window.open(url + "?" + param, "winName", option);
			}
			winObject.focus();
			return winObject;
		} catch (error) {
			alert(reloadwindow);
		}
	}
}

</script>

</head>
<body leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
<table width="100%" style='' cellspacing='0' cellpadding='0'>
<tr>
<td height='10' colspan='3'></td>
</tr>
<tr>
<td width='10'></td>
<td>

<table width='100%' border='0' cellspacing='0' cellpadding='0'>
<tr>
<td width='39'><img src='/ep/app/ref/image/title1.jpg' width='39' height='29'></td>
<td background='/ep/app/ref/image/titlebg.gif'><table border='0' cellspacing='0' cellpadding='0'>
    <tr>
      <td class='title'>
�����û���</td>
    </tr>
  </table></td>
<td width='13'><img src='/ep/app/ref/image/title2.gif' width='13' height='29'></td>
</tr>
</table>

<form name="myform" id="myform" method="post">
	<input type="hidden" name="deptId" value="<%=deptId%>" />
	<input type="hidden" name="userId" value="<%=userId%>" />
	<input type="hidden" name="businessCode" value="<%=businessCode%>" />
	<input type="hidden" name="filePath" value="<%=filePath%>" />
	<input type="hidden" name="redirectUrl" value="<%=redirectUrl%>" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<table width='100%' border='0' align='right' cellpadding='0' cellspacing='0'>
<tr><td>
<table border='0' align='right' cellpadding='0' cellspacing='0'>
<tr>

					<td>
<table ondragstart='return(false);' border='0' onClick="regForm();" style="cursor:pointer"  cellpadding='0' cellspacing='0'>
<tr>
<td><img src='/ep/app/ref/image/bu2_left.gif' alt=''></td>
<td background='/ep/app/ref/image/bu2_bg.gif' class='text_center' style='white-space:nowrap'>
<a href="#" title='���翬��'>���翬��</a>
</td>
<td><img src='/ep/app/ref/image/bu2_right.gif' alt='' width='8' height='20'></td>
</tr>
</table>

</td>

					<td width='4'></td>

					<td>
<table ondragstart='return(false);' border='0' onClick="listForm();" style="cursor:pointer"  cellpadding='0' cellspacing='0'>
<tr>
<td><img src='/ep/app/ref/image/bu2_left.gif' alt=''></td>
<td background='/ep/app/ref/image/bu2_bg.gif' class='text_center' style='white-space:nowrap'>
<a href="#" title='���'>���</a>
</td>
<td><img src='/ep/app/ref/image/bu2_right.gif' alt='' width='8' height='20'></td>
</tr>
</table>

</td>

				</tr>
</table>
</td>
</tr>
</table>

			</td>
			<tr>
			<td>
				<td height='10'></td>

			<td>
			</tr>	
		</tr>	
	</table>
		<table width="100%" class='table_grow' border='0' cellspacing='1' cellpadding='0'>

	
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">����</td>
				<td class="tb_left_bg" colspan="3" ><input id="title" name="title" type="text" class="input" value="���� ��û��(������)" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			
			<tr bgcolor="#ffffff" >
				<td class="tb_tit" width="18%">�Ⱓ From</td>
				<td class="tb_left_bg" ><input id="fromDate" name="fromDate" type="text" class="input" maxlength="10" value="2012-06-01"/></td>
				<td class="tb_tit" width="18%">�Ⱓ To</td>
				<td class="tb_left_bg" ><input id="toDate" name="toDate" type="text" class="input" maxlength="10" value="2012-06-04"/></td>
 			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">����</td>
				<td class="tb_left_bg" colspan="3" ><input id="objective" name="objective" type="text" class="input" maxlength="256" style="width: 99%;ime-mode:active;" onkeyup="checkInputMaxLength(this,'',512)"/>
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">���</td>
				<td class="tb_left_bg" colspan="3" ><input id="amount" name="amount" type="text" class="input" maxlength="12" style="width: 100;ime-mode:inactive; text-align:right" onkeyup="checkInputMaxLength(this,'',12)"/>��
				</td>
			</tr>
			<tr bgcolor="#ffffff" >
				<td  class="tb_tit" width="18%">�������</td>
				<td class="tb_left_bg" colspan="3" >

					<select name="approver1" class="select"">
						<option value="approver">���þ���</option>
					
						<option value="test01" >�ڰ���</option>
					
						<option value="xygate" selected>������</option>
					
					</select>&nbsp;&nbsp;&nbsp;
					<select name="art1" class="select"">
						<option value="ART000">���þ���</option>
					
						<option value="ART010" selected>���</option>
						<option value="ART020">����</option>
						<option value="ART030">����</option>
						<option value="ART130">����</option>
						<option value="ART040">����</option>
						<option value="ART050">����</option>
						<option value="ART053">1������</option>						
					</select><br/><br/>

					<select name="approver2" class="select"">
						<option value="approver">���þ���</option>
					
						<option value="test01" >�ڰ���</option>
					
						<option value="xygate" >������</option>
					
					</select>&nbsp;&nbsp;&nbsp;
					<select name="art2" class="select"">
						<option value="ART000">���þ���</option>
					
						<option value="ART010" >���</option>
						<option value="ART020">����</option>
						<option value="ART030">����</option>
						<option value="ART130">����</option>
						<option value="ART040">����</option>
						<option value="ART050">����</option>
						<option value="ART053">1������</option>						
					</select><br/><br/>

				</td>
			</tr>

	</table>

	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
				<td height='10'></td>

		</td>
	</tr>	
	<tr>	
			<td align="right">
			<table width='100%' border='0' align='right' cellpadding='0' cellspacing='0'>
<tr><td>
<table border='0' align='right' cellpadding='0' cellspacing='0'>
<tr>

				<td>
<table ondragstart='return(false);' border='0' onClick="regForm();" style="cursor:pointer"  cellpadding='0' cellspacing='0'>
<tr>
<td><img src='/ep/app/ref/image/bu2_left.gif' alt=''></td>
<td background='/ep/app/ref/image/bu2_bg.gif' class='text_center' style='white-space:nowrap'>
<a href="#" title='���翬��'>���翬��</a>
</td>
<td><img src='/ep/app/ref/image/bu2_right.gif' alt='' width='8' height='20'></td>
</tr>
</table>

</td>

				<td width='4'></td>

				<td>
<table ondragstart='return(false);' border='0' onClick="listForm();" style="cursor:pointer"  cellpadding='0' cellspacing='0'>
<tr>
<td><img src='/ep/app/ref/image/bu2_left.gif' alt=''></td>
<td background='/ep/app/ref/image/bu2_bg.gif' class='text_center' style='white-space:nowrap'>
<a href="#" title='���'>���</a>
</td>
<td><img src='/ep/app/ref/image/bu2_right.gif' alt='' width='8' height='20'></td>
</tr>
</table>

</td>

			</tr>
</table>
</td>
</tr>
</table>

		</td>
	</tr>	
</table>
<div id="approvalitem1" name="approvalitem" style="dispaly:none">
	<input id="attachFile" name="attachFile" type="hidden" value=""></input>
</div>
</form>
</td>
<td width='10'></td>
</tr>
</table>

	
</body>
</html>