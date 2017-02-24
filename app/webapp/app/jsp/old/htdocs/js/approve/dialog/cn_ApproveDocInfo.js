function setApproveData()
{
	var strDocNumber = getValueById("idDocNo");

	var strNumber = "";
	var strClassName = "";
	var strSerialNumber = "";
	var nNumberType = 0;

	var nLength = strDocNumber.length;
	var i = 0;
	while (i < nLength)
	{
		var ch = strDocNumber.charAt(i);
		if (ch == "-")
		{
			nNumberType = 2;
		}
		else if (ch >= "0" && ch <= "9")
		{
			if (nNumberType == 0)
				nNumberType = 1;
		}
		else
		{
			if (nNumberType == 1)
			{
				strClassName += strNumber;
				strNumber = "";
				nNumberType = 0;
			}
		}

		if (ch != "-")
		{
			if (nNumberType == 0)
				strClassName += ch.toString();
			else if (nNumberType == 1)
				strNumber += ch.toString();
			else if (nNumberType == 2)
				strSerialNumber += ch.toString();
		}

		i++;
	}

	if (strClassName.length > 16)
		strClassName = strClassName.substring(0, 16);

	if (strNumber.length > 32)
		strNumber = strNumber.substring(0, 32);

	var strDraftCons = getValueById("idDraftCons");
	var strEnforceCons = getValueById("idEnforceCons");
	var strUrgency = getValueByName("rdUrgency");
	var strSecurityPass = getValueById("idSecurityPass");

	opener.setOrgSymbol(strClassName);						// ORG_SYMBOL
	opener.setClassNumber(strNumber);						// CLASS_NUMBER
	opener.setSerialNumber(strSerialNumber);				// SERIAL_NUMBER
	opener.setUrgency(strUrgency);							// URGENCY
	opener.setDraftConserve(strDraftCons);					// DRAFT_CONSERVE
	opener.setEnforceConserve(strEnforceCons);				// ENFORCE_CONSERVE
	opener.setSecurityPass(strSecurityPass);				// SECURITY_PASS

	if (g_strOpt148 == "0")
	{
		var strAccessLevel = getValueByName("rdAccessLevel");
		var strPublicLevel = getValueByName("rdPublicLevel");
		opener.setAccessLevel(strAccessLevel);				// ACCESS_LEVEL
		opener.setPublicLevel(strPublicLevel);				// PUBLIC_LEVEL
	}

	opener.setClassNumberID(g_strClassNumber);

	if (nAnnounceStatus == 1)
	{
		var strAnnounceType = getValueByName("rdAnnounceType");
		opener.setAnnouncementStatus(strAnnounceType);
	}
//	if (strSecurityPass != "")
//		opener.removeRelatedSystemBySystemID("SDSDM");
}

function setApproveDataByProposal()
{
	var strSenderAsToForm = "";
	var objSelect = document.getElementById("idProposalSel");
	for (var iTBLLoop = 1; iTBLLoop <= objSelect.length; iTBLLoop++)
	{
		var strProposalID = "PROPOSALTBL" + iTBLLoop;
		var objProposalTBL = document.getElementById(strProposalID);
		if (objProposalTBL == null)
			return;

		var objTDs = objProposalTBL.getElementsByTagName("TD");
		if (objTDs == null)
			return;

		var old_strDocCategory = opener.getDocCategory(iTBLLoop);	// DOC_CATEGORY
		var old_strEnforceBound = opener.getEnforceBound(iTBLLoop);	// ENFORCE_BOUND

		var strTitle = objTDs[objTDs.length-1].getAttribute("title").replace(/&apos;/g, "'");
		var strRegistryType = getValueByName("rdRegistryType");
		var strDocCategory = objTDs[objTDs.length-1].getAttribute("doccategory");
		var strEnforceBound = objTDs[objTDs.length-1].getAttribute("enforcebound");
		if ((strDocCategory == "I") && (strRegistryType == "T"))
			strRegistryType = "Y";

		opener.setTitle(strTitle, iTBLLoop);						// TITLE
		opener.setRegistryType(strRegistryType);					// REGISTRY_TYPE
		opener.setDocCategory(strDocCategory, iTBLLoop)				// DOC_CATEGORY
		opener.setEnforceBound(strEnforceBound, iTBLLoop);			// ENFORCE_BOUND

		if (nUseTreatment == 1)
		{
			if (strDocCategory == "E")
			{
				var strTreatment = objTDs[objTDs.length-1].getAttribute("treatment");
				opener.setTreatment(strTreatment, iTBLLoop);		// TREATMENT
			}
			else
			{
				opener.setTreatment("", iTBLLoop);					// TREATMENT
			}

		}
		if (nSelSenderAs == 1)
		{
			var strSenderAs = objTDs[objTDs.length-1].getAttribute("senderas");
			var strSenderAsCode = objTDs[objTDs.length-1].getAttribute("senderascode");
			var strSenderAsName = objTDs[objTDs.length-1].getAttribute("senderasname");
			opener.setSenderAs(strSenderAs, iTBLLoop);				// SENDERAS
			opener.setSenderAsCode(strSenderAsCode, iTBLLoop);
			if (iTBLLoop == 1)
				strSenderAsToForm += strSenderAs;
			else
				strSenderAsToForm += "\u0002" + strSenderAs;
//			opener.setSenderAsToForm(strSenderAs);
//			opener.setOrganToForm(strSenderAsName);	//for 한양대
		}

		if ((strDocCategory != old_strDocCategory) || (strEnforceBound != old_strEnforceBound))
			opener.removeRecipGroup(iTBLLoop);
	}
	if (nSelSenderAs == 1)
	{
//alert(strSenderAsToForm);
		opener.setSenderAsToForm(strSenderAsToForm);
	}
}

function getApproveData()
{
	var strClassName = opener.getOrgSymbol();						// ORG_SYMBOL
	if (strClassName == "")
		strClassName = g_strClassName;
	var strNumber = opener.getClassNumber();						// CLASS_NUMBER
	var strSerialNumber = opener.getSerialNumber();					// SERIAL_NUMBER
	var strDocNo = strClassName + strNumber;
	if ((strSerialNumber != "") && (parseInt(strSerialNumber) != 0))
		strDocNo = strDocNo + "-" + strNumber;
	setValueById(strDocNo, "idDocNo");

	var strUrgency = opener.getUrgency();							// URGENCY
	setValueByName(strUrgency, "rdUrgency");

	var strDraftCons = opener.getDraftConserve();					// DRAFT_CONSERVE
	var strEnforceCons = opener.getEnforceConserve();				// ENFORCE_CONSERVE
	setValueById(strDraftCons, "idDraftCons");
	setValueById(strEnforceCons, "idEnforceCons");

	var strSecurityPass = opener.getSecurityPass();					// SECURITY_PASS
	if (strSecurityPass != "")
	{
		document.getElementById("idSecurity").checked = true;
		document.getElementById("idSecurityPass").disabled = false;
		document.getElementById("idSecurityPass").style.background = "#ffffff";
		setValueById(strSecurityPass, "idSecurityPass");
	}

	if (g_strOpt148 == "0")
	{
		var strAccessLevel = opener.getAccessLevel();				// ACCESS_LEVEL
		setValueByName(strAccessLevel, "rdAccessLevel");
		var strPublicLevel = opener.getPublicLevel();				// PUBLIC_LEVEL
		setValueByName(strPublicLevel, "rdPublicLevel");
	}

	if (nAnnounceStatus == 1)
	{
		var strAnnounceType = opener.getAnnouncementStatus();
		setValueByName(strAnnounceType, "rdAnnounceType");
	}
}

function getApproveDataByProposal()
{
	var strProposalID = "PROPOSALTBL" + g_nLoadedProposal;
	var objProposalTBL = document.getElementById(strProposalID);
	if (objProposalTBL == null)
		return;

	var objTDs = objProposalTBL.getElementsByTagName("TD");
	if (objTDs == null)
		return;

	strTitle = objTDs[objTDs.length-1].getAttribute("title").replace(/&apos;/g, "'");
	setValueById(strTitle, "idDocTitle");
	strDocCategory = objTDs[objTDs.length-1].getAttribute("doccategory");
	setValueByName(strDocCategory, "rdDocCategory");

	strEnforceBound = objTDs[objTDs.length-1].getAttribute("enforcebound");
	setValueByName(strEnforceBound, "rdEnforceBound");

	setDocCategory(strDocCategory, strEnforceBound);

	if (nUseTreatment == 1)
	{
		if (strDocCategory == "E")
		{
			enTreatment();
			var strTreatment = objTDs[objTDs.length-1].getAttribute("treatment");
			setValueById(strTreatment, "idTreatment");
		}
		else
		{
			disTreatment();
			setValueById("", "idTreatment");
		}
	}
	if (nSelSenderAs == 1)
	{
		var strSenderAsCode = objTDs[objTDs.length-1].getAttribute("senderascode");
		var objSelSenderAs = document.getElementById("selSenderAs");
		for (var i = 0; i < objSelSenderAs.length; i++)
		{
			var strSelSenderAsCode = objSelSenderAs.options[i].value;
			if (strSelSenderAsCode == strSenderAsCode)
			{
				objSelSenderAs.options[i].selected = true;
				break;
			}
		}
	}
}

function setDocCategory(strDocCategory, strEnforceBound)
{
	onClickDocCategory(strDocCategory);
	if (strDocCategory != "I")
		setValueByName(strEnforceBound, "rdEnforceBound");
	else
	{
		var strRegistryType = opener.getRegistryType();
		setValueByName(strRegistryType, "rdRegistryType");
	}
}

function onClickSecurity()
{
	if (document.getElementById("idSecurity").checked)
	{
		document.getElementById("idSecurityPass").disabled = false;
		document.getElementById("idSecurityPass").style.background = "#ffffff";
		document.getElementById("idSecurityPass").focus();
	}
	else
	{
		document.getElementById("idSecurityPass").disabled = true;
		document.getElementById("idSecurityPass").style.background = "#dfe2e8";
		document.getElementById("idSecurityPass").value = "";
	}
}

function drawConv()
{
	var strTerm = getConvData();
	var arrTerm = strTerm.split("^");

	var objDraftCons = document.getElementById("cboDraftCons");
	var objEnforceCons = document.getElementById("cboEnforceCons");
	objDraftCons.length = arrTerm.length-1;
	objEnforceCons.length = arrTerm.length-1;
	for (var i = 0; i < objDraftCons.length; i++)
	{
		objDraftCons.options[i].text = arrTerm[i];
		objDraftCons.options[i].value = arrTerm[i];
		objEnforceCons.options[i].text = arrTerm[i];
		objEnforceCons.options[i].value = arrTerm[i];
	}
}

function onChangeConserve()
{
	var objEnforceCons = document.getElementById("cboEnforceCons");
	objEnforceCons.value = getValueById("cboDraftCons");
}

function setConserve(strRetentionDate)
{
	setValueById(strRetentionDate, "idDraftCons");
	setValueById(strRetentionDate, "idEnforceCons");
}

function onClickDocCategory(strCategoryType)
{
	var objRegistryType = document.getElementsByName("rdRegistryType");
	var objEnforceBound = document.getElementsByName("rdEnforceBound");
	if (strCategoryType == "I")
	{
		for (var i = 0; i < objEnforceBound.length; i++)
		{
			objEnforceBound[i].checked = false;
			objEnforceBound[i].disabled = true;
		}
		objRegistryType[1].disabled = false;
	}
	else
	{
		for (var i = 0; i < objEnforceBound.length; i++)
		{
			if (i == 0)
				objEnforceBound[i].checked = true;
			else
				objEnforceBound[i].checked = false;

			objEnforceBound[i].disabled = false;
		}
		objRegistryType[1].checked = false;
		objRegistryType[1].disabled = true;
		objRegistryType[0].checked = true;
	}

	if (nUseTreatment == 1)
	{
		if (strCategoryType == "E")
		{
			enTreatment();
			var objTreatment = document.getElementById("cboTreatment");
			objTreatment.options[0].selected = true;
		}
		else
		{
			disTreatment();
			setValueById("", "idTreatment");
		}

	}
	if (nSelSenderAs == 1)
	{
		var strSenderAsCode = "";
		var objSelSenderAs = document.getElementById("selSenderAs");
		if (strCategoryType == "E")
		{
			objSelSenderAs.disabled = false;
			var strProposalID = "PROPOSALTBL" + g_nLoadedProposal;
			var objProposalTBL = document.getElementById(strProposalID);
			if (objProposalTBL == null)
				return;

			var objTDs = objProposalTBL.getElementsByTagName("TD");
			if (objTDs == null)
				return;

			strSenderAsCode = objTDs[objTDs.length-1].getAttribute("senderascode");
		}
		else
		{
			objSelSenderAs.disabled = true;
		}
		for (var i = 0; i < objSelSenderAs.length; i++)
		{
			var strSelSenderAsCode = objSelSenderAs.options[i].value;
			if (strSelSenderAsCode == strSenderAsCode)
			{
				objSelSenderAs.options[i].selected = true;
				break;
			}
		}
	}
}

function getClassInfo()
{
	return getValueById("idDocNo");
}

function setClassInfo(strDocNumber)
{
	setValueById(strDocNumber, "idDocNo");

	if (strDocNumber != "")
	{
		var strRegistryType = getValueByName("rdRegistryType");
		if (strRegistryType == "N")
		{
			strRegistryType = "T";
			setValueByName(strRegistryType, "rdRegistryType");
		}
	}
}

function drawTreatment()
{
	var strTreatment = "정보통신망^모사전송^전신^전화^우편^인편^";
	var arrTreatment = strTreatment.split("^");
	var objTreatment = document.getElementById("cboTreatment");
	objTreatment.length = arrTreatment.length-1;
	for (var i = 0; i < objTreatment.length; i++)
	{
		objTreatment.options[i].text = arrTreatment[i];
		objTreatment.options[i].value = arrTreatment[i];
	}
}

function disTreatment()
{
	var objTreatment = document.getElementById("cboTreatment");
	objTreatment.disabled = true;
}

function enTreatment()
{
	var objTreatment = document.getElementById("cboTreatment");
	objTreatment.disabled = false;
}
