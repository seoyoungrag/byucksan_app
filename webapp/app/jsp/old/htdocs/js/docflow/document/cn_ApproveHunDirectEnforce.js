var g_nEnforceProposal = 1;
var g_strEnforceFormUrl = "";

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

					if (Document_Hun2KLa.IsExistRecord(strFieldName))
					{
						if (getLocalFileSize(strFilePath) > 0)
							Document_Hun2KLa.SetRecordImageFromFile(strFieldName, strFilePath);
					}
				}
			}
		}

		objExtendAttach = getNextExtendAttach(objExtendAttach);
	}
}

function clearEnforceApprovalSeal()
{
}

function setEnforceProposal(nCaseNumber)
{
	g_nEnforceProposal = nCaseNumber;
}

function getEnforceProposal()
{
	return g_nEnforceProposal;
}

function isEnforceModified()
{
	return false;
}