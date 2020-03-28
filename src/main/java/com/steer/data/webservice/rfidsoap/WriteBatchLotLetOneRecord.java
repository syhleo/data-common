
package com.steer.data.webservice.rfidsoap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="i_batch_8" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&gt;
 *         &lt;element name="i_Lot_8" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *         &lt;element name="i_gangzhong_8" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "iBatch8",
        "iLot8",
        "iGangzhong8"
})
@XmlRootElement(name = "Write_BatchLotLet_One_Record")
public class WriteBatchLotLetOneRecord {

    @XmlElement(name = "i_batch_8")
    @XmlSchemaType(name = "unsignedInt")
    protected Long iBatch8;
    @XmlElement(name = "i_Lot_8")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer iLot8;
    @XmlElement(name = "i_gangzhong_8")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer iGangzhong8;

    /**
     * ��ȡiBatch8���Ե�ֵ��
     *
     * @return possible object is
     * {@link Long }
     */
    public Long getIBatch8() {
        return iBatch8;
    }

    /**
     * ����iBatch8���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link Long }
     */
    public void setIBatch8(Long value) {
        this.iBatch8 = value;
    }

    /**
     * ��ȡiLot8���Ե�ֵ��
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getILot8() {
        return iLot8;
    }

    /**
     * ����iLot8���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setILot8(Integer value) {
        this.iLot8 = value;
    }

    /**
     * ��ȡiGangzhong8���Ե�ֵ��
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getIGangzhong8() {
        return iGangzhong8;
    }

    /**
     * ����iGangzhong8���Ե�ֵ��
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setIGangzhong8(Integer value) {
        this.iGangzhong8 = value;
    }

}
