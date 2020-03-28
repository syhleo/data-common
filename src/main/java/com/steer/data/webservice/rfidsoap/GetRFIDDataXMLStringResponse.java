
package com.steer.data.webservice.rfidsoap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 *
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Get_RFID_Data_XML_StringResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getRFIDDataXMLStringResult"
})
@XmlRootElement(name = "Get_RFID_Data_XML_StringResponse")
public class GetRFIDDataXMLStringResponse {

    @XmlElementRef(name = "Get_RFID_Data_XML_StringResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getRFIDDataXMLStringResult;

    /**
     * ��ȡgetRFIDDataXMLStringResult���Ե�ֵ��
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getGetRFIDDataXMLStringResult() {
        return getRFIDDataXMLStringResult;
    }

    /**
     * ����getRFIDDataXMLStringResult���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setGetRFIDDataXMLStringResult(JAXBElement<String> value) {
        this.getRFIDDataXMLStringResult = value;
    }

}
