function openMenu()
{
	var strHtml = "";

	if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
	{
		strHtml += "<TABLE border='0' cellspacing='0' cellpadding='0' valign='bottom' width='100%'>";
		strHtml += "<TR><TD width='10'><BR/></TD><TD align='right' nowrap>";
	}
	else
	{
		strHtml += "<TABLE width='100%' border='0' cellpadding='0' cellspacing='0'>";
		strHtml += "<TR><TD height='13'></TD></TR><TR><TD>";
		strHtml += "<TABLE align='right' border='0' cellpadding='0' cellspacing='0'><TR>";
	}

	return strHtml;
}

function openApproveMenu(strBodyType, strID, strStyle)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml = "<INPUT type='hidden' id='" + strID + "' value='";
	}
	else
	{
		if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
		{
			strHtml += "<TABLE border='0' cellspacing='0' cellpadding='0' valign='bottom' width='100%'";
			strHtml += " id='" + strID + "' style='" + strStyle + "'>";
			strHtml += "<TR><TD width='10'><BR/></TD><TD align='right' nowrap>";
		}
		else
		{
			strHtml += "<TABLE width='100%' border='0' cellpadding='0' cellspacing='0'";
			strHtml += " id='" + strID + "' style='" + strStyle + "'>";
			strHtml += "<TR><TD height='13'></TD></TR><TR><TD>";
			strHtml += "<TABLE align='right' border='0' cellpadding='0' cellspacing='0'><TR>";
		}
	}

	return strHtml;
}

function addMenu(strMenu, strFunction)
{
	var strHtml = "";

	if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
	{
		strHtml += "<B><FONT color='#cccccc'>I</FONT></B>\r\n";
		strHtml += "<A href='#' class='bt1' onclick=\"";
		strHtml += strFunction;
		strHtml += "\">";
		strHtml += strMenu;
		strHtml += "</A>\r\n";
	}
	else
	{
		strHtml += "<TD><IMG src='./image/";
		strHtml += g_strVersion;
		strHtml += "/bu2_left.gif' width='8' height='20'></TD>";
		strHtml += "<TD background='./image/";
		strHtml += g_strVersion;
		strHtml += "/bu2_bg.gif' class='text_left' nowrap><A href='#' onclick=\"";
		strHtml += strFunction;
		strHtml += "\">";
		strHtml += strMenu;
		strHtml += "</A></TD>";
		strHtml += "<TD><IMG src='./image/";
		strHtml += g_strVersion;
		strHtml += "/bu2_right.gif' width='8' height='20'></TD>";
		strHtml += "<TD width='3'></TD>";
	}

	return strHtml;
}

function closeMenu()
{
	var strHtml = "";

	if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
	{
		strHtml += "<B><FONT color='#cccccc'>I</FONT></B>";
		strHtml += "</TD><TD width='10'><BR/></TD></TR><TR><TD height='5'></TD></TR></TABLE>";
	}
	else
	{
		strHtml += "<TD width='7'></TD></TR></TABLE></TD></TR><TR><TD height='5'></TD></TR></TABLE>";
	}

	return strHtml;
}

function closeApproveMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml = "'></INPUT>";
	}
	else
	{
		if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
		{
			strHtml += "<B><FONT color='#cccccc'>I</FONT></B>";
			strHtml += "</TD><TD width='10'><BR/></TD></TR><TR><TD height='5'></TD></TR></TABLE>";
		}
		else
		{
			strHtml += "<TD width='7'></TD></TR></TABLE></TD></TR><TR><TD height='5'></TD></TR></TABLE>";
		}
	}

	return strHtml;
}
