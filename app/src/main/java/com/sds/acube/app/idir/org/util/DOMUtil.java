package com.sds.acube.app.idir.org.util;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import org.w3c.dom.*;
import java.io.*;

//import org.apache.xml.serialize.*;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class DOMUtil 
{
	/**
	 * save xml document at specified file path
	 */
	public static boolean saveDocument(Document document,
										 String strFilePath, 
										 String strMethod, 
										 String strEncoding)
	{
		if (strMethod.length() == 0)
			strMethod = "XML";
			
		if (strEncoding.length() == 0)
			strEncoding = "euc-kr";
			
		try
		{
			File			outputFile = new File(strFilePath);
			OutputFormat	outputFormat = new OutputFormat(strMethod, strEncoding,true); 
			FileWriter		fileWriter = new FileWriter(outputFile); 
			XMLSerializer   xmlSerializer = new XMLSerializer(fileWriter, outputFormat);
			
			xmlSerializer.asDOMSerializer();
			xmlSerializer.serialize(document.getDocumentElement());
		}
		catch(IOException e)
		{	
			return false;	
		}
		return true;
	}
	
	/**
	 * select single child node given tagname
	 */	
	public static Node selectSingleNode(Node parentNode, String strTagName)
	{
		Node 	 node = null;
		NodeList childNodeList = null;
		int  	 nNodeLength = 0;
		String 	 strNodeName = "";
		
		if (parentNode.hasChildNodes())
		{
			childNodeList = parentNode.getChildNodes();
				
			if (childNodeList != null)
			{
				nNodeLength = childNodeList.getLength();
									
				for (int i = 0 ; i < nNodeLength ; i++)
				{
					Node childNode = childNodeList.item(i);
					if (childNode != null)
					{
						strNodeName = childNode.getNodeName();
						if (strNodeName.compareTo(strTagName) == 0)
						{
							node = childNode;
						}		
					}
				}
			}
		}
		
		return node;
	}
	
	/**
	 * get node text value 
	 */
	public static String getTextNodeVal(Node node)
	{
		Node 	textNode = null;
		String 	strText = "";
		
		if (node != null)
		{
			if (node.hasChildNodes() && node.getChildNodes().getLength() == 1)
			{
				textNode = node.getFirstChild();
				if (textNode.getNodeType() == org.w3c.dom.Node.TEXT_NODE)
					strText = textNode.getNodeValue();
			}
		}
		
		return strText;
	}
	
	/**
	 * get CDATA node value
	 */
	public static String getCDATANodeVal(Node node)
	{
		Node cdataNode = null;
		String strText = "";
		
		if (node != null)
		{
			if (node.hasChildNodes() && node.getChildNodes().getLength() == 1)
			{
				cdataNode = node.getFirstChild();
				if (cdataNode.getNodeType() == org.w3c.dom.Node.CDATA_SECTION_NODE)
					strText = cdataNode.getNodeValue();
			}			
		}
		
		return strText;
	}
	
	/**
	 * get attribute value
	 */
	public static String getAttribute(Node node, String strItemName)
	{
		String strItemValue = "";
					
		NamedNodeMap namedNodeMap = node.getAttributes();
		if (namedNodeMap != null)
		{
			Node attributeNode = namedNodeMap.getNamedItem(strItemName);
			if (attributeNode != null)
				strItemValue = attributeNode.getNodeValue();
		}
				
		return strItemValue;
	}
	
	/**
	 * set Attribute value
	 * @param element(Element), strAttributeName(String), strAttributeValue(String) 
	 * @return boolean
	 */	
	public static void setAttribute(Element element, String strAttributeName,
										 String strAttributeValue)
	{
		element.setAttribute(strAttributeName, strAttributeValue);		
	}
}
