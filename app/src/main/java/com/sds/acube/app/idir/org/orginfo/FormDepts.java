package com.sds.acube.app.idir.org.orginfo;

/**
 * FormDepts.java
 * 2002-10-09
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.util.*;
import java.util.*;
import org.w3c.dom.*;

public class FormDepts 
{
	private LinkedList m_lFormDeptList = null;
	
	public FormDepts()
	{
		m_lFormDeptList = new LinkedList();
	}	
	
	/**
	 * 양식함 사용 부서 정보를 LinkedList로 얻음 
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lFormDeptList;
	}
	
	/**
	 * List에 양식사용 부서정보를 더함.
	 * @param FormDept 양식 부서  정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(FormDept formDept)
	{
		return m_lFormDeptList.add(formDept);
	}
	
	/**
	 * List에 양식사용 부서정보를 List 앞에 더함.
	 * @param FormDept 양식 부서  정보 
	 * @return boolean 성공 여부 
	 */
	public void addFirst(FormDept formDept)
	{
		m_lFormDeptList.addFirst(formDept);
	}
	
	/**
	 * 양식 사용 부서 리스트의 size
	 * @return int 부서의 수 
	 */ 
	public int size()
	{
		return m_lFormDeptList.size();
	}
	
	/**
	 * 선택된 양식사용 부서의 정보 
	 * @param  부서 index
	 * @return FormDept 부서 정보 
	 */
	public FormDept get(int index)
	{
		return (FormDept)m_lFormDeptList.get(index);
	}
	
	/**
	 * Returns Display Depth
	 * @param 부서 index
	 * @return int
	 */
	public int getDepth(int index)
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getDepth();
	}
	
	/**
	 * Returns 조직 코드  
	 * @param 부서 index
	 * @return String
	 */
	public String getOrgID(int index) 
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getOrgID();
	}

	/**
	 * Returns 조직명
	 * @param 부서 index
	 * @return String
	 */
	public String getOrgName(int index) 
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getOrgName();
	}
	
	/**
	 * Returns 타언어조직명
	 * @param 부서 index
	 * @return String
	 */
	public String getOrgOtherName(int index)
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getOrgOtherName();
	}
	
	/**
	 * Returns 상위 조직 ID
	 * @param 부서 index
	 * @return String
	 */
	public String getOrgParentID(int index)
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getOrgParentID();
	}
	
	/**
	 * Returns 양식 사용 정보
	 * @param 부서 index
	 * @return boolean
	 */
	public boolean getFormBoxInfo(int index)
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getFormBoxInfo();
	}
	
	/**
	 * 기본으로 선택되는 함인지 여부 반환.
	 * @param 부서 index
	 * @return int
	 */
	public int getIsSelected(int index)
	{
		FormDept formDept = (FormDept)m_lFormDeptList.get(index);
		return formDept.getIsSelected();	
	}
	
	/**
	 * 부서 양식 정보를 XML Document로 변환
	 * @return Document
	 */
	public Document toDocument()
	{
		DocumentGenerator 	documentGenerator = new DocumentGenerator();
						
		int nListLength = size();
		if (nListLength > 0)
		{
			if (documentGenerator.createNewDocument())
			{
				Element rootNode = documentGenerator.createRootNodeAndAppend("TreeDocument");
				
				for (int i = 0 ; i < nListLength ; i++)
				{
					FormDept formDept = get(i);
					
					Element formDeptNode = documentGenerator.createNodeAndAppend("TreeNode",rootNode, false, "");
					
					formDeptNode.setAttribute("id", formDept.getOrgID());
					formDeptNode.setAttribute("foldername", formDept.getOrgName());
					formDeptNode.setAttribute("folderothername", formDept.getOrgOtherName());
					formDeptNode.setAttribute("depth", Integer.toString(formDept.getDepth()));
					
					if (formDept.getIsSelected() > 0)
					{
						formDeptNode.setAttribute("isselected", Integer.toString(formDept.getIsSelected()));		
					}
				}
			}
		}
			
		return documentGenerator.getDocument();			
	}	
}
