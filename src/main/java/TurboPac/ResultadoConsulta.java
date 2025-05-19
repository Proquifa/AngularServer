
package TurboPac;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ResultadoConsulta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ResultadoConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AcuseCancelacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcuseEnvio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comprobante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}StatusComprobante" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultadoConsulta", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", propOrder = {
    "acuseCancelacion",
    "acuseEnvio",
    "comprobante",
    "status"
})
public class ResultadoConsulta {

    @XmlElementRef(name = "AcuseCancelacion", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> acuseCancelacion;
    @XmlElementRef(name = "AcuseEnvio", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> acuseEnvio;
    @XmlElementRef(name = "Comprobante", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> comprobante;
    @XmlElement(name = "Status")
    @XmlSchemaType(name = "string")
    protected StatusComprobante status;

    /**
     * Obtiene el valor de la propiedad acuseCancelacion.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAcuseCancelacion() {
        return acuseCancelacion;
    }

    /**
     * Define el valor de la propiedad acuseCancelacion.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAcuseCancelacion(JAXBElement<String> value) {
        this.acuseCancelacion = value;
    }

    /**
     * Obtiene el valor de la propiedad acuseEnvio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAcuseEnvio() {
        return acuseEnvio;
    }

    /**
     * Define el valor de la propiedad acuseEnvio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAcuseEnvio(JAXBElement<String> value) {
        this.acuseEnvio = value;
    }

    /**
     * Obtiene el valor de la propiedad comprobante.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getComprobante() {
        return comprobante;
    }

    /**
     * Define el valor de la propiedad comprobante.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setComprobante(JAXBElement<String> value) {
        this.comprobante = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link StatusComprobante }
     *     
     */
    public StatusComprobante getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusComprobante }
     *     
     */
    public void setStatus(StatusComprobante value) {
        this.status = value;
    }

}
