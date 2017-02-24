
/**
 * FolderServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.sds.acube.app.ws.client.document;

    /**
	 * FolderServiceCallbackHandler Callback class, Users can extend this class and implement their own receiveResult and receiveError methods.
	 */
    public abstract class FolderServiceCallbackHandler{



    /**
	 */
    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public FolderServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public FolderServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
	 * Get the client data
	 */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for move method
            * override this method for handling normal response from move operation
            */
           public void receiveResultmove(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.MoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from move operation
           */
            public void receiveErrormove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeShare method
            * override this method for handling normal response from removeShare operation
            */
           public void receiveResultremoveShare(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.RemoveShareResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeShare operation
           */
            public void receiveErrorremoveShare(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for remove method
            * override this method for handling normal response from remove operation
            */
           public void receiveResultremove(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.RemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from remove operation
           */
            public void receiveErrorremove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for testCleanup method
            * override this method for handling normal response from testCleanup operation
            */
           public void receiveResulttestCleanup(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.TestCleanupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from testCleanup operation
           */
            public void receiveErrortestCleanup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create method
            * override this method for handling normal response from create operation
            */
           public void receiveResultcreate(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.CreateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create operation
           */
            public void receiveErrorcreate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for share method
            * override this method for handling normal response from share operation
            */
           public void receiveResultshare(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.ShareResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from share operation
           */
            public void receiveErrorshare(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cleanup method
            * override this method for handling normal response from cleanup operation
            */
           public void receiveResultcleanup(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.CleanupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cleanup operation
           */
            public void receiveErrorcleanup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for order method
            * override this method for handling normal response from order operation
            */
           public void receiveResultorder(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.OrderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from order operation
           */
            public void receiveErrororder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for update method
            * override this method for handling normal response from update operation
            */
           public void receiveResultupdate(
                    com.sds.acube.app.ws.client.document.FolderServiceStub.UpdateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from update operation
           */
            public void receiveErrorupdate(java.lang.Exception e) {
            }
                


    }
    