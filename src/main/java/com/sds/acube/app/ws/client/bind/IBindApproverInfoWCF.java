package com.sds.acube.app.ws.client.bind;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.6.1
 * 2012-08-22T10:36:56.198+09:00
 * Generated source version: 2.6.1
 * 
 */
@WebService(targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/", name = "IBindApproverInfoWCF")
@XmlSeeAlso({ObjectFactory.class, com.sds.acube.app.ws.client.bind.MSObjectFactory.class})
public interface IBindApproverInfoWCF {

    @ResponseWrapper(localName = "setApproverInfoResponse", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.bind.SetApproverInfoResponse")
    @Action(input = "http://bind.ws.mobile.app.acube.sds.com/IBindApproverInfoWCF/setApproverInfo", output = "http://bind.ws.mobile.app.acube.sds.com/IBindApproverInfoWCF/setApproverInfoResponse")
    @RequestWrapper(localName = "setApproverInfo", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.bind.SetApproverInfo")
    @WebResult(name = "setApproverInfoResult", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/")
    @WebMethod(action = "http://bind.ws.mobile.app.acube.sds.com/IBindApproverInfoWCF/setApproverInfo")
    public com.sds.acube.app.ws.client.bind.ResVO setApproverInfo(
        @WebParam(name = "fileId", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/")
        java.lang.String fileId,
        @WebParam(name = "fileType", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/")
        java.lang.Integer fileType,
        @WebParam(name = "storVO", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/")
        com.sds.acube.app.ws.client.bind.StorVO storVO,
        @WebParam(name = "fieldVOs", targetNamespace = "http://bind.ws.mobile.app.acube.sds.com/")
        com.sds.acube.app.ws.client.bind.ArrayOfFieldVO fieldVOs
    );
}