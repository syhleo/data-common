
package com.steer.data.webservice.rfidsoap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="Get_XinTiao_IntResult" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getXinTiaoIntResult"
})
@XmlRootElement(name = "Get_XinTiao_IntResponse")
public class GetXinTiaoIntResponse {

    @XmlElement(name = "Get_XinTiao_IntResult")
    protected Integer getXinTiaoIntResult;

    /**
     * ��ȡgetXinTiaoIntResult���Ե�ֵ��
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getGetXinTiaoIntResult() {
        return getXinTiaoIntResult;
    }

    /**
     * ����getXinTiaoIntResult���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setGetXinTiaoIntResult(Integer value) {
        this.getXinTiaoIntResult = value;
    }

}
