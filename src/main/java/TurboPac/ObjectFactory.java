
package TurboPac;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mypackage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _StatusUuid_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "StatusUuid");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _RespuestaTimbrado_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "RespuestaTimbrado");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _RespuestaCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "RespuestaCancelacion");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _ArrayOfStatusUuid_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "ArrayOfStatusUuid");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ResultadoConsulta_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "ResultadoConsulta");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _StatusComprobante_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "StatusComprobante");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _ObtenerStatusUuidPassword_QNAME = new QName("http://turbopac.mx/TurboPac", "password");
    private final static QName _ObtenerStatusUuidUuid_QNAME = new QName("http://turbopac.mx/TurboPac", "uuid");
    private final static QName _ObtenerStatusUuidUserName_QNAME = new QName("http://turbopac.mx/TurboPac", "userName");
    private final static QName _TimbraURLCfdi33ResponseTimbraURLCfdi33Result_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraURLCfdi33Result");
    private final static QName _TimbraCfdi40Comprobante_QNAME = new QName("http://turbopac.mx/TurboPac", "comprobante");
    private final static QName _TimbraRetencionResponseTimbraRetencionResult_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraRetencionResult");
    private final static QName _CancelaCfdiResponseCancelaCfdiResult_QNAME = new QName("http://turbopac.mx/TurboPac", "CancelaCfdiResult");
    private final static QName _TimbraCfdi33ResponseTimbraCfdi33Result_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraCfdi33Result");
    private final static QName _CancelaRetencionResponseCancelaRetencionResult_QNAME = new QName("http://turbopac.mx/TurboPac", "CancelaRetencionResult");
    private final static QName _ResultadoConsultaAcuseEnvio_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "AcuseEnvio");
    private final static QName _ResultadoConsultaAcuseCancelacion_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "AcuseCancelacion");
    private final static QName _ResultadoConsultaComprobante_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "Comprobante");
    private final static QName _CancelaRetencionRequestCancelacion_QNAME = new QName("http://turbopac.mx/TurboPac", "requestCancelacion");
    private final static QName _StatusUuidStatus_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "Status");
    private final static QName _StatusUuidUuid_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "Uuid");
    private final static QName _TimbraURLCfdi40Url_QNAME = new QName("http://turbopac.mx/TurboPac", "url");
    private final static QName _ObtenerStatusUuidResponseObtenerStatusUuidResult_QNAME = new QName("http://turbopac.mx/TurboPac", "ObtenerStatusUuidResult");
    private final static QName _TimbraCfdi40ResponseTimbraCfdi40Result_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraCfdi40Result");
    private final static QName _TimbraURLCfdi40ResponseTimbraURLCfdi40Result_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraURLCfdi40Result");
    private final static QName _RespuestaTimbradoCfdi_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "Cfdi");
    private final static QName _RespuestaTimbradoCadenaTimbre_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "CadenaTimbre");
    private final static QName _RespuestaTimbradoDescripcionError_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "DescripcionError");
    private final static QName _TimbraCfdiResponseTimbraCfdiResult_QNAME = new QName("http://turbopac.mx/TurboPac", "TimbraCfdiResult");
    private final static QName _RespuestaCancelacionAcuse_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "Acuse");
    private final static QName _RespuestaCancelacionStatusUuids_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "StatusUuids");
    private final static QName _RespuestaCancelacionMensajeError_QNAME = new QName("http://schemas.datacontract.org/2004/07/PacUtils.Core", "MensajeError");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfStatusUuid }
     * 
     */
    public ArrayOfStatusUuid createArrayOfStatusUuid() {
        return new ArrayOfStatusUuid();
    }

    /**
     * Create an instance of {@link ResultadoConsulta }
     * 
     */
    public ResultadoConsulta createResultadoConsulta() {
        return new ResultadoConsulta();
    }

    /**
     * Create an instance of {@link RespuestaTimbrado }
     * 
     */
    public RespuestaTimbrado createRespuestaTimbrado() {
        return new RespuestaTimbrado();
    }

    /**
     * Create an instance of {@link StatusUuid }
     * 
     */
    public StatusUuid createStatusUuid() {
        return new StatusUuid();
    }

    /**
     * Create an instance of {@link RespuestaCancelacion }
     * 
     */
    public RespuestaCancelacion createRespuestaCancelacion() {
        return new RespuestaCancelacion();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link ObtenerStatusUuid }
     * 
     */
    public ObtenerStatusUuid createObtenerStatusUuid() {
        return new ObtenerStatusUuid();
    }

    /**
     * Create an instance of {@link TimbraCfdi33Response }
     * 
     */
    public TimbraCfdi33Response createTimbraCfdi33Response() {
        return new TimbraCfdi33Response();
    }

    /**
     * Create an instance of {@link TimbraCfdi40Response }
     * 
     */
    public TimbraCfdi40Response createTimbraCfdi40Response() {
        return new TimbraCfdi40Response();
    }

    /**
     * Create an instance of {@link ObtenerStatusUuidResponse }
     * 
     */
    public ObtenerStatusUuidResponse createObtenerStatusUuidResponse() {
        return new ObtenerStatusUuidResponse();
    }

    /**
     * Create an instance of {@link CancelaCfdiResponse }
     * 
     */
    public CancelaCfdiResponse createCancelaCfdiResponse() {
        return new CancelaCfdiResponse();
    }

    /**
     * Create an instance of {@link TimbraRetencionResponse }
     * 
     */
    public TimbraRetencionResponse createTimbraRetencionResponse() {
        return new TimbraRetencionResponse();
    }

    /**
     * Create an instance of {@link TimbraCfdi }
     * 
     */
    public TimbraCfdi createTimbraCfdi() {
        return new TimbraCfdi();
    }

    /**
     * Create an instance of {@link CancelaCfdi }
     * 
     */
    public CancelaCfdi createCancelaCfdi() {
        return new CancelaCfdi();
    }

    /**
     * Create an instance of {@link TimbraRetencion }
     * 
     */
    public TimbraRetencion createTimbraRetencion() {
        return new TimbraRetencion();
    }

    /**
     * Create an instance of {@link TimbraCfdi40 }
     * 
     */
    public TimbraCfdi40 createTimbraCfdi40() {
        return new TimbraCfdi40();
    }

    /**
     * Create an instance of {@link CancelaRetencion }
     * 
     */
    public CancelaRetencion createCancelaRetencion() {
        return new CancelaRetencion();
    }

    /**
     * Create an instance of {@link TimbraCfdi33 }
     * 
     */
    public TimbraCfdi33 createTimbraCfdi33() {
        return new TimbraCfdi33();
    }

    /**
     * Create an instance of {@link TimbraURLCfdi33Response }
     * 
     */
    public TimbraURLCfdi33Response createTimbraURLCfdi33Response() {
        return new TimbraURLCfdi33Response();
    }

    /**
     * Create an instance of {@link TimbraURLCfdi40Response }
     * 
     */
    public TimbraURLCfdi40Response createTimbraURLCfdi40Response() {
        return new TimbraURLCfdi40Response();
    }

    /**
     * Create an instance of {@link TimbraURLCfdi40 }
     * 
     */
    public TimbraURLCfdi40 createTimbraURLCfdi40() {
        return new TimbraURLCfdi40();
    }

    /**
     * Create an instance of {@link CancelaRetencionResponse }
     * 
     */
    public CancelaRetencionResponse createCancelaRetencionResponse() {
        return new CancelaRetencionResponse();
    }

    /**
     * Create an instance of {@link TimbraCfdiResponse }
     * 
     */
    public TimbraCfdiResponse createTimbraCfdiResponse() {
        return new TimbraCfdiResponse();
    }

    /**
     * Create an instance of {@link TimbraURLCfdi33 }
     * 
     */
    public TimbraURLCfdi33 createTimbraURLCfdi33() {
        return new TimbraURLCfdi33();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusUuid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "StatusUuid")
    public JAXBElement<StatusUuid> createStatusUuid(StatusUuid value) {
        return new JAXBElement<StatusUuid>(_StatusUuid_QNAME, StatusUuid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_ArrayOfstring_QNAME, ArrayOfstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "RespuestaTimbrado")
    public JAXBElement<RespuestaTimbrado> createRespuestaTimbrado(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_RespuestaTimbrado_QNAME, RespuestaTimbrado.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "RespuestaCancelacion")
    public JAXBElement<RespuestaCancelacion> createRespuestaCancelacion(RespuestaCancelacion value) {
        return new JAXBElement<RespuestaCancelacion>(_RespuestaCancelacion_QNAME, RespuestaCancelacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStatusUuid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "ArrayOfStatusUuid")
    public JAXBElement<ArrayOfStatusUuid> createArrayOfStatusUuid(ArrayOfStatusUuid value) {
        return new JAXBElement<ArrayOfStatusUuid>(_ArrayOfStatusUuid_QNAME, ArrayOfStatusUuid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoConsulta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "ResultadoConsulta")
    public JAXBElement<ResultadoConsulta> createResultadoConsulta(ResultadoConsulta value) {
        return new JAXBElement<ResultadoConsulta>(_ResultadoConsulta_QNAME, ResultadoConsulta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusComprobante }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "StatusComprobante")
    public JAXBElement<StatusComprobante> createStatusComprobante(StatusComprobante value) {
        return new JAXBElement<StatusComprobante>(_StatusComprobante_QNAME, StatusComprobante.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = ObtenerStatusUuid.class)
    public JAXBElement<String> createObtenerStatusUuidPassword(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, ObtenerStatusUuid.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "uuid", scope = ObtenerStatusUuid.class)
    public JAXBElement<String> createObtenerStatusUuidUuid(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUuid_QNAME, String.class, ObtenerStatusUuid.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = ObtenerStatusUuid.class)
    public JAXBElement<String> createObtenerStatusUuidUserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, ObtenerStatusUuid.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraURLCfdi33Result", scope = TimbraURLCfdi33Response.class)
    public JAXBElement<RespuestaTimbrado> createTimbraURLCfdi33ResponseTimbraURLCfdi33Result(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraURLCfdi33ResponseTimbraURLCfdi33Result_QNAME, RespuestaTimbrado.class, TimbraURLCfdi33Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraCfdi40 .class)
    public JAXBElement<String> createTimbraCfdi40Password(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "comprobante", scope = TimbraCfdi40 .class)
    public JAXBElement<String> createTimbraCfdi40Comprobante(String value) {
        return new JAXBElement<String>(_TimbraCfdi40Comprobante_QNAME, String.class, TimbraCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraCfdi40 .class)
    public JAXBElement<String> createTimbraCfdi40UserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraRetencionResult", scope = TimbraRetencionResponse.class)
    public JAXBElement<RespuestaTimbrado> createTimbraRetencionResponseTimbraRetencionResult(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraRetencionResponseTimbraRetencionResult_QNAME, RespuestaTimbrado.class, TimbraRetencionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "CancelaCfdiResult", scope = CancelaCfdiResponse.class)
    public JAXBElement<RespuestaCancelacion> createCancelaCfdiResponseCancelaCfdiResult(RespuestaCancelacion value) {
        return new JAXBElement<RespuestaCancelacion>(_CancelaCfdiResponseCancelaCfdiResult_QNAME, RespuestaCancelacion.class, CancelaCfdiResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraCfdi33Result", scope = TimbraCfdi33Response.class)
    public JAXBElement<RespuestaTimbrado> createTimbraCfdi33ResponseTimbraCfdi33Result(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraCfdi33ResponseTimbraCfdi33Result_QNAME, RespuestaTimbrado.class, TimbraCfdi33Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaCancelacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "CancelaRetencionResult", scope = CancelaRetencionResponse.class)
    public JAXBElement<RespuestaCancelacion> createCancelaRetencionResponseCancelaRetencionResult(RespuestaCancelacion value) {
        return new JAXBElement<RespuestaCancelacion>(_CancelaRetencionResponseCancelaRetencionResult_QNAME, RespuestaCancelacion.class, CancelaRetencionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "AcuseEnvio", scope = ResultadoConsulta.class)
    public JAXBElement<String> createResultadoConsultaAcuseEnvio(String value) {
        return new JAXBElement<String>(_ResultadoConsultaAcuseEnvio_QNAME, String.class, ResultadoConsulta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "AcuseCancelacion", scope = ResultadoConsulta.class)
    public JAXBElement<String> createResultadoConsultaAcuseCancelacion(String value) {
        return new JAXBElement<String>(_ResultadoConsultaAcuseCancelacion_QNAME, String.class, ResultadoConsulta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Comprobante", scope = ResultadoConsulta.class)
    public JAXBElement<String> createResultadoConsultaComprobante(String value) {
        return new JAXBElement<String>(_ResultadoConsultaComprobante_QNAME, String.class, ResultadoConsulta.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = CancelaRetencion.class)
    public JAXBElement<String> createCancelaRetencionPassword(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, CancelaRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "requestCancelacion", scope = CancelaRetencion.class)
    public JAXBElement<String> createCancelaRetencionRequestCancelacion(String value) {
        return new JAXBElement<String>(_CancelaRetencionRequestCancelacion_QNAME, String.class, CancelaRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = CancelaRetencion.class)
    public JAXBElement<String> createCancelaRetencionUserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, CancelaRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Status", scope = StatusUuid.class)
    public JAXBElement<String> createStatusUuidStatus(String value) {
        return new JAXBElement<String>(_StatusUuidStatus_QNAME, String.class, StatusUuid.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Uuid", scope = StatusUuid.class)
    public JAXBElement<String> createStatusUuidUuid(String value) {
        return new JAXBElement<String>(_StatusUuidUuid_QNAME, String.class, StatusUuid.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraURLCfdi40 .class)
    public JAXBElement<String> createTimbraURLCfdi40Password(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraURLCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "url", scope = TimbraURLCfdi40 .class)
    public JAXBElement<String> createTimbraURLCfdi40Url(String value) {
        return new JAXBElement<String>(_TimbraURLCfdi40Url_QNAME, String.class, TimbraURLCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraURLCfdi40 .class)
    public JAXBElement<String> createTimbraURLCfdi40UserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraURLCfdi40 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraURLCfdi33 .class)
    public JAXBElement<String> createTimbraURLCfdi33Password(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraURLCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "url", scope = TimbraURLCfdi33 .class)
    public JAXBElement<String> createTimbraURLCfdi33Url(String value) {
        return new JAXBElement<String>(_TimbraURLCfdi40Url_QNAME, String.class, TimbraURLCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraURLCfdi33 .class)
    public JAXBElement<String> createTimbraURLCfdi33UserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraURLCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraCfdi33 .class)
    public JAXBElement<String> createTimbraCfdi33Password(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "comprobante", scope = TimbraCfdi33 .class)
    public JAXBElement<String> createTimbraCfdi33Comprobante(String value) {
        return new JAXBElement<String>(_TimbraCfdi40Comprobante_QNAME, String.class, TimbraCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraCfdi33 .class)
    public JAXBElement<String> createTimbraCfdi33UserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraCfdi33 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultadoConsulta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "ObtenerStatusUuidResult", scope = ObtenerStatusUuidResponse.class)
    public JAXBElement<ResultadoConsulta> createObtenerStatusUuidResponseObtenerStatusUuidResult(ResultadoConsulta value) {
        return new JAXBElement<ResultadoConsulta>(_ObtenerStatusUuidResponseObtenerStatusUuidResult_QNAME, ResultadoConsulta.class, ObtenerStatusUuidResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraCfdi40Result", scope = TimbraCfdi40Response.class)
    public JAXBElement<RespuestaTimbrado> createTimbraCfdi40ResponseTimbraCfdi40Result(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraCfdi40ResponseTimbraCfdi40Result_QNAME, RespuestaTimbrado.class, TimbraCfdi40Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = CancelaCfdi.class)
    public JAXBElement<String> createCancelaCfdiPassword(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, CancelaCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "requestCancelacion", scope = CancelaCfdi.class)
    public JAXBElement<String> createCancelaCfdiRequestCancelacion(String value) {
        return new JAXBElement<String>(_CancelaRetencionRequestCancelacion_QNAME, String.class, CancelaCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = CancelaCfdi.class)
    public JAXBElement<String> createCancelaCfdiUserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, CancelaCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraRetencion.class)
    public JAXBElement<String> createTimbraRetencionPassword(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "comprobante", scope = TimbraRetencion.class)
    public JAXBElement<String> createTimbraRetencionComprobante(String value) {
        return new JAXBElement<String>(_TimbraCfdi40Comprobante_QNAME, String.class, TimbraRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraRetencion.class)
    public JAXBElement<String> createTimbraRetencionUserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraRetencion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraURLCfdi40Result", scope = TimbraURLCfdi40Response.class)
    public JAXBElement<RespuestaTimbrado> createTimbraURLCfdi40ResponseTimbraURLCfdi40Result(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraURLCfdi40ResponseTimbraURLCfdi40Result_QNAME, RespuestaTimbrado.class, TimbraURLCfdi40Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "password", scope = TimbraCfdi.class)
    public JAXBElement<String> createTimbraCfdiPassword(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidPassword_QNAME, String.class, TimbraCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "comprobante", scope = TimbraCfdi.class)
    public JAXBElement<String> createTimbraCfdiComprobante(String value) {
        return new JAXBElement<String>(_TimbraCfdi40Comprobante_QNAME, String.class, TimbraCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "userName", scope = TimbraCfdi.class)
    public JAXBElement<String> createTimbraCfdiUserName(String value) {
        return new JAXBElement<String>(_ObtenerStatusUuidUserName_QNAME, String.class, TimbraCfdi.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Uuid", scope = RespuestaTimbrado.class)
    public JAXBElement<String> createRespuestaTimbradoUuid(String value) {
        return new JAXBElement<String>(_StatusUuidUuid_QNAME, String.class, RespuestaTimbrado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Cfdi", scope = RespuestaTimbrado.class)
    public JAXBElement<String> createRespuestaTimbradoCfdi(String value) {
        return new JAXBElement<String>(_RespuestaTimbradoCfdi_QNAME, String.class, RespuestaTimbrado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "CadenaTimbre", scope = RespuestaTimbrado.class)
    public JAXBElement<String> createRespuestaTimbradoCadenaTimbre(String value) {
        return new JAXBElement<String>(_RespuestaTimbradoCadenaTimbre_QNAME, String.class, RespuestaTimbrado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "DescripcionError", scope = RespuestaTimbrado.class)
    public JAXBElement<ArrayOfstring> createRespuestaTimbradoDescripcionError(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_RespuestaTimbradoDescripcionError_QNAME, ArrayOfstring.class, RespuestaTimbrado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RespuestaTimbrado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://turbopac.mx/TurboPac", name = "TimbraCfdiResult", scope = TimbraCfdiResponse.class)
    public JAXBElement<RespuestaTimbrado> createTimbraCfdiResponseTimbraCfdiResult(RespuestaTimbrado value) {
        return new JAXBElement<RespuestaTimbrado>(_TimbraCfdiResponseTimbraCfdiResult_QNAME, RespuestaTimbrado.class, TimbraCfdiResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "Acuse", scope = RespuestaCancelacion.class)
    public JAXBElement<String> createRespuestaCancelacionAcuse(String value) {
        return new JAXBElement<String>(_RespuestaCancelacionAcuse_QNAME, String.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStatusUuid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "StatusUuids", scope = RespuestaCancelacion.class)
    public JAXBElement<ArrayOfStatusUuid> createRespuestaCancelacionStatusUuids(ArrayOfStatusUuid value) {
        return new JAXBElement<ArrayOfStatusUuid>(_RespuestaCancelacionStatusUuids_QNAME, ArrayOfStatusUuid.class, RespuestaCancelacion.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PacUtils.Core", name = "MensajeError", scope = RespuestaCancelacion.class)
    public JAXBElement<String> createRespuestaCancelacionMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaCancelacionMensajeError_QNAME, String.class, RespuestaCancelacion.class, value);
    }

}
