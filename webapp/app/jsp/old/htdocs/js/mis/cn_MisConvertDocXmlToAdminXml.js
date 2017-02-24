// returns Converted "Exchange" XML

function onConvertDocXmlToAdminXml(strType)
{
	try
	{
		var objXml = new ActiveXObject("MSXML.DOMDocument");
		objXml.async = false;

		if (strType == "RETURN")
			objXml.loadXML(ApprovalDoc.documentElement.xml);
		else
			objXml.loadXML(dataTransform.ApprovalDoc.documentElement.xml);

		var strCompleteTime = "";
		var objApprover = objXml.selectSingleNode("//APPROVER[./@IS_ACTIVE='Y']");
		if (objApprover != null)
		{
			strCompleteTime = objApprover.selectSingleNode("SIGN_DATE").text;
		}

		
		var objXsl = new ActiveXObject("MSXML.DOMDocument");
		objXsl.async = false;

		var strUrl = g_strBaseUrl + "mis/xsl/CN_MisConvertDocXmlToAdminXml.xsl";
		objXsl.load(strUrl);

		var strResult = objXml.transformNode(objXsl);

		if(strResult != "")
		{

			objXml.loadXML(strResult);

//// fill original EXCHANGE.XML info & update info.

			// Calibrate Title
/*
			var objTitle = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/TITLE");
			var strTitle = objTitle.text;

			var arrTitle = strTitle.split("[연계문서]");
			objTitle.text = arrTitle[arrTitle.length-1];
*/
			var strSystemID = "GovernmentMisSystemExchangeXml";
			var objRelatedSystem = getRelatedSystemBySystemID(strSystemID);

			// CREATED_DATE
			var objCreatedDate = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/CREATED_DATE");
			var strDate = getCurrentDate("-") + " " + getCurrentTime(":");
			objCreatedDate.text = strDate;

			// LEVEL - specify "final"
			
			var objLines = objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/SANCTION_INFO/LINES");

			if (objLines != null)
			{
				var objLine = objLines.selectNodes("LINE");
				if (objLine != null)
				{
					var nLine = objLine.length;
					var objLastLine = objLines.selectSingleNode("LINE[LEVEL='" + nLine + "']");
					var objLevel = objLastLine.selectSingleNode("LEVEL");

					var strLastLevelType = objLastLine.selectSingleNode("SANCTION").getAttribute("type");
					if (strLastLevelType == "전결" || strLastLevelType == "대결" || strLastLevelType == "결재")
						objLevel.text = "final";

					// 대결,전결의 경우 전결의 DATE노드 clear - 결재를 타지 않으므로 필요없음.
/*
					var nLineTaeKyul = nLine - 1;
					if (nLineTaeKyul > 1 && strLastLevelType == "전결")
					{
						var objTaeKyulLine = objLines.selectSingleNode("LINE[LEVEL='" + nLineTaeKyul + "']");
						var strLevelType = objTaeKyulLine.selectSingleNode("SANCTION").getAttribute("type");

						if (strLevelType == "대결")
						{
							objLastLine.selectSingleNode("SANCTION/DATE").text = "";
						}
					}
*/
				}
			}

			// 반송시 status 완료로, 기안자는 기안취소
			
			if (strType == "RETURN")
			{
				var objSanctionInfo = objXml.selectSingleNode("//SANCTION_INFO");
				if (objSanctionInfo != null)
					objSanctionInfo.setAttribute("status", "완료");
				
				var objLines = objSanctionInfo.selectSingleNode("LINES");
				if (objLines != null)
				{
					var objFirstLine = objLines.selectSingleNode("LINE[LEVEL='1']");
					if (objFirstLine != null)
					{
						var objSanction = objFirstLine.selectSingleNode("SANCTION");
						objSanction.setAttribute("result","기안취소");
					}
				}
			}

			// SENDER
			var objOriginSender = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/SENDER");
			var objTargetSender = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/RECEIVER");
			copyXMLNode(objOriginSender, objTargetSender);

			// RECEIVER
			var objOriginReceiver = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/RECEIVER");
			var objTargetReceiver = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/SENDER");
			copyXMLNode(objOriginReceiver, objTargetReceiver);

			// ADDENDA
			var objOriginAddenda = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/ADDENDA");
			if (objOriginAddenda != null)
			{
				var objHeader = objXml.selectSingleNode("EXCHANGE/HEADER");
				var objTargetAddenda = objXml.createNode(1, "ADDENDA", "");
				objHeader.appendChild(objTargetAddenda);
				copyXMLNode(objOriginAddenda, objTargetAddenda);
			}

			// MODIFIABLE
			var objModifiable = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/DIRECTION/TO_DOCUMENT_SYSTEM/MODIFICATION_FLAG/MODIFIABLE");
			var strModifyFlag = objModifiable.getAttribute("modifyflag");
			objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/MODIFICATION_FLAG/MODIFIABLE").setAttribute("modifyflag", strModifyFlag);

			// fill body to BODY(CDATA Section) node

			if (getApproverIsDocModified(objApprover) == "Y")
			{
				var objTextBox = document.getElementById("TextBody");
				var strBody = objTextBox.value;
				var objCDATA = objXml.createCDATASection(strBody);
				var objBody = objXml.createNode(1, "BODY", "");
				objXml.documentElement.appendChild(objBody);
				var objBody = objXml.selectSingleNode("EXCHANGE/BODY");
				objXml.selectSingleNode("EXCHANGE/BODY").appendChild(objCDATA);
			}

			// construct ATTACHMENTS Node & ATTACHNUM

			var nAttachNum = 0;
			var objFileInfo = ApprovalDoc.documentElement.selectSingleNode("FILE_INFO");

			var objRelatedAttaches = objFileInfo.selectSingleNode("RELATED_ATTACHES");

			var objBodyXml = null;
			var objBodyXsl = null;

			if (objRelatedAttaches != null)
			{
				objBodyXml = objRelatedAttaches.selectSingleNode("RELATED_ATTACH[./@MODIFY='1' && CLASSIFY='AttachBody' && SUBDIV='BodyXML']");

				objBodyXsl = objRelatedAttaches.selectSingleNode("RELATED_ATTACH[./@MODIFY='1' && CLASSIFY='AttachBody' && SUBDIV='BodyXSL']");
			}

			var objGeneralAttaches = objFileInfo.selectSingleNode("GENERAL_ATTACHES");
			var objGeneralAttach = null;
			if (objGeneralAttaches != null)
				objGeneralAttach = objGeneralAttaches.selectNodes("GENERAL_ATTACH[./@MODIFY='1']");

			if (objGeneralAttach != null)
			{
				var nGeneralAttach = objGeneralAttach.length;

				if (objBodyXml != null || objBodyXsl != null || nGeneralAttach > 0)
				{
					var objAttachments = objXml.createNode(1, "ATTACHMENTS", "");
					objXml.documentElement.appendChild(objAttachments);

					if (objBodyXml != null || objBodyXsl != null)
					{
						var objAdministrativeDB = objXml.createNode(1, "ADMINISTRATIVE_DB", "");
						objAttachments.appendChild(objAdministrativeDB);

						if (objBodyXml != null)
						{
							var objXmlFile = objXml.createNode(1, "XMLFILE", "");
							objAdministrativeDB.appendChild(objXmlFile);
							objXmlFile.text = objBodyXml.selectSingleNode("DISPLAY_NAME").text;
							objXmlFile.setAttribute("filename", objBodyXml.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}

						if (objBodyXsl != null)
						{
							var objXslFile = objXml.createNode(1, "XSLFILE", "");
							objAdministrativeDB.appendChild(objXslFile);
							objXslFile.text = objBodyXsl.selectSingleNode("DISPLAY_NAME").text;
							objXslFile.setAttribute("filename", objBodyXsl.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}
					}

					if (objGeneralAttach != null)
					{
						for (nIndex = 0; nIndex < objGeneralAttach.length; nIndex++)
						{
							var objNode = objGeneralAttach(nIndex);
							var objAttachment = objXml.createNode(1, "ATTACHMENT", "");
							objAttachments.appendChild(objAttachment);
							objAttachment.text = objNode.selectSingleNode("DISPLAY_NAME").text;
							objAttachment.setAttribute("filename", objNode.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}
					}
				}
			}
			// ATTACHNUM

			var objAttachNum = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/ATTACHNUM");
			if (objAttachNum != null)
				objAttachNum.text = nAttachNum;

			// MODIFIED

//			objApprover = getActiveApprover();
			if (objApprover != null)
			{
				if (nAttachNum > 0)
				{
					var objModificationFlag = objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/MODIFICATION_FLAG");
					var objModified = objModificationFlag.selectSingleNode("MODIFIED");

					if (objModified != null)
					{
						objModified.text = strCompleteTime;
					}
					else
					{
						objModified = objXml.createNode(1, "MODIFIED", "");
						objModificationFlag.appendChild(objModified);
						objModified.text = strCompleteTime;
					}
				}
			}

			// download general attaches
			downloadGeneralAttaches();

			var strXml = toXMLString(objXml);

//	for test
/*
			var strExchangeDocXml = strXml;
			if (strType == "RETURN")
				var strApprovalDocXml = ApprovalDoc.documentElement.xml;
			else
				var strApprovalDocXml = dataTransform.ApprovalDoc.documentElement.xml;

			var objTextBox = document.getElementById("TextBody");
			objTextBox.value = strApprovalDocXml + "\r\n\r\n\r\n" + strExchangeDocXml;
*/

			return strXml;
		}
		else
		{
			alert(ALERT_ERROR_CONVERT_TO_DOCXML);
			return "";
		}
	}
	catch(e)
	{
		alert(e.description);
	}
}


function onTestConvertDocXmlToAdminXml(strType)
{
	try
	{
		var objXml = new ActiveXObject("MSXML.DOMDocument");
		objXml.async = false;

		if (strType == "RETURN")
			objXml.loadXML(ApprovalDoc.documentElement.xml);
		else
			objXml.loadXML(dataTransform.ApprovalDoc.documentElement.xml);

		var strCompleteTime = "";
		var objApprover = objXml.selectSingleNode("//APPROVER[./@IS_ACTIVE='Y']");
		if (objApprover != null)
		{
			strCompleteTime = objApprover.selectSingleNode("SIGN_DATE").text;
		}

		
		var objXsl = new ActiveXObject("MSXML.DOMDocument");
		objXsl.async = false;

		var strUrl = g_strBaseUrl + "mis/xsl/CN_MisConvertDocXmlToAdminXml.xsl";
		objXsl.load(strUrl);

		var strResult = objXml.transformNode(objXsl);

		if(strResult != "")
		{

			objXml.loadXML(strResult);

//// fill original EXCHANGE.XML info & update info.

			// Calibrate Title
/*
			var objTitle = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/TITLE");
			var strTitle = objTitle.text;

			var arrTitle = strTitle.split("[연계문서]");
			objTitle.text = arrTitle[arrTitle.length-1];
*/
			var strSystemID = "GovernmentMisSystemExchangeXml";
			var objRelatedSystem = getRelatedSystemBySystemID(strSystemID);

			// LEVEL - specify "final"
			
			var objLines = objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/SANCTION_INFO/LINES");
			if (objLines != null)
			{
				var objLine = objLines.selectNodes("LINE");
				if (objLine != null)
				{
					var nLine = objLine.length;
					var objLastLine = objLines.selectSingleNode("LINE[LEVEL='" + nLine + "']");
					var objLevel = objLastLine.selectSingleNode("LEVEL");
					var strLastLevelType = objLastLine.selectSingleNode("SANCTION").getAttribute("type");
					if (strLastLevelType == "전결" || strLastLevelType == "대결" || strLastLevelType == "결재")
						objLevel.text = "final";

					// 대결,전결의 경우 전결의 DATE노드 clear - 결재를 타지 않으므로 필요없음.
/*
					var nLineTaeKyul = nLine - 1;
					if (nLineTaeKyul > 1 && strLastLevelType == "전결")
					{
						var objTaeKyulLine = objLines.selectSingleNode("LINE[LEVEL='" + nLineTaeKyul + "']");
						var strLevelType = objTaeKyulLine.selectSingleNode("SANCTION").getAttribute("type");

						if (strLevelType == "대결")
						{
							objLastLine.selectSingleNode("SANCTION/DATE").text = "";
						}
					}
*/
				}
			}

			// 반송시 status 완료로, 기안자는 기안취소
			
			if (strType == "RETURN")
			{
				var objSanctionInfo = objXml.selectSingleNode("//SANCTION_INFO");
				if (objSanctionInfo != null)
					objSanctionInfo.setAttribute("status", "완료");
				
				var objLines = objSanctionInfo.selectSingleNode("LINES");
				if (objLines != null)
				{
					var objFirstLine = objLines.selectSingleNode("LINE[LEVEL='1']");
					if (objFirstLine != null)
					{
						var objSanction = objFirstLine.selectSingleNode("SANCTION");
						objSanction.setAttribute("result","기안취소");
					}
				}
			}

			// SENDER
			var objOriginSender = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/SENDER");
			var objTargetSender = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/RECEIVER");
			copyXMLNode(objOriginSender, objTargetSender);

			// RECEIVER
			var objOriginReceiver = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/COMMON/RECEIVER");
			var objTargetReceiver = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/SENDER");
			copyXMLNode(objOriginReceiver, objTargetReceiver);

			// ADDENDA
			var objOriginAddenda = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/ADDENDA");
			if (objOriginAddenda != null)
			{
				var objHeader = objXml.selectSingleNode("EXCHANGE/HEADER");
				var objTargetAddenda = objXml.createNode(1, "ADDENDA", "");
				objHeader.appendChild(objTargetAddenda);
				copyXMLNode(objOriginAddenda, objTargetAddenda);
			}

			// MODIFIABLE
			var objModifiable = objRelatedSystem.selectSingleNode("//EXCHANGE/HEADER/DIRECTION/TO_DOCUMENT_SYSTEM/MODIFICATION_FLAG/MODIFIABLE");
			var strModifyFlag = objModifiable.getAttribute("modifyflag");
			objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/MODIFICATION_FLAG/MODIFIABLE").setAttribute("modifyflag", strModifyFlag);

			// fill body to BODY(CDATA Section) node

			var objTextBox = document.getElementById("TextBody");
			var strBody = objTextBox.value;
			var objCDATA = objXml.createCDATASection(strBody);
			var objBody = objXml.selectSingleNode("EXCHANGE/BODY");
			objXml.selectSingleNode("EXCHANGE/BODY").appendChild(objCDATA);

			// construct ATTACHMENTS Node & ATTACHNUM

			var nAttachNum = 0;
			var objFileInfo = ApprovalDoc.documentElement.selectSingleNode("FILE_INFO");

			var objRelatedAttaches = objFileInfo.selectSingleNode("RELATED_ATTACHES");

			var objBodyXml = null;
			var objBodyXsl = null;

			if (objRelatedAttaches != null)
			{
				objBodyXml = objRelatedAttaches.selectSingleNode("RELATED_ATTACH[./@MODIFY='1' && CLASSIFY='AttachBody' && SUBDIV='BodyXML']");

				objBodyXsl = objRelatedAttaches.selectSingleNode("RELATED_ATTACH[./@MODIFY='1' && CLASSIFY='AttachBody' && SUBDIV='BodyXSL']");
			}

			var objGeneralAttaches = objFileInfo.selectSingleNode("GENERAL_ATTACHES");
			var objGeneralAttach = null;
			if (objGeneralAttaches != null)
				objGeneralAttach = objGeneralAttaches.selectNodes("GENERAL_ATTACH[./@MODIFY='1']");
			
			if (objGeneralAttach != null)
			{
				var nGeneralAttach = objGeneralAttach.length;

				if (objBodyXml != null || objBodyXsl != null || nGeneralAttach > 0)
				{
					var objAttachments = objXml.createNode(1, "ATTACHMENTS", "");
					objXml.documentElement.appendChild(objAttachments);

					if (objBodyXml != null || objBodyXsl != null)
					{
						var objAdministrativeDB = objXml.createNode(1, "ADMINISTRATIVE_DB", "");
						objAttachments.appendChild(objAdministrativeDB);

						if (objBodyXml != null)
						{
							var objXmlFile = objXml.createNode(1, "XMLFILE", "");
							objAdministrativeDB.appendChild(objXmlFile);
							objXmlFile.text = objBodyXml.selectSingleNode("DISPLAY_NAME").text;
							objXmlFile.setAttribute("filename", objBodyXml.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}

						if (objBodyXsl != null)
						{
							var objXslFile = objXml.createNode(1, "XSLFILE", "");
							objAdministrativeDB.appendChild(objXslFile);
							objXslFile.text = objBodyXsl.selectSingleNode("DISPLAY_NAME").text;
							objXslFile.setAttribute("filename", objBodyXsl.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}

					}

					if (objGeneralAttach != null)
					{
						for (nIndex = 0; nIndex < objGeneralAttach.length; nIndex++)
						{
							var objNode = objGeneralAttach(nIndex);
							var objAttachment = objXml.createNode(1, "ATTACHMENT", "");
							objAttachments.appendChild(objAttachment);
							objAttachment.text = objNode.selectSingleNode("DISPLAY_NAME").text;
							objAttachment.setAttribute("filename", objNode.selectSingleNode("FILE_NAME").text);
							nAttachNum++;
						}
					}
				}
			}
			// ATTACHNUM

			var objAttachNum = objXml.selectSingleNode("EXCHANGE/HEADER/COMMON/ATTACHNUM");
			if (objAttachNum != null)
				objAttachNum.text = nAttachNum;

			// MODIFIED

//			objApprover = getActiveApprover();
			if (objApprover != null)
			{
				if (nAttachNum > 0)
				{
					var objModificationFlag = objXml.selectSingleNode("EXCHANGE/HEADER/DIRECTION/TO_ADMINISTRATIVE_SYSTEM/MODIFICATION_FLAG");
					var objModified = objModificationFlag.selectSingleNode("MODIFIED");

					if (objModified != null)
					{
						objModified.text = strCompleteTime;
					}
					else
					{
						objModified = objXml.createNode(1, "MODIFIED", "");
						objModificationFlag.appendChild(objModified);
						objModified.text = strCompleteTime;
					}
				}
			}

			// download general attaches
			downloadGeneralAttaches();

			var strXml = toXMLString(objXml);

//	for test

			var strExchangeDocXml = strXml;
			if (strType == "RETURN")
				var strApprovalDocXml = ApprovalDoc.documentElement.xml;
			else
				var strApprovalDocXml = dataTransform.ApprovalDoc.documentElement.xml;

			var objTextBox = document.getElementById("TextBody");
			objTextBox.value = strApprovalDocXml + "\r\n\r\n\r\n" + strExchangeDocXml;

			return strXml;
		}
		else
		{
			alert(ALERT_ERROR_CONVERT_TO_DOCXML);
			return "";
		}
	}
	catch(e)
	{
		alert(e.description);
	}
}

