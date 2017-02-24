package com.sds.acube.app.idir.org.user;

import com.sds.acube.app.idir.org.common.*;
import com.sds.acube.app.idir.common.GUID;
import java.io.*;

/**
 * UserPassword.java 2002-10-29
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class UserPassword implements Serializable
{
	private String 		m_strUserUID = "";
	private String 		m_strSystemPassword = "";
	private int 			m_nPasswordType = 0;
	private String 		m_strApprovalPassword = "";
	private byte			m_byFingerPrintInfo[] = null;
	/**
	 */
	private ErrorMessage	m_lastError = new ErrorMessage();
	
	/**
	 * 지문정보 설정.
	 * @param byFingerPrintInfo The m_byFingerPrintInfo to set
	 */
	public void setFingerPrintInfo(byte[] byFingerPrintInfo) 
	{
		m_byFingerPrintInfo = byFingerPrintInfo;
	}

	/**
	 * Password Type 설정.
	 * @param nPasswordType The m_nPasswordType to set
	 */
	public void setPasswordType(int nPasswordType) 
	{
		m_nPasswordType = nPasswordType;
	}

	/**
	 * 결재 Password 설정.
	 * @param strApprovalPassword The m_strApprovalPassword to set
	 */
	public void setApprovalPassword(String strApprovalPassword) 
	{
		m_strApprovalPassword = strApprovalPassword;
	}

	/**
	 * 시스템 Password 설정.
	 * @param strSystemPassword The m_strSystemPassword to set
	 */
	public void setSystemPassword(String strSystemPassword) 
	{
		m_strSystemPassword = strSystemPassword;
	}

	/**
	 * 사용자 UID 설정.
	 * @param strUserUID The m_strUserUID to set
	 */
	public void setUserUID(String strUserUID) 
	{
		m_strUserUID = strUserUID;
	}
	
	/**
	 * 지문 인식 정보 반환.
	 * @return byte[]
	 */
	public byte[] getFingerPrintInfo() 
	{
		return m_byFingerPrintInfo;
	}

	/**
	 * Password Type 반환.
	 * @return int
	 */
	public int getPasswordType() 
	{
		return m_nPasswordType;
	}

	/**
	 * 결재 Password 반환.
	 * @return String
	 */
	public String getApprovalPassword() 
	{
		return m_strApprovalPassword;
	}

	/**
	 * 시스템 Password 반환.
	 * @return String
	 */
	public String getSystemPassword() 
	{
		return m_strSystemPassword;
	}

	/**
	 * 사용자 UID 반환.
	 * @return String
	 */
	public String getUserUID() 
	{
		return m_strUserUID;
	}
	
	/**
	 * 지문 정보 load
	 * @param strFingerprintDataPath 지문정보 파일 Path
	 * @return boolean
	 */
	public boolean loadFingerPrintInfo(String strFingerprintDataPath)
	{
		FileInputStream inStream = null;
		boolean 		bReturn = false;
		File 			file = null;
		byte			byFingerPrintInfo[] = null;
		
		
		try
		{
			file = new File(strFingerprintDataPath);
			inStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to create file input stream.",
								   "UserPassword.loadFingerPrintInfo.new.FileInputStream(FileNotFoundException)",
								   e.getMessage());
			return bReturn;							
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to create file input stream.",
								   "UserPassword.loadFingerPrintInfo.new.FileInputStrem(Exception)",
								   e.getMessage());
		    return bReturn;
		}
		
		try
		{
			byFingerPrintInfo = new byte[(int)file.length()];
			inStream.read(byFingerPrintInfo);
			
			// load finger print information
			m_byFingerPrintInfo = new byte[(int)file.length()];
			System.arraycopy(byFingerPrintInfo, 0, m_byFingerPrintInfo, 0, (int)file.length());
			
			inStream.close();
			bReturn = true;	
		}
		catch(IOException e)
		{
			m_lastError.setMessage("Fail to load finger print information.",
								   "UserPassword.loadFingerPrintInfo.copy array(IOException)",
								   e.getMessage());
								   
			return bReturn;
		}
		catch(Exception e)
		{
			m_lastError.setMessage("Fail to load finger print information.",
								   "UserPassword.loadFingerPrintInfo.copy array(Exception)",
								   e.getMessage());	
								   
		    return bReturn;		
		}		
		
		
		return bReturn;	
	}
	
	/**
	 * 지문 정보 저장
	 * @param strDirectoryPath 지문정보가 저장될 디렉토리 경로
	 * @return String 
	 */
	public String saveFingerPrintInfo(String strDirectoryPath)
	{
		FileOutputStream outStream = null;
		String 			 strFilePath = "";
		String 			 strFileName = "";
		String 			 strDataFileType = "dat";
		GUID             guid = new GUID();
		
		strFileName = guid.toString() + "." + strDataFileType;
		strFilePath = strDirectoryPath + strFileName;
		
		try
		{
			outStream = new FileOutputStream(strFilePath);
			
			if (m_byFingerPrintInfo != null)
			{
				outStream.write(m_byFingerPrintInfo);
			}
			else
			{
				strFileName = "";
			}
			
			outStream.close();	
		}
		catch (FileNotFoundException e)
		{
			m_lastError.setMessage("Fail to save fingerprint information.",
					   			   "UserPassword.saveFingerPrintInfo(FileNotFoundException)",
					   				e.getMessage());
			return "";		
		}
		catch (IOException e)
		{
			m_lastError.setMessage("Fail to save fingerprint information.",
					   			   "UserPassword.saveFingerPrintInfo(IOException)",
					   				e.getMessage());
			return "";	
		}
		catch (Exception e)
		{
			m_lastError.setMessage("Fail to save fingerprint information.",
					   			   "UserPassword.saveFingerPrintInfo(Exception)",
					   				e.getMessage());
			return "";	
		}
		
		return strFileName; 
	}
}
