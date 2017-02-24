package com.sds.acube.app.idir.common;

import java.io.File;
import java.lang.SecurityException;
import java.io.FilePermission;

public final class FileCtrl
{
	public FileCtrl()
	{
	}

	public String getPathSeparator()
	{
		return File.separator;
	}

	public String getURLSeparator()
	{
		String strSeparator = "/";
		return strSeparator;
	}

	public String getKeyURL(String strSeed)
	{
		String strKeyURL = "";

		GUID objGUID = new GUID();
		strKeyURL = strSeed + "-" + objGUID.toString();

		return strKeyURL;
	}

	public int getFileSize(String strFilePath)
	{
		File objFile = new File(strFilePath);
		if (!objFile.exists())
			return -1;

		int nFileSize =0;
		try
		{
			nFileSize = (int)objFile.length();
		}
		catch (SecurityException e)
		{
			return -1;
		}

		return nFileSize;
	}

	public boolean createDirectory(String strServerPath)
	{
		File objFile = new File(strServerPath);

		if (objFile.exists())
			return true;

		boolean bRet = false;

		try
		{
			bRet = objFile.mkdir();
			if (bRet)
			{
				String strAction = "read, write, delete";
				FilePermission objFilePermission = new FilePermission(strServerPath, strAction);
			}
		}
		catch(SecurityException e)
		{
			return false;
		}

		return bRet;
	}

	public boolean deleteDirectory(String strPath)
	{
		File objFile = new File(strPath);

		if (!objFile.exists() || !objFile.isDirectory())
			return true;

		try
		{
			File[] objTargetFile = objFile.listFiles();

			for (int i = 0 ; i < objTargetFile.length ; i++)
			{
//				if (objTargetFile[i].isDirectory())
//				{
//					deleteDirectory(objTargetFile[i].getPath()
//				}
//				else
				{
					objTargetFile[i].delete();
				}
			}
		}
		catch(SecurityException e)
		{
			return false;
		}

		boolean bRet = objFile.delete();

		return bRet;
	}
}
