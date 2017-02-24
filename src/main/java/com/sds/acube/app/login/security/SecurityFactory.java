package com.sds.acube.app.login.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;import com.sds.acube.app.common.util.LogWrapper;


/** * Class Name  : SecurityFactory.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br> * @author   kimside  * @since  2012. 5. 23. * @version  1.0  * @see com.sds.acube.app.login.security.SecurityFactory.java */public class SecurityFactory
{
    /**	 */    private static SecurityFactory instance = new SecurityFactory();

    private String defaultAlgorithm;

    /**	 */    private String encoding;

    private Properties props;

    private Map objectMap = new HashMap();


    private SecurityFactory()
    {
        props = new Properties();
        InputStream fis = null;
        try
        {
            fis = getClass().getResourceAsStream("/app/config/security/security.properties");
            props.load(fis);

            this.defaultAlgorithm = props.getProperty("default", "DES");
            this.encoding = props.getProperty("encoding", "DEFAULT");
        }
        catch (IOException e)
        {
            LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        }
        finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            }
            catch (Exception e)
            {
            }
        }
    }


    /**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */    public static SecurityFactory getInstance()
    {
        if (instance == null)
        {
            instance = new SecurityFactory();
        }

        return instance;
    }


    public void clearInstance()
    {
        instance = null;
    }


    public SecurityEncoder getEncoder()
    {
        return getEncoder(defaultAlgorithm);
    }


    public SecurityEncoder getEncoder(String algorithm)
    {
        if (algorithm == null)
        {
            algorithm = defaultAlgorithm;
        }
        String keyName = algorithm + ".encoder";
        SecurityEncoder encoderObject = null;

        try
        {
            encoderObject = (SecurityEncoder) objectMap.get(keyName);

            if (encoderObject == null)
            {
                String encoderClass = props.getProperty(keyName);
                encoderObject = (SecurityEncoder) Class.forName(encoderClass).newInstance();
                objectMap.put(keyName, encoderObject);
            }
        }
        catch (Exception e)
        {
            LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        }

        return encoderObject;
    }


    public SecurityDecoder getDecoder()
    {
        return getDecoder(defaultAlgorithm);
    }


    public SecurityDecoder getDecoder(String algorithm)
    {
        SecurityDecoder decoderObject = null;

        try
        {
            if (algorithm == null)
            {
                algorithm = defaultAlgorithm;
            }

            String keyName = algorithm + ".decoder";
            decoderObject = (SecurityDecoder) objectMap.get(keyName);

            if (decoderObject == null)
            {
                String decoderClass = props.getProperty(keyName);
                decoderObject = (SecurityDecoder) Class.forName(decoderClass).newInstance();
                objectMap.put(keyName, decoderObject);
            }
        }
        catch (Exception e)
        {
            LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
        }

        return decoderObject;
    }


    /**	 * <pre>  설명 </pre>	 * @param str	 * @see   	 */    public void setEncoding(String str)
    {
        this.encoding = str;
    }


    /**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */    public String getEncoding()
    {
        return this.encoding;
    }
}
