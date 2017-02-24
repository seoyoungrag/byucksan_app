package com.sds.acube.app.idir.org.orginfo;

/**
 * OrgImage.java
 * 2002-10-17
 * 
 * 
 * 
 * @author kkang
 * @version 1.0.0.0
 *
 * Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.*;
import java.io.*;

/**
 * Class Name  : OrgImage.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.orginfo.OrgImage.java
 */
public class OrgImage implements Serializable
{
	private String 			m_strImageID = "";
	private String 			m_strImageName = "";
	private String 			m_strOrgID = "";
	private int 			m_nImageType = 0;
	private int				m_nImageClassification = 0;
	private String 			m_strRegistrationDate = "";
	private String 			m_strIssueReason = "";
	private String 			m_strManagedOrg = "";
	private String 			m_strRegistrationRemarks = "";
	private boolean 		m_bDisuseYN = false;
	private String 			m_strDisuseDate = "";
	private String 			m_strDisuseReason = "";
	private String 			m_strDisuseRemarks = "";
	private String 			m_strImageFileType = "";
	private byte			m_byImage[] = null;
	private int				m_nSizeWidth = 0;
	private int 			m_nSizeHeight = 0;
	private int 			m_nImageOrder = 0;
	
	/**
	 */
	private ErrorMessage 	m_lastError = new ErrorMessage();

	/**
	 * 이미지 순서 설정.
	 * @param nImageOrder 이미지 순서
	 */
	public void setImageOrder(int nImageOrder)
	{
		m_nImageOrder = nImageOrder;
	}
	
	/**
	 * 이미지 사이즈 가로 설정.
	 * @param nSizeWidth 이미지 사이즈 가로
	 */
	public void setSizeWidth(int nSizeWidth)
	{
		m_nSizeWidth = nSizeWidth;
	}
	
	/**
	 * 이미지 사이즈 세로 설정.
	 * @param nSizeHeight 이미지 사이즈 세로 
	 */
	public void setSizeHeight(int nSizeHeight)
	{
		m_nSizeHeight = nSizeHeight;
	}
	
	/**
	 * 폐기 여부 설정.
	 * @param bDisuseYN The m_bDisuseYN to set
	 */
	public void setDisuseYN(boolean bDisuseYN) 
	{
		m_bDisuseYN = bDisuseYN;
	}

	/**
	 * 조직 이미지 설정.
	 * @param byImage The m_byImage to set
	 */
	public void setImage(byte[] byImage) 
	{
		m_byImage = byImage;
	}

	/**
	 * 이미지 구분 설정(청인(1)/직인(2)/특수관인(3))
	 * @param nImageClassification The m_nImageClassification to set
	 */
	public void setImageClassification(int nImageClassification) 
	{
		m_nImageClassification = nImageClassification;
	}

	/**
	 * 이미지 종류 설정(서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)).
	 * @param nImageType The m_nImageType to set
	 */
	public void setImageType(int nImageType) 
	{
		m_nImageType = nImageType;
	}

	/**
	 * 폐기일 설정.
	 * @param strDisuseDate The m_strDisuseDate to set
	 */
	public void setDisuseDate(String strDisuseDate) 
	{
		m_strDisuseDate = strDisuseDate;
	}

	/**
	 * 폐기 사유 설정.
	 * @param strDisuseReason The m_strDisuseReason to set
	 */
	public void setDisuseReason(String strDisuseReason) 
	{
		m_strDisuseReason = strDisuseReason;
	}

	/**
	 * 폐기 비고 설정.
	 * @param strDisuseRemarks The m_strDisuseRemarks to set
	 */
	public void setDisuseRemarks(String strDisuseRemarks) 
	{
		m_strDisuseRemarks = strDisuseRemarks;
	}

	/**
	 * 이미지 파일 확장자 설정.
	 * @param strImageFileType The m_strImageFileType to set
	 */
	public void setImageFileType(String strImageFileType) 
	{
		m_strImageFileType = strImageFileType;
	}

	/**
	 * 이미지 ID 설정.
	 * @param strImageID The m_strImageID to set
	 */
	public void setImageID(String strImageID) 
	{
		m_strImageID = strImageID;
	}

	/**
	 * 이미지명 설정.
	 * @param strImageName The m_strImageName to set
	 */
	public void setImageName(String strImageName) 
	{
		m_strImageName = strImageName;
	}

	/**
	 * 교부 사유 설정.
	 * @param strIssueReason The m_strIssueReason to set
	 */
	public void setIssueReason(String strIssueReason) 
	{
		m_strIssueReason = strIssueReason;
	}

	/**
	 * 관리 부서 설정.
	 * @param strManagedOrg The m_strManagedOrg to set
	 */
	public void setManagedOrg(String strManagedOrg) 
	{
		m_strManagedOrg = strManagedOrg;
	}

	/**
	 * 조직 ID 설정.
	 * @param strOrgID The m_strOrgID to set
	 */
	public void setOrgID(String strOrgID) 
	{
		m_strOrgID = strOrgID;
	}

	/**
	 * 등록일 설정.
	 * @param strRegistrationDate The m_strRegistrationDate to set
	 */
	public void setRegistrationDate(String strRegistrationDate) 
	{
		m_strRegistrationDate = strRegistrationDate;
	}

	/**
	 * 교부 사유 설정.
	 * @param strRegistrationRemarks The m_strRegistrationRemarks to set
	 */
	public void setRegistrationRemarks(String strRegistrationRemarks)
	{
		m_strRegistrationRemarks = strRegistrationRemarks;
	}

	/**
	 * 이미지 사이즈 가로 반환.
	 * @return int
	 */
	public int getSizeWidth()
	{
		return m_nSizeWidth;	
	}
	
	/**
	 * 이미지 사이즈 세로 반환.
	 * @return int
	 */
	public int getSizeHeight()
	{
		return m_nSizeHeight;
	}
	
	/**
	 * 폐기여부 반환.
	 * @return boolean
	 */
	public boolean getDisuseYN() 
	{
		return m_bDisuseYN;
	}

	/**
	 * 조직 이미지 반환.
	 * @return byte[]
	 */
	public byte[] getImage() 
	{
		return m_byImage;
	}

	/**
	 * 이미지 구분 반환(청인(1)/직인(2)/특수관인(3)).
	 * @return int
	 */
	public int getImageClassification() 
	{
		return m_nImageClassification;
	}

	/**
	 * 이미지 종류 반환(서명인(0)/관인(1)/서명생략인(2)/관인생략인(3)/시행생략인(4)).
	 * @return int
	 */
	public int getImageType() 
	{
		return m_nImageType;
	}

	/**
	 * 폐기일 반환.
	 * @return String
	 */
	public String getDisuseDate() 
	{
		return m_strDisuseDate;
	}

	/**
	 * 페기 사유 반환.
	 * @return String
	 */
	public String getDisuseReason() 
	{
		return m_strDisuseReason;
	}

	/**
	 * 폐기 비고 반환.
	 * @return String
	 */
	public String getDisuseRemarks() 
	{
		return m_strDisuseRemarks;
	}

	/**
	 * 이미지 파일 확장자 반환.
	 * @return String
	 */
	public String getImageFileType() 
	{
		return m_strImageFileType;
	}

	/**
	 * 이미지 ID 반환.
	 * @return String
	 */
	public String getImageID() 
	{
		return m_strImageID;
	}

	/**
	 * 이미지명 반환.
	 * @return String
	 */
	public String getImageName() 
	{
		return m_strImageName;
	}

	/**
	 * 교부 사유 반환.
	 * @return String
	 */
	public String getIssueReason() 
	{
		return m_strIssueReason;
	}

	/**
	 * 관리 부서 반환.
	 * @return String
	 */
	public String getManagedOrg() 
	{
		return m_strManagedOrg;
	}

	/**
	 * 조직 ID 반환.
	 * @return String
	 */
	public String getOrgID() 
	{
		return m_strOrgID;
	}

	/**
	 * 이미지 등록일 반환.
	 * @return String
	 */
	public String getRegistrationDate() 
	{
		return m_strRegistrationDate;
	}

	/**
	 * 등록 비고 반환.
	 * @return String
	 */
	public String getRegistrationRemarks() 
	{
		return m_strRegistrationRemarks;
	}
	
	/**
	 * 이미지 순서 반환.
	 * @param int
	 */
	public int getImageOrder()
	{
		return m_nImageOrder;
	}
	
	/**
	 * Save Organization Image
	 * @param strFilePath 이미지가 저장될 디렉토리 경로 
	 * @return String
	 */
	public String save(String strDirectoryPath)
	{
		FileOutputStream 	outStream = null;
		String 				strFilePath = "";
		String 				strFileName = "";
		GUID				guid = new GUID();
				
		strFileName = guid.toString() + "." + m_strImageFileType;
		strFilePath = strDirectoryPath + strFileName;
			
		try
		{	
			outStream = new FileOutputStream(strFilePath);
			
			if (m_byImage != null)
				outStream.write(m_byImage);
			
			outStream.close();
		}
		catch(FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to save File.",
								   "OrgImage.save.new FileOutputStream",
								   e.getMessage());	
								 
			return "";
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to write File.",
								   "OrgImage.save.FileOutputStream.write",
								   e.getMessage());	
								   
			return "";			
		}
			
		return strFileName;
	}
	
	/**
	 * 이미지 load
	 * @param strImageFilePath 이미지 파일 Path
	 * @return boolean
	 */
	public boolean load(String strImageFilePath)
	{
		FileInputStream inStream = null;
		boolean 		bReturn = false;
		String 			strImageType = "bmp";
		File	 		file = null;
		byte			byImage[] = null;
		
		try
		{
			int nIndex = strImageFilePath.lastIndexOf('.');
			if (nIndex != -1)
				strImageType = strImageFilePath.substring(nIndex + 1);
				
			file = new File(strImageFilePath);
			inStream = new FileInputStream(file);
		}
		catch(FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to create file input stream.",
									"OrgImage.loadImage.new.FileInputStream",
									e.getMessage());
			return bReturn;		
		}	
		
		try
		{
			byImage = new byte[(int)file.length()];
			inStream.read(byImage);
			
			// load image
			m_byImage = new byte[(int)file.length()];
			System.arraycopy(byImage, 0, m_byImage, 0, (int)file.length());
			m_strImageFileType = strImageType;
			
			inStream.close();
			bReturn = true;
		}		
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to load image.",
								   "OrgImage.loadImage.",
								   e.getMessage());
								   
			return bReturn;
		}		
		return bReturn;
	}
	
}
