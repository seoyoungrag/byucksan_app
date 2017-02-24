package com.sds.acube.app.login.security;

/**
 * ACUBE 인코딩 모듈
 */
public interface SecurityEncoder
{
    public String encode(String sourceData) throws Exception;


    public String encode(String sourceData, String key) throws Exception;
}
