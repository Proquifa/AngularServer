
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
 *         &lt;element name="TimbraURLCfdi33Result" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}RespuestaTimbrado" minOccurs="0"/>
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
    "timbraURLCfdi33Result"
})
@XmlRootElement(name = "TimbraURLCfdi33Response")
public class TimbraURLCfdi33Response {

    @XmlElementRef(name = "TimbraURLCfdi33Result", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<RespuestaTimbrado> timbraURLCfdi33Result;

    /**
     * Obtiene el valor de la propiedad timbraURLCfdi33Result.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public JAXBElement<RespuestaTimbrado> getTimbraURLCfdi33Result() {
        return timbraURLCfdi33Result;
    }

    /**
     * Define el valor de la propiedad timbraURLCfdi33Result.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}
     *     
     */
    public void setTimbraURLCfdi33Result(JAXBElement<RespuestaTimbrado> value) {
        this.timbraURLCfdi33Result = value;
    }

}
