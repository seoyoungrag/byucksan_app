<%@ page contentType = "text/html; charset=euc-kr" %>
<%@ page session = "true" %>

<%@ page import="com.sds.acube.app.idir.ldaporg.client.*" %>
<%@ page import="com.sds.acube.app.idir.common.vo.*" %>
<%!
	public String MakeSubTree(LDAPOrganizations ldapOrgs, int nDepth)
	{
		StringBuffer 		strTable = new StringBuffer();
		LDAPOrganization 	ldapOrg = null;

		int nListLength = ldapOrgs.size();
		for (int i = 0 ; i < nListLength ; i++)
		{
			ldapOrg = ldapOrgs.get(i);

			strTable.append(drawTree(ldapOrg, nDepth));
		}

		return strTable.toString();
	}

	public String drawTree(LDAPOrganization ldapOrg, int nDepth)
	{
		StringBuffer strTable = new StringBuffer();
		
		strTable.append("[DN]" + ldapOrg.getDN());
		strTable.append("[UcOrgFullName]" + ldapOrg.getUcOrgFullName());
		strTable.append("[OrganizationalUnitName]" + ldapOrg.getOrganizationalUnitName());
		strTable.append("[UcOrganizationalUnitName]" + ldapOrg.getUcOrganizationalUnitName());
		strTable.append("[TopOUCode]" + ldapOrg.getTopOUCode());
		strTable.append("[OUCode]" + ldapOrg.getOUCode());
		strTable.append("[ParentOUCode]" + ldapOrg.getParentOUCode());
		strTable.append("[OULevel]" + ldapOrg.getOULevel());
		strTable.append("[OUOrder]" + ldapOrg.getOUOrder());
		strTable.append("[OUSendOutDocumentYN]" + ldapOrg.getOUSendOutDocumentYN());
		strTable.append("[OUReceiveDocumentYN]" + ldapOrg.getOUReceiveDocumentYN());
		strTable.append("[UcChiefTitle]" + ldapOrg.getUcChiefTitle());
		strTable.append("[OUSMTPAddress]" + ldapOrg.getOUSMTPAddress());
		strTable.append("[OUDocumentRecipientSymbol]" + ldapOrg.getOUDocumentRecipientSymbol());
		strTable.append("[UseGroupware]" + ldapOrg.getUseGroupware());
		strTable.append("<hr>");

		String strDeptCode = ldapOrg.getOUCode();
		String strDN = ldapOrg.getDN();
		String strID = strDeptCode;
		String strDisplayName = ldapOrg.getOrganizationalUnitName();
		String strSymbol = ldapOrg.getOUDocumentRecipientSymbol();
		String strChiefName = ldapOrg.getUcChiefTitle();
		String strDocCode = "";
		String strProxyCode = "";
		String strHasChild = "U";
		//String strDepth = ldapOrg.getOULevel();
		String strParentID = ldapOrg.getParentOUCode();
		String strIsInstitution = ldapOrg.getOUSendOutDocumentYN();
		if (strIsInstitution.equalsIgnoreCase("Y"))
			strIsInstitution = "Y";
		else
			strIsInstitution = "N";
		String strSelectYN = ldapOrg.getOUReceiveDocumentYN();
		if (strSelectYN.equalsIgnoreCase("Y"))
			strSelectYN = "Y";
		else
			strSelectYN = "N";

		// modify by kkang 2004.05.13 - LDAP parent OU Code 정보 불일치로 인한 정보 수정
		String strDepth = Integer.toString(nDepth);


		return strTable.toString();
	}

	public String MakeList(LDAPOrganizations ldapOrgs)
	{
		StringBuffer strTable = new StringBuffer();
		LDAPOrganization ldapOrg = null;

		int nListLength = ldapOrgs.size();
		for (int i = 0 ; i < nListLength ; i++)
		{
			ldapOrg = ldapOrgs.get(i);

			strTable.append(drawList(ldapOrg));
		}

		return strTable.toString();
	}

	public String drawList(LDAPOrganization ldapOrg)
	{

		StringBuffer strTable = new StringBuffer();

		String strDeptCode = ldapOrg.getOUCode();
		String strDN = ldapOrg.getDN();
		String strID = strDeptCode;
		String strDisplayName = ldapOrg.getOrganizationalUnitName();
		String strSymbol = ldapOrg.getOUDocumentRecipientSymbol();
		String strChiefName = ldapOrg.getUcChiefTitle();
		String strDocCode = "";
		String strProxyCode = "";
		String strHasChild = "U";
		String strDepth = ldapOrg.getOULevel();
		int nDepth = Integer.parseInt(strDepth);
		String strParentID = ldapOrg.getParentOUCode();
		String strIsInstitution = ldapOrg.getOUSendOutDocumentYN();
		if (strIsInstitution.equalsIgnoreCase("Y"))
			strIsInstitution = "Y";
		else
			strIsInstitution = "N";
		String strSelectYN = ldapOrg.getOUReceiveDocumentYN();
		if (strSelectYN.equalsIgnoreCase("Y"))
			strSelectYN = "Y";
		else
			strSelectYN = "N";


		strTable.append("<TABLE border='0' width='465' height='18' expand='0' haschild='U' cellspacing='0' cellpadding='0' id='");
		strTable.append(strID);
		strTable.append("TBL' depth='");
		strTable.append(strDepth);
		strTable.append("' deptcode='");
		strTable.append(strID);
		strTable.append("' parentid='");
		strTable.append(strParentID);
		strTable.append("' isinstitution='");
		strTable.append(strIsInstitution);


		if (strSelectYN.equals("Y"))
			strTable.append("'><TR name='selected' style='cursor:default' onclick='onListClick();'");
		else
			strTable.append("' style='color:gray;'><TR");

		strTable.append(" depth='");
		strTable.append(strDepth);
		strTable.append("' type='' id='");
		strTable.append(strID);
		strTable.append("' deptcode='");
		strTable.append(strDeptCode);
		strTable.append("' deptname='");
		strTable.append(strDisplayName);
		strTable.append("' deptsymbol='");
		strTable.append(strSymbol);
		strTable.append("' deptchief='");
		strTable.append(strChiefName);
		strTable.append("' doccode='");
		strTable.append(strDocCode);
		strTable.append("' proxycode='");
		strTable.append(strProxyCode);
		strTable.append("' parentid='");
		strTable.append(strParentID);
		strTable.append("' isinstitution='");
		strTable.append(strIsInstitution);
		strTable.append("' ispubdocrecip='Y' class='tb_left' iscertexist='N' sendingtype='0'>");

/*
		strTable.append("<TD width='10'></TD>");
		for (int i = 0; i < nDepth; i++)
		{
			strTable.append("<TD width='10'></TD>");
		}

		strTable.append("<TD align='center' valign='middle'><A href='#' onclick='javascript:getChild(&quot;");
		strTable.append(strID);
		strTable.append("&quot;,&quot;");
		strTable.append(strDN);
		strTable.append("&quot;);return(false);'>");
		strTable.append("<IMG src='image/");
		strTable.append(strVersion);
		strTable.append("/haschild_node.gif' border='0' id='");
		strTable.append(addString(strID, "IMG"));
		strTable.append("'></IMG></A></TD>");
*/

		if (strSelectYN.equals("Y"))
		{
//			strTable.append("<TD href='#' name='selected' style='cursor:hand;'");
//			strTable.append(" onclick='javascript:clickOnName(&quot;");
//			strTable.append(strID);
//			strTable.append("&quot;,&quot;");
//			strTable.append(strDisplayName);
//			strTable.append("&quot;);return(false);'");

			strTable.append("<TD ");
			strTable.append("onmousemove='onListMouseMove();'");
			strTable.append(" depth='");
			strTable.append(strDepth);
			strTable.append("' type='' id='");
			strTable.append(strID);
			strTable.append("' deptcode='");
			strTable.append(strDeptCode);
			strTable.append("' deptname='");
			strTable.append(strDisplayName);
			strTable.append("' deptsymbol='");
			strTable.append(strSymbol);
			strTable.append("' deptchief='");
			strTable.append(strChiefName);
			strTable.append("' doccode='");
			strTable.append(strDocCode);
			strTable.append("' proxycode='");
			strTable.append(strProxyCode);
			strTable.append("' ispubdocrecip='Y' class='tb_left' iscertexist='N' sendingtype='0'>");
		}
		else
		{
			strTable.append("<TD href='#' name='selected'");
//			strTable.append(" depth='' type='' id='' deptcode='' deptname='' deptsymbol='");
//			strTable.append("' deptchief='' doccode='' proxycode=''>");
// 2007.01.18 수신처 지정가능하도록 수정
			strTable.append(" depth='");
			strTable.append(strDepth);
			strTable.append("' type='' id='");
			strTable.append(strID);
			strTable.append("' deptcode='");
			strTable.append(strDeptCode);
			strTable.append("' deptname='");
			strTable.append(strDisplayName);
			strTable.append("' deptsymbol='");
			strTable.append(strSymbol);
			strTable.append("' deptchief='");
			strTable.append(strChiefName);
			strTable.append("' doccode='");
			strTable.append(strDocCode);
			strTable.append("' proxycode='");
			strTable.append(strProxyCode);
			strTable.append("' ispubdocrecip='Y' class='tb_left' iscertexist='N' sendingtype='0'>");
		}

		strTable.append(ldapOrg.getUcOrgFullName());
		strTable.append("</TD></TR></TABLE>");

		return strTable.toString();
	}
%>

<%

	String strConnectionIP = "";
	String strRootDN = "";
	String strRootName = "";
	String strPortNo = "";
	String strBaseDN = "";

	LDAPOrgManager ldapOrgManger = null;
	LDAPOrganizations ldapOrgs = null;

	String strOUCode = "";
	String strType = "";
	String strLDAPTag = "";
	String strSubLDAPTag = "";
	String strDepth = "";
	int    nDepth = 0;
%>

<%
	strType = request.getParameter("type");
	strOUCode = request.getParameter("oucode");
	strBaseDN = request.getParameter("basedn");
	strDepth = request.getParameter("depth");
	//strConnectionIP = "10.1.7.39";
	//strConnectionIP = "10.3.1.40";
	//strConnectionIP = "152.99.57.86";
	strConnectionIP = "pub.dir.go.kr";
	//strConnectionIP = "10.1.7.140"; 중앙행정
	//strConnectiopIP = "10.3.1.40";   시군구
	strRootDN = "o=Government of Korea,c=kr";
	strRootName = "Government of Korea";
	strPortNo = "389";

	// modify by kkang 2004.05.13 - LDAP parent OU Code 정보 불일치로 인한 정보 수정
	if (strDepth != null && strDepth.length() > 0)
	{
		//nDepth = Integer.parseInt(strDepth);
	}
	else
	{
		//nDepth = 0;
	}
	
	out.println("login.companycode: " + (String)session.getAttribute("login.companycode"));
	
	ldapOrgManger = new LDAPOrgManager(strConnectionIP, strRootDN, strRootName, strPortNo);
	
	System.out.println("ldapOrgManger: " + ldapOrgManger);
	
	ldapOrgs = ldapOrgManger.getSubLDAPOrg(strRootDN, null,
			   (ConnectionParam)session.getAttribute("application.orgconnectionparam"),
			   (String)session.getAttribute("login.companycode"));

if (ldapOrgs != null)
strSubLDAPTag = MakeSubTree(ldapOrgs, 0);

out.println(strSubLDAPTag);



%>

<BODY onload="">
</BODY>
</HTML>
