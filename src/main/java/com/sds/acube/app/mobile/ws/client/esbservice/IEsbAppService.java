package com.sds.acube.app.mobile.ws.client.esbservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.8
 * Thu Jun 28 15:31:15 KST 2012
 * Generated source version: 2.2.8
 * 
 */
 
@WebService(targetNamespace = "http://service.ws.app.acube.sds.com/", name = "IEsbAppService")
public interface IEsbAppService {

    @ResponseWrapper(localName = "listAppDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListAppDocResponse")
    @RequestWrapper(localName = "listAppDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListAppDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs listAppDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "listBoxResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListBoxResponse")
    @RequestWrapper(localName = "listBox", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListBox")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs listBox(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "listDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListDocResponse")
    @RequestWrapper(localName = "listDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs listDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "selectDocInfoResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.SelectDocInfoResponse")
    @RequestWrapper(localName = "selectDocInfo", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.SelectDocInfo")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO selectDocInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "countPortalDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.CountPortalDocResponse")
    @RequestWrapper(localName = "countPortalDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.CountPortalDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppItemCountVO countPortalDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "processDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessDocResponse")
    @RequestWrapper(localName = "processDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO processDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppActionVO arg0
    );

    @ResponseWrapper(localName = "countDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.CountDocResponse")
    @RequestWrapper(localName = "countDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.CountDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppItemCountVO countDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "selectDocInfoMobileResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.SelectDocInfoMobileResponse")
    @RequestWrapper(localName = "selectDocInfoMobile", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.SelectDocInfoMobile")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO selectDocInfoMobile(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "listMobileBoxResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListMobileBoxResponse")
    @RequestWrapper(localName = "listMobileBox", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ListMobileBox")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs listMobileBox(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );

    @ResponseWrapper(localName = "processAppDocResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessAppDocResponse")
    @RequestWrapper(localName = "processAppDoc", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessAppDoc")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO processAppDoc(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.HeaderVO arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.Files arg1
    );

    @ResponseWrapper(localName = "getAttachFileResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.GetAttachFileResponse")
    @RequestWrapper(localName = "getAttachFile", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.GetAttachFile")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO getAttachFile(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO arg0
    );
    
    @ResponseWrapper(localName = "processMobileApprovalResponse", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessMobileApprovalResponse")
    @RequestWrapper(localName = "processMobileApproval", targetNamespace = "http://service.ws.app.acube.sds.com/", className = "com.sds.acube.app.mobile.ws.client.esbservice.ProcessMobileApproval")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public com.sds.acube.app.mobile.vo.MobileAppResultVO processMobileApproval(
        @WebParam(name = "arg0", targetNamespace = "")
        com.sds.acube.app.mobile.vo.MobileAppActionVO arg0
    );
    
    
}
