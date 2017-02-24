package com.sds.acube.app.login.security.symmetric;

import com.sds.acube.esso.security.des.Encrypt;import com.sds.acube.app.login.security.SecurityDecoder;import com.sds.acube.app.login.security.SecurityEncoder;


public class EnDecoder implements SecurityEncoder, SecurityDecoder
{
    public String encode(String sourceData)
    {
        return encode(sourceData, null);
    }


    public String encode(String sourceData, String key)
    {
        StringBuffer buff = new StringBuffer();
        buff.append(":");
        buff.append(sourceData);
        buff.append(":sisenc");
        return Encrypt.com_Encode(buff.toString());
    }


    public String decode(String encodedData)
    {
        return decode(encodedData, null);
    }


    public String decode(String encodedData, String key)
    {
        String decodedData = Encrypt.com_Decode(encodedData);

        int e = decodedData.indexOf(":");
        int d = decodedData.lastIndexOf(":sisenc");
        if (e > -1 && d > -1)
        {
            decodedData = decodedData.substring(e + 1, d);
        }

        return decodedData;
    }
}
