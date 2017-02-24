package com.sds.acube.app.login.security.seed;

import com.sds.acube.app.login.security.SecurityDecoder;import com.sds.acube.app.login.security.SecurityEncoder;


public class SeedWrapper implements SecurityEncoder, SecurityDecoder
{
    public String encode(String sourceData) throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        String certFilesHome = classLoader.getResource("security").getPath();

        return encode(sourceData, certFilesHome + "/seed.roundkey");
    }


    public String encode(String sourceData, String key) throws Exception
    {
        return SeedBean.doEncrypt(sourceData, key);
    }


    public String decode(String encodedData) throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        String certFilesHome = classLoader.getResource("security").getPath();

        return decode(encodedData, certFilesHome + "/seed.roundkey");
    }


    public String decode(String encodedData, String key) throws Exception
    {
        return SeedBean.doDecode(encodedData, key);
    }
}
