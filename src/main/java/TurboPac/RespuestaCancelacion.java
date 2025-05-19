
package TurboPac;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RespuestaCancelacion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaCancelacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Acuse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MensajeError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusUuids" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}ArrayOfStatusUuid" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaCancelacion", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", propOrder = {
    "acuse",
    "mensajeError",
    "statusUuids"
})
public class RespuestaCancelacion {

    @XmlElementRef(name = "Acuse", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> acuse;
    @XmlElementRef(name = "MensajeError", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensajeError;
    @XmlElementRef(name = "StatusUuids", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfStatusUuid> statusUuids;

    /**
     * Obtiene el valor de la propiedad acuse.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAcuse() {
        return acuse;
    }

    /**
     * Define el valor de la propiedad acuse.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAcuse(JAXBElement<String> value) {
        this.acuse = value;
    }

    /**
     * Obtiene el valor de la propiedad mensajeError.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensajeError() {
        return mensajeError;
    }

    /**
     * Define el valor de la propiedad mensajeError.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensajeError(JAXBElement<String> value) {
        this.mensajeError = value;
    }

    /**
     * Obtiene el valor de la propiedad statusUuids.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfStatusUuid }{@code >}
     *     
     */
    public JAXBElement<ArrayOfStatusUuid> getStatusUuids() {
        return statusUuids;
    }

    /**
     * Define el valor de la propiedad statusUuids.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfStatusUuid }{@code >}
     *     
     */
    public void setStatusUuids(JAXBElement<ArrayOfStatusUuid> value) {
        this.statusUuids = value;
    }

}
