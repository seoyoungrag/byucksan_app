package com.sds.acube.app.idir.common;

public final class GUID
{
	private int unique;
	private long time;
	private short count;

	private int nMaxLength;

	private String m_strGUID;
	
	private static int getHostUniqueNum()
	{
		return (new Object()).hashCode();
	}

	private static int hostUnique = getHostUniqueNum();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = Short.MIN_VALUE;
	private static Object mutex = new Object();
	private static long  ONE_SECOND = 1000; // in milliseconds

	public GUID()
	{
		nMaxLength = 32;
		m_strGUID = "";
		generateGUID();
	}

	public GUID(int nLength)
	{
		nMaxLength = nLength;
		m_strGUID = "";
		generateGUID();
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
				    if (time < lastTime+ONE_SECOND)
				    {
					// pause for a second to wait for time to change
						try
						{
							Thread.sleep(ONE_SECOND);
//							Thread.currentThread().sleep(ONE_SECOND);
						}
						catch (java.lang.InterruptedException e)
						{
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

		m_strGUID = Integer.toHexString((new Object()).hashCode());
		m_strGUID += Integer.toHexString(unique);
		m_strGUID += Long.toHexString(time);
		m_strGUID += Integer.toHexString((int)count);

		m_strGUID = toGUIDString(m_strGUID.toUpperCase(), nMaxLength);
	}

	public int hashCode()
	{
		return (int)time + (int)count;
	}

	public String toString()
	{
		return m_strGUID;
    }

    private String toGUIDString(String strGUID, int nMaxLength)
    {
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
/*    
    public static void main(String[] arg)
    {
    	GUID objGUID = new GUID();

    	System.out.println(objGUID.toString());
    }
*/
}
