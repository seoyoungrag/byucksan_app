package com.sds.acube.app.design.style;

import java.util.*;
import java.io.*;

import com.sds.acube.app.common.util.LogWrapper;

public class AcubeStyle
{
    private Properties props;

    public AcubeStyle(String styleFile)
    {
 		props = new Properties();
        InputStream fis = null;
		try
		{
			fis = getClass().getResourceAsStream(styleFile);
			props.load(fis);
		}
		catch (Exception e)
		{
	            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
		}
		finally
		{
			try {
			    fis.close();
		    } catch (Exception e) {
			LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
		    }
		}
    }


	public String getString(String key) {
		return props.getProperty(key);
	}
	public int getInt(String key) {
	    return getInt(key, "0");
	}
	public float getFloat(String key) {
	    return getFloat(key, "0.0");
    }
	public String[] getStringArray(String key, String delim) {
	    return getStringArray(key, "", delim);
	}

	public String getString(String key, String defaultValue)
	{
		return props.getProperty(key, defaultValue);
	}
	public int getInt(String key, String defaultValue)
	{
	    int number = 0;
		try {
		    number = Integer.parseInt(props.getProperty(key, defaultValue));
		}
		catch (Exception e) {
	            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
		}

		return number;
	}
	public float getFloat(String key, String defaultValue)
	{
	    float number = 0;
		try {
		    number = Float.parseFloat(props.getProperty(key, defaultValue));
		}
		catch (Exception e) {
	            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
		}

		return number;
	}
	public String[] getStringArray(String key, String defaultValue, String delim)
	{
	    String[] arrayValue = null;

		try
		{
		    String listValue = props.getProperty(key, defaultValue);
		    StringTokenizer valueToken = new StringTokenizer(listValue, delim);
		    arrayValue = new String[valueToken.countTokens()];
		    for (int i=0; i < arrayValue.length; i++) {
		        arrayValue[i] = valueToken.nextToken();
		    }
		}
		catch (Exception e) {
	            LogWrapper.getLogger("com.sds.acube.app.design").error(e.getMessage());
		}

		return arrayValue;
	}
}
