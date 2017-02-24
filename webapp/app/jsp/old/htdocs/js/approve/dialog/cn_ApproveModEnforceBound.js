function getApproveDataByProposal(nProposal)
{
	var strTitle = opener.getTitle(nProposal);										// TITLE
	var objTitle = document.getElementById("idDocTitle");
	objTitle.innerText = strTitle;

	var objTable = document.getElementById("PROPOSALTBL" + nProposal);
	if (objTable == null)
		return;

	var objTDs = objTable.getElementsByTagName("TD");
	if (objTDs == null)
		return;

	var strDocCategory = objTDs[objTDs.length-1].getAttribute("doccategory");		// DOC_CATEGORY
	setValueByName(strDocCategory, "rdDocCategory");

	var strEnforceBound = objTDs[objTDs.length-1].getAttribute("enforcebound");		// ENFORCE_BOUND
	setValueByName(strEnforceBound, "rdEnforceBound");
}

function setApproveDataByProposal(nProposal)
{
	var old_strDocCategory = opener.getDocCategory(nProposal)							// DOC_CATEGORY
	var old_strEnforceBound = opener.getEnforceBound(nProposal);						// ENFORCE_BOUND

	var objTable = document.getElementById("PROPOSALTBL" + nProposal);
	if (objTable == null)
		return;

	var objTDs = objTable.getElementsByTagName("TD");
	if (objTDs == null)
		return;

	var strDocCategory = objTDs[objTDs.length-1].getAttribute("doccategory");
	var strEnforceBound = objTDs[objTDs.length-1].getAttribute("enforcebound");

	opener.setDocCategory(strDocCategory, nProposal)								// DOC_CATEGORY 
	opener.setEnforceBound(strEnforceBound, nProposal);								// ENFORCE_BOUND

	if ((strDocCategory != old_strDocCategory) || (strEnforceBound != old_strEnforceBound))
		opener.removeRecipGroup(nProposal);
}

function onClickDocCategory(strCategoryType)
{
	var objEnforceBound = document.getElementsByName("rdEnforceBound");
	if (strCategoryType == "I")
	{
		for (var i = 0; i < objEnforceBound.length; i++)
		{
			objEnforceBound[i].checked = false;
			objEnforceBound[i].disabled = true;
		}
	}
	else
	{
		for (var i = 0; i < objEnforceBound.length; i++)
		{
			objEnforceBound[0].checked = true;
			objEnforceBound[i].disabled = false;
		}
	}
}
