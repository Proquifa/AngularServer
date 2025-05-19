
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
 *         &lt;element name="TimbraRetencionResult" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaTimbrado" minOccurs="0"/>
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
    "timbraRetencionResult"
})
@XmlRootElement(name = "TimbraRetencionResponse")
public class TimbraRetencionResponse {

    @XmlElementRef(name = "TimbraRetencionResult", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTimbrado> timbraRetencionResult;

    /**
     * Obtiene el valor de la propiedad timbraRetencionResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public JAXBElement<RespuestaTimbrado> getTimbraRetencionResult() {
        return timbraRetencionResult;
    }

    /**
     * Define el valor de la propiedad timbraRetencionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public void setTimbraRetencionResult(JAXBElement<RespuestaTimbrado> value) {
        this.timbraRetencionResult = value;
    }

}
