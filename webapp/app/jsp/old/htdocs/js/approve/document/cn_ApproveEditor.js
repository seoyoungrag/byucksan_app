function changeEditorAccess()
{
	if (g_strBodyType == "gul")
	{
//		commonAccess.src = "";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveHunDocument.js";
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHunEnforce.js";
	}
	else if (g_strBodyType == "hwp")
	{
//		commonAccess.src = "./htdocs/js/approve/document/cn_ApproveHwpCommon.js";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveHwpDocument.js";
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHwpEnforce.js";
	}
	else if (g_strBodyType == "han")
	{
//		commonAccess.src = "./htdocs/js/approve/document/cn_ApproveHwp2002Common.js";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveHwp2002Document.js";
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHwp2002Enforce.js";
	}
	else if (g_strBodyType == "doc")
	{
//		commonAccess.src = "";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveWordDocument.js";
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveWordEnforce.js";
	}
	else if (g_strBodyType == "html")
	{
//		commonAccess.src = "";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveHtmlDocument.js";
		enforceAccess.src = "";
	}
	else if (g_strBodyType == "txt")
	{
//		commonAccess.src = "";
		bodyAccess.src = "./htdocs/js/approve/document/cn_ApproveTextDocument.js";
		enforceAccess.src = "";
	}
}

function changeEnforceAccess()
{
	if (g_strBodyType == "gul")
	{
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHunEnforce.js";
	}
	else if (g_strBodyType == "hwp")
	{
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHwpEnforce.js";
	}
	else if (g_strBodyType == "han")
	{
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveHwp2002Enforce.js";
	}
	else if (g_strBodyType == "doc")
	{
		enforceAccess.src = "./htdocs/js/approve/document/cn_ApproveWordEnforce.js";
	}
	else if (g_strBodyType == "html")
	{
		enforceAccess.src = "";
	}
	else if (g_strBodyType == "txt")
	{
		enforceAccess.src = "";
	}
}

function getEditorAccessHtml()
{
	var strHtml = "";
	if (g_strBodyType == "gul")
	{
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHunDocument.js\"></SCRIPT>\n";
		if (g_strIsDirect == "N" && (g_strOption46 == "8" || g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "11" || g_strEditType == "12" || g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17"))
		{
			strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHunEnforce.js\"></SCRIPT>\n";
		}
	}
	else if (g_strBodyType == "hwp")
	{
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwpCommon.js\"></SCRIPT>\n";
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwpDocument.js\"></SCRIPT>\n";

		if (g_strIsDirect == "N" && (g_strOption46 == "8" || g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "11" || g_strEditType == "12" || g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17"))
		{
			strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwpEnforce.js\"></SCRIPT>\n";
		}
	}
	else if (g_strBodyType == "han")
	{
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwp2002Common.js\"></SCRIPT>\n";
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwp2002Document.js\"></SCRIPT>\n";

		if (g_strIsDirect == "N" && (g_strOption46 == "8" || g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "11" || g_strEditType == "12" || g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17"))
		{
			strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHwp2002Enforce.js\"></SCRIPT>\n";
		}
	}
	else if (g_strBodyType == "doc")
	{

		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveWordDocument.js\"></SCRIPT>\n";
		strHtml += "<SCRIPT language=\"javascript\" for=\"wordObject\" EVENT=\"NavigateComplete2(objDisp, strUrl)\">\n";
		strHtml += "	return wordObject_NavigateComplete2(objDisp, strUrl)\n";
		strHtml += "</SCRIPT>\n";

		strHtml += "<SCRIPT language=\"VBScript\">\n";
		strHtml += "	Sub wordObject_BeforeNavigate2(objDisp, strUrl, flags, targetframename, postdata, headers, bCancel)\n";
		strHtml += "		onBeforeNavigate2 objDisp, strUrl\n";
		strHtml += "	End Sub\n";
		strHtml += "</SCRIPT>\n";

		if (g_strIsDirect == "N" && (g_strOption46 == "8" || g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "11" || g_strEditType == "12" || g_strEditType == "13" || g_strEditType == "14" || g_strEditType == "15" || g_strEditType == "16" || g_strEditType == "17"))
		{
			strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveWordEnforce.js\"></SCRIPT>\n";
			strHtml += "<SCRIPT language=\"javascript\" for=\"enforceObject\" EVENT=\"NavigateComplete2(objDisp, strUrl)\">\n";
			strHtml += "	return enforceObject_NavigateComplete2(objDisp, strUrl)\n";
			strHtml += "</SCRIPT>\n";

		}
	}
	else if (g_strBodyType == "html")
	{
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveHtmlDocument.js\"></SCRIPT>\n";
	}
	else if (g_strBodyType == "txt")
	{
		strHtml += "<SCRIPT language=\"javascript\" src=\"./htdocs/js/approve/document/cn_ApproveTextDocument.js\"></SCRIPT>\n";
	}
	
	return strHtml;
}

function getEditorHtml()
{
	var strHtml = "";

	if (g_strBodyType == "gul")
	{
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR>\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"1\">\n";
		strHtml += "			<OBJECT id=\"Document_Hun2KLa\" width=\"100%\" height=\"100%\" classid=\"CLSID:3CCEE269-A6D2-11D2-BF35-00A0C98C39D8\">\n";
		strHtml += "				<param name=\"_Version\" value=\"65536\"/>\n";
		strHtml += "				<param name=\"_ExtentX\" value=\"2646\"/>\n";
		strHtml += "				<param name=\"_ExtentY\" value=\"1323\"/>\n";
		strHtml += "				<param name=\"_StockProps\" value=\"0\"/>\n";
		strHtml += "			</OBJECT>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"1\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";
	}
	else if (g_strBodyType == "hwp")
	{
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" valign=\"center\">\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"1\">\n";
		strHtml += "			<OBJECT ID=\"Document_HWPProxy2\" width=\"100%\" height=\"1\" classid=\"CLSID:ED50765A-B652-48DB-ACD0-2989386B6FF4\">\n";
		strHtml += "				<PARAM NAME=\"_Version\" VALUE=\"65536\"/>\n";
		strHtml += "				<PARAM NAME=\"_ExtentX\" VALUE=\"2646\"/>\n";
		strHtml += "				<PARAM NAME=\"_ExtentY\" VALUE=\"1323\"/>\n";
		strHtml += "				<PARAM NAME=\"_StockProps\" VALUE=\"0\"/>\n";
		strHtml += "			</OBJECT>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"1\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";

		strHtml += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Document_HWPProxy2\" EVENT=\"OnCreated()\">\n";
		strHtml += "	return onDocumentHwpProxyCreated();\n";
		strHtml += "</SCRIPT>\n";
		strHtml += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Document_HWPProxy2\" EVENT=\"OnHWPNotFound()\">\n";
		strHtml += "	return onDocumentHwpNotFound();\n";
		strHtml += "</SCRIPT>\n";
		strHtml += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Document_HWPProxy2\" EVENT=\"OnHWPCommand(nCommandID)\">\n";
		strHtml += "	return onDocumentHwpProxyCommand(nCommandID);\n";
		strHtml += "</SCRIPT>\n";
		strHtml += "<SCRIPT LANGUAGE=\"JavaScript\" FOR=\"Document_HWPProxy2\" EVENT=\"OnManualSeal(strStampName)\">\n";
		strHtml += "	return onDocumentHwpManualSeal(strStampName);\n";
		strHtml += "</SCRIPT>\n";

	}
	else if (g_strBodyType == "han")
	{
		strHtml += "<OBJECT id=\"HWPUtil\" classid=\"CLSID:493D9E1A-E4D7-4F3D-9B7F-33D8DCCCC66A\">\n";
		strHtml += "    <PARAM name=\"_Version\" value=\"65536\"/>\n";
		strHtml += "    <PARAM name=\"_ExtentX\" value=\"2646\"/>\n";
		strHtml += "    <PARAM name=\"_ExtentY\" value=\"1323\"/>\n";
		strHtml += "    <PARAM name=\"_StockProps\" value=\"0\"/>\n";
		strHtml += "</OBJECT>\n";
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" valign=\"center\">\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"1\">\n";
		strHtml += "			<OBJECT id=\"Document_HwpCtrl\" width=\"100%\" height=\"100%\" classid=\"CLSID:BD9C32DE-3155-4691-8972-097D53B10052\">\n";
		strHtml += "				<PARAM name=\"_Version\" value=\"65536\"/>\n";
		strHtml += "				<PARAM name=\"_ExtentX\" value=\"20902\"/>\n";
		strHtml += "				<PARAM name=\"_ExtentY\" value=\"12435\"/>\n";
		strHtml += "				<PARAM name=\"_StockProps\" value=\"0\"/>\n";
		strHtml += "				<PARAM name=\"FILENAME\" value=\"\"/>\n";
		strHtml += "			</OBJECT>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"1\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";
	}
	else if (g_strBodyType == "doc")
	{
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" valign=\"center\">\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"456\">\n";
		strHtml += "			<OBJECT id=\"wordObject\" width=\"100%\" height=\"100%\" classid=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\">\n";
		strHtml += "				<param name=\"_Version\" value=\"65536\"/>\n";
		strHtml += "				<param name=\"_ExtentX\" value=\"2646\"/>\n";
		strHtml += "				<param name=\"_ExtentY\" value=\"1323\"/>\n";
		strHtml += "				<param name=\"_StockProps\" value=\"0\"/>\n";
		strHtml += "			</OBJECT>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"456\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";

		strHtml += "<SCRIPT language=\"javascript\" for=\"wordObject\" EVENT=\"NavigateComplete2(objDisp, strUrl)\">\n";
		strHtml += "	return wordObject_NavigateComplete2(objDisp, strUrl)\n";
		strHtml += "</SCRIPT>\n";
		strHtml += "<SCRIPT language=\"VBScript\">\n";
		strHtml += "	Sub wordObject_BeforeNavigate2(objDisp, strUrl, flags, targetframename, postdata, headers, bCancel)\n";
		strHtml += "		onBeforeNavigate2 objDisp, strUrl\n";
		strHtml += "	End Sub\n";
		strHtml += "</SCRIPT>\n";
	}
	else if (g_strBodyType == "html")
	{
		strHtml += "<SPAN id=\"DocInfoSpan\" style=\"display:none\">\n";
		strHtml += "</SPAN>\n";
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" valign=\"center\">\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"456\">\n";
		strHtml += "			<IFRAME name=\"editFrame\" src = \"\" width=\"100%\" height=\"100%\" scrolling=\"auto\" frameborder=\"no\" border=\"0\"></IFRAME>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"456\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";
	}
	else if (g_strBodyType == "txt")
	{
		strHtml += "<SPAN id=\"DocInfoSpan\" style=\"display:none\">\n";
		strHtml += "</SPAN>\n";
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" >\n";
		strHtml += "		<TD border=\"1\">\n";
		strHtml += "			<TEXTAREA style=\"width:100%;overflow:auto;\" id=\"TextBody\" name=\"txtOpinion\">\n";
		strHtml += "			</TEXTAREA>\n";
		strHtml += "		</TD>\n";
		strHtml += "		<TD id=\"enforceSide\" style=\"display:none\" height=\"456\">\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";
	}
	else
	{
		strHtml += "<TABLE width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n";
		strHtml += "	<TR align=\"center\" valign=\"middle\">\n";
		strHtml += "		<TD id=\"documentSide\" style=\"display:block\" height=\"456\">\n";
		strHtml += "			잠시후에 다시 시도하십시요.\n";
		strHtml += "		</TD>\n";
		strHtml += "	</TR>\n";
		strHtml += "</TABLE>\n";
	}

	return strHtml;
}
