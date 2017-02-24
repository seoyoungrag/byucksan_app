package com.sds.acube.app.idir.org.user;

/**
 * UserImage.java
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
import com.sds.acube.app.idir.common.GUID;
import java.io.*;

/**
 * Class Name  : UserImage.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.user.UserImage.java
 */
public class UserImage implements Serializable
{
	private String 		m_strUserUID = "";
	private String 		m_strPictureType = "";
	private String 		m_strStampType = "";
	private String 		m_strSignType = "";
	private byte			m_byPictureImage[] = null;
	private byte			m_byStampImage[] = null;
	private byte			m_bySignImage[] = null;
	private int 			m_nStampOrSign = 0;		// 0 : 도장 1 : 사인
    /**
	 */
    private ErrorMessage	m_lastError = new ErrorMessage();
	
	/**
	 * Sets 사진 이미지.
	 * @param byPictureImage The m_byPictureImage to set
	 */
	public void setPictureImage(byte[] byPictureImage) 
	{
		m_byPictureImage = byPictureImage;
	}

	/**
	 * Sets 사인 이미지.
	 * @param bySignImage The m_bySignImage to set
	 */
	public void setSignImage(byte[] bySignImage) 
	{
		m_bySignImage = bySignImage;
	}

	/**
	 * Sets 도장 이미지.
	 * @param byStampImage The m_byStampImage to set
	 */
	public void setStampImage(byte[] byStampImage) 
	{
		m_byStampImage = byStampImage;
	}

	/**
	 * Sets 도장 사인 사용 여부 .
	 * @param nStampOrSign The m_nStampOrSign to set
	 */
	public void setStampOrSign(int nStampOrSign) 
	{
		m_nStampOrSign = nStampOrSign;
	}

	/**
	 * Sets 사진 Type.
	 * @param strPictureType The m_strPictureType to set
	 */
	public void setPictureType(String strPictureType) 
	{
		m_strPictureType = strPictureType;
	}

	/**
	 * Sets 사인 Type.
	 * @param strSignType The m_strSignType to set
	 */
	public void setSignType(String strSignType) 
	{
		m_strSignType = strSignType;
	}


	/**
	 * Sets 도장 Type.
	 * @param strStampType The m_strStampType to set
	 */
	public void setStampType(String strStampType) 
	{
		m_strStampType = strStampType;
	}

	/**
	 * Sets 사용자UID.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}

	/**
	 * Returns 사진이미지.
	 * @return byte[]
	 */
	public byte[] getPictureImage() 
	{
		return m_byPictureImage;
	}

	/**
	 * Returns 사인이미지.
	 * @return byte[]
	 */
	public byte[] getSignImage() 
	{
		return m_bySignImage;
	}

	/**
	 * Returns 도장이미지.
	 * @return byte[]
	 */
	public byte[] getStampImage() 
	{
		return m_byStampImage;
	}

	/**
	 * Returns 사인 도장 여부 .
	 * @return int
	 */
	public int getStampOrSign() 
	{
		return m_nStampOrSign;
	}

	/**
	 * Returns 사진 Type.
	 * @return String
	 */
	public String getPictureType() 
	{
		return m_strPictureType;
	}

	/**
	 * Returns 사인 Type.
	 * @return String
	 */
	public String getSignType() 
	{
		return m_strSignType;
	}


	/**
	 * Returns 도장 Type.
	 * @return String
	 */
	public String getStampType() 
	{
		return m_strStampType;
	}

	/**
	 * Returns 사용자 ID.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
	
	/**
	 * 이미지 load
	 * @param nType 이미지 Type 0 : 사진  1 : 도장 2 : 사인 
	 * @param strImageFilePath  이미지 파일 path
	 * @return boolean   load성공여부
	 */
	public boolean load(int nType, String strImageFilePath)
	{
		File 			file = null;
		FileInputStream inStream = null;
		boolean 		bReturn = false;
		byte	 		byImage[] = null;
		String 			strImageType = "bmp";
		
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
									"UserImage.loadImage.new.FileInputStream",
									e.getMessage());
			return bReturn;		
		}	
		
		try
		{
			byImage = new byte[(int)file.length()];
			inStream.read(byImage);
			
			switch(nType)
			{
				case 0 : m_byPictureImage = new byte[(int)file.length()];
						  System.arraycopy(byImage, 0, m_byPictureImage, 0, (int)file.length());
						  setPictureType(strImageType);
						  break;
						  
				case 1 : m_byStampImage = new byte[(int)file.length()];
						  System.arraycopy(byImage, 0, m_byStampImage, 0, (int)file.length());
						  setStampType(strImageType);
						  break;
						  
				case 2 : m_bySignImage = new byte[(int)file.length()];
						 System.arraycopy(byImage, 0, m_bySignImage, 0, (int)file.length());
						 setSignType(strImageType);
						  break;
			}	
		
			inStream.close();
			bReturn = true;
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to load image.",
								   "UserImage.loadImage.",
								   e.getMessage());
								   
			return bReturn;
		}
			
		return bReturn; 
	}
	
	/**
	 * 이미지 save
	 * @param nType 이미지 Type 0 : 사진  1 : 도장 2 : 사인 
	 * @param strDirectoryPath  이미지가 저장될 디렉토리 경로
	 * @return String  
	 */
	public String save(int nType, String strDirectoryPath)
	{
		FileOutputStream 	outStream = null;
		String 				strFilePath = "";
		String 				strFileName = "";
		GUID				guid = new GUID();
				
		try
		{
			switch(nType)
			{
				case 0 :  // 사진 
						  if (m_byPictureImage!= null && m_byPictureImage.length > 0)
						  {
						  	strFileName = guid.toString() + "." + m_strPictureType;
						  	strFilePath = strDirectoryPath + strFileName;
						  	outStream = new FileOutputStream(strFilePath);
						  	outStream.write(m_byPictureImage);
						  	outStream.close();
						  }
						  break;
						  
				case 1 : // 도장 
						  if (m_byStampImage != null && m_byStampImage.length > 0)
						  {
						 	strFileName = guid.toString() + "." + m_strStampType;
						 	strFilePath = strDirectoryPath + strFileName;
						 	outStream = new FileOutputStream(strFilePath);
						 	outStream.write(m_byStampImage);
						 	outStream.close();
						  }
						 break;
						 
				case 2 : // 사진
				 		 if (m_bySignImage != null && m_bySignImage.length > 0)
				 		 {
				 		 	strFileName = guid.toString() + "." + m_strSignType;
						 	strFilePath = strDirectoryPath + strFileName;
						 	outStream = new FileOutputStream(strFilePath);
						 	outStream.write(m_bySignImage);
						 	outStream.close();
				 		 }
						 break;
			}	
		}
		catch(FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to save File.",
								   "UserImage.save.new FileOutputStream(Image Type : " + nType + ")",
								   e.getMessage());	
								 
			return "";
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to write File.",
								   "UserImage.save.FileOutputStream.write(Image Type : " + nType + ")",
								   e.getMessage());	
								   
			return "";			
		}
			
		return strFileName;
	}
	
	/**
	 * 이미지를 UID로 저장 
	 * @param nType 이미지 Type 0 : 사진  1 : 도장 2 : 사인 
	 * @param strDirectoryPath  이미지가 저장될 디렉토리 경로
	 * @return String  
	 */
	public String saveAsUID(int nType, String strDirectoryPath)
	{
		FileOutputStream 	outStream = null;
		String 				strFilePath = "";
		String 				strFileName = "";
				
		try
		{
			switch(nType)
			{
				case 0 :  // 사진 
						  if (m_byPictureImage!= null && m_byPictureImage.length > 0)
						  {
						  	strFileName = m_strUserUID + "." + m_strPictureType;
						  	strFilePath = strDirectoryPath + strFileName;
						  	outStream = new FileOutputStream(strFilePath);
						  	outStream.write(m_byPictureImage);
						  	outStream.close();
						  }
						  break;
						  
				case 1 : // 도장 
						  if (m_byStampImage != null && m_byStampImage.length > 0)
						  {
						 	strFileName = m_strUserUID + "." + m_strStampType;
						 	strFilePath = strDirectoryPath + strFileName;
						 	outStream = new FileOutputStream(strFilePath);
						 	outStream.write(m_byStampImage);
						 	outStream.close();
						  }
						 break;
						 
				case 2 : // 사진
				 		 if (m_bySignImage != null && m_bySignImage.length > 0)
				 		 {
				 		 	strFileName = m_strUserUID + "." + m_strSignType;
						 	strFilePath = strDirectoryPath + strFileName;
						 	outStream = new FileOutputStream(strFilePath);
						 	outStream.write(m_bySignImage);
						 	outStream.close();
				 		 }
						 break;
			}	
		}
		catch(FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to save File.",
								   "UserImage.saveAsUID.new FileOutputStream(Image Type : " + nType + ")",
								   e.getMessage());	
								 
			return "";
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to write File.",
								   "UserImage.saveAsUID.FileOutputStream.write(Image Type : " + nType + ")",
								   e.getMessage());	
								   
			return "";			
		}
			
		return strFileName;
	}
	
}
