function getMenuHtml()
{
	var strHtml = "";

	strHtml += openApproveMenu(g_strBodyType, "defaultMenu", "display:block");

	if (g_strEditType == "0")
	{
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
	}
	else if (g_strEditType == "1")
	{
		strHtml += composeAgreeMenu(g_strBodyType, g_strOption148);
	}
	else if (g_strEditType == "3" || g_strEditType == "4")
	{
		strHtml += composeCancelMenu(g_strBodyType, g_strEditType);
	}
	else if (g_strEditType == "5" || g_strEditType == "36")
	{
		strHtml += composeResubmitMenu(g_strBodyType, g_strEditType, g_strIsAdminMis);
	}
	else if (g_strEditType == "6")
	{
		strHtml += composeDeptReceiveMenu(g_strBodyType);
	}
	else if (g_strEditType == "7")
	{
		strHtml += composeParallelAgreeMenu(g_strBodyType);
	}
	else if (g_strEditType == "8")
	{
		strHtml += composeSendRelatedMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);
	}
	else if (g_strEditType == "9")
	{
		strHtml += composeDepartInspectAgreeMenu(g_strBodyType);
	}
	else if (g_strEditType == "10")
	{
		strHtml += composeDirectEnforceMenu(g_strBodyType);
	}
	else if (g_strEditType == "11")
	{
		if (g_strIsDirect == "N" && parseInt(g_strOption46) != 4)
			strHtml += composeEnforceChangeMenu(g_strBodyType);
		else
			strHtml += composeEnforceMenu(g_strBodyType, g_strEditType, g_strIsBatch, g_strIsDirect, g_strOption42, g_strOption46, g_strOption83, g_strOption148);
	}
	else if (g_strEditType == "12")
	{
		if (g_strIsDirect == "N")
			strHtml += composeEnforceChangeCompleteMenu(g_strBodyType);
	}
	else if (g_strEditType == "13")
	{
		strHtml += composeRejectEnforceMenu(g_strBodyType, g_strIsBatch, g_strIsDirect, g_strOption42, g_strOption46, g_strOption83, g_strOption148);
	}
	else if (g_strEditType == "14")
	{
		if ((g_strCabinet == "SUBMITED" && (parseInt(g_strOption46) & 1) == 1) ||
			(g_strCabinet == "SUBMITEDAPPROVAL" && (parseInt(g_strOption46) & 1) == 1) ||
			(g_strCabinet == "SUBMITEDDOCFLOW" && (parseInt(g_strOption46) & 1) == 1) ||
			(g_strCabinet == "SENDING" && (parseInt(g_strOption46) & 2) == 2))
		{
			strHtml += composeSendEnforceMenu(g_strBodyType, g_strIsBatch, g_strOption42, g_strOption83, g_strOption148);
		}
	}
	else if (g_strEditType == "15")
	{
		strHtml += composeViewEnforceMenu(g_strBodyType, g_strEditType, g_strIsBatch);
	}
	else if (g_strEditType == "16")
	{
		if (g_strIsDirect == "N" && parseInt(g_strOption46) == 4)
			strHtml += composeEnforceChangeMenu(g_strBodyType);
		else
			strHtml += composeExaminationMenu(g_strBodyType, g_strIsBatch, g_strOption83);		
	}
	else if (g_strEditType == "17")
	{
		strHtml += composeRegiDocMenu(g_strBodyType, g_strIsBatch, g_strIsPost, g_strOption161);
	}
	else if (g_strEditType == "18")
	{
		strHtml += composeDistributionMenu(g_strBodyType, g_strOption148);
	}
	else if (g_strEditType == "20")
	{
		strHtml += composeReceiveMenu(g_strBodyType, g_strOption148);
	}
	else if (g_strEditType == "21")
	{
		strHtml += composeRecvDocMenu(g_strBodyType, g_strIsPost, g_strOption44, g_strOption45, g_strOption148);
//		strHtml += composeMovePassMenu(g_strBodyType);
	}
	else if (g_strEditType == "22")
	{
//		strHtml += composeMovePassMenu(g_strBodyType);
		strHtml += composeSendRelatedMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);
	}
	else if (g_strEditType == "23")
	{
		if (g_strIsPost == "M")
			strHtml += composePublicPostMenu(g_strBodyType);
	}
	else if (g_strEditType == "24")
	{
		strHtml += composeSendRelatedMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);
	}
	else if (g_strEditType == "30")
	{
		strHtml += composeChargerMenu(g_strBodyType, g_strOption148, true);
	}
	else if (g_strEditType == "31")
	{
		strHtml += composePreApproverMenu(g_strBodyType, g_strOption148, true);
	}
	else if (g_strEditType == "32")
	{
		strHtml += composeChargerMenu(g_strBodyType, g_strOption148, false);
	}
	else if (g_strEditType == "33")
	{
		strHtml += composePreApproverMenu(g_strBodyType, g_strOption148, false);
	}
	else if (g_strEditType == "34")
	{
		strHtml += composeCircularApproverMenu(g_strBodyType, true);
	}
	else if (g_strEditType == "35")
	{
		strHtml += composeCircularApproverMenu(g_strBodyType, false);
	}
	else if (g_strEditType == "40")
	{
		strHtml += composeSubmitApproverMenu(g_strBodyType, g_strOption148);
	}
	else if (g_strEditType == "46")
	{
		strHtml += composeDeptInspectReceiveMenu(g_strBodyType);
	}
	else if (g_strEditType == "50")
	{
		strHtml += composeSendRelatedMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);
	}
	else if (g_strEditType == "51")
	{
		if (g_strIsBatch == "Y")
		{
			if (g_strBodyType == "hwp")
			{
				strHtml += "A/68,";
			}
			else
			{
				strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");
			}
		}
	}
	else if (g_strEditType == "52")
	{
		strHtml += composeSendPostMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);
	}

	if (g_strEditType == "2" && g_strCabinet == "REGILEDGER")
		strHtml += composeSendRelatedMenu(g_strBodyType, g_strEditType, g_strIsPost, g_strOption161);

// 벽산
	if (g_strEditType != "0" && g_strEditType != "10" && g_strCabinet == "SUBMITED")
	{
		strHtml += addMenu("BTN_SEND_TO", "javascript:onSelectRelatedSystem(1);return(false);");
	}

// 한양대 시작
/*
	if (g_strCabinet == "SUBMITEDDOCFLOW" && g_strLineName == "1")
	{
		if (g_strIsPost == "N")
		{
			strHtml += addMenu(BTN_PUBLIC_INSPECTION, "javascript:onSetPublicViewInfo();return(false);");
			strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		}
		else
		{
			strHtml += addMenu("공람확인", "javascript:onConfirmPublicView();return(false);");
		}
	}

	if (g_strEditType == "52" || g_strCabinet == "COMPLETED")
	{
		strHtml += addMenu("BTN_SEND_TO", "javascript:onSelectRelatedSystem(1);return(false);");
	}
*/
// 한양대 끝

	strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
	strHtml += closeApproveMenu(g_strBodyType);

	if (g_strEditType == "0" || g_strEditType == "1" || g_strEditType == "6" || g_strEditType == "7" || g_strEditType == "9")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");

		// 해경
		if (g_strEditType == "1" && g_strBodyType == "hwp")
			strHtml += "D/27,";

		strHtml += composeModifyMenu(g_strBodyType);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "5" || g_strEditType == "36")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "11")
	{
		if (g_strIsDirect == "N")
		{
			strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
			strHtml += composeEnforceMenu(g_strBodyType, g_strEditType, g_strIsBatch, g_strIsDirect, g_strOption42, g_strOption46, g_strOption83, g_strOption148);
			strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
			strHtml += closeApproveMenu(g_strBodyType);
		}

		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeModifyEnforceMenu(g_strBodyType);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "12")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeViewEnforceMenu(g_strBodyType, g_strEditType, g_strIsBatch);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "13")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);

		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeModifyRejectEnforceMenu(g_strBodyType);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "16")
	{
		if ((parseInt(g_strOption46) & 4) == 4)
		{
			strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
			strHtml += composeEnforceMenu(g_strBodyType, g_strEditType, g_strIsBatch, g_strIsDirect, g_strOption42, g_strOption46, g_strOption83, g_strOption148);
			strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
			strHtml += closeApproveMenu(g_strBodyType);
		}

		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeModifyRejectEnforceMenu(g_strBodyType);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "17")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "18")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "20")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeRecvDocMenu(g_strBodyType, g_strIsPost, g_strOption44, g_strOption45, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);

		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "21")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "8" || g_strEditType == "22" || g_strEditType == "24" || g_strEditType == "50" || (g_strEditType == "2" && g_strCabinet == "REGILEDGER"))
	{
		strHtml += openApproveMenu(g_strBodyType, "extendMenu", "display:none");
		strHtml += composeSubmitMenu(g_strBodyType, g_strEditType, g_strFormUrl, g_strFormUsage, g_strIsDirect, g_strIsAdminMis, g_strOption42, g_strOption148);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, "Y", g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}

	if (g_strEditType == "0" || g_strEditType == "1")
	{
		strHtml += openApproveMenu(g_strBodyType, "extendSubMenu", "display:none");
		strHtml += composeAutoEnforceMenu(g_strBodyType, g_strIsBatch, g_strOption42, g_strOption46, g_strOption83);
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}

	// icaha 20030716
	if (g_strEditType == "0" || g_strEditType == "5" || g_strEditType == "13" || g_strEditType == "36")
	{
		strHtml += openApproveMenu(g_strBodyType, "extraSubMenu", "display:none");
		strHtml += composePreConvertedEnforceMenu(g_strBodyType, g_strIsBatch);
//		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}
	else if (g_strEditType == "16")
	{
		strHtml += openApproveMenu(g_strBodyType, "extraSubMenu", "display:none");
		strHtml += composeExaminationMenu(g_strBodyType, g_strIsBatch, g_strOption83);		
		strHtml += composeDefaultMenu(g_strBodyType, g_strEditType, g_strIsAttached, g_strIsOpinion, g_strVersion, g_strRoleCode);
		strHtml += closeApproveMenu(g_strBodyType);
	}

	strHtml += openApproveMenu(g_strBodyType, "defaultSubMenu", "display:none");
	strHtml += composeDefaultSubMenu(g_strBodyType, g_strEditType, g_strIsDirect, g_strIsBatch, g_strIsAttached, g_strIsOpinion, g_strVersion);
	strHtml += closeApproveMenu(g_strBodyType);

	if (g_strBodyType == "hwp")
	{
		strHtml += "<INPUT type='hidden' id='hwpMenuSet'></INPUT>";
	}

	return strHtml;
}

function composeSubmitMenu(strBodyType, strEditType, strFormUrl, strFormUsage, strIsDirect, strIsAdminMis, strOption42, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/25,";
		if (strIsAdminMis == "Y")
			strHtml += "D/21,";

		if (strEditType != "18" && strEditType != "20")
			strHtml += "D/26,";

		strHtml += "D/65,D/16,";

		if (strEditType != "18" && strEditType != "20")
			strHtml += "D/45,";

		strHtml += "D/3,D/32,";

		if (strFormUsage == "0")
		{
// 20030824, icaha
//			if (strFormUrl != "")
				strHtml += "D/41,D/42,";
		}
		else
			strHtml += "D/11,";

		if (strOption148 == "0")
			strHtml += "D/44,";

		strHtml += "D/43,";

// icaha 20030716
		if ((strEditType == "0" || strEditType == "5" || strEditType == "13") && strIsDirect == "N")
			strHtml += "D/18,";
	}
	else
	{
		if (strIsAdminMis == "Y")
		{
			strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");
			strHtml += addMenu(BTN_RETURN_ENFORCE, "javascript:onSendReturnNotiToAdminMis();return(false);");
		}
		else
			strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(true);return(false);");

		if (strEditType != "18" && strEditType != "20")
			strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(0);return(false);");

		strHtml += addMenu(BTN_DOCUMENT_OPEN, "javascript:onOpenDocument();return(false);");
		strHtml += addMenu(BTN_DOC_INFORMATION, "javascript:onSetDocInfo();return(false);");

		if (strEditType != "18" && strEditType != "20")
			strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");

//		strHtml += addMenu(BTN_CLASS_INFO, "javascript:onSetClassInfo();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
// 한양대
//		if (strOption42 == "1")
//			strHtml += addMenu(BTN_INVESTIGATION_APPOINTMENT, "javascript:onSetDealerInfo(1);return(false);");

		if (!(strBodyType == "txt" || strBodyType == "html"))
		{
			if (strFormUsage == "0")
			{
// 20030824, icaha
//				if (strFormUrl != "")
				{
					strHtml += addMenu(BTN_ADD_DOCUMENT, "javascript:onAddDocument();return(false);");
					strHtml += addMenu(BTN_REMOVE_DOCUMENT, "javascript:onDeleteDocument();return(false);");
				}
			}
			else
				strHtml += addMenu(BTN_ZIPCODE, "javascript:onOpenZipcodeInput();return(false);");
		}

// related
		if (strOption148 == "0")
			strHtml += addMenu(BTN_SEND_DM, "javascript:onSelectRelatedSystem(0);return(false);");

		if (strBodyType == "gul" || strBodyType == "han")
			strHtml += addMenu(BTN_SUMMARY, "javascript:onEditSynopsis();return(false);");

// icaha 20030716
		if ((strEditType == "0" || strEditType == "5" || strEditType == "13") && strIsDirect == "N")
			strHtml += addMenu(BTN_PREVIEW_CONVERT, "javascript:onPreviewConvert();return(false);");
	}

	return strHtml;
}

function composeAgreeMenu(strBodyType, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/27,D/15,D/20,D/14,D/45,D/2,D/33,";

		if (strOption148 == "1")
			strHtml += "D/54,D/57,";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_AGREE, "javascript:onAgreeApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(1);return(false);");
		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onReverseApprove();return(false);");
		strHtml += addMenu(BTN_EDIT_DOCUMENT, "javascript:onModifyDocument();return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_RESET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
		strHtml += addMenu(BTN_RESET_RECIP_PART, "javascript:onSetRecipPart();return(false);");

		if (strOption148 == "1")
		{
			strHtml += addMenu(BTN_OPEN_OR_NOT, "javascript:onEditPublicLevel();return(false);");
			strHtml += addMenu(BTN_ARCHIVE_FILE, "javascript:onEditArchiveInfo();return(false);");
		}
	}

	return strHtml;
}

function composeCancelMenu(strBodyType, strEditType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (strEditType == "3")
		{
			strHtml += "D/9,";
		}
		else if (strEditType == "4")
		{
			strHtml += "D/1,";
		}
	}
	else
	{
		if (strEditType == "3")
		{
			strHtml += addMenu(BTN_SUBMIT_CANCEL, "javascript:onApproveCancel(0);return(false);");
		}
		else if (strEditType == "4")
		{
			strHtml += addMenu(BTN_AGREE_CANCEL, "javascript:onApproveCancel(1);return(false);");
		}
	}

	return strHtml;
}

function composeResubmitMenu(strBodyType, strEditType, strIsAdminMis)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/63,";
		if (strEditType == "5")
			strHtml += "D/56,";
		if (strIsAdminMis == "Y")
			strHtml += "D/21,";
	}
	else
	{
		strHtml += addMenu(BTN_RETRY_SUBMIT, "javascript:onRetrySubmit();return(false);");
		if (strEditType == "5")
			strHtml += addMenu(BTN_LEDGER_REGISTRATION, "javascript:onRegistRejectApproval();return(false);");
		if (strIsAdminMis == "Y")
			strHtml += addMenu(BTN_RETURN_ENFORCE, "javascript:onSendReturnNotiToAdminMis();return(false);");
	}

	return strHtml;
}

function composeDeptReceiveMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/25,D/79,D/45,D/3,";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");
		strHtml += addMenu(BTN_SUBMITER_SELECT, "javascript:onSetDealerInfo(0);return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
	}

	return strHtml;
}

function composeDeptInspectReceiveMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/66,D/25,D/79,D/45,D/3,";
	}
	else
	{
		strHtml += addMenu(BTN_CHANGE_FORM, "javascript:onChangeInspectForm();return(false);");
		strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");
		strHtml += addMenu(BTN_SUBMITER_SELECT, "javascript:onSetDealerInfo(0);return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
	}

	return strHtml;
}

function composeParallelAgreeMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/27,D/19,D/15,D/45,";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_AGREE, "javascript:onAgreeApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_OPPOSE, "javascript:onOpposeApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(1);return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
	}

	return strHtml;
}

function composeDepartInspectAgreeMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/27,D/15,D/20,D/45,";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_AGREE, "javascript:onAgreeApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(1);return(false);");
		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onDepartInspectReverseApprove();return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
	}

	return strHtml;
}

function composeChargerMenu(strBodyType, strOption148, bSelected)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (bSelected)
		{
			strHtml += "D/25,D/12,";

			if (strOption148 == "0")
				strHtml += "D/44,";
			else if (strOption148 == "1")
				strHtml += "D/57,";
		}
		else
			strHtml += "D/6,";

		strHtml += "D/20,D/45,D/3,";
	}
	else
	{
		if (bSelected)
		{
			strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");
			strHtml += addMenu(BTN_CHARGER_SELECT, "javascript:onSetDealerInfo(3);return(false);");
// related
			if (strOption148 == "0")
				strHtml += addMenu(BTN_SEND_DM, "javascript:onSelectRelatedSystem(0);return(false);");
			else if (strOption148 == "1")
				strHtml += addMenu(BTN_ARCHIVE_FILE, "javascript:onEditArchiveInfo();return(false);");
		}
		else
			strHtml += addMenu(BTN_APPROVE_CIRCULAR, "javascript:onAgreeApprove();return(false);");

		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onCircularReverseApprove();return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
	}

	return strHtml;
}

function composePreApproverMenu(strBodyType, strOption148, bSelected)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (bSelected)
		{
			strHtml += "D/30,D/12,";

			if (strOption148 == "0")
				strHtml += "D/44,";
		}
		else
			strHtml += "D/30,";

		strHtml += "D/20,D/15,D/52,D/3,";
	}
	else
	{
		if (bSelected)
		{
			strHtml += addMenu(BTN_APPROVE_PRIOR, "javascript:onSubmitApprove(false);return(false);");
			strHtml += addMenu(BTN_CHARGER_SELECT, "javascript:onSetDealerInfo(3);return(false);");
// related
			if (strOption148 == "0")
				strHtml += addMenu(BTN_SEND_DM, "javascript:onSelectRelatedSystem(0);return(false);");
		}
		else
			strHtml += addMenu(BTN_APPROVE_PRIOR, "javascript:onAgreeApprove();return(false);");

		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onCircularReverseApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(1);return(false);");
		strHtml += addMenu(BTN_SET_COMMAND, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
	}

	return strHtml;
}

function composeCircularApproverMenu(strBodyType, bSerial)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/6,D/20,D/15,D/45,";
		if (bSerial)
			strHtml += "D/3,";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_CIRCULAR, "javascript:onAgreeApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onCircularReverseApprove();return(false);");
		strHtml += addMenu(BTN_APPROVE_SUSPEND, "javascript:onSuspendApprove(1);return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");

		if (bSerial)
			strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
	}

	return strHtml;
}

function composeSubmitApproverMenu(strBodyType, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/25,D/79,D/20,D/45,D/3,";

		if (strOption148 == "0")
			strHtml += "D/44;";
	}
	else
	{
		strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");
		strHtml += addMenu(BTN_SUBMITER_SELECT, "javascript:onSetDealerInfo(0);return(false);");
		strHtml += addMenu(BTN_APPROVE_REJECT, "javascript:onCircularReverseApprove();return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
// related
		if (strOption148 == "0")
			strHtml += addMenu(BTN_SEND_DM, "javascript:onSelectRelatedSystem(0);return(false);");
	}

	return strHtml;
}

function composeDirectEnforceMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/64,D/16,D/69,D/32,";
	}
	else
	{
		strHtml += addMenu(BTN_ENFORCE_DOC, "javascript:onDirectEnforceApprove(true);return(false);");
		strHtml += addMenu(BTN_DOC_INFORMATION, "javascript:onSetDocInfo();return(false);");
		strHtml += addMenu(BTN_APPROVE_INFO, "javascript:onSetApproveInfo();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
/*
		strHtml += addMenu("zipcode test", "javascript:onOpenZipcodeInput();return(false);");

// required javascript code
function onOpenZipcodeInput()
{
	var strUrl = g_strBaseUrl + "zipcode/CN_ZipcodeInput.jsp";			// ?zipcodetarget=" + strZipcodeTarget + "&addresstarget=" + strAddressTarget;
	var objWindow = window.open(strUrl,"ZipcodeInput","toolbar=no,resizable=no, status=yes, width=500,height=440");
}
*/
	}

	return strHtml;
}

function composeEnforceChangeMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/35,D/67,D/32,";
	}
	else
	{
		strHtml += addMenu(BTN_CONVERT_ENFORCE, "javascript:onConvertEnforce();return(false);");
		strHtml += addMenu(BTN_CHANGE_DOCINFO, "javascript:onChangeDocInfo();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
	}

	return strHtml;
}

function composeEnforceChangeCompleteMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/18,";
	}
	else
	{
		strHtml += addMenu(BTN_VIEW_ENFORCEDOC, "javascript:onViewEnforceDoc();return(false);");
	}

	return strHtml;
}

function composeModifyMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/61,D/62,";
	}
	else
	{
		strHtml += addMenu(BTN_EDIT_COMPLETE, "javascript:onApplyModify();return(false);");
		strHtml += addMenu(BTN_EDIT_CANCEL, "javascript:onCancelModify();return(false);");
	}

	return strHtml;
}

function composeEnforceMenu(strBodyType, strEditType, strIsBatch, strIsDirect, strOption42, strOption46, strOption83, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13")) ||
			((parseInt(strOption46) & 4) == 4 && strEditType == "16"))
		{
			strHtml += "E/22,";		// 기안자 발송시
		}
		else
		{
			if (((parseInt(strOption46) & 2) == 2 && strEditType == "11" || strEditType == "13"))
				strHtml += "E/64,";
		}

		if (strOption42 == "1" && (strEditType == "11" || strEditType == "13"))
			strHtml += "D/40,";		// 기안자 심사의뢰시

		if (strIsBatch == "Y")
			strHtml += "R/68,";

		if (strIsDirect == "N")
			strHtml += "R/66,";

		if (strOption83 == "1" && ((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13") ||
			((parseInt(strOption46) & 4) == 4 && strEditType == "16")))
		{
			strHtml += "E/29,E/28,";
// 연합캐피탈 (향후 옵션처리)
			strHtml += "E/7,E/8,";
		}

		if (((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13")) ||
			((parseInt(strOption46) & 4) == 4 && strEditType == "16"))
		{
			if (strOption148 == "1")
				strHtml += "E/13,";
		}

// 건보 요구로 시행 수정 막음 -> 필드 수정 메뉴 추가함
//		strHtml += "D/67,R/14,A/32,";
		strHtml += "D/67,R/84,A/32,";
	}
	else
	{
		if (((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13")) ||
			((parseInt(strOption46) & 4) == 4 && strEditType == "16"))
		{
			strHtml += addMenu(BTN_SEND_ENFORCE, "javascript:onSendEnforce();return(false);");	// 기안자 발송시
		}
		else
		{
			if (((parseInt(strOption46) & 2) == 2 && strEditType == "11" || strEditType == "13"))
				strHtml += addMenu(BTN_ENFORCE_APPROVE, "javascript:onEnforceApprove();return(false);");
		}

		if (strOption42 == "1" && (strEditType == "11" || strEditType == "13"))
			strHtml += addMenu(BTN_INVESTIGATION, "javascript:onSetDealerInfo(1);return(false);");	// 기안자 심사의뢰시

		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		if (strBodyType != "txt" && strBodyType != "html")
		{
			if (strIsDirect == "N" && parseInt(g_strOption46) != 4)
				strHtml += addMenu(BTN_CHANGE_FORM, "javascript:onChangeForm();return(false);");

			if (strOption83 == "1" && ((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13") ||
				((parseInt(strOption46) & 4) == 4 && strEditType == "16")))
			{
				strHtml += addMenu(BTN_SIGNATURE_SEAL, "javascript:onClickStamp(0);return(false);");
				strHtml += addMenu(BTN_SIGNATURE_WITHOUT, "javascript:onClickStamp(2);return(false);");
// 연합캐피탈 (향후 옵션처리)
				strHtml += addMenu(BTN_OFFICIAL_SEAL, "javascript:onClickStamp(1);return(false);");
				strHtml += addMenu(BTN_OFFICIAL_SEAL_WITHOUT, "javascript:onClickStamp(3);return(false);");
			}
		}

		if (((parseInt(strOption46) & 1) == 1 && (strEditType == "11" || strEditType == "13")) ||
			((parseInt(strOption46) & 4) == 4 && strEditType == "16"))
		{
			if (strOption148 == "1")
				strHtml += addMenu(BTN_CIPHER, "javascript:onCertificateRecipient();return(false);");
		}

		strHtml += addMenu(BTN_CHANGE_DOCINFO, "javascript:onChangeDocInfo();return(false);");
//		strHtml += addMenu(BTN_EDIT_DOCUMENT, "javascript:onModifyEnforce();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
		strHtml += addMenu(BTN_BIG_VIEWING, "javascript:onSetViewScale(true);return(false);");
		strHtml += addMenu(BTN_SMALL_VIEWING, "javascript:onSetViewScale(false);return(false);");
	}

	return strHtml;
}

function composeRejectEnforceMenu(strBodyType, strIsBatch, strIsDirect, strOption42, strOption46, strOption83, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/63,";
	}
	else
	{
		strHtml += addMenu(BTN_RETRY_SUBMIT, "javascript:onRetrySubmitRejectEnforce();return(false);");
	}

	strHtml += composeEnforceMenu(strBodyType, strIsBatch, strIsDirect, strOption42, strOption46, strOption83, strOption148);

	return strHtml;
}

function composeAutoEnforceMenu(strBodyType, strIsBatch, strOption42, strOption46, strOption83)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
//		strHtml += "E/64,");
//		if (strOption46 == "1" || strOption46 == "8")
		if ((parseInt(strOption46) & 1) == 1 || (parseInt(strOption46) & 8) == 8)
			strHtml += "E/22,";		// 기안자 발송시

		if (strOption42 == "1")
			strHtml += "D/40,";		// 기안자 심사의뢰시

		if (strIsBatch == "Y")
			strHtml += "R/68,";

		strHtml += "R/66,";

		if (strOption83 == "1")
		{
			strHtml += "E/29,E/28,";
// 연합캐피탈 (향후 옵션처리)
			strHtml += "E/7,E/8,";
		}

		strHtml += "D/67,A/32,";
	}
	else
	{
//		strHtml += addMenu(BTN_ENFORCE_APPROVE"), "javascript:onEnforceApprove();return(false);");
//		if (strOption46 == "1" || strOption46 == "8")
		if ((parseInt(strOption46) & 1) == 1 || (parseInt(strOption46) & 8) == 8)
			strHtml += addMenu(BTN_SEND_ENFORCE, "javascript:onAutoSendEnforce();return(false);");	// 기안자 발송시

		if (strOption42 == "1")
			strHtml += addMenu(BTN_INVESTIGATION, "javascript:onSetDealerInfo(1);return(false);");	// 기안자 심사의뢰시

		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		if (strBodyType != "txt" && strBodyType != "html")
		{
			strHtml += addMenu(BTN_CHANGE_FORM, "javascript:onChangeForm();return(false);");

			if (strOption83 == "1")
			{
				strHtml += addMenu(BTN_SIGNATURE_SEAL, "javascript:onClickStamp(0);return(false);");
				strHtml += addMenu(BTN_SIGNATURE_WITHOUT, "javascript:onClickStamp(2);return(false);");
// 연합캐피탈 (향후 옵션처리)
				strHtml += addMenu(BTN_OFFICIAL_SEAL, "javascript:onClickStamp(1);return(false);");
				strHtml += addMenu(BTN_OFFICIAL_SEAL_WITHOUT, "javascript:onClickStamp(3);return(false);");
			}
		}

		strHtml += addMenu(BTN_CHANGE_DOCINFO, "javascript:onChangeDocInfo();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
		strHtml += addMenu(BTN_BIG_VIEWING, "javascript:onSetViewScale(true);return(false);");
		strHtml += addMenu(BTN_SMALL_VIEWING, "javascript:onSetViewScale(false);return(false);");
	}

	return strHtml;
}

function composeModifyEnforceMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "R/61,R/62,";
	}
	else
	{
		strHtml += addMenu(BTN_EDIT_COMPLETE, "javascript:onApplyEnforceModify();return(false);");
		strHtml += addMenu(BTN_EDIT_CANCEL, "javascript:onCancelEnforceModify();return(false);");
	}

	return strHtml;
}

function composeModifyRejectEnforceMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "R/61,R/62,";
	}
	else
	{
		strHtml += addMenu(BTN_EDIT_COMPLETE, "javascript:onApplyRejectEnforceModify();return(false);");
		strHtml += addMenu(BTN_EDIT_CANCEL, "javascript:onCancelRejectEnforceModify();return(false);");
	}

	return strHtml;
}

function composeViewEnforceMenu(strBodyType, strEditType, strIsBatch)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (strIsBatch == "Y")
			strHtml += "R/68,";

		if (strEditType == "12")
			strHtml += "A/55,";
	}
	else
	{
		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		if (strEditType == "12")
		{
			strHtml += addMenu(BTN_VIEW_BODYDOC, "javascript:onViewBodyDoc();return(false);");
			strHtml += addMenu(BTN_BIG_VIEWING, "javascript:onSetViewScale(true);return(false);");
			strHtml += addMenu(BTN_SMALL_VIEWING, "javascript:onSetViewScale(false);return(false);");
		}
	}

	return strHtml;
}

function composeSendEnforceMenu(strBodyType, strIsBatch, strOption42, strOption83, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "E/22,";

		if (strOption42 == "1")
			strHtml += "D/40,";

		if (strIsBatch == "Y")
			strHtml += "R/68,";

		if (strOption83 == "1")
		{
			strHtml += "E/29,E/28,";
// 연합캐피탈 (향후 옵션처리)
			strHtml += "E/7,E/8,";
		}

		if (strOption148 == "1")
			strHtml += "E/13,";

		strHtml += "D/67,A/32,";
	}
	else
	{
		strHtml += addMenu(BTN_SEND_ENFORCE, "javascript:onSendEnforce();return(false);");

		if (strOption42 == "1")
			strHtml += addMenu(BTN_INVESTIGATION, "onSetDealerInfo(1);return(false);");

		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		if (strBodyType != "txt" && strBodyType != "html")
		{
			if (strOption83 == "1")
			{
				strHtml += addMenu(BTN_SIGNATURE_SEAL, "javascript:onClickStamp(0);return(false);");
				strHtml += addMenu(BTN_SIGNATURE_WITHOUT, "javascript:onClickStamp(2);return(false);");
// 연합캐피탈 (향후 옵션처리)
				strHtml += addMenu(BTN_OFFICIAL_SEAL, "javascript:onClickStamp(1);return(false);");
				strHtml += addMenu(BTN_OFFICIAL_SEAL_WITHOUT, "javascript:onClickStamp(3);return(false);");
			}
		}

		if (strOption148 == "1")
			strHtml += addMenu(BTN_CIPHER, "javascript:onCertificateRecipient();return(false);");

		strHtml += addMenu(BTN_CHANGE_DOCINFO, "javascript:onChangeDocInfo();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
	}

	return strHtml;
}

function composeExaminationMenu(strBodyType, strIsBatch, strOption83)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/38,D/36,";

		if (strIsBatch == "Y")
			strHtml += "R/68,";

// 공공버전에서는 심사에서 관인을 찍지 않음
//		if (strOption83 == "1")
//			strHtml += "E/7,E/8,";

// 건보 시행문 수정 차단
//		strHtml += "R/14,D/45,A/32,";
		strHtml += "D/45,A/32,";
	}
	else
	{
		strHtml += addMenu(BTN_INVESTIGATION_SIGNATURE, "javascript:onAgreeExamination();return(false);");
		strHtml += addMenu(BTN_INVESTIGATION_RETURN, "javascript:onRejectExamination();return(false);");

		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

// 공공버전에서는 심사에서 관인을 찍지 않음
//		if (strOption83 == "1")
//		{
//			strHtml += addMenu(BTN_OFFICIAL_SEAL, "javascript:onClickStamp(1);return(false);");
//			strHtml += addMenu(BTN_OFFICIAL_SEAL_WITHOUT, "javascript:onClickStamp(3);return(false);");
//		}

//		strHtml += addMenu(BTN_EDIT_DOCUMENT, "javascript:onModifyEnforce();return(false);");
		strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
	}

	return strHtml;
}

function composeRegiDocMenu(strBodyType, strIsBatch, strIsPost, strOption161)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "E/48,";
		if (strIsBatch == "Y")
			strHtml += "R/68,";
//		strHtml += "E/73,E/10,A/32,D/39,";
		strHtml += "E/73,E/10,A/32,";
		
		if (strIsPost == "N" && (strOption161 == "2" || strOption161 == "3"))
			strHtml += "D/5,";
			
//		strHtml = addString(strHtml, "D/85,");
	}
	else
	{
		strHtml += addMenu(BTN_RESEND_ENFORCE, "javascript:onResendEnforce();return(false);");

		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		strHtml += addMenu(BTN_CONFIRM_ACCEPT, "javascript:onConfirmAccept();return(false);");
		strHtml += addMenu(BTN_CALLBACK_ENFORCE, "javascript:onCallbackEnforce();return(false);");
		strHtml += addMenu(BTN_SET_RECIP_PART, "javascript:onSetRecipPart();return(false);");
// related
//		strHtml += addMenu(BTN_SEND_TO, "javascript:onSelectRelatedSystem(1);return(false);");

		if (strIsPost == "N" && (strOption161 == "2" || strOption161 == "3"))
			strHtml += addMenu(BTN_PUBLIC_INSPECTION, "javascript:onSetPublicViewInfo();return(false);");

//		strHtml += addMenu(BTN_MODIFY_SUBMIT, "javascript:onModifySubmit();return(false);");
//		strHtml += addMenu(BTN_ATTACH_SUBMIT, "javascript:onChangeAttachSubmitForm();return(false);");
	}

	return strHtml;
}

function composeDistributionMenu(strBodyType, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/23,D/21,";

		if (strOption148 == "1")
			strHtml += "D/47,D/49,";
	}
	else
	{
		strHtml += addMenu(BTN_DELIVERY_ENFORCE, "javascript:onSelectDelivery();return(false);");
		strHtml += addMenu(BTN_RETURN_ENFORCE, "javascript:onRejectDistribute();return(false);");

		if (strOption148 == "1")
		{
//			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onMoveDistribute();return(false);");
//			strHtml += addMenu(BTN_PASS_ENFORCE, "javascript:onPassDistribute();return(false);");
		}
	}

	return strHtml;
}

function composeReceiveMenu(strBodyType, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/51,D/21,"

		if (strOption148 == "0")
			strHtml += "D/47,";
		else
			strHtml += "D/47,D/49,D/57,";
	}
	else
	{
		strHtml += addMenu(BTN_RECEIVE_ENFORCE, "javascript:onReceiveEnforce();return(false);");
		strHtml += addMenu(BTN_RETURN_ENFORCE, "javascript:onRejectReceive();return(false);");
/*
		if (strOption148 == "0")
			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onSelectMovePart();return(false);");
		else
		{
			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onMoveEnforce();return(false);");
			strHtml += addMenu(BTN_PASS_ENFORCE, "javascript:onPassEnforce();return(false);");
			strHtml += addMenu(BTN_ARCHIVE_FILE, "javascript:onEditArchiveInfo();return(false);");
		}
*/
	}

	return strHtml;
}

function composeRecvDocMenu(strBodyType, strIsPost, strOption44, strOption45, strOption148)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/21,";

		if (strOption148 == "0")
			strHtml += "D/47,";
		else
			strHtml += "D/47,D/49,";

		if (strOption44 == "1")
		{
			if (strOption148 == "0")
				strHtml += "D/31,";

			strHtml += "D/12,";
		}

		if (strOption45 == "1")
			strHtml += "D/79,";

		strHtml += "D/25,";

		if (strOption45 == "1")
			strHtml += "D/45,";

		strHtml += "D/3,";

		if (strOption148 == "0")
			strHtml += "D/44,";

		if (strIsPost == "N")
			strHtml += "D/5,";
	}
	else
	{
		strHtml += addMenu(BTN_RETURN_ENFORCE, "javascript:onRejectAfterReceive();return(false);");
/*
		if (strOption148 == "0")
			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onSelectMovePart();return(false);");
		else
		{
			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onMoveEnforce();return(false);");
			strHtml += addMenu(BTN_PASS_ENFORCE, "javascript:onPassEnforce();return(false);");
		}
*/
		if (strOption44 == "1")
		{
			if (strOption148 == "0")
				strHtml += addMenu(BTN_PREVIEW_SELECT, "javascript:onSetDealerInfo(2);return(false);");

			strHtml += addMenu(BTN_CHARGER_SELECT, "javascript:onSetDealerInfo(3);return(false);");
		}

		if (strOption45 == "1")
			strHtml += addMenu(BTN_SUBMITER_SELECT, "javascript:onSetDealerInfo(0);return(false);");

		strHtml += addMenu(BTN_APPROVE_SEND, "javascript:onSubmitApprove(false);return(false);");

		if (strOption45 == "1")
			strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");

		strHtml += addMenu(BTN_SET_APPROVE_LINE, "javascript:onSetApproveLine();return(false);");
// related
		if (strOption148 == "0")
			strHtml += addMenu(BTN_SEND_DM, "javascript:onSelectRelatedSystem(0);return(false);");

		if (strIsPost == "N")
			strHtml += addMenu(BTN_PUBLIC_INSPECTION, "javascript:onSetPublicViewInfo();return(false);");
	}

	return strHtml;
}

function composeMovePassMenu(strBodyType, strOption148)
{
	var strHtml = "";

	if (strOption148 == "1")
	{
		if (strBodyType == "hwp")
		{
			strHtml += "D/47,D/49,";
		}
		else
		{
//			strHtml += addMenu(BTN_MOVE_ENFORCE, "javascript:onMoveEnforce();return(false);");
//			strHtml += addMenu(BTN_PASS_ENFORCE, "javascript:onPassEnforce();return(false);");
		}
	}

	return strHtml;
}

function composeSendRelatedMenu(strBodyType, strEditType, strIsPost, strOption161)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
//		strHtml += "D/39,";

		if (strIsPost == "N" &&
			(strEditType == "22" || strEditType == "24" || (strEditType == "2" && (strOption161 == "2" || strOption161 == "3"))))
		{
			strHtml += "D/5,D/45,";
		}
		
//		if (strEditType == "2" || strEditType == "24")
//			strHtml += "D/85,";
	}
	else
	{
// related
//		strHtml += addMenu(BTN_SEND_TO, "javascript:onSelectRelatedSystem(1);return(false);");

		if (strIsPost == "N" &&
//			(strEditType == "22" || strEditType == "24" || (strEditType == "2" && (strOption161 == "2" || strOption161 == "3"))))
			(strEditType == "24" || (strEditType == "2" && (strOption161 == "2" || strOption161 == "3"))))
		{
			strHtml += addMenu(BTN_PUBLIC_INSPECTION, "javascript:onSetPublicViewInfo();return(false);");
			strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		}

//		if (strEditType == "2" || strEditType == "24")
		{
//			strHtml += addMenu(BTN_MODIFY_SUBMIT, "javascript:onModifySubmit();return(false);");
//			strHtml += addMenu(BTN_ATTACH_SUBMIT, "javascript:onChangeAttachSubmitForm();return(false);");
		}
	}

	return strHtml;
}

function composeSendPostMenu(strBodyType, strEditType, strIsPost, strOption161)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (strIsPost == "N" &&
			(strEditType == "22" || strEditType == "24" || strEditType == "52" || (strEditType == "2" && (strOption161 == "2" || strOption161 == "3"))))
		{
			strHtml += "D/5,D/26,";
		}
	}
	else
	{
		if (strIsPost == "N" &&
			(strEditType == "22" || strEditType == "24" || strEditType == "52" || (strEditType == "2" && (strOption161 == "2" || strOption161 == "3"))))
		{
			strHtml += addMenu(BTN_PUBLIC_INSPECTION, "javascript:onSetPublicViewInfo();return(false);");
			strHtml += addMenu(BTN_SET_OPINION, "javascript:onSetOpinion();return(false);");
		}
	}

	return strHtml;
}

function composePublicPostMenu(strBodyType)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		strHtml += "D/81,D/80,";
	}
	else
	{
		strHtml += addMenu(BTN_MODIFY_INFORMATION, "javascript:onModPublicViewInfo();return(false);");
		strHtml += addMenu(BTN_DELETE_BULLETIN, "javascript:onDeletePublicView();return(false);");
	}

	return strHtml;
}

function composeDefaultMenu(strBodyType, strEditType, strIsAttached, strIsOpinion, strVersion, strRoleCode)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (strEditType != "0" && strEditType != "10")
			strHtml += "A/24,";

		if (strIsAttached == "Y")
			strHtml += "A/53,";

		if (strIsOpinion == "Y")
			strHtml += "D/46,";

		strHtml += "A/58,A/59,A/60,";
		
		if (strEditType == "17" || strEditType == "19" || strEditType == "21" ||
			strEditType == "22" || strEditType == "24")
		{
			if (strRoleCode.indexOf("2001") != -1 || strRoleCode.indexOf("2002") != -1)
				strHtml += "A/86,";		
		}
	}
	else
	{
		if (strEditType != "0" && strEditType != "10")
			strHtml += addMenu(BTN_VIEW_DOC_DETAIL, "javascript:onViewDocDetail();return(false);");

		if (strIsAttached == "Y")
			strHtml += addMenu(BTN_ATTACH_FILE, "javascript:onAttachFiles();return(false);");

		if (strIsOpinion == "Y")
			strHtml += addMenu(BTN_VIEW_OPINION, "javascript:onViewOpinion();return(false);");

		strHtml += addMenu(BTN_SAVE_FILE, "javascript:onSaveFile();return(false);");
		strHtml += addMenu(BTN_PRINT, "javascript:onPrint();return(false);");

		if (strVersion.indexOf("1.0") != -1 || strVersion.indexOf("1.5") != -1)
		{
			strHtml += addMenu(BTN_CLOSE, "javascript:onClose();return(false);");
		}
	}

	return strHtml;
}

function composeDefaultSubMenu(strBodyType, strEditType, strIsDirect, strIsBatch, strIsAttached, strIsOpinion, strVersion)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
		if (strIsDirect == "N" && strIsBatch == "Y" &&
			(strEditType == "11" || strEditType == "14" || strEditType == "15" || strEditType == "16" || strEditType == "17"))
		{
			strHtml += "A/68,";
		}

		strHtml += "A/24,";

		if (strIsAttached == "Y")
			strHtml += "A/53,";

		if (strIsOpinion == "Y")
			strHtml += "D/46,";

		strHtml += "A/58,A/59,A/60,";
	}
	else
	{
		if (strIsDirect == "N" && strIsBatch == "Y" &&
			(strEditType == "11" || strEditType == "14" || strEditType == "15" || strEditType == "16" || strEditType == "17"))
		{
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");
		}

		strHtml += addMenu(BTN_VIEW_DOC_DETAIL, "javascript:onViewDocDetail();return(false);");

		if (strIsAttached == "Y")
			strHtml += addMenu(BTN_ATTACH_FILE, "javascript:onAttachFiles();return(false);");

		if (strIsOpinion == "Y")
			strHtml += addMenu(BTN_VIEW_OPINION, "javascript:onViewOpinion();return(false);");

		strHtml += addMenu(BTN_SAVE_FILE, "javascript:onSaveFile();return(false);");
		strHtml += addMenu(BTN_PRINT, "javascript:onPrint();return(false);");

		if (strVersion.indexOf("1.0") != -1 || strVersion.indexOf("1.5") != -1)
			strHtml += addMenu(BTN_CLOSE, "javascript:onClose();return(false);");
	}

	return strHtml;
}

// icaha 20030716
function composePreConvertedEnforceMenu(strBodyType, strIsBatch)
{
	var strHtml = "";

	if (strBodyType == "hwp")
	{
//		if (strIsBatch == "Y")
			strHtml += "R/68,";

		strHtml += "R/66,";

		strHtml += "A/55,";
	}
	else
	{
		if (strIsBatch == "Y")
			strHtml += addMenu(BTN_CHANGE_PROPOSAL, "javascript:onChangeProposal();return(false);");

		if (strBodyType != "txt" && strBodyType !== "html")
		{
			strHtml += addMenu(BTN_CHANGE_FORM, "javascript:onChangeForm();return(false);");
		}

		strHtml += addMenu(BTN_VIEW_BODYDOC, "javascript:onViewBodyDoc();return(false);");
		strHtml += addMenu(BTN_BIG_VIEWING, "javascript:onSetViewScale(true);return(false);");
		strHtml += addMenu(BTN_SMALL_VIEWING, "javascript:onSetViewScale(false);return(false);");
	}

	return strHtml;
}
