package com.sds.acube.app.login.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
{
    /**

    private String defaultAlgorithm;

    /**

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


    /**
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


    /**
    {
        this.encoding = str;
    }


    /**
    {
        return this.encoding;
    }
}