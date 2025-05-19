
package TurboPac;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CancelaRetencionResult" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaCancelacion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cancelaRetencionResult"
})
@XmlRootElement(name = "CancelaRetencionResponse")
public class CancelaRetencionResponse {

    @XmlElementRef(name = "CancelaRetencionResult", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaCancelacion> cancelaRetencionResult;

    /**
     * Obtiene el valor de la propiedad cancelaRetencionResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public JAXBElement<RespuestaCancelacion> getCancelaRetencionResult() {
        return cancelaRetencionResult;
    }

    /**
     * Define el valor de la propiedad cancelaRetencionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}
     *     
     */
    public void setCancelaRetencionResult(JAXBElement<RespuestaCancelacion> value) {
        this.cancelaRetencionResult = value;
    }

}
