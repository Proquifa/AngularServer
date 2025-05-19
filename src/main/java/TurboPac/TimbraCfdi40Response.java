
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
 *         &lt;element name="TimbraCfdi40Result" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaTimbrado" minOccurs="0"/>
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
    "timbraCfdi40Result"
})
@XmlRootElement(name = "TimbraCfdi40Response")
public class TimbraCfdi40Response {

    @XmlElementRef(name = "TimbraCfdi40Result", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTimbrado> timbraCfdi40Result;

    /**
     * Obtiene el valor de la propiedad timbraCfdi40Result.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public JAXBElement<RespuestaTimbrado> getTimbraCfdi40Result() {
        return timbraCfdi40Result;
    }

    /**
     * Define el valor de la propiedad timbraCfdi40Result.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public void setTimbraCfdi40Result(JAXBElement<RespuestaTimbrado> value) {
        this.timbraCfdi40Result = value;
    }

}
