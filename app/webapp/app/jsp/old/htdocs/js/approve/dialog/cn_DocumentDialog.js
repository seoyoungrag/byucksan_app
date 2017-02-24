function getFocusForHwp97() {
	if ((opener.location.href.indexOf("oldOpener.jsp") >= 0 ||
		opener.location.href.indexOf("CN_RelatedApproveDocument.jsp") >= 0) &&
		opener.getBodyType() == "hwp")
	{
		opener.focus();
		window.focus();
	}
}
