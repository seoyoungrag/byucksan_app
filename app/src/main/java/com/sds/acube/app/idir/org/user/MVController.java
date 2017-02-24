package com.sds.acube.app.idir.org.user;

/**
 * MVController.java
 * 2002-10-12
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.db.*;
import com.sds.acube.app.idir.org.util.*;
import java.util.*;
import org.w3c.dom.*;

public class MVController extends MultiValueController
{
	public static final int MV_SERVER = 0;
	public static final int MV_ROLE = 1;
	public static final int MV_MAIL = 2;
	
	public static final String[] m_strMVColumns =
	{
		"SERVERS",
		"ROLE_CODE",
		"EMAIL"
	};
	
	/**
	 * Get MultiValue Type
	 * @param strColumnName(String)
	 * @return int
	 */
	public int getMVType(String strColumnName)
	{
		for (int i = 0 ; i < m_strMVColumns.length ; i++)
		{
			if (m_strMVColumns[i].compareTo(strColumnName) == 0)
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Transfer MultiValue String to class list
	 * @param strMVString(String), int nMVType 
	 * @return LinkedList
	 */
	public LinkedList getMVList(String strMVString, int nMVType)
	{
		LinkedList 	transList = null;
		Document 	document = null;
	
		if (strMVString == null)
			return null;
				
		document = loadMVString(strMVString);
		if (document == null)
			return null;
			
		switch(nMVType)
		{
			case MV_SERVER:
						transList = makeServerList(document);	
						break;
			case MV_ROLE:
						transList = makeRoleCodeList(document);
						break;

		}
		return transList;		
	}
	
	/**
	 * Make Server List
	 * @param document(Document)
	 * @return LinkedList
	 */
	private LinkedList makeServerList(Document document)
	{
		LinkedList	serverlist = null;
		NodeList 	serverNodeList = null;
		int 		nNodeLength = 0;
		
		if (document == null)
			return null;
			
		serverNodeList = document.getElementsByTagName("SERVER");
		
		if ((serverNodeList == null) || (serverNodeList.getLength() == 0))
			return null;
		
		serverlist = new LinkedList();
									
		nNodeLength = serverNodeList.getLength();
		
		for (int i = 0 ; i < nNodeLength; i++)
		{
			Node 	node = serverNodeList.item(i);
			Server 	server = new Server();
			
			// set server type
			server.setServerType(DOMUtil.getAttribute(node,"TYPE"));
		
			// set server name
			Node serverNameNode = DOMUtil.selectSingleNode(node, "NAME");
			String strServerName = DOMUtil.getTextNodeVal(serverNameNode);
			server.setServerName(strServerName); 
			
			serverlist.add(server);
			
		}
		
		return serverlist;
	}
	
	/**
	 * Make role list
	 * @param document(Document)
	 * @return LinkedList
	 */
	private LinkedList makeRoleCodeList(Document document)
	{
		LinkedList	roleCodelist = null;
		NodeList 	roleNodeList = null;
		int 		nNodeLength = 0;
		
		if (document == null)
			return null;
			
		roleNodeList = document.getElementsByTagName("ROLE");
		
		if ((roleNodeList == null) || (roleNodeList.getLength() == 0))
			return null;
		
		roleCodelist = new LinkedList();
									
		nNodeLength = roleNodeList.getLength();
		
		for (int i = 0 ; i < nNodeLength; i++)
		{
			Node 		node = roleNodeList.item(i);
			RoleCode 	roleCode = new RoleCode();
			
			// set role code
			roleCode.setRoleCode(DOMUtil.getTextNodeVal(node));
			
			roleCodelist.add(roleCode);	
		}
		
		return roleCodelist;
	}
	
	/**
	 * get Role Code List
	 * @param strMVString RoleCode MultiString
	 * @return RoleCodes
	 */
	public static RoleCodes getRoleCodes(String strMVString)
	{
		RoleCodes 	roleCodes = null;
		String[]	strSplitString = null;
		String 		strDelimiter = "^"; 
		int 		nLength = 0;
		
		roleCodes = new RoleCodes();
		
		if (strMVString == null)
			return null;
			
		strSplitString = splitString(strMVString, strDelimiter);
			
		if ((strSplitString != null) && (strSplitString.length > 0))
		{
			for (int i = 0 ; i < strSplitString.length ; i++)
			{
				RoleCode roleCode = new RoleCode();
				roleCode.setRoleCode(strSplitString[i]);
				
				roleCodes.add(roleCode);
			}
			
		}
						
		return roleCodes;		
	}
	
	/**
	 * get Server List
	 * @param strMVString Server MultiString
	 * @return Servers
	 */
	public static Servers getServers(String strMVString)
	{
		String[] 	strSplitString = null; 
		Servers 	servers = null;
		String 		strOuterDelimiter = "^";
		String 		strInnerDelimiter = ":";
		int		nLength = 0;

		servers = new Servers();
		
		if (strMVString == null)
			return null;
			
		strSplitString = splitString(strMVString, strOuterDelimiter);
		
		if ((strSplitString != null) && (strSplitString.length > 0))
		{
			for (int i = 0 ; i < strSplitString.length ; i++)
			{
				Server server = new Server();
				String[] strSplitServer = splitString(strSplitString[i], strInnerDelimiter);
				
				if ((strSplitServer != null) && (strSplitServer.length == 2))
				{
					server.setServerType(strSplitServer[0]);
					server.setServerName(strSplitServer[1]);
				}
				
				servers.add(server);
			}
		}
		
		return servers;	
	}
	
	
}
