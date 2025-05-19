
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
 *         &lt;element name="TimbraCfdi33Result" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaTimbrado" minOccurs="0"/>
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
    "timbraCfdi33Result"
})
@XmlRootElement(name = "TimbraCfdi33Response")
public class TimbraCfdi33Response {

    @XmlElementRef(name = "TimbraCfdi33Result", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTimbrado> timbraCfdi33Result;

    /**
     * Obtiene el valor de la propiedad timbraCfdi33Result.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public JAXBElement<RespuestaTimbrado> getTimbraCfdi33Result() {
        return timbraCfdi33Result;
    }

    /**
     * Define el valor de la propiedad timbraCfdi33Result.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public void setTimbraCfdi33Result(JAXBElement<RespuestaTimbrado> value) {
        this.timbraCfdi33Result = value;
    }

}
