/*
function getClassInfo()
{
	return getValueById("idDocNo");
}
*/

function setClassInfo4AddItem(strDocNumber)
{
	setValueById(strDocNumber, "idDocNo");
}

function onClickClassInfo()
{
	var strUrl = g_strBaseUrl + "approve/dialog/CN_ApproveClassInfo.jsp?type=additem";
	window.open(strUrl,"Approve_ClassInfo","toolbar=no,resizable=no, status=yes, width=350,height=400");
}

function onClickPerson(nType, strTargetId)		// 1 : submiter			2 : approver		3 : receiver	4 : charger
{
	var strUrl = g_strBaseUrl + "docflow/dialog/CN_DocflowGetPerson.jsp?type=" + nType + "&targetid=" + strTargetId + "&loc=additem";
	window.open(strUrl, "Docflow_Person", "toolbar=no, resizable=no, status=yes, width=460, height=260");
}

function setPersonInfo(strUserName, strUID, strTarget)
{
//	var objTarget = document.getElementById(strTarget);
//	objTarget.value = strUserName;
	setValueById(strUserName, strTarget)
}

function setPersonAttributeInfo(objUserInfo, strTargetId)
{
	var objTarget = document.getElementById(strTargetId);
	objTarget.setAttribute("deptname", objUserInfo.deptname);
	objTarget.setAttribute("deptid", objUserInfo.deptid);
	objTarget.setAttribute("compname", objUserInfo.compname);
	objTarget.setAttribute("compid", objUserInfo.compid);
	objTarget.setAttribute("groupname", objUserInfo.groupname);
	objTarget.setAttribute("groupid", objUserInfo.groupid);
	objTarget.setAttribute("gradeabbr", objUserInfo.gradeabbr);
	objTarget.setAttribute("grade", objUserInfo.grade);
	objTarget.setAttribute("positionabbr", objUserInfo.positionabbr);
	objTarget.setAttribute("position", objUserInfo.position);
	objTarget.setAttribute("username", objUserInfo.username);
	objTarget.setAttribute("uid", objUserInfo.uid);
	objTarget.setAttribute("userid", objUserInfo.userid);
}

function onClickFileName(strFileName, strGUIDFileName)
{
	var strTempPath = getDownloadPath();
//	if (!(createDownloadPath(strTempPath)))
//	{
//		alertMsg(ALERT_FAIL_TO_CREATE_FOLDER);
//		return;
//	}
	var strUplaodFile = g_strUploadUrl + strGUIDFileName;
	var strLocalFile = strTempPath + strFileName;
	if (!(downloadFile(strUplaodFile, strLocalFile)))
	{
		alertMsg("[ " + strFileName + " ]" + " " + ALERT_FAIL_TO_DOWNLOAD_FILE);
		return;
	}
	if (!(openLocalFile(strLocalFile)))
	{
		alertMsg("[ " + strFileName + " ]" + " " + ALERT_FAIL_TO_OPEN_FILE);
		return;
	}
}

function onClickAddAttach()
{
	var strLocalFiles = openFileDialog("?");
	if (strLocalFiles != "")
	{
		showWaitDlg(TITLE_FILE_ATTACH, FILE_ATTACH_MESSAGE);
		var strAttachTag = "";
		var arrLocalFile = strLocalFiles.split("?");
		var objCheckBoxs = getCheckBoxs("chkFile");
		for (var i = 0; i < (objCheckBoxs.length + arrLocalFile.length - 1); ++i)
		{
			if ((i % 2) == 0)
				strAttachTag = strAttachTag + getOpenTBLTag();

			var strFileName = "";
			var strGUIDFileName = "";
			var strFileSize = "";
			if (i < objCheckBoxs.length)
			{
				strFileName = objCheckBoxs[i].getAttribute("filename");
				strGUIDFileName = objCheckBoxs[i].getAttribute("guidfilename");
				strFileSize = objCheckBoxs[i].getAttribute("filesize");
			}
			else
			{
				var strLocalFile = arrLocalFile[i-objCheckBoxs.length];
				var nFileSize = getLocalFileSize(strLocalFile);
				var nPosition = strLocalFile.lastIndexOf("\\");
				var strFileExt = "";

				strFileName = strLocalFile.substring(nPosition + 1);
				nPosition = strFileName.lastIndexOf(".");
				strFileExt = strFileName.substring(nPosition);
				strGUIDFileName = getGUID() + strFileExt;
				strFileSize = getFileSize(nFileSize);
				if (!(uploadFile(strLocalFile, g_strUploadUrl + strGUIDFileName)))
				{
					alertMsg("[ " + strFileName + " ]" + " " + ALERT_FAIL_TO_UPLOAD_FILE);
					continue;
				}
			}
			strAttachTag = strAttachTag + getAttachTDTag(strFileName, strGUIDFileName, strFileSize);

			if ((i % 2) == 1)
				strAttachTag = strAttachTag + getCloseTBLTag();
		}
		drawAttachTag("idAttach", strAttachTag);
		hideWaitDlg();
	}
}

function getCheckBoxs(strName)
{
	var objCheckBoxs = document.getElementsByName(strName);
	return objCheckBoxs;
}

function getCheckFlag(objChecks)
{
	var bCheckFlag = true;
	for (var i = 0; i < objChecks.length; ++i)
	{
		if (!objChecks[i].checked)
		{
			bCheckFlag = false;
			break;
		}
	}
	return bCheckFlag;
}

function getCheckCount(objCheckBoxs)
{
	var nCheckCount = 0;
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		if (objCheckBoxs[i].checked)
		{
			nCheckCount += 1;
		}
	}
	return nCheckCount;
}

function onClickAllCheck(strName)
{
	var objCheckBoxs = getCheckBoxs(strName);
	var bCheckFlag = getCheckFlag(objCheckBoxs);
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		objCheckBoxs[i].checked = (!bCheckFlag);
	}
}

function drawAttachTag(strID, strTag)
{
	var objAttach = document.getElementById(strID);
	objAttach.innerHTML = strTag;
}

function onClickDelAttach()
{
	var objCheckBoxs = getCheckBoxs("chkFile");
	if (objCheckBoxs.length > 0)
	{
		var bCheckFlag = getCheckFlag(objCheckBoxs);
		if (!bCheckFlag)
		{
			var nCheckCount = getCheckCount(objCheckBoxs);
			if (nCheckCount > 0)
			{
				var strAttachTag = getRemainAttach(objCheckBoxs);
				drawAttachTag("idAttach", strAttachTag);
			}
			else
			{
				alert(ALERT_SELECT_ATTACH4DEL);
			}
		}
		else
		{
			drawAttachTag("idAttach", "<TABLE><TR><TD></TD></TR></TABLE>");
		}
	}
}

function getRemainAttach(objCheckBoxs)
{
	var strAttachTag = "";
	var nUnCheckCount = 0;
	for (var i = 0; i < objCheckBoxs.length; ++i)
	{
		if (!objCheckBoxs[i].checked)
		{
			if ((nUnCheckCount % 2) == 0)
				strAttachTag = strAttachTag + getOpenTBLTag();

			var strFileName = objCheckBoxs[i].getAttribute("filename");
			var strGUIDFileName = objCheckBoxs[i].getAttribute("guidfilename");
			var strFileSize = objCheckBoxs[i].getAttribute("filesize");
			strAttachTag = strAttachTag + getAttachTDTag(strFileName, strGUIDFileName, strFileSize);

			if (((nUnCheckCount % 2) == 1) || (objCheckBoxs[i+1] == null))
				strAttachTag = strAttachTag + getCloseTBLTag();

			nUnCheckCount += 1;
		}
	}
	return strAttachTag;
}

function getOpenTBLTag()
{
	var strOpenTBLTag = "<TABLE width='100%' border='0' align='center' cellpadding='0' cellspacing='0'><TR>";
	return strOpenTBLTag;
}

function getCloseTBLTag()
{
	var strCloseTBLTag = "</TR></TABLE>";
	return strCloseTBLTag;
}

function getAttachTDTag(strFileName, strGUIDFileName, strFileSize)
{
	var strAttachTDTag = "";
	strAttachTDTag = strAttachTDTag + "<TD width='50%' class='text_gr'><INPUT type='checkbox' id='idCheck' name='chkFile' filename='" + strFileName + "' guidfilename='" + strGUIDFileName + "' filesize='" + strFileSize + "'></INPUT>&nbsp;";
	strAttachTDTag = strAttachTDTag + "<IMG src='./image/" + g_strVersion + "/" + getFileImage(strFileName) + "' width='16' height='16'></IMG>&nbsp;";
	strAttachTDTag = strAttachTDTag + "<A href='#' class='gr' onclick='javascript:onClickFileName(&quot;" + strFileName + "&quot;, &quot;" + strGUIDFileName + "&quot;);return(false);'>" + strFileName + "&nbsp;&lt;" + strFileSize + "KB&gt;</A></TD>";

	return strAttachTDTag;
}

function getFileImage(strFileName)
{
	var  strFileExt ;
	strFileExt = strFileName.substring(strFileName.lastIndexOf('.') +1, strFileName.length);

	if (strFileExt.toUpperCase() == "HTML")
		strFileExt = "HTM";

	if ((strFileExt.toUpperCase() != "XLS") &&
		(strFileExt.toUpperCase() != "DOC") &&
		(strFileExt.toUpperCase() != "PPT") &&
		(strFileExt.toUpperCase() != "GUL") &&
		(strFileExt.toUpperCase() != "HTM") &&
		(strFileExt.toUpperCase() != "TXT") &&
		(strFileExt.toUpperCase() != "HWP") &&
		(strFileExt.toUpperCase() != "BMP") &&
		(strFileExt.toUpperCase() != "EXE") &&
		(strFileExt.toUpperCase() != "GIF") &&
		(strFileExt.toUpperCase() != "HTM") &&
		(strFileExt.toUpperCase() != "INI") &&
		(strFileExt.toUpperCase() != "JPG") &&
		(strFileExt.toUpperCase() != "MGR") &&
		(strFileExt.toUpperCase() != "MPG") &&
		(strFileExt.toUpperCase() != "PDF") &&
		(strFileExt.toUpperCase() != "TIF") &&
		(strFileExt.toUpperCase() != "TXT") &&
		(strFileExt.toUpperCase() != "XML") &&
		(strFileExt.toUpperCase() != "WAV") &&
		(strFileExt.toUpperCase() != "FSC") &&
		(strFileExt.toUpperCase() != "ZIP") &&
		(strFileExt.toUpperCase() != "BC") &&
		(strFileExt.toUpperCase() != "DL"))
			strFileExt = "etc";

	strFileExt = strFileExt.toLowerCase();
	strFileExt = "attach_" + strFileExt + ".gif";

	return strFileExt;
}

function getFileSize(nByteSize)
{
	var strFileSize;
	strFileSize = "";

	if (nByteSize == 0)
		strFileSize = "0";
	else if (nByteSize < 512)
		strFileSize = "1";
	else
		strFileSize = Math.round(nByteSize/1024).toString();

	return strFileSize;
}

function alertMsg(strMsg)
{
	alert(strMsg);
}

function toXMLString(objXML)
{
	var strResult = "";
	var strXML = objXML.xml;

	var strPrevElement = "";
	var nFind = strXML.indexOf("><");
	while (nFind != -1)
	{
		var bSeparate = false;

		if (strXML.charAt(nFind + 2) == '/')
		{
			var nNextFind = strXML.indexOf(">", nFind + 2);
			if (nNextFind != -1)
			{
				var strNextElement = strXML.substring(nFind + 3, nNextFind);
				var strPrevElement = strXML.substring(nFind - strNextElement.length, nFind);
				if (strNextElement != strPrevElement)
				{
					bSeparate = true;
				}
				else
				{
					nFind = strXML.indexOf("><", nFind + 2);
				}
			}
		}
		else
		{
			bSeparate = true;
		}

		if (bSeparate)
		{
			strResult += strXML.substring(0, nFind + 1);
			strResult += "\r\n";
			strXML = strXML.substring(nFind + 1, strXML.length);

			nFind = strXML.indexOf("><");
		}
	}

	strResult += strXML;

	return strResult;
}