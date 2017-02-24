var g_nSenderAsFontSize = 18;
var g_objTargetHwpCtrl = null;

var g_strApprovalFieldNames = "";

var g_bSeal = true;
var g_bWithSize = false;
var g_strSealName = "";
var g_strSealPath = "";
var g_nImageWidth = 30;
var g_nImageHeight = 30;

function getValue(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return "";
	else
		return arr[key];
}

function isExistKey(arr, key)
{
	if (typeof(arr[key]) == "undefined")
		return false;
	else
		return true;
}

function verifyHwpCtrlVersion(objHwpCtrl)
{
	if (objHwpCtrl.getAttribute("Version") == null)
	{
		alert(MSG_HWPCTRL_NOT_INSTALLED);
		return false;
	}

	var nMinVersion =  0x0507011F;	// Hangul 2002 SE update (build 2881, 2002/10/10) for Hangul 2002
	var nCurVersion = objHwpCtrl.Version;

	if (nCurVersion < nMinVersion)
	{
		var strMsg = ALERT_UPDATE_RECOMMENDED + "\n\n" + CURRENT_VERSION + ": 0x" +
			nCurVersion.toString(16) + "\n" + RECOMMENDED_VERSION + ": 0x" +
			nMinVersion.toString(16) + " " + AND_HIGHER;

		alert(strMsg);
		window.close();
	}
	return true;
}

function initToolbar(objHwpCtrl, nType)
{
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_MENU");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_STANDARD");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_FORMAT");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_DRAW");
	objHwpCtrl.SetToolBar(-1, "TOOLBAR_TABLE");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_IMAGE");
//	objHwpCtrl.SetToolBar(-1, "TOOLBAR_HEADERFOOTER");
}

function changeEditMode(objHwpCtrl, nEditMode)
{
	if ((nEditMode & 0x01) != 0 && objHwpCtrl.FieldExist(FIELD_BODY))
	{
		objHwpCtrl.MoveToField(FIELD_BODY);
		if ((objHwpCtrl.CurFieldState & 0x0f) == 0x02)
			nEditMode = 0x02;
	}

	var vpSet = objHwpCtrl.ViewProperties;
	if ((nEditMode & 0x01) != 0 || (nEditMode & 0x02) != 0)
	{
		vpSet.SetItem("OptionFlag", 0x04 + 0x08);
		objHwpCtrl.ViewProperties = vpSet;
	}
	else
	{
		vpSet.SetItem("OptionFlag", 0);
		objHwpCtrl.ViewProperties = vpSet;
	}

	objHwpCtrl.EditMode = nEditMode;
}

function showToolbar(objHwpCtrl, nEditMode)
{
	if (nEditMode == 0)
		objHwpCtrl.ShowToolbar(false);
	else if ((nEditMode & 0x01) != 0 || (nEditMode & 0x02) != 0)
	{
		objHwpCtrl.ShowToolBar(true);
		
		// 찾기/바꾸기 Modeless Dialog에서 한글입력이 안되므로 한글입력이 되는 Modal Dialog로 변경
		objHwpCtrl.ReplaceAction("FindDlg", "HwpCtrlFindDlg");
		objHwpCtrl.ReplaceAction("ReplaceDlg", "HwpCtrlReplaceDlg");
		
		var lockCommandList = new Array(
		// 아래 Action은 OLE관련 문제로 인해 막아 놓는다.
		"OleCreateNew",
		"InsertTextArt",
		"InsertChart",
		"InsertVoice",

		// 아래 Action은 새창이나, 새탭을 사용하므로 막아 놓는다.
		"LabelTemplate", // 라벨 문서 만들기
		"ManuScriptTemplate", // 원고지 쓰기
		"IndexMark", // 찾아보기 표시
		"MakeIndex", // 찾아보기 만들기
		"MakeContents" // 차례만들기
		);
		for (i = 0; i < lockCommandList.length; i++)
			objHwpCtrl.LockCommand(lockCommandList[i], true);	
	}
}

function addProposal(objHwpCtrl)
{
	var diAction, diSet;
	var tableAction, tableSet;
	var nCurPara, nCurPos;
	var b2002 = false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var strField = FIELD_SENDER_AS + "#" + parent.getBodyCount();
	if (objHwpCtrl.FieldExist(strField))
	{
		objHwpCtrl.MoveToField(strField);

		if (parent.getBodyCount() == 1)
		{
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
			else
				objHwpCtrl.MoveToField(strField);

			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);

			objHwpCtrl.MovePos(26);
			diAction = objHwpCtrl.CreateAction("DocumentInfo");
			diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

			// copy
			var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
			var saveBlockSet = saveBlockAction.CreateSet();
			saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
			saveBlockSet.SetItem("Format", "HWP");
			saveBlockAction.Execute(saveBlockSet);
			objHwpCtrl.Run("Delete");

			objHwpCtrl.MovePos(3);			

			// paste
			objHwpCtrl.Insert(parent.getDownloadPath() + "clipboard.hwp");

			if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
			{
//				objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
//				objHwpCtrl.MovePos(26);

				// 개행문자...
				objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
				if (b2002)
					objHwpCtrl.MovePos(24);
				
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				tableSet.SetItem("TreatAsChar", 1);
				tableAction.Execute(tableSet);
			}
		}

		objHwpCtrl.MovePos(26);

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nCurPara = diSet.Item("CurPara");
		nCurPos = diSet.Item("CurPos");

		objHwpCtrl.MovePos(0, nCurPara, nCurPos + 8);
	}
	else
	{
		objHwpCtrl.MovePos(3);
	}

	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/Proposal.hwp");

	var nProposalCount = getBodyCount() + 1;

	objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (nProposalCount - 2) + "}}", "" + nProposalCount + "\u0002");

	objHwpCtrl.RenameField(FIELD_RECIPIENT + "{{1}}\u0002" + FIELD_REFERER + "{{1}}\u0002" + FIELD_TITLE + "{{1}}\u0002" + FIELD_SENDER_AS + "\u0002" + FIELD_RECIPIENT_REFER,
						FIELD_RECIPIENT + nProposalCount + "\u0002" + FIELD_REFERER + nProposalCount + "\u0002" + FIELD_TITLE + nProposalCount + "\u0002" + FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount);

//	enableAdjustTable(false);

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function addProposal2(objHwpCtrl)
{
	var diAction, diSet;
	var tableAction, tableSet;
	var nCurPara, nCurPos;
	var b2002 = false;
	var bBottomTableExist = false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var strField = FIELD_SENDER_AS + "#" + getBodyCount();
	bBottomTableExist = objHwpCtrl.FieldExist(strField);
	if (!bBottomTableExist)
	{
		strField = FIELD_RECIPIENT_REFER + "#" + getBodyCount();
		bBottomTableExist = objHwpCtrl.FieldExist(strField);
	}
	
	if (bBottomTableExist)
	{
		objHwpCtrl.MoveToField(strField);
		
		if (getBodyCount() == 1)
		{
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
			else
				objHwpCtrl.MoveToField(strField);

			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);

			objHwpCtrl.MovePos(26);
			diAction = objHwpCtrl.CreateAction("DocumentInfo");
			diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

			// copy
			var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
			var saveBlockSet = saveBlockAction.CreateSet();
			saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
			saveBlockSet.SetItem("Format", "HWP");
			saveBlockAction.Execute(saveBlockSet);
			objHwpCtrl.Run("Delete");

			objHwpCtrl.MovePos(3);			

			// paste
			objHwpCtrl.Insert(parent.getDownloadPath() + "clipboard.hwp");

			if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
			{
//				objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
//				objHwpCtrl.MovePos(26);

				// 개행문자...
				objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
				if (b2002)
					objHwpCtrl.MovePos(24);
				
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				tableSet.SetItem("TreatAsChar", 1);
				tableAction.Execute(tableSet);
			}			
		}

		objHwpCtrl.MovePos(26);
	}
	else
	{
		objHwpCtrl.MovePos(3);
	}

	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/Proposal2.hwp");

	var nProposalCount = getBodyCount() + 1;

	objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (nProposalCount - 2) + "}}", "" + nProposalCount + "\u0002");

	objHwpCtrl.RenameField(FIELD_RECIPIENT_REFER + "\u0002" + FIELD_RECIPIENT + "{{1}}\u0002" + FIELD_REFERER + "{{1}}\u0002" + FIELD_TITLE + "{{1}}\u0002" + FIELD_SENDER_AS + "#" + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1),
						FIELD_RECIPIENT_REFER + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT + nProposalCount + "\u0002" + FIELD_REFERER + nProposalCount + "\u0002" + FIELD_TITLE + nProposalCount + "\u0002" + FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount);

	var strReceiptBackup = objHwpCtrl.GetFieldText(FIELD_RECIPIENT_REFER + "#" + nProposalCount);
	
	objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1), strReceiptBackup);
	
	objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + nProposalCount, "");

//	enableAdjustTable(false);

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

// nIndex: zero base
function deleteProposal(objHwpCtrl, nIndex)
{
	var nProposalCount = getBodyCount();

	if (nIndex < 1 || nIndex >= nProposalCount)
		return false;

	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var diAction, diSet;

	var strField = FIELD_TITLE + (nIndex + 1);			
	objHwpCtrl.MoveToField(strField);		
	objHwpCtrl.MovePos(26);		
	diAction = objHwpCtrl.CreateAction("DocumentInfo");
	diSet = diAction.CreateSet();
	diAction.Execute(diSet);

	nStartPara = diSet.Item("CurPara");
	nStartPos = diSet.Item("CurPos");

	if (nIndex == nProposalCount - 1)
	{
		objHwpCtrl.MovePos(3);
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");					
	}
	else
	{
		strField = FIELD_TITLE + (nIndex + 2);
		objHwpCtrl.MoveToField(strField);
		objHwpCtrl.MovePos("26");
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");
	}

	objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	objHwpCtrl.Run("Delete");

	for (var i = nIndex; i < nProposalCount; i++)
	{
		objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (i - 1) + "}}", "" + (i + 1) + "\u0002");
		objHwpCtrl.RenameField(FIELD_RECIPIENT + (i + 1) + "\u0002" + FIELD_REFERER + (i + 1) + "\u0002" + FIELD_TITLE + (i + 1) + "\u0002" + FIELD_SENDER_AS + "#" + (i + 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (i + 1),
							FIELD_RECIPIENT + i + "\u0002" + FIELD_REFERER + i + "\u0002" + FIELD_TITLE + i + "\u0002" + FIELD_SENDER_AS + "#" + i + "\u0002" + FIELD_RECIPIENT_REFER + "#" + i);
	}

/*
	if (getBodyCount() == 1)
	 	enableAdjustTable(true);
*/
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function deleteProposal2(objHwpCtrl, nIndex)
{
	var nProposalCount = getBodyCount();

	if (nIndex < 1 || nIndex >= nProposalCount)
		return false;

//	objHwpCtrl.focus();
	var nEditMode = objHwpCtrl.EditMode;
	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x01;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var diAction, diSet;
	var bChangeLastTable = false;
	var strReceiptBackup;

	if (nIndex == nProposalCount - 1)
	{
		strField = FIELD_RECIPIENT_REFER + "#" + nIndex;
		if (objHwpCtrl.FieldExist(strField))
		{
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}
		else
		{
			strField = FIELD_TITLE + (nIndex + 1);
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nStartPara = diSet.Item("CurPara");
		nStartPos = diSet.Item("CurPos");

		var bLastTableExist = false;
		
		strField = FIELD_SENDER_AS + "#" + (nIndex + 1);
		bLastTableExist = objHwpCtrl.FieldExist(strField);
		if (!bLastTableExist)
		{
			strField = FIELD_RECIPIENT_REFER + "#" + (nIndex + 1);
			bLastTableExist = objHwpCtrl.FieldExist(strField);
		}

		if (bLastTableExist)
		{
			bChangeLastTable = true;
			strReceiptBackup = objHwpCtrl.GetFieldText(FIELD_RECIPIENT_REFER + "#" + nIndex);
			objHwpCtrl.MoveToField(strField);
			objHwpCtrl.MovePos(26);
		}
		else
		{
			objHwpCtrl.MovePos(3);
		}

		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);

		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");		
	}
	else
	{
		strField = FIELD_TITLE + (nIndex + 1);
		objHwpCtrl.MoveToField(strField);
		
		objHwpCtrl.MovePos(26);
		
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nStartPara = diSet.Item("CurPara");
		nStartPos = diSet.Item("CurPos");

		strField = FIELD_TITLE + (nIndex + 2);
		objHwpCtrl.MoveToField(strField);
		
		objHwpCtrl.MovePos(26);
		
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diAction.Execute(diSet);
	
		nEndPara = diSet.Item("CurPara");
		nEndPos = diSet.Item("CurPos");
	}

	objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	objHwpCtrl.Run("Delete");

	for (var i = nIndex; i < nProposalCount; i++)
	{
		objHwpCtrl.PutFieldText(FIELD_PROP_NUM + "{{" + (i - 1) + "}}", "" + (i + 1) + "\u0002");
		objHwpCtrl.RenameField(FIELD_RECIPIENT + (i + 1) + "\u0002" + FIELD_REFERER + (i + 1) + "\u0002" + FIELD_TITLE + (i + 1) + "\u0002" + FIELD_SENDER_AS + "#" + (i + 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (i + 1),
							FIELD_RECIPIENT + i + "\u0002" + FIELD_REFERER + i + "\u0002" + FIELD_TITLE + i + "\u0002" + FIELD_SENDER_AS + "#" + i + "\u0002" + FIELD_RECIPIENT_REFER + "#" + i);
	}

	if (bChangeLastTable)
	{
		objHwpCtrl.RenameField(FIELD_SENDER_AS + "#" + nProposalCount + "\u0002" + FIELD_RECIPIENT_REFER + "#" + nProposalCount,
							FIELD_SENDER_AS + "#" + (nProposalCount - 1) + "\u0002" + FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1));
		objHwpCtrl.PutFieldText(FIELD_RECIPIENT_REFER + "#" + (nProposalCount - 1), strReceiptBackup);
	}

/*
	if (getBodyCount() == 1)
	 	enableAdjustTable(true);
*/

	if ((nEditMode & 0x02) != 0)
		objHwpCtrl.EditMode = 0x02;

	return true;
}

function saveHwpDocument(objHwpCtrl, strFilePath, bAdjust)
{
	var tableAction, diAction;
	var tableSet, diSet;
	var nCurPara, nCurPos, nBottomPara, nBottomPos;
	var b2002 = false;

	objHwpCtrl.MovePos(2);

	if (bAdjust)
	{
		var bBodyExist = objHwpCtrl.FieldExist(FIELD_BODY);
		var bSenderAsExist = objHwpCtrl.FieldExist(FIELD_SENDER_AS + "#1");
		if (bSenderAsExist)
		{
			objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
			objHwpCtrl.MovePos(24);
			var parentCtrl = objHwpCtrl.ParentCtrl;
			if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
				b2002 = true;
		}

		// no body field - adjust
		if (!bBodyExist)
		{
			// title field - refer to as form
			if (objHwpCtrl.FieldExist(FIELD_TITLE))
			{
				// move top table
				objHwpCtrl.MoveToField(FIELD_TITLE);
				tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
				tableSet = tableAction.CreateSet();
				tableAction.GetDefault(tableSet);
				if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
				{
					objHwpCtrl.MovePos(26);
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diAction.Execute(diSet);

					nCurPara = diSet.Item("CurPara");
					nCurPos = diSet.Item("CurPos");
					if (nCurPara != 0 && nCurPos != 16)
					{
						objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

						objHwpCtrl.MovePos(2);
						objHwpCtrl.focus();
					}
				}

				// move bottom table
				if (parent.getBodyCount() == 1 && bSenderAsExist)
				{
					objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
					if (b2002)
						objHwpCtrl.MoveToField(24);
					tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
					tableSet = tableAction.CreateSet();
					tableAction.GetDefault(tableSet);
					if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
					{
						objHwpCtrl.MovePos(26);
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nCurPara = diSet.Item("CurPara");
						nCurPos = diSet.Item("CurPos");
					
						objHwpCtrl.MovePos(3);
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nBottomPara = diSet.Item("CurPara");
						nBottomPos = diSet.Item("CurPos");
						
						if (nCurPara != nBottomPara || nCurPos != nBottomPos - 8)
						{
							objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

							// copy
							var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
							var saveBlockSet = saveBlockAction.CreateSet();
							saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
							saveBlockSet.SetItem("Format", "HWP");
							saveBlockAction.Execute(saveBlockSet);
							objHwpCtrl.Run("Delete");

							objHwpCtrl.MovePos(3);
							objHwpCtrl.focus();

							// paste
							objHwpCtrl.Insert(parent.getDownloadPath() + "clipboard.hwp");
						}

						objHwpCtrl.focus();
/*
						if (!b2002)
						{
							// remove white space after bottom table
							var nBottomPara, nBottomPos, nCurPara, nCurPos, nPrevPara, nPrevPos;
							objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
							var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
							var tableSet = tableAction.CreateSet();
							tableAction.GetDefault(tableSet);
							if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
							{
								objHwpCtrl.MovePos(26);
								var diAction = objHwpCtrl.CreateAction("DocumentInfo");
								var diSet = diAction.CreateSet();
								diAction.Execute(diSet);

								nCurPara = diSet.Item("CurPara");
								nCurPos = diSet.Item("CurPos");

								nBottomPara = nCurPara;
								nBottomPos = nCurPos;

								do {
									// move to previous character
									objHwpCtrl.MovePos(15);

									var diAction = objHwpCtrl.CreateAction("DocumentInfo");
									var diSet = diAction.CreateSet();
									diAction.Execute(diSet);
									
									nPrevPara = diSet.Item("CurPara");
									nPrevPos = diSet.Item("CurPos");
									
									if (nCurPara == (nPrevPara + 1) && nCurPos == 0)
									{
										// carriage return;
									}
									else if (nCurPara == nPrevPara && nCurPos == (nPrevPos + 1))
									{
										var textSet = objHwpCtrl.CreateSet("GetText");
										objHwpCtrl.InitScan(0, 0);
										objHwpCtrl.GetTextBySet(textSet);
										objHwpCtrl.ReleaseScan();
										var strText = textSet.Item("Text");
			
										if (strText != "")
										{
											var nCharCode = strText.charCodeAt(0);
//											if (nCharCode == 0x0010 || nCharCode == 0x0013 || nCharCode == 0x0020 || nCharCode == 0x0009)
											if (nCharCode > 0x0020)
												break;
										}
									}
									nCurPara = nPrevPara;
									nCurPos = nPrevPos;
								} while (nCurPara > 0);
		
								// delete
								if (nCurPara != nBottomPara || nCurPos != nBottomPos)
								{
									objHwpCtrl.SelectText(nCurPara, nCurPos, nBottomPara, nBottomPos);
									objHwpCtrl.Run("Delete");
								}
							}	// remove white space after bottom table end
						}	// b2002 false end
*/
					}
				}	// move bottom table end

				// change table property into treat as char
				if (bSenderAsExist)
				{
					objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diSet.SetItem("DetailInfo", 1);
					diAction.Execute(diSet);
					if (diSet.Item("DetailCurPage") > 0)
					{
						objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
						if (b2002)
							objHwpCtrl.MovePos(24);
						tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
						tableSet = tableAction.CreateSet();
						tableAction.GetDefault(tableSet);
						tableSet.SetItem("TreatAsChar", 1);
						tableAction.Execute(tableSet);
					}
				}

			}	// title field exist end
			else	// no title field - memo
			{
				var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
				var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
				
				if (bDraftSign || bFinalSign)
				{
					if (bDraftSign)
						objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
					else if (bFinalSign)
						objHwpCtrl.MoveToField(FIELD_LAST + "1");
		
					objHwpCtrl.MovePos(26);
					
					diAction = objHwpCtrl.CreateAction("DocumentInfo");
					diSet = diAction.CreateSet();
					diAction.Execute(diSet);

					nCurPara = diSet.Item("CurPara");
					nCurPos = diSet.Item("CurPos");

					if (nCurPara != 0 || nCurPos != 16)
					{
						objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

						// copy
						var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
						var saveBlockSet = saveBlockAction.CreateSet();
						saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
						saveBlockSet.SetItem("Format", "HWP");
						saveBlockAction.Execute(saveBlockSet);
						objHwpCtrl.Run("Delete");

						objHwpCtrl.MovePos(2);

						// paste
						objHwpCtrl.Insert(parent.getDownloadPath() + "clipboard.hwp");
					}
					
					var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");
					var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
					if (bDraftDate || bFinalDate)
					{
						if (bDraftDate)
							objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
						else
							objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
						
						objHwpCtrl.MovePos(26);
						
						diAction = objHwpCtrl.CreateAction("DocumentInfo");
						diSet = diAction.CreateSet();
						diAction.Execute(diSet);

						nCurPara = diSet.Item("CurPara");
						nCurPos = diSet.Item("CurPos");

						if (nCurPara != 0 || nCurPos != (16 + 8))
						{
							objHwpCtrl.SelectText(nCurPara, nCurPos, nCurPara, nCurPos + 8);

							// copy
							var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
							var saveBlockSet = saveBlockAction.CreateSet();
							saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
							saveBlockSet.SetItem("Format", "HWP");
							saveBlockAction.Execute(saveBlockSet);
							objHwpCtrl.Run("Delete");

							objHwpCtrl.MovePos(0, 0, 8);

							// paste
							objHwpCtrl.Insert(parent.getDownloadPath() + "clipboard.hwp");
						}
					}
				}	// draft sign slot or final sign slot exist end
			}	// no title field - memo end
		}	// bBodyExist false end
	}	// bAdjust end
/*
	if (strFilePath == "")
	{
		var strTitle = "Body";
		strFilePath = parent.getDownloadPath() + strTitle + ".hwp";
	}
*/

	return objHwpCtrl.SaveAs(strFilePath, "HWP", "lock:FALSE");
}

function createApprovalHWPML(strFieldList, bDate, strHmlPath)
{
	var objXMLDom = new ActiveXObject("MSXML.DOMDocument");
	objXMLDom.async = false;
	objXMLDom.load(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/HmlHead.xml");
	var strHml = objXMLDom.documentElement.text;

	var arrFieldList = strFieldList.split("^");
	var nApprovalCount = arrFieldList.length;
	if (nApprovalCount > 0)
	{
		var nCellWidth = 4300, nCellHeight1 = 1700, nCellHeight2 = 5400;
		if (nApprovalCount > 10)
			nCellWidth = 43000 / nApprovalCount;
		
		strHml += "<TABLE PageBreak=\"Cell\" RepeatHeader=\"true\" RowCount=\"2\" ColCount=\""
				+ nApprovalCount
				+ "\" CellSpacing=\"0\" borderFill=\"1\">\r\n"
				+ "<SHAPEOBJECT ZOrder=\"4\" NumberingType=\"Table\" TextWrap=\"TopAndBottom\" Lock=\"false\">\r\n"
				+ "<POSITION TreatAsChar=\"false\" VertRelTo=\"Page\" HorzRelTo=\"Page\" VertAlign=\"Top\" HorzAlign=\"Right\" VertOffset=\"0\" HorzOffset=\"0\" FlowWithText=\"true\" AllowOverlap=\"false\"/>\r\n"
				+ "<SIZE Width=\""
				+ nCellWidth * nApprovalCount
				+ "\" Height=\""
				+ (nCellHeight1 + nCellHeight2)
				+ "\" WidthRelTo=\"Absolute\" HeightRelTo=\"Absolute\" Protect=\"false\"/>"
				+ "<OUTSIDEMARGIN Left=\"283\" Right=\"283\" Top=\"283\" Bottom=\"283\"/>\r\n"
				+ "</SHAPEOBJECT>\r\n"
				+ "<INSIDEMARGIN Left=\"141\" Right=\"141\" Top=\"141\" Bottom=\"141\"/>\r\n"
				+ "<ROW>\r\n";

		var nIndex;
		for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
		{
			strHml += "<CELL Name=\"*"
					+ arrFieldList[nIndex]
					+ "\" ColAddr=\""
					+ nIndex
					+ "\" RowAddr=\"0\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
					+ nCellWidth
					+ "\" Height=\""
					+ nCellHeight1
					+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
					+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
					+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
					+ "</PARALIST>\r\n"
					+ "</CELL>\r\n";
		}
		strHml += "</ROW>\r\n<ROW>\r\n";
// CharShape 0: 10pt, 2: 8pt
		for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
		{
			strHml += "<CELL Name=\""
					+ arrFieldList[nIndex]
					+ "\" ColAddr=\""
					+ nIndex
					+ "\" RowAddr=\"1\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
					+ nCellWidth
					+ "\" Height=\""
					+ nCellHeight2
					+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
					+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
					+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
					+ "</PARALIST>\r\n"
					+ "</CELL>\r\n";
		}
		strHml += "</ROW>\r\n</TABLE>\r\n";

		if (bDate)
		{
			strHml += "<TABLE PageBreak=\"Cell\" RepeatHeader=\"true\" RowCount=\"1\" ColCount=\""
					+ nApprovalCount
					+ "\" CellSpacing=\"0\" borderFill=\"1\">\r\n"
					+ "<SHAPEOBJECT Id=\"1982440985\" ZOrder=\"4\" NumberingType=\"Table\" TextWrap=\"BehindText\" Lock=\"false\">\r\n"
					+ "<POSITION TreatAsChar=\"false\" VertRelTo=\"Page\" HorzRelTo=\"Page\" VertAlign=\"Top\" HorzAlign=\"Right\" VertOffset=\""
					+ (nCellHeight1 + nCellHeight2)
					+ "\" HorzOffset=\"0\" FlowWithText=\"true\" AllowOverlap=\"false\"/>\r\n"
					+ "<SIZE Width=\""
					+ nCellWidth * nApprovalCount
					+ "\" Height=\""
					+ nCellHeight2
					+ "\" WidthRelTo=\"Absolute\" HeightRelTo=\"Absolute\" Protect=\"true\"/>"
					+ "<OUTSIDEMARGIN Left=\"283\" Right=\"283\" Top=\"283\" Bottom=\"283\"/>\r\n"
					+ "</SHAPEOBJECT>\r\n"
					+ "<INSIDEMARGIN Left=\"141\" Right=\"141\" Top=\"141\" Bottom=\"141\"/>\r\n"
					+ "<ROW>\r\n";

			for (nIndex = 0; nIndex < nApprovalCount; nIndex++)
			{
				strHml += "<CELL Name=\"@"
						+ arrFieldList[nIndex]
						+ "\" ColAddr=\""
						+ nIndex
						+ "\" RowAddr=\"0\" ColSpan=\"1\" RowSpan=\"1\" Width=\""
						+ nCellWidth
						+ "\" Height=\""
						+ nCellHeight1
						+ "\" Header=\"false\" HasMargin=\"false\" Protect=\"false\" Editable=\"false\" Dirty=\"false\" BorderFill=\"1\">\r\n"
						+ "<PARALIST Count=\"1\" TextDirection=\"0\" LineWrap=\"0\" VertAlign=\"Center\">\r\n"
						+ "<P ParaShape=\"3\"><TEXT CharShape=\"0\"/></P>\r\n"
						+ "</PARALIST>\r\n"
						+ "</CELL>\r\n";
			}
			strHml += "</ROW>\r\n</TABLE>\r\n";
		}
	}
	
	strHml += "<CHAR></CHAR>\n</TEXT>\n</P>\n</SECTION>\n</BODY>\n</HWPML>";
	parent.HttpTransfer1.SaveToLocalFile(strHmlPath, strHml);
}

function makeApprovalFields(objHwpCtrl, strFieldNames)
{
//	if (g_strApprovalFieldNames == strFieldNames)
//		return;

	var strHmlPath = parent.getDownloadPath() + "fieldtable.hml";
	createApprovalHWPML(strFieldNames, false, strHmlPath);

	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;

	var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
	var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
	if (bDraftSign || bFinalSign)
	{
		if (bDraftSign)
			objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField(FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");		
	var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");		
	if (bDraftDate || bFinalDate)
	{
		if (bDraftDate)
			objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	objHwpCtrl.MovePos(2);	// moveTopOfFile

	objHwpCtrl.Insert(strHmlPath, "HML/KS");

	objHwpCtrl.EditMode = nEditMode;

	g_strApprovalFieldNames = strFieldNames;
}

function removeApprovalFields(objHwpCtrl)
{
	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;

	var bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
	var bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
	if (bDraftSign || bFinalSign)
	{
		if (bDraftSign)
			objHwpCtrl.MoveToField(FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField(FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	var bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");		
	var bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
	if (bDraftDate || bFinalDate)
	{
		if (bDraftDate)
			objHwpCtrl.MoveToField("@" + FIELD_DRAFTER + "1");
		else
			objHwpCtrl.MoveToField("@" + FIELD_LAST + "1");
		objHwpCtrl.DeleteCtrl(objHwpCtrl.ParentCtrl);
	}

	objHwpCtrl.EditMode = nEditMode;
}

function copyProposal(objHwpCtrl, nProposal)
{
	if (nProposal < 1)
		return;

	var nStartPara, nStartPos, nEndPara, nEndPos;
	var strField = FIELD_BODY + "{{" + (nProposal - 1) + "}}";
	if (objHwpCtrl.FieldExist(strField))
	{
		objHwpCtrl.MoveToField(strField, true, true, true);
	}
	else if (objHwpCtrl.FieldExist("pubdoc/content/body"))
	{
		objHwpCtrl.MoveToField("pubdoc/content/body", true, true, true);
	}
	else
	{
		if (objHwpCtrl.FieldExist(FIELD_TITLE))
		{
			if (nProposal == 1)
				objHwpCtrl.MoveToField(FIELD_TITLE);
			else
				objHwpCtrl.MoveToField(FIELD_TITLE + nProposal);					
			objHwpCtrl.MovePos(26);
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nStartPara = diSet.Item("CurPara");
			nStartPos = diSet.Item("CurPos") + 8;
			
			strField = FIELD_SENDER_AS + "#" + nProposal;
			if (objHwpCtrl.FieldExist(strField))
			{
				objHwpCtrl.MoveToField(strField);
				objHwpCtrl.MovePos(26);
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				nEndPara = diSet.Item("CurPara");
				nEndPos = diSet.Item("CurPos");
			}
			else
			{
				strField = FIELD_TITLE + (nProposal + 1);
				if (objHwpCtrl.FieldExist(strField))
				{
					objHwpCtrl.MoveToField(strField);
					objHwpCtrl.MovePos(26);
				}
				else
				{
					objHwpCtrl.MovePos(3);
				}		
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				nEndPara = diSet.Item("CurPara");
				nEndPos = diSet.Item("CurPos");
			}
		}
		else
		{
			var bDraftSign, bFinalSign, bDraftDate, bFinalDate;
			bDraftSign = objHwpCtrl.FieldExist(FIELD_DRAFTER + "1");
			bFinalSign = objHwpCtrl.FieldExist(FIELD_LAST + "1");
			bDraftDate = objHwpCtrl.FieldExist("@" + FIELD_DRAFTER + "1");
			bFinalDate = objHwpCtrl.FieldExist("@" + FIELD_LAST + "1");
			
			nStartPara = 0;
			nStartPos = 16;
			if (bDraftSign || bFinalSign)
			{
				if (bDraftDate || bFinalDate)
					nStartPos += 16;
				else
					nStartPos += 8;
			}

			objHwpCtrl.MovePos(3);

			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nEndPara = diSet.Item("CurPara");
			nEndPos = diSet.Item("CurPos");
		}
		
		objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
	}	

	// copy or save ...
	var saveBlockAction = objHwpCtrl.CreateAction("ASaveBlockAction");
	var saveBlockSet = saveBlockAction.CreateSet();
	saveBlockSet.SetItem("FileName", parent.getDownloadPath() + "clipboard.hwp");
	saveBlockSet.SetItem("Format", "HWP");
	saveBlockAction.Execute(saveBlockSet);

	objHwpCtrl.MovePos(0, nEndPara, nEndPos);
}

function pasteProposal(objHwpCtrl)
{
	insertAux(objHwpCtrl, parent.getDownloadPath() + "clipboard.hwp", 1);
}
	
// nPos: 0 = before body, 1 = replace body, 2 = after body
function insertAux(objHwpCtrl, strFilePath, nPos)
{
	var b2002 = false;
	var nEditMode = objHwpCtrl.EditMode;
	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0x01;
	var bSenderAsExist = objHwpCtrl.FieldExist(FIELD_SENDER_AS + "#1");
	if (bSenderAsExist)
	{
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		objHwpCtrl.MovePos(24);
		var parentCtrl = objHwpCtrl.ParentCtrl;
		if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
			b2002 = true;
	}
	var bBodyExist = objHwpCtrl.FieldExist(FIELD_BODY);
	if (bBodyExist)
	{
		if (nPos == 0)
			objHwpCtrl.MoveToField(FIELD_BODY);
		else if (nPos == 1)
			objHwpCtrl.MoveToField(FIELD_BODY, true, true, true);
		else if (nPos == 2)
			objHwpCtrl.MoveToField(FIELD_BODY, true, false);
	}
	else
	{
		var nStartPara, nStartPos, nEndPara, nEndPos;
		if (nPos == 0 || nPos == 1)
		{
			objHwpCtrl.MoveToField(FIELD_TITLE);
			objHwpCtrl.MovePos(26);

			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nStartPara = diSet.Item("CurPara");
			nStartPos = diSet.Item("CurPos") + 8;
		}
		
		if (nPos == 1 || nPos == 2)
		{
			var strField = FIELD_SENDER_AS + "#1";
			if (bSenderAsExist)
			{
				objHwpCtrl.MoveToField(strField);
				objHwpCtrl.MovePos(26);
			}
			else
			{
				objHwpCtrl.MovePos(3);
			}
			
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);
			nEndPara = diSet.Item("CurPara");
			nEndPos = diSet.Item("CurPos") + 8;
		}
		
		if (nPos == 0)
		{
			objHwpCtrl.MovePos(0, nStartPara, nStartPos);
		}
		else if (nPos == 1)
		{
			objHwpCtrl.SelectText(nStartPara, nStartPos, nEndPara, nEndPos);
			objHwpCtrl.Run("Delete");
		}
		else if (nPos == 2)
		{
			objHwpCtrl.MovePos(0, nEndPara, nEndPos);
		}
	}
	
	objHwpCtrl.Insert(strFilePath);
/*
	// remove whitespace before bottom table
	if (!bBodyExist && parent.getBodyCount() == 1 && bSenderAsExist)
	{
		var nBottomPara, nBottomPos, nCurPara, nCurPos, nPrevPara, nPrevPos;
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		if (b2002)
			objHwpCtrl.MovePos(24);
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
		var tableSet = tableAction.CreateSet();
		tableAction.GetDefault(tableSet);
		if (tableSet.Item("TreatAsChar") == 0 && (tableSet.Item("VertRelTo") == 0 || tableSet.Item("VertRelTo") == 1))
		{
			objHwpCtrl.MovePos(26);
			var diAction = objHwpCtrl.CreateAction("DocumentInfo");
			var diSet = diAction.CreateSet();
			diAction.Execute(diSet);

			nCurPara = diSet.Item("CurPara");
			nCurPos = diSet.Item("CurPos");

			nBottomPara = nCurPara;
			nBottomPos = nCurPos;
			
			do {
				objHwpCtrl.MovePos(15);
				
				var diAction = objHwpCtrl.CreateAction("DocumentInfo");
				var diSet = diAction.CreateSet();
				diAction.Execute(diSet);
				
				nPrevPara = diSet.Item("CurPara");
				nPrevPos = diSet.Item("CurPos");
				
				if (nCurPara == (nPrevPara + 1) && nCurPos == 0)
				{
					// carriage return;
				}
				else if (nCurPara == nPrevPara && nCurPos == (nPrevPos + 1))
				{
					var textSet = objHwpCtrl.CreateSet("GetText");
					objHwpCtrl.InitScan(0, 0);
					objHwpCtrl.GetTextBySet(textSet);
					objHwpCtrl.ReleaseScan();
					var strText = textSet.Item("Text");

					if (strText != "")
					{
						var nCharCode = strText.charCodeAt(0);
//						if (nCharCode == 0x0010 || nCharCode == 0x0013 || nCharCode == 0x0020 || nCharCode == 0x0009)
						if (nCharCode > 0x0020)
							break;
					}
				}
				nCurPara = nPrevPara;
				nCurPos = nPrevPos;
			} while (nCurPara > 0);

			// delete
			if (nCurPara != nBottomPara || nCurPos != nBottomPos)
			{
				objHwpCtrl.SelectText(nCurPara, nCurPos, nBottomPara, nBottomPos);
				objHwpCtrl.Run("Delete");
			}
		}
	}	// remove whitespace before bottom table end
*/

	if (bSenderAsExist)
	{
//					objHwpCtrl.MovePos(3);
		objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
		diAction = objHwpCtrl.CreateAction("DocumentInfo");
		diSet = diAction.CreateSet();
		diSet.SetItem("DetailInfo", 1);
		diAction.Execute(diSet);

		if (diSet.Item("DetailCurPage") > 0)
		{
			objHwpCtrl.MoveToField(FIELD_SENDER_AS + "#1");
			if (b2002)
				objHwpCtrl.MovePos(24);
			tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
			tableSet = tableAction.CreateSet();
			tableAction.GetDefault(tableSet);
			tableSet.SetItem("TreatAsChar", 1);
			tableAction.Execute(tableSet);
		}
	}

	if (nEditMode == 0)
		objHwpCtrl.EditMode = 0;
}			

function putStampFromFile(objHwpCtrl, strFieldName, strFilePath, bReplace)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;

	objHwpCtrl.MoveToField(strFieldName, true, true, false);

//	objHwpCtrl.InsertBackgroundPicture("SelectedCell", strFilePath);

/*
InsertPicture(BSTR path, [boolean embeded], [short sizeoption], [boolean reverse], [boolean watermark], [short effect], [long width], [long height])
sizeoption :
	// 이미지 원래의 크기로 삽입한다. width와 height를 지정할 필요없다.
	0,
	// width와 height에 지정한 크기로 그림을 삽입한다.
	1,
	// 현재 캐럿이 표의 셀안에 있을 경우, 셀의 크기에 맞게 자동 조절하여 삽입한다.
	// width는 셀의 width만큼, height는 셀의 height만큼 확대/축소된다.
	// 캐럿이 셀안에 있지 않으면 이미지의 원래 크기대로 삽입된다.
	cellSize = 2,
	// 현재 캐럿이 표의 셀안에 있을 경우, 셀의 크기에 맞추어 원본 이미지의 가로 세로의 비율이 동일하게 확대/축소하여 삽입한다. 
	cellSizeWithSameRatio = 3

*/

	objHwpCtrl.Run("SelectAll");
	objHwpCtrl.Run("Delete");
	objHwpCtrl.InsertPicture(strFilePath, true, 3);
}

function putStampFromFileWithSize(objHwpCtrl, strFieldName, strFilePath, bReplace, nImageWidth, nImageHeight)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;

	objHwpCtrl.MoveToField(strFieldName, true, true, false);

//	objHwpCtrl.InsertBackgroundPicture("SelectedCell", strFilePath);

	objHwpCtrl.Run("SelectAll");
	objHwpCtrl.Run("Delete");
	objHwpCtrl.InsertPicture(strFilePath, true, 1, false, false, 0, nImageWidth, nImageHeight);
}


function insertSealHwp(objHwpCtrl, strSealName)
{
	objHwpCtrl.MovePos(3);
	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/stamp.hwp");
	objHwpCtrl.RenameField(FIELD_STAMP, strSealName);
}

function insertOmitSealHwp(objHwpCtrl, strSealName)
{
	objHwpCtrl.MovePos(3);
	objHwpCtrl.Insert(g_strBaseUrl + "htdocs/js/approve/document/resource/hwp2002/omitstamp.hwp");
	objHwpCtrl.RenameField(FIELD_OMIT_STAMP, strSealName);				
}

function adjustSealPos(objHwpCtrl, strSealName, strTextName, nFontSize) 
{
	var b2002 = false;
	objHwpCtrl.MoveToField(strTextName);
	objHwpCtrl.MovePos(24);
	var parentCtrl = objHwpCtrl.ParentCtrl;
	if (parentCtrl != null && parentCtrl.CtrlID == "tbl")
		b2002 = true;
	
	if (!b2002)
		return;

	objHwpCtrl.MoveToField(strTextName);
	var strText = objHwpCtrl.GetFieldText(strTextName);
	strText.replace(/[ \t]*$/, "");
	var nTextSize = 0;
	for (var i = 0; i < strText.length; i++)
	{
		if ((strText.charCodeAt(i) & 0xff00) != 0)
			nTextSize += 2;
		else
			nTextSize += 1;
	}
	var nCharOffset = nTextSize / 2 - 1;
	var nFontWidth = nFontSize * 50;
	var nHorizontalAdjust = nCharOffset * nFontWidth;

	var nVerticalAdjust = (8500 - nFontWidth) / 2 - 840;

	objHwpCtrl.MoveToField(strSealName);
	var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
	var tableSet = tableAction.CreateSet();
	tableAction.GetDefault(tableSet);

	// horizontal align relative to enclosing table
	if (tableSet.Item("HorzRelTo") != 3)
		tableSet.SetItem("HorzRelTo", 3);
	// center alignment
	tableSet.SetItem("HorzAlign", 1);
	tableSet.SetItem("HorzOffset", nHorizontalAdjust);
	tableAction.Execute(tableSet);
}

function onClickForSealOnHwp(x, y)
{
	var objHwpCtrl = g_objTargetHwpCtrl;

	var mousePosSet = objHwpCtrl.GetMousePos(0, 0);
	var xrelto = mousePosSet.Item("XRelto");
	var yrelto = mousePosSet.Item("YRelTo");
	var page = mousePosSet.Item("Page");
	var pagex = mousePosSet.Item("X");
	var pagey = mousePosSet.Item("Y");
	mousePosSet = objHwpCtrl.GetMousePos(1, 1);
	var paperx = mousePosSet.Item("X");
	var papery = mousePosSet.Item("Y");

	if (g_bSeal)
	{
		insertSealHwp(objHwpCtrl, g_strSealName);
		paperx -= 29900;
		papery -= 9200;
	}
	else
	{
		insertOmitSealHwp(objHwpCtrl, g_strSealName);
		paperx -= 29900;
		papery -= 3100;
	}

	objHwpCtrl.MoveToField(g_strSealName);
	var tableCtrl = objHwpCtrl.ParentCtrl;
	if (tableCtrl != null && tableCtrl.CtrlID == "tbl")
	{
		var tableAction = objHwpCtrl.CreateAction("TablePropertyDialog");
		var tableSet = tableAction.CreateSet();
		tableAction.GetDefault(tableSet);
		tableSet.SetItem("VertRelTo", 1); // 0 : Paper, 1: Page
		tableSet.SetItem("HorzRelTo", 1);
		tableSet.SetItem("HorzOffset", pagex);
		tableSet.SetItem("VertOffset", pagey);
		tableCtrl.Properties = tableSet;
	}

	if (g_bWithSize)
		putStampFromFileWithSize(objHwpCtrl, g_strSealName, g_strSealPath, true, g_nImageWidth, g_nImageHeight);
	else
		putStampFromFile(objHwpCtrl, g_strSealName, g_strSealPath, true);

	objHwpCtrl.detachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putSealFromFileEx(objHwpCtrl, strFieldName, strFilePath)
{
	g_bSeal = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_objTargetHwpCtrl = objHwpCtrl;
	
	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putSealFromFileExWithSize(objHwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight)
{
	g_bSeal = true;
	g_bWithSize = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_nImageWidth = nImageWidth;
	g_nImageHeight = nImageHeight
	g_objTargetHwpCtrl = objHwpCtrl;
	
	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putOmitSealFromFileEx(objHwpCtrl, strFieldName, strFilePath)
{
	g_bSeal = false;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;

	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function putOmitSealFromFileExWithSize(objHwpCtrl, strFieldName, strFilePath, nImageWidth, nImageHeight)
{
	g_bSeal = false;
	g_bWithSize = true;
	g_strSealName = strFieldName;
	g_strSealPath = strFilePath;
	g_nImageWidth = nImageWidth;
	g_nImageHeight = nImageHeight;

	objHwpCtrl.attachEvent("OnMouseLButtonDown", onClickForSealOnHwp);
}

function slashCell(objHwpCtrl, strFieldName, bDraw)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;
	objHwpCtrl.MoveToField(strFieldName);
	
	var borderAction = objHwpCtrl.CreateAction("CellBorderFill");
	var borderSet = borderAction.CreateSet();
	borderAction.GetDefault(borderSet);
	borderSet.SetItem("ApplyTo", 0);
	borderSet.SetItem("SlashFlag", (bDraw ? 2 : 0));
	borderSet.SetItem("DiagonalType", 1);
	borderAction.Execute(borderSet);
}

function clearStamp(objHwpCtrl, strFieldName)
{
	if (objHwpCtrl.FieldExist(strFieldName))
	{
		objHwpCtrl.MoveToField(strFieldName, true, true, false);

		objHwpCtrl.Run("SelectAll");
		objHwpCtrl.Run("Delete");
/*
		objHwpCtrl.Run("TableCellBlock");
		objHwpCtrl.InsertBackgroundPicture("SelectedCellDelete", "");
*/
	}
}

function setBorderLine(objHwpCtrl, strFieldName, bDraw)
{
	if (!objHwpCtrl.FieldExist(strFieldName))
		return;
	objHwpCtrl.MoveToField(strFieldName);
	
	var borderAction = objHwpCtrl.CreateAction("CellBorderFill");
	var borderSet = borderAction.CreateSet();
	borderAction.GetDefault(borderSet);
	borderSet.SetItem("ApplyTo", 0);

	var nBorderType = (bDraw ? 1 : 0);

	borderSet.SetItem("BorderTypeLeft", nBorderType);
	borderSet.SetItem("BorderTypeRight", nBorderType);
	borderSet.SetItem("BorderTypeTop", nBorderType);
	borderSet.SetItem("BorderTypeBottom", nBorderType);
/*
	var nBorderWidth = 0;
	borderSet.SetItem("BorderWidthLeft", nBorderWidth);
	borderSet.SetItem("BorderWidthRight", nBorderWidth);
	borderSet.SetItem("BorderWidthTop", nBorderWidth);
	borderSet.SetItem("BorderWidthBottom", nBorderWidth);
*/
	borderAction.Execute(borderSet);
}
