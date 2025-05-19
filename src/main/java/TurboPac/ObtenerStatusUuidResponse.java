
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
 *         &lt;element name="ObtenerStatusUuidResult" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}ResultadoConsulta" minOccurs="0"/>
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
    "obtenerStatusUuidResult"
})
@XmlRootElement(name = "ObtenerStatusUuidResponse")
public class ObtenerStatusUuidResponse {

    @XmlElementRef(name = "ObtenerStatusUuidResult", namespace = "http://turbopac.mx/TurboPac", type = JAXBElement.class, required = false)
    protected JAXBElement<ResultadoConsulta> obtenerStatusUuidResult;

    /**
     * Obtiene el valor de la propiedad obtenerStatusUuidResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ResultadoConsulta }{@code >}
     *     
     */
    public JAXBElement<ResultadoConsulta> getObtenerStatusUuidResult() {
        return obtenerStatusUuidResult;
    }

    /**
     * Define el valor de la propiedad obtenerStatusUuidResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ResultadoConsulta }{@code >}
     *     
     */
    public void setObtenerStatusUuidResult(JAXBElement<ResultadoConsulta> value) {
        this.obtenerStatusUuidResult = value;
    }

}
