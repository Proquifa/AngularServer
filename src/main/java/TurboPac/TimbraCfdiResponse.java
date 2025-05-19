
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
 *         &lt;element name="TimbraCfdiResult" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaTimbrado" minOccurs="0"/>
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
    "timbraCfdiResult"
})
@XmlRootElement(name = "TimbraCfdiResponse")
public class TimbraCfdiResponse {

    @XmlElementRef(name = "TimbraCfdiResult", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTimbrado> timbraCfdiResult;

    /**
     * Obtiene el valor de la propiedad timbraCfdiResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public JAXBElement<RespuestaTimbrado> getTimbraCfdiResult() {
        return timbraCfdiResult;
    }

    /**
     * Define el valor de la propiedad timbraCfdiResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public void setTimbraCfdiResult(JAXBElement<RespuestaTimbrado> value) {
        this.timbraCfdiResult = value;
    }

}
