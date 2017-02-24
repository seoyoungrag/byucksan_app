<%@page contentType="text/html;charset=utf-8" %>
<%@include file = "./include/session_check.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Namo CrossEditor : Admin</title>
	<script type="text/javascript"> var ce_jv = "ce_qi"; </script>
	<script type="text/javascript" src="../manage_common.js"></script>
	<script type="text/javascript" language="javascript" src="../../js/namo_cengine.js"></script>
	<script type="text/javascript" language="javascript" src="../manager.js"></script>
	<link href="../css/common.css" rel="stylesheet" type="text/css">
</head>

<body>

<%@include file = "../include/top.html"%>

<div id="ce_LX" class="ce_dk">	
	<table class="ce_ie">
	  <tr>
		<td class="ce_dk">
		
			<table id="Info">
				<tr>
					<td style="padding:0 0 0 10px;height:30px;text-align:left">
					<font style="font-size:14pt;color:#3e77c1;font-weight:bold;text-decoration:none;"><span id="ce_qB"></span></font></td>
					<td id="InfoText"><span id="ce_lU"></span></td>
				</tr>
				<tr>
					<td colspan="2"><img id="ce_mh" src="../images/title_line.jpg" alt="" /></td>
				</tr>
			</table>
		
		</td>
	  </tr>
	  <tr>
		<td class="ce_dk">
			
				<form method="post" id="ce_UW" action="account_proc.jsp" onsubmit="return ce_A(this);">
				<table class="ce_fn" >
				  <tr>
					<td>

						<table class="ce_bQ">
						  <tr><td class="ce_cY" colspan="3"></td></tr>
						</table>
						 
						<table class="ce_bQ" >
						  <tr>
							<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_nC"></span></b></td>
							<td class="ce_bx"></td>
							<td class="ce_bz">
								<input type="hidden" name="u_id" id="u_id" value="<%=session.getAttribute("memId")%>" />
								<input type="password" name="passwd" id="passwd" value="" class="ce_gE" />
							</td>
						  </tr>
						  <tr>
							<td class="ce_bC" colspan="3"></td>
						  </tr>
						  <tr>
							<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tu"></span></b></td>
							<td class="ce_bx"></td>
							<td class="ce_bz">
								<input type="password" name="newPasswd" id="newPasswd" value="" class="ce_gE" />
							</td>
						  </tr>
						  <tr>
							<td class="ce_bC" colspan="3"></td>
						  </tr>
						  <tr>
							<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tr"></span></b></td>
							<td class="ce_bx"></td>
							<td class="ce_bz">
								<input type="password" name="newPasswdCheck" id="newPasswdCheck" value="" class="ce_gE" />
							</td>
						  </tr>
						</table>
					
						<table class="ce_bQ">
						  <tr><td class="ce_cY" colspan="3"></td></tr>
						</table>
								
					</td>
				  </tr>
				  <tr id="ce_tw">
					<td id="ce_ts">
						<ul style="margin:0 auto;width:170px;">
							<li class="ce_eh">
								<input type="submit" id="ce_pX" value="" class="ce_eb ce_de" style="width:66px;height:26px;" />
							</li>
							<li class="ce_eh"><input type="button" id="ce_mc" value="" class="ce_eb ce_de" style="width:66px;height:26px;"></li>
						</ul>
					</td>
				  </tr>
				</table>
				</form>
		
		</td>
	  </tr>
	</table>

</div>

<%@include file = "../include/bottom.html"%>

</body>
<script>
var webPageKind = '<%=session.getAttribute("webPageKind")%>'
ce_f();
ce_w();
</script>

</html>

	
	

