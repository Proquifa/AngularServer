/**
 * CustomsDeclarationStatementDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;


/**
 * This provides the information necessary to identify the different
 * statements, declarations, acts, and/or certifications that apply to
 * this shipment.
 */
public class CustomsDeclarationStatementDetail  implements java.io.Serializable {
    /* This indicates the different statements, declarations, acts,
     * and/or certifications that apply to this shipment. */
    private com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType[] types;

    /* Specifies the NAFTA low value statement information. */
    private com.proquifa.net.negocio.envio.ship.stub.NaftaLowValueStatementDetail naftaLowValueStatementDetail;

    public CustomsDeclarationStatementDetail() {
    }

    public CustomsDeclarationStatementDetail(
           com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType[] types,
           com.proquifa.net.negocio.envio.ship.stub.NaftaLowValueStatementDetail naftaLowValueStatementDetail) {
           this.types = types;
           this.naftaLowValueStatementDetail = naftaLowValueStatementDetail;
    }


    /**
     * Gets the types value for this CustomsDeclarationStatementDetail.
     * 
     * @return types   * This indicates the different statements, declarations, acts,
     * and/or certifications that apply to this shipment.
     */
    public com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType[] getTypes() {
        return types;
    }


    /**
     * Sets the types value for this CustomsDeclarationStatementDetail.
     * 
     * @param types   * This indicates the different statements, declarations, acts,
     * and/or certifications that apply to this shipment.
     */
    public void setTypes(com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType[] types) {
        this.types = types;
    }

    public com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType getTypes(int i) {
        return this.types[i];
    }

    public void setTypes(int i, com.proquifa.net.negocio.envio.ship.stub.CustomsDeclarationStatementType _value) {
        this.types[i] = _value;
    }


    /**
     * Gets the naftaLowValueStatementDetail value for this CustomsDeclarationStatementDetail.
     * 
     * @return naftaLowValueStatementDetail   * Specifies the NAFTA low value statement information.
     */
    public com.proquifa.net.negocio.envio.ship.stub.NaftaLowValueStatementDetail getNaftaLowValueStatementDetail() {
        return naftaLowValueStatementDetail;
    }


    /**
     * Sets the naftaLowValueStatementDetail value for this CustomsDeclarationStatementDetail.
     * 
     * @param naftaLowValueStatementDetail   * Specifies the NAFTA low value statement information.
     */
    public void setNaftaLowValueStatementDetail(com.proquifa.net.negocio.envio.ship.stub.NaftaLowValueStatementDetail naftaLowValueStatementDetail) {
        this.naftaLowValueStatementDetail = naftaLowValueStatementDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomsDeclarationStatementDetail)) return false;
        CustomsDeclarationStatementDetail other = (CustomsDeclarationStatementDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.types==null && other.getTypes()==null) || 
             (this.types!=null &&
              java.util.Arrays.equals(this.types, other.getTypes()))) &&
            ((this.naftaLowValueStatementDetail==null && other.getNaftaLowValueStatementDetail()==null) || 
             (this.naftaLowValueStatementDetail!=null &&
              this.naftaLowValueStatementDetail.equals(other.getNaftaLowValueStatementDetail())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNaftaLowValueStatementDetail() != null) {
            _hashCode += getNaftaLowValueStatementDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomsDeclarationStatementDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsDeclarationStatementDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("types");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "Types"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "CustomsDeclarationStatementType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("naftaLowValueStatementDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaLowValueStatementDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/ship/v23", "NaftaLowValueStatementDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
