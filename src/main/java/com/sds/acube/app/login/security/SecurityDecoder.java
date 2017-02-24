package com.sds.acube.app.login.security;

/**
 * ACUBE 디코딩 모듈
 */
public interface SecurityDecoder
{
    public String decode(String encodedData) throws Exception;


    public String decode(String encodedData, String key) throws Exception;
}
