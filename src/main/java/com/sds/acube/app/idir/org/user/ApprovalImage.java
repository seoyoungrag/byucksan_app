package com.sds.acube.app.idir.org.user;

/**
 * ApprovalImage.java
 * 2002-10-15
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
 * Class Name  : ApprovalImage.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.user.ApprovalImage.java
 */
public class ApprovalImage 
{
	private int 			m_nStampOrSign = 0;
	private String 		m_strApprovalImageType = "bmp";
	private String 		m_strUserUID = "";
	private byte			m_byApprovalImage[] = null;
	/**
	 */
	private ErrorMessage 	m_lastError = new ErrorMessage();
	
	/**
	 * Get Last Error
	 * @return String
	 */
	public String getLastError()
	{
		return m_lastError.getMessage();
	}
	
	/**
	 * Sets User UID
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID)
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * Sets 결재 Image.
	 * @param byApprovalImage The m_byApprovalImage to set
	 */
	public void setApprovalImage(byte[] byApprovalImage) 
	{
		m_byApprovalImage = byApprovalImage;
	}
	
	/**
	 * Sets 결재 Image Type
	 * @param strApprovalImageType The m_strApprovalImageType to set
	 */
	public void setApprovalImageType(String strApprovalImageType)
	{
		m_strApprovalImageType = strApprovalImageType;
	}

	/**
	 * Sets 결재 사인 여부.
	 * @param nStampOrSign The m_nStampOrSign to set
	 */
	public void setStampOrSign(int nStampOrSign) 
	{
		m_nStampOrSign = nStampOrSign;
	}

	/**
	 * Returns 결재 Image Type
	 * @return String
	 */
	public String getApprovlImageType()
	{
		return m_strApprovalImageType;	
	}
		
	/**
	 * Returns 결재 Image.
	 * @return byte[]
	 */
	public byte[] getApprovalImage() 
	{
		return m_byApprovalImage;
	}

	/**
	 * Returns the m_nStampOrSign.
	 * @return int
	 */
	public int getStampOrSign() 
	{
		return m_nStampOrSign;
	}
	
	/**
	 * Returns the m_strUserUID.
	 * @return String
	 */
	public String getUserUID()
	{
		return m_strUserUID;
	}
	
	/**
	 * Save Approval Image
	 * @param strFilePath 이미지가 저장된 파일 경로 
	 * @return String
	 */
	public String save(String strDirectoryPath)
	{
		FileOutputStream 	outStream = null;
		String 				strFilePath = "";
		String 				strFileName = "";
		GUID				guid = new GUID();
						
		try
		{	
			if (m_byApprovalImage != null)
			{
				strFileName = guid.toString() + "." + m_strApprovalImageType;
				strFilePath = strDirectoryPath + strFileName;
				outStream = new FileOutputStream(strFilePath);
				outStream.write(m_byApprovalImage);
				outStream.flush();
				outStream.close();
			}
		}
		catch(FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to save File.",
								   "ApprovalImage.save.new FileOutputStream",
								   e.getMessage());	
			return "";
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to write File.",
								   "ApprovalImage.save.FileOutputStream.write",
								   e.getMessage());	
			return "";			
		}
				
		return strFileName;
	}
}
