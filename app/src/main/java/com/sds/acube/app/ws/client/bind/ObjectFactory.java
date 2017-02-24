
package com.sds.acube.app.ws.client.bind;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sds.acube.app.mobile.ws.bind package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StorVO_QNAME = new QName("http://bind.ws.mobile.app.acube.sds.com/", "StorVO");
    private final static QName _ArrayOfFieldVO_QNAME = new QName("http://bind.ws.mobile.app.acube.sds.com/", "ArrayOfFieldVO");
    private final static QName _FieldVO_QNAME = new QName("http://bind.ws.mobile.app.acube.sds.com/", "FieldVO");
    private final static QName _ResVO_QNAME = new QName("http://bind.ws.mobile.app.acube.sds.com/", "ResVO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sds.acube.app.mobile.ws.bind
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetApproverInfo }
     * 
     */
    public SetApproverInfo createSetApproverInfo() {
        return new SetApproverInfo();
    }

    /**
     * Create an instance of {@link StorVO }
     * 
     */
    public StorVO createStorVO() {
        return new StorVO();
    }

    /**
     * Create an instance of {@link ArrayOfFieldVO }
     * 
     */
    public ArrayOfFieldVO createArrayOfFieldVO() {
        return new ArrayOfFieldVO();
    }

    /**
     * Create an instance of {@link ResVO }
     * 
     */
    public ResVO createResVO() {
        return new ResVO();
    }

    /**
     * Create an instance of {@link FieldVO }
     * 
     */
    public FieldVO createFieldVO() {
        return new FieldVO();
    }

    /**
     * Create an instance of {@link SetApproverInfoResponse }
     * 
     */
    public SetApproverInfoResponse createSetApproverInfoResponse() {
        return new SetApproverInfoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StorVO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bind.ws.mobile.app.acube.sds.com/", name = "StorVO")
    public JAXBElement<StorVO> createStorVO(StorVO value) {
        return new JAXBElement<StorVO>(_StorVO_QNAME, StorVO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFieldVO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bind.ws.mobile.app.acube.sds.com/", name = "ArrayOfFieldVO")
    public JAXBElement<ArrayOfFieldVO> createArrayOfFieldVO(ArrayOfFieldVO value) {
        return new JAXBElement<ArrayOfFieldVO>(_ArrayOfFieldVO_QNAME, ArrayOfFieldVO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FieldVO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bind.ws.mobile.app.acube.sds.com/", name = "FieldVO")
    public JAXBElement<FieldVO> createFieldVO(FieldVO value) {
        return new JAXBElement<FieldVO>(_FieldVO_QNAME, FieldVO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResVO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bind.ws.mobile.app.acube.sds.com/", name = "ResVO")
    public JAXBElement<ResVO> createResVO(ResVO value) {
        return new JAXBElement<ResVO>(_ResVO_QNAME, ResVO.class, null, value);
    }

}
