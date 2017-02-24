<%@page contentType="text/html;charset=utf-8" %>
<%@include file = "manager_util.jsp"%>
<%@include file = "./include/session_check.jsp"%>
<%
	String fileRealFolder = "";
	String ContextPath = request.getContextPath();
	String urlPath = rootFolderPath(request.getRequestURI());
	urlPath = urlPath.substring(0, urlPath.indexOf("manage/jsp"));

	ServletContext context = getServletConfig().getServletContext();
	fileRealFolder = context.getRealPath(urlPath);
	if (!ContextPath.equals("") && !ContextPath.equals("/")){
		File tempFileRealDIR = new File(fileRealFolder);
		if(!tempFileRealDIR.exists()){
			if (urlPath.indexOf(ContextPath) != -1){
				String rename_image_temp = urlPath.substring(ContextPath.length());
				fileRealFolder = context.getRealPath(rename_image_temp);
			}
		}
	}

	if (fileRealFolder.lastIndexOf(File.separator) != fileRealFolder.length() - 1){
		fileRealFolder = fileRealFolder + File.separator;
	}

	String url = xmlUrl(fileRealFolder);
	Element root = configXMlLoad(url);
	Hashtable settingValue = childValueList(root);

	String encodingStyleValue = "";
	String EncodingValue = "";

	if (settingValue.get("UploadFileNameType") != null){
		String FileNameType = (String)settingValue.get("UploadFileNameType");
	
		if (FileNameType != ""){
			if(FileNameType.indexOf(",")!= -1){
				String FileNameTypeArr[] = FileNameType.split(",");
				encodingStyleValue = FileNameTypeArr[0];
				if(FileNameTypeArr.length > 1) EncodingValue = FileNameTypeArr[1];
			}
			else{
				encodingStyleValue = FileNameType;
			}
		}
	}

	String userAddMenuList = "";
	
	if(settingValue.get("AddMenuCheck").equals("true")){
		if(settingValue.get("AddMenu") != ""){
			List addMenuListValue = (List)settingValue.get("AddMenu");
			for(int i=0; i<addMenuListValue.size(); i++){
				if(userAddMenuList.equals("")) userAddMenuList = (String)addMenuListValue.get(i);
				else userAddMenuList += "||" + (String)addMenuListValue.get(i);
			}
		}
	}
	
	if (settingValue.get("AccessibilityOption") == null  || settingValue.get("AccessibilityOption") == "" ) settingValue.put("AccessibilityOption", "0");
	if (settingValue.get("UploadFileSubDir") == null || settingValue.get("UploadFileSubDir") == "") settingValue.put("UploadFileSubDir", "true");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>Namo CrossEditor : Admin</title>
	<script type="text/javascript"> var ce_jv = "ce_pK"; </script>
	<script type="text/javascript" src="../manage_common.js"></script>
	<script type="text/javascript" language="javascript" src="../../js/namo_cengine.js"></script>
	<script type="text/javascript" language="javascript" src="../manager.js"></script>
	<link type="text/css" rel="stylesheet" href="../../css/namose_general.css" />
	<link type="text/css" rel="stylesheet" href="../css/common.css" />
</head>

<body>

<%@include file = "../include/top.html"%>

<form method="post" id="ce_pw" name="ce_pw" action="manager_proc.jsp" onsubmit="return ce_v();">
<div id="ce_Oy">
	<table class="ce_ie">
		<tr>
			<td class="ce_dk">

				<table id="Info">
					<tr>
						<td style="padding:0 0 0 10px;height:30px;text-align:left">
						<font style="font-size:14pt;color:#3e77c1;font-weight:bold;text-decoration:none;"><span id="ce_pN"></span></font></td>
						<td id="InfoText"><span id="ce_lU"></span></td>
					</tr>
					<tr>
						<td colspan="2"><img id="ce_mh" src="../images/title_line.jpg" alt="" /></td>
					</tr>
				</table>

				<table class="ce_fn">
					<tr>
						<td class="ce_Op">				
							<ul>
								<li class="ce_gq ce_tJ"><input type="button" id="ce_eE" value="" style="width:110px;height:28px;" class="ce_lM ce_de" /></li>
								<li class="ce_gq"><input type="button" id="ce_hV" value="" style="width:110px;height:28px;" class="ce_lM ce_de" /></li>
								<li class="ce_gq"><input type="button" id="ce_zq" value="" style="width:110px;height:28px;" class="ce_lM ce_de" /></li>
							</ul>
						
						</td>
					</tr>
					<tr>
						<td class="ce_lV ce_fk"><font style="font-size:9pt;color:#FF9F4B;font-weight:bold;"><span id="ce_tI"></span></font></td>
					</tr>
				</table>

			</td>
		</tr>
		
		<tr>
			<td class="ce_dk">
				<div id="ce_AV">
					<table class="ce_ld">
						<tr>
							<td>
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
							 
								<table class="ce_bQ">
								
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_pB"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='WebServerOS' id='WebServerOS' class="inputSelectStyle">
												<option value=''></option>
												<option value='WINDOW' <% if(settingValue.get("WebServerOS").equals("WINDOW")) out.println("selected=\"selected\"");%>>WINDOW</option>
												<option value='LINUX' <% if(settingValue.get("WebServerOS").equals("LINUX"))out.println("selected=\"selected\"");%>>LINUX</option>
												<option value='UNIX' <% if(settingValue.get("WebServerOS").equals("UNIX")) out.println("selected=\"selected\"");%>>UNIX</option>
											</select>
										</td>
									</tr>
									
									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>

									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_oU"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='WebServerInfo' id='WebServerInfo' class="inputSelectStyle">
												<option value=''></option>
												<option value='IIS' <% if(settingValue.get("WebServerInfo").equals("IIS")) out.println("selected=\"selected\"");%>>IIS</option>
												<option value='Apache' <% if(settingValue.get("WebServerInfo").equals("Apache"))out.println("selected=\"selected\"");%>>Apache</option>
												<option value='Tomcat' <% if(settingValue.get("WebServerInfo").equals("Tomcat")) out.println("selected=\"selected\"");%>>Tomcat</option>
												<option value='Resin' <% if(settingValue.get("WebServerInfo").equals("Resin")) out.println("selected=\"selected\"");%>>Resin</option>
												<option value='Jeus' <% if(settingValue.get("WebServerInfo").equals("Jeus")) out.println("selected=\"selected\"");%>>Jeus</option>
												<option value='WebLogic' <% if(settingValue.get("WebServerInfo").equals("WebLogic")) out.println("selected=\"selected\"");%>>WebLogic</option>
												<option value='WebSphere' <% if(settingValue.get("WebServerInfo").equals("WebSphere")) out.println("selected=\"selected\"");%>>WebSphere</option>
												<option value='iPlanet' <% if(settingValue.get("WebServerInfo").equals("iPlanet")) out.println("selected=\"selected\"");%>>iPlanet</option>
											</select>
										</td>
									</tr>
									
									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>
									
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_oX"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='WebLanguage' id='WebLanguage' class="inputSelectStyle">
												<option value=''></option>
												<option value='ASP' <% if(settingValue.get("WebLanguage").equals("ASP")) out.println("selected=\"selected\"");%>>ASP</option>
												<option value='JSP' <% if(settingValue.get("WebLanguage").equals("JSP")) out.println("selected=\"selected\"");%>>JSP</option>
												<option value='PHP' <% if(settingValue.get("WebLanguage").equals("PHP")) out.println("selected=\"selected\"");%>>PHP</option>
												<option value='ASP.NET' <% if(settingValue.get("WebLanguage").equals("ASP.NET")) out.println("selected=\"selected\"");%>>ASP.NET</option>
												<option value='SERVLET' <% if(settingValue.get("WebLanguage").equals("SERVLET")) out.println("selected=\"selected\"");%>>SERVLET</option>
											</select>
										</td>
									</tr>
									
									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>
									
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_oH"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="text" id="ImageSavePath" class="ce_eu" name="ImageSavePath" value="<%=settingValue.get("ImageSavePath")%>" /> ex) http:// www.mysite.com/image
										</td>
									</tr>
									
									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>

									<% if (settingValue.get("UploadFileNameType") != null){ %>
									
									<div id="ce_Am">
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_pP"></span></b></td>
										<td class="ce_bx"></td>
										<td >
											<table class="ce_bQ">
												<tr>
													<td class="ce_bz"> &nbsp;&nbsp;
														<input type="radio" name="ce_gx" id="ce_rw" value="real" /><span id="ce_pq"></span>
														<input type="hidden" id="UploadFileNameType" name="UploadFileNameType" value="" />
													</td>
												</tr>
												<tr>
													<td class = "ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td class="ce_bz"> &nbsp;&nbsp;
													<input type="radio" name="ce_gx" id="ce_uP" value="trans" /><span id="ce_pV"></span>
													</td>
												</tr>
												<tr>
													<td class = "ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td class="ce_bz"> &nbsp;&nbsp;
														<input type="radio" name="ce_gx" id="ce_ux" value="random" /><span id="ce_tG"></span>
													</td>
												</tr>
											</table>
										</td>
									</tr>

									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>
									</div>

									<% } %>

									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_rV"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz"> &nbsp;
											<input type="hidden" id="UploadFileSubDir" name ="UploadFileSubDir" value="<%=settingValue.get("UploadFileSubDir")%>" />
											<input type="radio" id="ce_ov" name="ce_vk" value="true" /><label for="ce_ov"><span id="ce_sa"></span></label>&nbsp;&nbsp;
											<input type="radio" id="ce_ow" name="ce_vk" value="false" /><label for="ce_ow"><span id="ce_sb"></span></label>
										</td>
									</tr>

									<tr>
										<td class = "ce_bC" colspan="3"></td>
									</tr>

									
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_oB"></span></b></td>
										<td class="ce_bx"></td>
										<td >
											<table class="ce_bQ">
												<tr>
													<td >&nbsp;&nbsp;<span id="ce_pu"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz"><input type="text" id="Width" name="Width" class="ce_gE" maxlength="10" value="<%=settingValue.get("Width")%>" />
													px</td>
												</tr>
												<tr>
													<td class = "ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td >&nbsp;&nbsp;<span id="ce_oQ"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz"><input type="text" id="Height" name="Height" class="ce_gE" maxlength="10" value="<%=settingValue.get("Height")%>" /> px
													</td>
												</tr>	
											</table>
										</td>
									</tr>
								</table>

								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
											
							</td>
						</tr>
						<tr>
							<td class="ce_lV ce_fk"><font style="font-size:9pt;color:#FF9F4B;font-weight:bold;"><span id="ce_uh"></span></font></td>
						</tr>

						<tr>
							<td>
						
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>

								<table class="ce_bQ">
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_qY"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='SetFocus' id='SetFocus' class="inputSelectStyle">
												<option value='true' <% if(settingValue.get("SetFocus").equals("true")) out.println("selected=\"selected\"");%>>true</option>
												<option value='false' <% if(settingValue.get("SetFocus").equals("false")) out.println("selected=\"selected\"");%>>false</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_th"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='LineHeight' id='LineHeight' class="inputSelectStyle">
												<option value=''></option>
												<option value='100%' <% if(settingValue.get("LineHeight").equals("100%")) out.println("selected=\"selected\"");%>>100%</option>
												<option value='120%' <% if(settingValue.get("LineHeight").equals("120%")) out.println("selected=\"selected\"");%>>120%</option>
												<option value='140%' <% if(settingValue.get("LineHeight").equals("140%")) out.println("selected=\"selected\"");%>>140%</option>
												<option value='160%' <% if(settingValue.get("LineHeight").equals("160%")) out.println("selected=\"selected\"");%>>160%</option>
												<option value='180%' <% if(settingValue.get("LineHeight").equals("180%")) out.println("selected=\"selected\"");%>>180%</option>	
												<option value='200%' <% if(settingValue.get("LineHeight").equals("200%")) out.println("selected=\"selected\"");%>>200%</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_sg"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='UnloadWarning' id='UnloadWarning' class="inputSelectStyle">
												<option value='false' <% if(settingValue.get("UnloadWarning").equals("false")) out.println("selected=\"selected\"");%>>false</option>
												<option value='true' <% if(settingValue.get("UnloadWarning").equals("true")) out.println("selected=\"selected\"");%>>true</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_ra"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='SetDebug' id='SetDebug' class="inputSelectStyle">
												<option value='false' <% if(settingValue.get("SetDebug").equals("false")) out.println("selected=\"selected\"");%>>false</option>
												<option value='true' <% if(settingValue.get("SetDebug").equals("true")) out.println("selected=\"selected\"");%>>true</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tY"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='HTMLTabByTableLock' id='HTMLTabByTableLock' class="inputSelectStyle">
												<option value='false' <% if(settingValue.get("HTMLTabByTableLock").equals("false")) out.println("selected=\"selected\"");%>>false</option>
												<option value='true' <% if(settingValue.get("HTMLTabByTableLock").equals("true")) out.println("selected=\"selected\"");%>>true</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_sN"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='HTMLTabContents' id='HTMLTabContents' class="inputSelectStyle">
												<option value='html' <% if(settingValue.get("HTMLTabContents").equals("html")) out.println("selected=\"selected\"");%>>html</option>
												<option value='body' <% if(settingValue.get("HTMLTabContents").equals("body")) out.println("selected=\"selected\"");%>>body</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tX"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='AllowContentScript' id='AllowContentScript' class="inputSelectStyle">
												<option value='true' <% if(settingValue.get("AllowContentScript").equals("true")) out.println("selected=\"selected\"");%>>true</option>
												<option value='false' <% if(settingValue.get("AllowContentScript").equals("false")) out.println("selected=\"selected\"");%>>false</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_uj"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='AllowContentIframe' id='AllowContentIframe' class="inputSelectStyle">
												<option value='true' <% if(settingValue.get("AllowContentIframe").equals("true")) out.println("selected=\"selected\"");%>>true</option>
												<option value='false' <% if(settingValue.get("AllowContentIframe").equals("false")) out.println("selected=\"selected\"");%>>false</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tb"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='CharSet' id='CharSet' class="inputSelectStyle">
												<option value=''></option>
												<option value='auto' <% if(settingValue.get("CharSet").equals("auto")) out.println("selected=\"selected\"");%>></option>
												<option value='utf-8' <% if(settingValue.get("CharSet").equals("utf-8")) out.println("selected=\"selected\"");%>>utf-8</option>
												<option value='euc-kr' <% if(settingValue.get("CharSet").equals("euc-kr")) out.println("selected=\"selected\"");%>>euc-kr</option>
												<option value='ks_c_5601-1987' <% if(settingValue.get("CharSet").equals("ks_c_5601-1987")) out.println("selected=\"selected\"");%>>ks_c_5601-1987</option>
												<option value='ms949' <% if(settingValue.get("CharSet").equals("ms949")) out.println("selected=\"selected\"");%>>ms949</option>
												<option value='iso-8859-1' <% if(settingValue.get("CharSet").equals("iso-8859-1")) out.println("selected=\"selected\"");%>>iso-8859-1</option>
												<option value='iso-8859-2' <% if(settingValue.get("CharSet").equals("iso-8859-2")) out.println("selected=\"selected\"");%>>iso-8859-2</option>
												<option value='euc-jp' <% if(settingValue.get("CharSet").equals("euc-jp")) out.println("selected=\"selected\"");%>>euc-jp</option>
												<option value='shift_jis' <% if(settingValue.get("CharSet").equals("shift_jis")) out.println("selected=\"selected\"");%>>shift_jis</option>
												<option value='gb2312' <% if(settingValue.get("CharSet").equals("gb2312")) out.println("selected=\"selected\"");%>>gb2312</option>
												<option value='gbk' <% if(settingValue.get("CharSet").equals("gbk")) out.println("selected=\"selected\"");%>>gbk</option>
												<option value='big5' <% if(settingValue.get("CharSet").equals("big5")) out.println("selected=\"selected\"");%>>big5</option>
												<option value='big5-hkscs' <% if(settingValue.get("CharSet").equals("big5-hkscs")) out.println("selected=\"selected\"");%>>big5-hkscs</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_qM"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="text" id="DocBaseURL" name="DocBaseURL" class="ce_eu" value="<%=settingValue.get("DocBaseURL")%>" /> ex) http://www.mysite.com/doc.html 
										</td>
									</tr>
								</table>

								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
						
							</td>
						</tr>
					</table>
				</div>	
				
			</td>
		</tr>
		<tr>
			<td class="ce_dk">
				<div id="ce_Ax">
					<table class="ce_ld">
						<tr>
							<td>
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
								 
								<table class="ce_bQ">
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_rK"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<% out.println(skinDirectory(fileRealFolder, (String)settingValue.get("Skin"))); %>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_ql"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="text" id="Css" name="Css" class="ce_eu" value="<%=settingValue.get("Css")%>" />  ex) http://www.mysite.com/common.css 
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_rB"></span></b>
											<input type="hidden" id="UserSkinColor" name="UserSkinColor" value="<%=settingValue.get("UserSkinColor")%>" />
										</td>
											
										<td class="ce_bx"></td>
										<td >
											<table class="ce_bQ">
												<tr>
													<td class="ce_in">&nbsp;&nbsp;<span id="ce_tZ"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz">
														<input type="text" id="ce_ht" name="ce_ht" class="ce_im" value="" />  ex) #000000 or black 
													</td>
												</tr>
												<tr>
													<td class="ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td class="ce_in">&nbsp;&nbsp;<span id="ce_sZ"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz">
														<input type="text" id="ce_hn" name="ce_hn" class="ce_im" value="" />
													</td>
												</tr>
												<tr>
													<td class="ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td class="ce_in">&nbsp;&nbsp;<span id="ce_tO"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz">
														<input type="text" id="ce_hb" name="ce_hb" class="ce_im" value="" />
													</td>
												</tr>
												<tr>
													<td class="ce_bC" colspan="3"></td>
												</tr>
												<tr>
													<td class="ce_in">&nbsp;&nbsp;<span id="ce_sf"></span></td>
													<td class="ce_bx"></td>
													<td class="ce_bz">
														<input type="text" id="ce_hu" name="ce_hu" class="ce_im" value="" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_tv"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="hidden" id="CreateTab" name ="CreateTab" value="<%=settingValue.get("CreateTab")%>" />
											<input type="checkbox" id="ce_nm" name="ce_iS" value="0" /><label for="ce_nm"><span id="wysiwyg"></span></label>&nbsp;&nbsp;
											<input type="checkbox" id="ce_mE" name="ce_iS" value="1" /><label for="ce_mE"><span id="html"></span></label>&nbsp;&nbsp;
											<input type="checkbox" id="ce_mS" name="ce_iS" value="2" /><label for="ce_mS"><span id="preview"></span></label>
										</td>
									</tr>
								</table>
									
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
							</td>
						</tr>

						<tr>
							<td class="ce_lV ce_fk"><font style="font-size:9pt;color:#FF9F4B;font-weight:bold;"><span id="ce_sh"></span></font></td>
						</tr>
						<tr>
							<td>
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
									
								<table class="ce_bQ">
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_rt"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<select name='UserToolbar' id='UserToolbar' class="inputSelectStyle">
												<option value='false' <% if(settingValue.get("UserToolbar").equals("false")) out.println("selected=\"selected\"");%>>false</option>
												<option value='true' <% if(settingValue.get("UserToolbar").equals("true")) out.println("selected=\"selected\"");%>>true</option>
											</select>
											<input type="hidden" id="CreateToolbar" name="CreateToolbar" value="<%=settingValue.get("CreateToolbar")%>" >
											<input type="hidden" id="Name" name="Name" value="<%=settingValue.get("Name")%>">
											<input type="hidden" id="Logo" name="Logo" value="<%=settingValue.get("Logo")%>">
											<input type="hidden" id="Help" name="Help" value="<%=settingValue.get("Help")%>">
											<input type="hidden" id="Info" name="Info" value="<%=settingValue.get("Info")%>">
											<input type="hidden" id="UserAddMenu" name="UserAddMenu" value="<%=userAddMenuList%>" />
											<input type="hidden" id="AddMenuCheck" name="AddMenuCheck" value="<%=settingValue.get("AddMenuCheck")%>" />
											<input type="hidden" id="Tab" name="Tab" value="" />
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
										
								</table>	
							</td>		
						</tr>			
					</table>

					<div id="ce_ub">
						<table class="ce_fn">
							<tr>
								<td>
									<table class="ce_bQ">
										<tr>
											<td class="ce_bC" colspan="5"></td>
										</tr>
										<tr>
											<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_uC"></span></b></td>
											<td class="ce_bx"></td>
											<td>
												<table class="ce_bQ">
													<tr>
														<td class="ce_iK" >&nbsp;&nbsp;<span id="ce_uu"></span></td>
														<td class="ce_bx"></td>
														<td class="ce_bz">
															<input type="text" id="ce_gc" name="ce_gc" class="ce_eu" value="" /> 
														</td>
													</tr>
													<tr>
														<td class="ce_bC" colspan="3"></td>
													</tr>
													<tr>
														<td class="ce_iK">&nbsp;&nbsp;<span id="ce_vI"></span></td>
														<td class="ce_bx"></td>
														<td class="ce_bz">
															<input type="radio" name="ce_ia" id="ce_qr" value="function" /><label for="ce_qr"><span id="ce_uI"></span></label>&nbsp;&nbsp;<input type="radio" name="ce_ia" id="ce_qx" value="plugin" /><label for="ce_qx"><span id="ce_vo"></span></label>
														</td>
													</tr>
													<tr>
														<td class="ce_bC" colspan="3"></td>
													</tr>
													<tr>
														<td class="ce_iK">&nbsp;&nbsp;<span id="ce_rZ"></span></td>
														<td class="ce_bx"></td>
														<td class="ce_bz" height="50px">
															<input type="text" id="ce_gP" name="ce_gP" class="ce_eu" value="" />
															<br/>ex) http://www.mysite.com/image/MenuIcon.jpg
														</td>
													</tr>
													<tr>
														<td class="ce_bC" colspan="3"></td>
													</tr>
													<tr>
														<td class="ce_iK">&nbsp;&nbsp;<span id="ce_vf"></span></td>
														<td class="ce_bx"></td>
														<td class="ce_bz">	
															<input type="text" id="ce_lW" name="ce_lW" class="ce_eu" value="" />
														</td>
													</tr>
												</table>
											</td>
											<td class="ce_bx"></td>
											<td style="text-align:center;vertical-align:middle;"><input type="button" id="ce_qd" value="" class="" style="width:60px;height:60px;"/>
											</td>
										</tr>
										<tr>
											<td class="ce_bC" colspan="5"></td>
										</tr>
										<tr>
											<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_rj"></span></b></td>
											<td class="ce_bx"></td>
											<td class="ce_bz" colspan="2">
												<div id="ce_Ny">&nbsp;</div>
											</td>
										</tr>
										<tr>
											<td class="ce_bC" colspan="5"></td>
										</tr>
									</table>	
								</td>		
							</tr>
						</table>
					</div>
		
					<div id = "ce_bQ">
						
						<table class="ce_fn">	
							<tr>
								<td id="ce_ua" class="ce_fk">
									
									<table>
										<tr>
											<td id="ce_NR">
												<span id="ce_tf"></span>: <br>
												<div id="ce_AQ" >
													<span id="ce_lQ"></span> <br> 
													<span id="ce_qj"></span><br>
													<span id="ce_pm"></span><br>
												</div>
											</td>
											<td id="ce_KP">
											<span id="ce_ud"></span>:<br>
												<div id="ce_wM" ></div>
											</td> 
											<td valign="bottom">
												<ul style="margin:0 auto;width:340px;">
													<li class="ce_eh">
														<input type="button" id="spacebar" value="" class="ce_eb ce_de" style="width:80px;height:26px;" />
													</li>
													<li class="ce_eh"><input type="button" id="space" value="" class="ce_eb ce_de" style="width:68px;height:26px;"></li>
													<li class="ce_eh"><input type="button" id="enter" value="" class="ce_eb ce_de" style="width:66px;height:26px;"></li>
												</ul>
											</td>
										</tr>
									</table>
									
								</td>
							</tr>
							<tr>
								<td>
									<table class="ce_bQ">
										<tr><td class="ce_cY" colspan="3"></td></tr>
									</table>
								</td>
							</tr>
						</table>	
				
						<table id="ce_Se" class="ce_fn">
							
							<tr>
								<td id="ce_ua" class="ce_fk">
									<span id="preview"></span>:<br>
									<div id="ce_FL"></div>
									<br />
									<span id="ce_sR"></span>
								</td>
							</tr>
						</table>
									
					</div>
				</div>	

			</td>
		</tr>
		<tr>
			<td class="ce_dk">
				<div id="ce_AK">
					<table class="ce_ld">
						<tr>
							<td>
								<table class="ce_bQ"><tr><td class="ce_cY" colspan="3"></td></tr>
								</table>
								 
								<table class="ce_bQ">
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_sC"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="text" id="DocTitle" name="DocTitle" class="ce_eu" value="<%=settingValue.get("DocTitle")%>" />
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
									<tr>
										<td class="ce_bI">&nbsp;&nbsp;&nbsp;&nbsp;<b><span id="ce_sj"></span></b></td>
										<td class="ce_bx"></td>
										<td class="ce_bz">
											<input type="hidden" id="AccessibilityOption" name ="AccessibilityOption" value="<%=settingValue.get("AccessibilityOption")%>" />
											<input type="radio" id="ce_pa" name="ce_mY" value="0" /><label for="ce_pa"><span id="ce_ue"></span></label>&nbsp;&nbsp;
											<input type="radio" id="ce_qN" name="ce_mY" value="1" /><label for="ce_qN"><span id="ce_tP"></span></label>&nbsp;&nbsp;
											<input type="radio" id="ce_qk" name="ce_mY" value="2" /><label for="ce_qk"><span id="ce_tN"></span></label>
										</td>
									</tr>
									<tr>
										<td class="ce_bC" colspan="3"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
</table>

<table class="ce_ie">
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
	
</div>
</form>
<%@include file = "../include/bottom.html"%>
<script>
	var webPageKind = '<%=session.getAttribute("webPageKind")%>';
	var ce_uF = '<%=encodingStyleValue%>';
	ce_f();
	ce_u('<% if(request.getParameter("Tab") != null) out.print(request.getParameter("Tab"));%>');
</script>

</body> 
</html>