
package TurboPac;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ArrayOfStatusUuid complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStatusUuid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusUuid" type="{http://schemas.datacontract.org/2004/07/PacUtils.Core}StatusUuid" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStatusUuid", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", propOrder = {
    "statusUuid"
})
public class ArrayOfStatusUuid {

    @XmlElement(name = "StatusUuid", nillable = true)
    protected List<StatusUuid> statusUuid;

    /**
     * Gets the value of the statusUuid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusUuid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusUuid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusUuid }
     * 
     * 
     */
    public List<StatusUuid> getStatusUuid() {
        if (statusUuid == null) {
            statusUuid = new ArrayList<StatusUuid>();
        }
        return this.statusUuid;
    }

}
