
package TurboPac;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para RespuestaTimbrado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="RespuestaTimbrado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CadenaTimbre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cfdi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescripcionError" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/>
 *         &lt;element name="Uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Valido" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaTimbrado", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", propOrder = {
    "cadenaTimbre",
    "cfdi",
    "descripcionError",
    "uuid",
    "valido"
})
public class RespuestaTimbrado {

    @XmlElementRef(name = "CadenaTimbre", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cadenaTimbre;
    @XmlElementRef(name = "Cfdi", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cfdi;
    @XmlElementRef(name = "DescripcionError", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfstring> descripcionError;
    @XmlElementRef(name = "Uuid", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", type = JAXBElement.class, required = false)
    protected JAXBElement<String> uuid;
    @XmlElement(name = "Valido")
    protected Boolean valido;

    /**
     * Obtiene el valor de la propiedad cadenaTimbre.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCadenaTimbre() {
        return cadenaTimbre;
    }

    /**
     * Define el valor de la propiedad cadenaTimbre.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCadenaTimbre(JAXBElement<String> value) {
        this.cadenaTimbre = value;
    }

    /**
     * Obtiene el valor de la propiedad cfdi.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCfdi() {
        return cfdi;
    }

    /**
     * Define el valor de la propiedad cfdi.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCfdi(JAXBElement<String> value) {
        this.cfdi = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionError.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getDescripcionError() {
        return descripcionError;
    }

    /**
     * Define el valor de la propiedad descripcionError.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setDescripcionError(JAXBElement<ArrayOfstring> value) {
        this.descripcionError = value;
    }

    /**
     * Obtiene el valor de la propiedad uuid.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUuid() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUuid(JAXBElement<String> value) {
        this.uuid = value;
    }

    /**
     * Obtiene el valor de la propiedad valido.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValido() {
        return valido;
    }

    /**
     * Define el valor de la propiedad valido.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValido(Boolean value) {
        this.valido = value;
    }

}
