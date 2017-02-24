package com.sds.acube.app.idir.org.util;

/**
 * @author Organization
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;

public class DocumentGenerator 
{
	private Document 				m_document = null;
	private String   				m_strLastError = null;
	
	
	/**
	 * get last error message
	 */	
	public String getLastError()
	{
		return m_strLastError;
	}
	
	/**
	 * make formatted error message
	 */
	private void setLastError(String strDescription,
									 String strFunction,
									 String strErrorMessage)
	{
		String strErrorDescription = "";
		String strErrorFunction	   = "";
		String strExceptionMessage = "";
		
		if (strDescription.length() != 0)
			strErrorDescription = "[Description] " + strDescription + "\n";
			
		if (strFunction.length() != 0)
			strErrorFunction    = "[Fuction] " + strFunction + "\n";
			
		if (strErrorMessage.length() != 0)
			strExceptionMessage = "[Message] " + strErrorMessage + "\n";
		
		m_strLastError = strErrorDescription + strErrorFunction + strExceptionMessage;
			
	}
	
	/**
	 * create root node and append
	 */ 
	public Element createRootNodeAndAppend(String strTagName)
	{
		Element element = null;
		
		try
		{
			element = createNode(strTagName);
			
			if (element == null)		// fail to create element	
				return null;
				
			m_document.appendChild(element);
		}
		catch(DOMException e)
		{
			String strDescription = "Fail to create new root element and append.";
			String strFunction = "DocumentGenerator.createRootNodeAndAppend.appendTextChild(DOMException)";
			String strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return null;
		}
	
		return element;
	}
	
	
	/**
	 * create xml document
	 */
	public boolean createNewDocument()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			m_document = builder.newDocument();	
		}
		catch(ParserConfigurationException e)
		{
			String strDescription = "Fail to create new document.";
			String strFunction = "DocumentGenerator.createDocument.DocumentBuilder.newDocument(ParserConfigurationException)";
			String strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);		
				
			return false;
		}
		
		return true;
	}
	
	/**
	 * create element node
	 */
	public Element createNode(String strTagName)
	{
		Element element = null;
		
		try
		{
			element = m_document.createElement(strTagName);
		}
		catch(DOMException e)
		{
			String strDescription = "Fail to create new element.";
			String strFunction = "DocumentGenerator.createNode.m_document.createElement(DOMException)";
			String strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return null;	
		}
		
		return element;
	}
	
	/**
	 * create node and append (single value)
	 */
	public Element createNodeAndAppend(String 		strTagName,
										Element 	parentNode,
										boolean 	bIsSetText,
										String   	strText)
	{
		Element element = null;
		
		try
		{
			element = createNode(strTagName);
			
			if (element == null)		// fail to create element	
				return null;
				
			if (bIsSetText && (strText.length() != 0))
				element.appendChild(m_document.createTextNode(strText)); 
				
			parentNode.appendChild(element);
		}
		catch(DOMException e)
		{
			String strDescription = "Fail to create new element and append.";
			String strFunction = "DocumentGenerator.createNodeAndAppend.appendTextChild(DOMException)";
			String strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return null;
		}
	
		return element;
	}
	
	/**
	 * create node and append (mulit value)
	 */
	public Element createNodeAndAppend(String 	strTagName,
									    Element parentNode,
									    String 	strChildTagName,
										String 	strMultiText)
	{
		DocumentBuilderFactory 	factory = DocumentBuilderFactory.newInstance();
		Document				document = null;
		InputSource 			inputSource = null;
		Element 				element = null;
		NodeList				nodeList = null;
		String 					strDescription = "";
		String 					strFunction = "";
		String 					strMessage = "";
		boolean				bResult = false;
		
		try	
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			element = createNode(strTagName);
			if (element == null)		// fail to creat node
				return element;
			
			parentNode.appendChild(element);
				
			// load document from string
			inputSource = new InputSource(new StringReader(strMultiText));
			
			try
			{
				document = builder.parse(inputSource);				
			}
			catch(IOException e)
			{
				strDescription = "Fail to load document from string.";
				strFunction = "DocumentGenerator.createNodeAndAppend.DocumentBuilder.parse(IOException)";	
				strMessage = e.getMessage();
				setLastError(strDescription, strFunction, strMessage);
				
				return null;	
			}
			catch(SAXException e)
			{
				strDescription = "Fail to load document from string.";
				strFunction = "DocumentGenerator.createNodeAndAppend.DocumentBuilder.parse(SAXException)";	
				strMessage = e.getMessage();
				setLastError(strDescription, strFunction, strMessage);
				
				return null;	
			}
			
			nodeList = document.getElementsByTagName(strChildTagName);
			
			if (nodeList == null)
			{
				strDescription = "Fail to get element by tag name (" + strChildTagName + ")";
				strFunction = "DocumentGenerator.createNodeAndAppend.Document.getElementsByTagName";
				setLastError(strDescription, strFunction, "");
				
				return null; 
			}	
			
			bResult = appendNodeList(element, nodeList);
			if (!bResult)
				return null;
				
		}
		catch(ParserConfigurationException e)
		{
			strDescription = "Fail to creat new document for XML formatted text node";
			strFunction = "DocumentGenerator.createNodeAndAppend.factory.newDocumentBuilder";
			strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return null;
		}
		
		return element;	
	}
	
	/**
	 * copy node list and append node list to parent node (recursive function)
	 */
	public boolean appendNodeList(Element parentNode, NodeList nodeList)
	{
		Element element = null;
		Node 	node = null;
		int 	nListLength = 0;
		int 	nNodeType = 0;
		
		
		if (nodeList == null)				// check null pointer
			return false;
			
		nListLength = nodeList.getLength();
		if (nListLength == 0)
			return true;
			
		for (int i = 0 ; i < nListLength ; i++)
		{
			node = nodeList.item(i);
			nNodeType = node.getNodeType();
			
			switch(nNodeType)
			{
			case org.w3c.dom.Node.ELEMENT_NODE:
					element = m_document.createElement(node.getNodeName());
					
					// copy attributes
					NamedNodeMap namedNodeMap = node.getAttributes();
					
					if (namedNodeMap != null)
					{
						int nAttCount = namedNodeMap.getLength();
						for (int j = 0 ; j < nAttCount ; j++)
						{
							Node attributeNode = namedNodeMap.item(j);
							element.setAttribute(attributeNode.getNodeName(), attributeNode.getNodeValue());
						} 
					}
					
					// recurive child node
					if (node.hasChildNodes())
					{
						NodeList childNodeList = node.getChildNodes();
						appendNodeList(element, childNodeList);
					}
					
					parentNode.appendChild(element);
					
					break;
			case org.w3c.dom.Node.TEXT_NODE:
					parentNode.appendChild(m_document.createTextNode(node.getNodeValue()));
					break;
			case org.w3c.dom.Node.CDATA_SECTION_NODE:
					parentNode.appendChild(m_document.createCDATASection(node.getNodeValue()));
					break;
			case org.w3c.dom.Node.COMMENT_NODE:
					parentNode.appendChild(m_document.createComment(node.getNodeValue()));
					break;
			default:
					String strDescription = "Fail to get correct data type.";
					String strFunction = "DocumentGenerator.appendNodeList.getNodeType (NodeType :"+ nNodeType +")";
					setLastError(strDescription, strFunction, ""); 
					return false;	
			}	
		}			
		return true;	
	} 
	
	/**
	 * get xml document
	 */
	public Document getDocument()
	{
		if (m_document != null)
			return m_document;
		else
			return null;
	}
	

	
	/**
	 * load xml document from input stream
	 */
	public boolean loadDocumentStream(String strInputStream)
	{
		DocumentBuilderFactory 	factory = null;
		DocumentBuilder 		builder = null;
		InputSource 			inputSource = null;
		String 					strDescription = "";
		String 					strFunction = "";
		String 	    			strMessage = "";
		
		// check basic condition
	    if (strInputStream.length() == 0)
	    {
	    	strDescription = "Empty Stream.";
	    	strFunction = "DocumentGenerator.loadDocumentStrem";
	    	setLastError(strDescription, strFunction, "");
	    	
	    	return false;
	    }
	    
	
		factory = DocumentBuilderFactory.newInstance();	
		
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e)
		{
			strDescription = "Fail to create new document builder.";
			strFunction = "DocumentGenerator.loadDocumentStrem.DocumentBuilderFactory.newDocumentBuilder(ParserConfigurationException)";
			strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return false;
		}
		
		inputSource = new InputSource(new StringReader(strInputStream));
		
		try
		{
			m_document = builder.parse(inputSource);
		}
		catch(IOException e)
		{
			strDescription = "Fail to load document.";
			strFunction = "DocumentGenerator.loadDocumentStrem.DocumentBuilder.parse(IOException)";
			strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return false;
		}
		catch(SAXException e)
		{
			strDescription = "Fail to load document.";
			strFunction = "DocumentGenerator.loadDocumentStrem.DocumentBuilder.parse(SAXException)";
			strMessage = e.getMessage();
			setLastError(strDescription, strFunction, strMessage);
			
			return false;
		}
		
		return true;
	}
}
