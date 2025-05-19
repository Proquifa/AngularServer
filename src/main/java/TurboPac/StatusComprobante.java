
package TurboPac;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para StatusComprobante.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="StatusComprobante">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NoEncontrado"/>
 *     &lt;enumeration value="EnProceso"/>
 *     &lt;enumeration value="Enviado"/>
 *     &lt;enumeration value="Cancelado"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StatusComprobante", namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core")
@XmlEnum
public enum StatusComprobante {

    @XmlEnumValue("NoEncontrado")
    NO_ENCONTRADO("NoEncontrado"),
    @XmlEnumValue("EnProceso")
    EN_PROCESO("EnProceso"),
    @XmlEnumValue("Enviado")
    ENVIADO("Enviado"),
    @XmlEnumValue("Cancelado")
    CANCELADO("Cancelado");
    private final String value;

    StatusComprobante(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StatusComprobante fromValue(String v) {
        for (StatusComprobante c: StatusComprobante.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
