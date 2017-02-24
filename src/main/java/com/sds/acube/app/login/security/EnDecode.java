package com.sds.acube.app.login.security;import com.sds.acube.app.common.util.AppConfig;import com.sds.acube.common.security.hash.HashEncrypt;import com.sds.acube.esso.security.des.Encrypt;



public class EnDecode
{    private static String ENCODE_ALGORITHM_DES = "DES";    private static String ENCODE_ALGORITHM = AppConfig.getProperty("encode_algorithm", ENCODE_ALGORITHM_DES, "organization");
    public static String[] EncodeByFileInType(String strType, String filePath, String strData)
    {
    	String[] arrRet = new String[4];
    	String strD0 = strType;
    	String strD1 = EncodeBySType(strData);
    	String strD2 = null;
    	String strD3 = null;

    	arrRet[0] = strD1;
    	arrRet[1] = strD2;
    	arrRet[2] = strD3;
    	arrRet[3] = strD0;

    	return arrRet;
    }


    public static String[] EncodeByFile(String filePath, String strData)
    {
    	String strType = "SDS";
    	return EncodeByFileInType(strType, filePath, strData);
    }


    public static String[] EncodeByCertIDInType(String strType, String certID, String strData)
    {

    	String[] arrRet = new String[4];
    	String strD0 = strType;
    	String strD1 = EncodeBySType(strData);
    	String strD2 = null;
    	String strD3 = null;

    	arrRet[0] = strD1;
    	arrRet[1] = strD2;
    	arrRet[2] = strD3;
    	arrRet[3] = strD0;

    	return arrRet;
    }

    public static String[] EncodeByCertID(String certID, String strData)
    {
    	String strType = "SDS";
    	return EncodeByCertIDInType(strType, certID, strData);
    }


    public static String DecodeByFileInType(String strType, String filePath,
                                String strKey, String strHash, String strData)
    {
    	return DecodeBySType(strData);
    }


    public static String DecodeByFile(String filePath, String strKey, String strHash, String strData)
    {
    	String strType = "SDS";
    	return DecodeByFileInType(strType, filePath, strKey, strHash, strData);
    }


    public static String EncodeBySType(String strData)
    {	if (ENCODE_ALGORITHM_DES.equals(ENCODE_ALGORITHM)) {
	    return Encrypt.com_Encode(":" + strData + ":sisenc");	}	else {	    return HashEncrypt.getHashEncryptData(strData, ENCODE_ALGORITHM);	}
    }


    public static String DecodeBySType(String strData)
    {
    	String strRet = Encrypt.com_Decode(strData);

    	int e = strRet.indexOf(":");
    	int d = strRet.indexOf(":sisenc");
    	if(e > -1 && d > -1) {
    		strRet = strRet.substring(e+1, d);
    	}

    	return strRet;
    }
}
