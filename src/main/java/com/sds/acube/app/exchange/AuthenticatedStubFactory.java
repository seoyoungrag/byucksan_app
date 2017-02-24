/**
 * 
 */
package com.sds.acube.app.exchange;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;

import com.sds.acube.app.ws.client.document.DocumentServiceStub;
import com.sds.acube.app.ws.client.document.FolderServiceStub;


/**
 * Class Name : AuthenticatedStubFactory.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 1. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 1.
 * @version 1.0
 * @see com.sds.acube.app.ws.service.AuthenticatedStubFactory.java
 */

public class AuthenticatedStubFactory {

    private static final String NAME_SPACE = "http://service.ws.dms.ion.net";

    private SOAPFactory factory;
    private OMNamespace namespace;

    private String userId;
    private String userPwd;


    public AuthenticatedStubFactory(String userId, String userPwd) {
	this.userId = userId;
	this.userPwd = userPwd;
    }


    public DocumentServiceStub getDocumentServiceStub(String uri) throws AxisFault {
	DocumentServiceStub stub = new DocumentServiceStub(uri);
	return (DocumentServiceStub) this.loginToStub(stub);
    }


    public FolderServiceStub getFolderServiceStub(String uri) throws AxisFault {
	FolderServiceStub stub = new FolderServiceStub(uri);
	return (FolderServiceStub) this.loginToStub(stub);
    }


    private Stub loginToStub(Stub stub) {
	ServiceClient client = stub._getServiceClient();
	client.getOptions().setManageSession(true);

	setHeaderForLogin(client, userId, userPwd);

	return stub;
    }


    private void setHeaderForLogin(ServiceClient client, String userId, String passwd) {
	createAndSetFactoryAndNS();
	setHeaderBlockAt(client, "userId", userId);
	setHeaderBlockAt(client, "userPwd", passwd);
    }


    private void createAndSetFactoryAndNS() {
	this.factory = OMAbstractFactory.getSOAP11Factory();
	this.namespace = factory.createOMNamespace(NAME_SPACE, "dms");
    }


    private void setHeaderBlockAt(ServiceClient client, String key, String value) {
	if (this.factory == null || this.namespace == null) {
	    throw new NullPointerException("WS SoapFactory or namespace object is not initialized");
	}

	SOAPHeaderBlock headerBlock = this.factory.createSOAPHeaderBlock(key, namespace);
	headerBlock.setText(value);
	client.addHeader(headerBlock);
    }
}
