var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";
var g_bIsEnforceModified = false;
var g_nMenuType = 0;

var g_nCase = 0;
var g_wordApp = null
var g_CaseNumber = 0;

function openEnforceDocument(strFormUrl)
{
//이상한 코드...
	wordObject.Navigate(strFormUrl);
}

function saveEnforceFile(nCaseNumber)
{
	clearEnforceApproveFlow();

	var bRet = false;

	var objEnforceFile = getEnforceFileObj(nCaseNumber);
	var strFileName = getAttachFileName(objEnforceFile);

	var strEnforceDocName = g_DocWord.Name;
	if (strEnforceDocName.indexOf(".doc") == -1)
		strEnforceDocName += ".doc";

	if (strFileName != g_DocWord.Name)
	{
		if (g_DocWord.SaveAs(g_strDownloadPath + strFileName) == true)
		{
			bRet = true;

			var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
			setAttachFileSize(objEnforceFile, strFileSize);
		}
	}

	else
	{
		if (g_DocWord.Save() == true)
		{
			bRet = true;

			var strFileSize = getLocalFileSize(g_strDownloadPath + strFileName);
			setAttachFileSize(objEnforceFile, strFileSize);
		}
	}
	restoreEnforceApproveFlow();

	return bRet;
}

function uploadEnforceFile(nCaseNumber)
{
	var strFileName = getAttachFileName(getEnforceFileObj(nCaseNumber));
	var strLocalPath = g_strDownloadPath + strFileName;
	var strServerUrl = g_strUploadUrl + strFileName;

	if (uploadFile(strLocalPath, strServerUrl) == 0)
		return false;

	return true;
}

function clearEnforceApproveFlow()
{
	clearEnforceApprovalSeal();
}

function clearEnforceApprovalSeal()
{
}

function restoreEnforceApproveFlow()
{
	setEnforceApprovalSeal();
}


function setEnforceApprovalSeal()
{
	var objExtendAttach = getFirstExtendAttach();
	while (objExtendAttach != null)
	{
		var strClassify = getAttachClassify(objExtendAttach);
		if (strClassify == "EnforceRelated")
		{
			var strCaseNumber = getAttachCaseNumber(objExtendAttach);
			if (parseInt(strCaseNumber) == g_nEnforceProposal)
			{
				var strSubDiv = getAttachSubDiv(objExtendAttach);
				var strFieldName = "";

				if (strSubDiv == "DeptStamp")
					strFieldName = FIELD_DEPT_STAMP;
				else if (strSubDiv == "DeptOmitStamp")
					strFieldName = FIELD_DEPT_OMIT_STAMP;
				else if (strSubDiv == "CompanyStamp")
					strFieldName = FIELD_COMPANY_STAMP;
				else if (strSubDiv == "CompanyOmitStamp")
					strFieldName = FIELD_COMPANY_OMIT_STAMP;

				if (strFieldName != "")
				{
					var strFileName = getAttachFileName(objExtendAttach);
					var strFilePath = g_strDownloadPath + strFileName;

					if (g_DocWord.Bookmarks.Exists(strFieldName))
					{
						if (getLocalFileSize(strFilePath) > 0)
							setImageFromFile(strFieldName, strFilePath, g_DocWord);
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function editEnforceDocument(bModify)
{
	if (bModify == true)
	{
		g_nMenuType = 1;
		g_nCase = 3;
	}
	else
	{
		g_nMenuType = 0;
		g_nCase = 2;
	}

	var strFileName = getAttachFileName(getEnforceFileObj(g_nEnforceProposal));

	if (strFileName != "")
	{
		g_bModify = bModify;
		var strLocalPath = g_strDownloadPath + strFileName;

		if (g_nCase == 3)
			g_DocWord.Save();

		openEnforceDocument(strLocalPath);
	}

	return true;
}

function saveEnforceDocument(bIncludeSign)
{
	var strTitle = getTitle(1);
	g_DocWord.Activate();
	g_AppWord.Dialogs(84).Show();
}

function saveEnforceDocumentAs(strFilePath, bIncludeSign)
{
}

function isEnforceModified()
{
	return g_bIsEnforceModified;
}

function setEnforceModified(bEnforceModified)
{
	g_bIsEnforceModified = bEnforceModified;
}

function setEnforceProposal(nCaseNumber)
{
	g_nEnforceProposal = nCaseNumber;
}

function getEnforceProposal()
{
	return g_nEnforceProposal;
}

function setRecordText(strApprovalField, strData, objDocType)
{
	if (objDocType.ProtectionType == 1)
		objDocType.UnProtect();

	if (objDocType.Bookmarks.Exists(strApprovalField))
	{
		var myRange = objDocType.Bookmarks(strApprovalField).Range;
		var strRecordText = readRecordText(strApprovalField,objDocType);

		if (strRecordText != strData)
		{
			var startloc = myRange.Cells(1).Range.Start;
			var endloc = myRange.Cells(1).Range.End;
			myRange.Delete(1,endloc-startloc);
			myRange.Text = strData;
		}
	}
}

function readRecordText(strApprovalField, objDocType)
{
	var startloc = objDocType.Bookmarks(strApprovalField).Range.Cells(1).Range.Start;
	var endloc = objDocType.Bookmarks(strApprovalField).Range.Cells(1).Range.End;
	strRecordText = objDocType.Range(startloc, endloc-1).Text;

	return strRecordText;
}

function setImageFromFile(strApprovalField, strSignUrl, objDocType)
{
	clearImage(strApprovalField);
	objDocType.Bookmarks(strApprovalField).Range.InlineShapes.AddPicture(strSignUrl,false,true);
}

function clearImage(strApprovalField)
{
	if (g_DocWord.ProtectionType == 1)
		g_DocWord.UnProtect();

	g_DocWord.Bookmarks(strApprovalField).Range.Delete(1,1);
}

function drawMenu(nType)
{
	if (g_DocWord.ProtectionType == 1 || g_DocWord.ProtectionType == 2 )
		g_DocWord.UnProtect();

	if (nType == 1)
	{
		var StandardBars = g_DocWord.CommandBars("Standard");
		StandardBars.Position = 1;
		StandardBars.RowIndex = 1;
		StandardBars.Left = 0;
		StandardBars.Controls(2).Enabled = false;
		StandardBars.Controls(3).Enabled = false;
		StandardBars.Controls(5).Enabled = false;
		StandardBars.Controls(6).Enabled = false;
		StandardBars.Visible = true;

		var FormatBars = g_DocWord.CommandBars("Formatting");
		FormatBars.Position = 1;
		FormatBars.RowIndex = 2;
		FormatBars.Left = 0;
		FormatBars.Visible = true;

		var DrawingBars = g_DocWord.CommandBars("Drawing");
		DrawingBars.Visible = true;
	}

	else if (nType == 2)
	{
		if (g_DocWord.ProtectionType == -1)
		{
			if (g_bIsFormData == true)
				g_DocWord.Protect(1);
			else
			{
				if (g_DocWord.Sections.Count != 1)
				{
					g_DocWord.Sections(1).ProtectedForForms = true;
					g_DocWord.Sections(2).ProtectedForForms = true;
					g_DocWord.Protect(2);
				}
			}
		}
	}
}