package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.org.util.*;
import java.util.*;
import org.w3c.dom.*;

/**
 * UserBoxes.java 2002-10-14
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserBoxes 
{
	/**
	 */
	private DocumentGenerator 	m_documentGenerator = null;
	private LinkedList 			m_lUserBoxList = null;
	
	public UserBoxes()
	{
		m_lUserBoxList = new LinkedList();
	}
		
	/**
	 * List에 사용자 함 정보를 더함.
	 * @param userBox 사용자 함 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean add(UserBox userBox)
	{
		return m_lUserBoxList.add(userBox);
	}
	
	/**
	 * List에 사용자 함 정보들을 를 더함.
	 * @param 사용자 함 정보 
	 * @return boolean 성공 여부 
	 */
	public boolean addAll(UserBoxes userBoxes)
	{
		return m_lUserBoxList.addAll(userBoxes.toLinkedList());
	}
	
	/** 
	 * LinkedList로 변환
	 * @return LinkedList
	 */
	public LinkedList toLinkedList()
	{
		return m_lUserBoxList;
	}
	
	/**
	 * 함 정보 리스트의 size
	 * @return int 사용자 함의 수
	 */ 
	public int size()
	{
		return m_lUserBoxList.size();
	}
	
	/**
	 * 함 정보
	 * @param  index 함 index 
	 * @return UserBox
	 */
	public UserBox get(int index)
	{
		return (UserBox)m_lUserBoxList.get(index);
	}
	
	/**
	 * 사용자별 함 정보
	 * @param strUserUID 사용자 로그인 UID
	 * @return UserBox
	 */
	public UserBox getUserBoxByUID(String strUserUID)
	{
		UserBox userBox = null;
		
		if (m_lUserBoxList != null)
		{
			for (int i = 0 ; i < m_lUserBoxList.size() ; i++)
			{
				UserBox loopUserBox = (UserBox)m_lUserBoxList.get(i);
				if (loopUserBox.getUserUID().compareTo(strUserUID) == 0)
				{
					userBox = loopUserBox;	
				}
			}	
		}
		
		return userBox;
	}
		
	/**
	 * UserBoxes를 xml document로 변환 
	 * @return Document 
	 */
	public Document toDocument()
	{		
		Document 			document = null;
		int 				nSize = 0;
		
		m_documentGenerator = new DocumentGenerator();
		
		if (m_documentGenerator.createNewDocument())
		{		
			Element rootNode = m_documentGenerator.createRootNodeAndAppend("TreeDocument");
			
			nSize = size();
			if (nSize > 0)
			{
				for (int i = 0 ; i < nSize ; i++)
				{
					Element userBoxNode = m_documentGenerator.createNodeAndAppend("TreeUser",rootNode, false, "");
					UserBox userBox = get(i);	
					
					// make User Node
					userBoxNode.setAttribute("userid", userBox.getUserUID());
					userBoxNode.setAttribute("username", userBox.getUserName());
					userBoxNode.setAttribute("userothername", userBox.getUserOtherName());
					userBoxNode.setAttribute("compid", userBox.getCompID());
					userBoxNode.setAttribute("compname", userBox.getCompName());
					userBoxNode.setAttribute("compothername", userBox.getCompOtherName());
					userBoxNode.setAttribute("deptid", userBox.getDeptID());
					userBoxNode.setAttribute("deptname", userBox.getDeptName());
					userBoxNode.setAttribute("deptothername", userBox.getDeptOtherName());
					userBoxNode.setAttribute("reftype", Integer.toString(userBox.getRefType()));
					userBoxNode.setAttribute("title", userBox.getUserTitle());
					userBoxNode.setAttribute("othertitle", userBox.getUserOtherTitle());
					userBoxNode.setAttribute("institutionid", userBox.getInstitutionID());
					
					RoleCodes roleCodes = userBox.getRoleCodes();
					String	  strRoleCodes = "";
					if (roleCodes != null && roleCodes.size() > 0)
					{
						for (int j = 0 ; j < roleCodes.size(); j ++)
						{
							strRoleCodes += roleCodes.get(j).getRoleCode() + "^";	
						}
					}
					
					if (strRoleCodes != null && strRoleCodes.length() > 0)
					{
						userBoxNode.setAttribute("rolecode", strRoleCodes);
					}
					
					int nBoxesSize = userBox.getBoxesSize();
					if (nBoxesSize > 0) {
						for (int k = 0; k < nBoxesSize; k++)
							makeBoxList(userBoxNode, userBox.getBoxes(k));
					}
				}
			}
			
			document = m_documentGenerator.getDocument();
		}
		
		return document;
	}
	
	/**
	 * 함들을 xml document로 변환 
	 * @param userBoxNode  parent Element
	 * @param approvalUserBoxes    Approval User Boxes
	 * @return boolean 	성공 여부  
	 */
	public boolean makeBoxList(Element parentElement, Boxes boxes)
	{
		if (boxes != null && boxes.size() > 1)
		{
			
			Element boxNode = m_documentGenerator.createNodeAndAppend("TreeNode", parentElement,  false, "");
			Box		box = boxes.get(0);
			
			boxNode.setAttribute("id", box.getBoxID());
			boxNode.setAttribute("displayname", box.getDisplayName());
			boxNode.setAttribute("depth", "0");
			boxNode.setAttribute("dataurl", box.getDataURL());
			boxNode.setAttribute("parentid", box.getParentBoxID());
			
			if (box.getIsExpand() > 0)
			{
				boxNode.setAttribute("isexpand", Integer.toString(box.getIsExpand()));
			}
			
			if (box.getIsSelected() > 0)
			{
				boxNode.setAttribute("isselected", Integer.toString(box.getIsSelected()));		
			}
			
			if (!makeSubBoxList(box.getBoxID(), boxNode, boxes, 1))
				return false;
		}
		
		return true;
		
	}
	
	/**
	 * 하위 업무함 리스트를 추출하는 함수.
	 * @param parentID parent ID
	 * @param cabinetID Cabinet ID
	 * @param parentElement parentElement
	 * @param boxes Boxes
	 * @param depth Tree Depth
	 * @return true;
	 */
	private boolean makeSubBoxList(String parentID, Element parentElement, Boxes boxes, int depth) {
		
		// recursive end condition
		if ((parentID == null) || (parentID.length() == 0))
			return true;
		
		int count = 0;
		for (int i = 1 ; i < boxes.size(); i++) {
			Box box = boxes.get(i);
			if (parentID.equals(box.getParentBoxID()))
				count++;
		}
		
		if (count == 0)
			return true;
		
		// make cabinet tree
		for (int i = 1 ; i < boxes.size(); i++) {
			Box box = boxes.get(i);
			if (parentID.equals(box.getParentBoxID()) && parentID.equals(box.getBoxID()))
				return false;
			
			if (parentID.equals(box.getParentBoxID()) == true) {
				Element boxNode = m_documentGenerator.createNodeAndAppend("TreeNode", parentElement,  false, "");
				
				boxNode.setAttribute("id", box.getBoxID());
				boxNode.setAttribute("parentid", box.getParentBoxID());
				boxNode.setAttribute("displayname", box.getDisplayName());
				boxNode.setAttribute("depth", Integer.toString(depth));
				boxNode.setAttribute("dataurl",box.getDataURL());	
				
				if (box.getIsExpand() > 0)
				{
					boxNode.setAttribute("isexpand", Integer.toString(box.getIsExpand()));
				}
				
				if (box.getIsSelected() > 0)
				{
					boxNode.setAttribute("isselected", Integer.toString(box.getIsSelected()));		
				}
				
				if (box.getIsBizCategory() > 0)
				{
					boxNode.setAttribute("isbizcategory", Integer.toString(box.getIsBizCategory()));
					boxNode.setAttribute("businessid", box.getBusinessID());			
				}
				
				// sub cabinet tree
				if (makeSubBoxList(box.getBoxID(), boxNode, boxes, depth + 1) == false)
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * List에 사용자 함정보를 더함
	 * @param strCategoryID 그룹 ID
	 * @param userBox 사용자 함 정보 
	 * @return boolean 성공여부 
	 */
	public boolean appendCabient(String strCategoryID, Box box) {
		
		return appendCabient(strCategoryID, "", box);
	}
	
	/**
	 * List에 사용자 함정보를 더함
	 * @param strCategoryID 그룹 ID
	 * @param previousBoxID 이전 함 ID
	 * @param userBox 사용자 함 정보 
	 * @return boolean 성공여부 
	 */
	public boolean appendCabient(String strCategoryID, String previousBoxID, Box box) {
		
		boolean result = false;
		
		if (m_lUserBoxList == null)
			return false;
		
		for (int i = 0; i < m_lUserBoxList.size(); i++) {
			UserBox userBox = (UserBox) m_lUserBoxList.get(i);
			
			LinkedList boxesList = userBox.getBexes();
			if (boxesList != null) {
				for (int j = 0; j < boxesList.size(); j++) {
					Boxes boxes = (Boxes) boxesList.get(j);
					if ((boxes != null) && (boxes.size() > 0)) {
						if (strCategoryID.equals(boxes.get(0).getBoxID()) == true) {
							// 동일한 CategoryID 하위 삽입
							result = setCabeint(boxes, previousBoxID, box);
						}
					}
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * List에 Cabinet 정보를 더함
	 * @param boxes Boxes
	 * @param previousBoxID
	 * @param box Box
	 * @retrun boolean
	 */
	private boolean setCabeint(Boxes boxes, String previousID, Box box) {
		
		boolean result = false;
		
		if (boxes == null)
			return result;
		
		String parentID = box.getParentBoxID();
		String id = "";
		if ((previousID == null) || (previousID.length() == 0)) {
			Boxes siblingBoxes = new Boxes();
			
			for (int i = 0; i < boxes.size(); i++) {
				if (parentID.equals(boxes.get(i).getParentBoxID()) == true) {
					siblingBoxes.add(boxes.get(i));
				}
			}
			
			if (siblingBoxes.size() > 0) {
				id = siblingBoxes.get(siblingBoxes.size() - 1).getBoxID();
			} else {
				id = parentID;
			}
		} else {
			id = previousID;
		}
		
		for (int i = 0; i < boxes.size(); i++) {
			Box previousBox = boxes.get(i);
			if (previousBox.getBoxID().equals(id) == true) {
				if ((i + 1) >= (boxes.size())) {
					boxes.add(box);
				} else {
					boxes.add(i + 1, box);
				}
				
				result = true;
			} 
		}
		
		return result;
	}
	
	/**
	 * 사용자명 반환.
	 * @param  nIndex   사용자 index
	 * @return m_strUserName
	 */	
	public String getUserName(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getUserName();
	}
	
	/**
	 * 사용자 UID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserUID(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getUserUID();
	}
	
	/**
	 * 기관 코드 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getInstitutionID(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getInstitutionID();
	}
	
	/**
	 * 문서과 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return boolean
	 */
	public boolean getIsODCD(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getIsODCD();
	}
	
	/**
	 * 감사과 여부 반환.
	 * @param  nIndex   사용자 index
	 * @return boolean
	 */
	public boolean getIsInspection(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getIsInspection();
	}
	
	/**
	 * 부서명 반환.
	 * @param  nIndex   사용자 index
	 * @return m_strDeptName
	 */
	public String getDeptName(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getDeptName();
	}
	
	/**
	 * 역할 코드 반환.
	 * @param  nIndex   사용자 index
	 * @return RoleCodes
	 */
	public RoleCodes getRoleCodes(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getRoleCodes();
	}
	
	/**
	 * 참조 Type 반환.(겸직, 파견, 직무대리)
	 * @param  nIndex   사용자 index
	 * @return m_nRefType
	 */
	public int getRefType(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getRefType();	
	}	

	/**
	 * 부서 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getDeptID(int nIndex) 
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getDeptID();
	}
	
	/**
	 * 회사 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getCompID(int nIndex) 
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getCompID();
	}

	/**
	 * 회사 이름 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getCompName(int nIndex) 
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getCompName();
	}

	/**
	 * 사용자 결재함 ID 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserBoxID(int nIndex) 
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getUserBoxID();
	}
	
	/**
	 * 사용자 직책 반환.
	 * @param  nIndex   사용자 index
	 * @return String
	 */
	public String getUserTitle(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getUserTitle();
	}
	
	/**
	 * 트리에서 디폴트로 펼쳐질 사용자 설정.
	 * @param nIndex 사용자 index
	 * @param bIsDefaultUser 디폴트 사용자 여부
	 */
	public boolean getIsDefaultUser(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getIsDefaultUser();	
	}
	
	/**
	 * 타언어 사용자 이름 반환.
	 * @param nIndex 사용자 index
	 * @return String 
	 */
	public String getUserOtherName(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getUserOtherName();
	}
	
	/**
	 * 타언어 회사 이름 반환.
	 * @param nIndex 사용자 index
	 * @return String 
	 */
	public String getCompOtherName(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getCompOtherName();
	}
	
	/**
	 * 타언어 부서 이름 반환.
	 * @param nIndex 사용자 index
	 * @return String 
	 */
	public String getDeptOtherName(int nIndex)
	{
		UserBox userBox = (UserBox)m_lUserBoxList.get(nIndex);
		return userBox.getDeptOtherName();
	}
}
