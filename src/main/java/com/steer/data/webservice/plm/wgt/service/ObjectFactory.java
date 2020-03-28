package com.steer.data.webservice.plm.wgt.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.plm.wgt.service package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InsertACCEPT_QNAME = new QName("http://service.wgt.plm.com/", "insertACCEPT");
    private final static QName _InsertACCEPTResponse_QNAME = new QName("http://service.wgt.plm.com/", "insertACCEPTResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.plm.wgt.service
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link  }
     */
    public InsertACCEPT createInsertACCEPT() {
        return new InsertACCEPT();
    }

    /**
     * Create an instance of {@link InsertACCEPTResponse }
     */
    public InsertACCEPTResponse createInsertACCEPTResponse() {
        return new InsertACCEPTResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertACCEPT }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.wgt.plm.com/", name = "insertACCEPT")
    public JAXBElement<InsertACCEPT> createInsertACCEPT(InsertACCEPT value) {
        return new JAXBElement<InsertACCEPT>(_InsertACCEPT_QNAME, InsertACCEPT.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertACCEPTResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.wgt.plm.com/", name = "insertACCEPTResponse")
    public JAXBElement<InsertACCEPTResponse> createInsertACCEPTResponse(InsertACCEPTResponse value) {
        return new JAXBElement<InsertACCEPTResponse>(_InsertACCEPTResponse_QNAME, InsertACCEPTResponse.class, null, value);
    }

}
