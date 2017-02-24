function getTitleHtml()
{
	var strTitleHtml = "";
	if (g_strBodyType == "hwp")
	{
		strTitleHtml = "<TABLE border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' align='center'>" +
					"<TR valign='center'>" +
					"<TD align='center' width='100%' valign='center'>" +
					"<B><FONT color='darkblue' size='2'>" +
					"<DIV id='waitMessage' style='top:0;left:0;visibility:visible;'>" + MESSAGE_WAIT_FOR_HANGUL + "</DIV>" +
					"</FONT></B>" +
					"</TD>" +
					"</TR>" +
					"</TABLE>";
	}
	else
	{
		if (g_strEditType == "0" || g_strEditType == "10")
			strTitleHtml = drawTitle(TITLE_WRITE_DOCUMENT);
		else
			strTitleHtml = drawTitle(TITLE_READ_DOCUMENT + " (" + g_strSubject + ")");
	}
	return strTitleHtml;
}

function drawTitle(strTitle)
{
	var strHtml = "";
	if (g_strVersion.indexOf("1.0") != -1 || g_strVersion.indexOf("1.5") != -1)
	{
		strHtml += "<TABLE border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">";
		strHtml += "<TR><TD width=\"10\"><BR/></TD>";
		strHtml += "<TD width=\"42\"><IMG src=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/l_title_bg_1.gif\" height=\"36\"/></TD>";
		strHtml += "<TD background=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/c_title_bg_2.gif\" class=\"title_1\" nowrap>";
		strHtml += strTitle;
		strHtml += "</TD><TD width=\"46\"><DIV align=\"right\"><IMG src=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/c_title_bg_3.gif\" width=\"46\" height=\"36\"/></DIV></TD>";
		strHtml += "<TD width=\"10\"><BR/></TD></TR></TABLE>";
	}
	else
	{
		strHtml += "<TABLE width=\"100%\" height=\"33\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" background=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/pop_topbg.gif\">";
		strHtml += "<TR><TD width=\"10\"></TD>";
		strHtml += "<TD width=\"9\"><IMG src=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/tit_icon_pop.gif\" width=\"9\" height=\"9\"></TD>";
		strHtml += "<TD width=\"5\"></TD><TD class=\"title_wh\" nowrap>";
		strHtml += strTitle;
		strHtml += "</TD><TD width=\"19\"><IMG src=\"./image/";
		strHtml += g_strVersion;
		strHtml += "/bu5_close.gif\" width=\"19\" height=\"19\" onclick=\"javascript:onClose();return(false);\"></TD>";
		strHtml += "<TD width=\"10\"></TD></TR></TABLE>";
	}

	return strHtml;
}
