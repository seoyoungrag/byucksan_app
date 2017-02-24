
/**
 * ExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package com.sds.acube.app.ws.client.document;

/**
 * Class Name  : ExceptionException0.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.ws.client.document.ExceptionException0.java
 */
public class ExceptionException0 extends java.lang.Exception{
    
    /**
	 */
    private com.sds.acube.app.ws.client.document.FolderServiceStub.ExceptionE faultMessage;
    
    public ExceptionException0() {
        super("ExceptionException0");
    }
           
    public ExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public ExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @param msg
	 * @see   
	 */
    public void setFaultMessage(com.sds.acube.app.ws.client.document.FolderServiceStub.ExceptionE msg){
       faultMessage = msg;
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public com.sds.acube.app.ws.client.document.FolderServiceStub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    