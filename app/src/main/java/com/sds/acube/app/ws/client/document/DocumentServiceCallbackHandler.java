
/**
 * DocumentServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.sds.acube.app.ws.client.document;

    /**
	 * DocumentServiceCallbackHandler Callback class, Users can extend this class and implement their own receiveResult and receiveError methods.
	 */
    public abstract class DocumentServiceCallbackHandler{



    /**
	 */
    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public DocumentServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public DocumentServiceCallbackHandler(){
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
                    com.sds.acube.app.ws.client.document.DocumentServiceStub.MoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from move operation
           */
            public void receiveErrormove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for remove method
            * override this method for handling normal response from remove operation
            */
           public void receiveResultremove(
                    com.sds.acube.app.ws.client.document.DocumentServiceStub.RemoveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from remove operation
           */
            public void receiveErrorremove(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create method
            * override this method for handling normal response from create operation
            */
           public void receiveResultcreate(
                    com.sds.acube.app.ws.client.document.DocumentServiceStub.CreateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create operation
           */
            public void receiveErrorcreate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for retrieve method
            * override this method for handling normal response from retrieve operation
            */
           public void receiveResultretrieve(
                    com.sds.acube.app.ws.client.document.DocumentServiceStub.RetrieveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from retrieve operation
           */
            public void receiveErrorretrieve(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for update method
            * override this method for handling normal response from update operation
            */
           public void receiveResultupdate(
                    com.sds.acube.app.ws.client.document.DocumentServiceStub.UpdateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from update operation
           */
            public void receiveErrorupdate(java.lang.Exception e) {
            }
                


    }
    