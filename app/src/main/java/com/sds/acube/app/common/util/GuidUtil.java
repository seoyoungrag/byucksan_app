package com.sds.acube.app.common.util;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Class Name  : GuidUtil.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 28. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 28.
 * @version  1.0 
 * @see  com.sds.acube.app.common.util.GuidUtil.java
 */

public final class GuidUtil {

	public static String getGUID() {
	    GuidUtil objGUID = new GuidUtil();
		return objGUID.toString();
	}
	
	/**
	 * prefix를 결합한 GUID String을 생성함
	 * @param prefix
	 * @return String
	 */
	public static String getGUID(String s) {
		String prefix = s;
		if (prefix == null) {
			prefix = "";
		}
		
		return prefix + getGUID();
	}
	
	private int unique;
	private long time;
	private short count;

	private final int nMaxLength;

	private String strGUID;
	
	private static int getHostUniqueNum()
	{
		return new Object().hashCode();
	}

	private static int hostUnique = getHostUniqueNum();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = Short.MIN_VALUE;
	private static Object mutex = new Object();
	private static long  oneSecond = 1000; // in milliseconds

	public GuidUtil()
	{
		nMaxLength = 32;
		strGUID = "";
		generateGUID();
	}

	public GuidUtil(int nLength)
	{
		nMaxLength = nLength;
		strGUID = "";
		generateGUID();
	}
	
	/**
	 * for PMD
	 * @param  unique
	 */
	protected void setUnique(int unique) {
		this.unique = unique;
	}

	private void generateGUID()
	{
		synchronized (mutex)
		{
			if (lastCount == Short.MAX_VALUE)
			{
				boolean done = false;
				while (!done)
				{
					time = System.currentTimeMillis();
					if (time < lastTime+oneSecond)
					{
					// pause for a second to wait for time to change
						try
						{
							Thread.sleep(oneSecond);
//							Thread.currentThread().sleep(oneSecond);
						}
						catch (java.lang.InterruptedException e)
						{
							e.getMessage();
						}	// ignore exception
						continue;
					}
					else
					{
						lastTime = time;
						lastCount = Short.MIN_VALUE;
						done = true;
					}
				}
			}
			else
			{
				time = lastTime;
			}

			unique = hostUnique;
			count = lastCount++;
		}

		strGUID = Integer.toHexString((new Object()).hashCode());
		strGUID += Integer.toHexString(unique);
		strGUID += Long.toHexString(time);
		strGUID += Integer.toHexString((int)count);

		strGUID = toGUIDString(strGUID.toUpperCase(), nMaxLength);
	}

	public int hashCode()
	{
		return (int)time + (int)count;
	}

	public String toString()
	{
		return strGUID;
	}

	private String toGUIDString(String s, int nMaxLength)
	{
		String strGUID = s;
		int nLength = strGUID.length();

		if (nLength > nMaxLength)
		{
			strGUID = strGUID.substring(0, nMaxLength);
		}
		else if (nLength < nMaxLength)
		{
			while (nLength < nMaxLength)
			{
				strGUID = "0" + strGUID;
				nLength++;
			}
		}

		return strGUID;
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

}
