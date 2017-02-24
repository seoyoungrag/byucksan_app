function callBack(strList)
{
	var arrCallBackRow = strList.split("$..$");
	for (var iRow = 0; iRow < arrCallBackRow.length - 1; iRow++)
	{
		var arrItems = arrCallBackRow[iRow].split("$.$");
			
		for (var iItem = 0; iItem < arrItems.length - 1; iItem++)
		{
			var strCaseNumber = arrItems[0];
			var strDeptCode = arrItems[1];
			if (arrItems[2] != null)
				var strRefDeptCode = arrItems[2];
			else
				var strRefDeptCode = "";

			if (g_strOpenLocale == "DOCUMENT")
			{
				var objRecipGroup = opener.getRecipGroup(strCaseNumber);
				if (objRecipGroup != null)
				{
					var objRecip = opener.getFirstRecipient(objRecipGroup);
					while(objRecip)
					{
						if	(opener.getRecipientDeptCode(objRecip) == strDeptCode && opener.getRecipientRefDeptCode(objRecip) == strRefDeptCode)
						{
							opener.setDataAttribute(objRecip, "METHOD", "withdraw");
						}
						objRecip = opener.getNextRecipient(objRecip);
					}
				}
			}
			else
			{
				var objRecipGroup = getRecipGroup(strCaseNumber);
				if (objRecipGroup != null)
				{
					var objRecip = getFirstRecipient(objRecipGroup);
					while(objRecip)
					{
						if	(getRecipientDeptCode(objRecip) == strDeptCode && getRecipientRefDeptCode(objRecip) == strRefDeptCode)
						{
							setDataAttribute(objRecip, "METHOD", "withdraw");
						}
						objRecip = getNextRecipient(objRecip);
					}
				}
			}
		}
	}
}